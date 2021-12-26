package baguchan.utils;

import net.minecraft.world.entity.Entity;

public class MovementUtils {
	public static double movementDistanceSqr(Entity entity) {
		return entity.getDeltaMovement().x * entity.getDeltaMovement().x + entity.getDeltaMovement().y * entity.getDeltaMovement().y + entity.getDeltaMovement().z * entity.getDeltaMovement().z;
	}
}
