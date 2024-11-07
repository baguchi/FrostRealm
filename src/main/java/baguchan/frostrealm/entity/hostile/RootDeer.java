package baguchan.frostrealm.entity.hostile;

import baguchan.frostrealm.entity.goal.AppearGoal;
import baguchan.frostrealm.entity.goal.NonMoveAnimateAttackGoal;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class RootDeer extends Monster {
    protected static final EntityDataAccessor<Direction> DATA_ATTACH_FACE_ID = SynchedEntityData.defineId(RootDeer.class, EntityDataSerializers.DIRECTION);

    private final int attackAnimationLength = (int) (30);
    private final int attackAnimationActionPoint = (int) (0.5 * 20);
    private int attackAnimationTick;
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState summonAnimationState = new AnimationState();
    public final AnimationState deathAnimationState = new AnimationState();

    public static final Predicate<LivingEntity> FROST_PREY_SELECTOR = (p_30437_) -> {
        EntityType<?> entitytype = p_30437_.getType();
        return entitytype != FrostEntities.CRYSTAL_FOX.get();
    };

    public RootDeer(EntityType<? extends RootDeer> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        this.setAttachFace(Direction.DOWN);
    }

    public void recreateFromPacket(ClientboundAddEntityPacket p_219067_) {
        super.recreateFromPacket(p_219067_);
        this.yBodyRot = 0.0F;
        this.yBodyRotO = 0.0F;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new AppearGoal(this, 20));
        this.goalSelector.addGoal(1, new NonMoveAnimateAttackGoal(this, attackAnimationActionPoint, attackAnimationLength) {
            @Override
            public boolean canUse() {
                return getPose() != Pose.EMERGING && super.canUse();
            }
        });
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Animal.class, true, (living, serverlevel) -> FROST_PREY_SELECTOR.test(living)));

    }

    @Override
    protected BodyRotationControl createBodyControl() {
        return new VineBodyRotationControl(this);
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.0D).add(Attributes.FOLLOW_RANGE, 8F).add(Attributes.MAX_HEALTH, 15F).add(Attributes.ATTACK_DAMAGE, 4.0F);
    }

    public static boolean checkDeerSpawnRules(
            EntityType<? extends Monster> p_219014_, ServerLevelAccessor p_219015_, EntitySpawnReason p_361180_, BlockPos p_219017_, RandomSource p_219018_
    ) {
        return p_219015_.getDifficulty() != Difficulty.PEACEFUL
                && (EntitySpawnReason.ignoresLightRequirements(p_361180_) || isDarkEnoughToSpawn(p_219015_, p_219017_, p_219018_))
                && checkDirectionSpawnRules(p_219015_, p_219017_);
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor p_21686_, EntitySpawnReason p_361803_) {
        return true;
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader p_21433_) {
        return !p_21433_.containsAnyLiquid(this.getBoundingBox()) && p_21433_.getBlockState(this.blockPosition()).isAir();
    }

    public static boolean checkDirectionSpawnRules(
            LevelAccessor p_217059_, BlockPos p_217061_
    ) {
        for (Direction direction : Direction.values()) {
            BlockPos blockpos = p_217061_.offset(direction.getUnitVec3i());
            return p_217059_.getBlockState(blockpos).is(FrostBlocks.FROSTBITE_LOG);
        }
        return false;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(
            ServerLevelAccessor p_149780_, DifficultyInstance p_149781_, EntitySpawnReason p_363202_, @Nullable SpawnGroupData p_149783_
    ) {
        this.setYRot(0.0F);
        this.yHeadRot = this.getYRot();
        this.setOldPosAndRot();
        this.setPose(Pose.EMERGING);
        return super.finalizeSpawn(p_149780_, p_149781_, p_363202_, p_149783_);
    }


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ATTACH_FACE_ID, Direction.DOWN);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag p_33432_) {
        super.readAdditionalSaveData(p_33432_);
        this.setAttachFace(Direction.from3DDataValue(p_33432_.getByte("AttachFace")));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag p_33443_) {
        super.addAdditionalSaveData(p_33443_);
        p_33443_.putByte("AttachFace", (byte) this.getAttachFace().get3DDataValue());
    }

    @Override
    public void setPos(double p_33449_, double p_33450_, double p_33451_) {
        BlockPos blockpos = this.blockPosition();
        if (this.isPassenger()) {
            super.setPos(p_33449_, p_33450_, p_33451_);
        } else {
            super.setPos((double) Mth.floor(p_33449_) + 0.5, (double) Mth.floor(p_33450_ + 0.5), (double) Mth.floor(p_33451_) + 0.5);
        }
    }


    @Override
    public Vec3 getDeltaMovement() {
        return Vec3.ZERO;
    }

    @Override
    public void setDeltaMovement(Vec3 p_149804_) {
    }


    @Override
    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }

    private void findNewAttachment() {
        Direction direction = this.findAttachableSurface(this.blockPosition());
        if (direction != null && this.tickCount < 5) {
            this.setAttachFace(direction);
        } else {
            if (this.level() instanceof ServerLevel serverLevel) {
                this.kill(serverLevel);
            }
        }
    }

    @Nullable
    protected Direction findAttachableSurface(BlockPos p_149811_) {
        for (Direction direction : Direction.values()) {
            if (this.canStayAt(p_149811_, direction)) {
                return direction;
            }
        }

        return null;
    }

    boolean canStayAt(BlockPos p_149786_, Direction p_149787_) {
        if (this.isPositionBlocked(p_149786_)) {
            return false;
        } else {
            Direction direction = p_149787_.getOpposite();
            if (!this.level().loadedAndEntityCanStandOnFace(p_149786_.relative(p_149787_), this, direction.getOpposite())) {
                return false;
            } else {
                return true;
            }
        }
    }

    private boolean isPositionBlocked(BlockPos p_149813_) {
        BlockState blockstate = this.level().getBlockState(p_149813_);
        if (blockstate.isAir()) {
            return false;
        } else {
            boolean flag = blockstate.is(Blocks.MOVING_PISTON) && p_149813_.equals(this.blockPosition());
            return !flag;
        }
    }


    public Direction getAttachFace() {
        return this.entityData.get(DATA_ATTACH_FACE_ID);
    }

    private void setAttachFace(Direction p_149789_) {
        this.entityData.set(DATA_ATTACH_FACE_ID, p_149789_);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> p_219422_) {
        if (DATA_POSE.equals(p_219422_)) {
            switch (this.getPose()) {
                case EMERGING:
                    this.summonAnimationState.start(this.tickCount);
                    break;
            }
        }

        if (DATA_ATTACH_FACE_ID.equals(p_219422_)) {
            this.setBoundingBox(this.makeBoundingBox());
        }

        super.onSyncedDataUpdated(p_219422_);
    }


    @Override
    protected AABB makeBoundingBox() {
        Direction direction = this.getAttachFace().getOpposite();
        double d0 = (double) this.getX();
        double d1 = (double) this.getY();
        double d2 = (double) this.getZ();
        double d6 = (double) this.getType().getWidth() / 2;
        double d7 = (double) this.getType().getHeight() / 2;
        double d8 = (double) this.getType().getWidth() / 2;
        if (direction.getAxis() == Direction.Axis.Z) {
            d8 = this.getType().getHeight() / 2;
            d7 = this.getType().getWidth() / 2;
            d1 += (double) this.getType().getWidth();
        } else if (direction.getAxis() == Direction.Axis.X) {
            d6 = this.getType().getHeight() / 2;
            d7 = this.getType().getWidth() / 2;
            d1 += (double) this.getType().getWidth();
        } else if (direction == Direction.UP) {
            d7 = (double) this.getType().getHeight() / 2;
            d1 += (double) this.getType().getHeight() / 2;
        }

        return new AABB(d0 - d6, d1 - d7, d2 - d8, d0 + d6, d1 + d7, d2 + d8);
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
        }
    }

    @Override
    public void tick() {
        super.tick();


        if (!this.level().isClientSide && !this.isPassenger() && !this.canStayAt(this.blockPosition(), this.getAttachFace())) {
            this.findNewAttachment();
        }

        switch (this.getPose()) {
            case EMERGING:
                this.clientDiggingParticles();
                break;
            case DIGGING:
                this.clientDiggingParticles();
        }
    }


    private void clientDiggingParticles() {
        BlockState blockstate = this.level().getBlockState(this.blockPosition().offset(this.getAttachFace().getUnitVec3i()));
        RandomSource randomsource = this.getRandom();

        float size = this.getDimensions(this.getPose()).width() / 2;
        if (blockstate.getRenderShape() != RenderShape.INVISIBLE) {
            SoundType soundType = blockstate.getSoundType();

            if (this.level().isClientSide()) {
                for (int i = 0; i < 4; ++i) {
                    Direction direction = this.getAttachFace().getOpposite();
                    double d0 = this.getX() + (double) Mth.randomBetween(randomsource, -size, size) + direction.getStepX();
                    double d1 = this.getY() + direction.getStepY();
                    double d2 = this.getZ() + (double) Mth.randomBetween(randomsource, -size, size) + direction.getStepZ();
                    this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate), d0, d1, d2, 0.0, 0.0, 0.0);
                }
            } else {
                this.playSound(soundType.getBreakSound());
            }
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
        if (++this.deathTime >= 30 && !this.level().isClientSide() && !this.isRemoved()) {
            this.level().broadcastEntityEvent(this, (byte) 60);
            if (this.level() instanceof ServerLevel serverlevel) {
                AABB aabb = this.getBoundingBox();
                Vec3 vec3 = aabb.getCenter();
                double d0 = aabb.getXsize() / 2;
                double d1 = aabb.getYsize();
                double d2 = aabb.getZsize() / 2;
                serverlevel.sendParticles(
                        new BlockParticleOption(
                                ParticleTypes.BLOCK_CRUMBLE,
                                FrostBlocks.FROSTBITE_LOG.get().defaultBlockState()
                        ),
                        vec3.x,
                        vec3.y,
                        vec3.z,
                        40,
                        d0,
                        d1,
                        d2,
                        0.0
                );
                this.playSound(SoundType.CHERRY_WOOD.getBreakSound());
            }
            this.remove(RemovalReason.KILLED);
        }
    }

    public void handleEntityEvent(byte p_219360_) {
        if (p_219360_ == 4) {
            this.attackAnimationTick = 0;
            this.attackAnimationState.start(this.tickCount);
        } else {
            super.handleEntityEvent(p_219360_);
        }

    }

    @Override
    protected AABB getAttackBoundingBox() {
        return super.getAttackBoundingBox().deflate(0.1F, 0, 0.1F).inflate(0, 0.1F, 0);
    }

    static class VineBodyRotationControl extends BodyRotationControl {
        public VineBodyRotationControl(Mob p_149816_) {
            super(p_149816_);
        }

        @Override
        public void clientTick() {
        }
    }
}
