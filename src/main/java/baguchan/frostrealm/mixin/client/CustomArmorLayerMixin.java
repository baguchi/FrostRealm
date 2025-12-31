package baguchan.frostrealm.mixin.client;

import baguchan.frostrealm.client.FrostRenderType;
import baguchan.frostrealm.utils.aurorapower.AuroraPowerUtils;
import baguchi.bagus_lib.client.layer.CustomArmorLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.EquipmentAssetManager;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentAsset;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Function;

@Mixin(CustomArmorLayer.class)
public class CustomArmorLayerMixin {

    @Shadow
    @Final
    private EquipmentAssetManager equipmentAssets;

    @Shadow
    @Final
    private Function<CustomArmorLayer.LayerTextureKey, Identifier> layerTextureLookup;

    @Inject(method = "renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;order(I)Lnet/minecraft/client/renderer/OrderedSubmitNodeCollector;", shift = At.Shift.BEFORE, ordinal = 0))
    public <S> void renderLayers(EquipmentClientInfo.LayerType p_387484_, ResourceKey<EquipmentAsset> p_387603_, Model<? super S> p_371731_, S p_435806_, ItemStack p_371670_, PoseStack p_371767_, SubmitNodeCollector p_435795_, int light, Identifier p_371639_, int outlineColor, int p_436591_, String renderPart, CallbackInfo ci) {
        if (!AuroraPowerUtils.getAuroraPowers(p_371670_).isEmpty()) {
            ModelPart part = (ModelPart) p_371731_.root().createPartLookup().apply(renderPart);

            net.neoforged.neoforge.client.extensions.common.IClientItemExtensions extensions = net.neoforged.neoforge.client.extensions.common.IClientItemExtensions.of(p_371670_);
            List<EquipmentClientInfo.Layer> list = this.equipmentAssets.get(p_387603_).getLayers(p_387484_);
            if (!list.isEmpty()) {
                int i = extensions.getDefaultDyeColor(p_371670_);
                int idx = 0;
                for (EquipmentClientInfo.Layer equipmentclientinfo$layer : list) {

                    int k = extensions.getArmorLayerTintColor(p_371670_, equipmentclientinfo$layer, idx, i);
                    if (k != 0) {
                        p_435795_.order(p_436591_++).submitModelPart(part, p_371767_, FrostRenderType.AURORA_ARMOR_ENTITY_GLINT, light, outlineColor, (TextureAtlasSprite) null, k, (ModelFeatureRenderer.CrumblingOverlay) null);

                    }
                }
            }
        }
    }
}
