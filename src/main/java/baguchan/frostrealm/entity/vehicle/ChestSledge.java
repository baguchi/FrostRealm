package baguchan.frostrealm.entity.vehicle;

import baguchan.frostrealm.entity.FrostWolf;
import baguchan.frostrealm.registry.FrostEntities;
import baguchan.frostrealm.registry.FrostItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ChestSledge extends ChestBoat {
    public ChestSledge(EntityType<? extends Boat> p_38290_, Level p_38291_) {
        super(p_38290_, p_38291_);
        this.maxUpStep = 1.0F;
    }

    public ChestSledge(Level p_38293_, double p_38294_, double p_38295_, double p_38296_) {
        this(FrostEntities.CHEST_SLEDGE.get(), p_38293_);
        this.setPos(p_38294_, p_38295_, p_38296_);
        this.xo = p_38294_;
        this.yo = p_38295_;
        this.zo = p_38296_;
        this.maxUpStep = 1.0F;
    }

    public InteractionResult interact(Player p_38330_, InteractionHand p_38331_) {
        if (p_38330_.isSecondaryUseActive()) {
            boolean flag = false;

            int i = (int) p_38330_.getX();
            int j = (int) p_38330_.getY();
            int k = (int) p_38330_.getZ();
            for (Mob mob : p_38330_.level.getEntitiesOfClass(Mob.class, new AABB((double) i - 7.0D, (double) j - 7.0D, (double) k - 7.0D, (double) i + 7.0D, (double) j + 7.0D, (double) k + 7.0D))) {
                if (mob.getLeashHolder() == p_38330_) {

                    mob.setLeashedTo(this, true);
                    flag = true;
                }
            }
            return flag ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
        }
        return super.interact(p_38330_, p_38331_);
    }

    @Override
    public ItemStack getPickResult() {
        return FrostItems.OAK_CHEST_SLEDGE.get().getDefaultInstance();
    }

    @Override
    public void tick() {
        this.boatMoving();

        super.tick();

    }

    public void boatMoving() {
        if (this.isVehicle()) {
            List<FrostWolf> frostWolfList = this.level.getEntitiesOfClass(FrostWolf.class, new AABB(this.blockPosition()).inflate(7.0F)).stream().filter(entity -> entity.getLeashHolder() == this).toList();

            for (FrostWolf wolf : frostWolfList) {
                if (wolf.isTame() && !wolf.isInSittingPose()) {
                    wolf.goalSelector.enableControlFlag(Goal.Flag.MOVE);
                    wolf.getNavigation().moveTo(wolf.getX() + this.getLookAngle().x * 4, wolf.getY() + this.getLookAngle().y * 4, wolf.getZ() + this.getLookAngle().z * 4, 1.3F);
                }
                if (this.distanceTo(wolf) > 3) {
                    float f = this.getBlockStateOn().is(BlockTags.SNOW) ? 1.2F : this.getBlockSpeedFactor();
                    double d0 = (wolf.getX() - this.getX()) * 0.1F * f;
                    double d1 = (wolf.getY() - this.getY()) * 0.1F * f;
                    double d2 = (wolf.getZ() - this.getZ()) * 0.1F * f;
                    this.setDeltaMovement(d0, this.getDeltaMovement().y, d2);
                }
            }
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }
}
