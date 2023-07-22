package baguchan.frostrealm.entity;

import bagu_chan.bagus_lib.entity.goal.AnimatedAttackGoal;
import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
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
import net.minecraft.world.level.block.Blocks;

public class StrayWarrior extends AbstractSkeleton {
    public int attackAnimationTick;
    private final int attackAnimationLength = (int) (20 * 1.75);
    private final int attackAnimationLeftActionPoint = (int) ((int) attackAnimationLength - (20 * 0.655));
    public final AnimationState attackAnimationState = new AnimationState();


    public StrayWarrior(EntityType<? extends StrayWarrior> p_32133_, Level p_32134_) {
        super(p_32133_, p_32134_);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(4, new AnimatedAttackGoal(this, 1.2D, attackAnimationLeftActionPoint, attackAnimationLength) {
            @Override
            protected double getAttackReachSqr(LivingEntity p_25556_) {
                return (double) (getBbWidth() * 2.0F * getBbWidth() * 2.0F + 6.0F + p_25556_.getBbWidth());
            }
        });
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.25D);
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
        }
    }

    @Override
    public void handleEntityEvent(byte p_21375_) {
        if (p_21375_ == 4) {
            this.attackAnimationState.start(this.tickCount);
            this.attackAnimationTick = 0;
        } else {
            super.handleEntityEvent(p_21375_);
        }
    }

    protected void populateDefaultEquipmentSlots(RandomSource p_218949_, DifficultyInstance p_218950_) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(FrostItems.FROST_SPEAR.get()));
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(FrostItems.YETI_FUR_HELMET.get()));
    }

    @Override
    public boolean doHurtTarget(Entity p_21372_) {
        if (this.getMainHandItem().is(FrostItems.FROST_SPEAR.get())) {
            p_21372_.setTicksFrozen(200 + p_21372_.getTicksFrozen());
        }
        return super.doHurtTarget(p_21372_);
    }

    public static boolean checkStraySpawnRules(EntityType<StrayWarrior> p_219121_, ServerLevelAccessor p_219122_, MobSpawnType p_219123_, BlockPos p_219124_, RandomSource p_219125_) {
        BlockPos blockpos = p_219124_;

        do {
            blockpos = blockpos.above();
        } while (p_219122_.getBlockState(blockpos).is(Blocks.POWDER_SNOW));

        return checkMonsterSpawnRules(p_219121_, p_219122_, p_219123_, p_219124_, p_219125_) && (p_219123_ == MobSpawnType.SPAWNER || p_219122_.canSeeSky(blockpos.below()));
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
}
