package baguchan.frostrealm.entity;

import baguchan.frostrealm.entity.goal.BreedAndEggGoal;
import baguchan.frostrealm.entity.goal.MeleeAttackNonFlyingGoal;
import baguchan.frostrealm.entity.goal.RandomMoveNonFlyingGoal;
import baguchan.frostrealm.registry.FrostEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;

public class FrostormDragon extends Animal implements IFlyMob, IHasEgg {
    private static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(FrostormDragon.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_EGG = SynchedEntityData.defineId(FrostormDragon.class, EntityDataSerializers.BOOLEAN);


    private final FrostormDragonPart[] subEntities;
    public final FrostormDragonPart head;
    private final FrostormDragonPart neck;
    private final FrostormDragonPart body;
    private final FrostormDragonPart tail1;
    private final FrostormDragonPart tail2;
    private final FrostormDragonPart tail3;
    private final FrostormDragonPart tail4;
    private final FrostormDragonPart tail5;


    FrostormDragon.AttackPhase attackPhase = FrostormDragon.AttackPhase.CIRCLE;


    protected final FlyingPathNavigation flyingPathNavigation;
    protected final GroundPathNavigation groundNavigation;


    public FrostormDragon(EntityType<? extends FrostormDragon> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        this.xpReward = 200;
        this.setMaxUpStep(2.0F);
        this.head = new FrostormDragonPart(this, "head", 1.0F, 1.0F, 1.25F);
        this.neck = new FrostormDragonPart(this, "neck", 2.0F, 2.0F, 1.15F);
        this.body = new FrostormDragonPart(this, "body", 3.0F, 3.0F, 1.0F);
        this.tail1 = new FrostormDragonPart(this, "tail", 2.0F, 1.0F, 0.85F);
        this.tail2 = new FrostormDragonPart(this, "tail", 2.0F, 1.0F, 0.8F);
        this.tail3 = new FrostormDragonPart(this, "tail", 2.0F, 1.0F, 0.785F);
        this.tail4 = new FrostormDragonPart(this, "tail", 2.0F, 1.0F, 0.785F);
        this.tail5 = new FrostormDragonPart(this, "tail", 2.0F, 1.0F, 0.785F);

        this.subEntities = new FrostormDragonPart[]{this.head, this.neck, this.body, this.tail1, this.tail2, this.tail3, this.tail4, this.tail5};
        this.moveControl = new FrostormDragonMoveControl(this);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);

        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, this.level());
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        this.flyingPathNavigation = flyingpathnavigation;
        this.groundNavigation = new GroundPathNavigation(this, this.level());
        this.lookControl = new FrostormDragon.FrostormDragonLookControl(this);
    }


    protected BodyRotationControl createBodyControl() {
        return new FrostormDragonBodyRotationControl(this);
    }


    public void updateFlying() {
        if (!this.level().isClientSide) {
            if (this.isEffectiveAi() && this.isFlying()) {
                this.navigation = this.flyingPathNavigation;
            } else {
                this.navigation = this.groundNavigation;
            }
        }

    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createMobAttributes().add(Attributes.MAX_HEALTH, 300.0D).add(Attributes.KNOCKBACK_RESISTANCE, 0.9F).add(Attributes.ATTACK_KNOCKBACK, 2.0F).add(Attributes.FOLLOW_RANGE, 32.0D).add(Attributes.ATTACK_DAMAGE, 12.0D).add(Attributes.MOVEMENT_SPEED, 0.3F);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FLYING, false);
        this.entityData.define(HAS_EGG, false);
    }

    public boolean isFlying() {
        return this.entityData.get(FLYING);
    }

    public void setFlying(boolean shear) {
        this.entityData.set(FLYING, shear);
    }

    @Override
    public boolean hasEgg() {
        return this.entityData.get(HAS_EGG);
    }

    @Override
    public void setHasEgg(boolean hasEgg) {
        this.entityData.set(HAS_EGG, hasEgg);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag p_27576_) {
        super.readAdditionalSaveData(p_27576_);

        this.setFlying(p_27576_.getBoolean("Flying"));
        this.setHasEgg(p_27576_.getBoolean("HasEgg"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag p_27587_) {
        super.addAdditionalSaveData(p_27587_);
        p_27587_.putBoolean("Flying", this.isFlying());
        p_27587_.putBoolean("HasEgg", this.hasEgg());
    }
    public void travel(Vec3 p_20818_) {
        if (this.isFlying()) {
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
        } else {
            super.travel(p_20818_);
        }
    }

    public boolean onClimbable() {
        return false;
    }
   /* protected BodyRotationControl createBodyControl() {
        return new FrostormDragon.FrostormDragonBodyRotationControl(this);
    }*/

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackNonFlyingGoal<>(this, 0.9F, true));
        //this.goalSelector.addGoal(3, new TemptGoal(this, 0.9F, true));
        this.goalSelector.addGoal(2, new BreedAndEggGoal<>(this, 0.9F));
        this.goalSelector.addGoal(6, new RandomMoveNonFlyingGoal<>(this, 0.8F, 140));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.goalSelector.addGoal(1, new FrostormDragon.FrostormDragonAttackStrategyGoal());
        this.goalSelector.addGoal(2, new FrostormDragon.FrostormDragonSweepAttackGoal());
        this.goalSelector.addGoal(3, new FrostormDragon.FrostormDragonCircleAroundAnchorGoal());
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new FrostormDragon.FrostormDragonAttackPlayerTargetGoal());
    }

    protected float getStandingEyeHeight(Pose p_33136_, EntityDimensions p_33137_) {
        return p_33137_.height * 0.95F;
    }

    @Override
    public AABB getBoundingBoxForCulling() {
        return super.getBoundingBoxForCulling().inflate(3.0F, 2.0F, 3.0F);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> p_33134_) {

        super.onSyncedDataUpdated(p_33134_);
    }

    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    public void aiStep() {
        super.aiStep();
        if (this.isDeadOrDying()) {
            float f8 = (this.random.nextFloat() - 0.5F) * 8.0F;
            float f10 = (this.random.nextFloat() - 0.5F) * 4.0F;
            float f11 = (this.random.nextFloat() - 0.5F) * 8.0F;
            this.level().addParticle(ParticleTypes.EXPLOSION, this.getX() + (double) f8, this.getY() + 2.0D + (double) f10, this.getZ() + (double) f11, 0.0D, 0.0D, 0.0D);
        } else {
            Vec3[] avec3 = new Vec3[this.subEntities.length];

            for (int j = 0; j < this.subEntities.length; ++j) {
                avec3[j] = new Vec3(this.subEntities[j].getX(), this.subEntities[j].getY(), this.subEntities[j].getZ());
            }

            Vec3 noFlyingVec3 = this.calculateViewVector(0.0F, this.yBodyRot).scale(this.getScale());


            Vec3 vec3 = this.calculateViewVector(-this.getXRot(), this.yBodyRot).scale(this.getScale());

            if (this.isFlying()) {
                this.tickPart(this.body, (double) (-vec3.x * 0.5F), 0.0D, (double) (-vec3.z * 0.5F));
            } else {
                this.tickPart(this.body, (double) (-noFlyingVec3.x * 0.5F), 0.0D, (double) (-noFlyingVec3.z * 0.5F));

            }
            if (!this.level().isClientSide && this.hurtTime == 0) {
                if (this.isFlying()) {
                    this.knockBack(this.level().getEntities(this, this.body.getBoundingBox().inflate(4.0D, 2.0D, 4.0D).move(0.0D, -2.0D, 0.0D), EntitySelector.NO_CREATIVE_OR_SPECTATOR));
                    this.hurt(this.level().getEntities(this, this.head.getBoundingBox(), EntitySelector.NO_CREATIVE_OR_SPECTATOR));
                    this.hurt(this.level().getEntities(this, this.neck.getBoundingBox(), EntitySelector.NO_CREATIVE_OR_SPECTATOR));
                }
            }
            if (this.isFlying()) {
                this.tickPart(this.head, (double) (-vec3.x * 4.5F), (double) (-vec3.y * 4.5F - 2F * this.getScale()), (double) (-vec3.z * 4.5F));
                this.tickPart(this.neck, (double) (-vec3.x * 4.0F), (double) (-vec3.y * 4.0F - 2F * this.getScale()), (double) (-vec3.z * 4.0F));
            } else {
                this.tickPart(this.head, (double) (-noFlyingVec3.x * 4.5F), (double) (-2F * this.getScale()), (double) (-noFlyingVec3.z * 4.5F));
                this.tickPart(this.neck, (double) (-noFlyingVec3.x * 4.0F), (double) (-2F * this.getScale()), (double) (-noFlyingVec3.z * 4.0F));

            }
            for (int k = 0; k < 5; ++k) {
                FrostormDragonPart part = null;
                if (k == 0) {
                    part = this.tail1;
                }

                if (k == 1) {
                    part = this.tail2;
                }

                if (k == 2) {
                    part = this.tail3;
                }
                if (k == 3) {
                    part = this.tail4;
                }
                if (k == 4) {
                    part = this.tail5;
                }
                float f22 = (float) (k + 1) * 2.0F;
                if (this.isFlying()) {
                    this.tickPart(part, (double) ((vec3.x * f22)), vec3.y * f22 - 2F * this.getScale(), (double) vec3.z * f22);

                } else {
                    this.tickPart(part, (double) ((noFlyingVec3.x * f22)), -2F * this.getScale(), (double) noFlyingVec3.z * f22);

                }
            }
/*
                if (!this.level().isClientSide) {
                    this.inWall = this.checkWalls(this.head.getBoundingBox()) | this.checkWalls(this.neck.getBoundingBox()) | this.checkWalls(this.body.getBoundingBox());
                    if (this.dragonFight != null) {
                        this.dragonFight.updateDragon(this);
                    }
                }*/

            for (int l = 0; l < this.subEntities.length; ++l) {
                this.subEntities[l].xo = avec3[l].x;
                this.subEntities[l].yo = avec3[l].y;
                this.subEntities[l].zo = avec3[l].z;
                this.subEntities[l].xOld = avec3[l].x;
                this.subEntities[l].yOld = avec3[l].y;
                this.subEntities[l].zOld = avec3[l].z;
            }
        }


        if (this.isFlying()) {
            if (this.isFlyStopCoudition()) {
                this.setFlying(false);
            }
        }
        /*if (!this.isFlying() && !this.isInFluidType() && this.fallDistance > 3.0F) {
            this.setFlying(true);
        }*/

        this.updateFlying();
    }

    private float rotWrap(double p_31165_) {
        return (float) Mth.wrapDegrees(p_31165_);
    }

    public boolean isFlyStopCoudition() {
        return this.isInFluidType() || this.isInPowderSnow || this.onGround();
    }


    private void tickPart(FrostormDragonPart p_31116_, double p_31117_, double p_31118_, double p_31119_) {
        p_31116_.setPos(this.getX() - p_31117_, this.getY() - p_31118_, this.getZ() - p_31119_);
    }

    protected float getSoundVolume() {
        return 3.0F;
    }

    @Override
    public void push(Entity p_33636_) {
        if (p_33636_ != this && !(p_33636_ instanceof FrostormDragon) && !(p_33636_ instanceof FrostormDragonPart)) {
            super.push(p_33636_);
        }
    }

    private void knockBack(List<Entity> p_31132_) {
        double d0 = (this.body.getBoundingBox().minX + this.body.getBoundingBox().maxX) / 2.0D;
        double d1 = (this.body.getBoundingBox().minZ + this.body.getBoundingBox().maxZ) / 2.0D;

        for (Entity entity : p_31132_) {
            if (entity instanceof LivingEntity) {
                double d2 = entity.getX() - d0;
                double d3 = entity.getZ() - d1;
                double d4 = Math.max(d2 * d2 + d3 * d3, 0.1D);
                entity.push(d2 / d4 * 4.0D, (double) 0.2F, d3 / d4 * 4.0D);
                if (((LivingEntity) entity).getLastHurtByMobTimestamp() < entity.tickCount - 2) {
                    entity.hurt(this.damageSources().mobAttack(this), 5.0F);
                    this.doEnchantDamageEffects(this, entity);
                }
            }
        }

    }

    private void hurt(List<Entity> p_31142_) {
        for (Entity entity : p_31142_) {
            if (entity instanceof LivingEntity) {
                entity.hurt(this.damageSources().mobAttack(this), 10.0F);
                this.doEnchantDamageEffects(this, entity);
            }
        }

    }

    @Override
    public boolean hurt(DamageSource source, float p_21017_) {
        if (source.is(DamageTypes.CRAMMING) || source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.DROWN)) {
            return false;
        }
        return super.hurt(source, p_21017_);
    }


    public boolean hurt(FrostormDragonPart frostormDragon, DamageSource source, float p_21017_) {
        if (source.is(DamageTypes.CRAMMING) || source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.DROWN)) {
            return false;
        }
        return this.hurt(source, p_21017_);
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_33126_, DifficultyInstance p_33127_, MobSpawnType p_33128_, @Nullable SpawnGroupData p_33129_, @Nullable CompoundTag p_33130_) {

        return super.finalizeSpawn(p_33126_, p_33127_, p_33128_, p_33129_, p_33130_);
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return FrostEntities.FROSTORM_DRAGON.get().create(p_146743_);
    }

    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENDER_DRAGON_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource p_33152_) {
        return SoundEvents.ENDER_DRAGON_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENDER_DRAGON_DEATH;
    }

    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    public double getPassengersRidingOffset() {
        return this.getBbHeight();
    }

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public net.minecraftforge.entity.PartEntity<?>[] getParts() {
        return this.subEntities;
    }


    public FrostormDragonPart[] getSubEntities() {
        return this.subEntities;
    }

    public void remove(Entity.RemovalReason removalReason) {
        super.remove(removalReason);
        if (subEntities != null) {
            for (PartEntity part : subEntities) {
                part.remove(RemovalReason.KILLED);
            }
        }
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
                if (FrostormDragon.this.isFlying()) {
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
            return livingentity != null ? FrostormDragon.this.isFlying() && FrostormDragon.this.canAttack(livingentity, TargetingConditions.DEFAULT) : false;
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
                    FrostormDragon.this.playSound(SoundEvents.ENDER_DRAGON_AMBIENT, 10.0F, 0.5F + FrostormDragon.this.random.nextFloat() * 0.1F);
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
        protected final FrostormDragon frostormDragon;

        public FrostormDragonBodyRotationControl(FrostormDragon p_33216_) {
            super(p_33216_);
            this.frostormDragon = p_33216_;
        }

        public void clientTick() {
            if (FrostormDragon.this.isFlying()) {
                FrostormDragon.this.yHeadRot = FrostormDragon.this.yBodyRot;
                FrostormDragon.this.yBodyRot = FrostormDragon.this.getYRot();
            } else {
                //fix part entity cannot move
                super.clientTick();
            }

        }
    }

    class FrostormDragonCircleAroundAnchorGoal extends Goal {
        private float angle;
        private float distance;
        private float height;
        private float clockwise;

        public boolean canUse() {
            return FrostormDragon.this.isFlying() && (FrostormDragon.this.getTarget() == null || FrostormDragon.this.attackPhase == FrostormDragon.AttackPhase.CIRCLE);
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
            if (!FrostormDragon.this.isFlying()) {
                super.tick();
            }
        }
    }

    class FrostormDragonMoveControl extends MoveControl {
        protected final FrostormDragon frostormDragon;

        public FrostormDragonMoveControl(FrostormDragon p_33241_) {
            super(p_33241_);
            this.frostormDragon = p_33241_;
        }

        public void tick() {

            if (this.frostormDragon.isFlying()) {
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

            } else {
                this.mob.setNoGravity(false);
                super.tick();
            }
        }
    }

    class FrostormDragonSweepAttackGoal extends Goal {

        public boolean canUse() {
            return FrostormDragon.this.getTarget() != null && FrostormDragon.this.attackPhase == FrostormDragon.AttackPhase.SWOOP;
        }

        public boolean canContinueToUse() {
            LivingEntity livingentity = FrostormDragon.this.getTarget();
            if (livingentity == null || !FrostormDragon.this.isFlying()) {
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
