package baguchan.frostrealm.utils;

import static java.lang.Math.pow;

public class EasingMath {
	public static double easeInOutCubic(double x) {
		return x < 0.5 ? 4 * x * x * x : 1 - pow(-2 * x + 2, 3) / 2;
	}
}
