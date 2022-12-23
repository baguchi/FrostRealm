package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;

public class FrostTags {
	public static class Blocks {
		public static final TagKey<Block> BASE_STONE_FROSTREALM = tag("base_stone_frostrealm");
		public static final TagKey<Block> TUNDRA_REPLACEABLE = tag("tundra_replaceable");
		public static final TagKey<Block> HOT_SOURCE = tag("hot_source");
		public static final TagKey<Block> WORLD_CARVER_REPLACEABLE = tag("world_replaceable");

		private static TagKey<Block> tag(String name) {
			return BlockTags.create(new ResourceLocation(FrostRealm.MODID, name));
		}
	}

	public static class EntityTypes {
		public static final TagKey<EntityType<?>> COLD_WEATHER_IMMUNE = tag("cold_weather_immune");

		private static TagKey<EntityType<?>> tag(String p_203849_) {
			return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(FrostRealm.MODID, p_203849_));
		}
	}
}
