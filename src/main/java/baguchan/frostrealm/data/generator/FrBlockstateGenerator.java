package baguchan.frostrealm.data.generator;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.data.provider.FrBlockstateModelProvider;
import baguchan.frostrealm.registry.FrostBlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import javax.annotation.Nonnull;

public class FrBlockstateGenerator extends FrBlockstateModelProvider {

	public FrBlockstateGenerator(PackOutput gen) {
		super(gen, FrostRealm.MODID);
	}

	@Override
	protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
		createGrassLikeFrostBlock(blockModels, FrostBlocks.FROZEN_GRASS_BLOCK.get(), FrostBlocks.FROZEN_DIRT.get());

		createGrassLikeFrostStoneBlock(blockModels, FrostBlocks.FRIGID_GRASS_BLOCK.get(), FrostBlocks.FRIGID_STONE.get());


		createFrostPortalBlock(blockModels);
		createFrostFarmland(blockModels);
		createPointedIce(blockModels);
		blockModels.createTrivialCube(FrostBlocks.FROZEN_DIRT.get());

		blockModels.createTrivialCube(FrostBlocks.PERMA_SLATE.get());
		family(blockModels, FrostBlocks.PERMA_SLATE_BRICK.get())
				.slab(FrostBlocks.PERMA_SLATE_BRICK_SLAB.get())
				.stairs(FrostBlocks.PERMA_SLATE_BRICK_STAIRS.get())
				.wall(FrostBlocks.PERMA_SLATE_BRICK_WALL.get());
		blockModels.createTrivialCube(FrostBlocks.PERMA_SLATE_SMOOTH.get());
		createGlowCube(blockModels, FrostBlocks.PERMA_MAGMA.get());

		family(blockModels, FrostBlocks.FRIGID_STONE.get())
				.slab(FrostBlocks.FRIGID_STONE_SLAB.get())
				.stairs(FrostBlocks.FRIGID_STONE_STAIRS.get());
		blockModels.createTrivialCube(FrostBlocks.FRIGID_STONE_SMOOTH.get());
		blockModels.createTrivialCube(FrostBlocks.CHISELED_FRIGID_STONE_BRICK.get());

		family(blockModels, FrostBlocks.FRIGID_STONE_BRICK.get())
				.slab(FrostBlocks.FRIGID_STONE_BRICK_SLAB.get())
				.stairs(FrostBlocks.FRIGID_STONE_BRICK_STAIRS.get())
				.wall(FrostBlocks.FRIGID_STONE_BRICK_WALL.get());

		family(blockModels, FrostBlocks.FRIGID_STONE_MOSSY.get())
				.slab(FrostBlocks.FRIGID_STONE_MOSSY_SLAB.get())
				.stairs(FrostBlocks.FRIGID_STONE_MOSSY_STAIRS.get());

		family(blockModels, FrostBlocks.FRIGID_STONE_BRICK_MOSSY.get())
				.slab(FrostBlocks.FRIGID_STONE_BRICK_MOSSY_SLAB.get())
				.stairs(FrostBlocks.FRIGID_STONE_BRICK_MOSSY_STAIRS.get());

		blockModels.createTrivialCube(FrostBlocks.SHERBET_SAND.get());

		blockModels.familyWithExistingFullBlock(FrostBlocks.SHERBET_SANDSTONE.get())
				.fullBlock(FrostBlocks.SHERBET_SANDSTONE.get(), ModelTemplates.CUBE_BOTTOM_TOP)
				.slab(FrostBlocks.SHERBET_SANDSTONE_SLAB.get())
				.stairs(FrostBlocks.SHERBET_SANDSTONE_STAIRS.get());


		blockModels.createTrivialCube(FrostBlocks.GLACINIUM_ORE.get());
		blockModels.createTrivialCube(FrostBlocks.GLACINIUM_BLOCK.get());
		blockModels.createTrivialCube(FrostBlocks.RAW_GLACINIUM_BLOCK.get());

		blockModels.woodProvider(FrostBlocks.FROSTROOT_LOG.get())
				.log(FrostBlocks.FROSTROOT_LOG.get())
				.log(FrostBlocks.STRIPPED_FROSTROOT_LOG.get());
		blockModels.createTrivialCube(FrostBlocks.FROSTROOT_LEAVES.get());
		blockModels.createCrossBlockWithDefaultItem(FrostBlocks.FROSTROOT_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);

		blockModels.registerSimpleItemModel(FrostBlocks.FROSTROOT_PLANKS.get(), ModelLocationUtils.getModelLocation(FrostBlocks.FROSTROOT_PLANKS.get()));

		family(blockModels, FrostBlocks.FROSTROOT_PLANKS.get())
				.slab(FrostBlocks.FROSTROOT_PLANKS_SLAB.get())
				.stairs(FrostBlocks.FROSTROOT_PLANKS_STAIRS.get())
				.fence(FrostBlocks.FROSTROOT_FENCE.get())
				.fenceGate(FrostBlocks.FROSTROOT_FENCE_GATE.get())
				.door(FrostBlocks.FROSTROOT_DOOR.get())
				.button(FrostBlocks.FROSTROOT_BUTTON.get())
				.pressurePlate(FrostBlocks.FROSTROOT_PRESSURE_PLATE.get())
				.trapdoor(FrostBlocks.FROSTROOT_TRAPDOOR.get());

		blockModels.woodProvider(FrostBlocks.FROSTBITE_LOG.get())
				.log(FrostBlocks.FROSTBITE_LOG.get())
				.log(FrostBlocks.STRIPPED_FROSTBITE_LOG.get());
		blockModels.createTrivialCube(FrostBlocks.FROSTBITE_LEAVES.get());
		blockModels.createCrossBlockWithDefaultItem(FrostBlocks.FROSTBITE_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);

		blockModels.registerSimpleItemModel(FrostBlocks.FROSTBITE_PLANKS.get(), ModelLocationUtils.getModelLocation(FrostBlocks.FROSTBITE_PLANKS.get()));
		family(blockModels, FrostBlocks.FROSTBITE_PLANKS.get())
				.slab(FrostBlocks.FROSTBITE_PLANKS_SLAB.get())
				.stairs(FrostBlocks.FROSTBITE_PLANKS_STAIRS.get())
				.fence(FrostBlocks.FROSTBITE_FENCE.get())
				.fenceGate(FrostBlocks.FROSTBITE_FENCE_GATE.get())
				.door(FrostBlocks.FROSTBITE_DOOR.get())
				.button(FrostBlocks.FROSTBITE_BUTTON.get())
				.pressurePlate(FrostBlocks.FROSTBITE_PRESSURE_PLATE.get())
				.trapdoor(FrostBlocks.FROSTBITE_TRAPDOOR.get());

		blockModels.createCrossBlockWithDefaultItem(FrostBlocks.VIGOROSHROOM.get(), BlockModelGenerators.PlantType.NOT_TINTED);
		blockModels.createCrossBlockWithDefaultItem(FrostBlocks.ARCTIC_POPPY.get(), BlockModelGenerators.PlantType.NOT_TINTED);
		blockModels.createCrossBlockWithDefaultItem(FrostBlocks.ARCTIC_WILLOW.get(), BlockModelGenerators.PlantType.NOT_TINTED);

		blockModels.createCrossBlockWithDefaultItem(FrostBlocks.COLD_GRASS.get(), BlockModelGenerators.PlantType.TINTED);

		blockModels.createTintedDoublePlant(FrostBlocks.COLD_TALL_GRASS.get());

		blockModels.createCropBlock(FrostBlocks.BEARBERRY_BUSH.get(), BlockStateProperties.AGE_3, 0, 1, 2, 3);
		blockModels.createCropBlock(FrostBlocks.SUGARBEET.get(), BlockStateProperties.AGE_3, 0, 1, 2, 3);
		blockModels.createCropBlock(FrostBlocks.RYE.get(), BlockStateProperties.AGE_7, 0, 1, 2, 3, 4, 5, 6, 7);

		createGlowCube(blockModels, FrostBlocks.FROST_CRYSTAL_ORE.get());
		createGlowCube(blockModels, FrostBlocks.GLIMMERROCK_ORE.get());
		blockModels.createTrivialCube(FrostBlocks.FROST_CRYSTAL_BLOCK.get());
		blockModels.createNormalTorch(FrostBlocks.FROST_TORCH.get(), FrostBlocks.WALL_FROST_TORCH.get());

		blockModels.createTrivialCube(FrostBlocks.ASTRIUM_ORE.get());

		createGlowCube(blockModels, FrostBlocks.FROST_CRYSTAL_SLATE_ORE.get());
		createGlowCube(blockModels, FrostBlocks.GLIMMERROCK_SLATE_ORE.get());
		blockModels.createTrivialCube(FrostBlocks.ASTRIUM_SLATE_ORE.get());
		blockModels.createTrivialCube(FrostBlocks.ASTRIUM_BLOCK.get());
		blockModels.createTrivialCube(FrostBlocks.RAW_ASTRIUM_BLOCK.get());
		blockModels.createTrivialCube(FrostBlocks.GLIMMERROCK_BLOCK.get());

		createGlowCube(blockModels, FrostBlocks.STARDUST_CRYSTAL_ORE.get());
		createTranslucentCube(blockModels, FrostBlocks.STARDUST_CRYSTAL_CLUSTER.get());
		createTranslucentCube(blockModels, FrostBlocks.WARPED_CRYSTAL_BLOCK.get());

		blockModels.createTrivialCube(FrostBlocks.SILK_MOON_COCOON.get());

		blockModels.createCampfires(FrostBlocks.FROST_CAMPFIRE.get());

		blockModels.createNonTemplateModelBlock(FrostBlocks.HOT_SPRING.get());
		blockModels.createCraftingTableLike(FrostBlocks.AURORA_INFUSER.get(), FrostBlocks.AURORA_INFUSER.get(), TextureMapping::craftingTable);
		createBlockEgg(blockModels, FrostBlocks.SILK_MOON_EGG.get());
		createEgg(blockModels, FrostBlocks.SNOWPILE_QUAIL_EGG.get());
		blockModels.createRotatedPillarWithHorizontalVariant(FrostBlocks.RYE_BLOCK.get(), TexturedModel.COLUMN, TexturedModel.COLUMN_HORIZONTAL);

	}

	@Nonnull
	@Override
	public String getName() {
		return "FrostRealm blockstates and block models";
	}
}
