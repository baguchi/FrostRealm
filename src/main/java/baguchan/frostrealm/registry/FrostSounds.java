package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FrostSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, FrostRealm.MODID);


    public static final Supplier<SoundEvent> FROST_MOON_BGM = register("music.frost_moon");
    public static final Supplier<SoundEvent> CALM_NIGHT_BGM = register("music.calm_night");
    public static final Supplier<SoundEvent> BLIZZARD_AMBIENT = register("ambient.blizzard");
    public static final Supplier<SoundEvent> MARMOT_IDLE = register("entity.marmot.idle");
    public static final Supplier<SoundEvent> MARMOT_HURT = register("entity.marmot.hurt");
    public static final Supplier<SoundEvent> MARMOT_DEATH = register("entity.marmot.death");
    public static final Supplier<SoundEvent> SNOWPILE_QUAIL_IDLE = register("entity.snowpile_quail.idle");
    public static final Supplier<SoundEvent> SNOWPILE_QUAIL_HURT = register("entity.snowpile_quail.hurt");
    public static final Supplier<SoundEvent> SNOWPILE_QUAIL_DEATH = register("entity.snowpile_quail.death");
    public static final Supplier<SoundEvent> SEAL_IDLE = register("entity.seal.idle");
    public static final Supplier<SoundEvent> SEAL_HURT = register("entity.seal.hurt");
    public static final Supplier<SoundEvent> SEAL_DEATH = register("entity.seal.death");
    public static final Supplier<SoundEvent> SEAL_FART = register("entity.seal.fart");

    public static final Supplier<SoundEvent> MORTAR = register("ambient.mortar.moving");

    private static Supplier<SoundEvent> register(String sound) {
        ResourceLocation name = ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, sound);
		return SOUND_EVENTS.register(sound, () -> SoundEvent.createVariableRangeEvent(name));
	}
}
