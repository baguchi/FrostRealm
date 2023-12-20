package baguchan.frostrealm.capability;

import baguchan.frostrealm.registry.FrostWeathers;
import baguchan.frostrealm.weather.FrostWeather;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.HashMap;
import java.util.Map;

public class FrostWeatherSavedData extends SavedData {

	private static final String IDENTIFIER = "frost_weather_data";
	private int weatherTime;

	private float unstableLevel;
	private final ServerLevel serverLevel;
	private static Map<Level, FrostWeatherSavedData> dataMap = new HashMap<>();

	private FrostWeather frostWeather = FrostWeathers.BLIZZARD.get();

	public FrostWeatherSavedData(ServerLevel serverLevel) {
		this.serverLevel = serverLevel;
	}

	public void setUnstableLevel(float unstableLevel) {
		this.unstableLevel = unstableLevel;
	}

	public float getUnstableLevel() {
		return unstableLevel;
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
			ServerLevel overworld = world.getServer().getLevel(Level.OVERWORLD);
			FrostWeatherSavedData fromMap = dataMap.get(overworld);
			if (fromMap == null) {
				DimensionDataStorage storage = overworld.getDataStorage();
				FrostWeatherSavedData data = storage.computeIfAbsent(FrostWeatherSavedData.factory(serverLevel), IDENTIFIER);
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

	public static SavedData.Factory<FrostWeatherSavedData> factory(ServerLevel p_300199_) {
		return new SavedData.Factory<>(() -> {
			return new FrostWeatherSavedData(p_300199_);
		}, (p_296865_) -> {
			return load(p_300199_, p_296865_);
		}, DataFixTypes.SAVED_DATA_RAIDS);
	}

	public static FrostWeatherSavedData load(ServerLevel p_300199_, CompoundTag nbt) {
		FrostWeatherSavedData data = new FrostWeatherSavedData(p_300199_);
		data.weatherTime = nbt.getInt("WeatherTime");
		data.unstableLevel = nbt.getFloat("UnstableLevel");
		FrostWeather frostWeather = FrostWeathers.getRegistry().get(ResourceLocation.tryParse(nbt.getString("FrostWeather")));
		if (frostWeather != null) {
			data.frostWeather = frostWeather;
		} else {
			data.frostWeather = FrostWeathers.BLIZZARD.get();
		}
		return data;
	}

	@Override
	public CompoundTag save(CompoundTag p_77763_) {
		CompoundTag nbt = new CompoundTag();

		nbt.putInt("WeatherTime", this.weatherTime);
		nbt.putFloat("UnstableLevel", this.unstableLevel);
		if (this.frostWeather != null) {
			nbt.putString("FrostWeather", FrostWeathers.getRegistry().getKey(this.frostWeather).toString());
		}
		return nbt;
	}

	public boolean isWeatherCooldownActive() {
		return FrostWeathers.NOPE.get() == frostWeather;
	}
}