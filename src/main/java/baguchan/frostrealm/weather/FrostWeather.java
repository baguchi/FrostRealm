package baguchan.frostrealm.weather;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.biome.Biome;

import java.util.Optional;

public class FrostWeather {
	private final Properties properties;
	private final boolean useFog;

	public FrostWeather(Properties properties) {
		this.properties = properties;
		this.useFog = true;
	}

	public FrostWeather() {
		this.properties = new Properties(new FogProperties(1, 1, 1, 0.9F), Optional.empty(), Optional.empty());
		this.useFog = false;
	}

	public void tick(LivingEntity livingEntity) {

	}

	public float getRed() {
		return properties.fogProperties.red;
	}

	public float getGreen() {
		return properties.fogProperties.green;
	}

	public float getBlue() {
		return properties.fogProperties.blue;
	}

	public float getDensity() {
		return properties.fogProperties.density;
	}

	public Optional<SoundEvent> getSoundEvents() {
		return properties.sounds;
	}

	public Optional<TagKey<Biome>> getNonAffectableBiome() {
		return properties.biomeTagKey;
	}

	public boolean isUseFog() {
		return useFog;
	}

	public static class Properties {
		private final FogProperties fogProperties;
		private final Optional<SoundEvent> sounds;
		private final Optional<TagKey<Biome>> biomeTagKey;

		public Properties(FogProperties fogProperties, Optional<SoundEvent> sounds, Optional<TagKey<Biome>> biomeTagKey) {
			this.fogProperties = fogProperties;
			this.sounds = sounds;
			this.biomeTagKey = biomeTagKey;
		}
	}

	public static class FogProperties {
		private final float red;
		private final float green;
		private final float blue;
		private final float density;

		public FogProperties(float red, float green, float blue, float density) {
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.density = density;
		}
	}
}
