package baguchan.frostrealm.registry;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;

public class FrostTags {
	public static class Blocks {
		public static final Tag.Named<Block> BASE_STONE_FROSTREALM = tag("base_stone_frostrealm");
		public static final Tag.Named<Block> TUNDRA_REPLACEABLE = tag("tundra_replaceable");
		public static final Tag.Named<Block> HOT_SOURCE = tag("hot_source");

		private static Tag.Named<Block> tag(String name) {
			return BlockTags.bind("frostrealm:" + name);
		}
	}
}
