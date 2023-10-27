package baguchan.frostrealm.entity;

import baguchan.frostrealm.registry.FrostEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FrostormDragon extends Monster {
    private static final EntityDataAccessor<Optional<UUID>> CHILD_UUID = SynchedEntityData.defineId(FrostormDragon.class, EntityDataSerializers.OPTIONAL_UUID);

    public static final float FLAP_DEGREES_PER_TICK = 7.448451F;
    public static final int TICKS_PER_FLAP = Mth.ceil(24.166098F);
    FrostormDragon.AttackPhase attackPhase = FrostormDragon.AttackPhase.CIRCLE;

    public FrostormDragon(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        this.xpReward = 200;
        this.moveControl = new FrostormDragon.FrostormDragonMoveControl(this);
        this.lookControl = new FrostormDragon.FrostormDragonLookControl(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 100.0D).add(Attributes.FOLLOW_RANGE, 32.0D).add(Attributes.ATTACK_DAMAGE, 8.0D).add(Attributes.MOVEMENT_SPEED, 0.3F);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CHILD_UUID, Optional.empty());
    }

    @Nullable
    public UUID getChildId() {
        return this.entityData.get(CHILD_UUID).orElse(null);
    }

    public void setChildId(@Nullable UUID uniqueId) {
        this.entityData.set(CHILD_UUID, Optional.ofNullable(uniqueId));
    }

    public Entity getChild() {
        UUID id = getChildId();
        if (id != null && !level().isClientSide) {
            return ((ServerLevel) level()).getEntity(id);
        }
        return null;
    }

    protected void checkFallDamage(double p_20809_, boolean p_20810_, BlockState p_20811_, BlockPos p_20812_) {
    }

    public void travel(Vec3 p_20818_) {
        if (this.isControlledByLocalInstance()) {
            if (this.isInWater()) {
                this.moveRelative(0.02F, p_20818_);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale((double) 0.9F));
            } else if (this.isInLava()) {
                this.moveRelative(0.02F, p_20818_);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
            } else {
                this.moveRelative(0.1F, p_20818_);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale((double) 0.91F));
            }
        }

        this.calculateEntityAnimation(false);
    }

    protected PathNavigation createNavigation(Level p_218342_) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, p_218342_);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    public boolean onClimbable() {
        return false;
    }

    public boolean isFlapping() {
        return (this.getUniqueFlapTickOffset() + this.tickCount) % TICKS_PER_FLAP == 0;
    }

    protected BodyRotationControl createBodyControl() {
        return new FrostormDragon.FrostormDragonBodyRotationControl(this);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FrostormDragon.FrostormDragonAttackStrategyGoal());
        this.goalSelector.addGoal(2, new FrostormDragon.FrostormDragonSweepAttackGoal());
        this.goalSelector.addGoal(3, new FrostormDragon.FrostormDragonCircleAroundAnchorGoal());
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new FrostormDragon.FrostormDragonAttackPlayerTargetGoal());
    }

    protected float getStandingEyeHeight(Pose p_33136_, EntityDimensions p_33137_) {
        return p_33137_.height * 0.35F;
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> p_33134_) {

        super.onSyncedDataUpdated(p_33134_);
    }

    public int getUniqueFlapTickOffset() {
        return this.getId() * 3;
    }

    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            float f = Mth.cos((float) (this.getUniqueFlapTickOffset() + this.tickCount) * 7.448451F * ((float) Math.PI / 180F) + (float) Math.PI);
            float f1 = Mth.cos((float) (this.getUniqueFlapTickOffset() + this.tickCount + 1) * 7.448451F * ((float) Math.PI / 180F) + (float) Math.PI);
            if (f > 0.0F && f1 <= 0.0F) {
                this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.PHANTOM_FLAP, this.getSoundSource(), 0.95F + this.random.nextFloat() * 0.05F, 0.95F + this.random.nextFloat() * 0.05F, false);
            }
        }
        if (this.tickCount > 20 && (getChild() == null || !getChild().isAlive() || getChild() instanceof FrostormDragonPart frostormDragonPart && (frostormDragonPart.getParent() == null || !frostormDragonPart.isAlive()))) {
            dead = true;
        }
    }

    @Override
    public float getVoicePitch() {
        return 0.5F;
    }

    protected float getSoundVolume() {
        return 4.0F;
    }

    @Override
    public void push(Entity p_33636_) {
        if (p_33636_ != this && !(p_33636_ instanceof FrostormDragon) && !(p_33636_ instanceof FrostormDragonPart)) {
            super.push(p_33636_);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float p_21017_) {
        if (source.is(DamageTypes.CRAMMING) || source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.DROWN)) {
            return false;
        }
        return super.hurt(source, p_21017_);
    }

    protected void customServerAiStep() {
        super.customServerAiStep();
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_33126_, DifficultyInstance p_33127_, MobSpawnType p_33128_, @Nullable SpawnGroupData p_33129_, @Nullable CompoundTag p_33130_) {
        final Entity child = getChild();
        if (child == null) {
            LivingEntity partParent = this;
            final int segments = 5 + getRandom().nextInt(3);
            for (int i = 0; i < segments; i++) {
                FrostormDragonPart part = new FrostormDragonPart(FrostEntities.FROSTORM_DRAGON_PART.get(), partParent, 0.8F, 180, 0);
                part.setParent(partParent);
                part.setBodyIndex(i);
                if (partParent == this) {
                    this.setChildId(part.getUUID());
                }
                part.setInitialPartPos(this);
                partParent = part;
                p_33126_.addFreshEntity(part);
            }
        }

        return super.finalizeSpawn(p_33126_, p_33127_, p_33128_, p_33129_, p_33130_);
    }

    public void readAdditionalSaveData(CompoundTag p_33132_) {
        super.readAdditionalSaveData(p_33132_);
        if (p_33132_.hasUUID("ChildUUID")) {
            this.setChildId(p_33132_.getUUID("ChildUUID"));
        }
    }

    public void addAdditionalSaveData(CompoundTag p_33141_) {
        super.addAdditionalSaveData(p_33141_);
        if (this.getChildId() != null) {
            p_33141_.putUUID("ChildUUID", this.getChildId());
        }
    }

    public boolean shouldRenderAtSqrDistance(double p_33107_) {
        return true;
    }

    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.PHANTOM_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource p_33152_) {
        return SoundEvents.PHANTOM_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.PHANTOM_DEATH;
    }

    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    public boolean canAttackType(EntityType<?> p_33111_) {
        return true;
    }

    public double getPassengersRidingOffset() {
        return (double) this.getEyeHeight();
    }

    static enum AttackPhase {
        CIRCLE,
        SWOOP;
    }

    class FrostormDragonAttackPlayerTargetGoal extends Goal {
        private final TargetingConditions attackTargeting = TargetingConditions.forCombat().range(64.0D);
        private int nextScanTick = reducedTickDelay(20);

        public boolean canUse() {
            if (this.nextScanTick > 0) {
                --this.nextScanTick;
                return false;
            } else {
                this.nextScanTick = reducedTickDelay(60);
                List<Player> list = FrostormDragon.this.level().getNearbyPlayers(this.attackTargeting, FrostormDragon.this, FrostormDragon.this.getBoundingBox().inflate(46.0D, 64.0D, 46.0D));
                if (!list.isEmpty()) {
                    list.sort(Comparator.<Entity, Double>comparing(Entity::getY).reversed());

                    for (Player player : list) {
                        if (FrostormDragon.this.canAttack(player, TargetingConditions.DEFAULT)) {
                            FrostormDragon.this.setTarget(player);
                            return true;
                        }
                    }
                }

                return false;
            }
        }

        public boolean canContinueToUse() {
            LivingEntity livingentity = FrostormDragon.this.getTarget();
            return livingentity != null ? FrostormDragon.this.canAttack(livingentity, TargetingConditions.DEFAULT) : false;
        }
    }

    class FrostormDragonAttackStrategyGoal extends Goal {
        private int nextSweepTick;

        public boolean canUse() {
            LivingEntity livingentity = FrostormDragon.this.getTarget();
            return livingentity != null ? FrostormDragon.this.canAttack(livingentity, TargetingConditions.DEFAULT) : false;
        }

        public void start() {
            this.nextSweepTick = this.adjustedTickDelay(40);
            FrostormDragon.this.attackPhase = FrostormDragon.AttackPhase.CIRCLE;
            this.setAnchorAboveTarget();
        }

        public void stop() {
            BlockPos pos = FrostormDragon.this.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, FrostormDragon.this.blockPosition()).above(15 + FrostormDragon.this.random.nextInt(20));
            FrostormDragon.this.getNavigation().moveTo(pos.getX(), pos.getY(), pos.getZ(), 1.2D);
        }

        public void tick() {
            if (FrostormDragon.this.attackPhase == FrostormDragon.AttackPhase.CIRCLE) {
                --this.nextSweepTick;
                if (this.nextSweepTick <= 0) {
                    FrostormDragon.this.attackPhase = FrostormDragon.AttackPhase.SWOOP;
                    this.setAnchorAboveTarget();
                    this.nextSweepTick = this.adjustedTickDelay((6 + FrostormDragon.this.random.nextInt(4)) * 20);
                    FrostormDragon.this.playSound(SoundEvents.PHANTOM_SWOOP, 10.0F, 0.5F + FrostormDragon.this.random.nextFloat() * 0.1F);
                }
            }

        }

        private void setAnchorAboveTarget() {
            BlockPos pos = FrostormDragon.this.getTarget().blockPosition().above(20 + FrostormDragon.this.random.nextInt(20));
            if (pos.getY() < FrostormDragon.this.level().getSeaLevel()) {
                pos = new BlockPos(pos.getX(), FrostormDragon.this.level().getSeaLevel() + 1, pos.getZ());
            }
            FrostormDragon.this.getNavigation().moveTo(pos.getX(), pos.getY(), pos.getZ(), 1.2D);
        }
    }

    class FrostormDragonBodyRotationControl extends BodyRotationControl {
        public FrostormDragonBodyRotationControl(Mob p_33216_) {
            super(p_33216_);
        }

        public void clientTick() {
            FrostormDragon.this.yHeadRot = FrostormDragon.this.yBodyRot;
            FrostormDragon.this.yBodyRot = FrostormDragon.this.getYRot();
        }
    }

    class FrostormDragonCircleAroundAnchorGoal extends Goal {
        private float angle;
        private float distance;
        private float height;
        private float clockwise;

        public boolean canUse() {
            return FrostormDragon.this.getTarget() == null || FrostormDragon.this.attackPhase == FrostormDragon.AttackPhase.CIRCLE;
        }

        public void start() {
            this.distance = 5.0F + FrostormDragon.this.random.nextFloat() * 10.0F;
            this.height = -4.0F + FrostormDragon.this.random.nextFloat() * 9.0F;
            this.clockwise = FrostormDragon.this.random.nextBoolean() ? 1.0F : -1.0F;
            this.selectNext();
        }

        public void tick() {
            if (FrostormDragon.this.random.nextInt(this.adjustedTickDelay(350)) == 0) {
                this.height = -4.0F + FrostormDragon.this.random.nextFloat() * 9.0F;
            }

            if (FrostormDragon.this.random.nextInt(this.adjustedTickDelay(250)) == 0) {
                ++this.distance;
                if (this.distance > 15.0F) {
                    this.distance = 5.0F;
                    this.clockwise = -this.clockwise;
                }
            }

            if (FrostormDragon.this.getNavigation().isDone()) {
                this.angle = FrostormDragon.this.random.nextFloat() * 2.0F * (float) Math.PI;
                this.selectNext();


                if (!FrostormDragon.this.level().isEmptyBlock(FrostormDragon.this.blockPosition().below(1))) {
                    this.height = Math.max(2.0F, this.height);
                    this.selectNext();
                }

                if (!FrostormDragon.this.level().isEmptyBlock(FrostormDragon.this.blockPosition().above(1))) {
                    this.height = Math.min(-2.0F, this.height);
                    this.selectNext();
                }
            } else {

                if (FrostormDragon.this.random.nextInt(this.adjustedTickDelay(350)) == 0) {
                    BlockPos pos = FrostormDragon.this.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, FrostormDragon.this.blockPosition()).above(15 + FrostormDragon.this.random.nextInt(20));

                    if (FrostormDragon.this.blockPosition().getY() + 10 < pos.getY()) {
                        FrostormDragon.this.getNavigation().moveTo(pos.getX(), pos.getY(), pos.getZ(), 1.2D);
                    }
                }
            }
        }

        private void selectNext() {

            this.angle += this.clockwise * 15.0F * ((float) Math.PI / 180F);
            Vec3 vec3 = new Vec3(FrostormDragon.this.blockPosition().getX(), FrostormDragon.this.blockPosition().getY(), FrostormDragon.this.blockPosition().getZ()).add((double) (this.distance * Mth.cos(this.angle)), (double) (-4.0F + this.height), (double) (this.distance * Mth.sin(this.angle)));
            FrostormDragon.this.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, 0.8D);
        }
    }

    class FrostormDragonLookControl extends LookControl {
        public FrostormDragonLookControl(Mob p_33235_) {
            super(p_33235_);
        }

        public void tick() {
        }
    }

    class FrostormDragonMoveControl extends MoveControl {

        public FrostormDragonMoveControl(Mob p_33241_) {
            super(p_33241_);
        }

        public void tick() {
            this.operation = MoveControl.Operation.WAIT;
            this.mob.setNoGravity(true);
            double d0 = this.wantedX - this.mob.getX();
            double d1 = this.wantedY - this.mob.getY();
            double d2 = this.wantedZ - this.mob.getZ();
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            if (d3 < (double) 2.5000003E-7F) {
                this.mob.setYya(0.0F);
                this.mob.setZza(0.0F);
                return;
            }
            if (Math.abs(d3) > (double) 1.0E-5F) {
                double d4 = 1.0D - Math.abs(d1 * (double) 0.7F) / d3;
                d0 *= d4;
                d2 *= d4;
                d3 = Math.sqrt(d0 * d0 + d2 * d2);
                double d5 = Math.sqrt(d0 * d0 + d2 * d2 + d1 * d1);
                float f = FrostormDragon.this.getYRot();
                FrostormDragon.this.yBodyRot = FrostormDragon.this.getYRot();
                float speed = (float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
                if (Mth.degreesDifferenceAbs(f, FrostormDragon.this.getYRot()) < 3.0F) {
                    this.mob.setSpeed(Mth.lerp(0.05F, this.mob.getSpeed(), speed));
                } else {
                    this.mob.setSpeed(Mth.lerp(0.125F, this.mob.getSpeed(), speed));
                }

                /*
                 * smooth turn start
                 */
                double maxTurnY = 1.5F;

                float xRot = ((float) (Mth.atan2(d1, d3) * (double) (180F / (float) Math.PI)));
                this.mob.setXRot(this.rotlerp(this.mob.getXRot(), xRot, 7.5F));

                float yRot = (float) (Mth.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
                this.mob.setYRot(this.rotlerp(this.mob.getYRot(), yRot, (float) maxTurnY));
                /*
                 * smooth turn fin
                 */


                float f4 = (float) FrostormDragon.this.getXRot();
                float f5 = FrostormDragon.this.getYRot() + 90.0F;
                double d6 = (double) (this.mob.getSpeed() * Mth.cos(f5 * ((float) Math.PI / 180F))) * Math.abs(d0 / d5);
                double d7 = (double) (this.mob.getSpeed() * Mth.sin(f5 * ((float) Math.PI / 180F))) * Math.abs(d2 / d5);
                double d8 = (double) (this.mob.getSpeed() * Mth.sin(f4 * ((float) Math.PI / 180F))) * Math.abs(d1 / d5);
                Vec3 vec3 = FrostormDragon.this.getDeltaMovement();
                FrostormDragon.this.setDeltaMovement(vec3.add((new Vec3(d6, d8, d7)).subtract(vec3).scale(0.2D)));
            }

        }
    }

    class FrostormDragonSweepAttackGoal extends Goal {

        public boolean canUse() {
            return FrostormDragon.this.getTarget() != null && FrostormDragon.this.attackPhase == FrostormDragon.AttackPhase.SWOOP;
        }

        public boolean canContinueToUse() {
            LivingEntity livingentity = FrostormDragon.this.getTarget();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else {
                if (livingentity instanceof Player) {
                    Player player = (Player) livingentity;
                    if (livingentity.isSpectator() || player.isCreative()) {
                        return false;
                    }
                }

                if (!this.canUse()) {
                    return false;
                } else {

                    return true;
                }
            }
        }

        public void start() {
        }

        public void stop() {
            FrostormDragon.this.setTarget((LivingEntity) null);
            FrostormDragon.this.attackPhase = FrostormDragon.AttackPhase.CIRCLE;
        }

        public void tick() {
            LivingEntity livingentity = FrostormDragon.this.getTarget();
            if (livingentity != null) {
                FrostormDragon.this.getNavigation().moveTo(livingentity, 1.25F);
                if (FrostormDragon.this.getBoundingBox().inflate((double) 0.2F).intersects(livingentity.getBoundingBox())) {
                    FrostormDragon.this.doHurtTarget(livingentity);
                    FrostormDragon.this.attackPhase = FrostormDragon.AttackPhase.CIRCLE;
                    if (!FrostormDragon.this.isSilent()) {
                        FrostormDragon.this.level().levelEvent(1039, FrostormDragon.this.blockPosition(), 0);
                    }
                } else if (FrostormDragon.this.hurtTime > 0) {
                    FrostormDragon.this.attackPhase = FrostormDragon.AttackPhase.CIRCLE;
                }

            }
        }
    }
}
