package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.advancement.PutCrystalTrigger;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FrostCriterions {
    public static final DeferredRegister<CriterionTrigger<?>> CRITERIONS_REGISTER = DeferredRegister.create(BuiltInRegistries.TRIGGER_TYPES, FrostRealm.MODID);

    public static final Supplier<PutCrystalTrigger> PUT_CRYSTAL = CRITERIONS_REGISTER.register(PutCrystalTrigger.ID.getPath(), () -> new PutCrystalTrigger());
}