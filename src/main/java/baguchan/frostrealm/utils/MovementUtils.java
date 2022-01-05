package baguchan.frostrealm.utils;

import net.minecraft.world.entity.Entity;

public class MovementUtils {
	public static double movementDamageDistanceSqr(Entity entity) {
		return (entity.getDeltaMovement().x * entity.getDeltaMovement().x + entity.getDeltaMovement().z * entity.getDeltaMovement().z) * 20.0F;
	}
}
