package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = FrostRealm.MODID)
public class FrostAnimations {
    public static ResourceLocation ATTACK = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, "attack");
    @SubscribeEvent
    public static void entityAnimationRegister(baguchi.bagus_lib.event.RegisterBagusAnimationEvents events) {
        if (events.getEntity() instanceof Player) {
            events.addAnimationState(ATTACK);
        }
    }
}
