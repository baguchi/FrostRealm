package baguchan.frostrealm.data.generator;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.registry.FrostBlocks;
import baguchan.frostrealm.registry.FrostTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class FrBlockTagGenerator extends BlockTagsProvider {
    public FrBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, FrostRealm.MODID);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(FrostBlocks.FROZEN_DIRT.get(), FrostBlocks.FROZEN_GRASS_BLOCK.get())
                .add(FrostBlocks.SHERBET_SAND.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(FrostBlocks.FRIGID_STONE.get(), FrostBlocks.FRIGID_STONE_SLAB.get(), FrostBlocks.FRIGID_STONE_STAIRS.get()
                , FrostBlocks.FRIGID_STONE_MOSSY.get(), FrostBlocks.FRIGID_STONE_MOSSY_SLAB.get(), FrostBlocks.FRIGID_STONE_MOSSY_STAIRS.get()
                , FrostBlocks.FRIGID_STONE_BRICK_MOSSY.get(), FrostBlocks.FRIGID_STONE_BRICK_MOSSY_SLAB.get(), FrostBlocks.FRIGID_STONE_BRICK_MOSSY_STAIRS.get()
                , FrostBlocks.FRIGID_STONE_SMOOTH.get(), FrostBlocks.FRIGID_STONE_BRICK.get(), FrostBlocks.FRIGID_STONE_BRICK_STAIRS.get(), FrostBlocks.FRIGID_STONE_BRICK_SLAB.get(), FrostBlocks.FRIGID_STONE_BRICK_WALL.get(), FrostBlocks.CHISELED_FRIGID_STONE_BRICK.get()
                , FrostBlocks.PERMA_SLATE_SMOOTH.get(), FrostBlocks.PERMA_SLATE_BRICK.get(), FrostBlocks.PERMA_SLATE_BRICK_STAIRS.get(), FrostBlocks.PERMA_SLATE_BRICK_SLAB.get(), FrostBlocks.PERMA_SLATE_BRICK_WALL.get()
                , FrostBlocks.PERMA_MAGMA.get()
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
                , FrostBlocks.RAW_GLACINIUM_BLOCK.get()
                , FrostBlocks.SILK_MOON_EGG.get()
                , FrostBlocks.SNOWPILE_QUAIL_EGG.get()
                )
                .add(FrostBlocks.MAGMA_CORE.get())
                .add(FrostBlocks.ROCK_WOOD.get(), FrostBlocks.ROCK_WOOD_PLANKS.get(), FrostBlocks.ROCK_WOOD_PLANKS_STAIRS.get(), FrostBlocks.ROCK_WOOD_PLANKS_SLAB.get(), FrostBlocks.ROCK_WOOD_FENCE.get(), FrostBlocks.ROCK_WOOD_FENCE_GATE.get())
                .add(FrostBlocks.ROCK_WOOD_DOOR.get()).add(FrostBlocks.ROCK_WOOD_TRAPDOOR.get()).add(FrostBlocks.ROCK_WOOD_PRESSURE_PLATE.get()).add(FrostBlocks.ROCK_WOOD_BUTTON.get())
        ;
        tag(BlockTags.MINEABLE_WITH_AXE).add(FrostBlocks.FROSTROOT_LOG.get(), FrostBlocks.STRIPPED_FROSTROOT_LOG.get(), FrostBlocks.FROSTROOT_PLANKS.get(), FrostBlocks.FROSTROOT_PLANKS_STAIRS.get(), FrostBlocks.FROSTROOT_PLANKS_SLAB.get(), FrostBlocks.FROSTROOT_FENCE.get(), FrostBlocks.FROSTROOT_FENCE_GATE.get()
                )
                .add(FrostBlocks.FROSTROOT_DOOR.get()).add(FrostBlocks.FROSTROOT_TRAPDOOR.get()).add(FrostBlocks.FROSTROOT_PRESSURE_PLATE.get()).add(FrostBlocks.FROSTROOT_BUTTON.get())
                .add(FrostBlocks.FROSTBITE_LOG.get(), FrostBlocks.STRIPPED_FROSTBITE_LOG.get(), FrostBlocks.FROSTBITE_PLANKS.get(), FrostBlocks.FROSTBITE_PLANKS_STAIRS.get(), FrostBlocks.FROSTBITE_PLANKS_SLAB.get(), FrostBlocks.FROSTBITE_FENCE.get(), FrostBlocks.FROSTBITE_FENCE_GATE.get())
                .add(FrostBlocks.FROSTBITE_DOOR.get()).add(FrostBlocks.FROSTBITE_TRAPDOOR.get()).add(FrostBlocks.FROSTBITE_PRESSURE_PLATE.get()).add(FrostBlocks.FROSTBITE_BUTTON.get())
                .add(FrostBlocks.ROCK_WOOD.get(), FrostBlocks.ROCK_WOOD_PLANKS.get(), FrostBlocks.ROCK_WOOD_PLANKS_STAIRS.get(), FrostBlocks.ROCK_WOOD_PLANKS_SLAB.get(), FrostBlocks.ROCK_WOOD_FENCE.get(), FrostBlocks.ROCK_WOOD_FENCE_GATE.get())
                .add(FrostBlocks.ROCK_WOOD_DOOR.get()).add(FrostBlocks.ROCK_WOOD_TRAPDOOR.get()).add(FrostBlocks.ROCK_WOOD_PRESSURE_PLATE.get()).add(FrostBlocks.ROCK_WOOD_BUTTON.get())
                .add(FrostBlocks.DRIP_LOG.get(), FrostBlocks.DRIP_PLANKS.get(), FrostBlocks.DRIP_PLANKS_STAIRS.get(), FrostBlocks.DRIP_PLANKS_SLAB.get(), FrostBlocks.DRIP_FENCE.get(), FrostBlocks.DRIP_FENCE_GATE.get())
                .add(FrostBlocks.DRIP_DOOR.get()).add(FrostBlocks.DRIP_TRAPDOOR.get()).add(FrostBlocks.DRIP_PRESSURE_PLATE.get()).add(FrostBlocks.DRIP_BUTTON.get());


        tag(BlockTags.MINEABLE_WITH_HOE).add(FrostBlocks.FROSTROOT_LEAVES.get()).add(FrostBlocks.FROSTROOT_SAPLING.get()).add(FrostBlocks.DRIP_LEAVES.get()).add(FrostBlocks.DRIP_HANGING_LEAVES.get()).add(FrostBlocks.DRIP_SAPLING.get())
                .add(FrostBlocks.SILK_MOON_COCOON.get()).add(FrostBlocks.FROSTBITE_LEAVES.get()).add(FrostBlocks.FROSTBITE_SAPLING.get()).add(FrostBlocks.RYE_BLOCK.get());

        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(FrostBlocks.STARDUST_CRYSTAL_ORE.get(), FrostBlocks.GLIMMERROCK_BLOCK.get(), FrostBlocks.GLIMMERROCK_ORE.get(), FrostBlocks.ASTRIUM_ORE.get(), FrostBlocks.GLIMMERROCK_SLATE_ORE.get(), FrostBlocks.ASTRIUM_SLATE_ORE.get(), FrostBlocks.ASTRIUM_BLOCK.get(), FrostBlocks.RAW_ASTRIUM_BLOCK.get());

        tag(BlockTags.NEEDS_IRON_TOOL).add(FrostBlocks.GLACINIUM_ORE.get(), FrostBlocks.GLACINIUM_BLOCK.get(), FrostBlocks.RAW_GLACINIUM_BLOCK.get());

        tag(BlockTags.LOGS_THAT_BURN).add(FrostBlocks.FROSTROOT_LOG.get(), FrostBlocks.STRIPPED_FROSTROOT_LOG.get()).add(FrostBlocks.FROSTBITE_LOG.get(), FrostBlocks.STRIPPED_FROSTBITE_LOG.get())
                .add(FrostBlocks.DRIP_LOG.get());
        tag(BlockTags.LEAVES).add(FrostBlocks.FROSTROOT_LEAVES.get()).add(FrostBlocks.FROSTBITE_LEAVES.get()).add(FrostBlocks.DRIP_LEAVES.get());
        tag(BlockTags.WOODEN_FENCES).add(FrostBlocks.FROSTROOT_FENCE.get()).add(FrostBlocks.FROSTBITE_FENCE.get()).add(FrostBlocks.DRIP_LEAVES.get());
        tag(BlockTags.FENCE_GATES).add(FrostBlocks.ROCK_WOOD_FENCE_GATE.get()).add(FrostBlocks.FROSTROOT_FENCE_GATE.get()).add(FrostBlocks.FROSTBITE_FENCE_GATE.get()).add(FrostBlocks.DRIP_FENCE_GATE.get());
        tag(BlockTags.WOODEN_DOORS).add(FrostBlocks.FROSTROOT_DOOR.get()).add(FrostBlocks.FROSTBITE_DOOR.get()).add(FrostBlocks.DRIP_DOOR.get());
        tag(BlockTags.WOODEN_TRAPDOORS).add(FrostBlocks.FROSTROOT_TRAPDOOR.get()).add(FrostBlocks.FROSTBITE_TRAPDOOR.get()).add(FrostBlocks.DRIP_TRAPDOOR.get());
        tag(BlockTags.WOODEN_SLABS).add(FrostBlocks.FROSTROOT_PLANKS_SLAB.get()).add(FrostBlocks.FROSTBITE_PLANKS_SLAB.get()).add(FrostBlocks.DRIP_PLANKS_SLAB.get());
        tag(BlockTags.WOODEN_STAIRS).add(FrostBlocks.FROSTROOT_PLANKS_STAIRS.get()).add(FrostBlocks.FROSTBITE_PLANKS_STAIRS.get()).add(FrostBlocks.DRIP_PLANKS_STAIRS.get());
        tag(BlockTags.WOODEN_PRESSURE_PLATES).add(FrostBlocks.FROSTROOT_PRESSURE_PLATE.get()).add(FrostBlocks.FROSTBITE_PRESSURE_PLATE.get()).add(FrostBlocks.DRIP_PRESSURE_PLATE.get());
        tag(BlockTags.WOODEN_BUTTONS).add(FrostBlocks.FROSTROOT_BUTTON.get()).add(FrostBlocks.FROSTBITE_BUTTON.get()).add(FrostBlocks.DRIP_BUTTON.get());
        tag(BlockTags.WALLS).add(FrostBlocks.FRIGID_STONE_BRICK_WALL.get(), FrostBlocks.PERMA_SLATE_BRICK_WALL.get());

        tag(BlockTags.FENCES).add(FrostBlocks.ROCK_WOOD_FENCE.get());
        tag(BlockTags.DOORS).add(FrostBlocks.ROCK_WOOD_DOOR.get());
        tag(BlockTags.TRAPDOORS).add(FrostBlocks.ROCK_WOOD_TRAPDOOR.get());
        tag(BlockTags.SLABS).add(FrostBlocks.ROCK_WOOD_PLANKS_SLAB.get());
        tag(BlockTags.STAIRS).add(FrostBlocks.ROCK_WOOD_PLANKS_STAIRS.get());
        tag(BlockTags.PRESSURE_PLATES).add(FrostBlocks.ROCK_WOOD_PRESSURE_PLATE.get());
        tag(BlockTags.BUTTONS).add(FrostBlocks.ROCK_WOOD_BUTTON.get());

        tag(BlockTags.CROPS).add(FrostBlocks.SUGARBEET.get(), FrostBlocks.RYE.get());
        tag(BlockTags.MAINTAINS_FARMLAND).add(FrostBlocks.SUGARBEET.get(), FrostBlocks.RYE.get());
        tag(BlockTags.BEE_GROWABLES).add(FrostBlocks.SUGARBEET.get(), FrostBlocks.RYE.get());

        tag(FrostTags.Blocks.BASE_STONE_FROSTREALM).add(FrostBlocks.FRIGID_STONE.get(), FrostBlocks.PERMA_SLATE.get());
        tag(FrostTags.Blocks.HOT_SOURCE).addTag(BlockTags.CAMPFIRES).addTag(BlockTags.FIRE).add(Blocks.MAGMA_BLOCK)
                .add(Blocks.FURNACE, Blocks.BLAST_FURNACE, Blocks.SMOKER).add(FrostBlocks.HOT_SPRING.get());
		tag(BlockTags.CAMPFIRES).add(FrostBlocks.FROST_CAMPFIRE.get());
        tag(BlockTags.FIRE).add(FrostBlocks.FROST_FIRE.get());
        tag(FrostTags.Blocks.TUNDRA_REPLACEABLE).add(FrostBlocks.FRIGID_STONE.get(), FrostBlocks.FROZEN_DIRT.get());
        tag(FrostTags.Blocks.WORLD_CARVER_REPLACEABLE).add(FrostBlocks.FRIGID_STONE.get(), FrostBlocks.PERMA_SLATE.get(), FrostBlocks.FROZEN_DIRT.get(), FrostBlocks.FROZEN_GRASS_BLOCK.get(), Blocks.ICE, Blocks.BLUE_ICE, Blocks.PACKED_ICE, FrostBlocks.SHERBET_SAND.get(), FrostBlocks.SHERBET_SANDSTONE.get());

        tag(FrostTags.Blocks.ANIMAL_SPAWNABLE).add(FrostBlocks.FROZEN_GRASS_BLOCK.get(), FrostBlocks.FRIGID_GRASS_BLOCK.get());
        tag(BlockTags.DIRT).add(FrostBlocks.FROZEN_DIRT.get(), FrostBlocks.FROZEN_GRASS_BLOCK.get()).add(FrostBlocks.FRIGID_GRASS_BLOCK.get());
        this.tag(BlockTags.CLIMBABLE)
                .add(
                        FrostBlocks.DRIP_HANGING_LEAVES.get()
                );
        tag(Tags.Blocks.ORES).add(FrostBlocks.ASTRIUM_ORE.get(), FrostBlocks.STARDUST_CRYSTAL_ORE.get(), FrostBlocks.GLIMMERROCK_ORE.get(), FrostBlocks.FROST_CRYSTAL_ORE.get())
                .add(FrostBlocks.ASTRIUM_SLATE_ORE.get(), FrostBlocks.GLIMMERROCK_SLATE_ORE.get(), FrostBlocks.FROST_CRYSTAL_SLATE_ORE.get());
        tag(FrostTags.Blocks.NON_FREEZE_CROP).add(FrostBlocks.SUGARBEET.get(), FrostBlocks.RYE.get());
        tag(FrostTags.Blocks.NON_FREEZE_SAPLING).add(FrostBlocks.FROSTBITE_SAPLING.get()).add(FrostBlocks.FROSTROOT_SAPLING.get()).add(FrostBlocks.DRIP_SAPLING.get());
        tag(FrostTags.Blocks.SEAL_SPAWNABLE).add(FrostBlocks.FRIGID_STONE.get(), Blocks.SNOW_BLOCK, Blocks.ICE, Blocks.BLUE_ICE, Blocks.PACKED_ICE);
        tag(BlockTags.SMALL_FLOWERS).add(FrostBlocks.ARCTIC_POPPY.get(), FrostBlocks.ARCTIC_WILLOW.get());
	}
}