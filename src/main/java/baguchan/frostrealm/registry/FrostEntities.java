package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.entity.Yeti;
import baguchan.frostrealm.entity.animal.*;
import baguchan.frostrealm.entity.boss.Seeker;
import baguchan.frostrealm.entity.hostile.*;
import baguchan.frostrealm.entity.projectile.FlyingBlockEntity;
import baguchan.frostrealm.entity.projectile.VenomBall;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = FrostRealm.MODID)
public class FrostEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, FrostRealm.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<Marmot>> MARMOT = ENTITIES.register("marmot", () -> EntityType.Builder.of(Marmot::new, MobCategory.CREATURE).sized(0.65F, 0.6F).eyeHeight(0.4F).build(prefix("marmot")));
    public static final DeferredHolder<EntityType<?>, EntityType<SnowPileQuail>> SNOWPILE_QUAIL = ENTITIES.register("snowpile_quail", () -> EntityType.Builder.of(SnowPileQuail::new, MobCategory.CREATURE).sized(0.6F, 0.6F).eyeHeight(0.4F).build(prefix("snowpile_quail")));
    public static final DeferredHolder<EntityType<?>, EntityType<CrystalFox>> CRYSTAL_FOX = ENTITIES.register("crystal_fox", () -> EntityType.Builder.of(CrystalFox::new, MobCategory.CREATURE).sized(0.6F, 0.7F).eyeHeight(0.4F).clientTrackingRange(8).build(prefix("crystal_fox")));
    public static final DeferredHolder<EntityType<?>, EntityType<SnowMole>> SNOW_MOLE = ENTITIES.register("snow_mole", () -> EntityType.Builder.of(SnowMole::new, MobCategory.CREATURE).sized(0.6F, 0.6F).eyeHeight(0.3F).clientTrackingRange(8).immuneTo(Blocks.POWDER_SNOW).immuneTo(Blocks.POWDER_SNOW_CAULDRON).build(prefix("snow_mole")));
    public static final DeferredHolder<EntityType<?>, EntityType<Seal>> SEAL = ENTITIES.register("seal", () -> EntityType.Builder.of(Seal::new, MobCategory.CREATURE).sized(0.95F, 0.8F).eyeHeight(0.45F).clientTrackingRange(10).build(prefix("seal")));
    public static final DeferredHolder<EntityType<?>, EntityType<Wolfflue>> WOLFFLUE = ENTITIES.register("wolfflue", () -> EntityType.Builder.of(Wolfflue::new, MobCategory.CREATURE).sized(1.25F, 1.4F).eyeHeight(1.2F).clientTrackingRange(10).build(prefix("wolfflue")));
    public static final DeferredHolder<EntityType<?>, EntityType<Ferret>> FERRET = ENTITIES.register("ferret", () -> EntityType.Builder.of(Ferret::new, MobCategory.CREATURE).sized(1.0F, 0.4F).eyeHeight(0.3F).clientTrackingRange(10).build(prefix("ferret")));
    public static final DeferredHolder<EntityType<?>, EntityType<SilkMoonWorm>> SILK_MOON_WORM = ENTITIES.register("silk_moon_worm", () -> EntityType.Builder.of(SilkMoonWorm::new, MobCategory.CREATURE).sized(0.5F, 0.3F).eyeHeight(0.15F).clientTrackingRange(10).build(prefix("silk_moon_worm")));
    public static final DeferredHolder<EntityType<?>, EntityType<SilkMoon>> SILK_MOON = ENTITIES.register("silk_moon", () -> EntityType.Builder.of(SilkMoon::new, MobCategory.CREATURE).sized(0.5F, 0.5F).eyeHeight(0.35F).clientTrackingRange(10).build(prefix("silk_moon")));

    public static final DeferredHolder<EntityType<?>, EntityType<Yeti>> YETI = ENTITIES.register("yeti", () -> EntityType.Builder.of(Yeti::new, MobCategory.CREATURE).sized(1.6F, 1.95F).eyeHeight(1.75F).build(prefix("yeti")));
    public static final DeferredHolder<EntityType<?>, EntityType<FrostWraith>> FROST_WRAITH = ENTITIES.register("frost_wraith", () -> EntityType.Builder.of(FrostWraith::new, FrostMobCategory.FROSTREALM_WEATHER_MONSTER).sized(0.6F, 2.1F).notInPeaceful().build(prefix("frost_wraith")));
    public static final DeferredHolder<EntityType<?>, EntityType<LesserWarrior>> LESSER_WARRIOR = ENTITIES.register("lesser_warrior", () -> EntityType.Builder.of(LesserWarrior::new, MobCategory.MONSTER).sized(0.6F, 1.99F).notInPeaceful().immuneTo(Blocks.POWDER_SNOW).clientTrackingRange(8).build(prefix("lesser_warrior")));

    public static final DeferredHolder<EntityType<?>, EntityType<AstraBall>> ASTRA_BALL = ENTITIES.register("astra_ball", () -> EntityType.Builder.of(AstraBall::new, MobCategory.MONSTER).sized(0.5F, 0.5F).notInPeaceful().eyeHeight(0.25F).build(prefix("astra_ball")));
    public static final DeferredHolder<EntityType<?>, EntityType<GlacierBoar>> GLACIER_BOAR = ENTITIES.register("glacier_boar", () -> EntityType.Builder.of(GlacierBoar::new, MobCategory.CREATURE).sized(1.8F, 2.1F).eyeHeight(1.5F).build(prefix("glacier_boar")));
    public static final DeferredHolder<EntityType<?>, EntityType<Venochem>> VENOCHEM = ENTITIES.register("venochem", () -> EntityType.Builder.of(Venochem::new, MobCategory.MONSTER).sized(0.8F, 0.8F).notInPeaceful().eyeHeight(0.45F).build(prefix("venochem")));
    public static final DeferredHolder<EntityType<?>, EntityType<Gokkur>> GOKKUR = ENTITIES.register("gokkur", () -> EntityType.Builder.of(Gokkur::new, MobCategory.MONSTER).sized(1.0F, 1.2F).notInPeaceful().eyeHeight(0.525F).fireImmune().build(prefix("gokkur")));
    public static final DeferredHolder<EntityType<?>, EntityType<UnderGokkur>> UNDER_GOKKUR = ENTITIES.register("under_gokkur", () -> EntityType.Builder.of(UnderGokkur::new, MobCategory.MONSTER).sized(1.0F, 1.2F).notInPeaceful().eyeHeight(0.525F).fireImmune().build(prefix("under_gokkur")));
    public static final DeferredHolder<EntityType<?>, EntityType<RootDeer>> ROOT_DEER = ENTITIES.register("root_deer", () -> EntityType.Builder.of(RootDeer::new, MobCategory.MONSTER).sized(0.5F, 2.375F).notInPeaceful().eyeHeight(0.35F).build(prefix("root_deer")));
    public static final DeferredHolder<EntityType<?>, EntityType<Seeker>> SEEKER = ENTITIES.register("seeker", () -> EntityType.Builder.of(Seeker::new, MobCategory.MONSTER).sized(0.8F, 5.4F).eyeHeight(5.15F).build(prefix("seeker")));

    public static final DeferredHolder<EntityType<?>, EntityType<VenomBall>> VENOM_BALL = ENTITIES.register("venom_ball", () -> EntityType.Builder.<VenomBall>of(VenomBall::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(8).updateInterval(30).build(prefix("venom_ball")));
    public static final DeferredHolder<EntityType<?>, EntityType<FlyingBlockEntity>> FLYING_BLOCK = ENTITIES.register("flying_block", () -> EntityType.Builder.<FlyingBlockEntity>of(FlyingBlockEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(10).updateInterval(40).build(prefix("flying_block")));


    private static ResourceKey<EntityType<?>> prefix(String path) {
        return ResourceKey.create(Registries.ENTITY_TYPE, FrostRealm.prefix(path));
    }


    @SubscribeEvent
    public static void registerEntityAttribute(EntityAttributeCreationEvent event) {
        event.put(MARMOT.get(), Marmot.createAttributes().build());
        event.put(SNOWPILE_QUAIL.get(), SnowPileQuail.createAttributes().build());
        event.put(CRYSTAL_FOX.get(), CrystalFox.createAttributes().build());
        event.put(SNOW_MOLE.get(), SnowMole.createAttributes().build());
        event.put(SEAL.get(), Seal.createAttributes().build());
        event.put(WOLFFLUE.get(), Wolfflue.createAttributes().build());
        event.put(FERRET.get(), Ferret.createAttributes().build());
        event.put(SILK_MOON_WORM.get(), SilkMoonWorm.createAttributes().build());
        event.put(SILK_MOON.get(), SilkMoon.createAttributes().build());

        event.put(YETI.get(), Yeti.createAttributeMap().build());
        event.put(FROST_WRAITH.get(), FrostWraith.createAttributes().build());
        event.put(LESSER_WARRIOR.get(), LesserWarrior.createAttributes().build());
        event.put(ASTRA_BALL.get(), AstraBall.createAttributes().build());
        event.put(GLACIER_BOAR.get(), GlacierBoar.createAttributes().build());
        event.put(VENOCHEM.get(), Venochem.createAttributes().build());
        event.put(GOKKUR.get(), Gokkur.createAttributes().build());
        event.put(UNDER_GOKKUR.get(), UnderGokkur.createAttributes().build());
        event.put(ROOT_DEER.get(), RootDeer.createAttributes().build());
        event.put(SEEKER.get(), Seeker.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawn(RegisterSpawnPlacementsEvent event) {
        event.register(MARMOT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Marmot::checkFrostAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);

        event.register(SNOWPILE_QUAIL.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SnowPileQuail::checkFrostAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(CRYSTAL_FOX.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CrystalFox::checkFrostAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(SNOW_MOLE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SnowMole::checkSnowMoleSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(SEAL.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Seal::checkSealSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(WOLFFLUE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Wolfflue::checkWolfSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(FERRET.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Ferret::checkWolfSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(SILK_MOON_WORM.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(SILK_MOON.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SilkMoon::checkSilkSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);

        event.register(YETI.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);

        event.register(FROST_WRAITH.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WarpedMonster::checkWarpedMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);

        event.register(LESSER_WARRIOR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LesserWarrior::checkStraySpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(ASTRA_BALL.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(GLACIER_BOAR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GlacierBoar::checkFrostAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(VENOCHEM.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Venochem::checkVenochemSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(GOKKUR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(UNDER_GOKKUR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(ROOT_DEER.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, RootDeer::checkDeerSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(SEEKER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
    }
}