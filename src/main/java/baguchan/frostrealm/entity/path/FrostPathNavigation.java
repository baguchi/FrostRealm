package baguchan.frostrealm.entity.path;

import baguchan.frostrealm.capability.FrostWeatherCapability;
import baguchan.frostrealm.utils.BlizzardUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Node;

public class FrostPathNavigation extends GroundPathNavigation {
	public FrostPathNavigation(Mob p_26448_, Level p_26449_) {
		super(p_26448_, p_26449_);
	}

	@Override
	protected void trimPath() {
		super.trimPath();

		if (FrostWeatherCapability.isBadWeatherActive(this.level) && BlizzardUtils.isAffectWeather(this.mob, new BlockPos(this.mob.getX(), this.mob.getY() + 0.5D, this.mob.getZ()))) {
			return;
		}

		for (int i = 0; i < this.path.getNodeCount(); ++i) {
			Node node = this.path.getNode(i);
			if ((FrostWeatherCapability.isBadWeatherActive(this.level) && BlizzardUtils.isAffectWeather(this.mob, new BlockPos(node.x, node.y, node.z)))) {
				this.path.truncateNodes(i);
				return;
			}
		}
	}
}
