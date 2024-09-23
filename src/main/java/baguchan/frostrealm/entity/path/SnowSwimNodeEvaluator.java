package baguchan.frostrealm.entity.path;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.pathfinder.*;

import javax.annotation.Nullable;

public class SnowSwimNodeEvaluator extends WalkNodeEvaluator {
	public SnowSwimNodeEvaluator() {
	}

	public void prepare(PathNavigationRegion p_164671_, Mob p_164672_) {
		super.prepare(p_164671_, p_164672_);
        p_164672_.setPathfindingMalus(PathType.POWDER_SNOW, 0.0F);
	}

	public void done() {
		super.done();
	}

	@Nullable
	public Node getStart() {
		return this.getStartNode(new BlockPos(Mth.floor(this.mob.getBoundingBox().minX), Mth.floor(this.mob.getBoundingBox().minY + 0.5D), Mth.floor(this.mob.getBoundingBox().minZ)));
	}


	@Nullable
    public Target getTarget(double p_164662_, double p_164663_, double p_164664_) {
        return this.getTargetNodeAt(Mth.floor(p_164662_), Mth.floor(p_164663_ + 0.5D), Mth.floor(p_164664_));
	}

	public int getNeighbors(Node[] p_164676_, Node p_164677_) {
		int i = super.getNeighbors(p_164676_, p_164677_);
        PathType blockpathtypes = this.getCachedPathType(p_164677_.x, p_164677_.y + 1, p_164677_.z);
        PathType blockpathtypes1 = this.getCachedPathType(p_164677_.x, p_164677_.y, p_164677_.z);
		int j;
        if (this.mob.getPathfindingMalus(blockpathtypes) >= 0.0F && blockpathtypes1 != PathType.STICKY_HONEY) {
            j = Mth.floor(Math.max(1.0F, this.mob.maxUpStep()));
		} else {
			j = 0;
		}

		double d0 = this.getFloorLevel(new BlockPos(p_164677_.x, p_164677_.y, p_164677_.z));
		Node node = this.findAcceptedNode(p_164677_.x, p_164677_.y + 1, p_164677_.z, Math.max(0, j - 1), d0, Direction.UP, blockpathtypes1);
		Node node1 = this.findAcceptedNode(p_164677_.x, p_164677_.y - 1, p_164677_.z, j, d0, Direction.DOWN, blockpathtypes1);
		if (this.isVerticalNeighborValid(node, p_164677_)) {
			p_164676_[i++] = node;
		}

        if (this.isVerticalNeighborValid(node1, p_164677_) && blockpathtypes1 != PathType.TRAPDOOR) {
			p_164676_[i++] = node1;
		}

		return i;
	}

	private boolean isVerticalNeighborValid(@Nullable Node p_230611_, Node p_230612_) {
		return this.isNeighborValid(p_230611_, p_230612_);
	}

	protected double getFloorLevel(BlockPos p_164674_) {
		return this.mob.wasInPowderSnow ? (double) p_164674_.getY() + 0.5D : super.getFloorLevel(p_164674_);
	}

	protected boolean isAmphibious() {
		return false;
	}

    public PathType getPathType(PathfindingContext p_164666_, int p_164667_, int p_164668_, int p_164669_) {
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        PathType blockpathtypes = getPathTypeStatic(p_164666_, blockpos$mutableblockpos.set(p_164667_, p_164668_, p_164669_));
        if (blockpathtypes == PathType.POWDER_SNOW) {
			for (Direction direction : Direction.values()) {
                PathType blockpathtypes1 = getPathTypeStatic(p_164666_, blockpos$mutableblockpos.set(p_164667_, p_164668_, p_164669_).move(direction));
                if (blockpathtypes1 == PathType.BLOCKED) {
                    return PathType.BLOCKED;
				}
			}

            return PathType.POWDER_SNOW;
		} else {
			return super.getPathType(p_164666_, p_164667_, p_164668_, p_164669_);
		}
	}
}