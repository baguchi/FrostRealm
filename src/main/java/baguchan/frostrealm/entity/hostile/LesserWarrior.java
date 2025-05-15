package baguchan.frostrealm.entity.hostile;

import baguchan.frostrealm.data.resource.registries.AttachableCrystals;
import baguchan.frostrealm.entity.IGuardMob;
import baguchan.frostrealm.entity.goal.CounterGoal;
import baguchan.frostrealm.entity.goal.GuardAndCounterAnimationGoal;
import baguchan.frostrealm.entity.utils.GuardHandler;
import baguchan.frostrealm.registry.FrostDataCompnents;
import baguchan.frostrealm.registry.FrostEntities;
import baguchan.frostrealm.registry.FrostItems;
import baguchan.frostrealm.utils.aurorapower.AuroraPowerUtils;
import baguchi.bagus_lib.entity.AnimationScale;
import baguchi.bagus_lib.entity.goal.AnimateAttackGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class LesserWarrior extends AbstractSkeleton implements IGuardMob {
    private static final EntityDataAccessor<Boolean> DATA_GUARD = SynchedEntityData.defineId(LesserWarrior.class, EntityDataSerializers.BOOLEAN);

    public int attackAnimationTick;
    private final int attackAnimationLength = (int) (20 * 1.5);
    private final int attackAnimationActionPoint = (int) ((int) (20 * 0.275));
    public int counterAnimationTick;
    private final int counterAnimationLength = (int) (20);
    private final int counterAnimationActionPoint = (int) (int) (20 * 0.2f);


    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState counterAnimationState = new AnimationState();
    public AnimationScale guardAnimationScale = new AnimationScale(0.2F);
    public final GuardHandler guardHandler = new GuardHandler(4);
    public GuardAndCounterAnimationGoal guardAnimationGoal;
    public CounterGoal counterGoal;

    public LesserWarrior(EntityType<? extends LesserWarrior> p_32133_, Level p_32134_) {
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
        guardAnimationGoal = new GuardAndCounterAnimationGoal<>(this, true, 40) {
            @Override
            protected void stopGuardAndAttackAnimation() {
                super.stopGuardAndAttackAnimation();
                counterGoal.trigger();
            }
        };
        this.goalSelector.addGoal(1, counterGoal);
        this.goalSelector.addGoal(2, guardAnimationGoal);
        this.goalSelector.addGoal(4, new RangedBowAttackGoal<>(this, 1.0D, 30, 16));
        this.goalSelector.addGoal(4, new AnimateAttackGoal(this, 1.2D, attackAnimationActionPoint, attackAnimationLength) {
            @Override
            public boolean canUse() {
                return !getMainHandItem().is(Items.BOW) && !isGuard() && super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                return !getMainHandItem().is(Items.BOW) && !isGuard() && super.canContinueToUse();
            }
        });
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.MAX_HEALTH, 30F).add(Attributes.FOLLOW_RANGE, 18F);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_GUARD, false);
    }

    @Override
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
            if (!this.getMainHandItem().is(Items.BOW) && this.guardHandler.isTrigger()) {
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

    @javax.annotation.Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_34717_, DifficultyInstance p_34718_, EntitySpawnReason p_361787_, @javax.annotation.Nullable SpawnGroupData p_34720_) {
        RandomSource randomsource = p_34717_.getRandom();
        this.populateDefaultEquipmentSlots(randomsource, p_34718_);
        this.populateDefaultEquipmentEnchantments(p_34717_, randomsource, p_34718_);
        return super.finalizeSpawn(p_34717_, p_34718_, p_361787_, p_34720_);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource p_218949_, DifficultyInstance p_218950_) {
        if (p_218949_.nextFloat() < 0.75F) {
            ItemStack spear = new ItemStack(FrostItems.ASTRIUM_SWORD.get());

            AuroraPowerUtils.auroraInfusionItem(p_218949_, spear, 5, false);
            this.setItemSlot(EquipmentSlot.MAINHAND, spear);
        } else {
            ItemStack spear = new ItemStack(Items.BOW);
            this.setItemSlot(EquipmentSlot.MAINHAND, spear);
        }
        ItemStack helmet = new ItemStack(FrostItems.YETI_FUR_HELMET.get());
        AuroraPowerUtils.auroraInfusionItem(p_218949_, helmet, 5, false);
        this.setItemSlot(EquipmentSlot.HEAD, helmet);
        ItemStack chest = new ItemStack(FrostItems.YETI_FUR_CHESTPLATE.get());
        AuroraPowerUtils.auroraInfusionItem(p_218949_, chest, 5, false);

        this.setItemSlot(EquipmentSlot.CHEST, chest);
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
    public boolean hurtServer(ServerLevel serverLevel, DamageSource p_21016_, float p_21017_) {


        if (this.isDamageSourceBlockedBySpear(p_21016_)) {
            this.playSound(SoundEvents.ANVIL_LAND, 1.0F, 1.5F);
            return false;
        } else {
            if (!this.level().isClientSide()) {
                this.guardHandler.addHurtCount(p_21017_);
            }
        }

        return super.hurtServer(serverLevel, p_21016_, p_21017_);
    }

    @Override
    protected boolean considersEntityAsAlly(Entity p_360600_) {
        if (super.considersEntityAsAlly(p_360600_)) {
            return true;
        } else {
            return p_360600_.getType() != FrostEntities.LESSER_WARRIOR.get() ? false : this.getTeam() == null && p_360600_.getTeam() == null;
        }
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

    public static boolean checkStraySpawnRules(EntityType<LesserWarrior> p_219121_, ServerLevelAccessor p_219122_, EntitySpawnReason p_219123_, BlockPos p_219124_, RandomSource p_219125_) {
        return checkMonsterSpawnRules(p_219121_, p_219122_, p_219123_, p_219124_, p_219125_) && (p_219123_ == EntitySpawnReason.SPAWNER || p_219122_.canSeeSky(p_219124_));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.STRAY_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_33850_) {
        return SoundEvents.STRAY_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.STRAY_DEATH;
    }

    @Override
    protected boolean isSunBurnTick() {
        return false;
    }

    @Override
    protected SoundEvent getStepSound() {
        return SoundEvents.STRAY_STEP;
    }


    @Override
    protected AbstractArrow getArrow(ItemStack p_32156_, float p_32157_, @Nullable ItemStack p_346155_) {
        p_32156_.set(FrostDataCompnents.ATTACH_CRYSTAL, this.level().registryAccess().lookupOrThrow(AttachableCrystals.ATTACHABLE_CRYSTAL_REGISTRY_KEY).getOrThrow(AttachableCrystals.FROST));

        AbstractArrow abstractarrow = super.getArrow(p_32156_, p_32157_, p_346155_);
        return abstractarrow;
    }

    @Override
    protected AABB getAttackBoundingBox() {
        return this.getMainHandItem().is(FrostItems.FROST_SPEAR.get()) ? super.getAttackBoundingBox().inflate(1F, 0, 1F) : super.getAttackBoundingBox();
    }
}
