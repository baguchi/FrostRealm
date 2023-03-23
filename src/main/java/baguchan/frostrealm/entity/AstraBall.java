package baguchan.frostrealm.entity;

import baguchan.frostrealm.client.LivingEntityFlyingSoundInstance;
import baguchan.frostrealm.entity.goal.MoveAttackerGoal;
import baguchan.frostrealm.registry.FrostSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class AstraBall extends Monster {
    public AstraBall(EntityType<? extends Monster> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        this.moveControl = new FlyingMoveControl(this, 10, false);
    }

    protected PathNavigation createNavigation(Level p_29417_) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, p_29417_);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MoveAttackerGoal(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomFlyingGoal(this, 0.95D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.ATTACK_DAMAGE, 2.0F).add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.FOLLOW_RANGE, 20.0D).add(Attributes.ARMOR, 6.0F).add(Attributes.KNOCKBACK_RESISTANCE, 0.5F).add(Attributes.MOVEMENT_SPEED, 0.24D).add(Attributes.FLYING_SPEED, 0.24D);
    }

    public void push(Entity p_33636_) {
        if (p_33636_.isPushable() && !(p_33636_ instanceof AstraBall) && !(p_33636_ instanceof Player)) {
            p_33636_.hurt(this.damageSources().mobAttack(this), 2.0F);
        }
        super.push(p_33636_);
    }

    @Override
    public void playerTouch(Player p_20081_) {
        super.playerTouch(p_20081_);

        p_20081_.hurt(this.damageSources().mobAttack(this), 2.0F);

    }

    @Override
    public void travel(Vec3 p_21280_) {
        if (this.isControlledByLocalInstance()) {
            if (this.isInWater()) {
                this.moveRelative(0.02F, p_21280_);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale((double) 0.8F));
            } else if (this.isInLava()) {
                this.moveRelative(0.02F, p_21280_);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
            } else {
                this.moveRelative(this.getSpeed() * 0.21F, p_21280_);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale((double) 0.91F));
            }
        }

        this.calculateEntityAnimation(false);
    }

    public boolean causeFallDamage(float p_148989_, float p_148990_, DamageSource p_148991_) {
        return false;
    }

    protected void checkFallDamage(double p_29370_, boolean p_29371_, BlockState p_29372_, BlockPos p_29373_) {
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket clientboundAddMobPacket) {
        super.recreateFromPacket(clientboundAddMobPacket);
        LivingEntityFlyingSoundInstance.playSound(this, FrostSounds.MORTAR.get());
    }

    @Override
    protected float getStandingEyeHeight(Pose p_21131_, EntityDimensions p_21132_) {
        return p_21132_.height * 0.5F;
    }

}
