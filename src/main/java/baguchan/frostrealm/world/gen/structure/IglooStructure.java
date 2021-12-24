package baguchan.frostrealm.world.gen.structure;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.JigsawFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;

public class IglooStructure extends JigsawFeature {
	public IglooStructure(Codec<JigsawConfiguration> codec) {
		super(codec, 0, true, true, (p_197185_) -> {
			return true;
		});
	}

	@Override
	public GenerationStep.Decoration step() {
		return GenerationStep.Decoration.SURFACE_STRUCTURES;
	}
}
