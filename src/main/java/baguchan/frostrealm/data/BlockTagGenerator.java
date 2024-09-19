package baguchan.frostrealm.data;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class BlockTagGenerator extends BlockTagsProvider {
	public BlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper exFileHelper) {
		super(output, lookupProvider, FrostRealm.MODID, exFileHelper);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(FrostBlocks.FROZEN_DIRT.get(), FrostBlocks.FROZEN_GRASS_BLOCK.get())
                .add(FrostBlocks.SHERBET_SAND.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(FrostBlocks.FRIGID_STONE.get(), FrostBlocks.FRIGID_STONE_SLAB.get(), FrostBlocks.FRIGID_STONE_STAIRS.get()
                , FrostBlocks.FRIGID_STONE_MOSSY.get(), FrostBlocks.FRIGID_STONE_MOSSY_SLAB.get(), FrostBlocks.FRIGID_STONE_MOSSY_STAIRS.get()
                , FrostBlocks.FRIGID_STONE_BRICK_MOSSY.get(), FrostBlocks.FRIGID_STONE_BRICK_MOSSY_SLAB.get(), FrostBlocks.FRIGID_STONE_BRICK_MOSSY_STAIRS.get()
                , FrostBlocks.FRIGID_STONE_SMOOTH.get(), FrostBlocks.FRIGID_STONE_BRICK.get(), FrostBlocks.FRIGID_STONE_BRICK_STAIRS.get(), FrostBlocks.FRIGID_STONE_BRICK_SLAB.get(), FrostBlocks.CHISELED_FRIGID_STONE_BRICK.get()
                , FrostBlocks.PERMA_SLATE_SMOOTH.get(), FrostBlocks.PERMA_SLATE_BRICK.get(), FrostBlocks.PERMA_SLATE_BRICK_STAIRS.get(), FrostBlocks.PERMA_SLATE_BRICK_SLAB.get()
                , FrostBlocks.SHERBET_SANDSTONE.get(), FrostBlocks.SHERBET_SANDSTONE_STAIRS.get(), FrostBlocks.SHERBET_SANDSTONE_SLAB.get()
                , FrostBlocks.POINTED_ICE.get()
                , FrostBlocks.FROST_CRYSTAL_BLOCK.get()
                , FrostBlocks.FROST_CRYSTAL_ORE.get(), FrostBlocks.GLIMMERROCK_ORE.get(), FrostBlocks.ASTRIUM_ORE.get(), FrostBlocks.ASTRIUM_BLOCK.get(), FrostBlocks.RAW_ASTRIUM_BLOCK.get(), FrostBlocks.STARDUST_CRYSTAL_ORE.get(), FrostBlocks.STARDUST_CRYSTAL_CLUSTER.get()
                , FrostBlocks.FROST_CRYSTAL_SLATE_ORE.get(), FrostBlocks.GLIMMERROCK_SLATE_ORE.get(), FrostBlocks.ASTRIUM_SLATE_ORE.get()
                , FrostBlocks.WARPED_CRYSTAL_BLOCK.get()
                , FrostBlocks.AURORA_INFUSER.get()
                , FrostBlocks.FRIGID_GRASS_BLOCK.get()
                , FrostBlocks.PERMA_SLATE.get()
                , FrostBlocks.GLACINIUM_ORE.get()
                , FrostBlocks.GLACINIUM_BLOCK.get()
        );
        tag(BlockTags.MINEABLE_WITH_AXE).add(FrostBlocks.FROSTROOT_LOG.get(), FrostBlocks.STRIPPED_FROSTROOT_LOG.get(), FrostBlocks.FROSTROOT_PLANKS.get(), FrostBlocks.FROSTROOT_PLANKS_STAIRS.get(), FrostBlocks.FROSTROOT_PLANKS_SLAB.get(), FrostBlocks.FROSTROOT_FENCE.get(), FrostBlocks.FROSTROOT_FENCE_GATE.get()
                        , FrostBlocks.FROSTROOT_CHEST.get(), FrostBlocks.FROSTROOT_CRAFTING_TABLE.get())
        ;

        tag(BlockTags.MINEABLE_WITH_HOE).add(FrostBlocks.FROSTROOT_LEAVES.get()).add(FrostBlocks.FROSTROOT_SAPLING.get());

        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(FrostBlocks.STARDUST_CRYSTAL_ORE.get(), FrostBlocks.GLIMMERROCK_BLOCK.get(), FrostBlocks.GLIMMERROCK_ORE.get(), FrostBlocks.ASTRIUM_ORE.get(), FrostBlocks.GLIMMERROCK_SLATE_ORE.get(), FrostBlocks.ASTRIUM_SLATE_ORE.get(), FrostBlocks.ASTRIUM_BLOCK.get(), FrostBlocks.RAW_ASTRIUM_BLOCK.get());

        tag(BlockTags.NEEDS_IRON_TOOL).add(FrostBlocks.GLACINIUM_ORE.get(), FrostBlocks.GLACINIUM_BLOCK.get());

        tag(BlockTags.LOGS_THAT_BURN).add(FrostBlocks.FROSTROOT_LOG.get(), FrostBlocks.STRIPPED_FROSTROOT_LOG.get());
        tag(BlockTags.LEAVES).add(FrostBlocks.FROSTROOT_LEAVES.get());
        tag(BlockTags.WOODEN_FENCES).add(FrostBlocks.FROSTROOT_FENCE.get());
        tag(BlockTags.FENCE_GATES).add(FrostBlocks.FROSTROOT_FENCE_GATE.get());
        tag(BlockTags.WOODEN_DOORS).add(FrostBlocks.FROSTROOT_DOOR.get());
        tag(BlockTags.WOODEN_SLABS).add(FrostBlocks.FROSTROOT_PLANKS_SLAB.get());
        tag(Tags.Blocks.CHESTS_WOODEN).add(FrostBlocks.FROSTROOT_CHEST.get());

        tag(BlockTags.CROPS).add(FrostBlocks.SUGARBEET.get(), FrostBlocks.RYE.get());
        tag(BlockTags.MAINTAINS_FARMLAND).add(FrostBlocks.SUGARBEET.get(), FrostBlocks.RYE.get());
        tag(BlockTags.BEE_GROWABLES).add(FrostBlocks.SUGARBEET.get(), FrostBlocks.RYE.get());

        tag(FrostTags.Blocks.BASE_STONE_FROSTREALM).add(FrostBlocks.FRIGID_STONE.get(), FrostBlocks.PERMA_SLATE.get());
        tag(FrostTags.Blocks.HOT_SOURCE).addTag(BlockTags.CAMPFIRES).addTag(BlockTags.FIRE).add(Blocks.MAGMA_BLOCK)
                .add(Blocks.FURNACE, Blocks.BLAST_FURNACE, Blocks.SMOKER).add(FrostBlocks.HOT_SPRING.get());
		tag(BlockTags.CAMPFIRES).add(FrostBlocks.FROST_CAMPFIRE.get());
        tag(FrostTags.Blocks.TUNDRA_REPLACEABLE).add(FrostBlocks.FRIGID_STONE.get(), FrostBlocks.FROZEN_DIRT.get());
        tag(FrostTags.Blocks.WORLD_CARVER_REPLACEABLE).add(FrostBlocks.FRIGID_STONE.get(), FrostBlocks.PERMA_SLATE.get(), FrostBlocks.FROZEN_DIRT.get(), FrostBlocks.FROZEN_GRASS_BLOCK.get(), Blocks.ICE, Blocks.BLUE_ICE, Blocks.PACKED_ICE);

        tag(FrostTags.Blocks.ANIMAL_SPAWNABLE).add(FrostBlocks.FROZEN_GRASS_BLOCK.get(), FrostBlocks.FRIGID_GRASS_BLOCK.get());
        tag(BlockTags.DIRT).add(FrostBlocks.FROZEN_DIRT.get(), FrostBlocks.FROZEN_GRASS_BLOCK.get());
        tag(BlockTags.DIRT).add(FrostBlocks.FRIGID_GRASS_BLOCK.get());
        tag(Tags.Blocks.ORES).add(FrostBlocks.ASTRIUM_ORE.get(), FrostBlocks.STARDUST_CRYSTAL_ORE.get(), FrostBlocks.GLIMMERROCK_ORE.get(), FrostBlocks.FROST_CRYSTAL_ORE.get())
                .add(FrostBlocks.ASTRIUM_SLATE_ORE.get(), FrostBlocks.GLIMMERROCK_SLATE_ORE.get(), FrostBlocks.FROST_CRYSTAL_SLATE_ORE.get());
        tag(FrostTags.Blocks.NON_FREEZE_CROP).add(FrostBlocks.SUGARBEET.get(), FrostBlocks.RYE.get());
        tag(FrostTags.Blocks.SEAL_SPAWNABLE).add(FrostBlocks.FRIGID_STONE.get(), Blocks.SNOW_BLOCK, Blocks.ICE, Blocks.BLUE_ICE, Blocks.PACKED_ICE);
        tag(BlockTags.SMALL_FLOWERS).add(FrostBlocks.ARCTIC_POPPY.get(), FrostBlocks.ARCTIC_WILLOW.get());
	}
}