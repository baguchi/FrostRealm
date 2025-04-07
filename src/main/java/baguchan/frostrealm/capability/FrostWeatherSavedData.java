package baguchan.frostrealm.capability;

import baguchan.frostrealm.data.resource.FrostDimensions;
import baguchan.frostrealm.message.ChangeWeatherMessage;
import baguchan.frostrealm.registry.FrostWeathers;
import baguchan.frostrealm.utils.BlizzardUtils;
import baguchan.frostrealm.weather.FrostWeather;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;

public class FrostWeatherSavedData extends SavedData {

	public static final Codec<FrostWeatherSavedData> CODEC = RecordCodecBuilder.create(
			p_400930_ -> p_400930_.group(
							Codec.INT.fieldOf("weather_time").forGetter(p_400933_ -> p_400933_.weatherTime),
							Codec.INT.fieldOf("weather_cooldown").forGetter(p_400933_ -> p_400933_.weatherCooldown),
							Codec.FLOAT.fieldOf("unstable_level").forGetter(p_400933_ -> p_400933_.unstableLevel),
							Codec.FLOAT.fieldOf("aurora_level").forGetter(p_400933_ -> p_400933_.auroraLevel)
					)
					.apply(p_400930_, FrostWeatherSavedData::new)
	);
	private int weatherTime;
	private int weatherCooldown;

	private float unstableLevel;
	private float auroraLevel;
	private static Map<Level, FrostWeatherSavedData> dataMap = new HashMap<>();
	public static final SavedDataType<FrostWeatherSavedData> TYPE = new SavedDataType<>(
			"frost_weather_data",
			FrostWeatherSavedData::new,
			CODEC);

    private FrostWeather frostWeather = FrostWeathers.NOPE.get();

	public FrostWeatherSavedData() {
		this(0, 20000, 0, 1.0F);
	}

	public FrostWeatherSavedData(int weatherTime, int weatherCooldown, float unstableLevel, float auroraLevel) {
		this.weatherTime = weatherTime;
		this.weatherCooldown = weatherCooldown;
		this.unstableLevel = unstableLevel;
		this.auroraLevel = auroraLevel;
	}
	public void setUnstableLevel(float unstableLevel) {
		this.unstableLevel = Mth.clamp(unstableLevel, 0, 1F);
		if (this.unstableLevel != unstableLevel) {
			this.setDirty();
		}
	}

	public float getUnstableLevel() {
		return unstableLevel;
	}


	public void setAuroraLevel(float auroraLevel) {
		this.auroraLevel = Mth.clamp(auroraLevel, 0, 1F);
		if (this.auroraLevel != auroraLevel) {
			this.setDirty();
		}
	}

	public float getAuroraLevel() {
		return auroraLevel;
	}

	public boolean isWeatherActive() {
		return weatherTime > 0;
	}

	public int getWeatherTime() {
		return weatherTime;
	}

	public void setWetherTime(int time) {
		this.weatherTime = time;
	}


	public void setFrostWeather(FrostWeather frostWeather) {
		this.frostWeather = frostWeather;
	}

	public FrostWeather getFrostWeather() {
		return frostWeather;
	}

	public static FrostWeatherSavedData get(Level world) {
		if (world instanceof ServerLevel serverLevel) {
			ServerLevel overworld = world.getServer().getLevel(FrostDimensions.FROSTREALM_LEVEL);
			FrostWeatherSavedData fromMap = dataMap.get(overworld);
			if (fromMap == null) {
				DimensionDataStorage storage = overworld.getDataStorage();
				FrostWeatherSavedData data = storage.computeIfAbsent(TYPE);
				if (data != null) {
					data.setDirty();
				}
				dataMap.put(world, data);
				return data;
			}
			return fromMap;
		}
		return null;
	}



	public void tick(Level level) {
		if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
			if (level.dimension() == FrostDimensions.FROSTREALM_LEVEL) {
				if (isWeatherActive()) {
					if (frostWeather == FrostWeathers.PURPLE_FOG.get()) {
						unstableLevel = 0;
					}
					//If weather active
					setWetherTime(getWeatherTime() - 1);
				} else {
					if (isWeatherCooldownActive()) {
						//If weather not active and cooldown active
						setWeatherCooldown(getWeatherCooldown() - 1);
						if (getWeatherCooldown() <= 0) {
							unstableLevel += (float) (level.random.nextDouble() * 0.1F);
							FrostWeather frostWeather = BlizzardUtils.makeRandomWeather(level.random, this.unstableLevel);

							setFrostWeather(frostWeather);
							ChangeWeatherMessage message = new ChangeWeatherMessage(frostWeather);
							PacketDistributor.sendToPlayersInDimension(serverLevel, message);

							setWetherTime(((level.random.nextInt(5) + 5) * 60) * 20);
						}
					} else {

						//If wether not active and cooldown not active too
						setWeatherCooldown(((level.random.nextInt(5) + 10) * 60) * 20);
						setFrostWeather(FrostWeathers.NOPE.get());
						ChangeWeatherMessage message2 = new ChangeWeatherMessage(FrostWeathers.NOPE.get());
						PacketDistributor.sendToPlayersInDimension(serverLevel, message2);
					}
				}
			}
		}
	}

	public void setWeatherCooldown(int weatherCooldown) {
		this.weatherCooldown = weatherCooldown;
	}

	public int getWeatherCooldown() {
		return weatherCooldown;
	}

	public boolean isWeatherCooldownActive() {
		return FrostWeathers.NOPE.get() == frostWeather;
	}
}