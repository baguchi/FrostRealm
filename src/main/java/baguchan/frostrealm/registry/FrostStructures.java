package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.world.gen.structure.FrostMineshaftStructure;
import baguchan.frostrealm.world.gen.structure.IglooStructure;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Locale;

@Mod.EventBusSubscriber(modid = FrostRealm.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FrostStructures {
	public static final Holder<StructureTemplatePool> IGLOO_START = Pools.register(new StructureTemplatePool(new ResourceLocation("frostrealm:igloo/igloo_entrance"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(StructurePoolElement.single("frostrealm:igloo/igloo_entrance", ProcessorLists.EMPTY), 1)), StructureTemplatePool.Projection.RIGID));

	public static final StructureFeature<JigsawConfiguration> IGLOO = new IglooStructure(JigsawConfiguration.CODEC);
	public static final StructureFeature<JigsawConfiguration> FROST_MINESHAFT = new FrostMineshaftStructure(JigsawConfiguration.CODEC);


	static StructurePieceType setPieceId(StructurePieceType p_67164_, String p_67165_) {
		return Registry.register(Registry.STRUCTURE_PIECE, p_67165_.toLowerCase(Locale.ROOT), p_67164_);
	}

	@SubscribeEvent
	public static void registerStructure(RegistryEvent.Register<StructureFeature<?>> registry) {
		registry.getRegistry().register(IGLOO.setRegistryName("igloo"));
		registry.getRegistry().register(FROST_MINESHAFT.setRegistryName("frost_mineshaft"));
	}

	private static String prefix(String path) {
		return "frostrealm:" + path;
	}

}
