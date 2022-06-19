package net.frozenblock.wilderwild.world.feature.features;

import com.mojang.serialization.Codec;
import net.frozenblock.wilderwild.block.ShelfFungusBlock;
import net.frozenblock.wilderwild.world.feature.features.config.ShelfFungusFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Iterator;
import java.util.List;

public class ShelfFungusFeature extends Feature<ShelfFungusFeatureConfig> {
    public ShelfFungusFeature(Codec<ShelfFungusFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<ShelfFungusFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        Random abstractRandom = context.getRandom();
        ShelfFungusFeatureConfig shelfFungusFeatureConfig = context.getConfig();
        if (!isAirOrWater(structureWorldAccess.getBlockState(blockPos))) {
            return false;
        } else {
            List<Direction> list = shelfFungusFeatureConfig.shuffleDirections(abstractRandom);
            if (generate(structureWorldAccess, blockPos, structureWorldAccess.getBlockState(blockPos), shelfFungusFeatureConfig, abstractRandom, list)) {
                return true;
            } else {
                Mutable mutable = blockPos.mutableCopy();

                for (Direction direction : list) {
                    mutable.set(blockPos);
                    List<Direction> list2 = shelfFungusFeatureConfig.shuffleDirections(abstractRandom, direction.getOpposite());

                    for (int i = 0; i < shelfFungusFeatureConfig.searchRange; ++i) {
                        mutable.set(blockPos, direction);
                        BlockState blockState = structureWorldAccess.getBlockState(mutable);
                        if (!isAirOrWater(blockState) && !blockState.isOf(shelfFungusFeatureConfig.fungus)) {
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

    public static boolean generate(StructureWorldAccess world, BlockPos pos, BlockState state, ShelfFungusFeatureConfig config, Random random, List<Direction> directions) {
        Mutable mutable = pos.mutableCopy();
        Iterator<Direction> var7 = directions.iterator();

        Direction direction;
        Direction placementDirection;
        BlockState blockState;
        do {
            if (!var7.hasNext()) {
                return false;
            }

            direction = var7.next();
            blockState = world.getBlockState(mutable.set(pos, direction));
            placementDirection = direction;
            if (placementDirection.getAxis() == Direction.Axis.Y) {
                placementDirection = Direction.Type.HORIZONTAL.random(random);
            } else {
                placementDirection = placementDirection.getOpposite();
            }
        } while (!blockState.isIn(config.canPlaceOn));

        BlockState blockState2 = config.fungus.getDefaultState().with(ShelfFungusBlock.FACING, placementDirection).with(ShelfFungusBlock.FACE, ShelfFungusBlock.getFace(direction)).with(ShelfFungusBlock.STAGE, random.nextInt(3) + 1);
        if (blockState2 == null) {
            return false;
        } else {
            world.setBlockState(pos, blockState2, 3);
            world.getChunk(pos).markBlockForPostProcessing(pos);

            return true;
        }
    }

    private static boolean isAirOrWater(BlockState state) {
        return state.isAir() || state.isOf(Blocks.WATER);
    }
}
