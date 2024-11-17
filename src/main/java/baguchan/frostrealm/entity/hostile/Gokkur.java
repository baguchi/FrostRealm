package baguchan.frostrealm.entity.hostile;

import baguchan.frostrealm.entity.goal.RollGoal;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostTags;
import baguchi.bagus_lib.client.camera.CameraCore;
import baguchi.bagus_lib.client.camera.holder.CameraHolder;
import baguchi.bagus_lib.util.GlobalVec3;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import org.jetbrains.annotations.Nullable;

public class Gokkur extends Monster {
    protected static final EntityDataAccessor<Boolean> GRASS = SynchedEntityData.defineId(Gokkur.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Float> SNOW_PROGRESS = SynchedEntityData.defineId(Gokkur.class, EntityDataSerializers.FLOAT);
    private static final ProjectileDeflection PROJECTILE_DEFLECTION = (p_344345_, p_344346_, p_344347_) -> {
        p_344346_.level().playSound((Player) null, p_344346_, SoundEvents.ANVIL_PLACE, p_344346_.getSoundSource(), 1.0F, 1.5F);
        ProjectileDeflection.REVERSE.deflect(p_344345_, p_344346_, p_344347_);
    };


    private static final EntityDimensions SPIN_DIMENSIONS = EntityDimensions.scalable(1.0F, 1.0F)
            .withEyeHeight(0.5F);

    public AnimationState rollAnimationState = new AnimationState();
    public AnimationState startRollAnimationState = new AnimationState();


    public Gokkur(EntityType<? extends Gokkur> entityType, Level level) {
        super(entityType, level);
        GroundPathNavigation groundpathnavigation = (GroundPathNavigation) this.getNavigation();
        groundpathnavigation.setCanFloat(false);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> p_312373_) {
        if (this.level().isClientSide() && DATA_POSE.equals(p_312373_)) {
            this.stopAllAnimation();
            Pose pose = this.getPose();
            switch (pose) {
                case SITTING:
                    this.startRollAnimationState.startIfStopped(this.tickCount);
                    break;
                case SPIN_ATTACK:
                    this.rollAnimationState.startIfStopped(this.tickCount);
                    break;
            }
        }

        super.onSyncedDataUpdated(p_312373_);
    }


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(GRASS, false);
        builder.define(SNOW_PROGRESS, 0F);
    }

    public void setGrass(boolean grass) {
        this.entityData.set(GRASS, grass);
    }

    public boolean isGrass() {
        return this.entityData.get(GRASS);
    }

    public void setSnowProgress(float progress) {
        this.entityData.set(SNOW_PROGRESS, progress);
    }

    public float getSnowProgress() {
        return this.entityData.get(SNOW_PROGRESS);
    }

    private void stopAllAnimation() {
        this.rollAnimationState.stop();
        this.startRollAnimationState.stop();
    }

    @Override
    public EntityDimensions getDefaultDimensions(Pose p_316664_) {
        return p_316664_ == Pose.SPIN_ATTACK ? SPIN_DIMENSIONS.scale(this.getAgeScale()) : super.getDefaultDimensions(p_316664_);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Grass", this.isGrass());
        tag.putFloat("SnowProgress", this.getSnowProgress());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setGrass(tag.getBoolean("Grass"));
        this.setSnowProgress(tag.getFloat("SnowProgress"));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getPose() == Pose.SPIN_ATTACK) {
            if (this.level().getBlockState(this.getOnPos()).is(BlockTags.SNOW)) {
                if (this.getSnowProgress() <= 1.5F) {
                    this.setSnowProgress(this.getSnowProgress() + 0.05F);
                }
            }

            if (this.level().getBlockState(this.blockPosition()).is(BlockTags.SNOW)) {
                if (this.getSnowProgress() <= 1.5F) {
                    this.setSnowProgress(this.getSnowProgress() + 0.05F);
                }
            }
        }
    }


    @Override
    public boolean canSpawnSprintParticle() {
        return super.canSpawnSprintParticle() || this.getPose() == Pose.SPIN_ATTACK;
    }

    @Override
    protected void playStepSound(BlockPos p_20135_, BlockState p_20136_) {
        super.playStepSound(p_20135_, p_20136_);
        this.playSound(SoundEvents.STONE_STEP, 0.8F, this.getVoicePitch());
    }

    @Override
    protected float nextStep() {
        if (this.getPose() == Pose.SPIN_ATTACK) {
            return this.moveDist + 0.25F;
        }
        return super.nextStep();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new RollGoal(this, 20, 30));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 0.8F, false));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true).setUnseenMemoryTicks(300));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 16.0)
                .add(Attributes.ARMOR, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.24)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
                .add(Attributes.ATTACK_DAMAGE, 2)
                .add(Attributes.STEP_HEIGHT, 1.0);
    }

    public Crackiness.Level getCrackiness() {
        return Crackiness.GOLEM.byFraction(this.getHealth() / this.getMaxHealth());
    }

    protected void dealDamage(LivingEntity livingentity) {
        if (this.isAlive() && getPose() == Pose.SPIN_ATTACK) {
            boolean flag = livingentity.isDamageSourceBlocked(this.damageSources().mobAttack(this));
            float f1 = (float) Mth.clamp(livingentity.getDeltaMovement().horizontalDistanceSqr() * 1.5F, 0.5F, 3.0F);
            float f2 = flag ? 0.25F + this.getSnowProgress() * 0.25F : 1.0F + this.getSnowProgress() * 0.5F;
            double d1 = this.getX() - livingentity.getX();
            double d2 = this.getZ() - livingentity.getZ();
            double d3 = livingentity.getX() - this.getX();
            double d4 = livingentity.getZ() - this.getZ();
            if (this.level() instanceof ServerLevel serverLevel) {
                if (livingentity.hurtServer(serverLevel, this.damageSources().mobAttack(this), Mth.floor(getAttackDamage() * 1.5F + this.getSnowProgress()))) {
                    this.playSound(SoundEvents.PLAYER_ATTACK_KNOCKBACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                    livingentity.knockback(f2 * f1, d1, d2);
                }
            }
        }
    }

    @Override
    public void push(Entity p_33636_) {
        if (p_33636_ instanceof LivingEntity && !(p_33636_ instanceof Gokkur) && !(p_33636_ instanceof Player)) {
            this.dealDamage((LivingEntity) p_33636_);
        }
        super.push(p_33636_);
    }

    @Override
    public void playerTouch(Player p_20081_) {
        super.playerTouch(p_20081_);
        this.dealDamage(p_20081_);
    }

    @Override
    public boolean hurtServer(ServerLevel p_376221_, DamageSource p_376460_, float p_376610_) {
        ItemStack itemstack = p_376460_.getWeaponItem();

        float damageScale = 1;

        if (itemstack != null && itemstack.canPerformAction(ItemAbilities.PICKAXE_DIG)) {
            damageScale = 2;
        }


        if (p_376460_.is(DamageTypeTags.IS_PROJECTILE)) {
            damageScale = 0.25F;
        }

        Crackiness.Level crackiness$level = this.getCrackiness();
        boolean flag = super.hurtServer(p_376221_, p_376460_, p_376610_ * damageScale);
        if (flag && this.getCrackiness() != crackiness$level) {
            this.playSound(SoundEvents.STONE_BREAK, 1.0F, 1.0F);

            p_376221_.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, this.getEntityBlocks()), this.getRandomX(this.getBbWidth() / 2F), this.getRandomY(), this.getRandomZ(this.getBbWidth() / 2F), 10, this.random.nextIntBetweenInclusive(-2, 2) * 0.15F, this.random.nextIntBetweenInclusive(-2, 2) * 0.15F, this.random.nextIntBetweenInclusive(-2, 2) * 0.15F, 0.15F);
            if (this.getSnowProgress() > 0.25F) {
                this.setSnowProgress(this.getSnowProgress() - 0.25F);
                p_376221_.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.SNOW_BLOCK.defaultBlockState()), this.getRandomX(this.getBbWidth() / 2F), this.getRandomY(), this.getRandomZ(this.getBbWidth() / 2F), 10, this.random.nextIntBetweenInclusive(-2, 2) * 0.15F, this.random.nextIntBetweenInclusive(-2, 2) * 0.15F, this.random.nextIntBetweenInclusive(-2, 2) * 0.15F, 0.15F);
            } else if (this.getSnowProgress() > 0) {
                this.setSnowProgress(0);
                p_376221_.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.SNOW_BLOCK.defaultBlockState()), this.getRandomX(this.getBbWidth() / 2F), this.getRandomY(), this.getRandomZ(this.getBbWidth() / 2F), 5, this.random.nextIntBetweenInclusive(-2, 2) * 0.15F, this.random.nextIntBetweenInclusive(-2, 2) * 0.15F, this.random.nextIntBetweenInclusive(-2, 2) * 0.15F, 0.15F);
            }
        }

        return flag;
    }

    public BlockState getEntityBlocks() {
        return FrostBlocks.FRIGID_STONE.get().defaultBlockState();
    }

    @Override
    public ProjectileDeflection deflection(Projectile p_319824_) {
        return this.hasPose(Pose.SPIN_ATTACK) ? PROJECTILE_DEFLECTION : super.deflection(p_319824_);
    }

    protected float getAttackDamage() {
        return (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    @Override
    protected void blockedByShield(LivingEntity p_21246_) {
        super.blockedByShield(p_21246_);
        if (this.isAlive() && this.getPose() == Pose.SPIN_ATTACK) {
            this.playSound(SoundEvents.PLAYER_ATTACK_KNOCKBACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);

            this.knockback(0.8F, p_21246_.getX() - this.getX(), p_21246_.getZ() - this.getZ());
            this.setPose(Pose.STANDING);
            CameraCore.addCameraHolderList(this.level(), new CameraHolder(6, 30, GlobalVec3.of(this.level().dimension(), this.position())));
        }
    }

    @Override
    protected int decreaseAirSupply(int p_28882_) {
        return p_28882_;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        return effect.getEffect() != MobEffects.POISON && super.canBeAffected(effect);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance p_21435_, EntitySpawnReason p_21436_, @Nullable SpawnGroupData p_21437_) {
        if (serverLevelAccessor.getBiome(this.blockPosition()).is(FrostTags.Biomes.GRASS_FROST_BIOME)) {
            this.setGrass(true);
        }

        return super.finalizeSpawn(serverLevelAccessor, p_21435_, p_21436_, p_21437_);
    }
}