package baguchan.frostrealm.registry;

import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;

public class FrostMusics {
	public static final Music CALM_NIGHT = createGameMusic(FrostSounds.CALM_NIGHT_BGM.get());
	public static final Music FRSOT_MOON = createGameMusic(FrostSounds.FROST_MOON_BGM.get());

	public static Music createGameMusic(SoundEvent p_11654_) {
		return new Music(p_11654_, 12000, 24000, false);
	}

}
