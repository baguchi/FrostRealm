package baguchan.frostrealm.data.provider;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.data.generator.FrostModelTemplates;
import baguchan.frostrealm.data.generator.FrostTextureMappings;
import baguchan.frostrealm.registry.FrostBlocks;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.color.item.GrassColorSource;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HangingMossBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.block.state.properties.Property;

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

    public FrBlockstateModelProvider(Consumer<BlockModelDefinitionGenerator> blockStateOutput, ItemModelOutput itemModelOutput, BiConsumer<ResourceLocation, ModelInstance> modelOutput) {
        super(blockStateOutput, itemModelOutput, modelOutput);
    }

    public void createItemWithDoubleGrassTint(Block p_388714_) {
        this.registerSimpleTintedItemModel(p_388714_, ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(p_388714_), TextureMapping.layer0(getBlockTexture(p_388714_).withSuffix("_top")), this.modelOutput), new GrassColorSource());
    }

    @Override
    public void createHangingMoss(Block p_386702_) {
        this.registerSimpleFlatItemModel(p_386702_);
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(p_386702_).with(PropertyDispatch.initial(HangingMossBlock.TIP).generate(p_408963_ -> {
            String s = p_408963_ ? "_tip" : "";
            TextureMapping texturemapping = TextureMapping.cross(TextureMapping.getBlockTexture(p_386702_, s));
            return plainVariant(BlockModelGenerators.PlantType.NOT_TINTED.getCross().extend().renderType("cutout").build().createWithSuffix(p_386702_, s, texturemapping, this.modelOutput));
        })));
    }

    public void createTintedDoublePlant(Block p_388276_) {
        ResourceLocation resourcelocation = this.createFlatItemModelWithBlockTexture(p_388276_.asItem(), p_388276_, "_top");
        this.registerSimpleTintedItemModel(p_388276_, resourcelocation, new GrassColorSource());
        createDoublePlant(p_388276_, BlockModelGenerators.PlantType.TINTED);
    }

    public void createDoublePlant(Block p_388543_, BlockModelGenerators.PlantType p_388551_) {
        MultiVariant resourcelocation = plainVariant(this.createSuffixedVariant(p_388543_, "_top", p_388551_.getCross().extend().renderType("cutout").build(), TextureMapping::cross));
        MultiVariant resourcelocation1 = plainVariant(this.createSuffixedVariant(p_388543_, "_bottom", p_388551_.getCross().extend().renderType("cutout").build(), TextureMapping::cross));
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
        MultiVariant multivariant = plainVariant(ModelTemplates.TRAPDOOR_TOP.extend().renderType("cutout").build().create(p_387551_, texturemapping, this.modelOutput));
        ResourceLocation resourcelocation = ModelTemplates.TRAPDOOR_BOTTOM.extend().renderType("cutout").build().create(p_387551_, texturemapping, this.modelOutput);
        MultiVariant multivariant1 = plainVariant(ModelTemplates.TRAPDOOR_OPEN.extend().renderType("cutout").build().create(p_387551_, texturemapping, this.modelOutput));
        this.blockStateOutput.accept(createTrapdoor(p_387551_, multivariant, plainVariant(resourcelocation), multivariant1));
        this.registerSimpleItemModel(p_387551_, resourcelocation);
    }


    public void createOrientableTrapdoor(Block p_388937_) {
        TextureMapping texturemapping = TextureMapping.defaultTexture(p_388937_);
        MultiVariant multivariant = plainVariant(ModelTemplates.ORIENTABLE_TRAPDOOR_TOP.extend().renderType("cutout").build().create(p_388937_, texturemapping, this.modelOutput));
        ResourceLocation resourcelocation = ModelTemplates.ORIENTABLE_TRAPDOOR_BOTTOM.extend().renderType("cutout").build().create(p_388937_, texturemapping, this.modelOutput);
        MultiVariant multivariant1 = plainVariant(ModelTemplates.ORIENTABLE_TRAPDOOR_OPEN.extend().renderType("cutout").build().create(p_388937_, texturemapping, this.modelOutput));
        this.blockStateOutput.accept(createOrientableTrapdoor(p_388937_, multivariant, plainVariant(resourcelocation), multivariant1));
        this.registerSimpleItemModel(p_388937_, resourcelocation);
    }

    @Override
    public void createDoor(Block p_386982_) {
        TextureMapping texturemapping = TextureMapping.door(p_386982_);
        MultiVariant multivariant = plainVariant(ModelTemplates.DOOR_BOTTOM_LEFT.extend().renderType("cutout").build().create(p_386982_, texturemapping, this.modelOutput));
        MultiVariant multivariant1 = plainVariant(ModelTemplates.DOOR_BOTTOM_LEFT_OPEN.extend().renderType("cutout").build().create(p_386982_, texturemapping, this.modelOutput));
        MultiVariant multivariant2 = plainVariant(ModelTemplates.DOOR_BOTTOM_RIGHT.extend().renderType("cutout").build().create(p_386982_, texturemapping, this.modelOutput));
        MultiVariant multivariant3 = plainVariant(ModelTemplates.DOOR_BOTTOM_RIGHT_OPEN.extend().renderType("cutout").build().create(p_386982_, texturemapping, this.modelOutput));
        MultiVariant multivariant4 = plainVariant(ModelTemplates.DOOR_TOP_LEFT.extend().renderType("cutout").build().create(p_386982_, texturemapping, this.modelOutput));
        MultiVariant multivariant5 = plainVariant(ModelTemplates.DOOR_TOP_LEFT_OPEN.extend().renderType("cutout").build().create(p_386982_, texturemapping, this.modelOutput));
        MultiVariant multivariant6 = plainVariant(ModelTemplates.DOOR_TOP_RIGHT.extend().renderType("cutout").build().create(p_386982_, texturemapping, this.modelOutput));
        MultiVariant multivariant7 = plainVariant(ModelTemplates.DOOR_TOP_RIGHT_OPEN.extend().renderType("cutout").build().create(p_386982_, texturemapping, this.modelOutput));
        this.registerSimpleFlatItemModel(p_386982_.asItem());
        this.blockStateOutput
                .accept(
                        createDoor(p_386982_, multivariant, multivariant1, multivariant2, multivariant3, multivariant4, multivariant5, multivariant6, multivariant7)
                );
    }

    public void createCutoutMippedCube(Block block) {
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, plainVariant(ModelTemplates.CUBE_ALL.extend().renderType("cutout_mipped").build().create(block, TextureMapping.cube(block), this.modelOutput))));
    }

    public void createTranslucentCube(Block block) {
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, plainVariant(ModelTemplates.CUBE_ALL.extend().renderType("translucent").build().create(block, TextureMapping.cube(block), this.modelOutput))));
    }

    public ResourceLocation createTranslucentItemModelWithBlockTexture(Item item, Block block) {
        return ModelTemplates.FLAT_ITEM.extend().renderType("translucent").build().create(ModelLocationUtils.getModelLocation(item), TextureMapping.layer0(block), this.modelOutput);
    }

    public void createCubeColumn(Block side, Block top) {
        TextureMapping mapping = TextureMapping.column(TextureMapping.getBlockTexture(side), TextureMapping.getBlockTexture(top));
        MultiVariant location = plainVariant(ModelTemplates.CUBE_COLUMN.create(side, mapping, this.modelOutput));
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(side, location));
    }

    public void createCampfires(Block... p_387949_) {
        MultiVariant multivariant = plainVariant(ModelLocationUtils.decorateBlockModelLocation("campfire_off"));

        for (Block block : p_387949_) {
            MultiVariant multivariant1 = plainVariant(ModelTemplates.CAMPFIRE.extend().renderType("cutout").build().create(block, TextureMapping.campfire(block), this.modelOutput));
            this.registerSimpleFlatItemModel(block.asItem());
            this.blockStateOutput
                    .accept(
                            MultiVariantGenerator.dispatch(block)
                                    .with(createBooleanModelDispatch(BlockStateProperties.LIT, multivariant1, multivariant))
                                    .with(ROTATION_HORIZONTAL_FACING_ALT)
                    );
        }
    }

    public void createCutoutCube(Block block) {
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, plainVariant(CUTOUT_CUBE.create(block, TextureMapping.cube(block), this.modelOutput))));
    }

    public void createGlowCube(Block block) {
        this.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, plainVariant(GLOW_CUBE.create(block, FrostTextureMappings.glowCube(block), this.modelOutput))));
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
                        MultiVariantGenerator.dispatch(block)
                                .with(
                                        PropertyDispatch.initial(BlockStateProperties.EGGS, BlockStateProperties.HATCH)
                                                .generate((p_408968_, p_408969_) -> createRotatedVariants(this.createEggModel(block, p_408968_, p_408969_)))
                                )
                );
    }

    public void createBlockEgg(Block block) {
        this.blockStateOutput
                .accept(
                        MultiVariantGenerator.dispatch(block)
                                .with(
                                        PropertyDispatch.initial(BlockStateProperties.EGGS, BlockStateProperties.HATCH)
                                                .generate((p_408968_, p_408969_) -> createRotatedVariants(this.createEggModel(block, p_408968_, p_408969_)))
                                )
                );
        this.registerSimpleItemModel(block.asItem(), ModelLocationUtils.getModelLocation(block));

    }

    public Variant createEggModel(Block block, Integer p_386499_, Integer p_387511_) {
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

    public Variant createEggModel(Block block, int p_387392_, String p_387935_, TextureMapping p_388813_) {
        switch (p_387392_) {
            case 1:
                return plainModel(ModelTemplates.TURTLE_EGG.create(FrostRealm.prefix(p_387935_ + getBlockName(block)).withPrefix("block/"), p_388813_, this.modelOutput));
            case 2:
                return plainModel(ModelTemplates.TWO_TURTLE_EGGS
                        .create(FrostRealm.prefix("two_" + p_387935_ + getBlockName(block)).withPrefix("block/"), p_388813_, this.modelOutput));
            case 3:
                return plainModel(ModelTemplates.THREE_TURTLE_EGGS
                        .create(FrostRealm.prefix("three_" + p_387935_ + getBlockName(block)).withPrefix("block/"), p_388813_, this.modelOutput));
            case 4:
                return plainModel(ModelTemplates.FOUR_TURTLE_EGGS
                        .create(FrostRealm.prefix("four_" + p_387935_ + getBlockName(block)).withPrefix("block/"), p_388813_, this.modelOutput));
            default:
                throw new UnsupportedOperationException();
        }
    }


    public void createFrostFarmland() {
        TextureMapping texturemapping = new TextureMapping().put(TextureSlot.DIRT, getBlockTexture(FrostBlocks.FROZEN_DIRT.get())).put(TextureSlot.TOP, getBlockTexture(FrostBlocks.FROZEN_FARMLAND.get()));
        TextureMapping texturemapping1 = new TextureMapping().put(TextureSlot.DIRT, getBlockTexture(FrostBlocks.FROZEN_DIRT.get())).put(TextureSlot.TOP, getBlockTexture(FrostBlocks.FROZEN_FARMLAND.get(), "_moist"));
        MultiVariant resourcelocation = plainVariant(ModelTemplates.FARMLAND.create(FrostBlocks.FROZEN_FARMLAND.get(), texturemapping, this.modelOutput));
        MultiVariant resourcelocation1 = plainVariant(ModelTemplates.FARMLAND.create(getBlockTexture(FrostBlocks.FROZEN_FARMLAND.get(), "_moist"), texturemapping1, this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(FrostBlocks.FROZEN_FARMLAND.get()).with(BlockModelGenerators.createEmptyOrFullDispatch(BlockStateProperties.MOISTURE, 7, resourcelocation1, resourcelocation)));
    }

    public static MultiVariantGenerator createSimpleBlock(Block p_387997_, ResourceLocation p_388814_) {
        return MultiVariantGenerator.dispatch(p_387997_, plainVariant(p_388814_));
    }

    public void createFrostPortalBlock() {
        this.blockStateOutput
                .accept(
                        MultiVariantGenerator.dispatch(FrostBlocks.FROST_PORTAL.get())
                                .with(
                                        PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_AXIS)
                                                .select(Direction.Axis.X, plainVariant(ModelLocationUtils.getModelLocation(FrostBlocks.FROST_PORTAL.get(), "_ns")))
                                                .select(Direction.Axis.Z, plainVariant(ModelLocationUtils.getModelLocation(FrostBlocks.FROST_PORTAL.get(), "_ew")))
                                )
                );
    }

    public void createPointedIce(BlockModelGenerators generator) {
        PropertyDispatch.C2<MultiVariant, Direction, DripstoneThickness> c2 = PropertyDispatch.initial(
                BlockStateProperties.VERTICAL_DIRECTION, BlockStateProperties.DRIPSTONE_THICKNESS
        );

        for (DripstoneThickness dripstonethickness : DripstoneThickness.values()) {
            c2.select(Direction.UP, dripstonethickness, this.createPointedDripstoneVariant(Direction.UP, dripstonethickness));
        }

        for (DripstoneThickness dripstonethickness1 : DripstoneThickness.values()) {
            c2.select(Direction.DOWN, dripstonethickness1, this.createPointedDripstoneVariant(Direction.DOWN, dripstonethickness1));
        }

        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(FrostBlocks.POINTED_ICE.get()).with(c2));
        generator.registerSimpleFlatItemModel(FrostBlocks.POINTED_ICE.get(), "_up_tip");
    }

    public MultiVariant createPointedDripstoneVariant(Direction p_387068_, DripstoneThickness p_388190_) {
        String s = "_" + p_387068_.getSerializedName() + "_" + p_388190_.getSerializedName();
        TextureMapping texturemapping = TextureMapping.cross(TextureMapping.getBlockTexture(FrostBlocks.POINTED_ICE.get(), s));
        return plainVariant(ModelTemplates.POINTED_DRIPSTONE.extend().renderType("cutout").build().createWithSuffix(FrostBlocks.POINTED_ICE.get(), s, texturemapping, this.modelOutput));
    }

    public void createCropBlock(Block p_387553_, Property<Integer> p_386757_, int... p_388514_) {
        this.registerSimpleFlatItemModel(p_387553_.asItem());
        if (p_386757_.getPossibleValues().size() != p_388514_.length) {
            throw new IllegalArgumentException();
        } else {
            Int2ObjectMap<ResourceLocation> int2objectmap = new Int2ObjectOpenHashMap<>();
            this.blockStateOutput
                    .accept(
                            MultiVariantGenerator.dispatch(p_387553_)
                                    .with(
                                            PropertyDispatch.initial(p_386757_)
                                                    .generate(
                                                            p_408977_ -> {
                                                                int i = p_388514_[p_408977_];
                                                                return plainVariant(
                                                                        int2objectmap.computeIfAbsent(
                                                                                i,
                                                                                p_387308_ -> this.createSuffixedVariant(
                                                                                        p_387553_, "_" + p_387308_, ModelTemplates.CROP.extend().renderType("cutout").build(), TextureMapping::crop
                                                                                )
                                                                        )
                                                                );
                                                            }
                                                    )
                                    )
                    );
        }
    }

    public MultiVariant createSideFireModels(Block p_387079_) {
        return variants(plainModel(ModelTemplates.FIRE_SIDE.extend().renderType("cutout").build().create(ModelLocationUtils.getModelLocation(p_387079_, "_side0"), TextureMapping.fire0(p_387079_), this.modelOutput)), plainModel(ModelTemplates.FIRE_SIDE.extend().renderType("cutout").build().create(ModelLocationUtils.getModelLocation(p_387079_, "_side1"), TextureMapping.fire1(p_387079_), this.modelOutput)), plainModel(ModelTemplates.FIRE_SIDE_ALT.extend().renderType("cutout").build().create(ModelLocationUtils.getModelLocation(p_387079_, "_side_alt0"), TextureMapping.fire0(p_387079_), this.modelOutput)), plainModel(ModelTemplates.FIRE_SIDE_ALT.extend().renderType("cutout").build().create(ModelLocationUtils.getModelLocation(p_387079_, "_side_alt1"), TextureMapping.fire1(p_387079_), this.modelOutput)));
    }

    public MultiVariant createTopFireModels(Block p_387163_) {
        return variants(plainModel(ModelTemplates.FIRE_UP.extend().renderType("cutout").build().create(ModelLocationUtils.getModelLocation(p_387163_, "_up0"), TextureMapping.fire0(p_387163_), this.modelOutput)), plainModel(ModelTemplates.FIRE_UP.extend().renderType("cutout").build().create(ModelLocationUtils.getModelLocation(p_387163_, "_up1"), TextureMapping.fire1(p_387163_), this.modelOutput)), plainModel(ModelTemplates.FIRE_UP_ALT.extend().renderType("cutout").build().create(ModelLocationUtils.getModelLocation(p_387163_, "_up_alt0"), TextureMapping.fire0(p_387163_), this.modelOutput)), plainModel(ModelTemplates.FIRE_UP_ALT.extend().renderType("cutout").build().create(ModelLocationUtils.getModelLocation(p_387163_, "_up_alt1"), TextureMapping.fire1(p_387163_), this.modelOutput)));
    }

    public MultiVariant createFloorFireModels(Block p_387402_) {
        return variants(plainModel(ModelTemplates.FIRE_FLOOR.extend().renderType("cutout").build().create(ModelLocationUtils.getModelLocation(p_387402_, "_floor0"), TextureMapping.fire0(p_387402_), this.modelOutput)), plainModel(ModelTemplates.FIRE_FLOOR.extend().renderType("cutout").build().create(ModelLocationUtils.getModelLocation(p_387402_, "_floor1"), TextureMapping.fire1(p_387402_), this.modelOutput)));
    }

    public void createFrostFire() {
        MultiVariant multivariant = this.createFloorFireModels(FrostBlocks.FROST_FIRE.get());
        MultiVariant multivariant1 = this.createSideFireModels(FrostBlocks.FROST_FIRE.get());
        this.blockStateOutput.accept(MultiPartGenerator.multiPart(FrostBlocks.FROST_FIRE.get()).with(multivariant).with(multivariant1).with(multivariant1.with(Y_ROT_90)).with(multivariant1.with(Y_ROT_180)).with(multivariant1.with(Y_ROT_270)));
    }
}
