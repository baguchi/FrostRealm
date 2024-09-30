package baguchan.frostrealm.client.event;

import baguchan.frostrealm.capability.FrostWeatherManager;
import baguchan.frostrealm.registry.FrostDimensions;
import com.mojang.blaze3d.shaders.FogShape;
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

            if (weatherLevel > 0F && (FrostWeatherManager.getFrostWeather().isUseFog() || FrostWeatherManager.getPrevFrostWeather().isUseFog())) {
                float fogDensity = Mth.lerp((1.0F - weatherLevel), (float) FrostWeatherManager.getPrevFrostWeather().getDensity(), (float) FrostWeatherManager.getFrostWeather().getDensity());

                float near = event.getNearPlaneDistance();
                float far = event.getFarPlaneDistance();

                float nearLevel = Mth.clamp((1.0F / weatherLevel), 0.0F, 1.0F);

                near = near * ((1.0F - weatherLevel)) + near * fogDensity + nearLevel * 20F;
                far = far * ((1.0F - weatherLevel)) + far * fogDensity + nearLevel * 60F;


                event.setNearPlaneDistance(near);
                event.setFarPlaneDistance(far);
                RenderSystem.setShaderFogStart(event.getNearPlaneDistance());
                RenderSystem.setShaderFogEnd(event.getFarPlaneDistance());
                event.setFogShape(FogShape.SPHERE);
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
                float green = FrostWeatherManager.getPrevFrostWeather().getGreen();
                float blue = FrostWeatherManager.getPrevFrostWeather().getBlue();


                float red2 = FrostWeatherManager.getFrostWeather().getRed();
                float green2 = FrostWeatherManager.getFrostWeather().getGreen();
                float blue2 = FrostWeatherManager.getFrostWeather().getBlue();


                float redTotal = Mth.lerp((weatherLevel), (float) red, (float) red2);
                float greenTotal = Mth.lerp((weatherLevel), (float) green, (float) green2);
                float blueTotal = Mth.lerp((weatherLevel), (float) blue, (float) blue2);

                fogRed = fogRed * (weatherLevel) + fogRed * redTotal;
                fogGreen = fogGreen * (weatherLevel) + fogGreen * greenTotal;
                fogBlue = fogBlue * (weatherLevel) + fogBlue * blueTotal;

                event.setRed(fogRed);
                event.setGreen(fogGreen);
                event.setBlue(fogBlue);
            }
        }
    }
}
