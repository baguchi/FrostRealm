package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.world.gen.feature.BigRockFeature;
import baguchan.frostrealm.world.gen.feature.LargeIceFeature;
import baguchan.frostrealm.world.gen.feature.SmallIceFeature;
import baguchan.frostrealm.world.gen.feature.WarpedIslandFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FrostFeatures {
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, FrostRealm.MODID);


	public static final RegistryObject<Feature<LargeDripstoneConfiguration>> LARGE_ICE = FEATURES.register("large_ice", () -> new LargeIceFeature(LargeDripstoneConfiguration.CODEC));
	public static final RegistryObject<Feature<DripstoneClusterConfiguration>> ICE_CLUSTER = FEATURES.register("ice_cluster", () -> new SmallIceFeature(DripstoneClusterConfiguration.CODEC));
	public static final RegistryObject<Feature<BlockStateConfiguration>> BIG_ROCK = FEATURES.register("big_rock", () -> new BigRockFeature(BlockStateConfiguration.CODEC));

	public static final RegistryObject<Feature<BlockStateConfiguration>> WARPED_ISLAND = FEATURES.register("warped_island", () -> new WarpedIslandFeature(BlockStateConfiguration.CODEC));

}
