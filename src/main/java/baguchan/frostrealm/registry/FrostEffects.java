package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.effect.ColdResistanceEffect;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FrostEffects {
	public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, FrostRealm.MODID);
	public static final DeferredRegister<Potion> POTION = DeferredRegister.create(BuiltInRegistries.POTION, FrostRealm.MODID);


	public static final Supplier<MobEffect> COLD_RESISTANCE = MOB_EFFECTS.register("cold_resistance", () -> new ColdResistanceEffect(MobEffectCategory.BENEFICIAL, 0xDA784A));

	public static void init() {

	}
}