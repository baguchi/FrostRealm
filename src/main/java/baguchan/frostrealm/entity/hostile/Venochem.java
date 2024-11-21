package baguchan.frostrealm.entity.hostile;

import baguchan.frostrealm.entity.goal.ShootProjectileAnimationGoal;
import baguchan.frostrealm.entity.projectile.VenomBall;
import baguchan.frostrealm.registry.FrostEntities;
import baguchi.bagus_lib.entity.goal.AnimateAttackGoal;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class Venochem extends Monster implements RangedAttackMob {

    private int attackTick;
    public AnimationState attackAnimationState = new AnimationState();
    public AnimationState shootAnimationState = new AnimationState();

    public Venochem(EntityType<? extends Venochem> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            //handle the attack animation
            if (this.attackAnimationState.isStarted()) {
                if (this.attackTick >= 40) {
                    this.attackAnimationState.stop();
                } else {
                    ++this.attackTick;
                }
            }
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RestrictSunGoal(this));
        this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.2));
        this.goalSelector.addGoal(4, new AnimateAttackGoal(this, 1.1F, (int) (0.25 * 20), 20) {
            @Override
            public boolean canUse() {
                return super.canUse() && this.mob.distanceToSqr(this.mob.getTarget()) <= 3 * 3;
            }

            @Override
            public boolean canContinueToUse() {
                return super.canContinueToUse() && this.mob.distanceToSqr(this.mob.getTarget()) <= 6 * 6;
            }
        });
        this.goalSelector.addGoal(5, new ShootProjectileAnimationGoal(this, 0.8F, 20, 20 * 3, 10.0F));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.8F));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false).setUnseenMemoryTicks(300));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, false).setUnseenMemoryTicks(300));
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 61) {
            this.attackAnimationState.start(this.tickCount);
            this.attackTick = 0;
            this.shootAnimationState.stop();
        } else if (pId == 62) {
            this.shootAnimationState.start(this.tickCount);
            this.attackAnimationState.stop();
        } else if (pId == 63) {
            this.shootAnimationState.stop();
        } else {
            super.handleEntityEvent(pId);
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 14.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.FOLLOW_RANGE, 16.0)
                .add(Attributes.ATTACK_DAMAGE, 2.0);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        VenomBall ball = new VenomBall(FrostEntities.VENOM_BALL.get(), this, this.level());
        double d0 = target.getEyeY() - this.getEyeY();
        double d1 = target.getX() - this.getX();
        double d3 = target.getZ() - this.getZ();
        double d4 = Math.sqrt(d1 * d1 + d3 * d3) * 0.2F;
        ball.setXRot(ball.getXRot() - -20.0F);
        ball.shoot(d1, d0 + d4, d3, 0.8F, 6.0F);
        this.playSound(SoundEvents.SLIME_ATTACK, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(ball);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        return effect.getEffect() != MobEffects.POISON && super.canBeAffected(effect);
    }
}