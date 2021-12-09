package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.world.gen.feature.BigRockFeature;
import baguchan.frostrealm.world.gen.feature.LargeIceFeature;
import baguchan.frostrealm.world.gen.feature.SmallIceFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DripstoneClusterConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FrostRealm.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FrostFeatures {
	public static final Feature<LargeDripstoneConfiguration> LARGE_ICE = new LargeIceFeature(LargeDripstoneConfiguration.CODEC);
	public static final Feature<DripstoneClusterConfiguration> ICE_CLUSTER = new SmallIceFeature(DripstoneClusterConfiguration.CODEC);
	public static final Feature<BlockStateConfiguration> BIG_ROCK = new BigRockFeature(BlockStateConfiguration.CODEC);

	@SubscribeEvent
	public static void registerFeature(RegistryEvent.Register<Feature<?>> event) {
		event.getRegistry().register(LARGE_ICE.setRegistryName("large_ice"));
		event.getRegistry().register(ICE_CLUSTER.setRegistryName("ice_cluster"));
		event.getRegistry().register(BIG_ROCK.setRegistryName("big_rock"));
	}
}
