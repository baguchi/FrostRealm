package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = FrostRealm.MODID)
public class FrostAnimations {
    public static ResourceLocation SPEAR_ATTACK = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "attack");
    public static ResourceLocation BURGER = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "burger");
    @SubscribeEvent
    public static void entityAnimationRegister(baguchi.bagus_lib.event.RegisterBagusAnimationEvents events) {
        if (events.getEntity() instanceof Player) {
            events.addFirstPersonPlayableAnimationState(SPEAR_ATTACK);
            events.addAnimationState(BURGER);
        }
    }
}
