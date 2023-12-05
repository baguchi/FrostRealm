package baguchan.frostrealm.client.event;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostDimensions;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;

@OnlyIn(Dist.CLIENT)
public class ClientFogEvent {

    private float fogLevel;
    private float oFogLevel;

    @SubscribeEvent
    public void setFog(ViewportEvent.RenderFog event) {
        Entity entity = event.getCamera().getEntity();
        float partialTicks = (float) event.getPartialTick();
        if (entity.level().dimension() == FrostDimensions.FROSTREALM_LEVEL) {

			entity.level().getCapability(FrostRealm.FROST_WEATHER_CAPABILITY).ifPresent(cap -> {

				float weatherLevel = cap.getWeatherLevel(partialTicks);

				if (weatherLevel > 0F && cap.getFrostWeather() != null && cap.getFrostWeather().isUseFog()) {
					event.setNearPlaneDistance(20.0F * (cap.getFrostWeather().getDensity() / weatherLevel));
					event.setFarPlaneDistance(160.0F * (cap.getFrostWeather().getDensity() / weatherLevel));
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
		if (entity.level().dimension() == FrostDimensions.FROSTREALM_LEVEL) {
			entity.level().getCapability(FrostRealm.FROST_WEATHER_CAPABILITY).ifPresent(cap -> {
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
					fogRed = fogRed * f9 * red;
					fogGreen = fogGreen * f9 * green;
					fogBlue = fogBlue * f9 * blue;

					event.setRed(fogRed);
					event.setGreen(fogGreen);
					event.setBlue(fogBlue);
				}
			});
		}
	}
}
