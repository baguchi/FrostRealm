package baguchan.frostrealm.client;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.event.ClientColdHUDEvent;
import baguchan.frostrealm.client.model.*;
import baguchan.frostrealm.client.render.*;
import baguchan.frostrealm.client.render.blockentity.FrostChestRenderer;
import baguchan.frostrealm.registry.FrostBlockEntitys;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = FrostRealm.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistrar {
	public static final CubeDeformation OUTER_ARMOR_DEFORMATION = new CubeDeformation(1.0F);
	public static final CubeDeformation INNER_ARMOR_DEFORMATION = new CubeDeformation(0.5F);

	@SubscribeEvent
	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(FrostEntities.CRYSTAL_TORTOISE, CrystalTortoiseRenderer::new);
		event.registerEntityRenderer(FrostEntities.MARMOT, MarmotRenderer::new);
		event.registerEntityRenderer(FrostEntities.SNOWPILE_QUAIL, SnowPileQuailRenderer::new);
		event.registerEntityRenderer(FrostEntities.FROST_WOLF, FrostWolfRenderer::new);

		event.registerEntityRenderer(FrostEntities.YETI, YetiRenderer::new);
		event.registerEntityRenderer(FrostEntities.FROST_WRAITH, FrostWraithRenderer::new);
		event.registerEntityRenderer(FrostEntities.GOKKUR, GokkurRenderer::new);
		event.registerEntityRenderer(FrostEntities.GOKKUDILLO, GokkudilloRenderer::new);
		event.registerEntityRenderer(FrostEntities.FROST_BEASTER, FrostBeasterRenderer::new);
	}

	@SubscribeEvent
	public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(FrostModelLayers.CRYSTAL_TORTOISE, CrystalTortoiseModel::createBodyLayer);
		event.registerLayerDefinition(FrostModelLayers.YETI, YetiModel::createBodyLayer);
		event.registerLayerDefinition(FrostModelLayers.FROST_WRAITH, FrostWraithModel::createBodyLayer);
		event.registerLayerDefinition(FrostModelLayers.MARMOT, MarmotModel::createBodyLayer);
		event.registerLayerDefinition(FrostModelLayers.SNOWPILE_QUAIL, SnowPileQuailModel::createBodyLayer);
		event.registerLayerDefinition(FrostModelLayers.FROST_WOLF, FrostWolfModel::createBodyLayer);
		event.registerLayerDefinition(FrostModelLayers.WOLFESTER, WolfesterModel::createBodyLayer);
		event.registerLayerDefinition(FrostModelLayers.GOKKUR, GokkurModel::createBodyLayer);
		event.registerLayerDefinition(FrostModelLayers.GOKKUDILLO, GokkudilloModel::createBodyLayer);

		event.registerLayerDefinition(FrostModelLayers.YETI_FUR_ARMOR_INNER, () -> YetiFurArmorModel.createBodyLayer(INNER_ARMOR_DEFORMATION));
		event.registerLayerDefinition(FrostModelLayers.YETI_FUR_ARMOR_OUTER, () -> YetiFurArmorModel.createBodyLayer(OUTER_ARMOR_DEFORMATION));
	}

	public static void renderTileEntity() {
		BlockEntityRenderers.register(FrostBlockEntitys.FROST_CHEST, FrostChestRenderer::new);
	}

	public static void renderBlockColor() {
		Minecraft.getInstance().getBlockColors().register((p_92621_, p_92622_, p_92623_, p_92624_) -> {
			return p_92622_ != null && p_92623_ != null ? BiomeColors.getAverageGrassColor(p_92622_, p_92623_) : -1;
		}, FrostBlocks.FROZEN_GRASS_BLOCK);

		Minecraft.getInstance().getBlockColors().register((p_92621_, p_92622_, p_92623_, p_92624_) -> {
			return p_92622_ != null && p_92623_ != null ? BiomeColors.getAverageGrassColor(p_92622_, p_92623_) : -1;
		}, FrostBlocks.COLD_GRASS);

		Minecraft.getInstance().getItemColors().register((p_92687_, p_92688_) -> {
			BlockState blockstate = ((BlockItem) p_92687_.getItem()).getBlock().defaultBlockState();
			return Minecraft.getInstance().getBlockColors().getColor(blockstate, (BlockAndTintGetter) null, (BlockPos) null, p_92688_);
		}, FrostBlocks.COLD_GRASS);

		Minecraft.getInstance().getItemColors().register((p_92687_, p_92688_) -> {
			BlockState blockstate = ((BlockItem) p_92687_.getItem()).getBlock().defaultBlockState();
			return Minecraft.getInstance().getBlockColors().getColor(blockstate, (BlockAndTintGetter) null, (BlockPos) null, p_92688_);
		}, FrostBlocks.FROZEN_GRASS_BLOCK);
	}

	public static void renderBlockLayer() {
		setRenderLayer(FrostBlocks.FROST_PORTAL, RenderType.translucent());

		setRenderLayer(FrostBlocks.FROZEN_GRASS_BLOCK, RenderType.cutoutMipped());

		setRenderLayer(FrostBlocks.POINTED_ICE, RenderType.cutout());

		setRenderLayer(FrostBlocks.FROSTROOT_LEAVES, RenderType.cutoutMipped());
		setRenderLayer(FrostBlocks.FROSTROOT_SAPLING, RenderType.cutout());

		setRenderLayer(FrostBlocks.FROSTROOT_DOOR, RenderType.cutout());

		setRenderLayer(FrostBlocks.VIGOROSHROOM, RenderType.cutout());
		setRenderLayer(FrostBlocks.ARCTIC_POPPY, RenderType.cutout());
		setRenderLayer(FrostBlocks.ARCTIC_WILLOW, RenderType.cutout());
		setRenderLayer(FrostBlocks.SUGARBEET, RenderType.cutout());

		setRenderLayer(FrostBlocks.COLD_GRASS, RenderType.cutout());
		setRenderLayer(FrostBlocks.COLD_TALL_GRASS, RenderType.cutout());

		setRenderLayer(FrostBlocks.BEARBERRY_BUSH, RenderType.cutout());

		setRenderLayer(FrostBlocks.STARDUST_CRYSTAL_CLUSTER, RenderType.translucent());

		setRenderLayer(FrostBlocks.FROST_TORCH, RenderType.cutout());
		setRenderLayer(FrostBlocks.WALL_FROST_TORCH, RenderType.cutout());
	}

	private static void setRenderLayer(Block block, RenderType type) {
		ItemBlockRenderTypes.setRenderLayer(block, type::equals);
	}

	public static void setup(FMLCommonSetupEvent event) {
		MinecraftForge.EVENT_BUS.register(new ClientColdHUDEvent());
		renderTileEntity();
		renderBlockColor();
		renderBlockLayer();
		FrostRealmRenderInfo renderInfo = new FrostRealmRenderInfo(192.0F, true, DimensionSpecialEffects.SkyType.NORMAL, false, false);
		DimensionSpecialEffects.EFFECTS.put(FrostRealm.prefix("renderer"), renderInfo);
	}

	@SubscribeEvent
	public static void onTextureStitch(TextureStitchEvent.Pre event) {
		if (event.getAtlas().location().equals(Sheets.CHEST_SHEET)) {
			event.addSprite(FrostChestRenderer.CHEST_LOCATION.texture());
			event.addSprite(FrostChestRenderer.CHEST_LOCATION_LEFT.texture());
			event.addSprite(FrostChestRenderer.CHEST_LOCATION_RIGHT.texture());
		}
	}
}