package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;

public class FrostStructures {
	public static final ResourceKey<Structure> CRYSTAL_TEMPLE = ResourceKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(FrostRealm.MODID, "crystal_temple"));

}
