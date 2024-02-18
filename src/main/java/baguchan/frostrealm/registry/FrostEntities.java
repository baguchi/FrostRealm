package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.entity.*;
import baguchan.frostrealm.entity.projectile.WarpedCrystalShard;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.SpawnPlacementRegisterEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = FrostRealm.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FrostEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, FrostRealm.MODID);

    public static final Supplier<EntityType<BushBug>> BUSH_BUG = ENTITIES.register("bush_bug", () -> EntityType.Builder.of(BushBug::new, MobCategory.CREATURE).sized(0.6F, 0.6F).build(prefix("bush_bug")));

    public static final Supplier<EntityType<Marmot>> MARMOT = ENTITIES.register("marmot", () -> EntityType.Builder.of(Marmot::new, MobCategory.CREATURE).sized(0.65F, 0.6F).build(prefix("marmot")));
    public static final Supplier<EntityType<SnowPileQuail>> SNOWPILE_QUAIL = ENTITIES.register("snowpile_quail", () -> EntityType.Builder.of(SnowPileQuail::new, MobCategory.CREATURE).sized(0.6F, 0.6F).build(prefix("snowpile_quail")));
    public static final Supplier<EntityType<CrystalFox>> CRYSTAL_FOX = ENTITIES.register("crystal_fox", () -> EntityType.Builder.of(CrystalFox::new, MobCategory.CREATURE).sized(0.6F, 0.7F).clientTrackingRange(8).build(prefix("crystal_fox")));
    public static final Supplier<EntityType<SnowMole>> SNOW_MOLE = ENTITIES.register("snow_mole", () -> EntityType.Builder.of(SnowMole::new, MobCategory.CREATURE).sized(0.6F, 0.6F).clientTrackingRange(8).immuneTo(Blocks.POWDER_SNOW).immuneTo(Blocks.POWDER_SNOW_CAULDRON).build(prefix("snow_mole")));
    public static final Supplier<EntityType<Seal>> SEAL = ENTITIES.register("seal", () -> EntityType.Builder.of(Seal::new, MobCategory.CREATURE).sized(0.95F, 0.8F).clientTrackingRange(10).build(prefix("seal")));

    public static final Supplier<EntityType<Yeti>> YETI = ENTITIES.register("yeti", () -> EntityType.Builder.of(Yeti::new, MobCategory.CREATURE).sized(1.6F, 1.95F).build(prefix("yeti")));
    public static final Supplier<EntityType<FrostWraith>> FROST_WRAITH = ENTITIES.register("frost_wraith", () -> EntityType.Builder.of(FrostWraith::new, FrostMobCategory.FROSTREALM_WEATHER_MONSTER).sized(0.6F, 2.1F).build(prefix("frost_wraith")));
    public static final Supplier<EntityType<Seeker>> SEEKER = ENTITIES.register("seeker", () -> EntityType.Builder.of(Seeker::new, MobCategory.MONSTER).sized(0.6F, 1.99F).immuneTo(Blocks.POWDER_SNOW).clientTrackingRange(8).build(prefix("seeker")));
    public static final Supplier<EntityType<MindVine>> MIND_VINE = ENTITIES.register("mind_vine", () -> EntityType.Builder.of(MindVine::new, MobCategory.MONSTER).sized(0.5F, 2.375F).clientTrackingRange(8).build(prefix("mind_vine")));

    public static final Supplier<EntityType<AstraBall>> ASTRA_BALL = ENTITIES.register("astra_ball", () -> EntityType.Builder.of(AstraBall::new, MobCategory.MONSTER).sized(0.5F, 0.5F).build(prefix("astra_ball")));
    public static final Supplier<EntityType<FrostBoar>> FROST_BOAR = ENTITIES.register("frost_boar", () -> EntityType.Builder.of(FrostBoar::new, MobCategory.CREATURE).sized(1.8F, 1.95F).build(prefix("frost_boar")));
    public static final Supplier<EntityType<FrostormDragon>> FROSTORM_DRAGON = ENTITIES.register("frostorm_dragon", () -> EntityType.Builder.of(FrostormDragon::new, MobCategory.MONSTER).sized(3.25F, 3.0F).build(prefix("frostorm_dragon")));
    public static final Supplier<EntityType<WarpedCrystalShard>> WARPED_CRYSTAL_SHARD = ENTITIES.register("warped_crystal", () -> EntityType.Builder.<WarpedCrystalShard>of(WarpedCrystalShard::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(prefix("warped_crystal")));

    private static String prefix(String path) {
        return FrostRealm.MODID + "." + path;
    }


    @SubscribeEvent
    public static void registerEntityAttribute(EntityAttributeCreationEvent event) {
        event.put(MARMOT.get(), Marmot.createAttributes().build());
        event.put(SNOWPILE_QUAIL.get(), SnowPileQuail.createAttributes().build());
        event.put(CRYSTAL_FOX.get(), CrystalFox.createAttributes().build());
        event.put(SNOW_MOLE.get(), SnowMole.createAttributes().build());
        event.put(SEAL.get(), Seal.createAttributes().build());
        event.put(BUSH_BUG.get(), BushBug.createAttributes().build());

        event.put(YETI.get(), Yeti.createAttributeMap().build());
        event.put(FROST_WRAITH.get(), FrostWraith.createAttributes().build());
        event.put(SEEKER.get(), Seeker.createAttributes().build());
        event.put(MIND_VINE.get(), MindVine.createAttributes().build());
        event.put(ASTRA_BALL.get(), AstraBall.createAttributes().build());
        event.put(FROST_BOAR.get(), FrostBoar.createAttributes().build());
        event.put(FROSTORM_DRAGON.get(), FrostormDragon.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawn(SpawnPlacementRegisterEvent event) {

        event.register(MARMOT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Marmot::checkFrostAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(SNOWPILE_QUAIL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SnowPileQuail::checkFrostAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(CRYSTAL_FOX.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CrystalFox::checkFrostAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(SNOW_MOLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SnowMole::checkSnowMoleSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(SEAL.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Seal::checkSealSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(BUSH_BUG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FrostAnimal::checkFrostAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);


        event.register(YETI.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(FROST_WRAITH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FrozenMonster::checkFrozenMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);

        event.register(SEEKER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Seeker::checkStraySpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ASTRA_BALL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(FROST_BOAR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FrostBoar::checkFrostAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }
}