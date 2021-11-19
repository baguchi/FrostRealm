package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.entity.FrostWraith;
import baguchan.frostrealm.entity.Yeti;
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
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = FrostRealm.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FrostEntities {
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, FrostRealm.MODID);


	public static final EntityType<Yeti> YETI_TYPE = EntityType.Builder.of(Yeti::new, MobCategory.CREATURE).sized(1.6F, 1.95F).build(prefix("yeti"));
	public static final RegistryObject<EntityType<Yeti>> YETI = ENTITIES.register("yeti", () -> YETI_TYPE);
	public static final EntityType<FrostWraith> FROST_WRAITH_TYPE = EntityType.Builder.of(FrostWraith::new, MobCategory.MONSTER).sized(0.6F, 1.85F).build(prefix("frost_wraith"));
	public static final RegistryObject<EntityType<FrostWraith>> FROST_WRAITH = ENTITIES.register("frost_wraith", () -> FROST_WRAITH_TYPE);


	private static String prefix(String path) {
		return FrostRealm.MODID + "." + path;
	}

	@SubscribeEvent
	public static void registerEntity(RegistryEvent.Register<EntityType<?>> event) {
		SpawnPlacements.register(YETI.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
		SpawnPlacements.register(FROST_WRAITH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
	}

	@SubscribeEvent
	public static void registerEntityAttribute(EntityAttributeCreationEvent event) {
		event.put(YETI.get(), Yeti.createAttributeMap().build());
		event.put(FROST_WRAITH.get(), FrostWraith.createAttributes().build());
	}
}