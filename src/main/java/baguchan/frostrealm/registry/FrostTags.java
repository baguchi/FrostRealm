package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class FrostTags {
	public static class Blocks {
		public static final TagKey<Block> BASE_STONE_FROSTREALM = tag("base_stone_frostrealm");
		public static final TagKey<Block> TUNDRA_REPLACEABLE = tag("tundra_replaceable");
		public static final TagKey<Block> HOT_SOURCE = tag("hot_source");
		public static final TagKey<Block> WORLD_CARVER_REPLACEABLE = tag("world_replaceable");
        public static final TagKey<Block> NON_FREEZE_CROP = tag("non_freeze_crop");
		public static final TagKey<Block> SEAL_SPAWNABLE = tag("seal_spawnable");

		private static TagKey<Block> tag(String name) {
			return BlockTags.create(new ResourceLocation(FrostRealm.MODID, name));
		}
	}

	public static class Items {
        public static final TagKey<Item> AURORA_FUELS = tag("aurora_fuels");
        public static final TagKey<Item> YETI_LOVED = tag("yeti_loved");
        public static final TagKey<Item> YETI_CURRENCY = tag("yeti_currency");
        public static final TagKey<Item> YETI_SCARED = tag("yeti_scared");
        public static final TagKey<Item> YETI_BIG_CURRENCY = tag("yeti_big_currency");

        private static TagKey<Item> tag(String p_203849_) {
            return TagKey.create(Registries.ITEM, new ResourceLocation(FrostRealm.MODID, p_203849_));
        }
    }

	public static class EntityTypes {
		public static final TagKey<EntityType<?>> COLD_WEATHER_IMMUNE = tag("cold_weather_immune");

		private static TagKey<EntityType<?>> tag(String p_203849_) {
			return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(FrostRealm.MODID, p_203849_));
		}
	}
}
