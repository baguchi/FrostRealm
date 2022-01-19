package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FrostRealm.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FrostSounds {
	public static final SoundEvent FROST_MOON_BGM = register("music.frost_moon");
	public static final SoundEvent CALM_NIGHT_BGM = register("music.calm_night");
	public static final SoundEvent BLIZZARD_AMBIENT = register("ambient.blizzard");
	public static final SoundEvent GOKKUR_IDLE = register("entity.gokkur.idle");
	public static final SoundEvent GOKKUR_HURT = register("entity.gokkur.hurt");
	public static final SoundEvent GOKKUR_DEATH = register("entity.gokkur.death");
	public static final SoundEvent MARMOT_IDLE = register("entity.marmot.idle");
	public static final SoundEvent MARMOT_HURT = register("entity.marmot.hurt");
	public static final SoundEvent MARMOT_DEATH = register("entity.marmot.death");
	public static final SoundEvent SNOWPILE_QUAIL_IDLE = register("entity.snowpile_quail.idle");
	public static final SoundEvent SNOWPILE_QUAIL_HURT = register("entity.snowpile_quail.hurt");
	public static final SoundEvent SNOWPILE_QUAIL_DEATH = register("entity.snowpile_quail.death");

	@SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> registry) {
		registry.getRegistry().register(FROST_MOON_BGM);
		registry.getRegistry().register(CALM_NIGHT_BGM);
		registry.getRegistry().register(BLIZZARD_AMBIENT);
		registry.getRegistry().register(GOKKUR_IDLE);
		registry.getRegistry().register(GOKKUR_HURT);
		registry.getRegistry().register(GOKKUR_DEATH);
		registry.getRegistry().register(MARMOT_IDLE);
		registry.getRegistry().register(MARMOT_HURT);
		registry.getRegistry().register(MARMOT_DEATH);
		registry.getRegistry().register(SNOWPILE_QUAIL_IDLE);
		registry.getRegistry().register(SNOWPILE_QUAIL_HURT);
		registry.getRegistry().register(SNOWPILE_QUAIL_DEATH);
	}

	private static SoundEvent register(String name) {
		return new SoundEvent(new ResourceLocation(FrostRealm.MODID, name)).setRegistryName(FrostRealm.MODID, name);
	}
}
