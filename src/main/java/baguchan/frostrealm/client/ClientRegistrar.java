package baguchan.frostrealm.client;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.capability.FrostLivingCapability;
import baguchan.frostrealm.client.event.ClientFogEvent;
import baguchan.frostrealm.client.model.*;
import baguchan.frostrealm.client.overlay.FrostOverlay;
import baguchan.frostrealm.client.render.*;
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
import net.minecraft.world.level.GrassColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Matrix4f;


@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = FrostRealm.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientRegistrar {
	public static final CubeDeformation OUTER_ARMOR_DEFORMATION = new CubeDeformation(1.0F);
	public static final CubeDeformation INNER_ARMOR_DEFORMATION = new CubeDeformation(0.5F);

	@SubscribeEvent
	public static void registerClientExtend(RegisterClientExtensionsEvent event) {
		event.registerItem(new GlimmerRockItem.ItemRender(), FrostItems.GLIMMERROCK.get(), FrostItems.CRYONITE_CREAM.get());
		event.registerItem(YetiFurArmorItem.ArmorRender.INSTANCE, FrostItems.YETI_FUR_BOOTS.get(), FrostItems.YETI_FUR_LEGGINGS.get(), FrostItems.YETI_FUR_CHESTPLATE.get(), FrostItems.YETI_FUR_HELMET.get());
		event.registerItem(YetiFurArmorItem.ArmorRender.INSTANCE, FrostItems.FROST_BOAR_FUR_BOOTS.get(), FrostItems.FROST_BOAR_FUR_LEGGINGS.get(), FrostItems.FROST_BOAR_FUR_CHESTPLATE.get(), FrostItems.FROST_BOAR_FUR_HELMET.get());
		event.registerFluidType(new IClientFluidTypeExtensions() {
			private static final ResourceLocation TEXTURE_STILL = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "block/hot_spring_still");
			private static final ResourceLocation TEXTURE_FLOW = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "block/hot_spring_flow");
			private static final ResourceLocation TEXTURE_OVERLAY = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/block/hot_spring_still_overlay.png");

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
			public void renderOverlay(Minecraft mc, PoseStack poseStack, MultiBufferSource buffers) {
				ResourceLocation texture = this.getRenderOverlayTexture(mc);
				if (texture == null) return;
				RenderSystem.setShader(CoreShaders.POSITION_TEX);
				RenderSystem.setShaderTexture(0, texture);
				BufferBuilder buffer = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
				BlockPos playerEyePos = BlockPos.containing(mc.player.getX(), mc.player.getEyeY(), mc.player.getZ());
				float brightness = LightTexture.getBrightness(mc.player.level().dimensionType(), mc.player.level().getMaxLocalRawBrightness(playerEyePos));
				RenderSystem.enableBlend();
				RenderSystem.defaultBlendFunc();
				RenderSystem.setShaderColor(brightness, brightness, brightness, 0.65F);
				float uOffset = -mc.player.getYRot() / 64.0F;
				float vOffset = mc.player.getXRot() / 64.0F;
				Matrix4f pose = poseStack.last().pose();
				buffer.addVertex(pose, -1.0F, -1.0F, -0.5F).setUv(4.0F + uOffset, 4.0F + vOffset);
				buffer.addVertex(pose, 1.0F, -1.0F, -0.5F).setUv(uOffset, 4.0F + vOffset);
				buffer.addVertex(pose, 1.0F, 1.0F, -0.5F).setUv(uOffset, vOffset);
				buffer.addVertex(pose, -1.0F, 1.0F, -0.5F).setUv(4.0F + uOffset, vOffset);
				BufferUploader.drawWithShader(buffer.buildOrThrow());
				RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
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
		event.registerEntityRenderer(FrostEntities.FERRET.get(), FerretRenderer::new);

        event.registerEntityRenderer(FrostEntities.YETI.get(), YetiRenderer::new);
        event.registerEntityRenderer(FrostEntities.FROST_WRAITH.get(), FrostWraithRenderer::new);
		event.registerEntityRenderer(FrostEntities.ASTRA_BALL.get(), AstraBallRenderer::new);
        event.registerEntityRenderer(FrostEntities.FROST_BOAR.get(), FrostBoarRenderer::new);
        event.registerEntityRenderer(FrostEntities.SEEKER.get(), SeekerRenderer::new);
        event.registerEntityRenderer(FrostEntities.SEAL.get(), SealRenderer::new);
		event.registerEntityRenderer(FrostEntities.CORRUPTED_WALKER.get(), CorruptedWalkerRenderer::new);
        event.registerEntityRenderer(FrostEntities.VENOM_BALL.get(), VenomBallRenderer::new);
		event.registerEntityRenderer(FrostEntities.VENOCHEM.get(), VenochemRenderer::new);
		event.registerEntityRenderer(FrostEntities.GOKKUR.get(), GokkurRenderer::new);
		event.registerEntityRenderer(FrostEntities.UNDER_GOKKUR.get(), UnderGokkurRenderer::new);
		event.registerEntityRenderer(FrostEntities.ROOT_DEER.get(), RootDeerRenderer::new);
		event.registerEntityRenderer(FrostEntities.FLYING_BLOCK.get(), FlyingBlockRenderer::new);
		event.registerEntityRenderer(FrostEntities.SILK_MOON_WORM.get(), SilkMoonWormRenderer::new);
		event.registerEntityRenderer(FrostEntities.SILK_MOON.get(), SilkMoonRenderer::new);
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
		event.registerLayerDefinition(FrostModelLayers.FERRET, FerretModel::createBodyLayer);


		event.registerLayerDefinition(FrostModelLayers.CRYSTAL_FOX, CrystalFoxModel::createBodyLayer);
        event.registerLayerDefinition(FrostModelLayers.SNOW_MOLE, SnowMoleModel::createBodyLayer);
        event.registerLayerDefinition(FrostModelLayers.ASTRA_BALL, AstraBallModel::createBodyLayer);
        event.registerLayerDefinition(FrostModelLayers.FROST_BOAR, FrostBoarModel::createBodyLayer);
        event.registerLayerDefinition(FrostModelLayers.SEEKER, SeekerModel::createBodyLayer);
        event.registerLayerDefinition(FrostModelLayers.SEAL, SealModel::createBodyLayer);

		event.registerLayerDefinition(FrostModelLayers.GOKKUR, GokkurModel::createBodyLayer);

		event.registerLayerDefinition(FrostModelLayers.VENOCHEM, VenochemModel::createBodyLayer);
		event.registerLayerDefinition(FrostModelLayers.VENOM_BALL, VenomBallModel::createBodyLayer);

        event.registerLayerDefinition(FrostModelLayers.CORRUPTED_WALKER, CorruptedWalkerModel::createBodyLayer);
        event.registerLayerDefinition(FrostModelLayers.CORRUPTED_WALKER_FOOT, CorruptedWalkerFootModel::createBodyLayer);
        event.registerLayerDefinition(FrostModelLayers.CORRUPTED_WALKER_PART, CorruptedWalkerPartModel::createBodyLayer);

		event.registerLayerDefinition(FrostModelLayers.ROOT_DEER, RootDeerModel::createBodyLayer);

		event.registerLayerDefinition(FrostModelLayers.SILK_MOON, SilkMoonModel::createBodyLayer);
		event.registerLayerDefinition(FrostModelLayers.SILK_MOON_WORM, SilkMoonWormModel::createBodyLayer);


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
		RenderType cutout = RenderType.cutout();
		RenderType transluct = RenderType.translucent();
	}

	@SubscribeEvent
	public static void renderItemTint(RegisterColorHandlersEvent.Block event) {
		event.register((p_92621_, p_92622_, p_92623_, p_92624_) -> {
			return p_92622_ != null && p_92623_ != null ? BiomeColors.getAverageGrassColor(p_92622_, p_92623_) : GrassColor.get(0.5D, 1.0D);
		}, FrostBlocks.FROZEN_GRASS_BLOCK.get());

		event.register((p_92621_, p_92622_, p_92623_, p_92624_) -> {
			return p_92622_ != null && p_92623_ != null ? BiomeColors.getAverageGrassColor(p_92622_, p_92623_) : GrassColor.get(0.5D, 1.0D);
		}, FrostBlocks.COLD_GRASS.get());
		event.register((p_92621_, p_92622_, p_92623_, p_92624_) -> {
			return p_92622_ != null && p_92623_ != null ? BiomeColors.getAverageGrassColor(p_92622_, p_92623_) : GrassColor.get(0.5D, 1.0D);
		}, FrostBlocks.COLD_TALL_GRASS.get());

	}


	public static void setup(FMLCommonSetupEvent event) {
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
			TextureAtlasSprite textureatlassprite = minecraft.getBlockRenderer().getBlockModelShaper().getParticleIcon(FrostBlocks.FROST_PORTAL.get().defaultBlockState());
			guiGraphics.blitSprite(RenderType::guiTexturedOverlay, textureatlassprite, 0, 0,
					guiGraphics.guiWidth(),
					guiGraphics.guiHeight(),
					0);
			RenderSystem.disableBlend();
			RenderSystem.depthMask(true);
			RenderSystem.enableDepthTest();
		}
	}
	@SubscribeEvent
	public static void registerDimensionEffect(RegisterDimensionSpecialEffectsEvent event) {
        FrostRealmRenderInfo renderInfo = new FrostRealmRenderInfo(192.0F, true, DimensionSpecialEffects.SkyType.OVERWORLD, false, false);
		event.register(FrostRealm.prefix("renderer"), renderInfo);
	}

    @SubscribeEvent
    public static void screenEvent(RegisterMenuScreensEvent event) {
        event.register(FrostMenuTypes.AURORA_INFUSER.get(), AuroraInfuserScreen::new);
    }

	@SubscribeEvent
	public static void registerRenderBuffers(RegisterRenderBuffersEvent event) {
		event.registerRenderBuffer(FrostRenderType.AURORA_GLINT);
	}


}