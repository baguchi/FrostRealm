package baguchan.frostrealm.capability;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostDimensions;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FrostWeatherCapability implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {

	private int weatherTime;
	private int weatherCooldown;

	private float weatherLevel;
	private float oWeatherLevel;

	public float getWeatherLevel(float p_46723_) {
		return Mth.lerp(p_46723_, this.oWeatherLevel, this.weatherLevel);
	}

	public void setWeatherLevel(float p_46735_) {
		float f = Mth.clamp(p_46735_, 0.0F, 1.0F);
		this.oWeatherLevel = f;
		this.weatherLevel = f;
	}

	public void tick(Level level) {
		if (level.dimension() == FrostDimensions.FROSTREALM_LEVEL) {
			if (isWeatherActive()) {
				setWetherTime(getWeatherTime() - 1);
				setWeatherLevel(getWeatherLevel(1.0F) + 0.05F);
			} else {
				if (isWeatherCooldownActive()) {
					setWeatherCooldown(getWeatherTime() - 1);
					if (getWeatherCooldown() <= 0) {
						setWetherTime(6000 + (level.random.nextInt(5) + 10) * 60);
					}
					setWeatherLevel(getWeatherLevel(1.0F) - 0.05F);
				} else {
					setWeatherCooldown(6000 + (level.random.nextInt(10) + 10) * 60);
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