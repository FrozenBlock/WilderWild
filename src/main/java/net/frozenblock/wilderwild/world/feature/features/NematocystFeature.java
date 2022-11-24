package net.frozenblock.wilderwild.world.feature.features;

import com.mojang.serialization.Codec;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.world.feature.features.config.NematocystFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.FluidState;

public class NematocystFeature extends Feature<NematocystFeatureConfig> {

    public NematocystFeature(Codec<NematocystFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NematocystFeatureConfig> context) {
        NematocystFeatureConfig config = context.config();
        RandomSource randomSource = context.random();
        BlockPos blockPos = context.origin();
        WorldGenLevel level = context.level();
        int i = 0;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int j = config.xzSpread() + 1;
        int k = config.ySpread() + 1;
        for (int l = 0; l < config.tries(); ++l) {
            mutableBlockPos.setWithOffset(blockPos, randomSource.nextInt(j) - randomSource.nextInt(j), randomSource.nextInt(k) - randomSource.nextInt(k), randomSource.nextInt(j) - randomSource.nextInt(j));
            if (!this.place(level, randomSource, mutableBlockPos, config)) continue;
            ++i;
        }
        return i > 0;
    }

    public boolean place(WorldGenLevel level, RandomSource randomSource, BlockPos pos, NematocystFeatureConfig config) {
        BlockState placeState = getSurvivalState(config.stateProvider().getState(randomSource, pos), level, pos);
        if (placeState == null) {
            return false;
        }
        level.setBlock(pos, placeState, 2);
        return true;
    }

    public BlockState getSurvivalState(BlockState blockState, LevelReader level, BlockPos pos) {
        Direction direction = blockState.getValue(BlockStateProperties.FACING);
        BlockPos blockPos2 = pos.relative(direction.getOpposite());
        BlockState placedOnState = level.getBlockState(blockPos2);
        FluidState fluidState = level.getFluidState(pos);
        BlockState replaceState = level.getBlockState(pos);
        boolean waterlogged = !fluidState.isEmpty() && placedOnState.is(Blocks.WATER);
        if ((replaceState.isAir() && fluidState.isEmpty()) || waterlogged) {
            Direction facing = blockState.getValue(BlockStateProperties.FACING);
            if (placedOnState.is(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA)) {
                blockState = RegisterBlocks.BLUE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, waterlogged).setValue(BlockStateProperties.FACING, facing);
            } else if (placedOnState.is(RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA)) {
                blockState = RegisterBlocks.PURPLE_PEARLESCENT_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, waterlogged).setValue(BlockStateProperties.FACING, facing);
            }
            return placedOnState.isFaceSturdy(level, blockPos2, direction) ? blockState : null;
        }
        return null;
    }

}

