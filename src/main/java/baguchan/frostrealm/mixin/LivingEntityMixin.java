package baguchan.frostrealm.mixin;

import baguchan.frostrealm.registry.FrostEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Inject(method = "addEatEffect", at = @At("HEAD"))
	private void addEatEffect(ItemStack p_21064_, Level p_21065_, LivingEntity p_21066_, CallbackInfo callbackInfo) {
		Item item = p_21064_.getItem();
		if (item.isEdible() && (item == Items.MUSHROOM_STEW || item == Items.RABBIT_STEW || item == Items.BEETROOT_SOUP)) {
			p_21066_.addEffect(new MobEffectInstance(FrostEffects.COLD_RESISTANCE.get(), 1200));
		}
	}
}
