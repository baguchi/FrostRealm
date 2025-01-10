package baguchan.frostrealm.client.render.layer;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.FrostRenderType;
import baguchan.frostrealm.client.model.WolfflueModel;
import baguchan.frostrealm.client.render.state.WolfflueRenderState;
import baguchan.frostrealm.item.WolfflueArmorItem;
import baguchan.frostrealm.utils.aurorapower.AuroraPowerUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Crackiness;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SaddleItem;

import java.util.Map;

public class WolfflueArmorLayer<T extends WolfflueRenderState> extends RenderLayer<T, WolfflueModel<T>> {
    private final WolfflueModel<T> model;
    private static final Map<Crackiness.Level, ResourceLocation> ARMOR_CRACK_LOCATIONS = Map.of(
            Crackiness.Level.LOW,
            ResourceLocation.withDefaultNamespace("textures/entity/wolf/wolf_armor_crackiness_low.png"),
            Crackiness.Level.MEDIUM,
            ResourceLocation.withDefaultNamespace("textures/entity/wolf/wolf_armor_crackiness_medium.png"),
            Crackiness.Level.HIGH,
            ResourceLocation.withDefaultNamespace("textures/entity/wolf/wolf_armor_crackiness_high.png")
    );
    private static final ResourceLocation SADDLE_LOCATION = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/wolfflue/armor/saddle.png");



    public WolfflueArmorLayer(RenderLayerParent<T, WolfflueModel<T>> p_316639_, EntityModelSet p_316756_) {
        super(p_316639_);
        this.model = new WolfflueModel<>(p_316756_.bakeLayer(FrostModelLayers.WOLFFLUE_ARMOR));
    }

    public void render(
            PoseStack p_316608_,
            MultiBufferSource p_316832_,
            int p_316312_,
            T p_316642_,
            float p_316350_,
            float p_316147_
    ) {
        if (p_316642_.bodyArmorItem.getItem() instanceof WolfflueArmorItem wolfflueArmorItem) {
            ItemStack itemstack = p_316642_.bodyArmorItem;

            this.model.setupAnim(p_316642_);
            VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(p_316832_, RenderType.entityCutoutNoCull(wolfflueArmorItem.getTexture()), itemstack.hasFoil());
                this.model.renderToBuffer(p_316608_, vertexconsumer, p_316312_, OverlayTexture.NO_OVERLAY);
            this.maybeRenderCracks(p_316608_, p_316832_, p_316312_, itemstack);

            if (!AuroraPowerUtils.getAuroraPowers(itemstack).isEmpty()) {
                VertexConsumer vertexconsumer2 = p_316832_.getBuffer(FrostRenderType.AURORA_ENTITY_GLINT);
                this.model.renderToBuffer(p_316608_, vertexconsumer2, p_316312_, OverlayTexture.NO_OVERLAY);
            }

            return;

        }

        if (p_316642_.bodyArmorItem.getItem() instanceof SaddleItem) {
            this.model.setupAnim(p_316642_);
            VertexConsumer vertexconsumer = p_316832_.getBuffer(RenderType.entityCutoutNoCull(SADDLE_LOCATION));
            this.model.renderToBuffer(p_316608_, vertexconsumer, p_316312_, OverlayTexture.NO_OVERLAY);
        }
    }


    private void maybeRenderCracks(PoseStack p_331222_, MultiBufferSource p_331637_, int p_330931_, ItemStack p_331187_) {
        /*Crackiness.Level crackiness$level = Crackiness.WOLF_ARMOR.byDamage(p_331187_);
        if (crackiness$level != Crackiness.Level.NONE) {
            ResourceLocation resourcelocation = ARMOR_CRACK_LOCATIONS.get(crackiness$level);
            VertexConsumer vertexconsumer = p_331637_.getBuffer(RenderType.entityTranslucent(resourcelocation));
            this.model.renderToBuffer(p_331222_, vertexconsumer, p_330931_, OverlayTexture.NO_OVERLAY);
        }*/
    }
}
