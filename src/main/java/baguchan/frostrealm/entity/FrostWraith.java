package baguchan.frostrealm.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class FrostWraith extends FrozenMonster {
	public FrostWraith(EntityType<? extends FrostWraith> p_33002_, Level p_33003_) {
		super(p_33002_, p_33003_);
		this.moveControl = new FlyingMoveControl(this, 10, false);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.05F, true));
		this.goalSelector.addGoal(3, new WaterAvoidingRandomFlyingGoal(this, 0.95D));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	protected PathNavigation createNavigation(Level p_29417_) {
		FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, p_29417_);
		flyingpathnavigation.setCanOpenDoors(false);
		flyingpathnavigation.setCanFloat(true);
		flyingpathnavigation.setCanPassDoors(true);
		return flyingpathnavigation;
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMobAttributes().add(Attributes.MAX_HEALTH, 24.0D).add(Attributes.ATTACK_DAMAGE, 4.0F).add(Attributes.ARMOR, 4.0F).add(Attributes.FLYING_SPEED, (double) 0.24F).add(Attributes.MOVEMENT_SPEED, (double) 0.21F).add(Attributes.FOLLOW_RANGE, 18.0F);
	}

	public void aiStep() {
		boolean flag = this.isSunBurnTick();

		if (flag) {
			this.setSecondsOnFire(8);
		}

		super.aiStep();
		this.calculateFlapping();
	}

	@Override
	protected boolean isSunBurnTick() {
		return super.isSunBurnTick();
	}

	private void calculateFlapping() {
		Vec3 vec3 = this.getDeltaMovement();
		if (!this.onGround && vec3.y < 0.0D) {
			this.setDeltaMovement(vec3.multiply(1.0D, 0.6D, 1.0D));
		}

	}

	@Override
	public void travel(Vec3 p_21280_) {
		this.flyingSpeed = this.getSpeed() * 0.21600002F;
		super.travel(p_21280_);
		this.flyingSpeed = 0.02F;
	}

	public boolean causeFallDamage(float p_148989_, float p_148990_, DamageSource p_148991_) {
		return false;
	}

	protected void checkFallDamage(double p_29370_, boolean p_29371_, BlockState p_29372_, BlockPos p_29373_) {
	}
}
