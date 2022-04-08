package baguchan.frostrealm.weather;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class FrostWeather extends ForgeRegistryEntry<FrostWeather> {
	private final Properties properties;

	public FrostWeather(Properties properties) {
		this.properties = properties;
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

	public static class Properties {
		private final FogProperties fogProperties;

		public Properties(FogProperties fogProperties) {
			this.fogProperties = fogProperties;
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
