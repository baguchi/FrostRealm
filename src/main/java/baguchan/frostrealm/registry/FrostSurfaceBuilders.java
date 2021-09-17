package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderConfiguration;

public class FrostSurfaceBuilders {
	public static final SurfaceBuilderBaseConfiguration CONFIG_GRASS = new SurfaceBuilderBaseConfiguration(FrostBlocks.FROZEN_GRASS_BLOCK.get().defaultBlockState(), FrostBlocks.FROZEN_DIRT.get().defaultBlockState(), FrostBlocks.FROZEN_DIRT.get().defaultBlockState());
	public static final SurfaceBuilderBaseConfiguration CONFIG_STONE = new SurfaceBuilderBaseConfiguration(FrostBlocks.FRIGID_STONE.get().defaultBlockState(), FrostBlocks.FRIGID_STONE.get().defaultBlockState(), FrostBlocks.FRIGID_STONE.get().defaultBlockState());
	public static final SurfaceBuilderBaseConfiguration CONFIG_ICE = new SurfaceBuilderBaseConfiguration(Blocks.BLUE_ICE.defaultBlockState(), FrostBlocks.FRIGID_STONE.get().defaultBlockState(), FrostBlocks.FRIGID_STONE.get().defaultBlockState());


	public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> GRASS = register(FrostRealm.MODID + ":frozen_grass", SurfaceBuilder.DEFAULT.configured(CONFIG_GRASS));
	public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> FRIGID_STONE = register(FrostRealm.MODID + ":frigid_stone", SurfaceBuilder.DEFAULT.configured(CONFIG_STONE));
	public static final ConfiguredSurfaceBuilder<SurfaceBuilderBaseConfiguration> ICE = register(FrostRealm.MODID + ":ice", SurfaceBuilder.DEFAULT.configured(CONFIG_ICE));

	private static <SC extends SurfaceBuilderConfiguration> ConfiguredSurfaceBuilder<SC> register(String p_127301_, ConfiguredSurfaceBuilder<SC> p_127302_) {
		return BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, p_127301_, p_127302_);
	}

	public static void init() {
	}
}
