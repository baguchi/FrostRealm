package baguchan.frostrealm.registry;

import baguchan.frostrealm.FrostRealm;
import baguchan.frostrealm.world.caver.FRCanyonWorldCarver;
import baguchan.frostrealm.world.caver.FRCaveWorldCarver;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.TrapezoidFloat;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.*;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FrostCarvers {
	public static final DeferredRegister<WorldCarver<?>> CARVERS = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, FrostRealm.MODID);

	public static final RegistryObject<WorldCarver<CaveCarverConfiguration>> FROSTREALM_CAVE = CARVERS.register("frostrealm_cave", () -> new FRCaveWorldCarver(CaveCarverConfiguration.CODEC));

	public static final RegistryObject<WorldCarver<CanyonCarverConfiguration>> FROSTREALM_CANYON = CARVERS.register("frostrealm_canyon", () -> new FRCanyonWorldCarver(CanyonCarverConfiguration.CODEC));

	public static void registerConfiguredCarvers() {
		register("frostrealm_cave", FROSTREALM_CAVE.get().configured(new CaveCarverConfiguration(0.33333334F, UniformHeight.of(VerticalAnchor.aboveBottom(8), VerticalAnchor.absolute(126)), UniformFloat.of(0.1F, 0.9F), VerticalAnchor.aboveBottom(8), false, CarverDebugSettings.of(false, Blocks.CRIMSON_BUTTON.defaultBlockState()), UniformFloat.of(0.7F, 1.4F), UniformFloat.of(0.8F, 1.3F), UniformFloat.of(-1.0F, -0.4F))));
		register("frostrealm_canyon", FROSTREALM_CANYON.get().configured(new CanyonCarverConfiguration(0.02F, UniformHeight.of(VerticalAnchor.absolute(10), VerticalAnchor.absolute(67)), ConstantFloat.of(3.0F), VerticalAnchor.aboveBottom(8), false, CarverDebugSettings.of(false, Blocks.WARPED_BUTTON.defaultBlockState()), UniformFloat.of(-0.125F, 0.125F), new CanyonCarverConfiguration.CanyonShapeConfiguration(UniformFloat.of(0.75F, 1.0F), TrapezoidFloat.of(0.0F, 6.0F, 2.0F), 3, UniformFloat.of(0.75F, 1.0F), 1.0F, 0.0F))));
	}

	private static <WC extends CarverConfiguration> ConfiguredWorldCarver<WC> register(String p_126856_, ConfiguredWorldCarver<WC> p_126857_) {
		return BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_CARVER, new ResourceLocation(FrostRealm.MODID, p_126856_), p_126857_);
	}
}
