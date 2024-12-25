package baguchan.frostrealm.data.generator;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

import static net.minecraft.client.data.models.model.TexturedModel.createDefault;

public class FrBlockstateGenerator extends ModelProvider {
	public static final TexturedModel.Provider GLOW_CUBE = createDefault(FrostTextureMapping::glowCube, FrostModelTemplate.GLOW_CUBE);
	public static final TexturedModel.Provider TRANSLUCENT_CUBE = createDefault(TextureMapping::cube, FrostModelTemplate.TRANSLUCENT_CUBE);


	public FrBlockstateGenerator(PackOutput gen) {
		super(gen, FrostRealm.MODID);
	}


	@Override
	protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
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

		blockModels.woodProvider(FrostBlocks.FROSTROOT_LOG.get());
		blockModels.woodProvider(FrostBlocks.STRIPPED_FROSTROOT_LOG.get());
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

		blockModels.woodProvider(FrostBlocks.FROSTBITE_LOG.get());
		blockModels.woodProvider(FrostBlocks.STRIPPED_FROSTBITE_LOG.get());
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

		blockModels.createCropBlock(FrostBlocks.BEARBERRY_BUSH.get(), BlockStateProperties.AGE_3, 0, 1, 2, 3);
		blockModels.createCropBlock(FrostBlocks.SUGARBEET.get(), BlockStateProperties.AGE_3, 0, 1, 2, 3);
		blockModels.createCropBlock(FrostBlocks.RYE.get(), BlockStateProperties.AGE_7, 0, 1, 2, 3, 4, 5, 6, 7);

		createGlowCube(blockModels, FrostBlocks.FROST_CRYSTAL_ORE.get());
		createGlowCube(blockModels, FrostBlocks.GLIMMERROCK_ORE.get());
		blockModels.createTrivialCube(FrostBlocks.FROST_CRYSTAL_BLOCK.get());

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
		blockModels.registerSimpleFlatItemModel(FrostBlocks.SNOWPILE_QUAIL_EGG.asItem());
		blockModels.registerSimpleFlatItemModel(FrostBlocks.FROST_CAMPFIRE.asItem());
		blockModels.registerSimpleFlatItemModel(FrostBlocks.POINTED_ICE.asItem());
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

	@Override
	public final Stream<? extends Holder<Item>> getKnownItems() {
		return super.getKnownItems().filter(item -> item instanceof BlockItem);
	}

	@Override
	protected Stream<? extends Holder<Block>> getKnownBlocks() {
		return Stream.of();
	}

	@Nonnull
	@Override
	public String getName() {
		return "FrostRealm blockstates and block models";
	}
}
