package baguchan.frostrealm.client;

import baguchan.frostrealm.registry.FrostBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ClientRegistrar {

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