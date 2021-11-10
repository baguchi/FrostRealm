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
				, FrostBlocks.FRIGID_STONE_SMOOTH_BRICK.get(), FrostBlocks.FRIGID_STONE_BRICK.get(), FrostBlocks.FRIGID_STONE_BRICK_STAIRS.get(), FrostBlocks.FRIGID_STONE_BRICK_SLAB.get()
				, FrostBlocks.POINTED_ICE.get());
		tag(BlockTags.MINEABLE_WITH_AXE).add(FrostBlocks.FROSTROOT_PLANKS.get(), FrostBlocks.FROSTROOT_PLANKS_STAIRS.get(), FrostBlocks.FROSTROOT_PLANKS_SLAB.get(), FrostBlocks.FROSTROOT_FENCE.get(), FrostBlocks.FROSTROOT_FENCE_GATE.get());

		tag(BlockTags.MINEABLE_WITH_HOE).add(FrostBlocks.FROSTROOT_LEAVES.get()).add(FrostBlocks.FROSTROOT_SAPLING.get());

		tag(BlockTags.NEEDS_STONE_TOOL)
				.add(FrostBlocks.STARDUST_CRYSTAL_ORE.get(), FrostBlocks.GLIMMERROCK_ORE.get());

		tag(FrostTags.Blocks.BASE_STONE_FROSTREALM).add(FrostBlocks.FRIGID_STONE.get());
		tag(FrostTags.Blocks.HOT_SOURCE).addTag(BlockTags.CAMPFIRES).addTag(BlockTags.FIRE).add(Blocks.MAGMA_BLOCK);
		tag(FrostTags.Blocks.TUNDRA_REPLACEABLE).add(FrostBlocks.FRIGID_STONE.get(), FrostBlocks.FROZEN_DIRT.get());

		tag(Tags.Blocks.DIRT).add(FrostBlocks.FROZEN_DIRT.get());
		tag(Tags.Blocks.ORES).add(FrostBlocks.STARDUST_CRYSTAL_ORE.get(), FrostBlocks.GLIMMERROCK_ORE.get(), FrostBlocks.FROST_CRYSTAL_ORE.get());
	}
}