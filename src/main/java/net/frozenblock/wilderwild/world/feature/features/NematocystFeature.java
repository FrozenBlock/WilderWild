package net.frozenblock.wilderwild.world.feature.features;

import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.MultifaceGrowthFeature;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import org.jetbrains.annotations.NotNull;

public class NematocystFeature extends MultifaceGrowthFeature {

    public NematocystFeature(Codec<MultifaceGrowthConfiguration> codec) {
        super(codec);
    }

    @Override
	public boolean place(FeaturePlaceContext<MultifaceGrowthConfiguration> context) {
		WorldGenLevel worldGenLevel = context.level();
		BlockPos blockPos = context.origin();
		RandomSource randomSource = context.random();
		MultifaceGrowthConfiguration multifaceGrowthConfiguration = context.config();
		if (!isAirOrWater(worldGenLevel.getBlockState(blockPos))) {
			return false;
		} else {
			List<Direction> list = multifaceGrowthConfiguration.getShuffledDirections(randomSource);
			if (placeGrowthIfPossible(worldGenLevel, blockPos, worldGenLevel.getBlockState(blockPos), multifaceGrowthConfiguration, randomSource, list)) {
				return true;
			} else {
				BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();

				for(Direction direction : list) {
					mutableBlockPos.set(blockPos);
					List<Direction> list2 = multifaceGrowthConfiguration.getShuffledDirectionsExcept(randomSource, direction.getOpposite());

					for(int i = 0; i < multifaceGrowthConfiguration.searchRange; ++i) {
						mutableBlockPos.setWithOffset(blockPos, direction);
						BlockState blockState = worldGenLevel.getBlockState(mutableBlockPos);
						if (!isAirOrWater(blockState) && !blockState.is(multifaceGrowthConfiguration.placeBlock)) {
							break;
						}

						if (placeGrowthIfPossible(worldGenLevel, mutableBlockPos, blockState, multifaceGrowthConfiguration, randomSource, list2)) {
							return true;
						}
					}
				}

				return false;
			}
		}
	}

	public static boolean placeGrowthIfPossible(
			@NotNull WorldGenLevel level, BlockPos pos, @NotNull BlockState state, @NotNull MultifaceGrowthConfiguration config, @NotNull RandomSource random, List<Direction> directions
	) {
		BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();

		for(Direction direction : directions) {
			BlockState blockState = level.getBlockState(mutableBlockPos.setWithOffset(pos, direction));
			if (blockState.is(config.canBePlacedOn)) {
				BlockState blockState2 = config.placeBlock.getStateForPlacement(state, level, pos, direction);
				if (blockState2 == null) {
					return false;
				}

				level.setBlock(pos, blockState2, 3);
				level.getChunk(pos).markPosForPostprocessing(pos);
				var optional = config.placeBlock.getSpreader().spreadFromFaceTowardRandomDirection(blockState2, level, pos, direction, random, true);
				for (int i = 0; i < random.nextInt(2) + 3; ++i) {
					if (optional.isPresent()) {
						var spreadPos = optional.get();
						optional = config.placeBlock.getSpreader().spreadFromFaceTowardRandomDirection(blockState2, level, spreadPos.pos(), spreadPos.face(), random, true);
					} else {
						break;
					}
				}

				return true;
			}
		}

		return false;
	}

}

