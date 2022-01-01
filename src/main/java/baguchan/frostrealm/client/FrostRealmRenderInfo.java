package baguchan.frostrealm.client;

import baguchan.frostrealm.client.sky.FrostrealmWeatherRenderer;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.IWeatherRenderHandler;

import javax.annotation.Nullable;

public class FrostRealmRenderInfo extends DimensionSpecialEffects {
	private IWeatherRenderHandler weatherRenderer;

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
}