package baguchan.frostrealm.world;

import net.minecraft.world.level.storage.DerivedLevelData;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WorldData;

public class FrostLevelData extends DerivedLevelData {
	private final ServerLevelData wrapped;

	public FrostLevelData(WorldData worldData, ServerLevelData levelData) {
		super(worldData, levelData);
		this.wrapped = levelData;
	}

	@Override
	public long getDayTime() {
		return this.wrapped.getDayTime();
	}

	@Override
	public void setDayTime(long pTime) {
		this.wrapped.setDayTime(pTime);
	}

	@Override
	public int getClearWeatherTime() {
		return 6000;
	}

	@Override
	public boolean isRaining() {
		return false;
	}

	@Override
	public boolean isThundering() {
		return false;
	}
}