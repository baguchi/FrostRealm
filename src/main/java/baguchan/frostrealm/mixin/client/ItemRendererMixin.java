package baguchan.frostrealm.mixin.client;

import baguchan.frostrealm.client.FrostRenderType;
import baguchan.frostrealm.utils.aurorapower.AuroraPowerUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderModelLists(Lnet/minecraft/client/resources/model/BakedModel;Lnet/minecraft/world/item/ItemStack;IILcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;)V", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
    public void render(

            ItemStack p_115144_, ItemDisplayContext p_270188_, boolean p_115146_, PoseStack p_115147_, MultiBufferSource p_115148_, int p_115149_, int p_115150_, BakedModel p_115151_, CallbackInfo ci, boolean flag, boolean flag1, Iterator var11, BakedModel model, Iterator var13, RenderType rendertype, VertexConsumer vertexconsumer) {
        if (!AuroraPowerUtils.getAuroraPowers(p_115144_).isEmpty()) {
            VertexConsumer vertexconsumer2 = getAuroraFoilBuffer(p_115148_, rendertype, true, true);
            this.renderModelLists(p_115151_, p_115144_, p_115149_, p_115150_, p_115147_, vertexconsumer2);
        }
    }

    @Shadow
    public void renderModelLists(BakedModel p115151, ItemStack p115144, int p115149, int p115150, PoseStack p115147, VertexConsumer vertexconsumer) {
    }

    private static VertexConsumer getAuroraFoilBuffer(MultiBufferSource p_115212_, RenderType p_115213_, boolean p_115214_, boolean p_115215_) {
        if (p_115215_) {
            return Minecraft.useShaderTransparency() && p_115213_ == Sheets.translucentItemSheet()
                    ? VertexMultiConsumer.create(p_115212_.getBuffer(FrostRenderType.AURORA_TRANSLUCENT), p_115212_.getBuffer(p_115213_))
                    : VertexMultiConsumer.create(p_115212_.getBuffer(p_115214_ ? FrostRenderType.AURORA : FrostRenderType.ENTITY_AURORA), p_115212_.getBuffer(p_115213_));
        } else {
            return p_115212_.getBuffer(p_115213_);
        }
    }

}
