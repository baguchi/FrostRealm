package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.world.gen.feature.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FrostFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, FrostRealm.MODID);


    public static final Supplier<Feature<LargeDripstoneConfiguration>> LARGE_ICE = FEATURES.register("large_ice", () -> new LargeIceFeature(LargeDripstoneConfiguration.CODEC));
    public static final Supplier<Feature<DripstoneClusterConfiguration>> ICE_CLUSTER = FEATURES.register("ice_cluster", () -> new SmallIceFeature(DripstoneClusterConfiguration.CODEC));
    public static final Supplier<Feature<BlockStateConfiguration>> BIG_ROCK = FEATURES.register("big_rock", () -> new BigRockFeature(BlockStateConfiguration.CODEC));
    public static final Supplier<Feature<BlockStateConfiguration>> SHAPE_CRYSTAL = FEATURES.register("shape_crystal", () -> new ShapeCrystalFeature(BlockStateConfiguration.CODEC));

    public static final Supplier<Feature<BlockStateConfiguration>> BIG_WARPED_ISLAND = FEATURES.register("big_warped_island", () -> new BigWarpedIslandFeature(BlockStateConfiguration.CODEC));
}
