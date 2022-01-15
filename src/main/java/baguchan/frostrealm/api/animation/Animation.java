package baguchan.frostrealm.api.animation;

public class Animation {
	private int duration;

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