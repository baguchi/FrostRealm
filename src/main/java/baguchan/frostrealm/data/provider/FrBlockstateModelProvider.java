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
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static net.minecraft.client.data.models.model.TextureMapping.getBlockTexture;

public abstract class FrBlockstateModelProvider extends BlockModelGenerators {

    public FrBlockstateModelProvider(Consumer<BlockModelDefinitionGenerator> blockStateOutput, ItemModelOutput itemModelOutput, BiConsumer<Identifier, ModelInstance> modelOutput) {
        super(blockStateOutput, itemModelOutput, modelOutput);
    }

    public static String getBlockName(Block p_387523_) {
        Identifier resourcelocation = BuiltInRegistries.BLOCK.getKey(p_387523_);
        return resourcelocation.getPath();
    }

    public void createTranslucentCube(Block block) {
        this.createTrivialBlock(block, TexturedModel.CUBE.updateTexture(TextureMapping::forceAllTranslucent));
    }

    @Override
    public void createTintedDoublePlant(Block p_388276_) {
        Identifier resourcelocation = this.createFlatItemModelWithBlockTexture(p_388276_.asItem(), p_388276_, "_top");
        this.registerSimpleTintedItemModel(p_388276_, resourcelocation, new GrassColorSource());
        createDoublePlant(p_388276_, BlockModelGenerators.PlantType.TINTED);
    }

    public void createGrassLikeFrostBlock(Block block, Block dirt) {
        TextureMapping texturemapping = new TextureMapping()
                .put(TextureSlot.BOTTOM, getBlockTexture(dirt))
                .put(TextureSlot.TOP, getBlockTexture(block, "_top"))
                .put(TextureSlot.SIDE, getBlockTexture(block, "_side"))
                .copySlot(TextureSlot.BOTTOM, TextureSlot.PARTICLE)
                .put(FrostTextureMappings.OVERLAY, getBlockTexture(block, "_side_overlay"));

        this.blockStateOutput.accept(createSimpleBlock(block, BlockModelGenerators.plainVariant(FrostModelTemplates.GRASS_BLOCK.create(block, texturemapping, this.modelOutput))));
        this.registerSimpleTintedItemModel(block, ModelLocationUtils.getModelLocation(block), new GrassColorSource());
    }

    public void createGrassLikeFrostStoneBlock(Block block, Block dirt) {
        TextureMapping texturemapping = new TextureMapping()
                .put(TextureSlot.BOTTOM, getBlockTexture(dirt))
                .put(TextureSlot.TOP, getBlockTexture(block, "_top"))
                .put(TextureSlot.SIDE, getBlockTexture(block, "_side"))
                .copySlot(TextureSlot.BOTTOM, TextureSlot.PARTICLE)
                .copySlot(TextureSlot.SIDE, FrostTextureMappings.OVERLAY);
        this.blockStateOutput.accept(createSimpleBlock(block, BlockModelGenerators.plainVariant(FrostModelTemplates.GRASS_BLOCK.create(block, texturemapping, this.modelOutput))));
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
        MultiVariant resourcelocation1 = plainVariant(ModelTemplates.FARMLAND.create(ModelLocationUtils.getModelLocation(FrostBlocks.FROZEN_FARMLAND.get(), "_moist"), texturemapping1, this.modelOutput));
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(FrostBlocks.FROZEN_FARMLAND.get()).with(BlockModelGenerators.createEmptyOrFullDispatch(BlockStateProperties.MOISTURE, 7, resourcelocation1, resourcelocation)));
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

    public void createCropBlock(Block p_387553_, Property<Integer> p_386757_, int... p_388514_) {
        this.registerSimpleFlatItemModel(p_387553_.asItem());
        if (p_386757_.getPossibleValues().size() != p_388514_.length) {
            throw new IllegalArgumentException();
        } else {
            Int2ObjectMap<Identifier> int2objectmap = new Int2ObjectOpenHashMap<>();
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
                                                                                        p_387553_, "_" + p_387308_, ModelTemplates.CROP, TextureMapping::crop
                                                                                )
                                                                        )
                                                                );
                                                            }
                                                    )
                                    )
                    );
        }
    }

    public void createFrostFire() {
        MultiVariant multivariant = this.createFloorFireModels(FrostBlocks.FROST_FIRE.get());
        MultiVariant multivariant1 = this.createSideFireModels(FrostBlocks.FROST_FIRE.get());
        this.blockStateOutput.accept(MultiPartGenerator.multiPart(FrostBlocks.FROST_FIRE.get()).with(multivariant).with(multivariant1).with(multivariant1.with(Y_ROT_90)).with(multivariant1.with(Y_ROT_180)).with(multivariant1.with(Y_ROT_270)));
    }
}
