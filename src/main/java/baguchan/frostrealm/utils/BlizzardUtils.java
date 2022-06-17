package baguchan.frostrealm.utils;

import baguchan.frostrealm.registry.FrostWeathers;
import baguchan.frostrealm.weather.FrostWeather;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.levelgen.Heightmap;

public class BlizzardUtils {
	public static boolean isAffectWeather(LivingEntity entity) {
		BlockPos blockpos = entity.blockPosition();
		return entity.level.canSeeSky(blockpos) && entity.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockpos).getY() <= blockpos.getY();
	}

	public static boolean isAffectWeather(LivingEntity entity, BlockPos blockpos) {
		return entity.level.canSeeSky(blockpos) && entity.level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockpos).getY() <= blockpos.getY();
	}

	public static FrostWeather makeRandomWeather(RandomSource random, float unstableLevel) {
		if (random.nextFloat() < unstableLevel) {
			return FrostWeathers.PURPLE_FOG.get();
		} else {
			return FrostWeathers.BLIZZARD.get();
		}
	}
}
