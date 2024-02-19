package baguchan.frostrealm.utils.aurorapower;

public class AuroraCombatRules {
    public static final float MIN_POISON_PERCENT_RATIO = 0.1F;

    public static float getDamageAddition(float damage, int mobEnchantLevel) {
        damage += 1.0F + Math.max(0, mobEnchantLevel - 1) * 1.0F;


        return damage;
    }

    public static float getDamageAdditionWithExtra(float damage, int mobEnchantLevel, float armor) {
        float damageExtra = 1.0F + Math.max(0, mobEnchantLevel - 1) * 1.0F;
        damageExtra *= 1 + (armor / 10.0F);

        return damage + damageExtra;
    }

    public static float getDamageReduction(float damage, int mobEnchantLevel) {
        float f2 = mobEnchantLevel * 0.5F;
        damage *= (1.0F - f2);
        return damage;
    }
}
