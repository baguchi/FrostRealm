package baguchan.frostrealm.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryLookupCodec;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.function.Supplier;

public class FrostChunkGenerator extends NoiseBasedChunkGenerator {
	public static final Codec<FrostChunkGenerator> CODEC = RecordCodecBuilder.create((p_188643_) -> {
		return p_188643_.group(RegistryLookupCodec.create(Registry.NOISE_REGISTRY).forGetter((p_188716_) -> {
			return p_188716_.noises;
		}), BiomeSource.CODEC.fieldOf("biome_source").forGetter((p_188711_) -> {
			return p_188711_.biomeSource;
		}), Codec.LONG.fieldOf("seed").stable().orElse(SeedHolder.getSeed()).forGetter((p_188690_) -> {
			return p_188690_.seed;
		}), NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter((p_188652_) -> {
			return p_188652_.settings;
		})).apply(p_188643_, p_188643_.stable(FrostChunkGenerator::new));
	});
	private final Registry<NormalNoise.NoiseParameters> noises;
	protected final long seed;
	protected final Supplier<NoiseGeneratorSettings> settings;

	public FrostChunkGenerator(Registry<NormalNoise.NoiseParameters> p_188609_, BiomeSource p_188610_, long p_188611_, Supplier<NoiseGeneratorSettings> p_188612_) {
		super(p_188609_, p_188610_, p_188611_, p_188612_);
		this.noises = p_188609_;
		this.seed = p_188611_;
		this.settings = p_188612_;
	}

	protected Codec<? extends ChunkGenerator> codec() {
		return CODEC;
	}

	public ChunkGenerator withSeed(long p_64374_) {
		return new FrostChunkGenerator(this.noises, this.biomeSource.withSeed(p_64374_), p_64374_, this.settings);
	}

	public WeightedRandomList<MobSpawnSettings.SpawnerData> getMobsAt(Biome p_158433_, StructureFeatureManager p_158434_, MobCategory p_158435_, BlockPos p_158436_) {
		if (!p_158434_.hasAnyStructureAt(p_158436_)) {
			return super.getMobsAt(p_158433_, p_158434_, p_158435_, p_158436_);
		} else {
			WeightedRandomList<MobSpawnSettings.SpawnerData> spawns = net.minecraftforge.common.world.StructureSpawnManager.getStructureSpawns(p_158434_, p_158435_, p_158436_);
			if (spawns != null) return spawns;
			if (false) {//Forge: We handle these hardcoded cases above in StructureSpawnManager#getStructureSpawns, but allow for insideOnly to be changed and allow for creatures to be spawned in ones other than just the witch hut
				if (p_158434_.getStructureWithPieceAt(p_158436_, StructureFeature.SWAMP_HUT).isValid()) {
					if (p_158435_ == MobCategory.MONSTER) {
						return SwamplandHutFeature.SWAMPHUT_ENEMIES;
					}

					if (p_158435_ == MobCategory.CREATURE) {
						return SwamplandHutFeature.SWAMPHUT_ANIMALS;
					}
				}

				if (p_158435_ == MobCategory.MONSTER) {
					if (p_158434_.getStructureAt(p_158436_, StructureFeature.PILLAGER_OUTPOST).isValid()) {
						return PillagerOutpostFeature.OUTPOST_ENEMIES;
					}

					if (p_158434_.getStructureAt(p_158436_, StructureFeature.OCEAN_MONUMENT).isValid()) {
						return OceanMonumentFeature.MONUMENT_ENEMIES;
					}

					if (p_158434_.getStructureWithPieceAt(p_158436_, StructureFeature.NETHER_BRIDGE).isValid()) {
						return NetherFortressFeature.FORTRESS_ENEMIES;
					}
				}
			}

			return (p_158435_ == MobCategory.UNDERGROUND_WATER_CREATURE || p_158435_ == MobCategory.AXOLOTLS) && p_158434_.getStructureAt(p_158436_, StructureFeature.OCEAN_MONUMENT).isValid() ? MobSpawnSettings.EMPTY_MOB_LIST : super.getMobsAt(p_158433_, p_158434_, p_158435_, p_158436_);
		}
	}

	public void spawnOriginalMobs(WorldGenRegion p_64379_) {
		if (!this.settings.get().disableMobGeneration()) {
			ChunkPos chunkpos = p_64379_.getCenter();
			Biome biome = p_64379_.getBiome(chunkpos.getWorldPosition().atY(p_64379_.getMaxBuildHeight() - 1));
			WorldgenRandom worldgenrandom = new WorldgenRandom(new LegacyRandomSource(RandomSupport.seedUniquifier()));
			worldgenrandom.setDecorationSeed(p_64379_.getSeed(), chunkpos.getMinBlockX(), chunkpos.getMinBlockZ());
			NaturalSpawner.spawnMobsForChunkGeneration(p_64379_, biome, chunkpos, worldgenrandom);
		}
	}
}
