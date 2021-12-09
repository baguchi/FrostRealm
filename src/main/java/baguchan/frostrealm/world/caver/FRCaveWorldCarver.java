package baguchan.frostrealm.world.caver;

import baguchan.frostrealm.registry.FrostBlocks;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CaveWorldCarver;
import net.minecraft.world.level.material.Fluids;

public class FRCaveWorldCarver extends CaveWorldCarver {
	public FRCaveWorldCarver(Codec<CaveCarverConfiguration> p_159194_) {
		super(p_159194_);
		this.replaceableBlocks = ImmutableSet.of(FrostBlocks.FRIGID_STONE
				, FrostBlocks.FROZEN_DIRT
				, FrostBlocks.FROZEN_GRASS_BLOCK
				, Blocks.PACKED_ICE, Blocks.BLUE_ICE
		);
		this.liquids = ImmutableSet.of(Fluids.WATER);
	}
}
