package baguchan.frostrealm.weather;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;

import java.util.Optional;

public class FrostWeather {
	private final Properties properties;
	private final boolean useFog;

	public FrostWeather(Properties properties) {
		this.properties = properties;
		this.useFog = true;
	}

	public FrostWeather() {
		this.properties = new Properties(new FogProperties(0, 0, 0, 0), Optional.empty());
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

	public boolean isUseFog() {
		return useFog;
	}

	public static class Properties {
		private final FogProperties fogProperties;
		private final Optional<SoundEvent> sounds;

		public Properties(FogProperties fogProperties, Optional<SoundEvent> sounds) {
			this.fogProperties = fogProperties;
			this.sounds = sounds;
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
