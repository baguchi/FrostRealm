package baguchan.frostrealm.capability;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.message.ChangeWeatherEvent;
import baguchan.frostrealm.message.ChangeWeatherTimeEvent;
import baguchan.frostrealm.registry.FrostDimensions;
import baguchan.frostrealm.registry.FrostWeathers;
import baguchan.frostrealm.utils.BlizzardUtils;
import baguchan.frostrealm.weather.FrostWeather;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FrostWeatherCapability implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {

	private int weatherTime;
	private int weatherCooldown;

	private float weatherLevel;
	private float oWeatherLevel;

	private float unstableLevel;

	public boolean needWeatherChanged;
	public boolean needWeatherCooldownChanged;
	private FrostWeather frostWeather;

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

	public void tick(Level level) {
		if (level.dimension() == FrostDimensions.FROSTREALM_LEVEL) {
			if (isWeatherActive()) {
				//If weather active
				setWetherTime(getWeatherTime() - 1);
				setWeatherLevel(getWeatherLevel(1.0F) + 0.05F);
			} else {
				if (isWeatherCooldownActive()) {
					//If weather not active and cooldown active
					setWeatherCooldown(getWeatherCooldown() - 1);
					if (getWeatherCooldown() <= 0) {
						unstableLevel += 0.5F * level.random.nextDouble();
						FrostWeather frostWeather = BlizzardUtils.makeRandomWeather(level.random, this.unstableLevel);

						setFrostWeather(frostWeather);
						if (!level.isClientSide()) {
							ChangeWeatherEvent message = new ChangeWeatherEvent(frostWeather);
							FrostRealm.CHANNEL.send(PacketDistributor.ALL.noArg(), message);
						}

						setWetherTime(((level.random.nextInt(5) + 5) * 60) * 20);
						needWeatherChanged = true;
					}
					setWeatherLevel(getWeatherLevel(1.0F) - 0.05F);
				} else {
					if (frostWeather == FrostWeathers.PURPLE_FOG.get()) {
						unstableLevel = 0;
					}
					//If weather not active and cooldown not active too
					setWeatherCooldown(((level.random.nextInt(10) + 10) * 60) * 20);
					needWeatherCooldownChanged = true;
				}
			}
		}

		if (!level.isClientSide()) {
			if (needWeatherChanged) {
				if (getWeatherLevel(1.0F) <= 1.0F) {
					ChangeWeatherTimeEvent message = new ChangeWeatherTimeEvent(getWeatherTime(), getWeatherCooldown(), getWeatherLevel(1.0F));
					FrostRealm.CHANNEL.send(PacketDistributor.ALL.noArg(), message);
					if (getWeatherLevel(1.0F) == 1.0F) {
						needWeatherChanged = false;
					}
				}
			}

			if (needWeatherCooldownChanged) {
				if (getWeatherLevel(1.0F) >= 0.0F) {
					ChangeWeatherTimeEvent message = new ChangeWeatherTimeEvent(getWeatherTime(), getWeatherCooldown(), getWeatherLevel(1.0F));
					FrostRealm.CHANNEL.send(PacketDistributor.ALL.noArg(), message);
					if (getWeatherLevel(1.0F) == 0.0F) {
						needWeatherCooldownChanged = false;
					}
				}
			}
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
			nbt.putString("FrostWeather", FrostWeathers.getRegistry().get().getKey(this.frostWeather).toString());
		}
		return nbt;
	}

	public void deserializeNBT(CompoundTag nbt) {
		this.weatherTime = nbt.getInt("WeatherTime");
		this.weatherCooldown = nbt.getInt("WeatherCooldown");
		this.unstableLevel = nbt.getFloat("UnstableLevel");
		FrostWeather frostWeather = FrostWeathers.getRegistry().get().getValue(ResourceLocation.tryParse(nbt.getString("FrostWeather")));
		if (frostWeather != null) {
			this.frostWeather = frostWeather;
		} else {
			this.frostWeather = FrostWeathers.BLIZZARD.get();
		}
	}
}