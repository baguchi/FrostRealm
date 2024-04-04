package baguchan.frostrealm.client;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.event.ClientColdHUDEvent;
import baguchan.frostrealm.client.event.ClientFogEvent;
import baguchan.frostrealm.client.model.*;
import baguchan.frostrealm.client.render.*;
import baguchan.frostrealm.client.render.blockentity.FrostChestRenderer;
import baguchan.frostrealm.client.screen.AuroraInfuserScreen;
import baguchan.frostrealm.registry.FrostBlockEntitys;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostEntities;
import baguchan.frostrealm.registry.FrostMenuTypes;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.io.IOException;


@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = FrostRealm.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistrar {
	public static final CubeDeformation OUTER_ARMOR_DEFORMATION = new CubeDeformation(1.0F);
	public static final CubeDeformation INNER_ARMOR_DEFORMATION = new CubeDeformation(0.5F);

	@SubscribeEvent
	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(FrostEntities.MARMOT.get(), MarmotRenderer::new);
        event.registerEntityRenderer(FrostEntities.SNOWPILE_QUAIL.get(), SnowPileQuailRenderer::new);
		event.registerEntityRenderer(FrostEntities.CRYSTAL_FOX.get(), CrystalFoxRenderer::new);
        event.registerEntityRenderer(FrostEntities.SNOW_MOLE.get(), SnowMoleRenderer::new);

        event.registerEntityRenderer(FrostEntities.YETI.get(), YetiRenderer::new);
        event.registerEntityRenderer(FrostEntities.FROST_WRAITH.get(), FrostWraithRenderer::new);
		event.registerEntityRenderer(FrostEntities.ASTRA_BALL.get(), AstraBallRenderer::new);
        event.registerEntityRenderer(FrostEntities.FROST_BOAR.get(), FrostBoarRenderer::new);
        event.registerEntityRenderer(FrostEntities.WARPED_CRYSTAL_SHARD.get(), WarpedCrystalRenderer::new);
		event.registerEntityRenderer(FrostEntities.FROSTORM_DRAGON.get(), FrostormDragonRenderer::new);
        event.registerEntityRenderer(FrostEntities.SEEKER.get(), SeekerRenderer::new);
        event.registerEntityRenderer(FrostEntities.SEAL.get(), SealRenderer::new);
        event.registerEntityRenderer(FrostEntities.MIND_VINE.get(), MindVineRenderer::new);
        event.registerEntityRenderer(FrostEntities.BUSH_BUG.get(), BushBugRender::new);
		event.registerEntityRenderer(FrostEntities.WARPED_INSECT.get(), WarpedInsectRenderer::new);
		event.registerEntityRenderer(FrostEntities.WARPED_INSECT_PART.get(), WarpedInsectPartRenderer::new);
	}

	@SubscribeEvent
	public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
		LayerDefinition layerdefinition1 = LayerDefinition.create(HumanoidModel.createMesh(OUTER_ARMOR_DEFORMATION, 0.0F), 64, 32);
		LayerDefinition layerdefinition3 = LayerDefinition.create(HumanoidModel.createMesh(INNER_ARMOR_DEFORMATION, 0.0F), 64, 32);

		event.registerLayerDefinition(FrostModelLayers.YETI, YetiModel::createBodyLayer);
		event.registerLayerDefinition(FrostModelLayers.FROST_WRAITH, FrostWraithModel::createBodyLayer);

		event.registerLayerDefinition(FrostModelLayers.MARMOT, MarmotModel::createBodyLayer);
        event.registerLayerDefinition(FrostModelLayers.SNOWPILE_QUAIL, SnowPileQuailModel::createBodyLayer);

        event.registerLayerDefinition(FrostModelLayers.CRYSTAL_FOX, CrystalFoxModel::createBodyLayer);
        event.registerLayerDefinition(FrostModelLayers.SNOW_MOLE, SnowMoleModel::createBodyLayer);
        event.registerLayerDefinition(FrostModelLayers.ASTRA_BALL, AstraBallModel::createBodyLayer);
        event.registerLayerDefinition(FrostModelLayers.FROST_BOAR, FrostBoarModel::createBodyLayer);
		event.registerLayerDefinition(FrostModelLayers.FROSTROM_DRAGON, FrostormDragonModel::createBodyLayer);
        event.registerLayerDefinition(FrostModelLayers.SEEKER, SeekerModel::createBodyLayer);
        event.registerLayerDefinition(FrostModelLayers.SEAL, SealModel::createBodyLayer);
        event.registerLayerDefinition(FrostModelLayers.MIND_VINE, MindVineModel::createBodyLayer);
        event.registerLayerDefinition(FrostModelLayers.BUSH_BUG, BushBugModel::createBodyLayer);

		event.registerLayerDefinition(FrostModelLayers.WARPED_INSECT, WarpedInsectModel::createBodyLayer);
		event.registerLayerDefinition(FrostModelLayers.WARPED_INSECT_PART, WarpedInsectPartModel::createBodyLayer);


		event.registerLayerDefinition(FrostModelLayers.YETI_FUR_ARMOR_INNER, () -> YetiFurArmorModel.createBodyLayer(INNER_ARMOR_DEFORMATION));
        event.registerLayerDefinition(FrostModelLayers.YETI_FUR_ARMOR_OUTER, () -> YetiFurArmorModel.createBodyLayer(OUTER_ARMOR_DEFORMATION));

        event.registerLayerDefinition(FrostModelLayers.FROST_BEASTER_INNER_ARMOR, () -> layerdefinition3);
        event.registerLayerDefinition(FrostModelLayers.FROST_BEASTER_OUTER_ARMOR, () -> layerdefinition1);
	}

	private static ModelLayerLocation createLocation(String p_171301_, String p_171302_) {
		return new ModelLayerLocation(new ResourceLocation(FrostRealm.MODID, p_171301_), p_171302_);
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
		}, FrostBlocks.FRIGID_GRASS_BLOCK.get());

		Minecraft.getInstance().getItemColors().register((p_92687_, p_92688_) -> {
			BlockState blockstate = ((BlockItem) p_92687_.getItem()).getBlock().defaultBlockState();
			return Minecraft.getInstance().getBlockColors().getColor(blockstate, null, null, p_92688_);
		}, FrostBlocks.FRIGID_GRASS_BLOCK.get());

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
        NeoForge.EVENT_BUS.register(new ClientColdHUDEvent());
        NeoForge.EVENT_BUS.register(new ClientFogEvent());
		FrostArmPoses.init();
		renderTileEntity();
		renderBlockColor();
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

    @SubscribeEvent
    public static void registerShaders(final RegisterShadersEvent event) {
        try {
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation(FrostRealm.MODID, "rendertype_aurora"), DefaultVertexFormat.POSITION_TEX), FrostShaders::setRenderTypeAuroraShader);
			event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation(FrostRealm.MODID, "rendertype_crystal_entity"), DefaultVertexFormat.NEW_ENTITY), FrostShaders::setRenderTypeCrystalEntityShader);
		} catch (IOException exception) {
            exception.printStackTrace();
        }
    }


}