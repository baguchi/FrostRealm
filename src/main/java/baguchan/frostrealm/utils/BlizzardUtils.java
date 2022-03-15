package baguchan.frostrealm.utils;

import net.minecraft.core.BlockPos;
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
}