package baguchan.frostrealm.capability;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.message.AuroraLevelMessage;
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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class FrostWeatherCapability implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {

	private int weatherTime;
	private int weatherCooldown = 20000;

	private float weatherLevel;
	private float oWeatherLevel;

	private float unstableLevel;

	private float auroraLevel = 1.0F;
	private int auroraRepairTick;

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

	public void setAuroraLevel(float auroraLevel) {
		this.auroraLevel = Mth.clamp(auroraLevel, 0.0F, 1.0F);
	}

	public float getAuroraLevel() {
		return auroraLevel;
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

	public static float getAuroraLevel(Level world) {
		Optional<FrostWeatherCapability> optional = get(world).filter(frostWeatherCapability -> {
			return true;
		});


		if (optional.isPresent()) {
			return get(world).filter(frostWeatherCapability -> {
				return true;
			}).get().getAuroraLevel();
		}

		return 0;
	}

	public void tick(Level level) {
		if (!level.isClientSide()) {
			if (level.dimension() == FrostDimensions.FROSTREALM_LEVEL) {
				if (this.auroraLevel < 1.0F) {
					if (this.auroraRepairTick > 0) {
						--this.auroraRepairTick;
					} else {
						this.auroraRepairTick = 400;
						this.setAuroraLevel(this.getAuroraLevel() + 0.01F);
						AuroraLevelMessage message = new AuroraLevelMessage(this.getAuroraLevel());
						FrostRealm.CHANNEL.send(PacketDistributor.DIMENSION.with(level::dimension), message);

					}
				}


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
							unstableLevel += level.random.nextDouble() * (1 - auroraLevel);
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
		nbt.putFloat("AuroraLevel", this.auroraLevel);
		nbt.putInt("AuroraRepairTick", this.auroraRepairTick);
		if (this.frostWeather != null) {
			nbt.putString("FrostWeather", FrostWeathers.getRegistry().get().getKey(this.frostWeather).toString());
		}
		return nbt;
	}

	public void deserializeNBT(CompoundTag nbt) {
		this.weatherTime = nbt.getInt("WeatherTime");
		this.weatherCooldown = nbt.getInt("WeatherCooldown");
		this.unstableLevel = nbt.getFloat("UnstableLevel");
		this.auroraLevel = nbt.getFloat("AuroraLevel");
		this.auroraRepairTick = nbt.getInt("AuroraRepairTick");
		FrostWeather frostWeather = FrostWeathers.getRegistry().get().getValue(ResourceLocation.tryParse(nbt.getString("FrostWeather")));
		if (frostWeather != null) {
			this.frostWeather = frostWeather;
		} else {
			this.frostWeather = FrostWeathers.BLIZZARD.get();
		}
	}
}