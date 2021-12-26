package baguchan.frostrealm.data;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockTagGenerator extends BlockTagsProvider {
	public BlockTagGenerator(DataGenerator generator, ExistingFileHelper exFileHelper) {
		super(generator, FrostRealm.MODID, exFileHelper);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void addTags() {
		tag(BlockTags.MINEABLE_WITH_SHOVEL).add(FrostBlocks.FROZEN_DIRT, FrostBlocks.FROZEN_GRASS_BLOCK);
		tag(BlockTags.MINEABLE_WITH_PICKAXE).add(FrostBlocks.FRIGID_STONE, FrostBlocks.FRIGID_STONE_SLAB, FrostBlocks.FRIGID_STONE_STAIRS
				, FrostBlocks.FRIGID_STONE_MOSSY, FrostBlocks.FRIGID_STONE_MOSSY_SLAB, FrostBlocks.FRIGID_STONE_MOSSY_STAIRS
				, FrostBlocks.FRIGID_STONE_BRICK_MOSSY, FrostBlocks.FRIGID_STONE_BRICK_MOSSY_SLAB, FrostBlocks.FRIGID_STONE_BRICK_MOSSY_STAIRS
				, FrostBlocks.FRIGID_STONE_SMOOTH_BRICK, FrostBlocks.FRIGID_STONE_BRICK, FrostBlocks.FRIGID_STONE_BRICK_STAIRS, FrostBlocks.FRIGID_STONE_BRICK_SLAB
				, FrostBlocks.POINTED_ICE
				, FrostBlocks.FROST_CRYSTAL_ORE, FrostBlocks.GLIMMERROCK_ORE, FrostBlocks.STARDUST_CRYSTAL_ORE, FrostBlocks.STARDUST_CRYSTAL_CLUSTER
				, FrostBlocks.FRIGID_STOVE);
		tag(BlockTags.MINEABLE_WITH_AXE).add(FrostBlocks.FROSTROOT_LOG, FrostBlocks.FROSTROOT_PLANKS, FrostBlocks.FROSTROOT_PLANKS_STAIRS, FrostBlocks.FROSTROOT_PLANKS_SLAB, FrostBlocks.FROSTROOT_FENCE, FrostBlocks.FROSTROOT_FENCE_GATE
				, FrostBlocks.FROSTROOT_CHEST);

		tag(BlockTags.MINEABLE_WITH_HOE).add(FrostBlocks.FROSTROOT_LEAVES).add(FrostBlocks.FROSTROOT_SAPLING);

		tag(BlockTags.NEEDS_STONE_TOOL)
				.add(FrostBlocks.STARDUST_CRYSTAL_ORE, FrostBlocks.GLIMMERROCK_ORE);

		tag(BlockTags.LOGS_THAT_BURN).add(FrostBlocks.FROSTROOT_LOG);
		tag(BlockTags.LEAVES).add(FrostBlocks.FROSTROOT_LEAVES);
		tag(BlockTags.WOODEN_FENCES).add(FrostBlocks.FROSTROOT_FENCE);
		tag(BlockTags.FENCE_GATES).add(FrostBlocks.FROSTROOT_FENCE_GATE);
		tag(BlockTags.WOODEN_DOORS).add(FrostBlocks.FROSTROOT_DOOR);
		tag(Tags.Blocks.CHESTS_WOODEN).add(FrostBlocks.FROSTROOT_CHEST);

		tag(BlockTags.CROPS).add(FrostBlocks.SUGARBEET);
		tag(BlockTags.BEE_GROWABLES).add(FrostBlocks.SUGARBEET);

		tag(FrostTags.Blocks.BASE_STONE_FROSTREALM).add(FrostBlocks.FRIGID_STONE);
		tag(FrostTags.Blocks.HOT_SOURCE).addTag(BlockTags.CAMPFIRES).addTag(BlockTags.FIRE).add(Blocks.MAGMA_BLOCK).add(FrostBlocks.HOT_AIR)
				.add(Blocks.FURNACE, Blocks.BLAST_FURNACE, Blocks.SMOKER, FrostBlocks.FRIGID_STOVE);
		tag(FrostTags.Blocks.TUNDRA_REPLACEABLE).add(FrostBlocks.FRIGID_STONE, FrostBlocks.FROZEN_DIRT);


		tag(Tags.Blocks.DIRT).add(FrostBlocks.FROZEN_DIRT, FrostBlocks.FROZEN_GRASS_BLOCK);
		tag(Tags.Blocks.ORES).add(FrostBlocks.STARDUST_CRYSTAL_ORE, FrostBlocks.GLIMMERROCK_ORE, FrostBlocks.FROST_CRYSTAL_ORE);
	}
}