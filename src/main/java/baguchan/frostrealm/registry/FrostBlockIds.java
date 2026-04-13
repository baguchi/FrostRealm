package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;

public class FrostBlockIds {

    public static final ResourceKey<Block> FROZEN_DIRT = createKey("frozen_dirt");
    public static final ResourceKey<Block> FRIGID_STONE = createKey("frigid_stone");

    private static ResourceKey<Block> createKey(String name) {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(FrostRealm.MODID, name));
    }
}

