package baguchan.frostrealm.world.caver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CanyonWorldCarver;
import net.minecraft.world.level.material.Fluids;

public class FRCanyonWorldCarver extends CanyonWorldCarver {
	public FRCanyonWorldCarver(Codec<CanyonCarverConfiguration> codec) {
		super(codec);
		this.liquids = ImmutableSet.of(Fluids.WATER);
	}
}
