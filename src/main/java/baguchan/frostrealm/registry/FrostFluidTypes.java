package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.fluidtype.HotSpringFluidType;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class FrostFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, FrostRealm.MODID);

    public static final Supplier<FluidType> HOT_SPRING = FLUID_TYPES.register("hot_spring", () -> new HotSpringFluidType(FluidType.Properties.create().canExtinguish(true).motionScale(0.005F).fallDistanceModifier(0F).supportsBoating(true)));
}