package baguchan.frostrealm.entity.hostile;

import baguchan.frostrealm.utils.LookUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
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
import net.minecraft.world.phys.Vec3;

public class FrostWraith extends WarpedMonster {
	public FrostWraith(EntityType<? extends FrostWraith> p_33002_, Level p_33003_) {
		super(p_33002_, p_33003_);
		this.moveControl = new FlyingMoveControl(this, 10, true);
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
		return Monster.createMobAttributes().add(Attributes.MAX_HEALTH, 24.0D).add(Attributes.ATTACK_DAMAGE, 4.0F).add(Attributes.ARMOR, 4.0F).add(Attributes.FLYING_SPEED, 0.24F).add(Attributes.MOVEMENT_SPEED, 0.21F).add(Attributes.FOLLOW_RANGE, 12.0F);
	}

	public void aiStep() {
		super.aiStep();
	}

	@Override
	public void tick() {
		super.tick();
	}

	@Override
	public void travel(Vec3 p_21280_) {
        if (this.isControlledByLocalInstance()) {
            if (this.isInWater()) {
                this.moveRelative(0.02F, p_21280_);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale((double) 0.8F));
            } else if (this.isInLava()) {
                this.moveRelative(0.02F, p_21280_);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
            } else {
                this.moveRelative(this.getSpeed() * 0.21F, p_21280_);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale((double) 0.91F));
            }
        }

        this.calculateEntityAnimation(false);
    }

	@Override
	public boolean hasLineOfSight(Entity p_147185_) {
		if (p_147185_.level() == this.level() && !LookUtils.isLookingAtYouTest(this, p_147185_)) {
			return false;
		}
		return super.hasLineOfSight(p_147185_);
	}


	public int getHeadRotSpeed() {
		return 1;
	}
}
