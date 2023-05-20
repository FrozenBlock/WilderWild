/*
 * Copyright 2022-2023 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.world.generation.features;

import com.mojang.serialization.Codec;
import java.util.Iterator;
import net.frozenblock.wilderwild.block.WaterloggableTallFlowerBlock;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import org.jetbrains.annotations.NotNull;

public class CattailFeature extends Feature<ProbabilityFeatureConfiguration> {

    public CattailFeature(@NotNull Codec<ProbabilityFeatureConfiguration> codec) {
        super(codec);
    }

	@Override
    public boolean place(@NotNull FeaturePlaceContext<ProbabilityFeatureConfiguration> context) {
        boolean generated = false;
        RandomSource random = context.random();
        WorldGenLevel level = context.level();
        BlockPos blockPos = context.origin();
		int posX = blockPos.getX();
		int posZ = blockPos.getZ();
		int maxHeight = level.getMaxBuildHeight() - 1;
		BlockPos.MutableBlockPos bottomBlockPos = blockPos.mutable();
		BlockPos.MutableBlockPos topBlockPos = blockPos.mutable();

        for (int l = 0; l < random.nextIntBetweenInclusive(12, 21); l++) {
            int randomX = random.nextIntBetweenInclusive(-7, 7);
            int randomZ = random.nextIntBetweenInclusive(-7, 7);
			int newX = posX + randomX;
			int newZ = posZ + randomZ;
            int oceanFloorY = level.getHeight(Types.OCEAN_FLOOR, newX, newZ);
			if (oceanFloorY < maxHeight - 1) {
				bottomBlockPos.set(newX, oceanFloorY, newZ);
				BlockState bottomState = level.getBlockState(bottomBlockPos);
				boolean bottomStateIsWater = bottomState.is(Blocks.WATER);
				BlockState bottomPlaceState = RegisterBlocks.CATTAIL.defaultBlockState();
				topBlockPos.set(bottomBlockPos).move(Direction.UP);
				BlockState topState = level.getBlockState(topBlockPos);
				if ((bottomState.isAir() || bottomStateIsWater) && topState.isAir() && bottomState.canSurvive(level, bottomBlockPos) && (bottomStateIsWater || isWaterNearby(level, bottomBlockPos, 2))) {
					bottomPlaceState = bottomPlaceState.setValue(WaterloggableTallFlowerBlock.WATERLOGGED, bottomStateIsWater);
					level.setBlock(bottomBlockPos, bottomPlaceState, 2);
					level.setBlock(topBlockPos, RegisterBlocks.CATTAIL.defaultBlockState().setValue(WaterloggableTallFlowerBlock.HALF, DoubleBlockHalf.UPPER), 2);
					generated = true;
				}
			}
        }

        return generated;
    }

    public static boolean isWaterNearby(@NotNull WorldGenLevel level, @NotNull BlockPos blockPos, int x) {
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
