package baguchan.frostrealm.entity;

import baguchan.frostrealm.capability.FrostWeatherSavedData;
import baguchan.frostrealm.registry.FrostWeathers;
import baguchan.frostrealm.utils.BlizzardUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public abstract class FrozenMonster extends Monster {
	public FrozenMonster(EntityType<? extends Monster> p_33002_, Level p_33003_) {
		super(p_33002_, p_33003_);
	}

	@Override
	public boolean hurt(DamageSource p_21016_, float p_21017_) {
        if (p_21016_.is(DamageTypeTags.IS_FIRE)) {
            float f = 2.0F;
            p_21017_ = Mth.clamp(p_21017_ * 1.25F + 1.0F, 0.0F, this.getMaxHealth());
        }

		return super.hurt(p_21016_, p_21017_);
	}

	public static boolean checkFrozenMonsterSpawnRules(EntityType<? extends FrozenMonster> p_27578_, ServerLevelAccessor p_27579_, MobSpawnType p_27580_, BlockPos p_27581_, RandomSource p_27582_) {
		return p_27579_.getDifficulty() != Difficulty.PEACEFUL
				&& (MobSpawnType.ignoresLightRequirements(p_27580_) || FrostWeatherSavedData.get(p_27579_.getLevel()).isWeatherActive() && FrostWeatherSavedData.get(p_27579_.getLevel()).getFrostWeather() == FrostWeathers.PURPLE_FOG && BlizzardUtils.isAffectWeather(p_27579_, p_27581_) && checkMonsterSpawnRules(p_27578_, p_27579_, p_27580_, p_27581_, p_27582_));
	}
}
