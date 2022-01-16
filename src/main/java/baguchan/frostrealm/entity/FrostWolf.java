package baguchan.frostrealm.entity;

import baguchan.frostrealm.registry.FrostBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

import java.util.Random;

public class FrostWolf extends Wolf {
	public FrostWolf(EntityType<? extends Wolf> p_30369_, Level p_30370_) {
		super(p_30369_, p_30370_);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, (double) 0.3F).add(Attributes.MAX_HEALTH, 12.0D).add(Attributes.ATTACK_DAMAGE, 3.0D);
	}

	public static boolean checkFrostWolfSpawnRules(EntityType<? extends Animal> p_27578_, LevelAccessor p_27579_, MobSpawnType p_27580_, BlockPos p_27581_, Random p_27582_) {
		return p_27579_.getBlockState(p_27581_.below()).is(FrostBlocks.FROZEN_GRASS_BLOCK) && p_27579_.getRawBrightness(p_27581_, 0) > 8;
	}

	public void setTame(boolean p_30443_) {
		super.setTame(p_30443_);
		if (p_30443_) {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(24.0D);
			this.setHealth(this.getMaxHealth());
		} else {
			this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(12.0D);
		}

		this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(5.0D);
	}

	@Override
	public void playAmbientSound() {
		super.playAmbientSound();
	}
}
