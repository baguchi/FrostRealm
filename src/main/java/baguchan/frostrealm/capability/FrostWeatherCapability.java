package baguchan.frostrealm.capability;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.message.ChangeWeatherTimeEvent;
import baguchan.frostrealm.registry.FrostDimensions;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
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

	private boolean isWeatherChanged;
	private boolean isWeatherCooldownChanged;

	public float getWeatherLevel(float level) {
		return Mth.lerp(level, this.oWeatherLevel, this.weatherLevel);
	}

	public void setWeatherLevel(float level) {
		float f = Mth.clamp(level, 0.0F, 1.0F);
		this.oWeatherLevel = f;
		this.weatherLevel = f;
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
				setWetherTime(getWeatherTime() - 1);
				setWeatherLevel(getWeatherLevel(1.0F) + 0.05F);
			} else {
				if (isWeatherCooldownActive()) {
					setWeatherCooldown(getWeatherCooldown() - 1);
					if (getWeatherCooldown() <= 0) {
						setWetherTime(((level.random.nextInt(5) + 5) * 60) * 20);
						isWeatherChanged = true;
					}
					setWeatherLevel(getWeatherLevel(1.0F) - 0.05F);
				} else {
					setWeatherCooldown(((level.random.nextInt(10) + 10) * 60) * 20);
					isWeatherCooldownChanged = true;
				}
			}
		}

		if (!level.isClientSide()) {
			if (isWeatherChanged) {
				if (getWeatherLevel(1.0F) <= 1.0F) {
					ChangeWeatherTimeEvent message = new ChangeWeatherTimeEvent(getWeatherTime(), getWeatherCooldown(), getWeatherLevel(1.0F));
					FrostRealm.CHANNEL.send(PacketDistributor.ALL.noArg(), message);
					if (getWeatherLevel(1.0F) == 1.0F) {
						isWeatherChanged = false;
					}
				}
			}

			if (isWeatherCooldownChanged) {
				if (getWeatherLevel(1.0F) >= 0.0F) {
					ChangeWeatherTimeEvent message = new ChangeWeatherTimeEvent(getWeatherTime(), getWeatherCooldown(), getWeatherLevel(1.0F));
					FrostRealm.CHANNEL.send(PacketDistributor.ALL.noArg(), message);
					if (getWeatherLevel(1.0F) == 0.0F) {
						isWeatherCooldownChanged = false;
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

	@Nonnull
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
		return (capability == FrostRealm.FROST_WEATHER_CAPABILITY) ? LazyOptional.of(() -> this).cast() : LazyOptional.empty();
	}

	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();

		nbt.putInt("WeatherTime", this.weatherTime);
		nbt.putInt("WeatherCooldown", this.weatherCooldown);

		return nbt;
	}

	public void deserializeNBT(CompoundTag nbt) {
		this.weatherTime = nbt.getInt("WeatherTime");
		this.weatherCooldown = nbt.getInt("WeatherCooldown");
	}
}