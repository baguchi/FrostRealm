package baguchan.frostrealm.entity.hostile;

import bagu_chan.bagus_lib.client.camera.CameraCore;
import bagu_chan.bagus_lib.client.camera.holder.CameraHolder;
import bagu_chan.bagus_lib.util.GlobalVec3;
import baguchan.frostrealm.entity.goal.RollGoal;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;

public class Gokkur extends Monster {
    protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(Gokkur.class, EntityDataSerializers.BYTE);

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

    private void stopAllAnimation() {
        this.rollAnimationState.stop();
        this.startRollAnimationState.stop();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder p_326192_) {
        super.defineSynchedData(p_326192_);
        p_326192_.define(DATA_FLAGS_ID, (byte) 0);
    }

    @Override
    public EntityDimensions getDefaultDimensions(Pose p_316664_) {
        return p_316664_ == Pose.SPIN_ATTACK ? SPIN_DIMENSIONS.scale(this.getAgeScale()) : super.getDefaultDimensions(p_316664_);
    }


    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RollGoal(this, 60));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.1F, false));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Raider.class).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true).setUnseenMemoryTicks(300));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 22.0)
                .add(Attributes.MOVEMENT_SPEED, 0.24)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
                .add(Attributes.ATTACK_DAMAGE, 2.0)
                .add(Attributes.STEP_HEIGHT, 1.0);
    }

    protected void dealDamage(LivingEntity livingentity) {
        if (this.isAlive() && getPose() == Pose.SPIN_ATTACK) {
            boolean flag = livingentity.isDamageSourceBlocked(this.damageSources().mobAttack(this));
            float f1 = (float) Mth.clamp(livingentity.getDeltaMovement().horizontalDistanceSqr() * 1.5F, 0.5F, 3.0F);
            float f2 = flag ? 0.25F : 1.0F;
            double d1 = this.getX() - livingentity.getX();
            double d2 = this.getZ() - livingentity.getZ();
            double d3 = livingentity.getX() - this.getX();
            double d4 = livingentity.getZ() - this.getZ();
            if (livingentity.hurt(this.damageSources().mobAttack(this), Mth.floor(getAttackDamage() * 1.25F))) {
                this.playSound(SoundEvents.PLAYER_ATTACK_KNOCKBACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                livingentity.knockback(f2 * f1, d1, d2);
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
}