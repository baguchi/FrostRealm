package baguchan.frostrealm.client.event;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostDimensions;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class ClientFogEvent {

    private float fogLevel;
    private float oFogLevel;

    @SubscribeEvent
    public void setFog(ViewportEvent.RenderFog event) {
        Entity entity = event.getCamera().getEntity();
        float partialTicks = (float) event.getPartialTick();
        if (entity.getLevel().dimension() == FrostDimensions.FROSTREALM_LEVEL) {
            if (entity.getY() < 32 && entity.getY() > -16) {
                if (fogLevel < 1F) {
                    fogLevel += 0.01F;
                } else {
                    fogLevel = 1F;
                }
            } else {
                if (fogLevel > 0F) {
                    fogLevel -= 0.01F;
                } else {
                    fogLevel = 0F;
                }
            }
            oFogLevel = fogLevel;

            float currentFogLevel = Mth.lerp(partialTicks, this.oFogLevel, this.fogLevel);


            entity.getLevel().getCapability(FrostRealm.FROST_WEATHER_CAPABILITY).ifPresent(cap -> {

                float weatherLevel = cap.getWeatherLevel(partialTicks);

                if (weatherLevel > 0F && cap.getFrostWeather() != null && cap.getFrostWeather().isUseFog()) {
                    event.setNearPlaneDistance(20.0F * (cap.getFrostWeather().getDensity() / weatherLevel) + (20.0F * 0.5F / currentFogLevel));
                    event.setFarPlaneDistance(160.0F * (cap.getFrostWeather().getDensity() / weatherLevel) + (160.0F * 0.5F / currentFogLevel));
                    RenderSystem.setShaderFogStart(event.getNearPlaneDistance());
                    RenderSystem.setShaderFogEnd(event.getFarPlaneDistance());
                    event.setCanceled(true);
                } else if (currentFogLevel > 0F) {
                    event.setNearPlaneDistance(20.0F * 0.5F / currentFogLevel);
                    event.setFarPlaneDistance(160.0F * 0.5F / currentFogLevel);
                    RenderSystem.setShaderFogStart(event.getNearPlaneDistance());
                    RenderSystem.setShaderFogEnd(event.getFarPlaneDistance());
                    event.setCanceled(true);
                }
			});


		}
	}

	@SubscribeEvent
	public void setFogColor(ViewportEvent.ComputeFogColor event) {
		Entity entity = event.getCamera().getEntity();
		if (entity.getLevel().dimension() == FrostDimensions.FROSTREALM_LEVEL) {
			entity.getLevel().getCapability(FrostRealm.FROST_WEATHER_CAPABILITY).ifPresent(cap -> {
				float partialTicks = (float) event.getPartialTick();
				float weatherLevel = cap.getWeatherLevel(partialTicks);
				if (weatherLevel > 0F && cap.getFrostWeather() != null && cap.getFrostWeather().isUseFog()) {
					float fogRed = event.getRed();
					float fogGreen = event.getGreen();
					float fogBlue = event.getBlue();

					float red = weatherLevel * cap.getFrostWeather().getRed();
					float green = weatherLevel * cap.getFrostWeather().getGreen();
					float blue = weatherLevel * cap.getFrostWeather().getBlue();

					float f9 = Math.min(1.0F / fogRed, Math.min(1.0F / fogGreen, 1.0F / fogBlue));
					fogRed = fogRed * (1.0F - red) + fogRed * f9 * red;
					fogGreen = fogGreen * (1.0F - green) + fogGreen * f9 * green;
					fogBlue = fogBlue * (1.0F - blue) + fogBlue * f9 * blue;

					event.setRed(fogRed);
					event.setGreen(fogGreen);
					event.setBlue(fogBlue);
				}
			});
		}
	}
}
