package baguchan.frostrealm.entity.animal;

import baguchan.frostrealm.block.SilkMoonEggBlock;
import baguchan.frostrealm.block.SnowPileQuailEggBlock;
import baguchan.frostrealm.entity.IHasEgg;
import baguchan.frostrealm.entity.goal.FindAndPlaceEggGoal;
import baguchan.frostrealm.entity.goal.SeekShelterEvenBlizzardGoal;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostEntities;
import baguchan.frostrealm.registry.FrostTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class SilkMoon extends FrostAnimal implements IHasEgg {
    private static final EntityDataAccessor<Boolean> HAS_EGG = SynchedEntityData.defineId(SilkMoon.class, EntityDataSerializers.BOOLEAN);

    public SilkMoon(EntityType<? extends SilkMoon> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }

    public static boolean checkSilkSpawnRules(EntityType<? extends Animal> p_27578_, LevelAccessor p_27579_, EntitySpawnReason p_27580_, BlockPos p_27581_, RandomSource p_27582_) {
        return (p_27579_.getBlockState(p_27581_.below()).is(FrostTags.Blocks.ANIMAL_SPAWNABLE)) && p_27579_.getRawBrightness(p_27581_, 0) <= 10;
    }

    @Override
    public float getWalkTargetValue(BlockPos p_27573_, LevelReader p_27574_) {
        return 0.5F - p_27574_.getPathfindingCostFromLightLevels(p_27573_);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return FrostEntities.SILK_MOON.get().create(p_146743_, EntitySpawnReason.BREEDING);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HAS_EGG, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new FindAndPlaceEggGoal<>(this, 0.85D) {
            @Override
            public void afterPlaceEgg() {
                level().playSound(null, blockPos.above(), SoundEvents.TURTLE_LAY_EGG, SoundSource.BLOCKS, 0.3F, 0.9F + level().random.nextFloat() * 0.2F);
                level().setBlock(blockPos.above(), FrostBlocks.SILK_MOON_EGG.get().defaultBlockState().setValue(SnowPileQuailEggBlock.EGGS, Integer.valueOf(random.nextInt(1) + 1)), 3);
            }

            @Override
            protected boolean isValidTarget(LevelReader p_25619_, BlockPos p_25620_) {
                return SilkMoonEggBlock.onDirt(p_25619_, p_25620_) && p_25619_.getBlockState(p_25620_.above()).isAir();
            }
        });
        this.goalSelector.addGoal(2, new SeekShelterEvenBlizzardGoal(this, 1.1D, true));

        this.goalSelector.addGoal(3, new WaterAvoidingRandomFlyingGoal(this, 0.95D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    @Override
    protected PathNavigation createNavigation(Level p_29417_) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, p_29417_);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes().add(Attributes.MOVEMENT_SPEED, 0.21F).add(Attributes.FLYING_SPEED, 0.24F).add(Attributes.MAX_HEALTH, 12.0);
    }

    @Override
    public void aiStep() {
        super.aiStep();
    }

    public boolean hasEgg() {
        return this.entityData.get(HAS_EGG);
    }

    public void setHasEgg(boolean hasEgg) {
        this.entityData.set(HAS_EGG, hasEgg);
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


    @Override
    public void addAdditionalSaveData(CompoundTag p_218709_) {
        super.addAdditionalSaveData(p_218709_);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag p_218698_) {
        super.readAdditionalSaveData(p_218698_);
    }

    @Override
    public boolean isFood(ItemStack p_27600_) {
        return p_27600_.is(ItemTags.LEAVES);
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    @Override
    public void setBaby(boolean p_218500_) {
    }

    @Override
    protected void checkFallDamage(double p_20990_, boolean p_20991_, BlockState p_20992_, BlockPos p_20993_) {
    }
}
