package baguchan.frostrealm.entity;

import baguchan.frostrealm.entity.brain.FrostBoarAi;
import baguchan.frostrealm.registry.FrostEntities;
import baguchan.frostrealm.registry.FrostMemoryModuleType;
import baguchan.frostrealm.registry.FrostSensors;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.monster.hoglin.HoglinBase;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class FrostBoar extends FrostAnimal {

    protected static final ImmutableList<? extends SensorType<? extends Sensor<? super FrostBoar>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_ADULT, SensorType.HURT_BY
            , FrostSensors.FROST_BOAR_SENSOR.get(), FrostSensors.ENEMY_SENSOR.get());
    protected static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.BREED_TARGET, MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.NEAREST_ATTACKABLE, MemoryModuleType.TEMPTING_PLAYER, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, MemoryModuleType.IS_TEMPTED, MemoryModuleType.HAS_HUNTING_COOLDOWN, MemoryModuleType.IS_PANICKING
            , FrostMemoryModuleType.NEAREST_ENEMYS.get(), FrostMemoryModuleType.NEAREST_ENEMY_COUNT.get(), MemoryModuleType.AVOID_TARGET, FrostMemoryModuleType.NEAREST_FROST_BOARS.get(), FrostMemoryModuleType.FROST_BOAR_COUNT.get());

    private float runningScale;
    public AnimationState attackAnimation = new AnimationState();

    public FrostBoar(EntityType<? extends FrostBoar> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
        this.getNavigation().setCanFloat(true);
    }

    @Override
    protected void customServerAiStep() {
        this.level.getProfiler().push("boarBrain");
        this.getBrain().tick((ServerLevel) this.level, this);
        this.level.getProfiler().pop();
        this.level.getProfiler().push("boarActivityUpdate");
        FrostBoarAi.updateActivity(this);
        this.level.getProfiler().pop();
    }

    protected Brain.Provider<FrostBoar> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    protected Brain<?> makeBrain(Dynamic<?> p_35064_) {
        return FrostBoarAi.makeBrain(this, this.brainProvider().makeBrain(p_35064_));
    }

    public Brain<FrostBoar> getBrain() {
        return (Brain<FrostBoar>) super.getBrain();
    }

    @javax.annotation.Nullable
    public LivingEntity getTarget() {
        return this.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse((LivingEntity) null);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.ATTACK_DAMAGE, 5.0F).add(Attributes.MAX_HEALTH, 50.0D).add(Attributes.FOLLOW_RANGE, 20.0D).add(Attributes.KNOCKBACK_RESISTANCE, 0.7F).add(Attributes.ATTACK_KNOCKBACK, 0.5D).add(Attributes.MOVEMENT_SPEED, 0.3D);
    }

    @Override
    public void tick() {
        if (level.isClientSide()) {
            if ((this.isMoving())) {
                if (isDashing()) {
                    //idleAnimationState.stop();
                    runningScale = Mth.clamp(runningScale + 0.1F, 0, 1);
                } else {
                    //idleAnimationState.stop();
                    runningScale = Mth.clamp(runningScale - 0.1F, 0, 1);
                }
            } else {
                //idleAnimationState.startIfStopped(this.tickCount);
            }
        }
        super.tick();
    }

    @OnlyIn(Dist.CLIENT)
    public float getRunningScale() {
        return runningScale;
    }


    private boolean isDashing() {
        return this.getDeltaMovement().horizontalDistanceSqr() > 0.02D;
    }

    private boolean isMoving() {
        return this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6D;
    }

    public boolean doHurtTarget(Entity p_34491_) {
        if (!(p_34491_ instanceof LivingEntity)) {
            return false;
        } else {
            this.level.broadcastEntityEvent(this, (byte) 4);
            this.playSound(SoundEvents.HOGLIN_ATTACK, 1.0F, this.getVoicePitch());
            FrostBoarAi.onHitTarget(this, (LivingEntity) p_34491_);
            return HoglinBase.hurtAndThrowTarget(this, (LivingEntity) p_34491_);
        }
    }

    public void handleEntityEvent(byte p_219360_) {
        if (p_219360_ == 4) {
            this.attackAnimation.start(this.tickCount);
        } else {
            super.handleEntityEvent(p_219360_);
        }

    }

    @Override
    public boolean hurt(DamageSource p_34503_, float p_34504_) {
        boolean flag = super.hurt(p_34503_, p_34504_);
        if (this.level.isClientSide) {
            return false;
        } else {
            if (flag && p_34503_.getEntity() instanceof LivingEntity) {
                FrostBoarAi.wasHurtBy(this, (LivingEntity) p_34503_.getEntity());
            }

            return flag;
        }
    }


    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        FrostBoar frostboar = FrostEntities.FROST_BOAR.get().create(p_146743_);
        if (frostboar != null) {
            frostboar.setPersistenceRequired();
        }

        return frostboar;
    }


    public boolean isAdult() {
        return !this.isBaby();
    }

}
