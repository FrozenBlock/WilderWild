package net.frozenblock.wilderwild.world.feature.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.AbstractHugeMushroomFeature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

public class JellyshroomFeature extends AbstractHugeMushroomFeature {
    public JellyshroomFeature(Codec<HugeMushroomFeatureConfiguration> codec) {
        super(codec);
    }

    protected void makeCap(LevelAccessor levelAccessor, RandomSource randomSource, BlockPos blockPos, int i, BlockPos.MutableBlockPos mutableBlockPos, HugeMushroomFeatureConfiguration hugeMushroomFeatureConfiguration) {
        for(int j = i - 1; j <= i; ++j) {
            int k = j < i ? hugeMushroomFeatureConfiguration.foliageRadius : hugeMushroomFeatureConfiguration.foliageRadius - 1;
            int l = hugeMushroomFeatureConfiguration.foliageRadius - 2;

            for(int m = -k; m <= k; ++m) {
                for(int n = -k; n <= k; ++n) {
                    boolean bl = m == -k;
                    boolean bl2 = m == k;
                    boolean bl3 = n == -k;
                    boolean bl4 = n == k;
                    boolean bl5 = bl || bl2;
                    boolean bl6 = bl3 || bl4;
                    if (j >= i || bl5 != bl6) {
                        mutableBlockPos.setWithOffset(blockPos, m, j, n);
                        if (!levelAccessor.getBlockState(mutableBlockPos).isSolidRender(levelAccessor, mutableBlockPos)) {
                            BlockState blockState = hugeMushroomFeatureConfiguration.capProvider.getState(randomSource, blockPos);
                            if (blockState.hasProperty(HugeMushroomBlock.WEST) && blockState.hasProperty(HugeMushroomBlock.EAST) && blockState.hasProperty(HugeMushroomBlock.NORTH) && blockState.hasProperty(HugeMushroomBlock.SOUTH) && blockState.hasProperty(HugeMushroomBlock.UP)) {
                                blockState = blockState.setValue(HugeMushroomBlock.UP, j >= i - 1).setValue(HugeMushroomBlock.WEST, m < -l).setValue(HugeMushroomBlock.EAST, m > l).setValue(HugeMushroomBlock.NORTH, n < -l).setValue(HugeMushroomBlock.SOUTH, n > l);
                            }

                            this.setBlock(levelAccessor, mutableBlockPos, blockState);
                        }
                    }
                }
            }
        }

    }

    protected int getTreeRadiusForHeight(int i, int j, int k, int l) {
        int m = 0;
        if (l < j && l >= j) {
            m = k;
        } else if (l == j) {
            m = k;
        }

        return m;
    }
}
