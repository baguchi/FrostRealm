package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FrostSounds {
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, FrostRealm.MODID);


	public static final RegistryObject<SoundEvent> FROST_MOON_BGM = register("music.frost_moon");
	public static final RegistryObject<SoundEvent> CALM_NIGHT_BGM = register("music.calm_night");
	public static final RegistryObject<SoundEvent> BLIZZARD_AMBIENT = register("ambient.blizzard");
	public static final RegistryObject<SoundEvent> GOKKUR_IDLE = register("entity.gokkur.idle");
	public static final RegistryObject<SoundEvent> GOKKUR_HURT = register("entity.gokkur.hurt");
	public static final RegistryObject<SoundEvent> GOKKUR_DEATH = register("entity.gokkur.death");
	public static final RegistryObject<SoundEvent> GOKKUDILLO_IDLE = register("entity.gokkudillo.idle");
	public static final RegistryObject<SoundEvent> GOKKUDILLO_HURT = register("entity.gokkudillo.hurt");
	public static final RegistryObject<SoundEvent> GOKKUDILLO_DEATH = register("entity.gokkudillo.death");
	public static final RegistryObject<SoundEvent> MARMOT_IDLE = register("entity.marmot.idle");
	public static final RegistryObject<SoundEvent> MARMOT_HURT = register("entity.marmot.hurt");
	public static final RegistryObject<SoundEvent> MARMOT_DEATH = register("entity.marmot.death");
	public static final RegistryObject<SoundEvent> SNOWPILE_QUAIL_IDLE = register("entity.snowpile_quail.idle");
	public static final RegistryObject<SoundEvent> SNOWPILE_QUAIL_HURT = register("entity.snowpile_quail.hurt");
	public static final RegistryObject<SoundEvent> SNOWPILE_QUAIL_DEATH = register("entity.snowpile_quail.death");
	public static final RegistryObject<SoundEvent> MORTAR = register("ambient.mortar.moving");

	private static RegistryObject<SoundEvent> register(String sound) {
		ResourceLocation name = new ResourceLocation(FrostRealm.MODID, sound);
		return SOUND_EVENTS.register(sound, () -> SoundEvent.createVariableRangeEvent(name));
	}
}
