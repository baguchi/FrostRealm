package baguchan.frostrealm.client;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.capability.FrostLivingCapability;
import baguchan.frostrealm.capability.FrostWeatherManager;
import baguchan.frostrealm.client.event.ClientFogEvent;
import baguchan.frostrealm.client.model.*;
import baguchan.frostrealm.client.overlay.FrostOverlay;
import baguchan.frostrealm.client.render.*;
import baguchan.frostrealm.client.screen.AuroraInfuserScreen;
import baguchan.frostrealm.item.GlimmerRockItem;
import baguchan.frostrealm.item.YetiFurArmorItem;
import baguchan.frostrealm.registry.*;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.level.GrassColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;
import net.neoforged.neoforge.common.NeoForge;


@EventBusSubscriber(modid = FrostRealm.MODID, value = Dist.CLIENT)
public class ClientRegistrar {
	public static final CubeDeformation OUTER_ARMOR_DEFORMATION = new CubeDeformation(1.0F);
	public static final CubeDeformation INNER_ARMOR_DEFORMATION = new CubeDeformation(0.5F);

	public static ContextKey<Boolean> HOLD_SPEAR_KEY = new ContextKey<>(ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "hold_spear_id"));


	@SubscribeEvent
	public static void registerLayer(RegisterRenderStateModifiersEvent event) {
		/*event.registerEntityModifier(LivingEntityRenderer.class, (abstractClientPlayer, playerRenderState) -> {
			boolean flag = abstractClientPlayer.getItemInHand(InteractionHand.MAIN_HAND).is(FrostTags.Items.SPEAR) && abstractClientPlayer.onGround();
			playerRenderState.setRenderData(HOLD_SPEAR_KEY, flag);
		});*/
	}

	@SubscribeEvent
	public static void registerClientExtend(RegisterClientExtensionsEvent event) {
		event.registerItem(new GlimmerRockItem.ItemRender(), FrostItems.GLIMMERROCK.get(), FrostItems.CRYONITE_CREAM.get());
		event.registerItem(YetiFurArmorItem.ArmorRender.INSTANCE, FrostItems.YETI_FUR_BOOTS.get(), FrostItems.YETI_FUR_LEGGINGS.get(), FrostItems.YETI_FUR_CHESTPLATE.get(), FrostItems.YETI_FUR_HELMET.get());
		event.registerItem(YetiFurArmorItem.ArmorRender.INSTANCE, FrostItems.GLACIER_BOAR_FUR_BOOTS.get(), FrostItems.GLACIER_BOAR_FUR_LEGGINGS.get(), FrostItems.GLACIER_BOAR_FUR_CHESTPLATE.get(), FrostItems.GLACIER_BOAR_FUR_HELMET.get());
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
        event.registerEntityRenderer(FrostEntities.GLACIER_BOAR.get(), GlacierBoarRenderer::new);
		event.registerEntityRenderer(FrostEntities.LESSER_WARRIOR.get(), LesserWarriorRenderer::new);
        event.registerEntityRenderer(FrostEntities.SEAL.get(), SealRenderer::new);
	   event.registerEntityRenderer(FrostEntities.VENOM_BALL.get(), VenomBallRenderer::new);
		event.registerEntityRenderer(FrostEntities.VENOCHEM.get(), VenochemRenderer::new);
		event.registerEntityRenderer(FrostEntities.GOKKUR.get(), GokkurRenderer::new);
		event.registerEntityRenderer(FrostEntities.UNDER_GOKKUR.get(), UnderGokkurRenderer::new);
		event.registerEntityRenderer(FrostEntities.ROOT_DEER.get(), RootDeerRenderer::new);
		event.registerEntityRenderer(FrostEntities.FLYING_BLOCK.get(), FlyingBlockRenderer::new);
		event.registerEntityRenderer(FrostEntities.SILK_MOON_WORM.get(), SilkMoonWormRenderer::new);
		event.registerEntityRenderer(FrostEntities.SILK_MOON.get(), SilkMoonRenderer::new);
		event.registerEntityRenderer(FrostEntities.SEEKER.get(), SeekerRenderer::new);
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
		event.registerLayerDefinition(FrostModelLayers.WOLFFLUE_BABY, () -> WolfflueModel.createBodyLayer(new CubeDeformation(0.0F)).apply(WolfModel.BABY_TRANSFORMER));
		event.registerLayerDefinition(FrostModelLayers.WOLFFLUE_ARMOR, () -> WolfflueModel.createBodyLayer(new CubeDeformation(0.2F)));
        event.registerLayerDefinition(FrostModelLayers.WOLFFLUE_BABY_ARMOR, () -> WolfflueModel.createBodyLayer(new CubeDeformation(0.2F)).apply(WolfModel.BABY_TRANSFORMER));
        event.registerLayerDefinition(FrostModelLayers.WOLFFLUE_SADDLE, () -> WolfflueModel.createBodyLayer(new CubeDeformation(0.21F)));
        event.registerLayerDefinition(FrostModelLayers.WOLFFLUE_BABY_SADDLE, () -> WolfflueModel.createBodyLayer(new CubeDeformation(0.21F)).apply(WolfModel.BABY_TRANSFORMER));
        event.registerLayerDefinition(FrostModelLayers.FERRET, FerretModel::createBodyLayer);


		event.registerLayerDefinition(FrostModelLayers.CRYSTAL_FOX, CrystalFoxModel::createBodyLayer);
        event.registerLayerDefinition(FrostModelLayers.SNOW_MOLE, SnowMoleModel::createBodyLayer);
        event.registerLayerDefinition(FrostModelLayers.ASTRA_BALL, AstraBallModel::createBodyLayer);
        event.registerLayerDefinition(FrostModelLayers.GLACIER_BOAR, GlacierBoarModel::createBodyLayer);
        event.registerLayerDefinition(FrostModelLayers.GLACIER_BOAR_BABY, () -> GlacierBoarModel.createBodyLayer().apply(GlacierBoarModel.BABY_TRANSFORMER));
        event.registerLayerDefinition(FrostModelLayers.LESSER_WARRIOR, LesserWarriorModel::createBodyLayer);
        event.registerLayerDefinition(FrostModelLayers.SEAL, SealModel::createBodyLayer);

		event.registerLayerDefinition(FrostModelLayers.GOKKUR, GokkurModel::createBodyLayer);

		event.registerLayerDefinition(FrostModelLayers.VENOCHEM, VenochemModel::createBodyLayer);
		event.registerLayerDefinition(FrostModelLayers.VENOM_BALL, VenomBallModel::createBodyLayer);

		event.registerLayerDefinition(FrostModelLayers.ROOT_DEER, RootDeerModel::createBodyLayer);

		event.registerLayerDefinition(FrostModelLayers.SILK_MOON, SilkMoonModel::createBodyLayer);
		event.registerLayerDefinition(FrostModelLayers.SILK_MOON_WORM, SilkMoonWormModel::createBodyLayer);
		event.registerLayerDefinition(FrostModelLayers.SEEKER, SeekerModel::createBodyLayer);

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
			int i = ARGB.white(timeInPortal);
			TextureAtlasSprite textureatlassprite = minecraft.getBlockRenderer().getBlockModelShaper().getParticleIcon(FrostBlocks.FROST_PORTAL.get().defaultBlockState());
			guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, textureatlassprite, 0, 0,
					guiGraphics.guiWidth(),
					guiGraphics.guiHeight(),
					i);
		}
	}
	@SubscribeEvent
	public static void registerDimensionEffect(RegisterDimensionSpecialEffectsEvent event) {
		FrostRealmRenderInfo renderInfo = new FrostRealmRenderInfo(DimensionSpecialEffects.SkyType.OVERWORLD, false, false);
		event.register(FrostRealm.prefix("renderer"), renderInfo);
	}

    @SubscribeEvent
    public static void screenEvent(RegisterMenuScreensEvent event) {
        event.register(FrostMenuTypes.AURORA_INFUSER.get(), AuroraInfuserScreen::new);
    }

	@SubscribeEvent
	public static void registerRenderBuffers(RegisterRenderBuffersEvent event) {
		event.registerRenderBuffer(FrostRenderType.AURORA_GLINT);
		event.registerRenderBuffer(FrostRenderType.AURORA_GLINT_TRANSLUCENT);
		event.registerRenderBuffer(FrostRenderType.AURORA_ARMOR_ENTITY_GLINT);
		event.registerRenderBuffer(FrostRenderType.AURORA_ENTITY_GLINT);
	}

	@SubscribeEvent
	public static void registerPipeline(RegisterRenderPipelinesEvent event) {
		event.registerPipeline(FrostRenderPipelines.DARK_OUTLINE_NO_CULL);
		event.registerPipeline(FrostRenderPipelines.DARK_OUTLINE_CULL);
		event.registerPipeline(FrostRenderPipelines.GLOW_OUTLINE_NO_CULL);
		event.registerPipeline(FrostRenderPipelines.GLOW_OUTLINE_CULL);
	}
    @SubscribeEvent
    public static void registerLevelRenderState(ExtractLevelRenderStateEvent event) {
        event.getRenderState().setRenderData(FrostRealmRenderInfo.NORMAL_WEATHER_LEVEL_KEY, FrostWeatherManager.getNormalWeatherLevel(event.getDeltaTracker().getGameTimeDeltaPartialTick(false)));
    }

}