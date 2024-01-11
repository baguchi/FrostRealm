package baguchan.frostrealm.capability;

import baguchan.frostrealm.message.ChangeWeatherMessage;
import baguchan.frostrealm.registry.FrostDimensions;
import baguchan.frostrealm.registry.FrostWeathers;
import baguchan.frostrealm.utils.BlizzardUtils;
import baguchan.frostrealm.weather.FrostWeather;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;

public class FrostWeatherManager {
    private static float weatherLevel;
    private static float oWeatherLevel;

    private static float unstableLevel;
    private static FrostWeather frostWeather = FrostWeathers.NOPE.get();
    private static FrostWeather prevFrostWeather = FrostWeathers.NOPE.get();

    public static void tick(Level level) {
        if (!level.isClientSide()) {
            if (level.dimension() == FrostDimensions.FROSTREALM_LEVEL) {
                FrostWeatherSavedData frostWeatherData = FrostWeatherSavedData.get(level);
                if (frostWeatherData.isWeatherActive()) {
                    if (frostWeatherData.getFrostWeather() == FrostWeathers.PURPLE_FOG.get()) {
                        frostWeatherData.setUnstableLevel(0);
                    }
                    //If weather active
                    frostWeatherData.setWetherTime(frostWeatherData.getWeatherTime() - 1);
                } else {
                    if (frostWeatherData.isWeatherCooldownActive()) {
                        //If weather not active and cooldown active
                        frostWeatherData.setFrostWeather(FrostWeathers.NOPE.get());
                        if (frostWeatherData.getWeatherTime() <= 0) {
                            frostWeatherData.setUnstableLevel((float) (frostWeatherData.getUnstableLevel() + level.random.nextDouble() * 0.1F));
                            FrostWeather frostWeather = BlizzardUtils.makeRandomWeather(level.random, frostWeatherData.getUnstableLevel());

                            frostWeatherData.setFrostWeather(frostWeather);
                            ChangeWeatherMessage message = new ChangeWeatherMessage(frostWeather);
                            PacketDistributor.DIMENSION.with(level.dimension()).send(message);

                            frostWeatherData.setWetherTime(((level.random.nextInt(5) + 5) * 60) * 20);
                            frostWeatherData.setDirty();
                        }
                    } else {

                        //If wether not active and cooldown not active too
                        frostWeatherData.setWetherTime(((level.random.nextInt(5) + 10) * 60) * 20);
                        frostWeatherData.setFrostWeather(FrostWeathers.NOPE.get());
                        ChangeWeatherMessage message = new ChangeWeatherMessage(frostWeather);
                        PacketDistributor.DIMENSION.with(level.dimension()).send(message);
                        frostWeatherData.setDirty();
                    }
                }
            }
        }
    }


    public static void clientTick(ClientLevel level) {
        if (level.isClientSide()) {
            setoWeatherLevel(Mth.clamp(getRawWeatherLevel(), 0.0F, 1.0F));
            if (frostWeather != FrostWeathers.NOPE.get()) {
                setWeatherLevel(getRawWeatherLevel() + 0.02F);
                if (getWeatherLevel() == 1 && prevFrostWeather != frostWeather) {

                    prevFrostWeather = frostWeather;
                }
            } else {
                setWeatherLevel(getRawWeatherLevel() - 0.01F);
                if (getWeatherLevel() == 0) {
                    prevFrostWeather = FrostWeathers.NOPE.get();
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static float getoWeatherLevel() {
        return oWeatherLevel;
    }

    @OnlyIn(Dist.CLIENT)
    public static float getUnstableLevel() {
        return unstableLevel;
    }

    @OnlyIn(Dist.CLIENT)
    public static float getWeatherLevel() {
        return weatherLevel;
    }

    @OnlyIn(Dist.CLIENT)
    public static void setUnstableLevel(float unstableLevel) {
        FrostWeatherManager.unstableLevel = unstableLevel;
    }

    @OnlyIn(Dist.CLIENT)
    public static float getWeatherLevel(float level) {
        return Mth.lerp(level, oWeatherLevel, weatherLevel);
    }

    @OnlyIn(Dist.CLIENT)
    public static float getRawWeatherLevel() {
        return weatherLevel;
    }

    @OnlyIn(Dist.CLIENT)
    public static float getRawOWeatherLevel() {
        return oWeatherLevel;
    }

    @OnlyIn(Dist.CLIENT)
    public static void setWeatherLevel(float level) {
        float f = Mth.clamp(level, 0.0F, 1.0F);
        weatherLevel = f;
    }

    @OnlyIn(Dist.CLIENT)
    public static void setoWeatherLevel(float oWeatherLevel) {
        float f = Mth.clamp(oWeatherLevel, 0.0F, 1.0F);
        FrostWeatherManager.oWeatherLevel = f;
    }

    public static boolean isBadWeatherActive(Level level) {
        FrostWeatherSavedData frostWeatherData = FrostWeatherSavedData.get(level);
        return frostWeatherData != null && frostWeatherData.isWeatherActive();
    }

    @OnlyIn(Dist.CLIENT)
    public static FrostWeather getFrostWeather() {
        return frostWeather;
    }

    @OnlyIn(Dist.CLIENT)
    public static void setFrostWeather(FrostWeather weather) {
        frostWeather = weather;
    }

    @OnlyIn(Dist.CLIENT)
    public static FrostWeather getPrevFrostWeather() {
        return prevFrostWeather;
    }
}
