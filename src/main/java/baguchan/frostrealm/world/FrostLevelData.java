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
    public long getGameTime() {
        return this.wrapped.getGameTime();
    }

    @Override
    public void setGameTime(long pTime) {
        this.wrapped.setGameTime(pTime);
    }

}