package baguchan.frostrealm.mixin;

import baguchan.frostrealm.capability.FrostWeatherManager;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity {
	protected MobMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
		super(p_20966_, p_20967_);
	}

	@Inject(method = "isSunBurnTick", at = @At("HEAD"), cancellable = true)
	protected void isSunBurnTick(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
		if (FrostWeatherManager.isBadWeatherActive(this.level())) {
			callbackInfoReturnable.setReturnValue(false);
		}
	}
}
