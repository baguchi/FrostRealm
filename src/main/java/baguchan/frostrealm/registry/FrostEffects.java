package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.effect.ColdResistanceEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FrostEffects {
	public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, FrostRealm.MODID);
	public static final DeferredRegister<Potion> POTION = DeferredRegister.create(ForgeRegistries.POTIONS, FrostRealm.MODID);


	public static final RegistryObject<MobEffect> COLD_RESISTANCE = MOB_EFFECTS.register("cold_resistance", () -> new ColdResistanceEffect(MobEffectCategory.BENEFICIAL, 0xDA784A));

	public static void init() {

	}
}