package baguchan.frostrealm.entity;

import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public abstract class FrozenMonster extends Monster {
	public FrozenMonster(EntityType<? extends Monster> p_33002_, Level p_33003_) {
		super(p_33002_, p_33003_);
	}

	@Override
	public boolean hurt(DamageSource p_21016_, float p_21017_) {
		if (p_21016_.isFire()) {
			float f = 2.0F;
			p_21017_ = Mth.clamp(p_21017_ * 1.25F + 1.0F, 0.0F, this.getMaxHealth());
		}

		return super.hurt(p_21016_, p_21017_);
	}
}
