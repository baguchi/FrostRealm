package baguchan.frostrealm.client.sky;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.capability.FrostWeatherCapability;
import baguchan.frostrealm.registry.FrostSounds;
import baguchan.frostrealm.registry.FrostWeathers;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.client.IWeatherParticleRenderHandler;

import java.util.Random;

public class FrostrealmWeatherParticleRenderer implements IWeatherParticleRenderHandler {
	private int rainSoundTime;

	@Override
	public void render(int ticks, ClientLevel world, Minecraft mc, Camera activeRenderInfoIn) {
		world.getCapability(FrostRealm.FROST_WEATHER_CAPABILITY).ifPresent(cap -> {
			if (cap.getFrostWeather() == FrostWeathers.BLIZZARD.get()) {
				this.frostWeatherRender(cap, ticks, world, mc, activeRenderInfoIn);
			}
		});
	}

	public void frostWeatherRender(FrostWeatherCapability cap, int ticks, ClientLevel world, Minecraft mc, Camera activeRenderInfoIn) {
		float f = cap.getWeatherLevel(1.0F) / (Minecraft.useFancyGraphics() ? 1.0F : 2.0F);
		if (!(f <= 0.0F)) {
			Random random = new Random((long) ticks * 312987231L);
			LevelReader levelreader = mc.level;
			BlockPos blockpos = new BlockPos(activeRenderInfoIn.getPosition());
			BlockPos blockpos1 = null;
			int i = (int) (100.0F * f * f) / (mc.options.particles == ParticleStatus.DECREASED ? 2 : 1);

			for (int j = 0; j < i; ++j) {
				int k = random.nextInt(21) - 10;
				int l = random.nextInt(21) - 10;
				BlockPos blockpos2 = levelreader.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockpos.offset(k, 0, l));
				if (blockpos2.getY() > levelreader.getMinBuildHeight() && blockpos2.getY() <= blockpos.getY() + 10 && blockpos2.getY() >= blockpos.getY() - 10) {
					blockpos1 = blockpos2.below();
				}
			}

			if (blockpos1 != null && random.nextInt(5) + 5 < this.rainSoundTime++) {
				this.rainSoundTime = 0;
				if (blockpos1.getY() > blockpos.getY() + 1 && levelreader.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockpos).getY() > Mth.floor((float) blockpos.getY())) {
					mc.level.playLocalSound(blockpos1, FrostSounds.BLIZZARD_AMBIENT, SoundSource.WEATHER, 0.5F, 0.5F, false);
				} else {
					mc.level.playLocalSound(blockpos1, FrostSounds.BLIZZARD_AMBIENT, SoundSource.WEATHER, 2.0F, 1.0F, false);
				}
			}

		}
	}
}
