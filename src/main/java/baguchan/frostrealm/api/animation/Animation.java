package baguchan.frostrealm.api.animation;

//From the citadel mod code, I remade own version
public class Animation {
	private final int duration;

	private Animation(int duration) {
		this.duration = duration;
	}

	public static Animation create(int duration) {
		return new Animation(duration);
	}

	/**
	 * @return the duration of this model
	 */
	public int getDuration() {
		return this.duration;
	}
}