package baguchan.frostrealm.client.render.layer;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.WolfflueModel;
import baguchan.frostrealm.client.render.state.WolfflueRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Crackiness;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;

import java.util.Map;

public class WolfflueArmorLayer<T extends WolfflueRenderState> extends RenderLayer<T, WolfflueModel<T>> {
    private final WolfflueModel<T> model;
    private static final Map<Crackiness.Level, Identifier> ARMOR_CRACK_LOCATIONS = Map.of(
            Crackiness.Level.LOW,
            Identifier.withDefaultNamespace("textures/entity/wolf/wolf_armor_crackiness_low.png"),
            Crackiness.Level.MEDIUM,
            Identifier.withDefaultNamespace("textures/entity/wolf/wolf_armor_crackiness_medium.png"),
            Crackiness.Level.HIGH,
            Identifier.withDefaultNamespace("textures/entity/wolf/wolf_armor_crackiness_high.png")
    );
    private static final Identifier SADDLE_LOCATION = Identifier.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/wolfflue/armor/saddle.png");

    private final EquipmentLayerRenderer equipmentRenderer;


    public WolfflueArmorLayer(RenderLayerParent<T, WolfflueModel<T>> p_316639_, EntityModelSet p_316756_, EquipmentLayerRenderer p_371602_) {
        super(p_316639_);
        this.model = new WolfflueModel<>(p_316756_.bakeLayer(FrostModelLayers.WOLFFLUE_ARMOR));
        this.equipmentRenderer = p_371602_;
    }

    public void submit(PoseStack p_436050_, SubmitNodeCollector p_434212_, int p_433618_, T entity, float p_435015_, float p_434923_) {
        ItemStack itemstack = entity.bodyArmorItem;
        Equippable equippable = itemstack.get(DataComponents.EQUIPPABLE);
        if (!entity.isBaby && equippable != null && !equippable.assetId().isEmpty()) {
            WolfflueModel<T> wolfmodel = this.model;
            this.equipmentRenderer
                    .renderLayers(
                            EquipmentClientInfo.LayerType.valueOf("FROSTREALM_WOLFFLUE"),
                            equippable.assetId().get(),
                            wolfmodel,
                            entity,
                            itemstack,
                            p_436050_,
                            p_434212_,
                            p_433618_,
                            entity.outlineColor
                    );
            this.maybeRenderCracks(p_436050_, p_434212_, p_433618_, itemstack, wolfmodel, entity);
        }
    }

    private void maybeRenderCracks(
            PoseStack p_331222_, SubmitNodeCollector p_434578_, int p_330931_, ItemStack p_331187_, Model<T> p_364428_, T p_433708_
    ) {
        Crackiness.Level crackiness$level = Crackiness.WOLF_ARMOR.byDamage(p_331187_);
        if (crackiness$level != Crackiness.Level.NONE) {
            Identifier resourcelocation = ARMOR_CRACK_LOCATIONS.get(crackiness$level);
            p_434578_.submitModel(
                    p_364428_,
                    p_433708_,
                    p_331222_,
                    RenderTypes.armorTranslucent(resourcelocation),
                    p_330931_,
                    OverlayTexture.NO_OVERLAY,
                    p_433708_.outlineColor,
                    null
            );
        }
    }
}
