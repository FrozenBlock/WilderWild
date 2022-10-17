package net.frozenblock.wilderwild.world.feature.features;

import com.mojang.serialization.Codec;
import java.util.Iterator;
import java.util.List;
import net.frozenblock.wilderwild.block.ShelfFungusBlock;
import net.frozenblock.wilderwild.world.feature.features.config.ShelfFungusFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class ShelfFungusFeature extends Feature<ShelfFungusFeatureConfig> {
    public ShelfFungusFeature(Codec<ShelfFungusFeatureConfig> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<ShelfFungusFeatureConfig> context) {
        WorldGenLevel structureWorldAccess = context.level();
        BlockPos blockPos = context.origin();
        RandomSource abstractRandom = context.random();
        ShelfFungusFeatureConfig shelfFungusFeatureConfig = context.config();
        if (!isAirOrWater(structureWorldAccess.getBlockState(blockPos))) {
            return false;
        } else {
            List<Direction> list = shelfFungusFeatureConfig.shuffleDirections(abstractRandom);
            if (generate(structureWorldAccess, blockPos, structureWorldAccess.getBlockState(blockPos), shelfFungusFeatureConfig, abstractRandom, list)) {
                return true;
            } else {
                MutableBlockPos mutable = blockPos.mutable();

                for (Direction direction : list) {
                    mutable.set(blockPos);
                    List<Direction> list2 = shelfFungusFeatureConfig.shuffleDirections(abstractRandom, direction.getOpposite());

                    for (int i = 0; i < shelfFungusFeatureConfig.searchRange; ++i) {
                        mutable.setWithOffset(blockPos, direction);
                        BlockState blockState = structureWorldAccess.getBlockState(mutable);
                        if (!isAirOrWater(blockState) && !blockState.is(shelfFungusFeatureConfig.fungus)) {
                            break;
                        }

                        if (generate(structureWorldAccess, mutable, blockState, shelfFungusFeatureConfig, abstractRandom, list2)) {
                            return true;
                        }
                    }
                }

                return false;
            }
        }
    }

    public static boolean generate(WorldGenLevel level, BlockPos pos, BlockState state, ShelfFungusFeatureConfig config, RandomSource random, List<Direction> directions) {
        MutableBlockPos mutable = pos.mutable();
        Iterator<Direction> var7 = directions.iterator();

        Direction direction;
        Direction placementDirection;
        BlockState blockState;
        do {
            if (!var7.hasNext()) {
                return false;
            }

            direction = var7.next();
            blockState = level.getBlockState(mutable.setWithOffset(pos, direction));
            placementDirection = direction;
            if (placementDirection.getAxis() == Direction.Axis.Y) {
                placementDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
            } else {
                placementDirection = placementDirection.getOpposite();
            }
        } while (!blockState.is(config.canPlaceOn));

        BlockState blockState2 = config.fungus.defaultBlockState().setValue(ShelfFungusBlock.FACING, placementDirection).setValue(ShelfFungusBlock.FACE, ShelfFungusBlock.getFace(direction)).setValue(ShelfFungusBlock.STAGE, random.nextInt(3) + 1);
        if (blockState2 == null) {
            return false;
        } else {
            level.setBlock(pos, blockState2, 3);
            level.getChunk(pos).markPosForPostprocessing(pos);

            return true;
        }
    }

    private static boolean isAirOrWater(BlockState state) {
        return state.isAir() || state.is(Blocks.WATER);
    }
}
