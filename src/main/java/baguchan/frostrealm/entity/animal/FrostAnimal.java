package baguchan.frostrealm.entity.animal;

import baguchan.frostrealm.registry.FrostTags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;

public abstract class FrostAnimal extends Animal {
    protected FrostAnimal(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }

    public static boolean checkFrostAnimalSpawnRules(
            EntityType<? extends Animal> p_218105_, LevelAccessor p_218106_, EntitySpawnReason p_360742_, BlockPos p_218108_, RandomSource p_218109_
    ) {
        boolean flag = EntitySpawnReason.ignoresLightRequirements(p_360742_) || isBrightEnoughToSpawn(p_218106_, p_218108_);
        return p_218106_.getBlockState(p_218108_.below()).is(FrostTags.Blocks.ANIMAL_SPAWNABLE) && flag;
    }

    @Override
    public float getWalkTargetValue(BlockPos p_27573_, LevelReader p_27574_) {
        return p_27574_.getBlockState(p_27573_.below()).is(FrostTags.Blocks.ANIMAL_SPAWNABLE) ? 10.0F : p_27574_.getPathfindingCostFromLightLevels(p_27573_);
    }
}
