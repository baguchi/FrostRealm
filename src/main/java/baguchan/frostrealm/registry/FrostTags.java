package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class FrostTags {
    public static class Biomes {
		public static final TagKey<Biome> HOT_BIOME = tag("hot_biome");

        private static TagKey<Biome> tag(String name) {
			return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, name));
        }
    }
	public static class Blocks {
		public static final TagKey<Block> BASE_STONE_FROSTREALM = tag("base_stone_frostrealm");
		public static final TagKey<Block> TUNDRA_REPLACEABLE = tag("tundra_replaceable");
		public static final TagKey<Block> HOT_SOURCE = tag("hot_source");
		public static final TagKey<Block> WORLD_CARVER_REPLACEABLE = tag("world_replaceable");
        public static final TagKey<Block> NON_FREEZE_CROP = tag("non_freeze_crop");
		public static final TagKey<Block> SEAL_SPAWNABLE = tag("seal_spawnable");
		public static final TagKey<Block> ANIMAL_SPAWNABLE = tag("frost_animal_spawnable");

		private static TagKey<Block> tag(String name) {
			return BlockTags.create(ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, name));
		}
	}

	public static class Items {
        public static final TagKey<Item> AURORA_FUELS = tag("aurora_fuels");
        public static final TagKey<Item> YETI_LOVED = tag("yeti_loved");
        public static final TagKey<Item> YETI_CURRENCY = tag("yeti_currency");
        public static final TagKey<Item> YETI_SCARED = tag("yeti_scared");
        public static final TagKey<Item> YETI_BIG_CURRENCY = tag("yeti_big_currency");
		public static final TagKey<Item> SICKLE = tag("sickle");

		public static final TagKey<Item> WOLFFLUE_FOODS = tag("wolfflue_foods");
		public static final TagKey<Item> SNOWPILE_FOODS = tag("snowpile_foods");
		public static final TagKey<Item> FROST_BOAR_FOODS = tag("frost_boar_foods");
		public static final TagKey<Item> CRYSTAL_FOX_FOODS = tag("crystal_fox_foods");


		private static TagKey<Item> tag(String p_203849_) {
			return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, p_203849_));
        }
    }

	public static class EntityTypes {
		public static final TagKey<EntityType<?>> COLD_WEATHER_IMMUNE = tag("cold_weather_immune");

		private static TagKey<EntityType<?>> tag(String p_203849_) {
			return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, p_203849_));
		}
	}
}
