package baguchan.frostrealm.entity.animal;

import baguchan.frostrealm.entity.goal.RandomMoveGoal;
import baguchan.frostrealm.registry.FrostEntities;
import baguchan.frostrealm.registry.FrostSounds;
import baguchan.frostrealm.registry.FrostTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;

public class Seal extends Animal {
    private static final EntityDimensions BABY_DIMENSIONS = FrostEntities.SEAL.get().getDimensions().scale(0.5F).withEyeHeight(0.25F);

    public static final Ingredient FOOD_ITEMS = Ingredient.of(ItemTags.FISHES);

    @javax.annotation.Nullable
    protected RandomStrollGoal randomStrollGoal;


    public final AnimationState fartAnimationState = new AnimationState();
    public int gasTick;
    private int destroyBlocksTick;

    public Seal(EntityType<? extends Seal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.4F, 0.9F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
        this.setPathfindingMalus(PathType.DOOR_IRON_CLOSED, -1.0F);
        this.setPathfindingMalus(PathType.DOOR_WOOD_CLOSED, -1.0F);
        this.setPathfindingMalus(PathType.DOOR_OPEN, -1.0F);
    }

    @Override
    public EntityDimensions getDefaultDimensions(Pose p_316516_) {
        return this.isBaby() ? BABY_DIMENSIONS : super.getDefaultDimensions(p_316516_);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.randomStrollGoal = new RandomMoveGoal(this, 1.0D, 30);

        this.goalSelector.addGoal(0, new BreathAirGoal(this) {
            @Override
            public boolean canUse() {
                return getAirSupply() < 600;
            }
        });
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.3F));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.15D, FOOD_ITEMS, false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new AvoidEntityGoal<>(this, PolarBear.class, 8F, 1.25F, 1.35F));

        this.goalSelector.addGoal(6, new MeleeAttackGoal(this, 1.25F, true));
        this.goalSelector.addGoal(7, randomStrollGoal);
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AbstractFish.class, true));

    }

    @Override
    public boolean isFood(ItemStack p_27600_) {
        return FOOD_ITEMS.test(p_27600_);
    }


    @Override
    public void baseTick() {
        super.baseTick();
        if (this.level().isClientSide) {
            if (this.gasTick > 0) {
                this.gasTick--;
            }

            if (this.gasTick <= 0 && this.fartAnimationState.isStarted()) {
                this.fartAnimationState.stop();
            }
        }
    }

    public void playAmbientSound() {
        if (this.random.nextInt(6) == 0) {
            this.makeSound(FrostSounds.SEAL_FART.get());
            if (!this.level().isClientSide()) {
                this.level().broadcastEntityEvent(this, (byte) 61);
            }
        } else {
            this.makeSound(this.getAmbientSound());
        }
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return FrostSounds.SEAL_IDLE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return FrostSounds.SEAL_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return FrostSounds.SEAL_DEATH.get();
    }


    @Override
    public void handleEntityEvent(byte p_27562_) {
        if (p_27562_ == 61) {
            this.fartAnimationState.start(this.tickCount);
            this.gasTick = 80;
        } else {
            super.handleEntityEvent(p_27562_);
        }
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_28332_, DifficultyInstance p_28333_, MobSpawnType p_28334_, @javax.annotation.Nullable SpawnGroupData p_28335_) {
        boolean flag = false;
        if (p_28335_ instanceof AgeableMobGroupData) {
            if (((AgeableMobGroupData) p_28335_).getGroupSize() >= 2) {
                flag = true;
            }
        } else {
            p_28335_ = new AgeableMobGroupData(true);
        }

        if (flag) {
            this.setAge(-24000);
        }

        this.setAirSupply(this.getMaxAirSupply());
        this.setXRot(0.0F);
        return super.finalizeSpawn(p_28332_, p_28333_, p_28334_, p_28335_);
    }

    public float getWalkTargetValue(BlockPos p_27573_, LevelReader p_27574_) {
        if (p_27574_.getBlockState(p_27573_.below()).is(FrostTags.Blocks.SEAL_SPAWNABLE) || p_27574_.getBlockState(p_27573_.below()).is(Blocks.SNOW_BLOCK)) {
            return 12.0F;
        }
        return p_27574_.getBlockState(p_27573_).is(Blocks.WATER) ? 10.0F : p_27574_.getPathfindingCostFromLightLevels(p_27573_) - 0.5F;
    }

    public static boolean checkSealSpawnRules(EntityType<? extends Animal> p_218105_, LevelAccessor p_218106_, MobSpawnType p_218107_, BlockPos p_218108_, RandomSource p_218109_) {
        return p_218106_.getBlockState(p_218108_.below()).is(FrostTags.Blocks.SEAL_SPAWNABLE) && isBrightEnoughToSpawn(p_218106_, p_218108_);
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 8;
    }

    protected PathNavigation createNavigation(Level p_27480_) {
        return new AmphibiousPathNavigation(this, p_27480_);
    }

    public void travel(Vec3 p_27490_) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(this.getSpeed(), p_27490_);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
        } else {
            super.travel(p_27490_);
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 16.0D).add(Attributes.ATTACK_DAMAGE, 3.0D).add(Attributes.STEP_HEIGHT, 1.0F).add(Attributes.FOLLOW_RANGE, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.24D);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return FrostEntities.SEAL.get().create(p_146743_);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.isAlive()) {
            if (this.isInWater()) {
                if (this.randomStrollGoal != null) {
                    this.randomStrollGoal.setInterval(10);
                }

            } else {
                if (this.randomStrollGoal != null) {
                    this.randomStrollGoal.setInterval(180);
                }
            }
        }
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        int l;
        if (this.destroyBlocksTick > 0) {
            --this.destroyBlocksTick;
        }

        if (this.verticalCollision && this.isInWater()) {
            if (this.destroyBlocksTick == 0 && EventHooks.canEntityGrief(this.level(), this)) {
                if (net.neoforged.neoforge.event.EventHooks.canEntityGrief(this.level(), this)) {
                    boolean flag = false;
                    AABB aabb = this.getBoundingBox().inflate(0.2);

                    for (BlockPos blockpos : BlockPos.betweenClosed(
                            Mth.floor(aabb.minX), Mth.floor(aabb.minY), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ)
                    )) {
                        BlockState blockstate = this.level().getBlockState(blockpos);
                        Block block = blockstate.getBlock();
                        if (blockstate.is(Blocks.ICE)) {
                            flag = this.level().destroyBlock(blockpos, true, this) || flag;
                        }
                        if (flag) {
                            this.level().setBlockAndUpdate(blockpos, Blocks.WATER.defaultBlockState());
                        }
                    }
                }
                this.destroyBlocksTick = 20;
            }
        }
    }

    public int getMaxAirSupply() {
        return 4800;
    }

    protected int increaseAirSupply(int p_28389_) {
        return this.getMaxAirSupply();
    }
}
