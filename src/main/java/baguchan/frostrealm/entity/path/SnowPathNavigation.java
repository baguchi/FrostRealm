package baguchan.frostrealm.entity.path;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;

public class SnowPathNavigation extends PathNavigation {
	public SnowPathNavigation(Mob p_26594_, Level p_26595_) {
		super(p_26594_, p_26595_);
	}

	protected PathFinder createPathFinder(int p_26598_) {
		this.nodeEvaluator = new SnowSwimNodeEvaluator();
		this.nodeEvaluator.setCanPassDoors(true);
		this.nodeEvaluator.setCanOpenDoors(false);
		this.nodeEvaluator.setCanFloat(true);
		return new PathFinder(this.nodeEvaluator, p_26598_);
	}

	protected boolean canUpdatePath() {
		return true;
	}

	protected Vec3 getTempMobPos() {
		return new Vec3(this.mob.getX(), this.mob.getY(0.5D), this.mob.getZ());
	}

	protected double getGroundY(Vec3 p_186132_) {
		BlockPos blockpos = BlockPos.containing(p_186132_);
		return this.level.getBlockState(blockpos.below()).isAir() || this.level.getBlockState(blockpos).is(Blocks.POWDER_SNOW) ? p_186132_.y : WalkNodeEvaluator.getFloorLevel(this.level, blockpos);
	}

	protected boolean canMoveDirectly(Vec3 p_186138_, Vec3 p_186139_) {
		return this.mob.wasInPowderSnow ? isClearForMovementBetween(this.mob, p_186138_, p_186139_, false) : false;
	}

	public boolean isStableDestination(BlockPos p_26608_) {
		return !this.level.getBlockState(p_26608_.below()).isAir() || this.level.getBlockState(p_26608_).is(Blocks.POWDER_SNOW);
	}


	public void setCanFloat(boolean p_26563_) {
		this.nodeEvaluator.setCanFloat(p_26563_);
	}
}