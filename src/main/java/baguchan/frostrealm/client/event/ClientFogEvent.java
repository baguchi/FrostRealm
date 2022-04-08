package baguchan.frostrealm.client.event;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostDimensions;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class ClientFogEvent {

	@SubscribeEvent
	public void setFog(EntityViewRenderEvent.RenderFogEvent event) {
		Entity entity = event.getCamera().getEntity();
		if (entity != null && entity.getLevel().dimension() == FrostDimensions.FROSTREALM_LEVEL) {
			entity.getLevel().getCapability(FrostRealm.FROST_WEATHER_CAPABILITY).ifPresent(cap -> {
				float weatherLevel = cap.getWeatherLevel(1.0F);
				if (weatherLevel > 0F && cap.getFrostWeather() != null && cap.getFrostWeather().isUseFog()) {
					event.setNearPlaneDistance(130.0F * (cap.getFrostWeather().getDensity() / weatherLevel));
					event.setFarPlaneDistance(160.0F * (cap.getFrostWeather().getDensity() / weatherLevel));
					RenderSystem.setShaderFogStart(event.getNearPlaneDistance());
					RenderSystem.setShaderFogEnd(event.getFarPlaneDistance());
					event.setCanceled(true);
				}
			});


		}
	}

	@SubscribeEvent
	public void setFogColor(EntityViewRenderEvent.FogColors event) {
		Entity entity = event.getCamera().getEntity();
		if (entity != null && entity.getLevel().dimension() == FrostDimensions.FROSTREALM_LEVEL) {
			entity.getLevel().getCapability(FrostRealm.FROST_WEATHER_CAPABILITY).ifPresent(cap -> {
				float weatherLevel = cap.getWeatherLevel(1.0F);
				if (weatherLevel > 0F && cap.getFrostWeather() != null && cap.getFrostWeather().isUseFog()) {
					float fogRed = event.getRed();
					float fogGreen = event.getGreen();
					float fogBlue = event.getBlue();

					event.setRed(Mth.clamp(((weatherLevel * cap.getFrostWeather().getRed())), 0.1F, 1));
					event.setGreen(Mth.clamp(((weatherLevel * cap.getFrostWeather().getGreen())), 0.1F, 1));
					event.setBlue(Mth.clamp(((weatherLevel * cap.getFrostWeather().getBlue())), 0.1F, 1));
				}
			});
		}
	}
}
