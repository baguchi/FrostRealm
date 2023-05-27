package baguchan.frostrealm.world;

import baguchan.frostrealm.entity.Yeti;
import baguchan.frostrealm.registry.FrostDimensions;
import baguchan.frostrealm.registry.FrostEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.levelgen.Heightmap;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Random;

public class FrostPatrolSpawner {
    private final Random random = new Random();
    private final ServerLevel world;
    private int timer;
    private int delay;
    private float chance;

    public FrostPatrolSpawner(ServerLevel level) {
        this.world = level;
        this.timer = 1200;
        FrostPatrolData worldinfo = FrostPatrolData.get(level);
        this.delay = worldinfo.getPatrolSpawnDelay();
        this.chance = worldinfo.getPatrolSpawnChance();
        if (this.delay == 0 && this.chance == 0) {
            this.delay = 12000;
            worldinfo.setPatrolSpawnDelay(this.delay);
            this.chance = 3 * 0.1F;
            worldinfo.setPatrolSpawnChance(this.chance);
        }

    }

    public void tick() {
        if (--this.timer <= 0) {
            this.timer = 1200;
            this.delay -= 1200;
            if (delay < 0) {
                delay = 0;
            }
            FrostPatrolData worldinfo = FrostPatrolData.get(world);
            worldinfo.setPatrolSpawnDelay(this.delay);
            if (this.delay <= 0) {
                this.delay = 12000;
                if (this.world.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING) && this.world.getGameRules().getBoolean(GameRules.RULE_DO_TRADER_SPAWNING)) {
                    float prevChance = this.chance;
                    this.chance = Mth.clamp(this.chance + 0.1F, 0, 1F);
                    worldinfo.setPatrolSpawnChance(this.chance);
                    if (this.random.nextFloat() <= prevChance && this.attemptSpawnWanderingPatrol()) {
                        this.chance = 0.1F;
                    }
                }
            }
        }

    }

    private boolean attemptSpawnWanderingPatrol() {
        FrostPatrolData worldinfo = FrostPatrolData.get(world);
        Player playerentity = this.world.getRandomPlayer();
        if (playerentity == null) {
            return true;
        } else if (this.world.dimension() != FrostDimensions.FROSTREALM_LEVEL) {
            return false;
        } else {
            BlockPos blockpos = BlockPos.containing(playerentity.position());
            BlockPos blockpos2 = this.findSpawnLocationNear(blockpos, 89);
            if (blockpos2 != null && this.func_226559_a_(blockpos2) && blockpos2.distSqr(blockpos) > 225) {
                if (this.hasEnoughSpace(world, blockpos2)) {
                    if (world.getBiome(blockpos2).is(BiomeTags.WITHOUT_WANDERING_TRADER_SPAWNS)) {
                        return false;
                    }

                    boolean flag = false;

                    for (int i = 0; i < 8; ++i) {
                        Yeti yeti = FrostEntities.YETI.get().create(world);
                        if (yeti != null) {
                            if (!flag) {
                                yeti.setHuntLeader(true);
                                flag = true;
                            }
                            yeti.setHunt(true);
                            yeti.moveTo((double) blockpos2.getX() + 0.5D, (double) blockpos2.getY(), (double) blockpos2.getZ() + 0.5D, 0.0F, 0.0F);
                            yeti.finalizeSpawn(world, world.getCurrentDifficultyAt(blockpos2), MobSpawnType.PATROL, (SpawnGroupData) null, (CompoundTag) null);

                            world.addFreshEntityWithPassengers(yeti);
                        }
                    }

                    return true;
                }

                return true;
            }
            return false;
        }
    }

    @Nullable
    private BlockPos findSpawnLocationNear(BlockPos center, int xzDistance) {
        BlockPos blockpos = null;

        for (int i = 0; i < 10; ++i) {
            int j = center.getX() + (this.random.nextInt(xzDistance * 2) - xzDistance);
            int k = center.getZ() + (this.random.nextInt(xzDistance * 2) - xzDistance);
            int l = this.world.getHeight(Heightmap.Types.WORLD_SURFACE, j, k);
            BlockPos blockpos1 = new BlockPos(j, l, k);
            if (NaturalSpawner.isSpawnPositionOk(SpawnPlacements.Type.ON_GROUND, this.world, blockpos1, FrostEntities.YETI.get())) {
                blockpos = blockpos1;
                break;
            }
        }

        return blockpos;
    }

    private boolean func_226559_a_(BlockPos p_226559_1_) {
        Iterator var2 = BlockPos.betweenClosed(p_226559_1_, p_226559_1_.offset(1, 2, 1)).iterator();

        BlockPos blockpos;
        do {
            if (!var2.hasNext()) {
                return true;
            }

            blockpos = (BlockPos) var2.next();
        } while (this.world.getBlockState(blockpos).getBlockSupportShape(this.world, blockpos).isEmpty() && world.getFluidState(blockpos).isEmpty());

        return false;
    }


    private boolean hasEnoughSpace(ServerLevel level, BlockPos pos) {
        if (!level.isLoaded(pos)) {
            return false;
        }

        for (BlockPos blockpos : BlockPos.betweenClosed(pos, pos.offset(1, 2, 1))) {
            if (!level.getBlockState(blockpos).getCollisionShape(level, blockpos).isEmpty()) {
                return false;
            }
        }

        return true;
    }
}