package baguchan.frostrealm.client;

import bagu_chan.bagus_lib.animation.BaguAnimationController;
import bagu_chan.bagus_lib.api.client.IRootModel;
import bagu_chan.bagus_lib.client.event.BagusModelEvent;
import bagu_chan.bagus_lib.util.client.AnimationUtil;
import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.api.recipe.AttachableCrystal;
import baguchan.frostrealm.client.animation.SpearAttackAnimations;
import baguchan.frostrealm.data.resource.registries.AttachableCrystals;
import baguchan.frostrealm.registry.FrostAnimations;
import baguchan.frostrealm.registry.FrostDataCompnents;
import baguchan.frostrealm.registry.FrostItems;
import baguchan.frostrealm.utils.aurorapower.AuroraPowerUtils;
import net.minecraft.Util;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.UUID;

@EventBusSubscriber(modid = FrostRealm.MODID, value = Dist.CLIENT)
public class ClientEvents {
    protected static final UUID BASE_ATTACK_DAMAGE_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    protected static final UUID BASE_ATTACK_SPEED_UUID = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");
    public static final DecimalFormat ATTRIBUTE_MODIFIER_FORMAT = Util.make(new DecimalFormat("#.##"), (p_41704_) -> {
        p_41704_.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
    });

    @SubscribeEvent
    public static void onAuroraToolTip(ItemTooltipEvent event) {
        AuroraPowerUtils.getAuroraPowers(event.getItemStack()).addToTooltip(event.getContext(), component -> {
            event.getToolTip().add(component);
        }, TooltipFlag.NORMAL);
        @Nullable Holder<AttachableCrystal> attachableCrystal = event.getItemStack().get(FrostDataCompnents.ATTACH_CRYSTAL);

        if (attachableCrystal != null) {
            event.getToolTip().add(Component.translatable(Util.makeDescriptionId("attach_crystal", event.getContext().registries().lookup(AttachableCrystals.ATTACHABLE_CRYSTAL_REGISTRY_KEY).get().getOrThrow(attachableCrystal.getKey()).getKey().location())));
        }
    }

    @SubscribeEvent
    public static void animationEvent(BagusModelEvent.Init bagusModelEvent) {
        IRootModel rootModel = bagusModelEvent.getRootModel();
        if (bagusModelEvent.isSupportedAnimateModel()) {
            rootModel.getBagusRoot().getAllParts().forEach(ModelPart::resetPose);
        }
    }

    @SubscribeEvent
    public static void animationEvent(BagusModelEvent.PostAnimate bagusModelEvent) {
        IRootModel rootModel = bagusModelEvent.getRootModel();
        BaguAnimationController animationController = AnimationUtil.getAnimationController(bagusModelEvent.getEntity());
        if (bagusModelEvent.isSupportedAnimateModel() && animationController != null && bagusModelEvent.getEntity() instanceof Player livingEntity) {
            if (livingEntity.getItemBySlot(EquipmentSlot.MAINHAND).is(FrostItems.FROST_SPEAR.get())) {
                if (livingEntity.getMainArm() == HumanoidArm.RIGHT) {
                    rootModel.getBagusRoot().getChild("right_arm").resetPose();
                    rootModel.getBagusRoot().getChild("left_arm").resetPose();
                    rootModel.applyStaticBagu(SpearAttackAnimations.idle_right);
                    if (animationController.getAnimationState(FrostAnimations.ATTACK).isStarted()) {
                        rootModel.animateBagu(animationController.getAnimationState(FrostAnimations.ATTACK), SpearAttackAnimations.spear_attack_right, bagusModelEvent.getAgeInTick(), 2.0F);
                    } else {
                        rootModel.applyStaticBagu(SpearAttackAnimations.idle_right);
                    }
                    if (bagusModelEvent.getModel() instanceof PlayerModel<?>) {
                        rootModel.getBagusRoot().getChild("right_sleeve").copyFrom(rootModel.getBagusRoot().getChild("right_arm"));
                        rootModel.getBagusRoot().getChild("left_sleeve").copyFrom(rootModel.getBagusRoot().getChild("left_arm"));
                    }
                } else {
                    rootModel.getBagusRoot().getChild("right_arm").resetPose();
                    rootModel.getBagusRoot().getChild("left_arm").resetPose();
                    if (animationController.getAnimationState(FrostAnimations.ATTACK).isStarted()) {
                        rootModel.animateBagu(animationController.getAnimationState(FrostAnimations.ATTACK), SpearAttackAnimations.spear_attack_left, bagusModelEvent.getAgeInTick(), 2.0F);
                    } else {
                        rootModel.applyStaticBagu(SpearAttackAnimations.idle_left);
                    }
                    if (bagusModelEvent.getModel() instanceof PlayerModel<?>) {
                        rootModel.getBagusRoot().getChild("right_sleeve").copyFrom(rootModel.getBagusRoot().getChild("right_arm"));
                        rootModel.getBagusRoot().getChild("left_sleeve").copyFrom(rootModel.getBagusRoot().getChild("left_arm"));
                    }
                }
            }
        }
    }
}
