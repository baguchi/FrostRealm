package baguchan.frostrealm.client.render.layer;

import baguchan.frostrealm.client.render.IArmor;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;

import java.util.Map;


/*
 * https://github.com/AlexModGuy/AlexsMobs/blob/1.19.4/src/main/java/com/github/alexthe666/alexsmobs/client/render/layer/LayerKangarooArmor.java
 * Thanks Alex!
 */
public class CustomArmorLayer<T extends LivingEntity, M extends EntityModel<T> & IArmor> extends RenderLayer<T, M> {

    private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();
    private final HumanoidModel defaultBipedModel;
    private RenderLayerParent<T, M> renderer;

    public CustomArmorLayer(RenderLayerParent<T, M> render, EntityRendererProvider.Context context) {
        super(render);
        defaultBipedModel = new HumanoidModel(context.bakeLayer(ModelLayers.ARMOR_STAND_OUTER_ARMOR));
        this.renderer = render;
    }

    public static ResourceLocation getArmorResource(net.minecraft.world.entity.Entity entity, ItemStack stack, EquipmentSlot slot, @javax.annotation.Nullable String type) {
        ArmorItem item = (ArmorItem) stack.getItem();
        String texture = item.getMaterial().getName();
        String domain = "minecraft";
        int idx = texture.indexOf(':');
        if (idx != -1) {
            domain = texture.substring(0, idx);
            texture = texture.substring(idx + 1);
        }
        String s1 = String.format("%s:textures/models/armor/%s_layer_%d%s.png", domain, texture, (1), type == null ? "" : String.format("_%s", type));

        s1 = net.minecraftforge.client.ForgeHooksClient.getArmorTexture(entity, stack, s1, slot, type);
        ResourceLocation resourcelocation = ARMOR_TEXTURE_RES_MAP.get(s1);

        if (resourcelocation == null) {
            resourcelocation = new ResourceLocation(s1);
            ARMOR_TEXTURE_RES_MAP.put(s1, resourcelocation);
        }

        return resourcelocation;
    }

    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        matrixStackIn.pushPose();
        if (!entity.isBaby()) {
            {
                matrixStackIn.pushPose();
                ItemStack itemstack = entity.getItemBySlot(EquipmentSlot.HEAD);
                if (itemstack.getItem() instanceof ArmorItem) {
                    ArmorItem armoritem = (ArmorItem) itemstack.getItem();
                    if (itemstack.canEquip(EquipmentSlot.HEAD, entity)) {
                        HumanoidModel a = defaultBipedModel;
                        a = getArmorModelHook(entity, itemstack, EquipmentSlot.HEAD, a);
                        boolean notAVanillaModel = a != defaultBipedModel;
                        this.setModelSlotVisible(a, EquipmentSlot.HEAD);
                        boolean flag1 = itemstack.hasFoil();
                        int clampedLight = packedLightIn;
                        if (armoritem instanceof net.minecraft.world.item.DyeableLeatherItem) { // Allow this for anything, not only cloth
                            int i = ((net.minecraft.world.item.DyeableLeatherItem) armoritem).getColor(itemstack);
                            float f = (float) (i >> 16 & 255) / 255.0F;
                            float f1 = (float) (i >> 8 & 255) / 255.0F;
                            float f2 = (float) (i & 255) / 255.0F;
                            renderHelmet(entity, matrixStackIn, bufferIn, clampedLight, flag1, a, f, f1, f2, getArmorResource(entity, itemstack, EquipmentSlot.HEAD, null), notAVanillaModel);
                            renderHelmet(entity, matrixStackIn, bufferIn, clampedLight, flag1, a, 1.0F, 1.0F, 1.0F, getArmorResource(entity, itemstack, EquipmentSlot.HEAD, "overlay"), notAVanillaModel);
                        } else {
                            renderHelmet(entity, matrixStackIn, bufferIn, clampedLight, flag1, a, 1.0F, 1.0F, 1.0F, getArmorResource(entity, itemstack, EquipmentSlot.HEAD, null), notAVanillaModel);
                        }
                    }
                } else {
                    this.renderer.getModel().translateToHead(matrixStackIn);
                    matrixStackIn.mulPose((new Quaternionf()).rotateX((float) Math.PI));
                    matrixStackIn.mulPose((new Quaternionf()).rotateY((float) Math.PI));
                    Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.FIXED, packedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn, entity.level, 0);
                }
                matrixStackIn.popPose();
            }
            {
                matrixStackIn.pushPose();
                ItemStack itemstack = entity.getItemBySlot(EquipmentSlot.CHEST);
                if (itemstack.getItem() instanceof ArmorItem) {
                    ArmorItem armoritem = (ArmorItem) itemstack.getItem();
                    if (armoritem.getEquipmentSlot() == EquipmentSlot.CHEST) {
                        HumanoidModel a = defaultBipedModel;
                        a = getArmorModelHook(entity, itemstack, EquipmentSlot.CHEST, a);
                        boolean notAVanillaModel = a != defaultBipedModel;
                        this.setModelSlotVisible(a, EquipmentSlot.CHEST);

                        boolean flag1 = itemstack.hasFoil();
                        int clampedLight = packedLightIn;
                        if (armoritem instanceof net.minecraft.world.item.DyeableLeatherItem) { // Allow this for anything, not only cloth
                            int i = ((net.minecraft.world.item.DyeableLeatherItem) armoritem).getColor(itemstack);
                            float f = (float) (i >> 16 & 255) / 255.0F;
                            float f1 = (float) (i >> 8 & 255) / 255.0F;
                            float f2 = (float) (i & 255) / 255.0F;
                            renderChestplate(entity, matrixStackIn, bufferIn, clampedLight, flag1, a, f, f1, f2, getArmorResource(entity, itemstack, EquipmentSlot.CHEST, null), notAVanillaModel);
                            renderChestplate(entity, matrixStackIn, bufferIn, clampedLight, flag1, a, 1.0F, 1.0F, 1.0F, getArmorResource(entity, itemstack, EquipmentSlot.CHEST, "overlay"), notAVanillaModel);
                        } else {
                            renderChestplate(entity, matrixStackIn, bufferIn, clampedLight, flag1, a, 1.0F, 1.0F, 1.0F, getArmorResource(entity, itemstack, EquipmentSlot.CHEST, null), notAVanillaModel);
                        }

                    }
                }
                matrixStackIn.popPose();
            }
        }
        matrixStackIn.popPose();

    }


    private void renderChestplate(LivingEntity entity, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, boolean glintIn, HumanoidModel modelIn, float red, float green, float blue, ResourceLocation armorResource, boolean notAVanillaModel) {
        VertexConsumer ivertexbuilder = ItemRenderer.getFoilBuffer(bufferIn, RenderType.entityCutoutNoCull(armorResource), false, glintIn);
        renderer.getModel().copyPropertiesTo(modelIn);
        modelIn.body.xRot = 0F;
        modelIn.body.yRot = 0;
        modelIn.body.zRot = 0;
        modelIn.body.x = 0;
        modelIn.body.y = 0F;
        modelIn.body.z = 0F;
        modelIn.rightArm.x = 0F;
        modelIn.rightArm.y = 0F;
        modelIn.rightArm.z = 0F;
        modelIn.rightArm.xRot = 0F;
        modelIn.rightArm.yRot = 0F;
        modelIn.rightArm.zRot = 0F;
        modelIn.leftArm.x = 0F;
        modelIn.leftArm.y = 0F;
        modelIn.leftArm.z = 0F;
        modelIn.leftArm.xRot = 0F;
        modelIn.leftArm.yRot = 0F;
        modelIn.leftArm.zRot = 0F;
        modelIn.leftArm.y = 0F;
        modelIn.rightArm.y = 0F;
        modelIn.leftArm.z = 0F;
        modelIn.rightArm.z = 0F;
        modelIn.body.visible = false;
        modelIn.rightArm.visible = true;
        modelIn.leftArm.visible = false;
        matrixStackIn.pushPose();
        renderer.getModel().translateToChestPat(HumanoidArm.RIGHT, matrixStackIn);
        modelIn.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
        matrixStackIn.popPose();
        modelIn.rightArm.visible = false;
        modelIn.leftArm.visible = true;
        matrixStackIn.pushPose();
        renderer.getModel().translateToChestPat(HumanoidArm.LEFT, matrixStackIn);
        modelIn.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
        matrixStackIn.popPose();
        modelIn.body.visible = true;
        modelIn.rightArm.visible = false;
        modelIn.leftArm.visible = false;
        matrixStackIn.pushPose();
        this.renderer.getModel().translateToChest(matrixStackIn);
        modelIn.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
        matrixStackIn.popPose();
        modelIn.rightArm.visible = true;
        modelIn.leftArm.visible = true;

    }

    private void renderHelmet(LivingEntity entity, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, boolean glintIn, HumanoidModel modelIn, float red, float green, float blue, ResourceLocation armorResource, boolean notAVanillaModel) {
        VertexConsumer ivertexbuilder = ItemRenderer.getFoilBuffer(bufferIn, RenderType.entityCutoutNoCull(armorResource), false, glintIn);
        renderer.getModel().copyPropertiesTo(modelIn);
        modelIn.head.xRot = 0F;
        modelIn.head.yRot = 0F;
        modelIn.head.zRot = 0F;
        modelIn.hat.xRot = 0F;
        modelIn.hat.yRot = 0F;
        modelIn.hat.zRot = 0F;
        modelIn.head.x = 0F;
        modelIn.head.y = 0F;
        modelIn.head.z = 0F;
        modelIn.hat.x = 0F;
        modelIn.hat.y = 0F;
        modelIn.hat.z = 0F;
        matrixStackIn.pushPose();
        this.renderer.getModel().translateToHead(matrixStackIn);
        modelIn.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
        matrixStackIn.popPose();
    }


    protected void setModelSlotVisible(HumanoidModel p_188359_1_, EquipmentSlot slotIn) {
        this.setModelVisible(p_188359_1_);
        switch (slotIn) {
            case HEAD:
                p_188359_1_.head.visible = true;
                p_188359_1_.hat.visible = true;
                break;
            case CHEST:
                p_188359_1_.body.visible = true;
                p_188359_1_.rightArm.visible = true;
                p_188359_1_.leftArm.visible = true;
                break;
            case LEGS:
                p_188359_1_.body.visible = true;
                p_188359_1_.rightLeg.visible = true;
                p_188359_1_.leftLeg.visible = true;
                break;
            case FEET:
                p_188359_1_.rightLeg.visible = true;
                p_188359_1_.leftLeg.visible = true;
        }
    }

    protected void setModelVisible(HumanoidModel model) {
        model.setAllVisible(false);

    }


    protected HumanoidModel<?> getArmorModelHook(LivingEntity entity, ItemStack itemStack, EquipmentSlot slot, HumanoidModel model) {
        Model basicModel = net.minecraftforge.client.ForgeHooksClient.getArmorModel(entity, itemStack, slot, model);
        return basicModel instanceof HumanoidModel ? (HumanoidModel<?>) basicModel : model;
    }
}