package baguchan.frostrealm.mixin.client;

import baguchan.frostrealm.api.IGlintAurora;
import baguchan.frostrealm.utils.aurorapower.AuroraPowerUtils;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.Entity;
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
    public void updateForLiving(
            ItemStackRenderState p_387100_, ItemStack p_387635_, ItemDisplayContext p_388107_, LivingEntity p_388201_, CallbackInfo ci
    ) {
        if (p_387100_ instanceof IGlintAurora glintAurora) {
            glintAurora.frostRealm$setGlint(!AuroraPowerUtils.getAuroraPowers(p_387635_).isEmpty());
        }
    }

    @Inject(method = "updateForNonLiving", at = @At(value = "HEAD"))
    public void updateForNonLiving(
            ItemStackRenderState p_386914_, ItemStack p_388286_, ItemDisplayContext p_387479_, Entity p_386766_, CallbackInfo ci
    ) {
        if (p_386914_ instanceof IGlintAurora glintAurora) {
            glintAurora.frostRealm$setGlint(!AuroraPowerUtils.getAuroraPowers(p_388286_).isEmpty());
        }
    }
}