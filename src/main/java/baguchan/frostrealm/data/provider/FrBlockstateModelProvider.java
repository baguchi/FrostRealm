package baguchan.frostrealm.data.provider;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.data.generator.FrostModelTemplate;
import baguchan.frostrealm.data.generator.FrostTextureMapping;
import baguchan.frostrealm.registry.FrostBlocks;
import net.minecraft.client.color.item.GrassColorSource;
import net.minecraft.client.data.models.BlockModelGenerators;
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

import java.util.Arrays;
import java.util.stream.Stream;

import static net.minecraft.client.data.models.BlockModelGenerators.*;
import static net.minecraft.client.data.models.model.TextureMapping.getBlockTexture;
import static net.minecraft.client.data.models.model.TexturedModel.createDefault;

public abstract class FrBlockstateModelProvider extends ModelProvider {
    public static final ModelTemplate GLOW_CUBE = FrostModelTemplate.GLOW_CUBE.extend().renderType("cutout").build();
    public static final ModelTemplate TRANSLUCENT_CUBE = ModelTemplates.CUBE_ALL.extend().renderType("translucent").build();
    public static final ModelTemplate CUTOUT_CUBE = ModelTemplates.CUBE_ALL.extend().renderType("cutout").build();
    public static final ModelTemplate CROP = ModelTemplates.CROP.extend().renderType("cutout").build();
    public static final TexturedModel.Provider LEAVES_PROVIDER = createDefault(TextureMapping::cube, ModelTemplates.LEAVES.extend().renderType("cutout").build());
    public static final TexturedModel.Provider COLUMN_CUTOUT = createDefault(TextureMapping::column, ModelTemplates.CUBE_COLUMN.extend().renderType("cutout").build());

    public FrBlockstateModelProvider(PackOutput p_388260_, String modId) {
        super(p_388260_, modId);
    }

    public void createItemWithDoubleGrassTint(BlockModelGenerators blockModels, Block p_388714_) {
        blockModels.registerSimpleTintedItemModel(p_388714_, ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(p_388714_), TextureMapping.layer0(getBlockTexture(p_388714_).withSuffix("_top")), blockModels.modelOutput), new GrassColorSource());
    }


    public void createTintedDoublePlant(BlockModelGenerators blockModels, Block p_388276_) {
        ResourceLocation resourcelocation = blockModels.createFlatItemModelWithBlockTexture(p_388276_.asItem(), p_388276_, "_top");
        blockModels.registerSimpleTintedItemModel(p_388276_, resourcelocation, new GrassColorSource());
        createDoublePlant(blockModels, p_388276_, BlockModelGenerators.PlantType.TINTED);
    }

    public void createDoublePlant(BlockModelGenerators blockModels, Block p_388543_, BlockModelGenerators.PlantType p_388551_) {
        ResourceLocation resourcelocation = blockModels.createSuffixedVariant(p_388543_, "_top", p_388551_.getCross().extend().renderType("cutout").build(), TextureMapping::cross);
        ResourceLocation resourcelocation1 = blockModels.createSuffixedVariant(p_388543_, "_bottom", p_388551_.getCross().extend().renderType("cutout").build(), TextureMapping::cross);
        blockModels.createDoubleBlock(p_388543_, resourcelocation, resourcelocation1);
    }


    public void createCrossBlockWithDefaultItem(BlockModelGenerators blockModels, Block p_386508_, BlockModelGenerators.PlantType p_387047_) {
        blockModels.registerSimpleItemModel(p_386508_.asItem(), p_387047_.createItemModel(blockModels, p_386508_));
        this.createCrossBlock(blockModels, p_386508_, p_387047_);
    }

    public void createCrossBlock(BlockModelGenerators blockModels, Block p_388178_, BlockModelGenerators.PlantType p_387157_) {
        TextureMapping texturemapping = p_387157_.getTextureMapping(p_388178_);
        this.createCrossBlock(blockModels, p_388178_, p_387157_, texturemapping);
    }

    public void createCrossBlock(BlockModelGenerators blockModels, Block p_388360_, BlockModelGenerators.PlantType p_386631_, TextureMapping p_388352_) {
        ResourceLocation resourcelocation = p_386631_.getCross().extend().renderType("cutout").build().create(p_388360_, p_388352_, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(createSimpleBlock(p_388360_, resourcelocation));
    }

    public void createTrivialCube(BlockModelGenerators blockModels, Block p_386512_) {
        blockModels.itemModelOutput.accept(p_386512_.asItem(), ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(p_386512_)));
        blockModels.createTrivialBlock(p_386512_, TexturedModel.CUBE);
    }

    public void createTrivialBlock(BlockModelGenerators blockModels, Block p_387678_, TexturedModel.Provider p_386545_) {
        blockModels.itemModelOutput.accept(p_387678_.asItem(), ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(p_387678_)));

        blockModels.blockStateOutput.accept(createSimpleBlock(p_387678_, p_386545_.create(p_387678_, blockModels.modelOutput)));
    }

    public void createCampfires(BlockModelGenerators blockModels, Block... p_387949_) {
        ResourceLocation resourcelocation = ModelLocationUtils.decorateBlockModelLocation("campfire_off");

        for (Block block : p_387949_) {
            ResourceLocation resourcelocation1 = ModelTemplates.CAMPFIRE.extend().renderType("cutout").build().create(block, TextureMapping.campfire(block), blockModels.modelOutput);
            blockModels.registerSimpleFlatItemModel(block.asItem());
            blockModels.blockStateOutput
                    .accept(
                            MultiVariantGenerator.multiVariant(block)
                                    .with(createBooleanModelDispatch(BlockStateProperties.LIT, resourcelocation1, resourcelocation))
                                    .with(createHorizontalFacingDispatchAlt())
                    );
        }
    }

    public void createCutoutCube(BlockModelGenerators blockModels, Block block) {
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, CUTOUT_CUBE.create(block, TextureMapping.cube(block), blockModels.modelOutput)));
    }


    public void createTranslucentCube(BlockModelGenerators blockModels, Block block) {
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, TRANSLUCENT_CUBE.create(block, TextureMapping.cube(block), blockModels.modelOutput)));
    }

    public void createGlowCube(BlockModelGenerators blockModels, Block block) {
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, GLOW_CUBE.create(block, FrostTextureMapping.glowCube(block), blockModels.modelOutput)));
    }


    public FrostBlockFamilyProvider family(BlockModelGenerators generators, Block p_388779_) {
        TexturedModel texturedmodel = generators.texturedModels.getOrDefault(p_388779_, TexturedModel.CUBE.get(p_388779_));
        return new FrostBlockFamilyProvider(generators, texturedmodel.getMapping()).fullBlock(p_388779_, texturedmodel.getTemplate());
    }

    public void createGrassLikeFrostBlock(BlockModelGenerators generators, Block block, Block dirt) {
        TextureMapping texturemapping = new TextureMapping()
                .put(TextureSlot.BOTTOM, getBlockTexture(dirt))
                .put(TextureSlot.TOP, getBlockTexture(block, "_top"))
                .put(TextureSlot.SIDE, getBlockTexture(block, "_side"))
                .copySlot(TextureSlot.BOTTOM, TextureSlot.PARTICLE)
                .put(FrostTextureMapping.OVERLAY, getBlockTexture(block, "_side_overlay"));

        generators.blockStateOutput.accept(createSimpleBlock(block, FrostModelTemplate.GRASS_BLOCK.extend().renderType("cutout_mipped").build().create(block, texturemapping, generators.modelOutput)));
        generators.registerSimpleTintedItemModel(block, ModelLocationUtils.getModelLocation(block), new GrassColorSource());
    }

    public void createGrassLikeFrostStoneBlock(BlockModelGenerators generators, Block block, Block dirt) {
        TextureMapping texturemapping = new TextureMapping()
                .put(TextureSlot.BOTTOM, getBlockTexture(dirt))
                .put(TextureSlot.TOP, getBlockTexture(block, "_top"))
                .put(TextureSlot.SIDE, getBlockTexture(block, "_side"))
                .copySlot(TextureSlot.BOTTOM, TextureSlot.PARTICLE)
                .copySlot(TextureSlot.SIDE, FrostTextureMapping.OVERLAY);
        generators.blockStateOutput.accept(createSimpleBlock(block, FrostModelTemplate.GRASS_BLOCK.extend().renderType("cutout").build().create(block, texturemapping, generators.modelOutput)));
        generators.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
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

    public void createBlockEgg(BlockModelGenerators generators, Block block) {
        generators.blockStateOutput
                .accept(
                        MultiVariantGenerator.multiVariant(block)
                                .with(
                                        PropertyDispatch.properties(BlockStateProperties.EGGS, BlockStateProperties.HATCH)
                                                .generateList((p_387353_, p_388815_) -> Arrays.asList(createRotatedVariants(this.createEggModel(generators, block, p_387353_, p_388815_))))
                                )
                );
        generators.registerSimpleItemModel(block.asItem(), ModelLocationUtils.getModelLocation(block));

    }

    public ResourceLocation createEggModel(BlockModelGenerators generators, Block block, Integer p_386499_, Integer p_387511_) {
        switch (p_387511_) {
            case 0:
                return createEggModel(generators, block, p_386499_, "", TextureMapping.cube(getBlockTexture(block)));
            case 1:
                return createEggModel(generators, block,
                        p_386499_, "slightly_cracked_", TextureMapping.cube(getBlockTexture(block))
                );
            case 2:
                return createEggModel(generators, block,
                        p_386499_, "very_cracked_", TextureMapping.cube(getBlockTexture(block))
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
                return ModelTemplates.TURTLE_EGG.create(FrostRealm.prefix(p_387935_ + getBlockName(block)).withPrefix("block/"), p_388813_, generators.modelOutput);
            case 2:
                return ModelTemplates.TWO_TURTLE_EGGS
                        .create(FrostRealm.prefix("two_" + p_387935_ + getBlockName(block)).withPrefix("block/"), p_388813_, generators.modelOutput);
            case 3:
                return ModelTemplates.THREE_TURTLE_EGGS
                        .create(FrostRealm.prefix("three_" + p_387935_ + getBlockName(block)).withPrefix("block/"), p_388813_, generators.modelOutput);
            case 4:
                return ModelTemplates.FOUR_TURTLE_EGGS
                        .create(FrostRealm.prefix("four_" + p_387935_ + getBlockName(block)).withPrefix("block/"), p_388813_, generators.modelOutput);
            default:
                throw new UnsupportedOperationException();
        }
    }


    public void createFrostFarmland(BlockModelGenerators blockModels) {
        TextureMapping texturemapping = new TextureMapping().put(TextureSlot.DIRT, getBlockTexture(FrostBlocks.FROZEN_DIRT.get())).put(TextureSlot.TOP, getBlockTexture(FrostBlocks.FROZEN_FARMLAND.get()));
        TextureMapping texturemapping1 = new TextureMapping().put(TextureSlot.DIRT, getBlockTexture(FrostBlocks.FROZEN_DIRT.get())).put(TextureSlot.TOP, getBlockTexture(FrostBlocks.FROZEN_FARMLAND.get(), "_moist"));
        ResourceLocation resourcelocation = ModelTemplates.FARMLAND.create(FrostBlocks.FROZEN_FARMLAND.get(), texturemapping, blockModels.modelOutput);
        ResourceLocation resourcelocation1 = ModelTemplates.FARMLAND.create(getBlockTexture(FrostBlocks.FROZEN_FARMLAND.get(), "_moist"), texturemapping1, blockModels.modelOutput);
        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(FrostBlocks.FROZEN_FARMLAND.get()).with(BlockModelGenerators.createEmptyOrFullDispatch(BlockStateProperties.MOISTURE, 7, resourcelocation1, resourcelocation)));
    }

    public static MultiVariantGenerator createSimpleBlock(Block p_387997_, ResourceLocation p_388814_) {
        return MultiVariantGenerator.multiVariant(p_387997_, Variant.variant().with(VariantProperties.MODEL, p_388814_));
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
        generator.registerSimpleItemModel(FrostBlocks.POINTED_ICE.get(), ModelLocationUtils.getModelLocation(FrostBlocks.POINTED_ICE.get()).withSuffix("_up_tip"));
    }

    public Variant createPointedIceVariant(BlockModelGenerators generator, Direction p_387068_, DripstoneThickness p_388190_) {
        String s = "_" + p_387068_.getSerializedName() + "_" + p_388190_.getSerializedName();
        TextureMapping texturemapping = TextureMapping.cross(getBlockTexture(FrostBlocks.POINTED_ICE.get(), s));
        return Variant.variant()
                .with(VariantProperties.MODEL, ModelTemplates.POINTED_DRIPSTONE.createWithSuffix(FrostBlocks.POINTED_ICE.get(), s, texturemapping, generator.modelOutput));
    }
}
