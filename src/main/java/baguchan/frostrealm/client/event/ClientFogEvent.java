package baguchan.frostrealm.client.event;

import baguchan.frostrealm.capability.FrostWeatherManager;
import baguchan.frostrealm.registry.FrostDimensions;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.util.Mth;
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

            if (weatherLevel > 0F) {
                float fogDensity = Mth.lerp((1.0F - weatherLevel), (float) FrostWeatherManager.getPrevFrostWeather().getDensity(), (float) FrostWeatherManager.getFrostWeather().getDensity());

                event.setNearPlaneDistance(20.0F * (fogDensity));
                event.setFarPlaneDistance(160.0F * (fogDensity));
                RenderSystem.setShaderFogStart(event.getNearPlaneDistance());
                RenderSystem.setShaderFogEnd(event.getFarPlaneDistance());
                event.setCanceled(true);
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
                ;
                float green = FrostWeatherManager.getPrevFrostWeather().getGreen();
                float blue = FrostWeatherManager.getPrevFrostWeather().getBlue();


                float red2 = FrostWeatherManager.getFrostWeather().getRed();
                float green2 = FrostWeatherManager.getFrostWeather().getGreen();
                float blue2 = FrostWeatherManager.getFrostWeather().getBlue();


                float redTotal = Mth.lerp((1.0F - weatherLevel), (float) red, (float) red2);
                float greenTotal = Mth.lerp((1.0F - weatherLevel), (float) green, (float) green2);
                float blueTotal = Mth.lerp((1.0F - weatherLevel), (float) blue, (float) blue2);

                fogRed = fogRed * (1.0F - weatherLevel) + fogRed * redTotal;
                fogGreen = fogGreen * (1.0F - weatherLevel) + fogGreen * greenTotal;
                fogBlue = fogBlue * (1.0F - weatherLevel) + fogBlue * blueTotal;

                event.setRed(fogRed);
                event.setGreen(fogGreen);
                event.setBlue(fogBlue);
            }
        }
    }
}
