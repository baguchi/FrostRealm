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
		tag(BlockTags.MINEABLE_WITH_SHOVEL).add(FrostBlocks.FROZEN_DIRT.get(), FrostBlocks.FROZEN_GRASS_BLOCK.get());
		tag(BlockTags.MINEABLE_WITH_PICKAXE).add(FrostBlocks.FRIGID_STONE.get(), FrostBlocks.FRIGID_STONE_SLAB.get(), FrostBlocks.FRIGID_STONE_STAIRS.get()
				, FrostBlocks.FRIGID_STONE_MOSSY.get(), FrostBlocks.FRIGID_STONE_MOSSY_SLAB.get(), FrostBlocks.FRIGID_STONE_MOSSY_STAIRS.get()
				, FrostBlocks.FRIGID_STONE_BRICK_MOSSY.get(), FrostBlocks.FRIGID_STONE_BRICK_MOSSY_SLAB.get(), FrostBlocks.FRIGID_STONE_BRICK_MOSSY_STAIRS.get()
				, FrostBlocks.FRIGID_STONE_SMOOTH.get(), FrostBlocks.FRIGID_STONE_BRICK.get(), FrostBlocks.FRIGID_STONE_BRICK_STAIRS.get(), FrostBlocks.FRIGID_STONE_BRICK_SLAB.get()
				, FrostBlocks.POINTED_ICE.get()
				, FrostBlocks.FROST_CRYSTAL_ORE.get(), FrostBlocks.GLIMMERROCK_ORE.get(), FrostBlocks.ASTRIUM_ORE.get(), FrostBlocks.STARDUST_CRYSTAL_ORE.get(), FrostBlocks.STARDUST_CRYSTAL_CLUSTER.get(), FrostBlocks.CORRUPTED_CRYSTAL_CLUSTER.get()
				, FrostBlocks.WARPED_CRYSTAL_BLOCK.get()
				, FrostBlocks.FRIGID_STOVE.get());
		tag(BlockTags.MINEABLE_WITH_AXE).add(FrostBlocks.FROSTROOT_LOG.get(), FrostBlocks.FROSTROOT_PLANKS.get(), FrostBlocks.FROSTROOT_PLANKS_STAIRS.get(), FrostBlocks.FROSTROOT_PLANKS_SLAB.get(), FrostBlocks.FROSTROOT_FENCE.get(), FrostBlocks.FROSTROOT_FENCE_GATE.get()
				, FrostBlocks.FROZEN_LOG.get(), FrostBlocks.FROZEN_PLANKS.get(), FrostBlocks.FROZEN_PLANKS_STAIRS.get(), FrostBlocks.FROZEN_PLANKS_SLAB.get(), FrostBlocks.FROZEN_FENCE.get(), FrostBlocks.FROZEN_FENCE_GATE.get()
				, FrostBlocks.FROSTROOT_CHEST.get());

		tag(BlockTags.MINEABLE_WITH_HOE).add(FrostBlocks.FROSTROOT_LEAVES.get()).add(FrostBlocks.FROSTROOT_SAPLING.get());

		tag(BlockTags.NEEDS_STONE_TOOL)
				.add(FrostBlocks.STARDUST_CRYSTAL_ORE.get(), FrostBlocks.GLIMMERROCK_ORE.get(), FrostBlocks.ASTRIUM_ORE.get());

		tag(BlockTags.LOGS_THAT_BURN).add(FrostBlocks.FROSTROOT_LOG.get(), FrostBlocks.FROZEN_LOG.get());
		tag(BlockTags.LEAVES).add(FrostBlocks.FROSTROOT_LEAVES.get(), FrostBlocks.FROZEN_LEAVES.get());
		tag(BlockTags.WOODEN_FENCES).add(FrostBlocks.FROSTROOT_FENCE.get(), FrostBlocks.FROZEN_FENCE.get());
		tag(BlockTags.FENCE_GATES).add(FrostBlocks.FROSTROOT_FENCE_GATE.get(), FrostBlocks.FROZEN_FENCE_GATE.get());
		tag(BlockTags.WOODEN_DOORS).add(FrostBlocks.FROSTROOT_DOOR.get());
		tag(Tags.Blocks.CHESTS_WOODEN).add(FrostBlocks.FROSTROOT_CHEST.get());

		tag(BlockTags.CROPS).add(FrostBlocks.SUGARBEET.get());
		tag(BlockTags.BEE_GROWABLES).add(FrostBlocks.SUGARBEET.get());

		tag(FrostTags.Blocks.BASE_STONE_FROSTREALM).add(FrostBlocks.FRIGID_STONE.get());
		tag(FrostTags.Blocks.HOT_SOURCE).addTag(BlockTags.CAMPFIRES).addTag(BlockTags.FIRE).add(Blocks.MAGMA_BLOCK).add(FrostBlocks.HOT_AIR.get())
				.add(Blocks.FURNACE, Blocks.BLAST_FURNACE, Blocks.SMOKER, FrostBlocks.FRIGID_STOVE.get());
		tag(FrostTags.Blocks.TUNDRA_REPLACEABLE).add(FrostBlocks.FRIGID_STONE.get(), FrostBlocks.FROZEN_DIRT.get());


		tag(BlockTags.DIRT).add(FrostBlocks.FROZEN_DIRT.get(), FrostBlocks.FROZEN_GRASS_BLOCK.get());
		tag(Tags.Blocks.ORES).add(FrostBlocks.STARDUST_CRYSTAL_ORE.get(), FrostBlocks.GLIMMERROCK_ORE.get(), FrostBlocks.FROST_CRYSTAL_ORE.get());
	}
}