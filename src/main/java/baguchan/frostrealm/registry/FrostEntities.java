package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.entity.Auroray;
import baguchan.frostrealm.entity.ClustWraith;
import baguchan.frostrealm.entity.CrystalFox;
import baguchan.frostrealm.entity.CrystalTortoise;
import baguchan.frostrealm.entity.FrostBeaster;
import baguchan.frostrealm.entity.FrostWolf;
import baguchan.frostrealm.entity.FrostWraith;
import baguchan.frostrealm.entity.Gokkudillo;
import baguchan.frostrealm.entity.Gokkur;
import baguchan.frostrealm.entity.Marmot;
import baguchan.frostrealm.entity.SnowMole;
import baguchan.frostrealm.entity.SnowPileQuail;
import baguchan.frostrealm.entity.Yeti;
import baguchan.frostrealm.entity.projectile.WarpedCrystalShard;
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


	public static final RegistryObject<EntityType<CrystalTortoise>> CRYSTAL_TORTOISE = ENTITIES.register("crystal_tortoise", () -> EntityType.Builder.of(CrystalTortoise::new, MobCategory.CREATURE).sized(0.85F, 0.85F).build(prefix("crystal_tortoise")));
	public static final RegistryObject<EntityType<Marmot>> MARMOT = ENTITIES.register("marmot", () -> EntityType.Builder.of(Marmot::new, MobCategory.CREATURE).sized(0.65F, 0.6F).build(prefix("marmot")));
	public static final RegistryObject<EntityType<SnowPileQuail>> SNOWPILE_QUAIL = ENTITIES.register("snowpile_quail", () -> EntityType.Builder.of(SnowPileQuail::new, MobCategory.CREATURE).sized(0.6F, 0.6F).build(prefix("snowpile_quail")));
	public static final RegistryObject<EntityType<FrostWolf>> FROST_WOLF = ENTITIES.register("frost_wolf", () -> EntityType.Builder.of(FrostWolf::new, MobCategory.CREATURE).sized(0.8F, 0.95F).build(prefix("frost_wolf")));
	public static final RegistryObject<EntityType<CrystalFox>> CRYSTAL_FOX = ENTITIES.register("crystal_fox", () -> EntityType.Builder.of(CrystalFox::new, MobCategory.CREATURE).sized(0.6F, 0.7F).clientTrackingRange(8).build(prefix("crystal_fox")));
	public static final RegistryObject<EntityType<SnowMole>> SNOW_MOLE = ENTITIES.register("snow_mole", () -> EntityType.Builder.of(SnowMole::new, MobCategory.CREATURE).sized(0.6F, 0.6F).clientTrackingRange(8).immuneTo(Blocks.POWDER_SNOW_CAULDRON).build(prefix("snow_mole")));


	public static final RegistryObject<EntityType<Yeti>> YETI = ENTITIES.register("yeti", () -> EntityType.Builder.of(Yeti::new, MobCategory.CREATURE).sized(1.6F, 1.95F).build(prefix("yeti")));
	public static final RegistryObject<EntityType<FrostWraith>> FROST_WRAITH = ENTITIES.register("frost_wraith", () -> EntityType.Builder.of(FrostWraith::new, MobCategory.MONSTER).sized(0.6F, 2.1F).build(prefix("frost_wraith")));
	public static final RegistryObject<EntityType<ClustWraith>> CLUST_WRAITH = ENTITIES.register("clust_wraith", () -> EntityType.Builder.of(ClustWraith::new, MobCategory.MONSTER).sized(0.6F, 1.4F).build(prefix("clust_wraith")));

	public static final RegistryObject<EntityType<Gokkur>> GOKKUR = ENTITIES.register("gokkur", () -> EntityType.Builder.of(Gokkur::new, MobCategory.MONSTER).sized(0.6F, 0.6F).fireImmune().build(prefix("gokkur")));
	public static final RegistryObject<EntityType<Gokkudillo>> GOKKUDILLO = ENTITIES.register("gokkudillo", () -> EntityType.Builder.of(Gokkudillo::new, MobCategory.MONSTER).sized(0.65F, 0.65F).fireImmune().build(prefix("gokkudillo")));
	public static final RegistryObject<EntityType<FrostBeaster>> FROST_BEASTER = ENTITIES.register("frost_beaster", () -> EntityType.Builder.of(FrostBeaster::new, MobCategory.MONSTER).sized(0.65F, 1.95F).build(prefix("frost_beaster")));
	public static final RegistryObject<EntityType<Auroray>> AURORAY = ENTITIES.register("auroray", () -> EntityType.Builder.of(Auroray::new, MobCategory.MONSTER).sized(2.0F, 0.6F).build(prefix("auroray")));


	public static final RegistryObject<EntityType<WarpedCrystalShard>> WARPED_CRYSTAL = ENTITIES.register("warped_crystal", () -> EntityType.Builder.<WarpedCrystalShard>of(WarpedCrystalShard::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(prefix("warped_crystal")));


	private static String prefix(String path) {
		return FrostRealm.MODID + "." + path;
	}


	@SubscribeEvent
	public static void registerEntityAttribute(EntityAttributeCreationEvent event) {
		event.put(CRYSTAL_TORTOISE.get(), CrystalTortoise.createAttributes().build());
		event.put(MARMOT.get(), Marmot.createAttributes().build());
		event.put(SNOWPILE_QUAIL.get(), SnowPileQuail.createAttributes().build());
		event.put(FROST_WOLF.get(), FrostWolf.createAttributes().build());
		event.put(CRYSTAL_FOX.get(), CrystalFox.createAttributes().build());
		event.put(SNOW_MOLE.get(), SnowMole.createAttributes().build());

		event.put(YETI.get(), Yeti.createAttributeMap().build());
		event.put(FROST_WRAITH.get(), FrostWraith.createAttributes().build());
		event.put(CLUST_WRAITH.get(), ClustWraith.createAttributes().build());
		event.put(GOKKUR.get(), Gokkur.createAttributes().build());
		event.put(GOKKUDILLO.get(), Gokkudillo.createAttributes().build());
		event.put(FROST_BEASTER.get(), FrostBeaster.createAttributes().build());
		event.put(AURORAY.get(), Auroray.createAttributes().build());

		SpawnPlacements.register(CRYSTAL_TORTOISE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CrystalTortoise::checkTortoiseSpawnRules);
		SpawnPlacements.register(MARMOT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Marmot::checkMarmotSpawnRules);
		SpawnPlacements.register(SNOWPILE_QUAIL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SnowPileQuail::checkQuailSpawnRules);
		SpawnPlacements.register(FROST_WOLF.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FrostWolf::checkFrostWolfSpawnRules);
		SpawnPlacements.register(CRYSTAL_FOX.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CrystalFox::checkCrystalFoxSpawnRules);
		SpawnPlacements.register(SNOW_MOLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SnowMole::checkSnowMoleSpawnRules);


		SpawnPlacements.register(YETI.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
		SpawnPlacements.register(FROST_WRAITH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(CLUST_WRAITH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);

		SpawnPlacements.register(GOKKUR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Gokkur::checkGokkurSpawnRules);
		SpawnPlacements.register(GOKKUDILLO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Gokkudillo::checkGokkudilloSpawnRules);
		SpawnPlacements.register(FROST_BEASTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FrostBeaster::checkFrostBeasterSpawnRules);
		SpawnPlacements.register(AURORAY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
	}
}