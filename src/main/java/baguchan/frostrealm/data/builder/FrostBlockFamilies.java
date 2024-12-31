package baguchan.frostrealm.data.builder;

import baguchan.frostrealm.registry.FrostBlocks;
import com.google.common.collect.Maps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.stream.Stream;

public class FrostBlockFamilies {
    private static final Map<Block, BlockFamily> MAP = Maps.newHashMap();

    public static final BlockFamily FROSTROOT = familyBuilder(FrostBlocks.FROSTROOT_PLANKS.get())
            .button(FrostBlocks.FROSTROOT_BUTTON.get())
            .fence(FrostBlocks.FROSTROOT_FENCE.get())
            .fenceGate(FrostBlocks.FROSTROOT_FENCE_GATE.get())
            .pressurePlate(FrostBlocks.FROSTROOT_PRESSURE_PLATE.get())
            //.sign(FrostBlocks.FROSTROOT_SIGN.get(), FrostBlocks.FROSTROOT_WALL_SIGN.get())
            .slab(FrostBlocks.FROSTROOT_PLANKS_SLAB.get())
            .stairs(FrostBlocks.FROSTROOT_PLANKS_STAIRS.get())
            .door(FrostBlocks.FROSTROOT_DOOR.get())
            .trapdoor(FrostBlocks.FROSTROOT_TRAPDOOR.get())
            .getFamily();
    public static final BlockFamily FROSTBITE = familyBuilder(FrostBlocks.FROSTBITE_PLANKS.get())
            .button(FrostBlocks.FROSTBITE_BUTTON.get())
            .fence(FrostBlocks.FROSTBITE_FENCE.get())
            .fenceGate(FrostBlocks.FROSTBITE_FENCE_GATE.get())
            .pressurePlate(FrostBlocks.FROSTBITE_PRESSURE_PLATE.get())
            //.sign(FrostBlocks.FROSTBITE_SIGN.get(), FrostBlocks.FROSTBITE_WALL_SIGN.get())
            .slab(FrostBlocks.FROSTBITE_PLANKS_SLAB.get())
            .stairs(FrostBlocks.FROSTBITE_PLANKS_STAIRS.get())
            .door(FrostBlocks.FROSTBITE_DOOR.get())
            .trapdoor(FrostBlocks.FROSTBITE_TRAPDOOR.get())
            .getFamily();

    private static BlockFamily.Builder familyBuilder(Block baseBlock) {
        BlockFamily.Builder builder = new BlockFamily.Builder(baseBlock);
        BlockFamily blockfamily = MAP.put(baseBlock, builder.getFamily());
        if (blockfamily != null) {
            throw new IllegalStateException("Duplicate family definition for " + BuiltInRegistries.BLOCK.getKey(baseBlock));
        } else {
            return builder;
        }
    }

    public static Stream<BlockFamily> getAllFamilies() {
        return MAP.values().stream();
    }
}