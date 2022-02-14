package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.entity.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FrostRealm.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FrostEntities {
	public static final EntityType<CrystalTortoise> CRYSTAL_TORTOISE = EntityType.Builder.of(CrystalTortoise::new, MobCategory.CREATURE).sized(0.85F, 0.85F).build(prefix("crystal_tortoise"));
	public static final EntityType<Marmot> MARMOT = EntityType.Builder.of(Marmot::new, MobCategory.CREATURE).sized(0.65F, 0.6F).build(prefix("marmot"));
	public static final EntityType<SnowPileQuail> SNOWPILE_QUAIL = EntityType.Builder.of(SnowPileQuail::new, MobCategory.CREATURE).sized(0.6F, 0.6F).build(prefix("snowpile_quail"));
	public static final EntityType<FrostWolf> FROST_WOLF = EntityType.Builder.of(FrostWolf::new, MobCategory.CREATURE).sized(0.8F, 0.95F).build(prefix("frost_wolf"));
	public static final EntityType<CrystalFox> CRYSTAL_FOX = EntityType.Builder.of(CrystalFox::new, MobCategory.CREATURE).sized(0.6F, 0.7F).clientTrackingRange(8).build(prefix("crystal_fox"));


	public static final EntityType<Yeti> YETI = EntityType.Builder.of(Yeti::new, MobCategory.CREATURE).sized(1.6F, 1.95F).build(prefix("yeti"));
	public static final EntityType<FrostWraith> FROST_WRAITH = EntityType.Builder.of(FrostWraith::new, MobCategory.MONSTER).sized(0.6F, 1.65F).build(prefix("frost_wraith"));
	public static final EntityType<Gokkur> GOKKUR = EntityType.Builder.of(Gokkur::new, MobCategory.MONSTER).sized(0.6F, 0.6F).fireImmune().build(prefix("gokkur"));
	public static final EntityType<Gokkudillo> GOKKUDILLO = EntityType.Builder.of(Gokkudillo::new, MobCategory.MONSTER).sized(0.65F, 0.65F).fireImmune().build(prefix("gokkudillo"));
	public static final EntityType<FrostBeaster> FROST_BEASTER = EntityType.Builder.of(FrostBeaster::new, MobCategory.MONSTER).sized(0.65F, 1.95F).build(prefix("frost_beaster"));


	private static String prefix(String path) {
		return FrostRealm.MODID + "." + path;
	}

	@SubscribeEvent
	public static void registerEntity(RegistryEvent.Register<EntityType<?>> event) {
		event.getRegistry().register(CRYSTAL_TORTOISE.setRegistryName("crystal_tortoise"));
		event.getRegistry().register(MARMOT.setRegistryName("marmot"));
		event.getRegistry().register(SNOWPILE_QUAIL.setRegistryName("snowpile_quail"));
		event.getRegistry().register(FROST_WOLF.setRegistryName("frost_wolf"));
		event.getRegistry().register(CRYSTAL_FOX.setRegistryName("crystal_fox"));

		event.getRegistry().register(YETI.setRegistryName("yeti"));
		event.getRegistry().register(FROST_WRAITH.setRegistryName("frost_wraith"));
		event.getRegistry().register(GOKKUR.setRegistryName("gokkur"));
		event.getRegistry().register(GOKKUDILLO.setRegistryName("gokkudillo"));

		event.getRegistry().register(FROST_BEASTER.setRegistryName("frost_beaster"));

		SpawnPlacements.register(CRYSTAL_TORTOISE, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CrystalTortoise::checkTortoiseSpawnRules);
		SpawnPlacements.register(MARMOT, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Marmot::checkMarmotSpawnRules);
		SpawnPlacements.register(SNOWPILE_QUAIL, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SnowPileQuail::checkQuailSpawnRules);
		SpawnPlacements.register(FROST_WOLF, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FrostWolf::checkFrostWolfSpawnRules);
		SpawnPlacements.register(CRYSTAL_FOX, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CrystalFox::checkCrystalFoxSpawnRules);

		SpawnPlacements.register(YETI, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
		SpawnPlacements.register(FROST_WRAITH, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(GOKKUR, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Gokkur::checkGokkurSpawnRules);
		SpawnPlacements.register(GOKKUDILLO, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Gokkudillo::checkGokkudilloSpawnRules);
		SpawnPlacements.register(FROST_BEASTER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FrostBeaster::checkFrostBeasterSpawnRules);
	}

	@SubscribeEvent
	public static void registerEntityAttribute(EntityAttributeCreationEvent event) {
		event.put(CRYSTAL_TORTOISE, CrystalTortoise.createAttributes().build());
		event.put(MARMOT, Marmot.createAttributes().build());
		event.put(SNOWPILE_QUAIL, SnowPileQuail.createAttributes().build());
		event.put(FROST_WOLF, FrostWolf.createAttributes().build());
		event.put(CRYSTAL_FOX, CrystalFox.createAttributes().build());

		event.put(YETI, Yeti.createAttributeMap().build());
		event.put(FROST_WRAITH, FrostWraith.createAttributes().build());
		event.put(GOKKUR, Gokkur.createAttributes().build());
		event.put(GOKKUDILLO, Gokkudillo.createAttributes().build());
		event.put(FROST_BEASTER, FrostBeaster.createAttributes().build());
	}
}