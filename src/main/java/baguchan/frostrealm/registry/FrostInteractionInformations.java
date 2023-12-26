package baguchan.frostrealm.registry;

import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.FluidInteractionRegistry;

public class FrostInteractionInformations {
    public static void init() {
        FluidInteractionRegistry.addInteraction(NeoForgeMod.LAVA_TYPE.value(), new FluidInteractionRegistry.InteractionInformation(
                FrostFluidTypes.HOT_SPRING.get(),
                fluidState -> FrostBlocks.FRIGID_STONE.get().defaultBlockState()
        ));
    }
}