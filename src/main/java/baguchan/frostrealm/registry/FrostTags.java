package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class FrostTags {
	public static class Blocks {
		public static final TagKey<Block> BASE_STONE_FROSTREALM = tag("base_stone_frostrealm");
		public static final TagKey<Block> TUNDRA_REPLACEABLE = tag("tundra_replaceable");
		public static final TagKey<Block> HOT_SOURCE = tag("hot_source");

		private static TagKey<Block> tag(String name) {
			return BlockTags.create(new ResourceLocation(FrostRealm.MODID, name));
		}
	}
}
