package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FrostSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, FrostRealm.MODID);


    public static final DeferredHolder<SoundEvent, SoundEvent> FROST_MOON_BGM = register("music.frost_moon");
    public static final DeferredHolder<SoundEvent, SoundEvent> CALM_NIGHT_BGM = register("music.calm_night");
    public static final DeferredHolder<SoundEvent, SoundEvent> BLIZZARD_AMBIENT = register("ambient.blizzard");
    public static final DeferredHolder<SoundEvent, SoundEvent> MARMOT_IDLE = register("entity.marmot.idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> MARMOT_HURT = register("entity.marmot.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> MARMOT_DEATH = register("entity.marmot.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> SNOWPILE_QUAIL_IDLE = register("entity.snowpile_quail.idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> SNOWPILE_QUAIL_HURT = register("entity.snowpile_quail.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> SNOWPILE_QUAIL_DEATH = register("entity.snowpile_quail.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> SEAL_IDLE = register("entity.seal.idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> SEAL_HURT = register("entity.seal.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> SEAL_DEATH = register("entity.seal.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> SEAL_FART = register("entity.seal.fart");
    public static final DeferredHolder<SoundEvent, SoundEvent> YETI_IDLE = register("entity.yeti.idle");


    public static final DeferredHolder<SoundEvent, SoundEvent> MORTAR = register("ambient.mortar.moving");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String sound) {
        ResourceLocation name = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, sound);
		return SOUND_EVENTS.register(sound, () -> SoundEvent.createVariableRangeEvent(name));
	}
}
