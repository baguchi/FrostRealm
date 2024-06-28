package baguchan.frostrealm.data;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class BlockstateGenerator extends BlockStateProvider {
	public BlockstateGenerator(PackOutput gen, ExistingFileHelper exFileHelper) {
		super(gen, FrostRealm.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		this.simpleBlock(FrostBlocks.FROST_PORTAL.get());
		this.simpleBlock(FrostBlocks.FROZEN_DIRT.get());

		this.simpleBlock(FrostBlocks.PERMA_SLATE.get());
		this.simpleBlock(FrostBlocks.PERMA_SLATE_BRICK.get());
		this.simpleBlock(FrostBlocks.PERMA_SLATE_SMOOTH.get());
		this.slab(FrostBlocks.PERMA_SLATE_BRICK_SLAB.get(), FrostBlocks.PERMA_SLATE_BRICK.get());
		this.stairs(FrostBlocks.PERMA_SLATE_BRICK_STAIRS.get(), FrostBlocks.PERMA_SLATE_BRICK.get());


		this.simpleBlock(FrostBlocks.FRIGID_STONE.get());
		this.slab(FrostBlocks.FRIGID_STONE_SLAB.get(), FrostBlocks.FRIGID_STONE.get());
		this.stairs(FrostBlocks.FRIGID_STONE_STAIRS.get(), FrostBlocks.FRIGID_STONE.get());
		this.simpleBlock(FrostBlocks.FRIGID_STONE_BRICK.get());
		this.simpleBlock(FrostBlocks.FRIGID_STONE_SMOOTH.get());
        this.simpleBlock(FrostBlocks.CHISELED_FRIGID_STONE_BRICK.get());
        this.slab(FrostBlocks.FRIGID_STONE_BRICK_SLAB.get(), FrostBlocks.FRIGID_STONE_BRICK.get());
		this.stairs(FrostBlocks.FRIGID_STONE_BRICK_STAIRS.get(), FrostBlocks.FRIGID_STONE_BRICK.get());

		this.simpleBlock(FrostBlocks.FRIGID_STONE_MOSSY.get());
		this.slab(FrostBlocks.FRIGID_STONE_MOSSY_SLAB.get(), FrostBlocks.FRIGID_STONE_MOSSY.get());
		this.stairs(FrostBlocks.FRIGID_STONE_MOSSY_STAIRS.get(), FrostBlocks.FRIGID_STONE_MOSSY.get());

		this.simpleBlock(FrostBlocks.FRIGID_STONE_BRICK_MOSSY.get());
		this.slab(FrostBlocks.FRIGID_STONE_BRICK_MOSSY_SLAB.get(), FrostBlocks.FRIGID_STONE_BRICK_MOSSY.get());
		this.stairs(FrostBlocks.FRIGID_STONE_BRICK_MOSSY_STAIRS.get(), FrostBlocks.FRIGID_STONE_BRICK_MOSSY.get());

		this.simpleBlock(FrostBlocks.SHERBET_SAND.get());

		ModelFile sandstone = models().withExistingParent(name(FrostBlocks.SHERBET_SANDSTONE.get()), ResourceLocation.withDefaultNamespace("block/orientable"))
				.texture("top", suffix(blockTexture(FrostBlocks.SHERBET_SANDSTONE.get()), "_top"))
				.texture("side", blockTexture(FrostBlocks.SHERBET_SANDSTONE.get()))
				.texture("front", blockTexture(FrostBlocks.SHERBET_SANDSTONE.get()))
				.texture("bottom", suffix(blockTexture(FrostBlocks.SHERBET_SANDSTONE.get()), "_bottom"));


		this.simpleBlock(FrostBlocks.SHERBET_SANDSTONE.get(), sandstone);
		this.slabBlock(FrostBlocks.SHERBET_SANDSTONE_SLAB.get(), texture(name(FrostBlocks.SHERBET_SANDSTONE.get())), texture(name(FrostBlocks.SHERBET_SANDSTONE.get())), suffix(blockTexture(FrostBlocks.SHERBET_SANDSTONE.get()), "_bottom"), suffix(blockTexture(FrostBlocks.SHERBET_SANDSTONE.get()), "_top"));
		this.stairsBlock(FrostBlocks.SHERBET_SANDSTONE_STAIRS.get(), blockTexture(FrostBlocks.SHERBET_SANDSTONE.get()), suffix(blockTexture(FrostBlocks.SHERBET_SANDSTONE.get()), "_bottom"), suffix(blockTexture(FrostBlocks.SHERBET_SANDSTONE.get()), "_top"));


		this.logBlock(FrostBlocks.FROSTROOT_LOG.get());
		this.logBlock(FrostBlocks.STRIPPED_FROSTROOT_LOG.get());
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
		this.ageSevenCrossBlock(FrostBlocks.RYE.get());

		this.glowBlock(FrostBlocks.FROST_CRYSTAL_ORE.get());
		this.glowBlock(FrostBlocks.GLIMMERROCK_ORE.get());
		this.simpleBlock(FrostBlocks.ASTRIUM_ORE.get());
		this.glowBlockWithOrigin(FrostBlocks.FROST_CRYSTAL_SLATE_ORE.get(), FrostBlocks.FROST_CRYSTAL_ORE.get());
		this.glowBlockWithOrigin(FrostBlocks.GLIMMERROCK_SLATE_ORE.get(), FrostBlocks.GLIMMERROCK_ORE.get());
		this.simpleBlock(FrostBlocks.ASTRIUM_SLATE_ORE.get());
		this.simpleBlock(FrostBlocks.ASTRIUM_BLOCK.get());
		this.simpleBlock(FrostBlocks.STARDUST_CRYSTAL_ORE.get());
		this.translucentBlock(FrostBlocks.STARDUST_CRYSTAL_CLUSTER.get());
		this.translucentBlock(FrostBlocks.WARPED_CRYSTAL_BLOCK.get());
		this.doorBlock(FrostBlocks.FROSTROOT_DOOR.get(), texture("frostroot_door_bottom"), texture("frostroot_door_top"));
	}

	public void glowBlockWithOrigin(Block block, Block origin) {
		ModelFile glow_column = models().withExistingParent(name(block), FrostRealm.prefix("block/glow_cube"))
				.texture("cube", blockTexture(block))
				.texture("glow", suffix(blockTexture(origin), "_glow")).renderType("minecraft:cutout");
		simpleBlock(block,
				glow_column);
	}

	public void glowBlock(Block block) {
		ModelFile glow_column = models().withExistingParent(name(block), FrostRealm.prefix("block/glow_cube"))
				.texture("cube", blockTexture(block))
				.texture("glow", suffix(blockTexture(block), "_glow")).renderType("minecraft:cutout");
		simpleBlock(block,
				glow_column);
	}

	private ResourceLocation suffix(ResourceLocation rl, String suffix) {
		return ResourceLocation.fromNamespaceAndPath(rl.getNamespace(), rl.getPath() + suffix);
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

	public void ageSevenCrossBlock(Block block) {
		getVariantBuilder(block).forAllStates(state -> {
			int age = state.getValue(BlockStateProperties.AGE_7);
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
        return BuiltInRegistries.BLOCK.getKey(block);
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
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
	}

	@Nonnull
	@Override
	public String getName() {
		return "FrostRealm blockstates and block models";
	}
}
