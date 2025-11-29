package baguchan.frostrealm.client;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.api.recipe.AttachableCrystal;
import baguchan.frostrealm.client.animation.SpearAttackAnimations;
import baguchan.frostrealm.data.resource.FrostDimensions;
import baguchan.frostrealm.data.resource.registries.AttachableCrystals;
import baguchan.frostrealm.registry.FrostAnimations;
import baguchan.frostrealm.registry.FrostDataCompnents;
import baguchan.frostrealm.registry.FrostItems;
import baguchan.frostrealm.registry.FrostSounds;
import baguchan.frostrealm.utils.aurorapower.AuroraPowerUtils;
import baguchi.bagus_lib.client.event.BagusModelEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.SelectMusicEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@EventBusSubscriber(modid = FrostRealm.MODID, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void clientAnimation(BagusModelEvent.PostAnimate event) {
        if (event.getEntityRenderState() instanceof HumanoidRenderState humanoidRenderState) {
            boolean flag = humanoidRenderState.mainArm == HumanoidArm.RIGHT;

            if (event.getEntityRenderState().getRenderData(ClientRegistrar.HOLD_SPEAR_KEY) != null) {
                if (event.getEntityRenderState().getRenderDataOrDefault(ClientRegistrar.HOLD_SPEAR_KEY, false)) {
                    event.getModel().root().getChild("right_arm").resetPose();
                    event.getModel().root().getChild("left_arm").resetPose();
                    if (event.getBaguAnimationController() != null && !event.getBaguAnimationController().getAnimationState(FrostAnimations.SPEAR_ATTACK).isStarted()) {
                        if (flag) {
                            SpearAttackAnimations.spear_attack_right.bake(event.getModel().root()).applyStatic();
                        } else {
                            SpearAttackAnimations.spear_attack_left.bake(event.getModel().root()).applyStatic();
                        }
                    }
                    if (flag) {
                        SpearAttackAnimations.spear_attack_right.bake(event.getModel().root()).apply(event.getBaguAnimationController().getAnimationState(FrostAnimations.SPEAR_ATTACK), event.getEntityRenderState().ageInTicks);
                    } else {
                        SpearAttackAnimations.spear_attack_left.bake(event.getModel().root()).apply(event.getBaguAnimationController().getAnimationState(FrostAnimations.SPEAR_ATTACK), event.getEntityRenderState().ageInTicks);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onToolTip(ItemTooltipEvent event) {
        AuroraPowerUtils.getAuroraPowers(event.getItemStack()).addToTooltip(event.getContext(), component -> {
            event.getToolTip().add(component);
        }, TooltipFlag.NORMAL, event.getItemStack());
        @Nullable Holder<AttachableCrystal> attachableCrystal = event.getItemStack().get(FrostDataCompnents.ATTACH_CRYSTAL);
        int damage = event.getItemStack().getOrDefault(FrostDataCompnents.CRYSTAL_USED, 0);

        if (attachableCrystal != null) {
            int damage2 = (attachableCrystal.value().getUse() - damage);
            if (event.getItemStack().getItem() instanceof ArrowItem || event.getItemStack().is(FrostItems.COATING_FUR)) {
                event.getToolTip().add(Component.translatable(Util.makeDescriptionId("attach_crystal", event.getContext().registries().lookup(AttachableCrystals.ATTACHABLE_CRYSTAL_REGISTRY_KEY).get().getOrThrow(attachableCrystal.getKey()).getKey().identifier())));
            } else {
                event.getToolTip().add(Component.translatable(Util.makeDescriptionId("attach_crystal", event.getContext().registries().lookup(AttachableCrystals.ATTACHABLE_CRYSTAL_REGISTRY_KEY).get().getOrThrow(attachableCrystal.getKey()).getKey().identifier()))
                        .append(" ").append(damage2 + " / " + attachableCrystal.value().getUse()));
            }
        }
    }
}
