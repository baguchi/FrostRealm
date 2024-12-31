package baguchan.frostrealm.data.generator;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.data.builder.FrostBlockFamilies;
import baguchan.frostrealm.data.provider.FrBlockstateModelProvider;
import baguchan.frostrealm.registry.FrostBlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import javax.annotation.Nonnull;

public class FrBlockstateGenerator extends FrBlockstateModelProvider {

	public FrBlockstateGenerator(PackOutput gen) {
		super(gen, FrostRealm.MODID);
	}

	@Override
	protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
		FrostBlockFamilies.getAllFamilies().filter(BlockFamily::shouldGenerateModel).forEach((family) -> family(blockModels, family.getBaseBlock()).generateFor(family));


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
		createTrivialBlock(blockModels, FrostBlocks.FROSTROOT_LEAVES.get(), LEAVES_PROVIDER);
		createCrossBlockWithDefaultItem(blockModels, FrostBlocks.FROSTROOT_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);

		blockModels.woodProvider(FrostBlocks.FROSTBITE_LOG.get())
				.log(FrostBlocks.FROSTBITE_LOG.get())
				.log(FrostBlocks.STRIPPED_FROSTBITE_LOG.get());
		createTrivialBlock(blockModels, FrostBlocks.FROSTBITE_LEAVES.get(), LEAVES_PROVIDER);
		createCrossBlockWithDefaultItem(blockModels, FrostBlocks.FROSTBITE_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);

		createCrossBlockWithDefaultItem(blockModels, FrostBlocks.VIGOROSHROOM.get(), BlockModelGenerators.PlantType.NOT_TINTED);
		createCrossBlockWithDefaultItem(blockModels, FrostBlocks.ARCTIC_POPPY.get(), BlockModelGenerators.PlantType.NOT_TINTED);
		createCrossBlockWithDefaultItem(blockModels, FrostBlocks.ARCTIC_WILLOW.get(), BlockModelGenerators.PlantType.NOT_TINTED);
		createCrossBlock(blockModels, FrostBlocks.COLD_GRASS.get(), BlockModelGenerators.PlantType.TINTED);
		blockModels.createItemWithGrassTint(FrostBlocks.COLD_GRASS.get());
		//blockModels.createTintedDoublePlant(FrostBlocks.COLD_TALL_GRASS.get());
		createTintedDoublePlant(blockModels, FrostBlocks.COLD_TALL_GRASS.get());

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

		createCampfires(blockModels, FrostBlocks.FROST_CAMPFIRE.get());

		blockModels.createNonTemplateModelBlock(FrostBlocks.HOT_SPRING.get());
		createAuroraInfuser(blockModels, FrostBlocks.AURORA_INFUSER.get());
		createBlockEgg(blockModels, FrostBlocks.SILK_MOON_EGG.get());
		createEgg(blockModels, FrostBlocks.SNOWPILE_QUAIL_EGG.get());
		blockModels.createRotatedPillarWithHorizontalVariant(FrostBlocks.RYE_BLOCK.get(), TexturedModel.COLUMN, TexturedModel.COLUMN_HORIZONTAL);

	}

	public void createAuroraInfuser(BlockModelGenerators blockModelGenerators, Block p_388054_) {
		TextureMapping texturemapping = FrostTextureMapping.auroraInfuser(p_388054_);
		blockModelGenerators.blockStateOutput.accept(createSimpleBlock(p_388054_, ModelTemplates.CUBE.create(p_388054_, texturemapping, blockModelGenerators.modelOutput)));
	}

	@Nonnull
	@Override
	public String getName() {
		return "FrostRealm blockstates and block models";
	}
}
