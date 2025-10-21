package baguchan.frostrealm.data.generator.models;

import baguchan.frostrealm.data.builder.FrostBlockFamilies;
import baguchan.frostrealm.data.generator.FrostTextureMappings;
import baguchan.frostrealm.data.provider.FrBlockstateModelProvider;
import baguchan.frostrealm.registry.FrostBlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class FrostBlockModels extends FrBlockstateModelProvider {

    public FrostBlockModels(Consumer<BlockModelDefinitionGenerator> blockStateOutput, ItemModelOutput itemModelOutput, BiConsumer<ResourceLocation, ModelInstance> modelOutput) {
        super(blockStateOutput, itemModelOutput, modelOutput);
    }

    @Override
    public void run() {
        FrostBlockFamilies.getAllFamilies().filter(BlockFamily::shouldGenerateModel).forEach((family) -> family(family.getBaseBlock()).generateFor(family));


        createGrassLikeFrostBlock(FrostBlocks.FROZEN_GRASS_BLOCK.get(), FrostBlocks.FROZEN_DIRT.get());

        createGrassLikeFrostStoneBlock(FrostBlocks.FRIGID_GRASS_BLOCK.get(), FrostBlocks.FRIGID_STONE.get());


        createFrostPortalBlock();
        createFrostFarmland();
        createPointedIce(this);
        this.createTrivialCube(FrostBlocks.FROZEN_DIRT.get());

        this.createTrivialCube(FrostBlocks.PERMA_SLATE.get());
        family(FrostBlocks.PERMA_SLATE_BRICK.get())
                .slab(FrostBlocks.PERMA_SLATE_BRICK_SLAB.get())
                .stairs(FrostBlocks.PERMA_SLATE_BRICK_STAIRS.get())
                .wall(FrostBlocks.PERMA_SLATE_BRICK_WALL.get());
        this.createTrivialCube(FrostBlocks.PERMA_SLATE_SMOOTH.get());
        createGlowCube(FrostBlocks.PERMA_MAGMA.get());

        family(FrostBlocks.FRIGID_STONE.get())
                .slab(FrostBlocks.FRIGID_STONE_SLAB.get())
                .stairs(FrostBlocks.FRIGID_STONE_STAIRS.get());
        this.createTrivialCube(FrostBlocks.FRIGID_STONE_SMOOTH.get());
        this.createTrivialCube(FrostBlocks.CHISELED_FRIGID_STONE_BRICK.get());

        this.createTrivialCube(FrostBlocks.MAGMA_CORE.get());

        family(FrostBlocks.FRIGID_STONE_BRICK.get())
                .slab(FrostBlocks.FRIGID_STONE_BRICK_SLAB.get())
                .stairs(FrostBlocks.FRIGID_STONE_BRICK_STAIRS.get())
                .wall(FrostBlocks.FRIGID_STONE_BRICK_WALL.get());

        family(FrostBlocks.FRIGID_STONE_MOSSY.get())
                .slab(FrostBlocks.FRIGID_STONE_MOSSY_SLAB.get())
                .stairs(FrostBlocks.FRIGID_STONE_MOSSY_STAIRS.get());

        family(FrostBlocks.FRIGID_STONE_BRICK_MOSSY.get())
                .slab(FrostBlocks.FRIGID_STONE_BRICK_MOSSY_SLAB.get())
                .stairs(FrostBlocks.FRIGID_STONE_BRICK_MOSSY_STAIRS.get());

        this.createTrivialCube(FrostBlocks.SHERBET_SAND.get());

        this.familyWithExistingFullBlock(FrostBlocks.SHERBET_SANDSTONE.get())
                .fullBlock(FrostBlocks.SHERBET_SANDSTONE.get(), ModelTemplates.CUBE_BOTTOM_TOP)
                .slab(FrostBlocks.SHERBET_SANDSTONE_SLAB.get())
                .stairs(FrostBlocks.SHERBET_SANDSTONE_STAIRS.get());


        this.createTrivialCube(FrostBlocks.GLACINIUM_ORE.get());
        this.createTrivialCube(FrostBlocks.GLACINIUM_BLOCK.get());
        this.createTrivialCube(FrostBlocks.RAW_GLACINIUM_BLOCK.get());

        this.woodProvider(FrostBlocks.FROSTROOT_LOG.get())
                .log(FrostBlocks.FROSTROOT_LOG.get());
        this.woodProvider(FrostBlocks.STRIPPED_FROSTROOT_LOG.get())
                .log(FrostBlocks.STRIPPED_FROSTROOT_LOG.get());
        createTrivialBlock(FrostBlocks.FROSTROOT_LEAVES.get(), LEAVES_PROVIDER);
        createCrossBlockWithDefaultItem(FrostBlocks.FROSTROOT_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);

        this.woodProvider(FrostBlocks.FROSTBITE_LOG.get())
                .log(FrostBlocks.FROSTBITE_LOG.get());
        this.woodProvider(FrostBlocks.STRIPPED_FROSTBITE_LOG.get())
                .log(FrostBlocks.STRIPPED_FROSTBITE_LOG.get());
        this.woodProvider(FrostBlocks.ROCK_WOOD.get())
                .wood(FrostBlocks.ROCK_WOOD.get());
        createTrivialBlock(FrostBlocks.FROSTBITE_LEAVES.get(), LEAVES_PROVIDER);
        createCrossBlockWithDefaultItem(FrostBlocks.FROSTBITE_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);

        this.woodProvider(FrostBlocks.DRIP_LOG.get())
                .log(FrostBlocks.DRIP_LOG.get());
        createTrivialBlock(FrostBlocks.DRIP_LEAVES.get(), LEAVES_PROVIDER);
        createCrossBlockWithDefaultItem(FrostBlocks.DRIP_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        createHangingMoss(FrostBlocks.DRIP_HANGING_LEAVES.get());

        createCrossBlockWithDefaultItem(FrostBlocks.VIGOROSHROOM.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        createCrossBlockWithDefaultItem(FrostBlocks.ARCTIC_POPPY.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        createCrossBlockWithDefaultItem(FrostBlocks.ARCTIC_WILLOW.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        createCrossBlock(FrostBlocks.COLD_GRASS.get(), BlockModelGenerators.PlantType.TINTED);
        this.createItemWithGrassTint(FrostBlocks.COLD_GRASS.get());
        //this.createTintedDoublePlant(FrostBlocks.COLD_TALL_GRASS.get());
        createTintedDoublePlant(FrostBlocks.COLD_TALL_GRASS.get());

        this.createCropBlock(FrostBlocks.BEARBERRY_BUSH.get(), BlockStateProperties.AGE_3, 0, 1, 2, 3);
        this.createCropBlock(FrostBlocks.SUGARBEET.get(), BlockStateProperties.AGE_3, 0, 1, 2, 3);
        this.createCropBlock(FrostBlocks.RYE.get(), BlockStateProperties.AGE_7, 0, 1, 2, 3, 4, 5, 6, 7);

        createGlowCube(FrostBlocks.FROST_CRYSTAL_ORE.get());
        createGlowCube(FrostBlocks.GLIMMERROCK_ORE.get());
        this.createTrivialCube(FrostBlocks.FROST_CRYSTAL_BLOCK.get());
        this.createNormalTorch(FrostBlocks.FROST_TORCH.get(), FrostBlocks.WALL_FROST_TORCH.get());

        this.createTrivialCube(FrostBlocks.ASTRIUM_ORE.get());

        createGlowCube(FrostBlocks.FROST_CRYSTAL_SLATE_ORE.get());
        createGlowCube(FrostBlocks.GLIMMERROCK_SLATE_ORE.get());
        this.createTrivialCube(FrostBlocks.ASTRIUM_SLATE_ORE.get());
        this.createTrivialCube(FrostBlocks.ASTRIUM_BLOCK.get());
        this.createTrivialCube(FrostBlocks.RAW_ASTRIUM_BLOCK.get());
        this.createTrivialCube(FrostBlocks.GLIMMERROCK_BLOCK.get());

        createGlowCube(FrostBlocks.STARDUST_CRYSTAL_ORE.get());
        createTranslucentCube(FrostBlocks.STARDUST_CRYSTAL_CLUSTER.get());
        createTranslucentCube(FrostBlocks.WARPED_CRYSTAL_BLOCK.get());

        this.createTrivialCube(FrostBlocks.SILK_MOON_COCOON.get());

        createCampfires(FrostBlocks.FROST_CAMPFIRE.get());

        this.createNonTemplateModelBlock(FrostBlocks.HOT_SPRING.get());
        createAuroraInfuser(FrostBlocks.AURORA_INFUSER.get());
        createWolfflue(FrostBlocks.WOLFFLUE_BLOCK.get());
        createBlockEgg(FrostBlocks.SILK_MOON_EGG.get());
        createEgg(FrostBlocks.SNOWPILE_QUAIL_EGG.get());
        this.createRotatedPillarWithHorizontalVariant(FrostBlocks.RYE_BLOCK.get(), TexturedModel.COLUMN, TexturedModel.COLUMN_HORIZONTAL);
        this.createFrostFire();
    }

    public void createAuroraInfuser(Block p_388054_) {
        TextureMapping texturemapping = FrostTextureMappings.auroraInfuser(p_388054_);
        this.blockStateOutput.accept(createSimpleBlock(p_388054_, ModelTemplates.CUBE.create(p_388054_, texturemapping, this.modelOutput)));
    }

    public void createWolfflue(Block p_388054_) {
        TextureMapping texturemapping = FrostTextureMappings.wolfflue(p_388054_);

        MultiVariant multiVariant = plainVariant(ModelTemplates.CUBE.create(p_388054_, texturemapping, this.modelOutput));
        this.blockStateOutput
                .accept(MultiVariantGenerator.dispatch(p_388054_, multiVariant).with(ROTATION_HORIZONTAL_FACING));
    }
}
