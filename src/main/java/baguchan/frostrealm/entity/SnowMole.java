package baguchan.frostrealm.entity;

import baguchan.frostrealm.entity.movecontrol.SnowMoveControl;
import baguchan.frostrealm.entity.path.SnowPathNavigation;
import baguchan.frostrealm.registry.FrostEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class SnowMole extends Animal {
	public SnowMole(EntityType<? extends SnowMole> p_27557_, Level p_27558_) {
		super(p_27557_, p_27558_);
		this.moveControl = new SnowMoveControl(this, 85, 10, 1.0F);
		this.lookControl = new SmoothSwimmingLookControl(this, 10);
	}

	protected PathNavigation createNavigation(Level p_27480_) {
		return new SnowPathNavigation(this, p_27480_);
	}

	public void travel(Vec3 p_27490_) {
		if (this.isEffectiveAi() && this.isInPowderSnow) {
			this.moveRelative(0.01F, p_27490_);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.6D));
		} else {
			super.travel(p_27490_);
		}

	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 12.0D).add(Attributes.FOLLOW_RANGE, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.26D);
	}

	public static boolean checkSnowMoleSpawnRules(EntityType<? extends Animal> p_27578_, LevelAccessor p_27579_, MobSpawnType p_27580_, BlockPos p_27581_, RandomSource p_27582_) {
		return p_27579_.getBlockState(p_27581_.below()).is(Blocks.SNOW_BLOCK) && p_27579_.getRawBrightness(p_27581_, 0) > 8;
	}

	@Nullable
	@Override
	public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
		return FrostEntities.SNOW_MOLE.get().create(p_146743_);
	}
}
