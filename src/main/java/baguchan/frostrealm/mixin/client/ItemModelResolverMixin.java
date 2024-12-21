package baguchan.frostrealm.mixin.client;

import baguchan.frostrealm.api.IGlintAurora;
import baguchan.frostrealm.utils.aurorapower.AuroraPowerUtils;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemModelResolver.class)
public abstract class ItemModelResolverMixin {

    @Inject(method = "updateForLiving", at = @At(value = "HEAD"))
    public void updateForTopItem(
            ItemStackRenderState p_387100_, ItemStack p_387635_, ItemDisplayContext p_388107_, boolean p_387193_, LivingEntity p_388201_, CallbackInfo ci
    ) {
        if (p_387100_ instanceof IGlintAurora glintAurora) {
            glintAurora.frostRealm$setGlint(!AuroraPowerUtils.getAuroraPowers(p_387635_).isEmpty());
        }
    }
}