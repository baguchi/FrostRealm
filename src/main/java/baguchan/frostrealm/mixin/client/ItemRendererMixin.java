package baguchan.frostrealm.mixin.client;

import baguchan.frostrealm.api.IGlintAurora;
import baguchan.frostrealm.utils.aurorapower.AuroraPowerUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Shadow @Final private ItemStackRenderState scratchItemStackRenderState;

    @Inject(method = "renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V", at = @At(value = "TAIL"))
    private void renderStatic(LivingEntity p_270101_, ItemStack p_270637_, ItemDisplayContext p_270437_, boolean p_270434_, PoseStack p_270230_, MultiBufferSource p_270411_, Level p_270641_, int p_270595_, int p_270927_, int p_270845_, CallbackInfo ci) {
        if (this.scratchItemStackRenderState instanceof IGlintAurora glintAurora) {
            glintAurora.frostRealm$setGlint(!AuroraPowerUtils.getAuroraPowers(p_270637_).isEmpty());
        }

    }
}