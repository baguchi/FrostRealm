package baguchan.frostrealm.entity;

import baguchan.frostrealm.api.animation.Animation;
import baguchan.frostrealm.api.animation.IAnimatable;
import baguchan.frostrealm.capability.FrostWeatherCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Random;

public class FrostBeaster extends Monster implements IAnimatable {
	private static final EntityDataAccessor<Integer> ANIMATION_ID = SynchedEntityData.defineId(FrostWolf.class, EntityDataSerializers.INT);

	public static final Animation NOT_BEG_ANIMATION = Animation.create(100);

	private int animationTick;

	public FrostBeaster(EntityType<? extends Monster> p_33002_, Level p_33003_) {
		super(p_33002_, p_33003_);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2F, true));
		this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.85D));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	@Override
	public void playAmbientSound() {
		super.playAmbientSound();
		this.setAnimation(NOT_BEG_ANIMATION);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.ATTACK_DAMAGE, 5.0F).add(Attributes.MAX_HEALTH, 24.0D).add(Attributes.FOLLOW_RANGE, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.26D);
	}

	public static boolean checkFrostBeasterSpawnRules(EntityType<? extends Monster> p_27578_, ServerLevelAccessor p_27579_, MobSpawnType p_27580_, BlockPos p_27581_, Random p_27582_) {
		FrostWeatherCapability capability = FrostWeatherCapability.get(p_27579_.getLevel()).orElse(null);
		if (capability != null && capability.isWeatherActive()) {
			return Monster.checkMonsterSpawnRules(p_27578_, p_27579_, p_27580_, p_27581_, p_27582_);
		}
		return false;
	}


	@Override
	public int getAnimationTick() {
		return animationTick;
	}

	@Override
	public void setAnimationTick(int tick) {
		this.animationTick = tick;
	}

	@Override
	public Animation getAnimation() {
		int index = this.entityData.get(ANIMATION_ID);
		if (index < 0) {
			return NO_ANIMATION;
		} else {
			return this.getAnimations()[index];
		}
	}

	@Override
	public Animation[] getAnimations() {
		return new Animation[]{
				NOT_BEG_ANIMATION
		};
	}

	@Override
	public void setAnimation(Animation animation) {
		this.entityData.set(ANIMATION_ID, ArrayUtils.indexOf(this.getAnimations(), animation));
	}
}
