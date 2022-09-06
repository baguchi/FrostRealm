package baguchan.frostrealm.data;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

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
		this.simpleBlock(FrostBlocks.FRIGID_STONE_SMOOTH.get());
		this.slab(FrostBlocks.FRIGID_STONE_BRICK_SLAB.get(), FrostBlocks.FRIGID_STONE_BRICK.get());
		this.stairs(FrostBlocks.FRIGID_STONE_BRICK_STAIRS.get(), FrostBlocks.FRIGID_STONE_BRICK.get());

		this.simpleBlock(FrostBlocks.FRIGID_STONE_MOSSY.get());
		this.slab(FrostBlocks.FRIGID_STONE_MOSSY_SLAB.get(), FrostBlocks.FRIGID_STONE_MOSSY.get());
		this.stairs(FrostBlocks.FRIGID_STONE_MOSSY_STAIRS.get(), FrostBlocks.FRIGID_STONE_MOSSY.get());

		this.simpleBlock(FrostBlocks.FRIGID_STONE_BRICK_MOSSY.get());
		this.slab(FrostBlocks.FRIGID_STONE_BRICK_MOSSY_SLAB.get(), FrostBlocks.FRIGID_STONE_BRICK_MOSSY.get());
		this.stairs(FrostBlocks.FRIGID_STONE_BRICK_MOSSY_STAIRS.get(), FrostBlocks.FRIGID_STONE_BRICK_MOSSY.get());

		this.logBlock(FrostBlocks.FROSTROOT_LOG.get());
		this.simpleBlock(FrostBlocks.FROSTROOT_LEAVES.get());
		this.crossBlock(FrostBlocks.FROSTROOT_SAPLING.get());
		this.simpleBlock(FrostBlocks.FROSTROOT_PLANKS.get());
		this.slab(FrostBlocks.FROSTROOT_PLANKS_SLAB.get(), FrostBlocks.FROSTROOT_PLANKS.get());
		this.stairs(FrostBlocks.FROSTROOT_PLANKS_STAIRS.get(), FrostBlocks.FROSTROOT_PLANKS.get());
		this.fenceBlock(FrostBlocks.FROSTROOT_FENCE.get(), texture(name(FrostBlocks.FROSTROOT_PLANKS.get())));
		this.fenceGateBlock(FrostBlocks.FROSTROOT_FENCE_GATE.get(), texture(name(FrostBlocks.FROSTROOT_PLANKS.get())));

		this.crossBlock(FrostBlocks.VIGOROSHROOM.get());
		this.crossBlock(FrostBlocks.ARCTIC_POPPY.get());
		this.crossBlock(FrostBlocks.ARCTIC_WILLOW.get());

		this.crossTintBlock(FrostBlocks.COLD_GRASS.get());

		this.ageThreeCrossBlock(FrostBlocks.BEARBERRY_BUSH.get());
		this.ageThreeCrossBlock(FrostBlocks.SUGARBEET.get());

		this.simpleBlock(FrostBlocks.FROST_CRYSTAL_ORE.get());
		this.simpleBlock(FrostBlocks.GLIMMERROCK_ORE.get());
		this.simpleBlock(FrostBlocks.ASTRIUM_ORE.get());
		this.simpleBlock(FrostBlocks.STARDUST_CRYSTAL_ORE.get());
		this.translucentBlock(FrostBlocks.STARDUST_CRYSTAL_CLUSTER.get());
		this.translucentBlock(FrostBlocks.CORRUPTED_CRYSTAL_CLUSTER.get());
		this.translucentBlock(FrostBlocks.WARPED_CRYSTAL_BLOCK.get());
		this.doorBlock(FrostBlocks.FROSTROOT_DOOR.get(), texture("frostroot_door_bottom"), texture("frostroot_door_top"));
	}

	public void translucentBlock(Block block) {
		simpleBlock(block, translucentCubeAll(block));
	}

	private ModelFile translucentCubeAll(Block block) {
		return models().cubeAll(name(block), blockTexture(block)).renderType("minecraft:translucent");
	}

	public void leavesTintBlock(Block block) {
		ModelFile tint = models().singleTexture(name(block), mcLoc("minecraft:block/leaves"), "all", texture(name(block))).renderType("minecraft:cutout_mipped");
		crossBlock(block, tint);
	}

	private void leavesBlock(Block block, ModelFile model) {
		getVariantBuilder(block).forAllStates(state ->
				ConfiguredModel.builder()
						.modelFile(model)
						.build());
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

		crossBlock(block, models().cross(name(block), texture(name(block))).renderType("minecraft:cutout"));
	}

	public void ageThreeCrossBlock(Block block) {
		getVariantBuilder(block).forAllStates(state -> {
			int age = state.getValue(BlockStateProperties.AGE_3);
			ModelFile cross_1 = models().singleTexture(name(block) + "_" + age, mcLoc("block/cross"), "cross", texture(name(block) + "_" + age)).renderType("minecraft:cutout");
			return ConfiguredModel.builder()
					.modelFile(cross_1)
					.build();
		});
	}

	public void crossTintBlock(Block block) {
		ModelFile tint = models().singleTexture(name(block), mcLoc("block/tinted_cross"), "cross", texture(name(block))).renderType("minecraft:cutout");


		crossBlock(block, tint);
	}

	private void crossBlock(Block block, ModelFile model) {
		getVariantBuilder(block).forAllStates(state ->
				ConfiguredModel.builder()
						.modelFile(model)
						.build());
	}


	private ResourceLocation key(Block block) {
		return ForgeRegistries.BLOCKS.getKey(block);
	}

	public void doorBlock(DoorBlock block, ResourceLocation bottom, ResourceLocation top) {
		doorBlockInternal(block, key(block).toString(), bottom, top);
	}

	public void door(Supplier<? extends DoorBlock> block, String name) {
		doorBlock(block.get(), texture(name + "_door_bottom"), texture(name + "_door_top"));
	}

	private ModelBuilder<?> door(String name, String model, ResourceLocation bottom, ResourceLocation top) {
		return models().withExistingParent(name, "block/" + model)
				.texture("bottom", bottom)
				.texture("top", top)
				.renderType("minecraft:cutout");
	}

	private void doorBlockInternal(DoorBlock block, String baseName, ResourceLocation bottom, ResourceLocation top) {
		ModelFile bottomLeft = door(baseName + "_bottom_left", "door_bottom_left", bottom, top);
		ModelFile bottomLeftOpen = door(baseName + "_bottom_left_open", "door_bottom_left_open", bottom, top);
		ModelFile bottomRight = door(baseName + "_bottom_right", "door_bottom_right", bottom, top);
		ModelFile bottomRightOpen = door(baseName + "_bottom_right_open", "door_bottom_right_open", bottom, top);
		ModelFile topLeft = door(baseName + "_top_left", "door_top_left", bottom, top);
		ModelFile topLeftOpen = door(baseName + "_top_left_open", "door_top_left_open", bottom, top);
		ModelFile topRight = door(baseName + "_top_right", "door_top_right", bottom, top);
		ModelFile topRightOpen = door(baseName + "_top_right_open", "door_top_right_open", bottom, top);
		doorBlock(block, bottomLeft, bottomLeftOpen, bottomRight, bottomRightOpen, topLeft, topLeftOpen, topRight, topRightOpen);
	}

	public void doorBlock(DoorBlock block, ModelFile bottomLeft, ModelFile bottomLeftOpen, ModelFile bottomRight, ModelFile bottomRightOpen, ModelFile topLeft, ModelFile topLeftOpen, ModelFile topRight, ModelFile topRightOpen) {
		getVariantBuilder(block).forAllStatesExcept(state -> {
			int yRot = ((int) state.getValue(DoorBlock.FACING).toYRot()) + 90;
			boolean right = state.getValue(DoorBlock.HINGE) == DoorHingeSide.RIGHT;
			boolean open = state.getValue(DoorBlock.OPEN);
			if (open) {
				yRot += 90;
			}
			if (right && open) {
				yRot += 180;
			}
			yRot %= 360;
			return ConfiguredModel.builder().modelFile(state.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER ? (right ? (open ? bottomRightOpen : bottomRight) : (open ? bottomLeftOpen : bottomLeft)) : (right ? (open ? topRightOpen : topRight) : (open ? topLeftOpen : topLeft)))
					.rotationY(yRot)
					.build();
		}, DoorBlock.POWERED);
	}

	public void lockableDoorBlock(DoorBlock block, ModelFile bottomLeft, ModelFile bottomLeftOpen, ModelFile bottomRight, ModelFile bottomRightOpen, ModelFile topLeft, ModelFile topLeftOpen, ModelFile topRight, ModelFile topRightOpen) {
		getVariantBuilder(block).forAllStatesExcept(state -> {
			int yRot = ((int) state.getValue(DoorBlock.FACING).toYRot()) + 90;
			boolean right = state.getValue(DoorBlock.HINGE) == DoorHingeSide.RIGHT;
			boolean open = state.getValue(DoorBlock.OPEN);
			if (open) {
				yRot += 90;
			}
			if (right && open) {
				yRot += 180;
			}
			yRot %= 360;
			return ConfiguredModel.builder().modelFile(state.getValue(DoorBlock.HALF) == DoubleBlockHalf.LOWER ? (right ? (open ? bottomRightOpen : bottomRight) : (open ? bottomLeftOpen : bottomLeft)) : (right ? (open ? topRightOpen : topRight) : (open ? topLeftOpen : topLeft)))
					.rotationY(yRot)
					.build();
		}, DoorBlock.POWERED);
	}

	protected ResourceLocation texture(String name) {
		return modLoc("block/" + name);
	}

	protected String name(Block block) {
		return ForgeRegistries.BLOCKS.getKey(block).getPath();
	}

	@Nonnull
	@Override
	public String getName() {
		return "FrostRealm blockstates and block models";
	}
}
