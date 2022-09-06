package net.frozenblock.wilderwild.world.feature.features;

import com.mojang.serialization.Codec;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.world.feature.features.config.NematocystFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.material.FluidState;

public class NematocystFeature extends Feature<NematocystFeatureConfig> {

    public NematocystFeature(Codec<NematocystFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NematocystFeatureConfig> featurePlaceContext) {
        NematocystFeatureConfig config = featurePlaceContext.config();
        RandomSource randomSource = featurePlaceContext.random();
        BlockPos blockPos = featurePlaceContext.origin();
        WorldGenLevel worldGenLevel = featurePlaceContext.level();
        int i = 0;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int j = config.xzSpread() + 1;
        int k = config.ySpread() + 1;
        for (int l = 0; l < config.tries(); ++l) {
            mutableBlockPos.setWithOffset(blockPos, randomSource.nextInt(j) - randomSource.nextInt(j), randomSource.nextInt(k) - randomSource.nextInt(k), randomSource.nextInt(j) - randomSource.nextInt(j));
            if (!this.place(worldGenLevel, randomSource, mutableBlockPos, config)) continue;
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

    public BlockState getSurvivalState(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        Direction direction = blockState.getValue(BlockStateProperties.FACING);
        BlockPos blockPos2 = blockPos.relative(direction.getOpposite());
        BlockState state2 = levelReader.getBlockState(blockPos2);
        FluidState fluidState = levelReader.getFluidState(blockPos2);
        if (!state2.isAir() || !(state2.is(Blocks.WATER) && fluidState.isSource())) {
            return null;
        }
        boolean waterlogged = state2.is(Blocks.WATER) && fluidState.isSource();
        Direction facing = blockState.getValue(BlockStateProperties.FACING);
        if (blockState.is(RegisterBlocks.NEMATOCYST) && !state2.is(RegisterBlocks.MESOGLEA)) {
            blockState = RegisterBlocks.NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, waterlogged).setValue(BlockStateProperties.FACING, facing);
        } else if (blockState.is(RegisterBlocks.PURPLE_MESOGLEA) && !state2.is(RegisterBlocks.PURPLE_MESOGLEA)) {
            blockState = RegisterBlocks.PURPLE_NEMATOCYST.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, waterlogged).setValue(BlockStateProperties.FACING, facing);
        }
        return state2.isFaceSturdy(levelReader, blockPos2, direction) ? blockState : null;
    }

}

