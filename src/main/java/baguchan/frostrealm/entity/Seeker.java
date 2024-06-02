package baguchan.frostrealm.entity;

import bagu_chan.bagus_lib.entity.AnimationScale;
import bagu_chan.bagus_lib.entity.goal.AnimateAttackGoal;
import baguchan.frostrealm.entity.goal.CounterGoal;
import baguchan.frostrealm.entity.goal.GuardAndCounterAnimationGoal;
import baguchan.frostrealm.entity.utils.GuardHandler;
import baguchan.frostrealm.registry.FrostItems;
import baguchan.frostrealm.utils.LookUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Seeker extends AbstractSkeleton implements IGuardMob {
    private static final EntityDataAccessor<Boolean> DATA_GUARD = SynchedEntityData.defineId(Seeker.class, EntityDataSerializers.BOOLEAN);

    public int attackAnimationTick;
    private final int attackAnimationLength = (int) (20 * 1.75);
    private final int attackAnimationActionPoint = (int) ((int) (20 * 0.655));
    public int counterAnimationTick;
    private final int counterAnimationLength = (int) (20 * 0.5F);
    private final int counterAnimationActionPoint = (int) (counterAnimationLength - (int) (20 * 0.1f));


    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState counterAnimationState = new AnimationState();
    public final AnimationScale guardAnimationScale = new AnimationScale(0.2F);
    public final GuardHandler guardHandler = new GuardHandler(2);
    public GuardAndCounterAnimationGoal guardAnimationGoal;
    public CounterGoal counterGoal;

    public Seeker(EntityType<? extends Seeker> p_32133_, Level p_32134_) {
        super(p_32133_, p_32134_);
        this.xpReward = 10;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        counterGoal = new CounterGoal(this, counterAnimationActionPoint, counterAnimationLength) {
            @Override
            protected void doTheAnimation() {
                this.attacker.level().broadcastEntityEvent(this.attacker, (byte) 61);
            }
        };
        guardAnimationGoal = new GuardAndCounterAnimationGoal<>(this, true, 60) {
            @Override
            protected void stopGuardAndAttackAnimation() {
                super.stopGuardAndAttackAnimation();
                counterGoal.trigger();
            }
        };
        this.goalSelector.addGoal(1, counterGoal);
        this.goalSelector.addGoal(2, guardAnimationGoal);
        this.goalSelector.addGoal(4, new AnimateAttackGoal(this, 1.2D, attackAnimationActionPoint, attackAnimationLength) {
            @Override
            public boolean canUse() {
                return !isGuard() && super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                return !isGuard() && super.canContinueToUse();
            }
        });
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.MAX_HEALTH, 30F);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_GUARD, false);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> p_219422_) {
        super.onSyncedDataUpdated(p_219422_);
        if (DATA_GUARD.equals(p_219422_)) {
            if (this.isGuard()) {
                if (!this.level().isClientSide()) {
                    this.attackAnimationState.stop();
                }
            }
        }

    }

    public void setGuard(boolean guard) {
        this.entityData.set(DATA_GUARD, guard);
    }

    public boolean isGuard() {
        return this.entityData.get(DATA_GUARD);
    }

    @Override
    public void reassessWeaponGoal() {
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


            if (this.counterAnimationTick < this.counterAnimationLength) {
                this.counterAnimationTick++;
                if (this.attackAnimationState.isStarted()) {
                    this.attackAnimationState.stop();
                }
            }

            if (this.counterAnimationTick >= this.counterAnimationLength) {
                this.counterAnimationState.stop();
            }

            if (this.isGuard()) {
                this.attackAnimationState.stop();
                this.counterAnimationState.stop();
            }
            this.guardAnimationScale.setFlag(this.isGuard());
            this.guardAnimationScale.tick(this);
        } else {
            this.guardHandler.tick(this);
            if (this.guardHandler.isTrigger()) {
                if (this.guardAnimationGoal != null) {
                    this.guardAnimationGoal.trigger();
                    this.guardHandler.setTrigger(false);
                    this.guardHandler.resetTrigger(true);
                }
            }
        }
    }

    @Override
    public void handleEntityEvent(byte p_21375_) {
        if (p_21375_ == 4) {
            this.attackAnimationState.start(this.tickCount);
            this.attackAnimationTick = 0;
        } else if (p_21375_ == 61) {
            this.counterAnimationState.start(this.tickCount);
            this.counterAnimationTick = 0;
        } else {
            super.handleEntityEvent(p_21375_);
        }
    }

    protected void populateDefaultEquipmentSlots(RandomSource p_218949_, DifficultyInstance p_218950_) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(FrostItems.FROST_SPEAR.get()));
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(FrostItems.YETI_FUR_HELMET.get()));
        this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(FrostItems.YETI_FUR_CHESTPLATE.get()));
    }

    @Override
    public boolean doHurtTarget(Entity p_21372_) {
        if (this.getMainHandItem().is(FrostItems.FROST_SPEAR.get())) {
            p_21372_.setTicksFrozen(Mth.clamp(p_21372_.getTicksFrozen() + 100, 0, 600));

        }
        return super.doHurtTarget(p_21372_);
    }

    @Override
    public boolean hurt(DamageSource p_21016_, float p_21017_) {


        if (this.isDamageSourceBlockedBySpear(p_21016_)) {
            this.playSound(SoundEvents.ANVIL_LAND, 1.0F, 1.5F);
            return false;
        } else {
            if (!this.level().isClientSide()) {
                this.guardHandler.addHurtCount(p_21017_);
            }
        }

        return super.hurt(p_21016_, p_21017_);
    }

    public boolean isDamageSourceBlockedBySpear(DamageSource p_21276_) {
        Entity entity = p_21276_.getDirectEntity();
        boolean flag = false;
        if (entity instanceof AbstractArrow abstractarrow) {
            if (abstractarrow.getPierceLevel() > 0) {
                flag = true;
            }
        }

        if (!p_21276_.is(DamageTypeTags.BYPASSES_SHIELD) && this.isGuard() && !flag) {
            Vec3 vec32 = p_21276_.getSourcePosition();
            if (vec32 != null) {
                Vec3 vec3 = this.getViewVector(1.0F);
                Vec3 vec31 = vec32.vectorTo(this.position()).normalize();
                vec31 = new Vec3(vec31.x, 0.0D, vec31.z);
                if (vec31.dot(vec3) < 0.0D) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean checkStraySpawnRules(EntityType<Seeker> p_219121_, ServerLevelAccessor p_219122_, MobSpawnType p_219123_, BlockPos p_219124_, RandomSource p_219125_) {
        return checkMonsterSpawnRules(p_219121_, p_219122_, p_219123_, p_219124_, p_219125_) && (p_219123_ == MobSpawnType.SPAWNER || p_219122_.canSeeSky(p_219124_));
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.STRAY_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource p_33850_) {
        return SoundEvents.STRAY_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.STRAY_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.STRAY_STEP;
    }

    protected AbstractArrow getArrow(ItemStack p_33846_, float p_33847_) {
        AbstractArrow abstractarrow = super.getArrow(p_33846_, p_33847_);
        if (abstractarrow instanceof Arrow) {
            ((Arrow) abstractarrow).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600));
        }

        return abstractarrow;
    }

    @Override
    public boolean hasLineOfSight(Entity p_147185_) {
        if (p_147185_.level() == this.level() && !LookUtils.isLookingAtYouTest(this, p_147185_)) {
            return false;
        }
        return super.hasLineOfSight(p_147185_);
    }

    @Override
    protected AABB getAttackBoundingBox() {
        return this.getMainHandItem().is(FrostItems.FROST_SPEAR.get()) ? super.getAttackBoundingBox().inflate(1F, 0, 1F) : super.getAttackBoundingBox();
    }
}
