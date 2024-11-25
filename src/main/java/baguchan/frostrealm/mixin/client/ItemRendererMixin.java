package baguchan.frostrealm.mixin.client;

import baguchan.frostrealm.client.FrostRenderType;
import baguchan.frostrealm.utils.aurorapower.AuroraPowerUtils;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Shadow
    public abstract void renderQuadList(PoseStack p_115163_, VertexConsumer p_115164_, List<BakedQuad> p_115165_, ItemStack p_115166_, int p_115167_, int p_115168_);

    @Inject(method = "renderItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderModelLists(Lnet/minecraft/client/resources/model/BakedModel;Lnet/minecraft/world/item/ItemStack;IILcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;)V", shift = At.Shift.BEFORE))
    private void renderItem(ItemStack p_361397_, ItemDisplayContext p_361627_, PoseStack p_360423_, MultiBufferSource p_360415_, int p_361265_, int p_364771_, BakedModel p_363970_, boolean p_364829_, CallbackInfo ci, @Local RenderType renderType, @Local LocalRef<VertexConsumer> vertexconsumer) {
        if (!AuroraPowerUtils.getAuroraPowers(p_361397_).isEmpty()) {
            frostRealm$renderAfterModelLists(p_363970_, p_361397_, p_361265_, p_364771_, p_360423_, FrostRenderType.getAurora(p_360415_, renderType));
        }
    }

    @Unique
    public void frostRealm$renderAfterModelLists(BakedModel p_115190_, ItemStack p_115191_, int p_115192_, int p_115193_, PoseStack p_115194_, VertexConsumer p_115195_) {
        RandomSource randomsource = RandomSource.create();
        long i = 42L;

        for (Direction direction : Direction.values()) {
            randomsource.setSeed(42L);
            this.renderQuadList(p_115194_, p_115195_, p_115190_.getQuads(null, direction, randomsource), p_115191_, p_115192_, p_115193_);
        }

        randomsource.setSeed(42L);
        this.renderQuadList(p_115194_, p_115195_, p_115190_.getQuads(null, null, randomsource), p_115191_, p_115192_, p_115193_);
    }
}