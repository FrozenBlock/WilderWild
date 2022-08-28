package net.frozenblock.wilderwild.world.feature.features;

import com.mojang.serialization.Codec;
import net.frozenblock.wilderwild.block.ClusterBlock;
import net.frozenblock.wilderwild.world.feature.features.config.ClusterFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.List;

public class ClusterFeature extends Feature<ClusterFeatureConfig> {

    public ClusterFeature(Codec<ClusterFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ClusterFeatureConfig> context) {
        WorldGenLevel worldGenLevel = context.level();
        BlockPos blockPos = context.origin();
        RandomSource randomSource = context.random();
        ClusterFeatureConfig amethystClusterFeatureConfiguration = context.config();
        if (!isAirOrWater(worldGenLevel.getBlockState(blockPos))) {
            return false;
        } else {
            List<Direction> list = amethystClusterFeatureConfiguration.getShuffledDirections(randomSource);
            if (placeGrowthIfPossible(worldGenLevel, blockPos, worldGenLevel.getBlockState(blockPos), amethystClusterFeatureConfiguration, randomSource, list)) {
                return true;
            } else {
                BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();

                for (Direction direction : list) {
                    mutableBlockPos.set(blockPos);
                    List<Direction> list2 = amethystClusterFeatureConfiguration.getShuffledDirectionsExcept(randomSource, direction.getOpposite());

                    for (int i = 0; i < amethystClusterFeatureConfiguration.searchRange; ++i) {
                        mutableBlockPos.setWithOffset(blockPos, direction);
                        BlockState blockState = worldGenLevel.getBlockState(mutableBlockPos);
                        if (!isAirOrWater(blockState) && !blockState.is(amethystClusterFeatureConfiguration.placeBlock)) {
                            break;
                        }

                        if (placeGrowthIfPossible(worldGenLevel, mutableBlockPos, blockState, amethystClusterFeatureConfiguration, randomSource, list2)) {
                            return true;
                        }
                    }
                }

                return false;
            }
        }
    }

    public static boolean placeGrowthIfPossible(
            WorldGenLevel world, BlockPos pos, BlockState state, ClusterFeatureConfig config, RandomSource random, List<Direction> directions
    ) {
        BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();

        for (Direction direction : directions) {
            BlockState blockState = world.getBlockState(mutableBlockPos.setWithOffset(pos, direction));
            if (blockState.is(config.canBePlacedOn)) {
                BlockState blockState2 = ((ClusterBlock) config.placeBlock).getStateForPlacement(world.getBlockState(pos).getBlock(), state, world, pos, direction);
                if (blockState2 == null) {
                    return false;
                }

                world.setBlock(pos, blockState2, 3);
                world.getChunk(pos).markPosForPostprocessing(pos);
                /*if (random.nextFloat() < config.chanceOfSpreading) {
                    config.placeBlock.getSpreader().spreadFromFaceTowardRandomDirection(blockState2, world, pos, direction, random, true);
                }*/

                return true;
            }
        }

        return false;
    }

    private static boolean isAirOrWater(BlockState state) {
        return state.isAir() || state.is(Blocks.WATER);
    }
}
