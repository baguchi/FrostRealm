package baguchan.frostrealm.entity.boss;

import baguchan.frostrealm.entity.goal.SeekerAttackGoal;
import baguchan.frostrealm.entity.goal.SeekerBreathAttackGoal;
import baguchan.frostrealm.entity.hostile.LesserWarrior;
import baguchan.frostrealm.registry.FrostEntityDatas;
import baguchan.frostrealm.registry.FrostItems;
import baguchan.frostrealm.registry.FrostSounds;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

public class Seeker extends Monster {
    private static final EntityDataAccessor<SeekerState> STATE = SynchedEntityData.defineId(Seeker.class, FrostEntityDatas.SEEKER_STATE.get());
    public int attackAnimationTick;
    public static final int attackAnimationLength = (int) (20 * 2);
    public static final int attackAnimationActionPoint = (int) ((int) (20 * 0.55));


    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState preAttackAnimationState = new AnimationState();
    public final AnimationState stopAttackAnimationState = new AnimationState();
    public final AnimationState deathAnimationState = new AnimationState();
    public final AnimationState breathAnimationState = new AnimationState();

    private long inStateTicks = 0L;
    private long noSpecialAttackTime = 0L;

    public Seeker(EntityType<? extends Seeker> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        this.xpReward = 200;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(4, new SeekerBreathAttackGoal(this));
        this.goalSelector.addGoal(4, new SeekerAttackGoal(this, 1.2D, attackAnimationActionPoint, attackAnimationLength, 60));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, (double) 1.0F));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));

    }

    @Override
    public void addAdditionalSaveData(CompoundTag p_21484_) {
        super.addAdditionalSaveData(p_21484_);
        p_21484_.putLong("NoAttackTime", this.noSpecialAttackTime);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag p_21450_) {
        super.readAdditionalSaveData(p_21450_);
        this.noSpecialAttackTime = p_21450_.getLongOr("NoAttackTime", 0);
    }

    public long getNoSpecialAttackTime() {
        return noSpecialAttackTime;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder p_326186_) {
        super.defineSynchedData(p_326186_);
        p_326186_.define(STATE, SeekerState.IDLE);
    }

    public SeekerState getState() {
        return this.entityData.get(STATE);
    }

    public void setState(SeekerState state) {
        this.entityData.set(STATE, state);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.24D).add(Attributes.MAX_HEALTH, 300F).add(Attributes.FOLLOW_RANGE, 18F).add(Attributes.ATTACK_DAMAGE, 6F).add(Attributes.KNOCKBACK_RESISTANCE, 1F);
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
    protected float getSoundVolume() {
        return 2;
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> p_316145_) {
        if (STATE.equals(p_316145_)) {
            this.inStateTicks = 0L;

            this.setupAnimationStates();
            if (this.getState() == SeekerState.BREATH) {
                this.noSpecialAttackTime = 0L;
            }
        }

        super.onSyncedDataUpdated(p_316145_);
    }


    public boolean isEnoughNoAttack() {
        return noSpecialAttackTime > 100;
    }

    public boolean shouldSwitchState() {
        return !this.getState().isLoop() && this.inStateTicks > (long) this.getState().animationDuration();
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
                if (this.isAggressive()) {
                    this.preAttackAnimationState.startIfStopped(this.tickCount);
                }
            }
        } else {
            if (this.isAggressive() && this.getState() == SeekerState.IDLE) {
                this.setState(SeekerState.PRE_ATTACK);
            } else if (!this.isAggressive() && this.getState() == SeekerState.PRE_ATTACK) {
                this.setState(SeekerState.STOP_ATTACK);
            } else if (this.shouldSwitchState()) {
                if (this.isAggressive()) {
                    this.setState(SeekerState.PRE_ATTACK);
                } else {
                    this.setState(SeekerState.IDLE);
                }
            }
        }
        if (this.isAggressive()) {
            ++this.noSpecialAttackTime;
        } else {
            --this.noSpecialAttackTime;
        }
        ++this.inStateTicks;
    }

    private void setupAnimationStates() {
        this.preAttackAnimationState.stop();
        this.stopAttackAnimationState.stop();
        this.breathAnimationState.stop();
        switch (this.getState()) {
            case SeekerState.IDLE:
                break;
            case SeekerState.PRE_ATTACK:
                this.preAttackAnimationState.startIfStopped(this.tickCount);
                break;
            case SeekerState.STOP_ATTACK:
                this.stopAttackAnimationState.startIfStopped(this.tickCount);

                break;
            case SeekerState.BREATH:
                this.breathAnimationState.startIfStopped(this.tickCount);
                break;
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

    protected boolean considersEntityAsAlly(Entity p_360600_) {
        if (super.considersEntityAsAlly(p_360600_) || p_360600_ instanceof Seeker || p_360600_ instanceof LesserWarrior) {
            return true;
        } else {
            return false;
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
    public boolean isLeftHanded() {
        return false;
    }

    @Override
    protected AABB getAttackBoundingBox() {
        return this.getMainHandItem().is(FrostItems.FROST_SPEAR.get()) ? super.getAttackBoundingBox().inflate(1.5F, 0, 1.5F) : super.getAttackBoundingBox();
    }

    public static enum SeekerState implements StringRepresentable {
        IDLE("idle", 0, 0) {
            public boolean shouldHideInShell(long p_326483_) {
                return false;
            }
        },
        PRE_ATTACK("pre_attack", -1, 1) {
        },
        STOP_ATTACK("stop_attack", 3, 2) {
        },
        ATTACK("attack", 80, 3) {
            public boolean canLook() {
                return false;
            }

            @Override
            public boolean canWalk() {
                return false;
            }
        },
        BREATH("breath", -1, 4) {
            public boolean canLook() {
                return false;
            }

            @Override
            public boolean canWalk() {
                return false;
            }
        };

        static final Codec<SeekerState> CODEC = StringRepresentable.fromEnum(SeekerState::values);
        private static final IntFunction<SeekerState> BY_ID = ByIdMap.continuous(SeekerState::id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final StreamCodec<ByteBuf, SeekerState> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, SeekerState::id);
        private final String name;
        private final int animationDuration;
        private final int id;

        private SeekerState(String p_316309_, int p_320184_, int p_326087_) {
            this.name = p_316309_;
            this.animationDuration = p_320184_;
            this.id = p_326087_;
        }

        public String getSerializedName() {
            return this.name;
        }

        private int id() {
            return this.id;
        }

        public boolean isLoop() {
            return this.animationDuration < 0;
        }

        public int animationDuration() {
            return this.animationDuration;
        }

        public boolean canLook() {
            return true;
        }

        public boolean canWalk() {
            return true;
        }
    }
}
