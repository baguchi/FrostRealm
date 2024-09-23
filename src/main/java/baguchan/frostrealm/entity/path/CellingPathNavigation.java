package baguchan.frostrealm.entity.path;

import baguchan.frostrealm.entity.hostile.CellingMonster;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;

public class CellingPathNavigation extends GroundPathNavigation {
    public final CellingMonster cellingMonster;

    public CellingPathNavigation(CellingMonster mob, Level level) {
        super(mob, level);
        this.cellingMonster = mob;
    }

    @Override
    protected boolean canUpdatePath() {
        return true;
    }

    protected PathFinder createPathFinder(int p_26598_) {
        this.nodeEvaluator = new CellingNodeEvaluator();
        this.nodeEvaluator.setCanPassDoors(true);
        this.nodeEvaluator.setCanOpenDoors(false);
        this.nodeEvaluator.setCanFloat(true);
        return new PathFinder(this.nodeEvaluator, p_26598_);
    }

    protected double getGroundY(Vec3 p_186132_) {
        BlockPos blockpos = BlockPos.containing(p_186132_);
        return WalkNodeEvaluator.getFloorLevel(this.level, blockpos);
    }

    protected boolean canMoveDirectly(Vec3 p_186138_, Vec3 p_186139_) {
        return true;
    }

    public boolean isStableDestination(BlockPos p_26608_) {
        return !this.level.getBlockState(p_26608_.below()).isAir() || this.cellingMonster.getAttachFacing() != Direction.DOWN;
    }


    public void setCanFloat(boolean p_26563_) {
        this.nodeEvaluator.setCanFloat(p_26563_);
    }
}