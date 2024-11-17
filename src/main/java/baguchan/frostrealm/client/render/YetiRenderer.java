package baguchan.frostrealm.client.render;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.client.FrostModelLayers;
import baguchan.frostrealm.client.model.YetiModel;
import baguchan.frostrealm.client.render.state.YetiRenderState;
import baguchan.frostrealm.entity.Yeti;
import baguchi.bagus_lib.client.layer.CustomArmorLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class YetiRenderer<T extends Yeti> extends MobRenderer<T, YetiRenderState, YetiModel<YetiRenderState>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/yeti/yeti.png");
    private static final RenderType YETI_GLOW = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "textures/entity/yeti/yeti_glow.png"));

	public YetiRenderer(EntityRendererProvider.Context p_173952_) {
		super(p_173952_, new YetiModel<>(p_173952_.bakeLayer(FrostModelLayers.YETI)), 0.75F);
        this.addLayer(new ItemInHandLayer<>(this, Minecraft.getInstance().getItemRenderer()));
        this.addLayer(new CustomArmorLayer<>(this, p_173952_));
        this.addLayer(new EyesLayer<>(this) {
            @Override
            public RenderType renderType() {
                return YETI_GLOW;
            }
        });
	}

    @Override
    public void extractRenderState(T p_365075_, YetiRenderState p_361774_, float p_363123_) {
        super.extractRenderState(p_365075_, p_361774_, p_363123_);
        p_361774_.sitAnimationState.copyFrom(p_365075_.sitAnimationState);
        p_361774_.sitPoseAnimationState.copyFrom(p_365075_.sitPoseAnimationState);
        p_361774_.sitUpAnimationState.copyFrom(p_365075_.sitUpAnimationState);
        p_361774_.noticedStealerAnimationState.copyFrom(p_365075_.noticedStealerAnimationState);
        p_361774_.snowChargeAnimationState.copyFrom(p_365075_.snowChargeAnimationState);
        p_361774_.idleAnimationState.copyFrom(p_365075_.idleAnimationState);
        p_361774_.state = Yeti.State.get(p_365075_.getState());
        p_361774_.isCrouching = p_365075_.isCrouching();
        p_361774_.isFallFlying = p_365075_.isFallFlying();
        p_361774_.isVisuallySwimming = p_365075_.isVisuallySwimming();
        p_361774_.isPassenger = p_365075_.isPassenger() && (p_365075_.getVehicle() != null && p_365075_.getVehicle().shouldRiderSit());
        p_361774_.speedValue = 1.0F;
        if (p_361774_.isFallFlying) {
            p_361774_.speedValue = (float) p_365075_.getDeltaMovement().lengthSqr();
            p_361774_.speedValue /= 0.2F;
            p_361774_.speedValue = p_361774_.speedValue * p_361774_.speedValue * p_361774_.speedValue;
        }

        p_361774_.attackTime = p_365075_.getAttackAnim(p_363123_);
        p_361774_.swimAmount = p_365075_.getSwimAmount(p_363123_);
        p_361774_.attackArm = getAttackArm(p_365075_);
        p_361774_.useItemHand = p_365075_.getUsedItemHand();
        p_361774_.maxCrossbowChargeDuration = (float) CrossbowItem.getChargeDuration(p_365075_.getUseItem(), p_365075_);
        p_361774_.ticksUsingItem = p_365075_.getTicksUsingItem();
        p_361774_.isUsingItem = p_365075_.isUsingItem();
        p_361774_.chestItem = p_365075_.getItemBySlot(EquipmentSlot.CHEST).copy();
        p_361774_.legsItem = p_365075_.getItemBySlot(EquipmentSlot.LEGS).copy();
        p_361774_.feetItem = p_365075_.getItemBySlot(EquipmentSlot.FEET).copy();
    }

    private static HumanoidArm getAttackArm(LivingEntity p_362737_) {
        HumanoidArm humanoidarm = p_362737_.getMainArm();
        return p_362737_.swingingArm == InteractionHand.MAIN_HAND ? humanoidarm : humanoidarm.getOpposite();
    }

    @Override
    public YetiRenderState createRenderState() {
        return new YetiRenderState();
    }

    @Override
    protected void scale(YetiRenderState p_115314_, PoseStack p_115315_) {
        p_115315_.scale(p_115314_.ageScale, p_115314_.ageScale, p_115314_.ageScale);
        super.scale(p_115314_, p_115315_);
    }

    @Override
    public ResourceLocation getTextureLocation(YetiRenderState p_110775_1_) {
        return TEXTURE;
	}
}