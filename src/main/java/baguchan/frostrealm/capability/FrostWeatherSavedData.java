package baguchan.frostrealm.capability;

import baguchan.frostrealm.message.ChangeAuroraMessage;
import baguchan.frostrealm.message.ChangeWeatherMessage;
import baguchan.frostrealm.registry.FrostDimensions;
import baguchan.frostrealm.registry.FrostWeathers;
import baguchan.frostrealm.utils.BlizzardUtils;
import baguchan.frostrealm.weather.FrostWeather;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;

public class FrostWeatherSavedData extends SavedData {

	private static final String IDENTIFIER = "frost_weather_data";
	private int weatherTime;
	private int weatherCooldown = 20000;

	private float unstableLevel;
	private float auroraLevel = 1.0F;
	private final ServerLevel serverLevel;
	private static Map<Level, FrostWeatherSavedData> dataMap = new HashMap<>();

    private FrostWeather frostWeather = FrostWeathers.NOPE.get();

	public FrostWeatherSavedData(ServerLevel serverLevel) {
		this.serverLevel = serverLevel;
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
		}, (p_296865_, t) -> {
			return load(p_300199_, p_296865_);
		}, DataFixTypes.SAVED_DATA_RAIDS);
	}

	public static FrostWeatherSavedData load(ServerLevel p_300199_, CompoundTag nbt) {
		FrostWeatherSavedData data = new FrostWeatherSavedData(p_300199_);
		data.weatherTime = nbt.getInt("WeatherTime");
		data.unstableLevel = nbt.getFloat("UnstableLevel");
		data.auroraLevel = nbt.getFloat("AuroraLevel");
		FrostWeather frostWeather = FrostWeathers.getRegistry().get(ResourceLocation.tryParse(nbt.getString("FrostWeather")));
		if (frostWeather != null) {
			data.frostWeather = frostWeather;
		} else {
            data.frostWeather = FrostWeathers.NOPE.get();
		}
        ChangeWeatherMessage message = new ChangeWeatherMessage(frostWeather);
		PacketDistributor.sendToPlayersInDimension(p_300199_, message);

		ChangeAuroraMessage message2 = new ChangeAuroraMessage(data.auroraLevel);
		PacketDistributor.sendToPlayersInDimension(p_300199_, message2);
		return data;
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

	@Override
	public CompoundTag save(CompoundTag p_77763_, HolderLookup.Provider p_323640_) {
		CompoundTag nbt = new CompoundTag();

		nbt.putInt("WeatherTime", this.weatherTime);
		nbt.putFloat("UnstableLevel", this.unstableLevel);
		nbt.putFloat("AuroraLevel", this.auroraLevel);
		if (this.frostWeather != null) {
			nbt.putString("FrostWeather", FrostWeathers.getRegistry().getKey(this.frostWeather).toString());
		}
		return nbt;
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