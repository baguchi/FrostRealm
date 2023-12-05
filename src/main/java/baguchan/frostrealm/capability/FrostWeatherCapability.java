package baguchan.frostrealm.capability;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.message.ChangeWeatherMessage;
import baguchan.frostrealm.message.ChangeWeatherTimeMessage;
import baguchan.frostrealm.registry.FrostDimensions;
import baguchan.frostrealm.registry.FrostWeathers;
import baguchan.frostrealm.utils.BlizzardUtils;
import baguchan.frostrealm.weather.FrostWeather;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.capabilities.Capability;
import net.neoforged.neoforge.common.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.common.capabilities.ICapabilitySerializable;
import net.neoforged.neoforge.common.util.LazyOptional;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FrostWeatherCapability implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {

	private int weatherTime;
	private int weatherCooldown = 20000;

	private float weatherLevel;
	private float oWeatherLevel;

	private float unstableLevel;

	private FrostWeather frostWeather = FrostWeathers.BLIZZARD.get();

	public float getWeatherLevel(float level) {
		return Mth.lerp(level, this.oWeatherLevel, this.weatherLevel);
	}

	public void setWeatherLevel(float level) {
		float f = Mth.clamp(level, 0.0F, 1.0F);
		this.oWeatherLevel = f;
		this.weatherLevel = f;
	}


	public void setUnstableLevel(float unstableLevel) {
		this.unstableLevel = unstableLevel;
	}

	public float getUnstableLevel() {
		return unstableLevel;
	}
	public static LazyOptional<FrostWeatherCapability> get(Level world) {
		return world.getCapability(FrostRealm.FROST_WEATHER_CAPABILITY);
	}

	public static boolean isBadWeatherActive(Level world) {
		return get(world).filter(FrostWeatherCapability::isWeatherActive).isPresent();
	}

	public static boolean isBlizzardActive(Level world) {
		return get(world).filter(frostWeatherCapability -> {
			return frostWeatherCapability.getFrostWeather() == FrostWeathers.BLIZZARD.get();
		}).isPresent();
	}

	public static boolean isPurpleFog(Level world) {
		return get(world).filter(frostWeatherCapability -> {
			return frostWeatherCapability.getFrostWeather() == FrostWeathers.PURPLE_FOG.get();
		}).isPresent();
	}

	public void tick(Level level) {
		if (!level.isClientSide()) {
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
							unstableLevel += level.random.nextDouble() * 0.1F;
							FrostWeather frostWeather = BlizzardUtils.makeRandomWeather(level.random, this.unstableLevel);

							setFrostWeather(frostWeather);
							ChangeWeatherMessage message = new ChangeWeatherMessage(frostWeather);
							FrostRealm.CHANNEL.send(PacketDistributor.DIMENSION.with(level::dimension), message);

							setWetherTime(((level.random.nextInt(5) + 5) * 60) * 20);

							ChangeWeatherTimeMessage message2 = new ChangeWeatherTimeMessage(weatherTime, weatherCooldown);
							FrostRealm.CHANNEL.send(PacketDistributor.DIMENSION.with(level::dimension), message2);
						}
					} else {

						//If wether not active and cooldown not active too
						setWeatherCooldown(((level.random.nextInt(5) + 10) * 60) * 20);

						ChangeWeatherTimeMessage message2 = new ChangeWeatherTimeMessage(weatherTime, weatherCooldown);
						FrostRealm.CHANNEL.send(PacketDistributor.DIMENSION.with(level::dimension), message2);
					}
				}
			}
		}
	}


	public void clientTick(ClientLevel level) {
		if (level.isClientSide()) {
			if (isWeatherActive()) {
				this.weatherLevel += 0.02F;

			} else if (isWeatherCooldownActive()) {
				this.weatherLevel -= 0.01F;
			}
			this.oWeatherLevel = this.weatherLevel;

			this.weatherLevel = Mth.clamp(this.weatherLevel, 0.0F, 1.0F);
			setWeatherLevel(weatherLevel);
		}
	}

	public boolean isWeatherActive() {
		return weatherTime > 0;
	}

	public boolean isWeatherCooldownActive() {
		return weatherCooldown > 0;
	}

	public int getWeatherTime() {
		return weatherTime;
	}

	public void setWetherTime(int time) {
		this.weatherTime = time;
	}

	public int getWeatherCooldown() {
		return weatherCooldown;
	}

	public void setWeatherCooldown(int time) {
		this.weatherCooldown = time;
	}

	public void setFrostWeather(FrostWeather frostWeather) {
		this.frostWeather = frostWeather;
	}

	public FrostWeather getFrostWeather() {
		return frostWeather;
	}

	@Nonnull
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
		return (capability == FrostRealm.FROST_WEATHER_CAPABILITY) ? LazyOptional.of(() -> this).cast() : LazyOptional.empty();
	}

	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();

		nbt.putInt("WeatherTime", this.weatherTime);
		nbt.putInt("WeatherCooldown", this.weatherCooldown);
		nbt.putFloat("UnstableLevel", this.unstableLevel);
		if (this.frostWeather != null) {
            nbt.putString("FrostWeather", FrostWeathers.getRegistry().getKey(this.frostWeather).toString());
		}
		return nbt;
	}

	public void deserializeNBT(CompoundTag nbt) {
		this.weatherTime = nbt.getInt("WeatherTime");
		this.weatherCooldown = nbt.getInt("WeatherCooldown");
		this.unstableLevel = nbt.getFloat("UnstableLevel");
        FrostWeather frostWeather = FrostWeathers.getRegistry().get(ResourceLocation.tryParse(nbt.getString("FrostWeather")));
		if (frostWeather != null) {
			this.frostWeather = frostWeather;
		} else {
			this.frostWeather = FrostWeathers.BLIZZARD.get();
		}
	}
}