package baguchan.frostrealm.client.event;

import baguchan.frostrealm.capability.FrostWeatherManager;
import baguchan.frostrealm.data.resource.FrostDimensions;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;

@OnlyIn(Dist.CLIENT)
public class ClientFogEvent {


    @SubscribeEvent
    public void setFog(ViewportEvent.RenderFog event) {
        Entity entity = event.getCamera().getEntity();
        float partialTicks = (float) event.getPartialTick();
        if (entity.level().dimension() == FrostDimensions.FROSTREALM_LEVEL) {
            float weatherLevel = FrostWeatherManager.getWeatherLevel(partialTicks);

            if ((FrostWeatherManager.getFrostWeather().isUseFog() || FrostWeatherManager.getPrevFrostWeather().isUseFog())) {

                float near = event.getNearPlaneDistance();
                float far = event.getFarPlaneDistance();

                float density = FrostWeatherManager.getPrevFrostWeather().getDensity();

                float densityNew = FrostWeatherManager.getFrostWeather().getDensity();

                float total = densityNew * (weatherLevel) + (1 - weatherLevel) * density;

                near *= (total);
                far *= (total);

                event.setNearPlaneDistance(near);
                event.setFarPlaneDistance(far);
            }
        }
    }

    @SubscribeEvent
    public void setFogColor(ViewportEvent.ComputeFogColor event) {
        Entity entity = event.getCamera().getEntity();
        if (entity.level().dimension() == FrostDimensions.FROSTREALM_LEVEL) {
            float partialTicks = (float) event.getPartialTick();
            float weatherLevel = FrostWeatherManager.getWeatherLevel(partialTicks);
            if (weatherLevel > 0F) {


                float fogRed = event.getRed();
                float fogGreen = event.getGreen();
                float fogBlue = event.getBlue();

                float red = FrostWeatherManager.getPrevFrostWeather().getRed();
                float green = FrostWeatherManager.getPrevFrostWeather().getGreen();
                float blue = FrostWeatherManager.getPrevFrostWeather().getBlue();


                float red2 = FrostWeatherManager.getFrostWeather().getRed();
                float green2 = FrostWeatherManager.getFrostWeather().getGreen();
                float blue2 = FrostWeatherManager.getFrostWeather().getBlue();


                float redTotal = red2 * (weatherLevel) + (1 - weatherLevel) * red;
                float greenTotal = green2 * (weatherLevel) + (1 - weatherLevel) * green;
                float blueTotal = blue2 * (weatherLevel) + (1 - weatherLevel) * blue;

                fogRed += (redTotal - fogRed) * 0.3F;
                fogGreen += (greenTotal - fogGreen) * 0.3F;
                fogBlue += (blueTotal - fogBlue) * 0.3F;

                event.setRed(fogRed);
                event.setGreen(fogGreen);
                event.setBlue(fogBlue);
            }
        }
    }
}
