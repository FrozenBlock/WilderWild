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
	protected void makeCap(
		LevelAccessor levelAccessor,
		RandomSource randomSource,
		BlockPos blockPos,
		int i,
		BlockPos.MutableBlockPos mutableBlockPos,
		@NotNull HugeMushroomFeatureConfiguration hugeMushroomFeatureConfiguration
	) {
		int radius = hugeMushroomFeatureConfiguration.foliageRadius;

		for (int x = -radius; x <= radius; x++) {
			for (int l = -radius; l <= radius; l++) {
				boolean negX = x == -radius;
				boolean posX = x == radius;
				boolean bl3 = l == -radius;
				boolean bl4 = l == radius;
				boolean bl5 = negX || posX;
				boolean bl6 = bl3 || bl4;
				if (!bl5 || !bl6) {
					mutableBlockPos.setWithOffset(blockPos, x, i, l);
					if (!levelAccessor.getBlockState(mutableBlockPos).isSolidRender()) {
						boolean west = negX || bl6 && x == 1 - radius;
						boolean east = posX || bl6 && x == radius - 1;
						boolean north = bl3 || bl5 && l == 1 - radius;
						boolean south = bl4 || bl5 && l == radius - 1;
						BlockState blockState = hugeMushroomFeatureConfiguration.capProvider.getState(randomSource, blockPos);
						if (blockState.hasProperty(HugeMushroomBlock.WEST)
							&& blockState.hasProperty(HugeMushroomBlock.EAST)
							&& blockState.hasProperty(HugeMushroomBlock.NORTH)
							&& blockState.hasProperty(HugeMushroomBlock.SOUTH)) {
							blockState = blockState.setValue(HugeMushroomBlock.WEST, west)
								.setValue(HugeMushroomBlock.EAST, east)
								.setValue(HugeMushroomBlock.NORTH, north)
								.setValue(HugeMushroomBlock.SOUTH, south);
						}

						this.setBlock(levelAccessor, mutableBlockPos, blockState);
					}
				}
			}
		}
	}

	@Override
	protected int getTreeRadiusForHeight(int i, int j, int radius, int height) {
		int finalRadius = 0;
		if (height < j && height >= j - 3) {
			finalRadius = radius;
		} else if (height == j) {
			finalRadius = radius;
		}

		return finalRadius;
	}
}
