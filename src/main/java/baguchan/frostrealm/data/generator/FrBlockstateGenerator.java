package baguchan.frostrealm.data.generator;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.client.data.models.model.*;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.stream.Stream;

import static net.minecraft.client.data.models.BlockModelGenerators.createRotatedVariants;
import static net.minecraft.client.data.models.model.TexturedModel.createDefault;

public class FrBlockstateGenerator extends ModelProvider {
	public static final TexturedModel.Provider GLOW_CUBE = createDefault(FrostTextureMapping::glowCube, FrostModelTemplate.GLOW_CUBE);
	public static final TexturedModel.Provider TRANSLUCENT_CUBE = createDefault(TextureMapping::cube, FrostModelTemplate.TRANSLUCENT_CUBE);


	public FrBlockstateGenerator(PackOutput gen) {
		super(gen, FrostRealm.MODID);
	}


	@Override
	protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
		TextureMapping grassMapping = new TextureMapping().put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(FrostBlocks.FROZEN_DIRT.get())).copyForced(TextureSlot.BOTTOM, TextureSlot.PARTICLE)
				.put(TextureSlot.TOP, TextureMapping.getBlockTexture(FrostBlocks.FROZEN_GRASS_BLOCK.get(), "_top"))
				.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(FrostBlocks.FROZEN_GRASS_BLOCK.get(), "_snow"));
		Variant snowVariant = Variant.variant().with(VariantProperties.MODEL, ModelTemplates.CUBE_BOTTOM_TOP.createWithSuffix(FrostBlocks.FROZEN_GRASS_BLOCK.get(), "_snow", grassMapping, blockModels.modelOutput));
		blockModels.createGrassLikeBlock(FrostBlocks.FROZEN_GRASS_BLOCK.get(), ModelLocationUtils.getModelLocation(FrostBlocks.FROZEN_GRASS_BLOCK.get()), snowVariant);

		TextureMapping grassMapping2 = new TextureMapping().put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(FrostBlocks.FRIGID_STONE.get())).copyForced(TextureSlot.BOTTOM, TextureSlot.PARTICLE)
				.put(TextureSlot.TOP, TextureMapping.getBlockTexture(FrostBlocks.FRIGID_GRASS_BLOCK.get(), "_top"))
				.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(FrostBlocks.FRIGID_GRASS_BLOCK.get(), "_snow"));
		Variant snowVariant2 = Variant.variant().with(VariantProperties.MODEL, ModelTemplates.CUBE_BOTTOM_TOP.createWithSuffix(FrostBlocks.FRIGID_GRASS_BLOCK.get(), "_snow", grassMapping2, blockModels.modelOutput));
		blockModels.createGrassLikeBlock(FrostBlocks.FRIGID_GRASS_BLOCK.get(), ModelLocationUtils.getModelLocation(FrostBlocks.FRIGID_GRASS_BLOCK.get()), snowVariant2);


		createFrostPortalBlock(blockModels);
		createFrostFarmland(blockModels);
		createPointedIce(blockModels);
		blockModels.createTrivialCube(FrostBlocks.FROZEN_DIRT.get());

		blockModels.createTrivialCube(FrostBlocks.PERMA_SLATE.get());
		blockModels.family(FrostBlocks.PERMA_SLATE_BRICK.get())
				.slab(FrostBlocks.PERMA_SLATE_BRICK_SLAB.get())
				.stairs(FrostBlocks.PERMA_SLATE_BRICK_STAIRS.get())
				.wall(FrostBlocks.PERMA_SLATE_BRICK_WALL.get());
		blockModels.createTrivialCube(FrostBlocks.PERMA_SLATE_SMOOTH.get());
		createGlowCube(blockModels, FrostBlocks.PERMA_MAGMA.get());

		blockModels.family(FrostBlocks.FRIGID_STONE.get())
				.slab(FrostBlocks.FRIGID_STONE_SLAB.get())
				.stairs(FrostBlocks.FRIGID_STONE_STAIRS.get());
		blockModels.createTrivialCube(FrostBlocks.FRIGID_STONE_SMOOTH.get());
		blockModels.createTrivialCube(FrostBlocks.CHISELED_FRIGID_STONE_BRICK.get());

		blockModels.family(FrostBlocks.FRIGID_STONE_BRICK.get())
				.slab(FrostBlocks.FRIGID_STONE_BRICK_SLAB.get())
				.stairs(FrostBlocks.FRIGID_STONE_BRICK_STAIRS.get())
				.wall(FrostBlocks.FRIGID_STONE_BRICK_WALL.get());

		blockModels.family(FrostBlocks.FRIGID_STONE_MOSSY.get())
				.slab(FrostBlocks.FRIGID_STONE_MOSSY_SLAB.get())
				.stairs(FrostBlocks.FRIGID_STONE_MOSSY_STAIRS.get());

		blockModels.family(FrostBlocks.FRIGID_STONE_BRICK_MOSSY.get())
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
		blockModels.createCrossBlock(FrostBlocks.FROSTROOT_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);

		blockModels.family(FrostBlocks.FROSTROOT_PLANKS.get())
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
		blockModels.createCrossBlock(FrostBlocks.FROSTBITE_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);
		blockModels.family(FrostBlocks.FROSTBITE_PLANKS.get())
				.slab(FrostBlocks.FROSTBITE_PLANKS_SLAB.get())
				.stairs(FrostBlocks.FROSTBITE_PLANKS_STAIRS.get())
				.fence(FrostBlocks.FROSTBITE_FENCE.get())
				.fenceGate(FrostBlocks.FROSTBITE_FENCE_GATE.get())
				.door(FrostBlocks.FROSTBITE_DOOR.get())
				.button(FrostBlocks.FROSTBITE_BUTTON.get())
				.pressurePlate(FrostBlocks.FROSTBITE_PRESSURE_PLATE.get())
				.trapdoor(FrostBlocks.FROSTBITE_TRAPDOOR.get());

		blockModels.createCrossBlock(FrostBlocks.VIGOROSHROOM.get(), BlockModelGenerators.PlantType.NOT_TINTED);
		blockModels.createCrossBlock(FrostBlocks.ARCTIC_POPPY.get(), BlockModelGenerators.PlantType.NOT_TINTED);
		blockModels.createCrossBlock(FrostBlocks.ARCTIC_WILLOW.get(), BlockModelGenerators.PlantType.NOT_TINTED);

		blockModels.createCrossBlock(FrostBlocks.COLD_GRASS.get(), BlockModelGenerators.PlantType.TINTED);

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
		createEgg(blockModels, FrostBlocks.SILK_MOON_EGG.get());
		createEgg(blockModels, FrostBlocks.SNOWPILE_QUAIL_EGG.get());
		blockModels.createRotatedPillarWithHorizontalVariant(FrostBlocks.RYE_BLOCK.get(), TexturedModel.COLUMN, TexturedModel.COLUMN_HORIZONTAL);

	}

	public void createEgg(BlockModelGenerators generators, Block block) {
		generators.registerSimpleFlatItemModel(block.asItem());
		generators.blockStateOutput
				.accept(
						MultiVariantGenerator.multiVariant(block)
								.with(
										PropertyDispatch.properties(BlockStateProperties.EGGS, BlockStateProperties.HATCH)
												.generateList((p_387353_, p_388815_) -> Arrays.asList(createRotatedVariants(this.createEggModel(generators, block, p_387353_, p_388815_))))
								)
				);
	}

	public ResourceLocation createEggModel(BlockModelGenerators generators, Block block, Integer p_386499_, Integer p_387511_) {
		switch (p_387511_) {
			case 0:
				return createEggModel(generators, block, p_386499_, "", TextureMapping.cube(TextureMapping.getBlockTexture(block)));
			case 1:
				return createEggModel(generators, block,
						p_386499_, "slightly_cracked_", TextureMapping.cube(TextureMapping.getBlockTexture(block))
				);
			case 2:
				return createEggModel(generators, block,
						p_386499_, "very_cracked_", TextureMapping.cube(TextureMapping.getBlockTexture(block))
				);
			default:
				throw new UnsupportedOperationException();
		}
	}

	public static String getBlockName(Block p_387523_) {
		ResourceLocation resourcelocation = BuiltInRegistries.BLOCK.getKey(p_387523_);
		return resourcelocation.getPath();
	}

	public ResourceLocation createEggModel(BlockModelGenerators generators, Block block, int p_387392_, String p_387935_, TextureMapping p_388813_) {
		switch (p_387392_) {
			case 1:
				return ModelTemplates.TURTLE_EGG.create(FrostRealm.prefix(p_387935_ + getBlockName(block)), p_388813_, generators.modelOutput);
			case 2:
				return ModelTemplates.TWO_TURTLE_EGGS
						.create(FrostRealm.prefix("two_" + p_387935_ + getBlockName(block)), p_388813_, generators.modelOutput);
			case 3:
				return ModelTemplates.THREE_TURTLE_EGGS
						.create(FrostRealm.prefix("three_" + p_387935_ + getBlockName(block)), p_388813_, generators.modelOutput);
			case 4:
				return ModelTemplates.FOUR_TURTLE_EGGS
						.create(FrostRealm.prefix("four_" + p_387935_ + getBlockName(block)), p_388813_, generators.modelOutput);
			default:
				throw new UnsupportedOperationException();
		}
	}


	public void createFrostFarmland(BlockModelGenerators blockModels) {
		TextureMapping texturemapping = new TextureMapping().put(TextureSlot.DIRT, TextureMapping.getBlockTexture(FrostBlocks.FROZEN_DIRT.get())).put(TextureSlot.TOP, TextureMapping.getBlockTexture(FrostBlocks.FROZEN_FARMLAND.get()));
		TextureMapping texturemapping1 = new TextureMapping().put(TextureSlot.DIRT, TextureMapping.getBlockTexture(FrostBlocks.FROZEN_DIRT.get())).put(TextureSlot.TOP, TextureMapping.getBlockTexture(FrostBlocks.FROZEN_FARMLAND.get(), "_moist"));
		ResourceLocation resourcelocation = ModelTemplates.FARMLAND.create(FrostBlocks.FROZEN_FARMLAND.get(), texturemapping, blockModels.modelOutput);
		ResourceLocation resourcelocation1 = ModelTemplates.FARMLAND.create(TextureMapping.getBlockTexture(FrostBlocks.FROZEN_FARMLAND.get(), "_moist"), texturemapping1, blockModels.modelOutput);
		blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(FrostBlocks.FROZEN_FARMLAND.get()).with(BlockModelGenerators.createEmptyOrFullDispatch(BlockStateProperties.MOISTURE, 7, resourcelocation1, resourcelocation)));
	}


	public void createTranslucentCube(BlockModelGenerators blockModels, Block p_386512_) {
		this.createTranslucentBlock(blockModels, p_386512_, TRANSLUCENT_CUBE);
	}

	public void createTranslucentBlock(BlockModelGenerators blockModels, Block p_387678_, TexturedModel.Provider p_386545_) {
		blockModels.blockStateOutput.accept(createSimpleBlock(p_387678_, p_386545_.create(p_387678_, blockModels.modelOutput)));
	}

	public static MultiVariantGenerator createSimpleBlock(Block p_387997_, ResourceLocation p_388814_) {
		return MultiVariantGenerator.multiVariant(p_387997_, Variant.variant().with(VariantProperties.MODEL, p_388814_));
	}

	public void createGlowCube(BlockModelGenerators blockModels, Block p_386512_) {
		blockModels.createTrivialBlock(p_386512_, GLOW_CUBE);
	}

	public void createFrostPortalBlock(BlockModelGenerators blockModels) {
		blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(FrostBlocks.FROST_PORTAL.get()).with(PropertyDispatch.property(BlockStateProperties.HORIZONTAL_AXIS)
				.select(Direction.Axis.X, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(FrostBlocks.FROST_PORTAL.get(), "_ns")))
				.select(Direction.Axis.Z, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(FrostBlocks.FROST_PORTAL.get(), "_ew")))));
	}

	@Override
	public final Stream<? extends Holder<Item>> getKnownItems() {
		return super.getKnownItems().filter(item -> item instanceof BlockItem);
	}

	public void createPointedIce(BlockModelGenerators generator) {
		PropertyDispatch.C2<Direction, DripstoneThickness> c2 = PropertyDispatch.properties(
				BlockStateProperties.VERTICAL_DIRECTION, BlockStateProperties.DRIPSTONE_THICKNESS
		);

		for (DripstoneThickness dripstonethickness : DripstoneThickness.values()) {
			c2.select(Direction.UP, dripstonethickness, createPointedIceVariant(generator, Direction.UP, dripstonethickness));
		}

		for (DripstoneThickness dripstonethickness1 : DripstoneThickness.values()) {
			c2.select(Direction.DOWN, dripstonethickness1, createPointedIceVariant(generator, Direction.DOWN, dripstonethickness1));
		}

		generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(FrostBlocks.POINTED_ICE.get()).with(c2));
	}

	public Variant createPointedIceVariant(BlockModelGenerators generator, Direction p_387068_, DripstoneThickness p_388190_) {
		String s = "_" + p_387068_.getSerializedName() + "_" + p_388190_.getSerializedName();
		TextureMapping texturemapping = TextureMapping.cross(TextureMapping.getBlockTexture(FrostBlocks.POINTED_ICE.get(), s));
		return Variant.variant()
				.with(VariantProperties.MODEL, ModelTemplates.POINTED_DRIPSTONE.createWithSuffix(FrostBlocks.POINTED_ICE.get(), s, texturemapping, generator.modelOutput));
	}

	@Nonnull
	@Override
	public String getName() {
		return "FrostRealm blockstates and block models";
	}
}
