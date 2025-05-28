package baguchan.frostrealm.entity.boss;

import baguchan.frostrealm.entity.brain.SeekerAi;
import baguchan.frostrealm.registry.FrostItems;
import baguchan.frostrealm.registry.FrostSounds;
import com.mojang.serialization.Dynamic;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

public class Seeker extends Monster {
    public int attackAnimationTick;
    public static final int attackAnimationLength = (int) (20 * 2);
    public static final int attackAnimationActionPoint = (int) ((int) (20 * 0.55));


    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState preAttackAnimationState = new AnimationState();
    public final AnimationState stopAttackAnimationState = new AnimationState();
    public final AnimationState deathAnimationState = new AnimationState();


    public Seeker(EntityType<? extends Seeker> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        this.xpReward = 200;
        this.getNavigation().setCanFloat(true);

    }

    @Override
    protected void customServerAiStep(ServerLevel serverLevel) {
        ProfilerFiller profiler = Profiler.get();
        profiler.push("boarBrain");
        this.getBrain().tick(serverLevel, this);
        profiler.pop();
        profiler.push("seekerActivityUpdate");
        SeekerAi.updateActivity(this);
        profiler.pop();
    }

    protected Brain.Provider<Seeker> brainProvider() {
        return Brain.provider(SeekerAi.MEMORY_TYPES, SeekerAi.SENSOR_TYPES);
    }

    protected Brain<?> makeBrain(Dynamic<?> p_35064_) {
        return SeekerAi.makeBrain(this, this.brainProvider().makeBrain(p_35064_));
    }

    public Brain<Seeker> getBrain() {
        return (Brain<Seeker>) super.getBrain();
    }

    @javax.annotation.Nullable
    public LivingEntity getTarget() {
        return this.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse((LivingEntity) null);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.24D).add(Attributes.MAX_HEALTH, 200F).add(Attributes.FOLLOW_RANGE, 18F).add(Attributes.ATTACK_DAMAGE, 6F);
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return FrostSounds.SEEKER_IDLE.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        return FrostSounds.SEEKER_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FrostSounds.SEEKER_DEATH.get();
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (this.level().isClientSide) {
            if (this.attackAnimationTick < this.attackAnimationLength) {
                this.attackAnimationTick++;
            }

            if (this.attackAnimationTick >= this.attackAnimationLength) {
                this.attackAnimationState.stop();

            }
            this.setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        if (!this.attackAnimationState.isStarted()) {
            if (this.isAggressive()) {
                this.stopAttackAnimationState.stop();
                this.preAttackAnimationState.startIfStopped(this.tickCount);
            } else {
                this.preAttackAnimationState.stop();
                this.stopAttackAnimationState.startIfStopped(this.tickCount);
            }
        }
    }

    @Override
    public void handleEntityEvent(byte p_21375_) {
        if (p_21375_ == 4) {
            this.attackAnimationState.start(this.tickCount);
            this.preAttackAnimationState.stop();
            this.stopAttackAnimationState.stop();
            this.attackAnimationTick = 0;
        } else {
            super.handleEntityEvent(p_21375_);
        }
    }

    @Override
    protected void tickDeath() {
        if (this.level().isClientSide()) {
            if (this.deathTime == 0) {
                this.deathAnimationState.start(this.tickCount);
                this.attackAnimationState.stop();
            }
        }
        if (++this.deathTime >= 60 && !this.level().isClientSide() && !this.isRemoved()) {
            this.level().broadcastEntityEvent(this, (byte) 60);
            this.remove(RemovalReason.KILLED);
        }
    }

    @javax.annotation.Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_34717_, DifficultyInstance p_34718_, EntitySpawnReason p_361787_, @javax.annotation.Nullable SpawnGroupData p_34720_) {
        RandomSource randomsource = p_34717_.getRandom();
        this.populateDefaultEquipmentSlots(randomsource, p_34718_);
        this.populateDefaultEquipmentEnchantments(p_34717_, randomsource, p_34718_);
        return super.finalizeSpawn(p_34717_, p_34718_, p_361787_, p_34720_);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource p_218949_, DifficultyInstance p_218950_) {
        ItemStack spear = new ItemStack(FrostItems.FROST_SPEAR.get());
        this.setItemSlot(EquipmentSlot.MAINHAND, spear);
        this.setGuaranteedDrop(EquipmentSlot.MAINHAND);
    }

    @Override
    public boolean doHurtTarget(ServerLevel serverLevel, Entity p_21372_) {
        boolean flag = super.doHurtTarget(serverLevel, p_21372_);

        if (flag && this.getMainHandItem().is(FrostItems.FROST_SPEAR.get())) {
            p_21372_.setTicksFrozen(Mth.clamp(p_21372_.getTicksFrozen() + 100, 0, 600));

        }
        return flag;
    }

    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource p_34503_, float p_34504_) {
        boolean flag = super.hurtServer(serverLevel, p_34503_, p_34504_);

        if (flag && p_34503_.getEntity() instanceof LivingEntity) {
            SeekerAi.wasHurtBy(serverLevel, this, (LivingEntity) p_34503_.getEntity());
        }

        return flag;
    }

    @Override
    public boolean isLeftHanded() {
        return false;
    }

    @Override
    protected AABB getAttackBoundingBox() {
        return this.getMainHandItem().is(FrostItems.FROST_SPEAR.get()) ? super.getAttackBoundingBox().inflate(1.5F, 0, 1.5F) : super.getAttackBoundingBox();
    }
}
