package baguchan.frostrealm.data;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;

public class BlockstateGenerator extends BlockStateProvider {
	public BlockstateGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, FrostRealm.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		this.simpleBlock(FrostBlocks.FROST_PORTAL.get());
		this.simpleBlock(FrostBlocks.FROZEN_DIRT.get());

		this.simpleBlock(FrostBlocks.FRIGID_STONE.get());
		this.slab(FrostBlocks.FRIGID_STONE_SLAB.get(), FrostBlocks.FRIGID_STONE.get());
		this.stairs(FrostBlocks.FRIGID_STONE_STAIRS.get(), FrostBlocks.FRIGID_STONE.get());
		this.simpleBlock(FrostBlocks.FRIGID_STONE_BRICK.get());
		this.simpleBlock(FrostBlocks.FRIGID_STONE_SMOOTH_BRICK.get());
		this.slab(FrostBlocks.FRIGID_STONE_BRICK_SLAB.get(), FrostBlocks.FRIGID_STONE_BRICK.get());
		this.stairs(FrostBlocks.FRIGID_STONE_BRICK_STAIRS.get(), FrostBlocks.FRIGID_STONE_BRICK.get());

		this.logBlock(FrostBlocks.FROSTROOT_LOG.get());
		this.simpleBlock(FrostBlocks.FROSTROOT_LEAVES.get());
		this.crossBlock(FrostBlocks.FROSTROOT_SAPLING.get());
		this.simpleBlock(FrostBlocks.FROSTROOT_PLANKS.get());
		this.slab(FrostBlocks.FROSTROOT_PLANKS_SLAB.get(), FrostBlocks.FROSTROOT_PLANKS.get());
		this.stairs(FrostBlocks.FROSTROOT_PLANKS_STAIRS.get(), FrostBlocks.FROSTROOT_PLANKS.get());
		this.fenceBlock(FrostBlocks.FROSTROOT_FENCE.get(), texture(name(FrostBlocks.FROSTROOT_PLANKS.get())));
		this.fenceGateBlock(FrostBlocks.FROSTROOT_FENCE_GATE.get(), texture(name(FrostBlocks.FROSTROOT_PLANKS.get())));
		this.crossBlock(FrostBlocks.VIGOROSHROOM.get());

		this.crossTintBlock(FrostBlocks.COLD_GRASS.get());

		this.simpleBlock(FrostBlocks.FROST_CRYSTAL_ORE.get());
		this.simpleBlock(FrostBlocks.GLIMMERROCK_ORE.get());
		this.simpleBlock(FrostBlocks.STARDUST_CRYSTAL_ORE.get());
		this.simpleBlock(FrostBlocks.STARDUST_CRYSTAL_CLUSTER.get());
	}

	public void torchBlock(Block block, Block wall) {
		ModelFile torch = models().torch(name(block), texture(name(block)));
		ModelFile torchwall = models().torchWall(name(wall), texture(name(block)));
		simpleBlock(block, torch);
		getVariantBuilder(wall).forAllStates(state ->
				ConfiguredModel.builder()
						.modelFile(torchwall)
						.rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 90) % 360)
						.build());
	}

	public void stairs(StairBlock block, Block fullBlock) {
		stairsBlock(block, texture(name(fullBlock)));
	}

	public void slab(SlabBlock block, Block fullBlock) {
		slabBlock(block, texture(name(fullBlock)), texture(name(fullBlock)));
	}

	public void crossBlock(Block block) {

		crossBlock(block, models().cross(name(block), texture(name(block))));
	}

	public void crossTintBlock(Block block) {
		ModelFile tint = models().singleTexture(name(block), mcLoc("block/tinted_cross"), "cross", texture(name(block)));


		crossBlock(block, tint);
	}

	private void crossBlock(Block block, ModelFile model) {
		getVariantBuilder(block).forAllStates(state ->
				ConfiguredModel.builder()
						.modelFile(model)
						.build());
	}

	protected ResourceLocation texture(String name) {
		return modLoc("block/" + name);
	}

	protected String name(Block block) {
		return block.getRegistryName().getPath();
	}

	@Nonnull
	@Override
	public String getName() {
		return "FrostRealm blockstates and block models";
	}
}
