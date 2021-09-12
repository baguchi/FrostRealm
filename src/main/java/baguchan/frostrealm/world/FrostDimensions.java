package baguchan.frostrealm.world;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class FrostDimensions {
	public static final ResourceKey<Level> frostrealm = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(FrostRealm.MODID, "frostrealm"));
}
