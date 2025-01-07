package baguchan.frostrealm.data.provider;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.data.generator.FrostModelTemplates;
import baguchan.frostrealm.data.generator.FrostTextureMappings;
import baguchan.frostrealm.registry.FrostBlocks;
import net.minecraft.client.color.item.GrassColorSource;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static net.minecraft.client.data.models.model.TextureMapping.getBlockTexture;
import static net.minecraft.client.data.models.model.TexturedModel.createDefault;

public abstract class FrBlockstateModelProvider extends BlockModelGenerators {
    public static final ModelTemplate GLOW_CUBE = FrostModelTemplates.GLOW_CUBE.extend().renderType("cutout").build();
    public static final ModelTemplate TRANSLUCENT_CUBE = ModelTemplates.CUBE_ALL.extend().renderType("translucent").build();
    public static final ModelTemplate CUTOUT_CUBE = ModelTemplates.CUBE_ALL.extend().renderType("cutout").build();
    public static final ModelTemplate CROP = ModelTemplates.CROP.extend().renderType("cutout").build();
    public static final TexturedModel.Provider LEAVES_PROVIDER = createDefault(TextureMapping::cube, ModelTemplates.LEAVES.extend().renderType("cutout").build());
    public static final TexturedModel.Provider COLUMN_CUTOUT = createDefault(TextureMapping::column, ModelTemplates.CUBE_COLUMN.extend().renderType("cutout").build());

    public FrBlockstateModelProvider(Consumer<BlockStateGenerator> blockStateOutput, ItemModelOutput itemModelOutput, BiConsumer<ResourceLocation, ModelInstance> modelOutput) {
        super(blockStateOutput, itemModelOutput, modelOutput);
    }

    public void createItemWithDoubleGrassTint(Block p_388714_) {
        this.registerSimpleTintedItemModel(p_388714_, ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(p_388714_), TextureMapping.layer0(getBlockTexture(p_388714_).withSuffix("_top")), this.modelOutput), new GrassColorSource());
    }


    public void createTintedDoublePlant(Block p_388276_) {
        ResourceLocation resourcelocation = this.createFlatItemModelWithBlockTexture(p_388276_.asItem(), p_388276_, "_top");
        this.registerSimpleTintedItemModel(p_388276_, resourcelocation, new GrassColorSource());
        createDoublePlant(p_388276_, BlockModelGenerators.PlantType.TINTED);
    }

    public void createDoublePlant(Block p_388543_, BlockModelGenerators.PlantType p_388551_) {
        ResourceLocation resourcelocation = this.createSuffixedVariant(p_388543_, "_top", p_388551_.getCross().extend().renderType("cutout").build(), TextureMapping::cross);
        ResourceLocation resourcelocation1 = this.createSuffixedVariant(p_388543_, "_bottom", p_388551_.getCross().extend().renderType("cutout").build(), TextureMapping::cross);
        this.createDoubleBlock(p_388543_, resourcelocation, resourcelocation1);
    }


    @Override
    public void createCrossBlock(Block block, PlantType type, TextureMapping mapping) {
        ResourceLocation resourcelocation = type.getCross().extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build().create(block, mapping, this.modelOutput);
        this.blockStateOutput.accept(createSimpleBlock(block, resourcelocation));
    }

    @Override
    public void createPlant(Block plant, Block pot, PlantType type) {
        this.createCrossBlock(plant, type);
        TextureMapping texturemapping = type.getPlantTextureMapping(plant);
        ResourceLocation resourcelocation = type.getCrossPot().extend().renderType(ResourceLocation.withDefaultNamespace("cutout")).build().create(pot, texturemapping, this.modelOutput);
        this.blockStateOutput.accept(createSimpleBlock(pot, resourcelocation));
    }

    @Override
    public void createTrapdoor(Block p_387551_) {
        TextureMapping texturemapping = TextureMapping.defaultTexture(p_387551_);
        ResourceLocation resourcelocation = ModelTemplates.TRAPDOOR_TOP.extend().renderType("cutout").build().create(p_387551_, texturemapping, this.modelOutput);
        ResourceLocation resourcelocation1 = ModelTemplates.TRAPDOOR_BOTTOM.extend().renderType("cutout").build().create(p_387551_, texturemapping, this.modelOutput);
        ResourceLocation resourcelocation2 = ModelTemplates.TRAPDOOR_OPEN.extend().renderType("cutout").build().create(p_387551_, texturemapping, this.modelOutput);
        this.blockStateOutput.accept(createTrapdoor(p_387551_, resourcelocation, resourcelocation1, resourcelocation2));
        this.registerSimpleItemModel(p_387551_, resourcelocation1.withSuffix(""));
    }

    @Override
    public void createOrientableTrapdoor(Block p_388937_) {
        TextureMapping texturemapping = TextureMapping.defaultTexture(p_388937_);
        ResourceLocation resourcelocation = ModelTemplates.ORIENTABLE_TRAPDOOR_TOP.extend().renderType("cutout").build().create(p_388937_, texturemapping, this.modelOutput);
        ResourceLocation resourcelocation1 = ModelTemplates.ORIENTABLE_TRAPDOOR_BOTTOM.extend().renderType("cutout").build().create(p_388937_, texturemapping, this.modelOutput);
        ResourceLocation resourcelocation2 = ModelTemplates.ORIENTABLE_TRAPDOOR_OPEN.extend().renderType("cutout").build().create(p_388937_, texturemapping, this.modelOutput);
        this.blockStateOutput.accept(createOrientableTrapdoor(p_388937_, resourcelocation, resourcelocation1, resourcelocation2));
        this.registerSimpleItemModel(p_388937_, resourcelocation1.withSuffix(""));
    }

    @Override
    public void createDoor(Block block) {
        TextureMapping bottomMapping = FrostTextureMappings.doorBottom(block);
        TextureMapping topMapping = FrostTextureMappings.doorTop(block);
        ResourceLocation left = ModelTemplates.DOOR_BOTTOM_LEFT.extend().renderType("cutout").build().create(block, bottomMapping, this.modelOutput);
        ResourceLocation bottomLeftOpen = ModelTemplates.DOOR_BOTTOM_LEFT_OPEN.extend().renderType("cutout").build().create(block, bottomMapping, this.modelOutput);
        ResourceLocation bottomRight = ModelTemplates.DOOR_BOTTOM_RIGHT.extend().renderType("cutout").build().create(block, bottomMapping, this.modelOutput);
        ResourceLocation bottomRightOpen = ModelTemplates.DOOR_BOTTOM_RIGHT_OPEN.extend().renderType("cutout").build().create(block, bottomMapping, this.modelOutput);
        ResourceLocation topLeft = ModelTemplates.DOOR_TOP_LEFT.extend().renderType("cutout").build().create(block, topMapping, this.modelOutput);
        ResourceLocation topLeftOpen = ModelTemplates.DOOR_TOP_LEFT_OPEN.extend().renderType("cutout").build().create(block, topMapping, this.modelOutput);
        ResourceLocation topRight = ModelTemplates.DOOR_TOP_RIGHT.extend().renderType("cutout").build().create(block, topMapping, this.modelOutput);
        ResourceLocation topRightOpen = ModelTemplates.DOOR_TOP_RIGHT_OPEN.extend().renderType("cutout").build().create(block, topMapping, this.modelOutput);
        this.registerSimpleFlatItemModel(block.asItem());
        this.blockStateOutput.accept(createDoor(block, left, bottomLeftOpen, bottomRight, bottomRightOpen, topLeft, topLeftOpen, topRight, topRightOpen));
    }

    public void createCutoutMippedCube(Block block) {
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, ModelTemplates.CUBE_ALL.extend().renderType("cutout_mipped").build().create(block, TextureMapping.cube(block), this.modelOutput)));
    }

    public void createTranslucentCube(Block block) {
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, ModelTemplates.CUBE_ALL.extend().renderType("translucent").build().create(block, TextureMapping.cube(block), this.modelOutput)));
    }

    public ResourceLocation createTranslucentItemModelWithBlockTexture(Item item, Block block) {
        return ModelTemplates.FLAT_ITEM.extend().renderType("translucent").build().create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(block), this.modelOutput);
    }

    public void createCubeColumn(Block side, Block top) {
        TextureMapping mapping = TextureMapping.column(TextureMapping.getBlockTexture(side), TextureMapping.getBlockTexture(top));
        ResourceLocation location = ModelTemplates.CUBE_COLUMN.create(side, mapping, this.modelOutput);
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(side, location));
    }

    public void createCampfires(Block... p_387949_) {
        ResourceLocation resourcelocation = ModelLocationUtils.decorateBlockModelLocation("campfire_off");

        for (Block block : p_387949_) {
            ResourceLocation resourcelocation1 = ModelTemplates.CAMPFIRE.extend().renderType("cutout").build().create(block, TextureMapping.campfire(block), this.modelOutput);
            this.registerSimpleFlatItemModel(block.asItem());
            this.blockStateOutput
                    .accept(
                            MultiVariantGenerator.multiVariant(block)
                                    .with(createBooleanModelDispatch(BlockStateProperties.LIT, resourcelocation1, resourcelocation))
                                    .with(createHorizontalFacingDispatchAlt())
                    );
        }
    }

    public void createCutoutCube(Block block) {
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, CUTOUT_CUBE.create(block, TextureMapping.cube(block), this.modelOutput)));
    }

    public void createGlowCube(Block block) {
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, GLOW_CUBE.create(block, FrostTextureMappings.glowCube(block), this.modelOutput)));
    }

    public void createGrassLikeFrostBlock(Block block, Block dirt) {
        TextureMapping texturemapping = new TextureMapping()
                .put(TextureSlot.BOTTOM, getBlockTexture(dirt))
                .put(TextureSlot.TOP, getBlockTexture(block, "_top"))
                .put(TextureSlot.SIDE, getBlockTexture(block, "_side"))
                .copySlot(TextureSlot.BOTTOM, TextureSlot.PARTICLE)
                .put(FrostTextureMappings.OVERLAY, getBlockTexture(block, "_side_overlay"));

        this.blockStateOutput.accept(createSimpleBlock(block, FrostModelTemplates.GRASS_BLOCK.extend().renderType("cutout_mipped").build().create(block, texturemapping, this.modelOutput)));
        this.registerSimpleTintedItemModel(block, ModelLocationUtils.getModelLocation(block), new GrassColorSource());
    }

    public void createGrassLikeFrostStoneBlock(Block block, Block dirt) {
        TextureMapping texturemapping = new TextureMapping()
                .put(TextureSlot.BOTTOM, getBlockTexture(dirt))
                .put(TextureSlot.TOP, getBlockTexture(block, "_top"))
                .put(TextureSlot.SIDE, getBlockTexture(block, "_side"))
                .copySlot(TextureSlot.BOTTOM, TextureSlot.PARTICLE)
                .copySlot(TextureSlot.SIDE, FrostTextureMappings.OVERLAY);
        this.blockStateOutput.accept(createSimpleBlock(block, FrostModelTemplates.GRASS_BLOCK.extend().renderType("cutout").build().create(block, texturemapping, this.modelOutput)));
        this.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block));
    }

    public void createEgg(Block block) {
        this.registerSimpleFlatItemModel(block.asItem());
        this.blockStateOutput
                .accept(
                        MultiVariantGenerator.multiVariant(block)
                                .with(
                                        PropertyDispatch.properties(BlockStateProperties.EGGS, BlockStateProperties.HATCH)
                                                .generateList((p_387353_, p_388815_) -> Arrays.asList(createRotatedVariants(this.createEggModel(block, p_387353_, p_388815_))))
                                )
                );
    }

    public void createBlockEgg(Block block) {
        this.blockStateOutput
                .accept(
                        MultiVariantGenerator.multiVariant(block)
                                .with(
                                        PropertyDispatch.properties(BlockStateProperties.EGGS, BlockStateProperties.HATCH)
                                                .generateList((p_387353_, p_388815_) -> Arrays.asList(createRotatedVariants(this.createEggModel(block, p_387353_, p_388815_))))
                                )
                );
        this.registerSimpleItemModel(block.asItem(), ModelLocationUtils.getModelLocation(block));

    }

    public ResourceLocation createEggModel(Block block, Integer p_386499_, Integer p_387511_) {
        switch (p_387511_) {
            case 0:
                return createEggModel(block, p_386499_, "", TextureMapping.cube(getBlockTexture(block)));
            case 1:
                return createEggModel(block,
                        p_386499_, "slightly_cracked_", TextureMapping.cube(getBlockTexture(block))
                );
            case 2:
                return createEggModel(block,
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

    public ResourceLocation createEggModel(Block block, int p_387392_, String p_387935_, TextureMapping p_388813_) {
        switch (p_387392_) {
            case 1:
                return ModelTemplates.TURTLE_EGG.create(FrostRealm.prefix(p_387935_ + getBlockName(block)).withPrefix("block/"), p_388813_, this.modelOutput);
            case 2:
                return ModelTemplates.TWO_TURTLE_EGGS
                        .create(FrostRealm.prefix("two_" + p_387935_ + getBlockName(block)).withPrefix("block/"), p_388813_, this.modelOutput);
            case 3:
                return ModelTemplates.THREE_TURTLE_EGGS
                        .create(FrostRealm.prefix("three_" + p_387935_ + getBlockName(block)).withPrefix("block/"), p_388813_, this.modelOutput);
            case 4:
                return ModelTemplates.FOUR_TURTLE_EGGS
                        .create(FrostRealm.prefix("four_" + p_387935_ + getBlockName(block)).withPrefix("block/"), p_388813_, this.modelOutput);
            default:
                throw new UnsupportedOperationException();
        }
    }


    public void createFrostFarmland() {
        TextureMapping texturemapping = new TextureMapping().put(TextureSlot.DIRT, getBlockTexture(FrostBlocks.FROZEN_DIRT.get())).put(TextureSlot.TOP, getBlockTexture(FrostBlocks.FROZEN_FARMLAND.get()));
        TextureMapping texturemapping1 = new TextureMapping().put(TextureSlot.DIRT, getBlockTexture(FrostBlocks.FROZEN_DIRT.get())).put(TextureSlot.TOP, getBlockTexture(FrostBlocks.FROZEN_FARMLAND.get(), "_moist"));
        ResourceLocation resourcelocation = ModelTemplates.FARMLAND.create(FrostBlocks.FROZEN_FARMLAND.get(), texturemapping, this.modelOutput);
        ResourceLocation resourcelocation1 = ModelTemplates.FARMLAND.create(getBlockTexture(FrostBlocks.FROZEN_FARMLAND.get(), "_moist"), texturemapping1, this.modelOutput);
        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(FrostBlocks.FROZEN_FARMLAND.get()).with(BlockModelGenerators.createEmptyOrFullDispatch(BlockStateProperties.MOISTURE, 7, resourcelocation1, resourcelocation)));
    }

    public static MultiVariantGenerator createSimpleBlock(Block p_387997_, ResourceLocation p_388814_) {
        return MultiVariantGenerator.multiVariant(p_387997_, Variant.variant().with(VariantProperties.MODEL, p_388814_));
    }

    public void createFrostPortalBlock() {
        this.blockStateOutput.accept(MultiVariantGenerator.multiVariant(FrostBlocks.FROST_PORTAL.get()).with(PropertyDispatch.property(BlockStateProperties.HORIZONTAL_AXIS)
                .select(Direction.Axis.X, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(FrostBlocks.FROST_PORTAL.get(), "_ns")))
                .select(Direction.Axis.Z, Variant.variant().with(VariantProperties.MODEL, ModelLocationUtils.getModelLocation(FrostBlocks.FROST_PORTAL.get(), "_ew")))));
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
        generator.registerSimpleFlatItemModel(FrostBlocks.POINTED_ICE.get(), "_up_tip");
    }

    public Variant createPointedIceVariant(BlockModelGenerators generator, Direction p_387068_, DripstoneThickness p_388190_) {
        String s = "_" + p_387068_.getSerializedName() + "_" + p_388190_.getSerializedName();
        TextureMapping texturemapping = TextureMapping.cross(getBlockTexture(FrostBlocks.POINTED_ICE.get(), s));
        return Variant.variant()
                .with(VariantProperties.MODEL, ModelTemplates.POINTED_DRIPSTONE.createWithSuffix(FrostBlocks.POINTED_ICE.get(), s, texturemapping, generator.modelOutput));
    }
}
