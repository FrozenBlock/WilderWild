package net.frozenblock.wilderwild.world.feature.features;

import com.mojang.serialization.Codec;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;

public final class AlgaeFeature
        extends Feature<ProbabilityFeatureConfiguration> {

    private static final int MIN_RADIUS = 4;
    private static final int MAX_RADIUS = 10;

    public AlgaeFeature(final Codec<ProbabilityFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(
            final FeaturePlaceContext<ProbabilityFeatureConfiguration> context) {
        boolean canPlace = false;
        BlockPos blockPos = context.origin();
        WorldGenLevel level = context.level();
        BlockPos solidBlockPos = blockPos.atY(
                level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES,
                        blockPos.getX(), blockPos.getZ()));
        int y = solidBlockPos.getY();
        RandomSource random = level.getRandom();
        int radius = random.nextIntBetweenInclusive(MIN_RADIUS, MAX_RADIUS);
        //DISK
        BlockPos.MutableBlockPos mutableDisk = solidBlockPos.mutable();
        int bx = solidBlockPos.getX();
        int bz = solidBlockPos.getZ();
        for (int x = bx - radius; x <= bx + radius; x++) {
            for (int z = bz - radius; z <= bz + radius; z++) {
                double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)));
                if (distance < radius * radius) {
                    mutableDisk.set(x, y, z);
                    boolean fade = !mutableDisk.closerThan(solidBlockPos,
                            radius * 0.8);
                    boolean hasGeneratedThisRound = false;
                    if (level.getBlockState(mutableDisk.below())
                            .is(Blocks.WATER) &&
                            level.getFluidState(mutableDisk).isEmpty() &&
                            level.getBlockState(mutableDisk).isAir()) {
                        if (random.nextFloat() > 0.2F) {
                            hasGeneratedThisRound = true;
                            if (fade) {
                                if (random.nextFloat() > 0.5F) {
                                    level.setBlock(mutableDisk,
                                            RegisterBlocks.ALGAE.defaultBlockState(),
                                            3);
                                    canPlace = true;
                                }
                            } else {
                                level.setBlock(mutableDisk,
                                        RegisterBlocks.ALGAE.defaultBlockState(),
                                        3);
                                canPlace = true;
                            }
                        }
                    } else {
                        for (int aY = 0; aY < 3; aY++) {
                            mutableDisk.set(x, y + aY, z);
                            if (level.getBlockState(mutableDisk.below())
                                    .is(Blocks.WATER) &&
                                    level.getFluidState(mutableDisk)
                                            .isEmpty() &&
                                    level.getBlockState(mutableDisk).isAir()) {
                                hasGeneratedThisRound = true;
                                if (random.nextFloat() > 0.2F) {
                                    if (fade) {
                                        if (random.nextFloat() > 0.5F) {
                                            level.setBlock(mutableDisk,
                                                    RegisterBlocks.ALGAE.defaultBlockState(),
                                                    3);
                                            canPlace = true;
                                        }
                                    } else {
                                        level.setBlock(mutableDisk,
                                                RegisterBlocks.ALGAE.defaultBlockState(),
                                                3);
                                        canPlace = true;
                                    }
                                }
                            }
                        }
                    }
                    if (!hasGeneratedThisRound) {
                        for (int aY = -3; aY < 0; aY++) {
                            mutableDisk.set(x, y + aY, z);
                            if (level.getBlockState(mutableDisk.below())
                                    .is(Blocks.WATER) &&
                                    level.getFluidState(mutableDisk)
                                            .isEmpty() &&
                                    level.getBlockState(mutableDisk).isAir()) {
                                if (random.nextFloat() > 0.2F) {
                                    if (fade) {
                                        if (random.nextFloat() > 0.5F) {
                                            level.setBlock(mutableDisk,
                                                    RegisterBlocks.ALGAE.defaultBlockState(),
                                                    3);
                                            canPlace = true;
                                        }
                                    } else {
                                        level.setBlock(mutableDisk,
                                                RegisterBlocks.ALGAE.defaultBlockState(),
                                                3);
                                        canPlace = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return canPlace;
    }

}
