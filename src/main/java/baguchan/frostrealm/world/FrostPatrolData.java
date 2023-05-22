package baguchan.frostrealm.world;

import baguchan.frostrealm.registry.FrostDimensions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.HashMap;
import java.util.Map;

public class FrostPatrolData extends SavedData {

    private static final String IDENTIFIER = "frost_patrol_world_data";
    private int patrolSpawnDelay;
    private float patrolSpawnChance;
    private static Map<Level, FrostPatrolData> dataMap = new HashMap<>();

    public FrostPatrolData() {
        super();
    }

    public static FrostPatrolData get(Level world) {
        if (world instanceof ServerLevel) {
            ServerLevel overworld = world.getServer().getLevel(FrostDimensions.FROSTREALM_LEVEL);
            FrostPatrolData fromMap = dataMap.get(overworld);
            if (fromMap == null) {
                DimensionDataStorage storage = overworld.getDataStorage();
                FrostPatrolData data = storage.computeIfAbsent(FrostPatrolData::load, FrostPatrolData::new, IDENTIFIER);
                if (data != null) {
                    data.setDirty();
                }
                dataMap.put(world, data);
                return data;
            }
            return fromMap;
        }
        return null;
    }

    public static FrostPatrolData load(CompoundTag nbt) {
        FrostPatrolData data = new FrostPatrolData();
        if (nbt.contains("PatrolSpawnDelay", 99)) {
            data.patrolSpawnDelay = nbt.getInt("PatrolSpawnDelay");
        }
        if (nbt.contains("PatrolSpawnChance", 99)) {
            data.patrolSpawnChance = nbt.getFloat("PatrolSpawnChance");
        }
        return data;
    }

    public int getPatrolSpawnDelay() {
        return this.patrolSpawnDelay;
    }

    public void setPatrolSpawnDelay(int delay) {
        this.patrolSpawnDelay = delay;
    }

    public float getPatrolSpawnChance() {
        return this.patrolSpawnChance;
    }

    public void setPatrolSpawnChance(float chance) {
        this.patrolSpawnChance = chance;
    }


    @Override
    public CompoundTag save(CompoundTag compound) {
        compound.putInt("PatrolSpawnDelay", this.patrolSpawnDelay);
        compound.putFloat("PatrolSpawnChance", this.patrolSpawnChance);
        return compound;
    }
}