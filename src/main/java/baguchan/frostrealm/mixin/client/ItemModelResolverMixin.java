package baguchan.frostrealm.mixin.client;

import baguchan.frostrealm.api.IGlintAurora;
import baguchan.frostrealm.utils.aurorapower.AuroraPowerUtils;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemModelResolver.class)
public abstract class ItemModelResolverMixin {

    @Inject(method = "updateForTopItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/item/ItemStackRenderState;clear()V", shift = At.Shift.AFTER))
    public void updateForLiving(
            ItemStackRenderState p_387014_, ItemStack p_388693_, ItemDisplayContext p_388835_, Level p_388064_, LivingEntity p_388047_, int p_388137_, CallbackInfo ci
    ) {
        if (!p_388693_.isEmpty()) {
            if (p_387014_ instanceof IGlintAurora glintAurora) {
                glintAurora.frostRealm$setGlint(!AuroraPowerUtils.getAuroraPowers(p_388693_).isEmpty());
            }
        }
    }
}