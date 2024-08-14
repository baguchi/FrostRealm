package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.world.gen.feature.*;
import baguchan.frostrealm.world.gen.feature.config.FloatingRockConfiguration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FrostFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, FrostRealm.MODID);


    public static final Supplier<Feature<LargeDripstoneConfiguration>> LARGE_ICE = FEATURES.register("large_ice", () -> new LargeIceFeature(LargeDripstoneConfiguration.CODEC));
    public static final Supplier<Feature<DripstoneClusterConfiguration>> ICE_CLUSTER = FEATURES.register("ice_cluster", () -> new SmallIceFeature(DripstoneClusterConfiguration.CODEC));
    public static final Supplier<Feature<BlockStateConfiguration>> BIG_ROCK = FEATURES.register("big_rock", () -> new BigRockFeature(BlockStateConfiguration.CODEC));
    public static final Supplier<Feature<BlockStateConfiguration>> SHAPE_CRYSTAL = FEATURES.register("shape_crystal", () -> new ShapeCrystalFeature(BlockStateConfiguration.CODEC));

    public static final Supplier<Feature<NoneFeatureConfiguration>> STONE_SPIKE = FEATURES.register("stone_spike", () -> new StoneSpikeFeature(NoneFeatureConfiguration.CODEC));
    public static final Supplier<Feature<FloatingRockConfiguration>> FLOATING_ROCK = FEATURES.register("floating_rock", () -> new FloatingRockFeature(FloatingRockConfiguration.CODEC));
}
