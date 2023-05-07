package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.entity.*;
import baguchan.frostrealm.entity.projectile.WarpedCrystalShard;
import baguchan.frostrealm.entity.vehicle.ChestSledge;
import baguchan.frostrealm.entity.vehicle.Sledge;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = FrostRealm.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FrostEntities {
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, FrostRealm.MODID);


	public static final RegistryObject<EntityType<Marmot>> MARMOT = ENTITIES.register("marmot", () -> EntityType.Builder.of(Marmot::new, MobCategory.CREATURE).sized(0.65F, 0.6F).build(prefix("marmot")));
	public static final RegistryObject<EntityType<SnowPileQuail>> SNOWPILE_QUAIL = ENTITIES.register("snowpile_quail", () -> EntityType.Builder.of(SnowPileQuail::new, MobCategory.CREATURE).sized(0.6F, 0.6F).build(prefix("snowpile_quail")));
	public static final RegistryObject<EntityType<FrostWolf>> FROST_WOLF = ENTITIES.register("frost_wolf", () -> EntityType.Builder.of(FrostWolf::new, MobCategory.CREATURE).sized(0.65F, 0.95F).build(prefix("frost_wolf")));
	public static final RegistryObject<EntityType<CrystalFox>> CRYSTAL_FOX = ENTITIES.register("crystal_fox", () -> EntityType.Builder.of(CrystalFox::new, MobCategory.CREATURE).sized(0.6F, 0.7F).clientTrackingRange(8).build(prefix("crystal_fox")));
	public static final RegistryObject<EntityType<SnowMole>> SNOW_MOLE = ENTITIES.register("snow_mole", () -> EntityType.Builder.of(SnowMole::new, MobCategory.CREATURE).sized(0.6F, 0.6F).clientTrackingRange(8).immuneTo(Blocks.POWDER_SNOW_CAULDRON).build(prefix("snow_mole")));
	public static final RegistryObject<EntityType<Yeti>> YETI = ENTITIES.register("yeti", () -> EntityType.Builder.of(Yeti::new, MobCategory.CREATURE).sized(1.6F, 1.95F).build(prefix("yeti")));
	public static final RegistryObject<EntityType<FrostWraith>> FROST_WRAITH = ENTITIES.register("frost_wraith", () -> EntityType.Builder.of(FrostWraith::new, MobCategory.MONSTER).sized(0.6F, 2.1F).build(prefix("frost_wraith")));
	public static final RegistryObject<EntityType<ClustWraith>> CLUST_WRAITH = ENTITIES.register("clust_wraith", () -> EntityType.Builder.of(ClustWraith::new, MobCategory.MONSTER).sized(0.6F, 1.4F).build(prefix("clust_wraith")));
	public static final RegistryObject<EntityType<Gokkudillo>> GOKKUDILLO = ENTITIES.register("gokkudillo", () -> EntityType.Builder.of(Gokkudillo::new, MobCategory.MONSTER).sized(1.0F, 0.85F).fireImmune().build(prefix("gokkudillo")));
	public static final RegistryObject<EntityType<FrostBeaster>> FROST_BEASTER = ENTITIES.register("frost_beaster", () -> EntityType.Builder.of(FrostBeaster::new, MobCategory.MONSTER).sized(0.65F, 1.95F).build(prefix("frost_beaster")));
	public static final RegistryObject<EntityType<AstraBall>> ASTRA_BALL = ENTITIES.register("astra_ball", () -> EntityType.Builder.of(AstraBall::new, MobCategory.MONSTER).sized(0.5F, 0.5F).build(prefix("astra_ball")));
    public static final RegistryObject<EntityType<FrostBoar>> FROST_BOAR = ENTITIES.register("frost_boar", () -> EntityType.Builder.of(FrostBoar::new, MobCategory.CREATURE).sized(1.8F, 1.95F).build(prefix("frost_boar")));
    public static final RegistryObject<EntityType<Sledge>> SLEDGE = ENTITIES.register("sledge", () -> EntityType.Builder.<Sledge>of(Sledge::new, MobCategory.MISC).sized(1.375F, 0.5625F).build(prefix("sledge")));
	public static final RegistryObject<EntityType<ChestSledge>> CHEST_SLEDGE = ENTITIES.register("chest_sledge", () -> EntityType.Builder.<ChestSledge>of(ChestSledge::new, MobCategory.MISC).sized(1.375F, 0.5625F).build(prefix("chest_sledge")));

	public static final RegistryObject<EntityType<WarpedCrystalShard>> WARPED_CRYSTAL_SHARD = ENTITIES.register("warped_crystal", () -> EntityType.Builder.<WarpedCrystalShard>of(WarpedCrystalShard::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(prefix("warped_crystal")));

	private static String prefix(String path) {
		return FrostRealm.MODID + "." + path;
	}


	@SubscribeEvent
	public static void registerEntityAttribute(EntityAttributeCreationEvent event) {
		event.put(MARMOT.get(), Marmot.createAttributes().build());
		event.put(SNOWPILE_QUAIL.get(), SnowPileQuail.createAttributes().build());
		event.put(FROST_WOLF.get(), FrostWolf.createAttributes().build());
		event.put(CRYSTAL_FOX.get(), CrystalFox.createAttributes().build());
		event.put(SNOW_MOLE.get(), SnowMole.createAttributes().build());

		event.put(YETI.get(), Yeti.createAttributeMap().build());
		event.put(FROST_WRAITH.get(), FrostWraith.createAttributes().build());
		event.put(CLUST_WRAITH.get(), ClustWraith.createAttributes().build());
		event.put(GOKKUDILLO.get(), Gokkudillo.createAttributes().build());
		event.put(FROST_BEASTER.get(), FrostBeaster.createAttributes().build());
		event.put(ASTRA_BALL.get(), AstraBall.createAttributes().build());
		event.put(FROST_BOAR.get(), AstraBall.createAttributes().build());


		SpawnPlacements.register(MARMOT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Marmot::checkFrostAnimalSpawnRules);

		SpawnPlacements.register(SNOWPILE_QUAIL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SnowPileQuail::checkFrostAnimalSpawnRules);
		SpawnPlacements.register(FROST_WOLF.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FrostWolf::checkFrostWolfSpawnRules);
		SpawnPlacements.register(CRYSTAL_FOX.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CrystalFox::checkFrostAnimalSpawnRules);
		SpawnPlacements.register(SNOW_MOLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SnowMole::checkSnowMoleSpawnRules);


		SpawnPlacements.register(YETI.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);

		SpawnPlacements.register(FROST_WRAITH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(CLUST_WRAITH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);

		SpawnPlacements.register(GOKKUDILLO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Gokkudillo::checkGokkudilloSpawnRules);
		SpawnPlacements.register(FROST_BEASTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FrostBeaster::checkFrostBeasterSpawnRules);
		SpawnPlacements.register(ASTRA_BALL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(FROST_BOAR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FrostBoar::checkFrostAnimalSpawnRules);

	}
}