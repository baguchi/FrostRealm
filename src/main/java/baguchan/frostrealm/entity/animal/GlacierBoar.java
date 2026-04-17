package baguchan.frostrealm.entity.animal;

import baguchan.frostrealm.entity.brain.GlacierBoarAi;
import baguchan.frostrealm.registry.FrostEntities;
import baguchan.frostrealm.registry.FrostMemoryModuleType;
import baguchan.frostrealm.registry.FrostSensors;
import baguchan.frostrealm.registry.FrostTags;
import baguchi.bagus_lib.register.ModSensors;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.hoglin.HoglinBase;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GlacierBoar extends FrostAnimal {
    private static final EntityDimensions BABY_DIMENSIONS = FrostEntities.GLACIER_BOAR.get().getDimensions().scale(0.5F).withEyeHeight(0.75F);

    private static final Brain.Provider<GlacierBoar> BRAIN_PROVIDER = Brain.provider(
            List.of(MemoryModuleType.BREED_TARGET, MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.ANGRY_AT, MemoryModuleType.NEAREST_ATTACKABLE, MemoryModuleType.TEMPTING_PLAYER, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, MemoryModuleType.IS_TEMPTED, MemoryModuleType.HAS_HUNTING_COOLDOWN, MemoryModuleType.IS_PANICKING
                    , FrostMemoryModuleType.NEAREST_ENEMYS.get(), FrostMemoryModuleType.NEAREST_ENEMY_COUNT.get(), MemoryModuleType.AVOID_TARGET, FrostMemoryModuleType.NEAREST_FROST_BOARS.get(), FrostMemoryModuleType.FROST_BOAR_COUNT.get()),
            List.of(
                    ModSensors.SMART_NEAREST_LIVING_ENTITY_SENSOR.get(), SensorType.NEAREST_ADULT, SensorType.HURT_BY
                    , FrostSensors.FROST_BOAR_SENSOR.get(), FrostSensors.ENEMY_SENSOR.get()
            ),
            var0 -> GlacierBoarAi.getActivities()
    );


    private float runningScale;
    public AnimationState attackAnimation = new AnimationState();

    public GlacierBoar(EntityType<? extends GlacierBoar> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
        this.getNavigation().setCanFloat(true);
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.85F;
    }

    @Override
    public EntityDimensions getDefaultDimensions(Pose p_316516_) {
        return this.isBaby() ? BABY_DIMENSIONS : super.getDefaultDimensions(p_316516_);
    }

    @Override
    public boolean isFood(ItemStack p_248671_) {
        return p_248671_.is(FrostTags.Items.GLACIER_BOAR_FOODS);
    }

    @Override
    protected void customServerAiStep(ServerLevel serverLevel) {
        ProfilerFiller profiler = Profiler.get();
        profiler.push("boarBrain");
        this.getBrain().tick(serverLevel, this);
        profiler.pop();
        profiler.push("boarActivityUpdate");
        GlacierBoarAi.updateActivity(this);
        profiler.pop();
    }

    @Override
    protected Brain<GlacierBoar> makeBrain(Brain.Packed packedBrain) {
        return BRAIN_PROVIDER.makeBrain(this, packedBrain);
    }

    public Brain<GlacierBoar> getBrain() {
        return (Brain<GlacierBoar>) super.getBrain();
    }

    @javax.annotation.Nullable
    public LivingEntity getTarget() {
        return this.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes().add(Attributes.ATTACK_DAMAGE, 5.0F).add(Attributes.MAX_HEALTH, 50.0D).add(Attributes.FOLLOW_RANGE, 20.0D).add(Attributes.KNOCKBACK_RESISTANCE, 0.7F).add(Attributes.ATTACK_KNOCKBACK, 0.5D).add(Attributes.MOVEMENT_SPEED, 0.3D);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.level().isClientSide() ? null : GlacierBoarAi.getSoundForCurrentActivity(this).orElse(null);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_34548_) {
        return SoundEvents.HOGLIN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.HOGLIN_DEATH;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.HOSTILE_SWIM;
    }

    @Override
    protected SoundEvent getSwimSplashSound() {
        return SoundEvents.HOSTILE_SPLASH;
    }

    @Override
    public void tick() {
        if (level().isClientSide()) {
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


    public float getRunningScale() {
        return runningScale;
    }


    private boolean isDashing() {
        return this.getDeltaMovement().horizontalDistanceSqr() > 0.02D;
    }

    private boolean isMoving() {
        return this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6D;
    }

    public boolean doHurtTarget(ServerLevel serverLevel, Entity p_34491_) {
        if (!(p_34491_ instanceof LivingEntity)) {
            return false;
        } else {
            this.level().broadcastEntityEvent(this, (byte) 4);
            this.playSound(SoundEvents.HOGLIN_ATTACK, 1.0F, this.getVoicePitch());
            GlacierBoarAi.onHitTarget(this, (LivingEntity) p_34491_);
            return HoglinBase.hurtAndThrowTarget(serverLevel, this, (LivingEntity) p_34491_);
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
    public boolean hurtServer(ServerLevel serverLevel, DamageSource p_34503_, float p_34504_) {
        boolean flag = super.hurtServer(serverLevel, p_34503_, p_34504_);

        if (flag && p_34503_.getEntity() instanceof LivingEntity) {
            GlacierBoarAi.wasHurtBy(serverLevel, this, (LivingEntity) p_34503_.getEntity());
        }

        return flag;
    }


    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        GlacierBoar frostboar = FrostEntities.GLACIER_BOAR.get().create(p_146743_, EntitySpawnReason.BREEDING);
        if (frostboar != null) {
            frostboar.setPersistenceRequired();
        }

        return frostboar;
    }


    public boolean isAdult() {
        return !this.isBaby();
    }

    public boolean canAttack(LivingEntity p_186270_) {
        return !(p_186270_ instanceof GlacierBoar) && super.canAttack(p_186270_);
    }
}
