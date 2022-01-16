package baguchan.frostrealm.client.animation;

import com.mojang.math.Vector3f;

//From the citadel mod code, I remade own version
public class ModelPartPos {
	private Vector3f rotation = new Vector3f();
	private Vector3f offsetRotation = new Vector3f();

	public void addRotation(Vector3f rotation) {
		this.rotation.add(rotation);
	}

	public void addOffsetRotation(Vector3f rotation) {
		this.offsetRotation.add(rotation);
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	public void setOffsetRotation(Vector3f offsetRotation) {
		this.offsetRotation = offsetRotation;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public Vector3f getOffsetRotation() {
		return offsetRotation;
	}
}
