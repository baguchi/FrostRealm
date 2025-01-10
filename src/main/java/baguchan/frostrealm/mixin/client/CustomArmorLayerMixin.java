package baguchan.frostrealm.mixin.client;

import baguchan.frostrealm.client.FrostRenderType;
import baguchan.frostrealm.utils.aurorapower.AuroraPowerUtils;
import baguchi.bagus_lib.client.layer.CustomArmorLayer;
import baguchi.bagus_lib.client.layer.IArmor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentAsset;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CustomArmorLayer.class)
public class CustomArmorLayerMixin<S extends LivingEntityRenderState, M extends EntityModel<S> & IArmor, A extends EntityModel<S>> {
    @Inject(method = "renderTrim", at = @At(value = "TAIL"))
    private void frostRealm$renderAurora(ItemStack itemStack, PoseStack poseStack, MultiBufferSource bufferIn, EquipmentClientInfo.LayerType layerType, ResourceKey<EquipmentAsset> resourceLocation, ModelPart a, int i, CallbackInfo ci) {
        if (!AuroraPowerUtils.getAuroraPowers(itemStack).isEmpty()) {
            VertexConsumer vertexconsumer2 = bufferIn.getBuffer(FrostRenderType.AURORA_ENTITY_GLINT);
            a.render(poseStack, vertexconsumer2, i, OverlayTexture.NO_OVERLAY);
        }

    }
}