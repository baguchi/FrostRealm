package baguchan.frostrealm.client;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.blockentity.FrostChestBlockEntity;
import baguchan.frostrealm.capability.FrostLivingCapability;
import baguchan.frostrealm.client.event.ClientFogEvent;
import baguchan.frostrealm.client.model.*;
import baguchan.frostrealm.client.overlay.FrostOverlay;
import baguchan.frostrealm.client.render.*;
import baguchan.frostrealm.client.render.blockentity.FrostChestRenderer;
import baguchan.frostrealm.client.screen.AuroraInfuserScreen;
import baguchan.frostrealm.item.GlimmerRockItem;
import baguchan.frostrealm.item.YetiFurArmorItem;
import baguchan.frostrealm.registry.*;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Matrix4f;

import javax.annotation.Nonnull;


@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = FrostRealm.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientRegistrar {
	public static final CubeDeformation OUTER_ARMOR_DEFORMATION = new CubeDeformation(1.0F);
	public static final CubeDeformation INNER_ARMOR_DEFORMATION = new CubeDeformation(0.5F);

	@SubscribeEvent
	public static void registerClientExtend(RegisterClientExtensionsEvent event) {
		event.registerItem(new IClientItemExtensions() {
			BlockEntityWithoutLevelRenderer myRenderer;


			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				if (Minecraft.getInstance().getEntityRenderDispatcher() != null && myRenderer == null) {
					myRenderer = new BlockEntityWithoutLevelRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()) {
						private FrostChestBlockEntity blockEntity;

						@Override
						public void renderByItem(@Nonnull ItemStack stack, @Nonnull ItemDisplayContext transformType, @Nonnull PoseStack matrix, @Nonnull MultiBufferSource buffer, int x, int y) {
							if (blockEntity == null) {
								blockEntity = new FrostChestBlockEntity(BlockPos.ZERO, FrostBlocks.FROSTROOT_CHEST.get().defaultBlockState());
							}
							Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(blockEntity, matrix, buffer, x, y);
						}
					};
				}

				return myRenderer;
			}
		}, FrostBlocks.FROSTROOT_CHEST.get().asItem());

		event.registerItem(new GlimmerRockItem.ItemRender(), FrostItems.GLIMMERROCK.get(), FrostItems.CRYONITE_CREAM.get());
		event.registerItem(YetiFurArmorItem.ArmorRender.INSTANCE, FrostItems.YETI_FUR_BOOTS.get(), FrostItems.YETI_FUR_LEGGINGS.get(), FrostItems.YETI_FUR_CHESTPLATE.get(), FrostItems.YETI_FUR_HELMET.get());
		event.registerItem(YetiFurArmorItem.ArmorRender.INSTANCE, FrostItems.FROST_BOAR_FUR_BOOTS.get(), FrostItems.FROST_BOAR_FUR_LEGGINGS.get(), FrostItems.FROST_BOAR_FUR_CHESTPLATE.get(), FrostItems.FROST_BOAR_FUR_HELMET.get());
		event.registerFluidType(new IClientFluidTypeExtensions() {
			private static final ResourceLocation TEXTURE_STILL = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "block/hot_spring_still");
			private static final ResourceLocation TEXTURE_FLOW = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "block/hot_spring_flow");
			private static final ResourceLocation TEXTURE_OVERLAY = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/block/hot_spring_still.png");

			@Override
			public ResourceLocation getStillTexture() {
				return TEXTURE_STILL;
			}

			@Override
			public ResourceLocation getFlowingTexture() {
				return TEXTURE_FLOW;
			}

			@Override
			public ResourceLocation getRenderOverlayTexture(Minecraft mc) {
				return TEXTURE_OVERLAY;
			}


			@Override
			public void renderOverlay(Minecraft mc, PoseStack stack) {
				ResourceLocation texture = this.getRenderOverlayTexture(mc);
				if (texture == null) return;
				RenderSystem.setShader(GameRenderer::getPositionTexShader);
				RenderSystem.setShaderTexture(0, texture);
				BufferBuilder buffer = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
				BlockPos playerEyePos = BlockPos.containing(mc.player.getX(), mc.player.getEyeY(), mc.player.getZ());
				float brightness = LightTexture.getBrightness(mc.player.level().dimensionType(), mc.player.level().getMaxLocalRawBrightness(playerEyePos));
				RenderSystem.enableBlend();
				RenderSystem.defaultBlendFunc();
				RenderSystem.setShaderColor(brightness, brightness, brightness, 0.65F);
				float uOffset = -mc.player.getYRot() / 64.0F;
				float vOffset = mc.player.getXRot() / 64.0F;
				Matrix4f pose = stack.last().pose();
				buffer.addVertex(pose, -1.0F, -1.0F, -0.5F).setUv(4.0F + uOffset, 4.0F + vOffset);
				buffer.addVertex(pose, 1.0F, -1.0F, -0.5F).setUv(uOffset, 4.0F + vOffset);
				buffer.addVertex(pose, 1.0F, 1.0F, -0.5F).setUv(uOffset, vOffset);
				buffer.addVertex(pose, -1.0F, 1.0F, -0.5F).setUv(4.0F + uOffset, vOffset);
				BufferUploader.drawWithShader(buffer.buildOrThrow());
				RenderSystem.disableBlend();
			}
		}, FrostFluidTypes.HOT_SPRING.get());
	}

	@SubscribeEvent
	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(FrostEntities.MARMOT.get(), MarmotRenderer::new);
        event.registerEntityRenderer(FrostEntities.SNOWPILE_QUAIL.get(), SnowPileQuailRenderer::new);
		event.registerEntityRenderer(FrostEntities.CRYSTAL_FOX.get(), CrystalFoxRenderer::new);
        event.registerEntityRenderer(FrostEntities.SNOW_MOLE.get(), SnowMoleRenderer::new);
		event.registerEntityRenderer(FrostEntities.WOLFFLUE.get(), WolfflueRenderer::new);

        event.registerEntityRenderer(FrostEntities.YETI.get(), YetiRenderer::new);
        event.registerEntityRenderer(FrostEntities.FROST_WRAITH.get(), FrostWraithRenderer::new);
		event.registerEntityRenderer(FrostEntities.ASTRA_BALL.get(), AstraBallRenderer::new);
        event.registerEntityRenderer(FrostEntities.FROST_BOAR.get(), FrostBoarRenderer::new);
        event.registerEntityRenderer(FrostEntities.WARPED_CRYSTAL_SHARD.get(), WarpedCrystalRenderer::new);
        event.registerEntityRenderer(FrostEntities.SEEKER.get(), SeekerRenderer::new);
        event.registerEntityRenderer(FrostEntities.SEAL.get(), SealRenderer::new);
        event.registerEntityRenderer(FrostEntities.MIND_VINE.get(), MindVineRenderer::new);
        event.registerEntityRenderer(FrostEntities.CORRUPTED_WALKER.get(), CorruptedWalkerRenderer::new);
        event.registerEntityRenderer(FrostEntities.VENOM_BALL.get(), VenomBallRenderer::new);
		event.registerEntityRenderer(FrostEntities.VENOCHEM.get(), VenochemRenderer::new);
		event.registerEntityRenderer(FrostEntities.GOKKUR.get(), GokkurRenderer::new);

		event.registerBlockEntityRenderer(FrostBlockEntitys.FROST_CHEST.get(), FrostChestRenderer::new);
	}

	@SubscribeEvent
	public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
		LayerDefinition layerdefinition1 = LayerDefinition.create(HumanoidModel.createMesh(OUTER_ARMOR_DEFORMATION, 0.0F), 64, 32);
		LayerDefinition layerdefinition3 = LayerDefinition.create(HumanoidModel.createMesh(INNER_ARMOR_DEFORMATION, 0.0F), 64, 32);

		event.registerLayerDefinition(FrostModelLayers.YETI, YetiModel::createBodyLayer);
		event.registerLayerDefinition(FrostModelLayers.FROST_WRAITH, FrostWraithModel::createBodyLayer);

		event.registerLayerDefinition(FrostModelLayers.MARMOT, MarmotModel::createBodyLayer);
        event.registerLayerDefinition(FrostModelLayers.SNOWPILE_QUAIL, SnowPileQuailModel::createBodyLayer);
		event.registerLayerDefinition(FrostModelLayers.WOLFFLUE, () -> WolfflueModel.createBodyLayer(new CubeDeformation(0.0F)));
		event.registerLayerDefinition(FrostModelLayers.WOLFFLUE_ARMOR, () -> WolfflueModel.createBodyLayer(new CubeDeformation(0.2F)));

        event.registerLayerDefinition(FrostModelLayers.CRYSTAL_FOX, CrystalFoxModel::createBodyLayer);
        event.registerLayerDefinition(FrostModelLayers.SNOW_MOLE, SnowMoleModel::createBodyLayer);
        event.registerLayerDefinition(FrostModelLayers.ASTRA_BALL, AstraBallModel::createBodyLayer);
        event.registerLayerDefinition(FrostModelLayers.FROST_BOAR, FrostBoarModel::createBodyLayer);
        event.registerLayerDefinition(FrostModelLayers.SEEKER, SeekerModel::createBodyLayer);
        event.registerLayerDefinition(FrostModelLayers.SEAL, SealModel::createBodyLayer);
        event.registerLayerDefinition(FrostModelLayers.MIND_VINE, MindVineModel::createBodyLayer);

		event.registerLayerDefinition(FrostModelLayers.GOKKUR, GokkurModel::createBodyLayer);

		event.registerLayerDefinition(FrostModelLayers.VENOCHEM, VenochemModel::createBodyLayer);
		event.registerLayerDefinition(FrostModelLayers.VENOM_BALL, VenomBallModel::createBodyLayer);

        event.registerLayerDefinition(FrostModelLayers.CORRUPTED_WALKER, CorruptedWalkerModel::createBodyLayer);
        event.registerLayerDefinition(FrostModelLayers.CORRUPTED_WALKER_FOOT, CorruptedWalkerFootModel::createBodyLayer);
        event.registerLayerDefinition(FrostModelLayers.CORRUPTED_WALKER_PART, CorruptedWalkerPartModel::createBodyLayer);


		event.registerLayerDefinition(FrostModelLayers.YETI_FUR_ARMOR_INNER, () -> YetiFurArmorModel.createBodyLayer(INNER_ARMOR_DEFORMATION));
        event.registerLayerDefinition(FrostModelLayers.YETI_FUR_ARMOR_OUTER, () -> YetiFurArmorModel.createBodyLayer(OUTER_ARMOR_DEFORMATION));

        event.registerLayerDefinition(FrostModelLayers.FROST_BEASTER_INNER_ARMOR, () -> layerdefinition3);
        event.registerLayerDefinition(FrostModelLayers.FROST_BEASTER_OUTER_ARMOR, () -> layerdefinition1);
	}

	private static ModelLayerLocation createLocation(String p_171301_, String p_171302_) {
		return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, p_171301_), p_171302_);
	}

	public static void renderTileEntity() {
	}

	public static void renderBlockColor() {
		Minecraft.getInstance().getBlockColors().register((p_92621_, p_92622_, p_92623_, p_92624_) -> {
			return p_92622_ != null && p_92623_ != null ? BiomeColors.getAverageGrassColor(p_92622_, p_92623_) : GrassColor.get(0.5D, 1.0D);
		}, FrostBlocks.FROZEN_GRASS_BLOCK.get());

		Minecraft.getInstance().getItemColors().register((p_92687_, p_92688_) -> {
			BlockState blockstate = ((BlockItem) p_92687_.getItem()).getBlock().defaultBlockState();
			return Minecraft.getInstance().getBlockColors().getColor(blockstate, null, null, p_92688_);
		}, FrostBlocks.FROZEN_GRASS_BLOCK.get());


		Minecraft.getInstance().getBlockColors().register((p_92621_, p_92622_, p_92623_, p_92624_) -> {
			return p_92622_ != null && p_92623_ != null ? BiomeColors.getAverageGrassColor(p_92622_, p_92623_) : GrassColor.get(0.5D, 1.0D);
		}, FrostBlocks.COLD_GRASS.get());
		Minecraft.getInstance().getBlockColors().register((p_92621_, p_92622_, p_92623_, p_92624_) -> {
            return p_92622_ != null && p_92623_ != null ? BiomeColors.getAverageGrassColor(p_92622_, p_92623_) : GrassColor.get(0.5D, 1.0D);
		}, FrostBlocks.COLD_TALL_GRASS.get());

		Minecraft.getInstance().getItemColors().register((p_92687_, p_92688_) -> {
			BlockState blockstate = ((BlockItem) p_92687_.getItem()).getBlock().defaultBlockState();
			return Minecraft.getInstance().getBlockColors().getColor(blockstate, null, null, p_92688_);
		}, FrostBlocks.COLD_GRASS.get());
		Minecraft.getInstance().getItemColors().register((p_92687_, p_92688_) -> {
			BlockState blockstate = ((BlockItem) p_92687_.getItem()).getBlock().defaultBlockState();
			return Minecraft.getInstance().getBlockColors().getColor(blockstate, null, null, p_92688_);
		}, FrostBlocks.COLD_TALL_GRASS.get());



	}


	public static void setup(FMLCommonSetupEvent event) {
		FrostRenderType.init();
        NeoForge.EVENT_BUS.register(new ClientFogEvent());
		renderBlockColor();
	}

	@SubscribeEvent
	public static void renderHudEvent(RegisterGuiLayersEvent event) {
		event.registerAbove(VanillaGuiLayers.FOOD_LEVEL, FrostRealm.prefix("frost_overlay"), new FrostOverlay());
		event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "frost_portal_overlay"), (guiGraphics, partialTicks) -> {
			Minecraft minecraft = Minecraft.getInstance();
			Window window = minecraft.getWindow();
			LocalPlayer player = minecraft.player;
			if (player != null) {
				renderPortalOverlay(guiGraphics, minecraft, window, player.getData(FrostAttachs.FROST_LIVING.get()), partialTicks);
			}
		});
	}


	private static void renderPortalOverlay(GuiGraphics guiGraphics, Minecraft minecraft, Window window, FrostLivingCapability handler, DeltaTracker partialTicks) {
		float timeInPortal = Mth.lerp(partialTicks.getGameTimeDeltaPartialTick(false), handler.getPrevPortalAnimTime(), handler.getPortalAnimTime());
		if (timeInPortal > 0.0F) {
			if (timeInPortal < 1.0F) {
				timeInPortal *= timeInPortal;
				timeInPortal *= timeInPortal;
				timeInPortal = timeInPortal * 0.8F + 0.2F;
			}

			RenderSystem.disableDepthTest();
			RenderSystem.depthMask(false);
			RenderSystem.enableBlend();
			guiGraphics.setColor(1.0F, 1.0F, 1.0F, timeInPortal);
			TextureAtlasSprite textureatlassprite = minecraft.getBlockRenderer().getBlockModelShaper().getParticleIcon(FrostBlocks.FROST_PORTAL.get().defaultBlockState());
			guiGraphics.blit(0, 0, -90, guiGraphics.guiWidth(), guiGraphics.guiHeight(), textureatlassprite);
			RenderSystem.disableBlend();
			RenderSystem.depthMask(true);
			RenderSystem.enableDepthTest();
			guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	@SubscribeEvent
	public static void registerDimensionEffect(RegisterDimensionSpecialEffectsEvent event) {
		FrostRealmRenderInfo renderInfo = new FrostRealmRenderInfo(192.0F, true, DimensionSpecialEffects.SkyType.NORMAL, false, false);
		event.register(FrostRealm.prefix("renderer"), renderInfo);
	}

    @SubscribeEvent
    public static void screenEvent(RegisterMenuScreensEvent event) {
        event.register(FrostMenuTypes.AURORA_INFUSER.get(), AuroraInfuserScreen::new);
    }


}