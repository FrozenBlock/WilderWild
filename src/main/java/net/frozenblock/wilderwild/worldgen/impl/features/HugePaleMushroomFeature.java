package net.frozenblock.wilderwild.worldgen.impl.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.AbstractHugeMushroomFeature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import org.jetbrains.annotations.NotNull;

public class HugePaleMushroomFeature extends AbstractHugeMushroomFeature {

	public HugePaleMushroomFeature(Codec<HugeMushroomFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	protected int getTreeHeight(@NotNull RandomSource randomSource) {
		return randomSource.nextInt(3) + 4;
	}

	@Override
	protected void makeCap(
		LevelAccessor levelAccessor,
		RandomSource randomSource,
		BlockPos blockPos,
		int height,
		BlockPos.MutableBlockPos mutableBlockPos,
		HugeMushroomFeatureConfiguration hugeMushroomFeatureConfiguration
	) {
		for (int j = height - 1; j <= height + 1; j++) {
			int radius = j < height + 1 ? hugeMushroomFeatureConfiguration.foliageRadius : hugeMushroomFeatureConfiguration.foliageRadius - 1;
			int withinRadius = hugeMushroomFeatureConfiguration.foliageRadius - 2;

			for (int m = -radius; m <= radius; m++) {
				for (int n = -radius; n <= radius; n++) {
					boolean onNegX = m == -radius;
					boolean onPosX = m == radius;
					boolean onNegZ = n == -radius;
					boolean onPosZ = n == radius;
					boolean onX = onNegX || onPosX;
					boolean onZ = onNegZ || onPosZ;
					boolean onCorner = onX && onZ;
					boolean onEdge = onX || onZ;
					if (j >= height + 1 || ((onX != onZ) || (j == height && !onCorner))) {
						mutableBlockPos.setWithOffset(blockPos, m, j, n);
						if (!levelAccessor.getBlockState(mutableBlockPos).isSolidRender()) {
							BlockState blockState = hugeMushroomFeatureConfiguration.capProvider.getState(randomSource, blockPos);
							if (blockState.hasProperty(HugeMushroomBlock.WEST)
								&& blockState.hasProperty(HugeMushroomBlock.EAST)
								&& blockState.hasProperty(HugeMushroomBlock.NORTH)
								&& blockState.hasProperty(HugeMushroomBlock.SOUTH)
								&& blockState.hasProperty(HugeMushroomBlock.UP)
							) {
								boolean hasUpState = j >= height + 1 || (onEdge && j == height);
								blockState = blockState
									.setValue(HugeMushroomBlock.UP, hasUpState)
									.setValue(HugeMushroomBlock.WEST, m < -withinRadius)
									.setValue(HugeMushroomBlock.EAST, m > withinRadius)
									.setValue(HugeMushroomBlock.NORTH, n < -withinRadius)
									.setValue(HugeMushroomBlock.SOUTH, n > withinRadius);
							}

							this.setBlock(levelAccessor, mutableBlockPos, blockState);
						}
					}
				}
			}
		}
	}

	@Override
	protected int getTreeRadiusForHeight(int i, int j, int k, int l) {
		int m = 0;
		if (l < j + 1 && l >= j - 1) {
			m = k;
		} else if (l == j) {
			m = k;
		}

		return m;
	}
}
