package baguchan.frostrealm.world.gen.structure;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.JigsawFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;

public class FrostMineshaftStructure extends JigsawFeature {
	public FrostMineshaftStructure(Codec<JigsawConfiguration> codec) {
		super(codec, 10, false, false, (p_197185_) -> {
			return true;
		});
	}

	@Override
	public GenerationStep.Decoration step() {
		return GenerationStep.Decoration.UNDERGROUND_STRUCTURES;
	}
}
