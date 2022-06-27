package baguchan.frostrealm.client;

import baguchan.frostrealm.client.sky.FrostrealmSkyRenderer;
import baguchan.frostrealm.client.sky.FrostrealmWeatherParticleRenderer;
import baguchan.frostrealm.client.sky.FrostrealmWeatherRenderer;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.ISkyRenderHandler;
import net.minecraftforge.client.IWeatherParticleRenderHandler;
import net.minecraftforge.client.IWeatherRenderHandler;

import javax.annotation.Nullable;

public class FrostRealmRenderInfo extends DimensionSpecialEffects {
	private IWeatherRenderHandler weatherRenderer;
	private IWeatherParticleRenderHandler weatherParticleRenderHandler = null;

	private ISkyRenderHandler skyRenderHandler = null;

	public FrostRealmRenderInfo(float cloudHeight, boolean placebo, SkyType fogType, boolean brightenLightMap, boolean entityLightingBottomsLit) {
		super(cloudHeight, placebo, fogType, brightenLightMap, entityLightingBottomsLit);
	}

	@Override
	public Vec3 getBrightnessDependentFogColor(Vec3 biomeFogColor, float daylight) {
		return biomeFogColor.multiply(daylight * 0.94F + 0.06F, (daylight * 0.94F + 0.06F), (daylight * 0.91F + 0.09F));
	}

	@Override
	public boolean isFoggyAt(int p_108874_, int p_108875_) {
		return false;
	}

	@Nullable
	@Override
	public IWeatherRenderHandler getWeatherRenderHandler() {
		if (weatherRenderer == null)
			weatherRenderer = new FrostrealmWeatherRenderer();
		return weatherRenderer;
	}

	@Nullable
	@Override
	public IWeatherParticleRenderHandler getWeatherParticleRenderHandler() {
		if (weatherParticleRenderHandler == null)
			weatherParticleRenderHandler = new FrostrealmWeatherParticleRenderer();
		return weatherParticleRenderHandler;
	}

	@org.jetbrains.annotations.Nullable
	@Override
	public ISkyRenderHandler getSkyRenderHandler() {
		if (skyRenderHandler == null)
			skyRenderHandler = new FrostrealmSkyRenderer();
		return skyRenderHandler;
	}
}