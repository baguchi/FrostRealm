package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.block.*;
import baguchan.frostrealm.block.crop.BearBerryBushBlock;
import baguchan.frostrealm.block.crop.RyeBlock;
import baguchan.frostrealm.block.crop.SugarBeetBlock;
import baguchan.frostrealm.item.block.DeferredDoubleHighBlockItem;
import baguchan.frostrealm.world.tree.FrostTrees;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;


public class FrostBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(FrostRealm.MODID);
    public static final DeferredBlock<LiquidBlock> HOT_SPRING = registerWithoutItem("hot_spring", (properties) -> new LiquidBlock(FrostFluids.HOT_SPRING.value(), properties), () -> BlockBehaviour.Properties.of().mapColor(MapColor.WATER).replaceable().noCollision().strength(100.0F).pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY));


    public static final DeferredBlock<FrostPortalBlock> FROST_PORTAL = registerWithoutItem("frostrealm_portal", (properties) -> new FrostPortalBlock(properties), () -> BlockBehaviour.Properties.of().noOcclusion().noCollision().randomTicks().lightLevel((state) -> {
        return 11;
    }).strength(-1.0F).sound(SoundType.GLASS));

    public static final DeferredBlock<Block> FROZEN_DIRT = register("frozen_dirt", (properties) -> new Block(properties), () -> BlockBehaviour.Properties.of().strength(0.5F).sound(SoundType.GRAVEL));
    public static final DeferredBlock<Block> FROZEN_GRASS_BLOCK = register("frozen_grass_block", (properties) -> new FrostGrassBlock(properties, FrostBlocks.FROZEN_DIRT), () -> BlockBehaviour.Properties.of().randomTicks().strength(0.6F).sound(SoundType.GRASS));
    public static final DeferredBlock<Block> FROZEN_FARMLAND = register("frozen_farmland", (properties) -> new FrozenFarmBlock(properties), () -> BlockBehaviour.Properties.of().noOcclusion().strength(0.5F).randomTicks().sound(SoundType.GRAVEL));

    public static final DeferredBlock<Block> POINTED_ICE = register("pointed_ice", (properties) -> new PointedIceBlock(properties), () -> BlockBehaviour.Properties.of().friction(0.98F).randomTicks().strength(0.5F).dynamicShape().offsetType(BlockBehaviour.OffsetType.XZ).sound(SoundType.GLASS));

    public static final DeferredBlock<Block> PERMA_SLATE = register("perma_slate", (properties) -> new Block(properties), () -> BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(1.75F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE));
    public static final DeferredBlock<Block> PERMA_SLATE_SMOOTH = register("perma_slate_smooth", (properties) -> new Block(properties), () -> BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(1.75F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE));
    public static final DeferredBlock<Block> PERMA_SLATE_BRICK = register("perma_slate_brick", (properties) -> new Block(properties), () -> BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(1.75F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE_BRICKS));
    public static final DeferredBlock<SlabBlock> PERMA_SLATE_BRICK_SLAB = register("perma_slate_brick_slab", (properties) -> new SlabBlock(properties), () -> BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(1.75F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE_BRICKS));
    public static final DeferredBlock<StairBlock> PERMA_SLATE_BRICK_STAIRS = register("perma_slate_brick_stairs", (properties) -> new StairBlock(PERMA_SLATE_BRICK.get().defaultBlockState(), properties), () -> BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(1.75F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE_BRICKS));
    public static final DeferredBlock<WallBlock> PERMA_SLATE_BRICK_WALL = register("perma_slate_brick_wall", (properties) -> new WallBlock(properties), () -> BlockBehaviour.Properties.of().strength(1.75F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK));

    public static final DeferredBlock<Block> PERMA_MAGMA = register("perma_magma", (properties) -> new PermaMagmaBlock(properties), () -> BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(1F, 3.0F)
            .isValidSpawn((p_187421_, p_187422_, p_187423_, p_187424_) -> p_187424_.fireImmune())
            .hasPostProcess(FrostBlocks::always));

    public static final DeferredBlock<Block> FRIGID_STONE = register("frigid_stone", (properties) -> new Block(properties), () -> BlockBehaviour.Properties.of().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK));
    public static final DeferredBlock<Block> FRIGID_GRASS_BLOCK = register("frigid_grass_block", (properties) -> new FrostGrassBlock(properties, FrostBlocks.FRIGID_STONE), () -> BlockBehaviour.Properties.of().randomTicks().requiresCorrectToolForDrops().strength(1.5F, 6.0F).sound(SoundType.NYLIUM));

    public static final DeferredBlock<SlabBlock> FRIGID_STONE_SLAB = register("frigid_stone_slab", (properties) -> new SlabBlock(properties), () -> BlockBehaviour.Properties.of().strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.NETHERRACK));
    public static final DeferredBlock<StairBlock> FRIGID_STONE_STAIRS = register("frigid_stone_stairs", (properties) -> new StairBlock(FRIGID_STONE.get().defaultBlockState(), properties), () -> BlockBehaviour.Properties.of().noOcclusion().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK));
    public static final DeferredBlock<Block> FRIGID_STONE_BRICK = register("frigid_stone_brick", (properties) -> new Block(properties), () -> BlockBehaviour.Properties.of().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK));
    public static final DeferredBlock<SlabBlock> FRIGID_STONE_BRICK_SLAB = register("frigid_stone_brick_slab", (properties) -> new SlabBlock(properties), () -> BlockBehaviour.Properties.of().strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK));
    public static final DeferredBlock<StairBlock> FRIGID_STONE_BRICK_STAIRS = register("frigid_stone_brick_stairs", (properties) -> new StairBlock(FRIGID_STONE_BRICK.get().defaultBlockState(), properties), () -> BlockBehaviour.Properties.of().strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK));
    public static final DeferredBlock<WallBlock> FRIGID_STONE_BRICK_WALL = register("frigid_stone_brick_wall", (properties) -> new WallBlock(properties), () -> BlockBehaviour.Properties.of().strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK));
    public static final DeferredBlock<Block> FRIGID_STONE_SMOOTH = register("frigid_stone_smooth", (properties) -> new Block(properties), () -> BlockBehaviour.Properties.of().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK));
    public static final DeferredBlock<Block> CHISELED_FRIGID_STONE_BRICK = register("chiseled_frigid_stone_brick", (properties) -> new Block(properties), () -> BlockBehaviour.Properties.of().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK));
    public static final DeferredBlock<Block> MAGMA_CORE = register("magma_core", (properties) -> new MagmaCoreBlock(properties), () -> BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(50.0F, 100.0F));


    public static final DeferredBlock<Block> FRIGID_STONE_MOSSY = register("frigid_stone_mossy", (properties) -> new Block(properties), () -> BlockBehaviour.Properties.of().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK));
    public static final DeferredBlock<SlabBlock> FRIGID_STONE_MOSSY_SLAB = register("frigid_stone_mossy_slab", (properties) -> new SlabBlock(properties), () -> BlockBehaviour.Properties.of().strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.NETHERRACK));
    public static final DeferredBlock<StairBlock> FRIGID_STONE_MOSSY_STAIRS = register("frigid_stone_mossy_stairs", (properties) -> new StairBlock(FRIGID_STONE_MOSSY.get().defaultBlockState(), properties), () -> BlockBehaviour.Properties.of().noOcclusion().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK));

    public static final DeferredBlock<Block> FRIGID_STONE_BRICK_MOSSY = register("frigid_stone_brick_mossy", (properties) -> new Block(properties), () -> BlockBehaviour.Properties.of().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK));
    public static final DeferredBlock<SlabBlock> FRIGID_STONE_BRICK_MOSSY_SLAB = register("frigid_stone_brick_mossy_slab", (properties) -> new SlabBlock(properties), () -> BlockBehaviour.Properties.of().strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK));
    public static final DeferredBlock<StairBlock> FRIGID_STONE_BRICK_MOSSY_STAIRS = register("frigid_stone_brick_mossy_stairs", (properties) -> new StairBlock(FRIGID_STONE_BRICK_MOSSY.get().defaultBlockState(), properties), () -> BlockBehaviour.Properties.of().strength(1.5F, 6.0F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK));

    public static final DeferredBlock<Block> SHERBET_SAND = register("sherbet_sand", (properties) -> new ColoredFallingBlock(new ColorRGBA(0xFFB9EB), properties), () -> BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.SAND));
    public static final DeferredBlock<Block> SHERBET_SANDSTONE = register("sherbet_sandstone", (properties) -> new Block(properties), () -> BlockBehaviour.Properties.of().strength(0.8F).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<SlabBlock> SHERBET_SANDSTONE_SLAB = register("sherbet_sandstone_slab", (properties) -> new SlabBlock(properties), () -> BlockBehaviour.Properties.of().strength(0.8F).noOcclusion().requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final DeferredBlock<StairBlock> SHERBET_SANDSTONE_STAIRS = register("sherbet_sandstone_stairs", (properties) -> new StairBlock(FRIGID_STONE.get().defaultBlockState(), properties), () -> BlockBehaviour.Properties.of().noOcclusion().strength(0.8F).requiresCorrectToolForDrops().sound(SoundType.STONE));


    //FROSTROOT
    public static final DeferredBlock<RotatedPillarBlock> FROSTROOT_LOG = register("frostroot_log", (properties) -> new RotatedPillarBlock(properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.PURPLE).strength(2.0F).sound(SoundType.CHERRY_WOOD));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_FROSTROOT_LOG = register("stripped_frostroot_log", (properties) -> new RotatedPillarBlock(properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.PURPLE).strength(2.0F).sound(SoundType.CHERRY_WOOD));
    public static final DeferredBlock<LeavesBlock> FROSTROOT_LEAVES = register("frostroot_leaves", (properties) -> new UntintedParticleLeavesBlock(0.01F, ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, 0x4F4C7C), properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.PURPLE).strength(0.2F).noOcclusion().isSuffocating(FrostBlocks::never).isViewBlocking(FrostBlocks::never).sound(SoundType.GRASS));
    public static final DeferredBlock<DoublePlantBlock> COLD_TALL_GRASS = registerDoubleBlockItem("cold_tall_grass", (properties) -> new DoublePlantBlock(properties.noOcclusion().noCollision().replaceable().sound(SoundType.GRASS)), BlockBehaviour.Properties.of());
    public static final DeferredBlock<Block> FROSTROOT_PLANKS = register("frostroot_planks", (properties) -> new Block(properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.PURPLE).strength(2.0F, 3.0F).sound(SoundType.CHERRY_WOOD));
    public static final DeferredBlock<SlabBlock> FROSTROOT_PLANKS_SLAB = register("frostroot_planks_slab", (properties) -> new SlabBlock(properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.PURPLE).strength(2.0F, 3.0F).noOcclusion().sound(SoundType.CHERRY_WOOD));
    public static final DeferredBlock<StairBlock> FROSTROOT_PLANKS_STAIRS = register("frostroot_planks_stairs", (properties) -> new StairBlock(FROSTROOT_PLANKS.get().defaultBlockState(), properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.PURPLE).strength(2.0F, 3.0F).noOcclusion().sound(SoundType.CHERRY_WOOD));
    public static final DeferredBlock<FenceBlock> FROSTROOT_FENCE = register("frostroot_fence", (properties) -> new FenceBlock(properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.PURPLE).strength(2.0F, 3.0F).noOcclusion().sound(SoundType.CHERRY_WOOD));
    public static final DeferredBlock<FenceGateBlock> FROSTROOT_FENCE_GATE = register("frostroot_fence_gate", (properties) -> new FenceGateBlock(FrostWoodTypes.FROSTROOT, properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.PURPLE).strength(2.0F, 3.0F).noOcclusion().sound(SoundType.CHERRY_WOOD));
    public static final DeferredBlock<DoorBlock> FROSTROOT_DOOR = register("frostroot_door", (properties) -> new DoorBlock(FrostBlockSetTypes.FROSTROOT, properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.PURPLE).strength(3.0F).noOcclusion().sound(SoundType.CHERRY_WOOD));
    public static final DeferredBlock<TrapDoorBlock> FROSTROOT_TRAPDOOR = register("frostroot_trapdoor", (properties) -> new TrapDoorBlock(FrostBlockSetTypes.FROSTROOT, properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.PURPLE).strength(3.0F).noOcclusion().sound(SoundType.CHERRY_WOOD));
    public static final DeferredBlock<ButtonBlock> FROSTROOT_BUTTON = register("frostroot_button", (properties) -> woodenButton(properties, FrostBlockSetTypes.FROSTBITE), () -> BlockBehaviour.Properties.of());
    public static final DeferredBlock<Block> BEARBERRY_BUSH = registerWithoutItem("bearberry_bush", (properties) -> new BearBerryBushBlock(properties.noOcclusion().noCollision().sound(SoundType.GRASS)), () -> BlockBehaviour.Properties.of());
    public static final DeferredBlock<RotatedPillarBlock> FROSTBITE_LOG = register("frostbite_log", (properties) -> new RotatedPillarBlock(properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.CYAN).strength(2.0F).randomTicks().sound(SoundType.NETHER_WOOD));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_FROSTBITE_LOG = register("stripped_frostbite_log", (properties) -> new RotatedPillarBlock(properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.CYAN).strength(2.0F).sound(SoundType.NETHER_WOOD));
    public static final DeferredBlock<LeavesBlock> FROSTBITE_LEAVES = register("frostbite_leaves", (properties) -> new UntintedParticleLeavesBlock(0.01F, ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, 0x526F90), properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.CYAN).strength(0.2F).noOcclusion().isSuffocating(FrostBlocks::never).isViewBlocking(FrostBlocks::never).sound(SoundType.GRASS));
    //CROP
    public static final DeferredBlock<Block> SUGARBEET = registerWithoutItem("sugarbeet", (properties) -> new SugarBeetBlock(properties.noOcclusion().noCollision().sound(SoundType.CROP)), () -> BlockBehaviour.Properties.of());
    public static final DeferredBlock<Block> FROSTBITE_PLANKS = register("frostbite_planks", (properties) -> new Block(properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.CYAN).strength(2.0F, 3.0F).sound(SoundType.NETHER_WOOD));
    public static final DeferredBlock<SlabBlock> FROSTBITE_PLANKS_SLAB = register("frostbite_planks_slab", (properties) -> new SlabBlock(properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.CYAN).strength(2.0F, 3.0F).noOcclusion().sound(SoundType.NETHER_WOOD));
    public static final DeferredBlock<StairBlock> FROSTBITE_PLANKS_STAIRS = register("frostbite_planks_stairs", (properties) -> new StairBlock(FROSTBITE_PLANKS.get().defaultBlockState(), properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.CYAN).strength(2.0F, 3.0F).noOcclusion().sound(SoundType.NETHER_WOOD));
    public static final DeferredBlock<FenceBlock> FROSTBITE_FENCE = register("frostbite_fence", (properties) -> new FenceBlock(properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.CYAN).strength(2.0F, 3.0F).noOcclusion().sound(SoundType.NETHER_WOOD));
    public static final DeferredBlock<FenceGateBlock> FROSTBITE_FENCE_GATE = register("frostbite_fence_gate", (properties) -> new FenceGateBlock(FrostWoodTypes.FROSTBITE, properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.CYAN).strength(2.0F, 3.0F).noOcclusion().sound(SoundType.NETHER_WOOD));
    public static final DeferredBlock<DoorBlock> FROSTBITE_DOOR = register("frostbite_door", (properties) -> new DoorBlock(FrostBlockSetTypes.FROSTBITE, properties), () -> BlockBehaviour.Properties.of().strength(3.0F).noOcclusion().sound(SoundType.NETHER_WOOD));
    public static final DeferredBlock<TrapDoorBlock> FROSTBITE_TRAPDOOR = register("frostbite_trapdoor", (properties) -> new TrapDoorBlock(FrostBlockSetTypes.FROSTBITE, properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.CYAN).strength(3.0F).noOcclusion().sound(SoundType.NETHER_WOOD));

    public static final DeferredBlock<ButtonBlock> FROSTBITE_BUTTON = register("frostbite_button", (properties) -> woodenButton(properties, FrostBlockSetTypes.FROSTBITE), () -> BlockBehaviour.Properties.of());
    public static final DeferredBlock<Block> RYE = registerWithoutItem("rye", (properties) -> new RyeBlock(properties.noOcclusion().noCollision().sound(SoundType.CROP)), () -> BlockBehaviour.Properties.of());

    public static final DeferredBlock<RotatedPillarBlock> ROCK_WOOD = register("rock_wood", (properties) -> new RotatedPillarBlock(properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.GRAY).strength(2.25F).sound(SoundType.CALCITE));
    public static final DeferredBlock<Block> ROCK_WOOD_PLANKS = register("rock_wood_planks", (properties) -> new Block(properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.GRAY).strength(2.25F, 5.0F).sound(SoundType.CALCITE));
    public static final DeferredBlock<SlabBlock> ROCK_WOOD_PLANKS_SLAB = register("rock_wood_planks_slab", (properties) -> new SlabBlock(properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.GRAY).strength(2.25F, 5.0F).noOcclusion().sound(SoundType.CALCITE));
    public static final DeferredBlock<StairBlock> ROCK_WOOD_PLANKS_STAIRS = register("rock_wood_planks_stairs", (properties) -> new StairBlock(ROCK_WOOD_PLANKS.get().defaultBlockState(), properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.GRAY).strength(2.25F, 5.0F).noOcclusion().sound(SoundType.CALCITE));
    public static final DeferredBlock<FenceBlock> ROCK_WOOD_FENCE = register("rock_wood_fence", (properties) -> new FenceBlock(properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.GRAY).strength(2.25F, 3.5F).noOcclusion().sound(SoundType.CALCITE));
    public static final DeferredBlock<FenceGateBlock> ROCK_WOOD_FENCE_GATE = register("rock_wood_fence_gate", (properties) -> new FenceGateBlock(FrostWoodTypes.ROCK_WOOD, properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.GRAY).strength(2.25F, 5.0F).noOcclusion().sound(SoundType.CALCITE));
    public static final DeferredBlock<DoorBlock> ROCK_WOOD_DOOR = register("rock_wood_door", (properties) -> new DoorBlock(FrostBlockSetTypes.ROCK_WOOD, properties), () -> BlockBehaviour.Properties.of().strength(2.25F, 5.0F).noOcclusion().sound(SoundType.CALCITE));
    public static final DeferredBlock<TrapDoorBlock> ROCK_WOOD_TRAPDOOR = register("rock_wood_trapdoor", (properties) -> new TrapDoorBlock(FrostBlockSetTypes.ROCK_WOOD, properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.GRAY).strength(2.25F, 5.0F).noOcclusion().sound(SoundType.CALCITE));

    public static final DeferredBlock<ButtonBlock> ROCK_WOOD_BUTTON = register("rock_wood_button", (properties) -> rockWoodButton(properties, FrostBlockSetTypes.ROCK_WOOD), () -> BlockBehaviour.Properties.of());
    public static final DeferredBlock<Block> WALL_FROST_TORCH = registerWithoutItem("wall_frost_torch", (properties) -> new WallFrostTorchBlock(properties), () -> BlockBehaviour.Properties.of().noCollision().instabreak().lightLevel(p_220871_ -> 14).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY));

    public static final DeferredBlock<RotatedPillarBlock> DRIP_LOG = register("drip_log", (properties) -> new RotatedPillarBlock(properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.BROWN).strength(2.0F).sound(SoundType.NETHER_WOOD));
    //public static final DeferredBlock<RotatedPillarBlock> STRIPPED_DRIP_LOG = register("stripped_drip_log", (properties) -> new RotatedPillarBlock(properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.BROWN).strength(2.0F).sound(SoundType.NETHER_WOOD));
    public static final DeferredBlock<LeavesBlock> DRIP_LEAVES = register("drip_leaves", (properties) -> new UntintedParticleLeavesBlock(0.01F, ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, 0xB84727), properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.BROWN).strength(0.2F).noOcclusion().isSuffocating(FrostBlocks::never).isViewBlocking(FrostBlocks::never).sound(SoundType.GRASS));
    public static final DeferredBlock<DripHangingMossBlock> DRIP_HANGING_LEAVES = register("drip_hanging_leaves", DripHangingMossBlock::new, () -> BlockBehaviour.Properties.of()
            .ignitedByLava()
            .mapColor(DyeColor.BROWN)
            .noCollision()
            .sound(SoundType.MOSS_CARPET)
            .pushReaction(PushReaction.DESTROY));
    public static final DeferredBlock<Block> FROST_TORCH = registerTorchBlock("frost_torch", (properties) -> new FrostTorchBlock(properties), WALL_FROST_TORCH, BlockBehaviour.Properties.of().noCollision().instabreak().lightLevel(p_220871_ -> 14).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY));
    public static final DeferredBlock<Block> DRIP_PLANKS = register("drip_planks", (properties) -> new Block(properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.BROWN).strength(2.0F, 3.0F).sound(SoundType.NETHER_WOOD));
    public static final DeferredBlock<SlabBlock> DRIP_PLANKS_SLAB = register("drip_planks_slab", (properties) -> new SlabBlock(properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.BROWN).strength(2.0F, 3.0F).noOcclusion().sound(SoundType.NETHER_WOOD));
    public static final DeferredBlock<StairBlock> DRIP_PLANKS_STAIRS = register("drip_planks_stairs", (properties) -> new StairBlock(DRIP_PLANKS.get().defaultBlockState(), properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.BROWN).strength(2.0F, 3.0F).noOcclusion().sound(SoundType.NETHER_WOOD));
    public static final DeferredBlock<FenceBlock> DRIP_FENCE = register("drip_fence", (properties) -> new FenceBlock(properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.BROWN).strength(2.0F, 3.0F).noOcclusion().sound(SoundType.NETHER_WOOD));
    public static final DeferredBlock<FenceGateBlock> DRIP_FENCE_GATE = register("drip_fence_gate", (properties) -> new FenceGateBlock(FrostWoodTypes.DRIP, properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.BROWN).strength(2.0F, 3.0F).noOcclusion().sound(SoundType.NETHER_WOOD));
    public static final DeferredBlock<DoorBlock> DRIP_DOOR = register("drip_door", (properties) -> new DoorBlock(FrostBlockSetTypes.DRIP, properties), () -> BlockBehaviour.Properties.of().strength(3.0F).noOcclusion().sound(SoundType.NETHER_WOOD));
    public static final DeferredBlock<TrapDoorBlock> DRIP_TRAPDOOR = register("drip_trapdoor", (properties) -> new TrapDoorBlock(FrostBlockSetTypes.DRIP, properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.BROWN).strength(3.0F).noOcclusion().sound(SoundType.NETHER_WOOD));

    public static final DeferredBlock<ButtonBlock> DRIP_BUTTON = register("drip_button", (properties) -> woodenButton(properties, FrostBlockSetTypes.DRIP), () -> BlockBehaviour.Properties.of());
    public static final DeferredBlock<SaplingBlock> FROSTROOT_SAPLING = register("frostroot_sapling", (properties) -> new SaplingBlock(FrostTrees.FROSTROOT, properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.PURPLE).randomTicks().noCollision().noOcclusion().sound(SoundType.GRASS));
    public static final DeferredBlock<PressurePlateBlock> FROSTROOT_PRESSURE_PLATE = register(
            "frostroot_pressure_plate",
            (properties) -> new PressurePlateBlock(
                    FrostBlockSetTypes.FROSTROOT,
                    properties), () -> BlockBehaviour.Properties.of()
                    .mapColor(FROSTROOT_PLANKS.get().defaultMapColor())
                    .forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS)
                    .noCollision()
                    .strength(0.5F)
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY)
    );
    public static final DeferredBlock<SaplingBlock> FROSTBITE_SAPLING = register("frostbite_sapling", (properties) -> new SaplingBlock(FrostTrees.FROSTBITE, properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.CYAN).randomTicks().noCollision().noOcclusion().sound(SoundType.GRASS));
    public static final DeferredBlock<PressurePlateBlock> FROSTBITE_PRESSURE_PLATE = register(
            "frostbite_pressure_plate",
            (properties) -> new PressurePlateBlock(
                    FrostBlockSetTypes.FROSTBITE,
                    properties), () -> BlockBehaviour.Properties.of()
                    .mapColor(FROSTBITE_PLANKS.get().defaultMapColor())
                    .forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS)
                    .noCollision()
                    .strength(0.5F)
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY)
    );
    public static final DeferredBlock<PressurePlateBlock> ROCK_WOOD_PRESSURE_PLATE = register(
            "rock_wood_pressure_plate",
            (properties) -> new PressurePlateBlock(
                    FrostBlockSetTypes.ROCK_WOOD,
                    properties), () -> BlockBehaviour.Properties.of()
                    .mapColor(ROCK_WOOD_PLANKS.get().defaultMapColor())
                    .forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS)
                    .noCollision()
                    .strength(0.5F, 2.5F)
                    .pushReaction(PushReaction.DESTROY)
    );
    public static final DeferredBlock<SaplingBlock> DRIP_SAPLING = register("drip_sapling", (properties) -> new SaplingBlock(FrostTrees.DRIP, properties), () -> BlockBehaviour.Properties.of().mapColor(DyeColor.BROWN).randomTicks().noCollision().noOcclusion().sound(SoundType.GRASS));
    public static final DeferredBlock<PressurePlateBlock> DRIP_PRESSURE_PLATE = register(
            "drip_pressure_plate",
            (properties) -> new PressurePlateBlock(
                    FrostBlockSetTypes.DRIP,
                    properties), () -> BlockBehaviour.Properties.of()
                    .mapColor(DRIP_PLANKS.get().defaultMapColor())
                    .forceSolidOn()
                    .instrument(NoteBlockInstrument.BASS)
                    .noCollision()
                    .strength(0.5F)
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY)
    );
    //PLANT
    public static final DeferredBlock<Block> VIGOROSHROOM = register("vigoroshroom", (properties) -> new VigoroMushroomBlock(properties), () -> BlockBehaviour.Properties.of().noOcclusion().noCollision().lightLevel(state -> {
        return 10;
    }).sound(SoundType.GRASS));
    public static final DeferredBlock<Block> ARCTIC_POPPY = register("arctic_poppy", (properties) -> new FlowerBlock(FrostEffects.COLD_RESISTANCE, 200, properties), () -> BlockBehaviour.Properties.of().noOcclusion().noCollision().sound(SoundType.GRASS));

    public static final DeferredBlock<Block> RYE_BLOCK = register("rye_block", (properties) -> new HayBlock(properties), () -> BlockBehaviour.Properties.of().sound(SoundType.GRASS));
    //EGG
    public static final DeferredBlock<Block> SNOWPILE_QUAIL_EGG = register("snowpile_quail_egg", (properties) -> new SnowPileQuailEggBlock(properties), () -> BlockBehaviour.Properties.of().noOcclusion().strength(0.2F, 0.25F).sound(SoundType.METAL));
    public static final DeferredBlock<Block> SILK_MOON_EGG = register("silk_moon_egg", (properties) -> new SilkMoonEggBlock(properties), () -> BlockBehaviour.Properties.of().noOcclusion().strength(0.2F, 0.25F).sound(SoundType.METAL));
    public static final DeferredBlock<Block> SILK_MOON_COCOON = register("silk_moon_cocoon", (properties) -> new SilkMoonCocoonBlock(properties), () -> BlockBehaviour.Properties.of().noOcclusion().strength(0.75F).sound(SoundType.WOOL));

    //ORE
    public static final DeferredBlock<Block> FROST_CRYSTAL_ORE = register("frost_crystal_ore", (properties) -> new DropExperienceBlock(UniformInt.of(1, 2), properties), () -> BlockBehaviour.Properties.of().strength(3.0F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK));
    public static final DeferredBlock<Block> GLIMMERROCK_ORE = register("glimmerrock_ore", (properties) -> new DropExperienceBlock(UniformInt.of(1, 3), properties), () -> BlockBehaviour.Properties.of().strength(3.0F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK));
    public static final DeferredBlock<Block> ASTRIUM_ORE = register("astrium_ore", (properties) -> new Block(properties), () -> BlockBehaviour.Properties.of().strength(3.0F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE));
    public static final DeferredBlock<Block> FROST_CRYSTAL_SLATE_ORE = register("frost_crystal_slate_ore", (properties) -> new DropExperienceBlock(UniformInt.of(1, 2), properties), () -> BlockBehaviour.Properties.of().strength(4.5F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE));
    public static final DeferredBlock<Block> GLIMMERROCK_SLATE_ORE = register("glimmerrock_slate_ore", (properties) -> new DropExperienceBlock(UniformInt.of(1, 3), properties), () -> BlockBehaviour.Properties.of().strength(4.5F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE));
    public static final DeferredBlock<Block> ASTRIUM_SLATE_ORE = register("astrium_slate_ore", (properties) -> new Block(properties), () -> BlockBehaviour.Properties.of().strength(4.6F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE));
    public static final DeferredBlock<Block> FROST_CRYSTAL_BLOCK = register("frost_crystal_block", (properties) -> new Block(properties), () -> BlockBehaviour.Properties.of().strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL));
    public static final DeferredBlock<Block> ASTRIUM_BLOCK = register("astrium_block", (properties) -> new Block(properties), () -> BlockBehaviour.Properties.of().strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK));
    public static final DeferredBlock<Block> RAW_ASTRIUM_BLOCK = register("raw_astrium_block", (properties) -> new Block(properties), () -> BlockBehaviour.Properties.of().strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK));

    public static final DeferredBlock<Block> GLIMMERROCK_BLOCK = register("glimmerrock_block", (properties) -> new Block(properties), () -> BlockBehaviour.Properties.of().strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL));


    public static final DeferredBlock<Block> GLACINIUM_ORE = register("glacinium_ore", (properties) -> new DropExperienceBlock(UniformInt.of(3, 6), properties), () -> BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops()
            .strength(10.0F, 100.0F).sound(SoundType.DEEPSLATE));

    public static final DeferredBlock<Block> RAW_GLACINIUM_BLOCK = register("raw_glacinium_block", (properties) -> new Block(properties), () -> BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops()
            .strength(15.0F, 100.0F).sound(SoundType.GLASS));
    public static final DeferredBlock<Block> GLACINIUM_BLOCK = register("glacinium_block", (properties) -> new Block(properties), () -> BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops()
            .strength(15.0F, 100.0F).sound(SoundType.GLASS));


    public static final DeferredBlock<Block> STARDUST_CRYSTAL_ORE = register("stardust_crystal_ore", (properties) -> new DropExperienceBlock(UniformInt.of(2, 4), properties), () -> BlockBehaviour.Properties.of().strength(3.0F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK));
    public static final DeferredBlock<Block> STARDUST_CRYSTAL_CLUSTER = register("stardust_crystal_cluster", (properties) -> new Block(properties), () -> BlockBehaviour.Properties.of().isSuffocating(FrostBlocks::never).requiresCorrectToolForDrops().lightLevel((state) -> {
        return 10;
    }).strength(5.0F, 6.0F).noOcclusion().sound(SoundType.DEEPSLATE));


    public static final DeferredBlock<Block> WARPED_CRYSTAL_BLOCK = register("warped_crystal_block", (properties) -> new Block(properties), () -> BlockBehaviour.Properties.of().requiresCorrectToolForDrops().lightLevel((state) -> {
        return 12;
    }).strength(5.0F, 6.0F).noOcclusion().sound(SoundType.GLASS));
    public static final DeferredBlock<Block> ARCTIC_WILLOW = register("arctic_willow", (properties) -> new FlowerBlock(FrostEffects.COLD_RESISTANCE, 200, properties), () -> BlockBehaviour.Properties.of().noOcclusion().noCollision().sound(SoundType.GRASS));
    public static final DeferredBlock<Block> COLD_GRASS = register("cold_grass", (properties) -> new ColdTallGrassBlock(properties), () -> BlockBehaviour.Properties.of().noOcclusion().noCollision().replaceable().sound(SoundType.GRASS));

    public static final DeferredBlock<Block> FROST_CAMPFIRE = register("frost_campfire", (properties) -> new FrostCampfireBlock(properties), () -> BlockBehaviour.Properties.of().strength(2.0F).noOcclusion().lightLevel(litBlockEmission(13)).sound(SoundType.WOOD));
    public static final DeferredBlock<Block> FROST_FIRE = registerWithoutItem("frost_fire",
            (prop) -> new FrostFireBlock(prop),
            () -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLUE)
                    .replaceable()
                    .noCollision()
                    .instabreak()
                    .lightLevel(p_50755_ -> 10)
                    .sound(SoundType.WOOL)
                    .pushReaction(PushReaction.DESTROY));

    public static final DeferredBlock<Block> AURORA_INFUSER = register("aurora_infuser", (properties) -> new AuroraInfuserBlock(properties), () -> BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(2.0F, 3.0F).sound(SoundType.METAL));
    public static final DeferredBlock<Block> WOLFFLUE_BLOCK = register("wolfflue_block", (properties) -> new WolfflueBlock(properties), () -> BlockBehaviour.Properties.of().strength(5.0F, 5.0F).sound(SoundType.WOOL));

    private static <T extends Block> DeferredBlock<Block> registerWithoutItem(String name, Supplier<Block.Properties> properties) {
        return registerWithoutItem(name, Block::new, properties);
    }

    private static <T extends Block> DeferredBlock<T> registerWithoutItem(String name, Function<Block.Properties, T> builder, Supplier<Block.Properties> properties) {
        return registerWithoutItem(name, createKey(name), builder, properties);
    }

    private static <T extends Block> DeferredBlock<T> registerWithoutItem(String name, ResourceKey<Block> key, Function<Block.Properties, T> builder, Supplier<Block.Properties> properties) {
        return BLOCKS.register(name, () -> builder.apply(properties.get().setId(key)));
    }

    private static <T extends Block> DeferredBlock<Block> register(String name, Supplier<Block.Properties> properties) {
        return register(name, Block::new, properties);
    }

    private static <T extends Block> DeferredBlock<T> register(String name, Function<Block.Properties, T> builder, Supplier<Block.Properties> properties) {
        return register(name, createKey(name), builder, properties);
    }

    private static <T extends Block> DeferredBlock<T> register(String name, ResourceKey<Block> key, Function<Block.Properties, T> builder, Supplier<Block.Properties> properties) {
        return baseRegister(name, key, builder, properties, (deferredBlock) -> registerBlockItem(deferredBlock, name));
    }

    private static ResourceKey<Block> createKey(String name) {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FrostRealm.MODID, name));
    }

    private static <T extends Block> DeferredBlock<T> baseRegister(String name, ResourceKey<Block> key, Function<Block.Properties, T> builder, Supplier<Block.Properties> properties, Function<DeferredBlock<T>, Supplier<? extends Item>> item) {
        DeferredBlock<T> registered = BLOCKS.register(name, () -> builder.apply(properties.get().setId(key)));
        FrostItems.ITEMS.register(name, item.apply(registered));
        return registered;
    }

    private static ToIntFunction<BlockState> litBlockEmission(int p_50760_) {
        return (p_50763_) -> {
            return p_50763_.getValue(BlockStateProperties.LIT) ? p_50760_ : 0;
        };
    }

    private static ButtonBlock woodenButton(BlockBehaviour.Properties properties, BlockSetType p_278239_) {
        return new ButtonBlock(p_278239_, 30, properties.noCollision().strength(0.5F).pushReaction(PushReaction.DESTROY));
    }

    private static ButtonBlock rockWoodButton(BlockBehaviour.Properties properties, BlockSetType p_278239_) {
        return new ButtonBlock(p_278239_, 30, properties.noCollision().strength(0.5F, 2.5F).pushReaction(PushReaction.DESTROY));
    }
    private static boolean always(BlockState p_50775_, BlockGetter p_50776_, BlockPos p_50777_) {
        return true;
    }

    private static boolean never(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
        return false;
    }

    public static void burnables() {
        FireBlock fireblock = (FireBlock) Blocks.FIRE;
        fireblock.setFlammable(FROSTROOT_SAPLING.get(), 60, 100);
        fireblock.setFlammable(FROSTROOT_LEAVES.get(), 60, 100);
        fireblock.setFlammable(FROSTROOT_LOG.get(), 5, 5);
        fireblock.setFlammable(STRIPPED_FROSTROOT_LOG.get(), 5, 5);
        fireblock.setFlammable(FROSTROOT_PLANKS.get(), 5, 20);
        fireblock.setFlammable(FROSTROOT_PLANKS_SLAB.get(), 5, 20);
        fireblock.setFlammable(FROSTROOT_PLANKS_STAIRS.get(), 5, 20);
        fireblock.setFlammable(FROSTROOT_FENCE.get(), 5, 20);
        fireblock.setFlammable(FROSTROOT_FENCE_GATE.get(), 5, 20);
        fireblock.setFlammable(FROSTROOT_DOOR.get(), 5, 20);
        fireblock.setFlammable(FROSTROOT_TRAPDOOR.get(), 5, 20);

        fireblock.setFlammable(FROSTBITE_SAPLING.get(), 60, 100);
        fireblock.setFlammable(FROSTBITE_LEAVES.get(), 60, 100);
        fireblock.setFlammable(FROSTBITE_LOG.get(), 5, 5);
        fireblock.setFlammable(STRIPPED_FROSTBITE_LOG.get(), 5, 5);
        fireblock.setFlammable(FROSTBITE_PLANKS.get(), 5, 20);
        fireblock.setFlammable(FROSTBITE_PLANKS_SLAB.get(), 5, 20);
        fireblock.setFlammable(FROSTBITE_PLANKS_STAIRS.get(), 5, 20);
        fireblock.setFlammable(FROSTBITE_FENCE.get(), 5, 20);
        fireblock.setFlammable(FROSTBITE_FENCE_GATE.get(), 5, 20);
        fireblock.setFlammable(FROSTBITE_DOOR.get(), 5, 20);
        fireblock.setFlammable(FROSTBITE_TRAPDOOR.get(), 5, 20);


        fireblock.setFlammable(DRIP_SAPLING.get(), 60, 100);
        fireblock.setFlammable(DRIP_LEAVES.get(), 60, 100);
        fireblock.setFlammable(DRIP_HANGING_LEAVES.get(), 60, 100);
        fireblock.setFlammable(DRIP_LOG.get(), 5, 5);
        //fireblock.setFlammable(STRIPPED_DRIP_LOG.get(), 5, 5);
        fireblock.setFlammable(DRIP_PLANKS.get(), 5, 20);
        fireblock.setFlammable(DRIP_PLANKS_SLAB.get(), 5, 20);
        fireblock.setFlammable(DRIP_PLANKS_STAIRS.get(), 5, 20);
        fireblock.setFlammable(DRIP_FENCE.get(), 5, 20);
        fireblock.setFlammable(DRIP_FENCE_GATE.get(), 5, 20);
        fireblock.setFlammable(DRIP_DOOR.get(), 5, 20);
        fireblock.setFlammable(DRIP_TRAPDOOR.get(), 5, 20);


        fireblock.setFlammable(COLD_TALL_GRASS.get(), 60, 100);
        fireblock.setFlammable(RYE_BLOCK.get(), 60, 20);
    }

    public static <T extends Block> DeferredBlock<T> registerDoubleBlockItem(String name, Function<BlockBehaviour.Properties, T> block, BlockBehaviour.Properties properties) {
        DeferredBlock<T> ret = BLOCKS.register(name, () -> block.apply(properties.setId(ResourceKey.create(Registries.BLOCK, FrostRealm.prefix(name)))));
        FrostItems.ITEMS.registerItem(name, itemProps -> new DeferredDoubleHighBlockItem(ret, itemProps.useBlockDescriptionPrefix()), new Item.Properties());
        return ret;
    }

    private static <T extends Block> DeferredBlock<T> registerTorchBlock(String name, Function<BlockBehaviour.Properties, T> block, DeferredBlock<T> wallTorchBlock, BlockBehaviour.Properties properties) {
        DeferredBlock<T> ret = BLOCKS.register(name, () -> block.apply(properties.setId(ResourceKey.create(Registries.BLOCK, FrostRealm.prefix(name)))));
        FrostItems.ITEMS.registerItem(name, itemProps -> new StandingAndWallBlockItem(ret.get(), wallTorchBlock.get(), Direction.DOWN, itemProps.useBlockDescriptionPrefix()), new Item.Properties());
        return ret;

    }

    private static <T extends Block> Supplier<BlockItem> registerBlockItem(final DeferredBlock<T> deferredBlock, String name) {
        return () -> {
            DeferredBlock<T> block = Objects.requireNonNull(deferredBlock);
            Item.Properties properties = new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(FrostRealm.MODID, name))).useBlockDescriptionPrefix();
            if (block == FROST_TORCH) {
                return new StandingAndWallBlockItem(FrostBlocks.FROST_TORCH.get(), FrostBlocks.WALL_FROST_TORCH.get(), Direction.DOWN, properties);
            } else {
                return new BlockItem(block.get(), properties);
            }
        };
    }
}
