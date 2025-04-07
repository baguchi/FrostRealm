package baguchan.frostrealm.entity.animal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public abstract class TamableBiggerAnimal extends TamableAnimal {
    protected TamableBiggerAnimal(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
    }

    @Override
    public boolean shouldTryTeleportToOwner() {
        LivingEntity livingentity = this.getOwner();
        return livingentity != null && this.distanceToSqr(this.getOwner()) >= (double) 400.0F;
    }

    public void tryToTeleportToOwner() {
        LivingEntity livingentity = this.getOwner();
        if (livingentity != null) {
            this.teleportToAroundBlockPos(livingentity.blockPosition());
        }
    }

    private void teleportToAroundBlockPos(BlockPos p_350657_) {
        for (int i = 0; i < 10; i++) {
            int j = this.random.nextIntBetweenInclusive(-4, 4);
            int k = this.random.nextIntBetweenInclusive(-4, 4);
            if (Math.abs(j) >= 3 || Math.abs(k) >= 3) {
                int l = this.random.nextIntBetweenInclusive(-2, 2);
                if (this.maybeTeleportTo(p_350657_.getX() + j, p_350657_.getY() + l, p_350657_.getZ() + k)) {
                    return;
                }
            }
        }
    }

    private boolean maybeTeleportTo(int p_350930_, int p_350303_, int p_350410_) {
        if (!this.canTeleportTo(new BlockPos(p_350930_, p_350303_, p_350410_))) {
            return false;
        } else {
            this.snapTo((double) p_350930_ + (double) 0.5F, (double) p_350303_, (double) p_350410_ + (double) 0.5F, this.getYRot(), this.getXRot());
            this.navigation.stop();
            return true;
        }
    }

    private boolean canTeleportTo(BlockPos p_350767_) {
        PathType pathtype = WalkNodeEvaluator.getPathTypeStatic(this, p_350767_);
        if (pathtype != PathType.WALKABLE) {
            return false;
        } else {
            BlockState blockstate = this.level().getBlockState(p_350767_.below());
            if (!this.canFlyToOwner() && blockstate.getBlock() instanceof LeavesBlock) {
                return false;
            } else {
                BlockPos blockpos = p_350767_.subtract(this.blockPosition());
                return this.level().noCollision(this, this.getBoundingBox().move(blockpos));
            }
        }
    }
}
