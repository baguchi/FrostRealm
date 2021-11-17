package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.world.gen.feature.BigRockFeature;
import baguchan.frostrealm.world.gen.feature.LargeIceFeature;
import baguchan.frostrealm.world.gen.feature.SmallIceFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SmallDripstoneConfiguration;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FrostFeatures {
	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, FrostRealm.MODID);

	public static final RegistryObject<Feature<LargeDripstoneConfiguration>> LARGE_ICE = FEATURES.register("large_ice", () -> new LargeIceFeature(LargeDripstoneConfiguration.CODEC));
	public static final RegistryObject<Feature<SmallDripstoneConfiguration>> SMALL_ICE = FEATURES.register("small_ice", () -> new SmallIceFeature(SmallDripstoneConfiguration.CODEC));
	public static final RegistryObject<Feature<BlockStateConfiguration>> BIG_ROCK = FEATURES.register("big_rock", () -> new BigRockFeature(BlockStateConfiguration.CODEC));
}
