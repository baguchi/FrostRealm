package baguchan.frostrealm.entity.goal;

import baguchan.frostrealm.entity.Yeti;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;

import java.util.EnumSet;
import java.util.List;

public class GetFoodGoal<T extends Yeti> extends Goal {
	private final T mob;

	public GetFoodGoal(T p_i50572_2_) {
		this.mob = p_i50572_2_;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	}

	public boolean canUse() {

		List<ItemEntity> list = this.mob.level.getEntitiesOfClass(ItemEntity.class, this.mob.getBoundingBox().inflate(16.0D, 8.0D, 16.0D), Yeti.ALLOWED_ITEMS);
		if (!list.isEmpty() && this.mob.hasLineOfSight(list.get(0))) {
			return this.mob.getNavigation().moveTo(list.get(0), 1.1F);
		}

		return false;

	}

	public void tick() {
		if (this.mob.getNavigation().getTargetPos().closerThan(this.mob.blockPosition(), 1.414D)) {
			List<ItemEntity> list = this.mob.level.getEntitiesOfClass(ItemEntity.class, this.mob.getBoundingBox().inflate(4.0D, 4.0D, 4.0D), Yeti.ALLOWED_ITEMS);
			if (!list.isEmpty()) {
				this.mob.pickUpItem(list.get(0));
			}
		}

	}
}
