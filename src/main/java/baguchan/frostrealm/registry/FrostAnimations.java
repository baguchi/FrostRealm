package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = FrostRealm.MODID)
public class FrostAnimations {
    @SubscribeEvent
    public static void entityAnimationRegister(baguchi.bagus_lib.event.RegisterBagusAnimationEvents events) {
    }
}
