package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.schedule.Activity;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FrostActivity {
    public static final DeferredRegister<Activity> ACTIVITY = DeferredRegister.create(BuiltInRegistries.ACTIVITY, FrostRealm.MODID);
    public static final Supplier<Activity> TAKE_BACK = ACTIVITY.register("take_back", () -> new Activity("take_back"));
}
