package net.frozenblock.wilderwild.world.generation.features;

import com.mojang.serialization.Codec;
import java.util.Iterator;
import net.frozenblock.wilderwild.block.WaterloggableTallFlowerBlock;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;

public class CattailFeature extends Feature<ProbabilityFeatureConfiguration> {
    public CattailFeature(Codec<ProbabilityFeatureConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<ProbabilityFeatureConfiguration> context) {
        boolean bl = false;
        RandomSource abstractRandom = context.random();
        WorldGenLevel structureWorldAccess = context.level();
        BlockPos blockPos = context.origin();
        int loopFor = context.random().nextIntBetweenInclusive(12, 21);
        for (int l = 0; l < loopFor; l++) {
            int i = abstractRandom.nextIntBetweenInclusive(-7, 7);
            int j = abstractRandom.nextIntBetweenInclusive(-7, 7);
            int k = structureWorldAccess.getHeight(Types.OCEAN_FLOOR, blockPos.getX() + i, blockPos.getZ() + j);
            BlockPos randomPos = new BlockPos(blockPos.getX() + i, k, blockPos.getZ() + j);
            if (structureWorldAccess.getBlockState(randomPos).is(Blocks.WATER)) {
                BlockState bottom = RegisterBlocks.CATTAIL.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true);
                if (bottom.canSurvive(structureWorldAccess, randomPos)) {
                    BlockState top = bottom.setValue(WaterloggableTallFlowerBlock.HALF, DoubleBlockHalf.UPPER).setValue(BlockStateProperties.WATERLOGGED, false);
                    BlockPos upPos = randomPos.above();
                    if (structureWorldAccess.getBlockState(upPos).is(Blocks.AIR)) {
                        structureWorldAccess.setBlock(randomPos, bottom, 2);
                        structureWorldAccess.setBlock(upPos, top, 2);
                        bl = true;
                    }
                }
            } else {
                BlockState bottom = RegisterBlocks.CATTAIL.defaultBlockState();
                if (bottom.canSurvive(structureWorldAccess, randomPos)) {
                    BlockState top = bottom.setValue(WaterloggableTallFlowerBlock.HALF, DoubleBlockHalf.UPPER);
                    BlockPos upPos = randomPos.above();
                    if (structureWorldAccess.getBlockState(upPos).is(Blocks.AIR) && isWaterNearby(structureWorldAccess, randomPos, 2)) {
                        structureWorldAccess.setBlock(randomPos, bottom, 2);
                        structureWorldAccess.setBlock(upPos, top, 2);
                        bl = true;
                    }
                }
            }
        }

        return bl;
    }

    public static boolean isWaterNearby(WorldGenLevel level, BlockPos blockPos, int x) {
        Iterator<BlockPos> var2 = BlockPos.betweenClosed(blockPos.offset(-x, -x, -x), blockPos.offset(x, x, x)).iterator();
        BlockPos blockPos2;
        do {
            if (!var2.hasNext()) {
                return false;
            }
            blockPos2 = var2.next();
        } while (!level.getBlockState(blockPos2).is(Blocks.WATER));
        return true;
    }
}
