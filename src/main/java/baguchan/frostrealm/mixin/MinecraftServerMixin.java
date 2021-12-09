package baguchan.frostrealm.mixin;

import baguchan.frostrealm.registry.FrostDimensions;
import baguchan.frostrealm.world.FrostChunkGenerator;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.WritableRegistry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.storage.WorldData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
	@Shadow
	@Final
	protected RegistryAccess.RegistryHolder registryHolder;

	@Shadow
	public abstract WorldData getWorldData();

	@Inject(at = @At("HEAD"), method = "loadLevel()V")
	private void initServer(CallbackInfo info) {
		WritableRegistry<LevelStem> writableregistry = registryHolder.ownedRegistryOrThrow(Registry.LEVEL_STEM_REGISTRY);
		Registry<DimensionType> registry = registryHolder.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY);
		Registry<Biome> registry1 = registryHolder.registryOrThrow(Registry.BIOME_REGISTRY);
		Registry<NoiseGeneratorSettings> registry2 = registryHolder.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY);
		Registry<NormalNoise.NoiseParameters> registry3 = registryHolder.registryOrThrow(Registry.NOISE_REGISTRY);
		writableregistry.register(FrostDimensions.FROSTREALM_LEVELSTEM, new LevelStem(() -> {
			return registry.getOrThrow(FrostDimensions.FROSTREALM_TYPE);
		}, new FrostChunkGenerator(registry3, FrostDimensions.FROSTREALM_PRESET.biomeSource(registry1, true), this.getWorldData().worldGenSettings().seed(), () -> {
			return registry2.getOrThrow(NoiseGeneratorSettings.NETHER);
		})), Lifecycle.stable());
	}
}
