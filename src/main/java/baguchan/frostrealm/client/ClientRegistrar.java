package baguchan.frostrealm.client;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.event.ClientColdHUDEvent;
import baguchan.frostrealm.client.event.ClientFogEvent;
import baguchan.frostrealm.client.model.AurorayModel;
import baguchan.frostrealm.client.model.ClustWraithModel;
import baguchan.frostrealm.client.model.CrystalFoxModel;
import baguchan.frostrealm.client.model.FrostWolfModel;
import baguchan.frostrealm.client.model.FrostWraithModel;
import baguchan.frostrealm.client.model.GokkudilloModel;
import baguchan.frostrealm.client.model.MarmotModel;
import baguchan.frostrealm.client.model.SnowMoleModel;
import baguchan.frostrealm.client.model.SnowPileQuailModel;
import baguchan.frostrealm.client.model.WarpedIceModel;
import baguchan.frostrealm.client.model.WolfesterModel;
import baguchan.frostrealm.client.model.YetiFurArmorModel;
import baguchan.frostrealm.client.model.YetiModel;
import baguchan.frostrealm.client.render.AurorayRenderer;
import baguchan.frostrealm.client.render.ClustWraithRenderer;
import baguchan.frostrealm.client.render.CrystalFoxRenderer;
import baguchan.frostrealm.client.render.FrostBeasterRenderer;
import baguchan.frostrealm.client.render.FrostWolfRenderer;
import baguchan.frostrealm.client.render.FrostWraithRenderer;
import baguchan.frostrealm.client.render.GokkudilloRenderer;
import baguchan.frostrealm.client.render.MarmotRenderer;
import baguchan.frostrealm.client.render.PurifiedStrayRenderer;
import baguchan.frostrealm.client.render.SnowMoleRenderer;
import baguchan.frostrealm.client.render.SnowPileQuailRenderer;
import baguchan.frostrealm.client.render.WarpedCrystalRenderer;
import baguchan.frostrealm.client.render.YetiRenderer;
import baguchan.frostrealm.client.render.blockentity.FrostChestRenderer;
import baguchan.frostrealm.client.screen.CrystalSmithingScreen;
import baguchan.frostrealm.registry.FrostBlockEntitys;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostEntities;
import baguchan.frostrealm.registry.FrostMenuTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = FrostRealm.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistrar {
	public static final CubeDeformation OUTER_ARMOR_DEFORMATION = new CubeDeformation(1.0F);
	public static final CubeDeformation INNER_ARMOR_DEFORMATION = new CubeDeformation(0.5F);

	@SubscribeEvent
	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(FrostEntities.MARMOT.get(), MarmotRenderer::new);
		event.registerEntityRenderer(FrostEntities.SNOWPILE_QUAIL.get(), SnowPileQuailRenderer::new);
		event.registerEntityRenderer(FrostEntities.FROST_WOLF.get(), FrostWolfRenderer::new);
		event.registerEntityRenderer(FrostEntities.CRYSTAL_FOX.get(), CrystalFoxRenderer::new);
		event.registerEntityRenderer(FrostEntities.SNOW_MOLE.get(), SnowMoleRenderer::new);

		event.registerEntityRenderer(FrostEntities.YETI.get(), YetiRenderer::new);
		event.registerEntityRenderer(FrostEntities.FROST_WRAITH.get(), FrostWraithRenderer::new);
		event.registerEntityRenderer(FrostEntities.CLUST_WRAITH.get(), ClustWraithRenderer::new);
		event.registerEntityRenderer(FrostEntities.GOKKUDILLO.get(), GokkudilloRenderer::new);
		event.registerEntityRenderer(FrostEntities.FROST_BEASTER.get(), FrostBeasterRenderer::new);
		event.registerEntityRenderer(FrostEntities.AURORAY.get(), AurorayRenderer::new);

		event.registerEntityRenderer(FrostEntities.PURIFIED_STRAY.get(), PurifiedStrayRenderer::new);

		event.registerEntityRenderer(FrostEntities.WARPED_CRYSTAL.get(), WarpedCrystalRenderer::new);
	}

	@SubscribeEvent
	public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
		LayerDefinition layerdefinition1 = LayerDefinition.create(HumanoidModel.createMesh(OUTER_ARMOR_DEFORMATION, 0.0F), 64, 32);
		LayerDefinition layerdefinition3 = LayerDefinition.create(HumanoidModel.createMesh(INNER_ARMOR_DEFORMATION, 0.0F), 64, 32);

		event.registerLayerDefinition(FrostModelLayers.YETI, YetiModel::createBodyLayer);
		event.registerLayerDefinition(FrostModelLayers.FROST_WRAITH, FrostWraithModel::createBodyLayer);
		event.registerLayerDefinition(FrostModelLayers.CLUST_WRAITH, ClustWraithModel::createBodyLayer);
		event.registerLayerDefinition(FrostModelLayers.MARMOT, MarmotModel::createBodyLayer);
		event.registerLayerDefinition(FrostModelLayers.SNOWPILE_QUAIL, SnowPileQuailModel::createBodyLayer);
		event.registerLayerDefinition(FrostModelLayers.FROST_WOLF, FrostWolfModel::createBodyLayer);
		event.registerLayerDefinition(FrostModelLayers.WOLFESTER, WolfesterModel::createBodyLayer);
		event.registerLayerDefinition(FrostModelLayers.AURORAY, AurorayModel::createBodyLayer);
		event.registerLayerDefinition(FrostModelLayers.GOKKUDILLO, GokkudilloModel::createBodyLayer);
		event.registerLayerDefinition(FrostModelLayers.CRYSTAL_FOX, CrystalFoxModel::createBodyLayer);
		event.registerLayerDefinition(FrostModelLayers.SNOW_MOLE, SnowMoleModel::createBodyLayer);
		event.registerLayerDefinition(FrostModelLayers.WARPED_ICE, WarpedIceModel::createBodyLayer);

		event.registerLayerDefinition(FrostModelLayers.YETI_FUR_ARMOR_INNER, () -> YetiFurArmorModel.createBodyLayer(INNER_ARMOR_DEFORMATION));
		event.registerLayerDefinition(FrostModelLayers.YETI_FUR_ARMOR_OUTER, () -> YetiFurArmorModel.createBodyLayer(OUTER_ARMOR_DEFORMATION));

		event.registerLayerDefinition(FrostModelLayers.FROST_BEASTER_INNER_ARMOR, () -> layerdefinition3);
		event.registerLayerDefinition(FrostModelLayers.FROST_BEASTER_OUTER_ARMOR, () -> layerdefinition1);
	}

	public static void renderTileEntity() {
		BlockEntityRenderers.register(FrostBlockEntitys.FROST_CHEST.get(), FrostChestRenderer::new);
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
		MinecraftForge.EVENT_BUS.register(new ClientColdHUDEvent());
		MinecraftForge.EVENT_BUS.register(new ClientFogEvent());
		IEventBus busMod = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus busForge = MinecraftForge.EVENT_BUS;
		FrostShaders.init(busMod);
		FrostArmPoses.init();
		renderTileEntity();
		renderBlockColor();

		MenuScreens.register(FrostMenuTypes.CRYSTAL_SMITHING.get(), CrystalSmithingScreen::new);
	}

	@SubscribeEvent
	public static void registerDimensionEffect(RegisterDimensionSpecialEffectsEvent event) {
		FrostRealmRenderInfo renderInfo = new FrostRealmRenderInfo(192.0F, true, DimensionSpecialEffects.SkyType.NORMAL, false, false);
		event.register(FrostRealm.prefix("renderer"), renderInfo);
	}
}