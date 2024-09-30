package baguchan.frostrealm.world.tree;

import baguchan.frostrealm.world.gen.FrostTreeFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class FrostTrees {
    public static final TreeGrower FROSTROOT = new TreeGrower(
            "frsotroot",
            0.15F,
            Optional.empty(),
            Optional.empty(),
            Optional.of(FrostTreeFeatures.FROST_TREE),
            Optional.of(FrostTreeFeatures.FROST_TREE_BIG),
            Optional.empty(),
            Optional.empty()
    );
}
