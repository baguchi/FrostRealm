package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;
import java.util.function.Supplier;

public class FrostPoiTypes {
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, FrostRealm.MODID);
    public static final ResourceKey<PoiType> FROST_PORTAL = createKey("frostrealm_portal");
    public static final Supplier<PoiType> FROST_PORTAL_POI = POI_TYPES.register("frostrealm_portal", () -> new PoiType(getBlockStates(FrostBlocks.FROST_PORTAL.get()), 0, 1));

    private static ResourceKey<PoiType> createKey(String p_218091_) {
        return ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, ResourceLocation.fromNamespaceAndPath(FrostRealm.MODID, p_218091_));
    }

    private static Set<BlockState> getBlockStates(Block p_218074_) {
        return ImmutableSet.copyOf(p_218074_.getStateDefinition().getPossibleStates());
    }
}