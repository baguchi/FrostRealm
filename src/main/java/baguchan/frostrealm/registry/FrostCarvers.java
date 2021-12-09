package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.world.caver.FRCanyonWorldCarver;
import baguchan.frostrealm.world.caver.FRCaveWorldCarver;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.TrapezoidFloat;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.*;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FrostRealm.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FrostCarvers {
	public static final WorldCarver<CaveCarverConfiguration> FROSTREALM_CAVE = new FRCaveWorldCarver(CaveCarverConfiguration.CODEC);
	public static final WorldCarver<CanyonCarverConfiguration> FROSTREALM_CANYON = new FRCanyonWorldCarver(CanyonCarverConfiguration.CODEC);

	public static final ConfiguredWorldCarver<CaveCarverConfiguration> CAVE = register("frostrealm_cave", FROSTREALM_CAVE.configured(new CaveCarverConfiguration(0.15F, UniformHeight.of(VerticalAnchor.aboveBottom(8), VerticalAnchor.absolute(180)), UniformFloat.of(0.1F, 0.9F), VerticalAnchor.aboveBottom(8), CarverDebugSettings.of(false, Blocks.CRIMSON_BUTTON.defaultBlockState()), UniformFloat.of(0.7F, 1.4F), UniformFloat.of(0.8F, 1.3F), UniformFloat.of(-1.0F, -0.4F))));
	public static final ConfiguredWorldCarver<CaveCarverConfiguration> CAVE_UNDERGROUND = register("frostrealm_cave_extra_underground", FROSTREALM_CAVE.configured(new CaveCarverConfiguration(0.07F, UniformHeight.of(VerticalAnchor.aboveBottom(8), VerticalAnchor.absolute(47)), UniformFloat.of(0.1F, 0.9F), VerticalAnchor.aboveBottom(8), CarverDebugSettings.of(false, Blocks.OAK_BUTTON.defaultBlockState()), UniformFloat.of(0.7F, 1.4F), UniformFloat.of(0.8F, 1.3F), UniformFloat.of(-1.0F, -0.4F))));
	public static final ConfiguredWorldCarver<CanyonCarverConfiguration> CANYON = register("frostrealm_canyon", FROSTREALM_CANYON.configured(new CanyonCarverConfiguration(0.01F, UniformHeight.of(VerticalAnchor.absolute(10), VerticalAnchor.absolute(67)), ConstantFloat.of(3.0F), VerticalAnchor.aboveBottom(8), CarverDebugSettings.of(false, Blocks.WARPED_BUTTON.defaultBlockState()), UniformFloat.of(-0.125F, 0.125F), new CanyonCarverConfiguration.CanyonShapeConfiguration(UniformFloat.of(0.75F, 1.0F), TrapezoidFloat.of(0.0F, 6.0F, 2.0F), 3, UniformFloat.of(0.75F, 1.0F), 1.0F, 0.0F))));

	@SubscribeEvent
	public static void registerCarver(RegistryEvent.Register<WorldCarver<?>> event) {
		event.getRegistry().register(FROSTREALM_CAVE.setRegistryName("frostrealm_cave"));
		event.getRegistry().register(FROSTREALM_CANYON.setRegistryName("frostrealm_canyon"));
	}

	public static void registerConfiguredCarvers() {
	}

	private static <WC extends CarverConfiguration> ConfiguredWorldCarver<WC> register(String p_126856_, ConfiguredWorldCarver<WC> p_126857_) {
		return Registry.register(BuiltinRegistries.CONFIGURED_CARVER, new ResourceLocation(FrostRealm.MODID, p_126856_), p_126857_);
	}
}
