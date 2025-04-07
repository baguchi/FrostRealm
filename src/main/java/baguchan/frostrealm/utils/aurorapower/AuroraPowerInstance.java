package baguchan.frostrealm.utils.aurorapower;

import baguchan.frostrealm.aurorapower.AuroraPower;

public class AuroraPowerInstance {
    public final AuroraPower auroraPower;
    public final int level;

    public AuroraPowerInstance(AuroraPower p_44950_, int p_44951_) {
        this.auroraPower = p_44950_;
        this.level = p_44951_;
    }

    public AuroraPower getAuroraPower() {
        return auroraPower;
    }

    public int getLevel() {
        return level;
    }

    public int weight() {
        return this.getAuroraPower().getRarity().getWeight();
    }
}