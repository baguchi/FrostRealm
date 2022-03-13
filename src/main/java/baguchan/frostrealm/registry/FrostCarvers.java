package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.world.caver.FRCanyonWorldCarver;
import baguchan.frostrealm.world.caver.FRCaveWorldCarver;
import net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FrostCarvers {
	public static final DeferredRegister<WorldCarver<?>> WORLD_CARVER = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, FrostRealm.MODID);

	public static final RegistryObject<WorldCarver<CaveCarverConfiguration>> FROSTREALM_CAVE = WORLD_CARVER.register("frostrealm_cave", () -> new FRCaveWorldCarver(CaveCarverConfiguration.CODEC));
	public static final RegistryObject<WorldCarver<CanyonCarverConfiguration>> FROSTREALM_CANYON = WORLD_CARVER.register("frostrealm_canyon", () -> new FRCanyonWorldCarver(CanyonCarverConfiguration.CODEC));
}
