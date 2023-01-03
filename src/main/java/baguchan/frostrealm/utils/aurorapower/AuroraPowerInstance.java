package baguchan.frostrealm.utils.aurorapower;

import baguchan.frostrealm.aurorapower.AuroraPower;
import net.minecraft.util.random.WeightedEntry;

public class AuroraPowerInstance extends WeightedEntry.IntrusiveBase {
	public final AuroraPower auroraPower;
	public final int level;

	public AuroraPowerInstance(AuroraPower p_44950_, int p_44951_) {
		super(p_44950_.getRarity().getWeight());
		this.auroraPower = p_44950_;
		this.level = p_44951_;
	}
}