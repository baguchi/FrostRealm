package baguchan.frostrealm.entity;

import baguchan.frostrealm.entity.goal.RandomFlyingAndHoverGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class Auroray extends Monster {
	public Auroray(EntityType<? extends Auroray> p_33002_, Level p_33003_) {
		super(p_33002_, p_33003_);
		this.xpReward = 10;
		this.moveControl = new AurorayMoveControl(this);
		this.lookControl = new AurorayLookControl(this);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMobAttributes().add(Attributes.MAX_HEALTH, 40.0D).add(Attributes.ATTACK_DAMAGE, 6.0F).add(Attributes.MOVEMENT_SPEED, 0.24F).add(Attributes.FOLLOW_RANGE, 20.0F);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.15F, false));
		this.goalSelector.addGoal(3, new RandomFlyingAndHoverGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
	}

	@Override
	protected PathNavigation createNavigation(Level p_29417_) {
		FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, p_29417_);
		flyingpathnavigation.setCanOpenDoors(false);
		flyingpathnavigation.setCanFloat(false);
		flyingpathnavigation.setCanPassDoors(true);
		return flyingpathnavigation;
	}


	protected BodyRotationControl createBodyControl() {
		return new AurorayBodyRotationControl(this);
	}

	public boolean causeFallDamage(float p_147105_, float p_147106_, DamageSource p_147107_) {
		return false;
	}

	protected void checkFallDamage(double p_20809_, boolean p_20810_, BlockState p_20811_, BlockPos p_20812_) {
	}

	public void travel(Vec3 p_20818_) {
		if (this.isEffectiveAi() || this.isControlledByLocalInstance()) {
			if (this.isInWater()) {
				this.moveRelative(0.02F, p_20818_);
				this.move(MoverType.SELF, this.getDeltaMovement());
				this.setDeltaMovement(this.getDeltaMovement().scale((double) 0.8F));
			} else if (this.isInLava()) {
				this.moveRelative(0.02F, p_20818_);
				this.move(MoverType.SELF, this.getDeltaMovement());
				this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
			} else {
				BlockPos ground = new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ());
				float f = 0.91F;
				if (this.onGround) {
					f = this.level.getBlockState(ground).getFriction(this.level, ground, this) * 0.91F;
				}

				float f1 = 0.16277137F / (f * f * f);
				f = 0.91F;
				if (this.onGround) {
					f = this.level.getBlockState(ground).getFriction(this.level, ground, this) * 0.91F;
				}

				this.moveRelative(this.onGround ? 0.1F * f1 : 0.02F, p_20818_);
				this.move(MoverType.SELF, this.getDeltaMovement());
				this.setDeltaMovement(this.getDeltaMovement().scale((double) f));
			}
		}

		this.calculateEntityAnimation(this, false);
	}

	public boolean onClimbable() {
		return false;
	}

	class AurorayLookControl extends LookControl {
		public AurorayLookControl(Mob p_33235_) {
			super(p_33235_);
		}

		public void tick() {
		}
	}

	class AurorayMoveControl extends MoveControl {
		private float speed = 0.1F;

		public AurorayMoveControl(Mob p_33241_) {
			super(p_33241_);
		}

		public void tick() {
			if (Auroray.this.horizontalCollision) {
				this.speed = 0.1F;
			}

			double d0 = this.wantedX - Auroray.this.getX();
			double d1 = this.wantedY - Auroray.this.getY();
			double d2 = this.wantedZ - Auroray.this.getZ();
			double d3 = Math.sqrt(d0 * d0 + d2 * d2);
			if (Math.abs(d3) > (double) 1.0E-5F) {
				double d4 = 1.0D - Math.abs(d1 * (double) 0.7F) / d3;
				d0 *= d4;
				d2 *= d4;
				d3 = Math.sqrt(d0 * d0 + d2 * d2);
				double d5 = Math.sqrt(d0 * d0 + d2 * d2 + d1 * d1);
				float f = Auroray.this.getYRot();
				float f1 = (float) Mth.atan2(d2, d0);
				float f2 = Mth.wrapDegrees(Auroray.this.getYRot() + 90.0F);
				float f3 = Mth.wrapDegrees(f1 * (180F / (float) Math.PI));
				Auroray.this.setYRot(Mth.approachDegrees(f2, f3, 4.0F) - 90.0F);
				Auroray.this.yBodyRot = Auroray.this.getYRot();
				if (Mth.degreesDifferenceAbs(f, Auroray.this.getYRot()) < 3.0F) {
					this.speed = Mth.approach(this.speed, 1.8F, 0.005F * (1.8F / this.speed));
				} else {
					this.speed = Mth.approach(this.speed, 0.2F, 0.025F);
				}

				float f4 = (float) (-(Mth.atan2(-d1, d3) * (double) (180F / (float) Math.PI)));
				Auroray.this.setXRot(f4);
				float f5 = Auroray.this.getYRot() + 90.0F;
				double d6 = (double) (this.speed * this.speedModifier * Mth.cos(f5 * ((float) Math.PI / 180F))) * Math.abs(d0 / d5);
				double d7 = (double) (this.speed * this.speedModifier * Mth.sin(f5 * ((float) Math.PI / 180F))) * Math.abs(d2 / d5);
				double d8 = (double) (this.speed * this.speedModifier * Mth.sin(f4 * ((float) Math.PI / 180F))) * Math.abs(d1 / d5);
				Vec3 vec3 = Auroray.this.getDeltaMovement();
				Auroray.this.setDeltaMovement(vec3.add((new Vec3(d6, d8, d7)).subtract(vec3).scale(0.2D)));
			}

		}
	}

	class AurorayBodyRotationControl extends BodyRotationControl {
		public AurorayBodyRotationControl(Mob p_33216_) {
			super(p_33216_);
		}

		public void clientTick() {
			Auroray.this.yHeadRot = Auroray.this.yBodyRot;
			Auroray.this.yBodyRot = Auroray.this.getYRot();
		}
	}
}
