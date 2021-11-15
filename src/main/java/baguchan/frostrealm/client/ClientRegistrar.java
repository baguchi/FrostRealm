package baguchan.frostrealm.client;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.model.YetiModel;
import baguchan.frostrealm.client.render.YetiRenderer;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = FrostRealm.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistrar {

	@SubscribeEvent
	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(FrostEntities.YETI, YetiRenderer::new);
	}

	@SubscribeEvent
	public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ModModelLayers.YETI, YetiModel::createBodyLayer);
	}

	public static void renderTileEntity() {
	}

	public static void renderBlockColor() {
		Minecraft.getInstance().getBlockColors().register((p_92621_, p_92622_, p_92623_, p_92624_) -> {
			return p_92622_ != null && p_92623_ != null ? BiomeColors.getAverageGrassColor(p_92622_, p_92623_) : -1;
		}, FrostBlocks.FROZEN_GRASS_BLOCK.get());
	}

	public static void renderBlockLayer() {
		setRenderLayer(FrostBlocks.FROST_PORTAL.get(), RenderType.translucent());

		setRenderLayer(FrostBlocks.FROZEN_GRASS_BLOCK.get(), RenderType.cutoutMipped());

		setRenderLayer(FrostBlocks.POINTED_ICE.get(), RenderType.cutout());

		setRenderLayer(FrostBlocks.FROSTROOT_LEAVES.get(), RenderType.cutoutMipped());
		setRenderLayer(FrostBlocks.FROSTROOT_SAPLING.get(), RenderType.cutout());

		setRenderLayer(FrostBlocks.VIGOROSHROOM.get(), RenderType.cutout());

		setRenderLayer(FrostBlocks.COLD_GRASS.get(), RenderType.cutout());
		setRenderLayer(FrostBlocks.COLD_TALL_GRASS.get(), RenderType.cutout());

		setRenderLayer(FrostBlocks.STARDUST_CRYSTAL_CLUSTER.get(), RenderType.translucent());

		setRenderLayer(FrostBlocks.FROST_TORCH.get(), RenderType.cutout());
		setRenderLayer(FrostBlocks.WALL_FROST_TORCH.get(), RenderType.cutout());
	}

	private static void setRenderLayer(Block block, RenderType type) {
		ItemBlockRenderTypes.setRenderLayer(block, type::equals);
	}

	public static void setup(FMLCommonSetupEvent event) {
		renderTileEntity();
		renderBlockColor();
		renderBlockLayer();
	}
}