package baguchan.frostrealm.world.caver;

import baguchan.frostrealm.registry.FrostBlocks;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.carver.CanyonCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CanyonWorldCarver;
import net.minecraft.world.level.material.Fluids;

public class FRCanyonWorldCarver extends CanyonWorldCarver {
	public FRCanyonWorldCarver(Codec<CanyonCarverConfiguration> codec) {
		super(codec);
		this.replaceableBlocks = ImmutableSet.of(FrostBlocks.FRIGID_STONE.get()
				, FrostBlocks.FROZEN_DIRT.get()
				, FrostBlocks.FROZEN_GRASS_BLOCK.get()
				, Blocks.PACKED_ICE, Blocks.BLUE_ICE);
		this.liquids = ImmutableSet.of(Fluids.WATER);
	}
}
