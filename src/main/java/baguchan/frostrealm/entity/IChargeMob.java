package baguchan.frostrealm.entity;

import net.minecraft.world.entity.LivingEntity;

public interface IChargeMob {

	void onCharge();

	void onChargeDamage(LivingEntity damageEntity);
}
