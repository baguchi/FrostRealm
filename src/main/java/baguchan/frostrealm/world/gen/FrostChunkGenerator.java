package baguchan.frostrealm.world.gen;

import baguchan.frostrealm.data.resource.FrostNoises;
import baguchan.frostrealm.registry.FrostBiomes;
import baguchan.frostrealm.registry.FrostBlocks;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;

import java.util.function.Supplier;

public class FrostChunkGenerator extends NoiseBasedChunkGenerator {
    public static final Codec<FrostChunkGenerator> FROST_CODEC = RecordCodecBuilder.create(
            p_255585_ -> p_255585_.group(
                            BiomeSource.CODEC.fieldOf("biome_source").forGetter(p_255584_ -> p_255584_.biomeSource),
                            NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(p_224278_ -> p_224278_.settings)
                    )
                    .apply(p_255585_, p_255585_.stable(FrostChunkGenerator::new))
    );
    protected final BiomeSource biomeSource;
    private final Holder<NoiseGeneratorSettings> settings;
    private final Supplier<Aquifer.FluidPicker> globalFluidPicker;

    public FrostChunkGenerator(BiomeSource p_256415_, Holder<NoiseGeneratorSettings> p_256182_) {
        super(p_256415_, p_256182_);
        this.biomeSource = p_256415_;
        this.settings = p_256182_;
        this.globalFluidPicker = Suppliers.memoize(() -> createFluidPicker(p_256182_.value()));
    }

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return FROST_CODEC;
    }

    private static Aquifer.FluidPicker createFluidPicker(NoiseGeneratorSettings p_249264_) {
        Aquifer.FluidStatus aquifer$fluidstatus = new Aquifer.FluidStatus(-54, Blocks.LAVA.defaultBlockState());
        int i = p_249264_.seaLevel();
        Aquifer.FluidStatus aquifer$fluidstatus1 = new Aquifer.FluidStatus(i, p_249264_.defaultFluid());
        Aquifer.FluidStatus aquifer$fluidstatus2 = new Aquifer.FluidStatus(DimensionType.MIN_Y * 2, Blocks.AIR.defaultBlockState());
        return (p_224274_, p_224275_, p_224276_) -> p_224275_ < Math.min(-54, i) ? aquifer$fluidstatus : aquifer$fluidstatus1;
    }

    //Thanks TwilightForest about Chunk based gen!
    private void addIslands(WorldGenRegion primer, ChunkAccess chunk, RandomState randomState, int height) {
        BlockPos blockpos = primer.getCenter().getWorldPosition();
        int[] thicks = new int[5 * 5];
        boolean biomeFound = false;
        boolean stony_islands = false;
        for (int dZ = 0; dZ < 5; dZ++) {
            for (int dX = 0; dX < 5; dX++) {
                for (int bx = -1; bx <= 1; bx++) {
                    for (int bz = -1; bz <= 1; bz++) {
                        BlockPos p = blockpos.offset((dX + bx) << 2, 0, (dZ + bz) << 2);
                        Biome biome = biomeSource.getNoiseBiome(p.getX() >> 2, 256, p.getZ() >> 2, randomState.sampler()).value();
                        if (FrostBiomes.CRYSTAL_FALL.location().equals(primer.registryAccess().registryOrThrow(Registries.BIOME).getKey(biome))) {
                            thicks[dX + dZ * 5]++;
                            biomeFound = true;
                            stony_islands = true;
                        }
                    }
                }
            }
        }

        if (!biomeFound) return;
        double d = 0.03125D;

        for (int dZ = 0; dZ < 16; dZ++) {
            for (int dX = 0; dX < 16; dX++) {
                int qx = dX >> 2;
                int qz = dZ >> 2;

                float xweight = (dX % 4) * 0.25F + 0.125F;
                float zweight = (dZ % 4) * 0.25F + 0.125F;

                float thickness = thicks[qx + (qz) * 5] * (1F - xweight) * (1F - zweight)
                        + thicks[qx + 1 + (qz) * 5] * (xweight) * (1F - zweight)
                        + thicks[qx + (qz + 1) * 5] * (1F - xweight) * (zweight)
                        + thicks[qx + 1 + (qz + 1) * 5] * (xweight) * (zweight)
                        - 4;
                if (thickness > 1) {
                    final int dY = chunk.getHeight(Heightmap.Types.WORLD_SURFACE_WG, dX, dZ);
                    final int oceanFloor = chunk.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, dX, dZ);
                    BlockPos pos = primer.getCenter().getWorldPosition().offset(dX, dY, dZ);

                    int noise = (int) (randomState.getOrCreateNoise(FrostNoises.ISLANDS_BOTTOM_HEIGHT).getValue(pos.getX(), pos.getY(), pos.getZ()) * 64D);
                    int noise2 = (int) (randomState.getOrCreateNoise(FrostNoises.ISLANDS_HEIGHT).getValue(pos.getX(), pos.getY(), pos.getZ()) * 24D);

                    // manipulate top and bottom
                    int islandsBottom = pos.getY() + height - (int) (thickness * 0.5F);
                    int islandsTop = islandsBottom + (int) (thickness * 1.5F);

                    islandsBottom -= noise;
                    islandsTop -= noise2;
                    BlockState baseBlock = FrostBlocks.FRIGID_STONE.get().defaultBlockState();
                    BlockState topBlock = FrostBlocks.FRIGID_GRASS_BLOCK.get().defaultBlockState();

                    if (!stony_islands) {
                        baseBlock = FrostBlocks.FROZEN_DIRT.get().defaultBlockState();
                        topBlock = FrostBlocks.FROZEN_GRASS_BLOCK.get().defaultBlockState();
                    }

                    for (int y = islandsBottom; y < islandsTop; y++) {
                        if (y < islandsTop - 1) {
                            primer.setBlock(pos.atY(y), baseBlock, 3);
                        } else {
                            primer.setBlock(pos.atY(y), topBlock, 3);
                        }
                    }

                    // What are you gonna do, call the cops?
                    forceHeightMapLevel(chunk, Heightmap.Types.WORLD_SURFACE_WG, pos, dY);
                    forceHeightMapLevel(chunk, Heightmap.Types.WORLD_SURFACE, pos, dY);
                    forceHeightMapLevel(chunk, Heightmap.Types.OCEAN_FLOOR_WG, pos, oceanFloor);
                    forceHeightMapLevel(chunk, Heightmap.Types.OCEAN_FLOOR, pos, oceanFloor);
                }
            }
        }
    }

    @Override
    public void buildSurface(WorldGenRegion p_224232_, StructureManager p_224233_, RandomState p_224234_, ChunkAccess p_224235_) {
        if (!SharedConstants.debugVoidTerrain(p_224235_.getPos())) {
            WorldGenerationContext worldgenerationcontext = new WorldGenerationContext(this, p_224232_);
            this.buildSurface(
                    p_224232_,
                    p_224235_,
                    worldgenerationcontext,
                    p_224234_,
                    p_224233_,
                    p_224232_.getBiomeManager(),
                    p_224232_.registryAccess().registryOrThrow(Registries.BIOME),
                    Blender.of(p_224232_)
            );
        }
    }

    @VisibleForTesting
    public void buildSurface(
            WorldGenRegion worldGenRegion,
            ChunkAccess p_224262_,
            WorldGenerationContext p_224263_,
            RandomState p_224264_,
            StructureManager p_224265_,
            BiomeManager p_224266_,
            Registry<Biome> p_224267_,
            Blender p_224268_
    ) {
        NoiseChunk noisechunk = p_224262_.getOrCreateNoiseChunk(p_224321_ -> this.createNoiseChunk(p_224321_, p_224265_, p_224268_, p_224264_));
        NoiseGeneratorSettings noisegeneratorsettings = this.settings.value();
        p_224264_.surfaceSystem()
                .buildSurface(
                        p_224264_,
                        p_224266_,
                        p_224267_,
                        noisegeneratorsettings.useLegacyRandomSource(),
                        p_224263_,
                        p_224262_,
                        noisechunk,
                        noisegeneratorsettings.surfaceRule()
                );
        this.addIslands(worldGenRegion, p_224262_, p_224264_, 64);

    }

    private NoiseChunk createNoiseChunk(ChunkAccess p_224257_, StructureManager p_224258_, Blender p_224259_, RandomState p_224260_) {
        return NoiseChunk.forChunk(
                p_224257_,
                p_224260_,
                Beardifier.forStructuresInChunk(p_224258_, p_224257_.getPos()),
                this.settings.value(),
                this.globalFluidPicker.get(),
                p_224259_
        );
    }


    static void forceHeightMapLevel(ChunkAccess chunk, Heightmap.Types type, BlockPos pos, int dY) {
        chunk.getOrCreateHeightmapUnprimed(type).setHeight(pos.getX() & 15, pos.getZ() & 15, dY + 1);
    }
}