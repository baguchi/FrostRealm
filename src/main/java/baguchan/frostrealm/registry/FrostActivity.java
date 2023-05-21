package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FrostActivity {
    public static final DeferredRegister<Activity> ACTIVITY = DeferredRegister.create(ForgeRegistries.ACTIVITIES, FrostRealm.MODID);

    public static final RegistryObject<Activity> UNCOMFORTABLE = ACTIVITY.register("uncomfortable", () -> new Activity("uncomfortable"));
}
