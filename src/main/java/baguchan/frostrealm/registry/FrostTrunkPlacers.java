package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.world.tree.trunkplacers.FrostBiteTrunkPlacer;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class FrostTrunkPlacers {
    public static final TrunkPlacerType<FrostBiteTrunkPlacer> FROST_BITE_TRUNK_PLACER = register(FrostRealm.prefixOnString("frost_bite_trunk_placer"), FrostBiteTrunkPlacer.CODEC);

    private static <P extends TrunkPlacer> TrunkPlacerType<P> register(String p_70327_, Codec<P> p_70328_) {
        return Registry.register(BuiltInRegistries.TRUNK_PLACER_TYPE, p_70327_, new TrunkPlacerType<>(p_70328_));
    }

    public static void init() {

    }
}
