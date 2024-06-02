package baguchan.frostrealm.entity.hostile;

import baguchan.frostrealm.entity.brain.WarpyAi;
import com.mojang.serialization.Dynamic;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class Warpy extends WarpedMonster {
    public AnimationState shoot = new AnimationState();
    public AnimationState flying = new AnimationState();

    public Warpy(EntityType<? extends Warpy> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> p_312201_) {
        return WarpyAi.makeBrain(this.brainProvider().makeBrain(p_312201_));
    }

    @Override
    public Brain<Warpy> getBrain() {
        return (Brain<Warpy>) super.getBrain();
    }

    @Override
    protected Brain.Provider<Warpy> brainProvider() {
        return Brain.provider(WarpyAi.MEMORY_TYPES, WarpyAi.SENSOR_TYPES);
    }

    public Optional<LivingEntity> getHurtBy() {
        return this.getBrain()
                .getMemory(MemoryModuleType.HURT_BY)
                .map(DamageSource::getEntity)
                .filter(p_321467_ -> p_321467_ instanceof LivingEntity)
                .map(p_321468_ -> (LivingEntity) p_321468_);
    }

    public boolean withinInnerCircleRange(Vec3 p_312331_) {
        Vec3 vec3 = this.blockPosition().getCenter();
        return p_312331_.closerThan(vec3, 4.0, 10.0);
    }

    @Override
    protected void customServerAiStep() {
        this.level().getProfiler().push("warpyBrain");
        this.getBrain().tick((ServerLevel) this.level(), this);
        this.level().getProfiler().popPush("warpyActivityUpdate");
        WarpyAi.updateActivity(this);
        this.level().getProfiler().pop();
        super.customServerAiStep();
    }


    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> p_312373_) {
        if (this.level().isClientSide() && DATA_POSE.equals(p_312373_)) {
            this.resetAnimations();
            Pose pose = this.getPose();
            switch (pose) {
                case SHOOTING:
                    this.shoot.startIfStopped(this.tickCount);
                    break;
            }
        }

        super.onSyncedDataUpdated(p_312373_);
    }

    private void resetAnimations() {
        this.shoot.stop();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            this.flying.startIfStopped(this.tickCount);
        }
    }

    @Override
    public boolean isFlapping() {
        return (float) this.tickCount % 20.0F == 0.0F;
    }

    protected PathNavigation createNavigation(Level p_29417_) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, p_29417_);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes().add(Attributes.MAX_HEALTH, 14.0D).add(Attributes.ARMOR, 4.0F).add(Attributes.FLYING_SPEED, 0.24F).add(Attributes.MOVEMENT_SPEED, 0.21F).add(Attributes.FOLLOW_RANGE, 32.0F);
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
}
