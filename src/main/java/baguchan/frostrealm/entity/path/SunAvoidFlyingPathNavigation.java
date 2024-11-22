package baguchan.frostrealm.entity.path;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Node;

public class SunAvoidFlyingPathNavigation extends FlyingPathNavigation {
    private boolean avoidSun;

    public SunAvoidFlyingPathNavigation(PathfinderMob mob, Level level) {
        super(mob, level);
    }

    @Override
    protected void trimPath() {
        super.trimPath();
        if (this.avoidSun) {
            if (this.level.canSeeSky(BlockPos.containing(this.mob.getX(), this.mob.getY() + 0.5, this.mob.getZ()))) {
                return;
            }

            for (int i = 0; i < this.path.getNodeCount(); i++) {
                Node node = this.path.getNode(i);
                if (this.level.canSeeSky(new BlockPos(node.x, node.y, node.z))) {
                    this.path.truncateNodes(i);
                    return;
                }
            }
        }
    }

    public void setAvoidSun(boolean p_26491_) {
        this.avoidSun = p_26491_;
    }
}
