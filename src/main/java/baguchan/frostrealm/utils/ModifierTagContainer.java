package baguchan.frostrealm.utils;

public class ModifierTagContainer {
    private final String id;
    private final String uuid;
    private final float scale;
    private final float strength;
    private final String equipSlot;

    public ModifierTagContainer(String id, String uuid, float scale, float strength, String equipSlot) {
        this.id = id;
        this.uuid = uuid;
        this.scale = scale;
        this.strength = strength;
        this.equipSlot = equipSlot;
    }

    public String getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public float getScale() {
        return scale;
    }

    public float getStrength() {
        return strength;
    }


    public String getEquipSlot() {
        return equipSlot;
    }
}
