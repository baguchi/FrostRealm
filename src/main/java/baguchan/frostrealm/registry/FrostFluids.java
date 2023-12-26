package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.fluid.HotSpringFluid;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FrostFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(BuiltInRegistries.FLUID, FrostRealm.MODID);

    public static final Supplier<FlowingFluid> HOT_SPRING = FLUIDS.register("hot_spring", () -> new HotSpringFluid.Source());
    public static final Supplier<FlowingFluid> HOT_SPRING_FLOW = FLUIDS.register("hot_spring_flow", () -> new HotSpringFluid.Flowing());

}