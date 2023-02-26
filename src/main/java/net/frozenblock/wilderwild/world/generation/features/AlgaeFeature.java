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
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;

public class AlgaeFeature extends Feature<ProbabilityFeatureConfiguration> {

    public AlgaeFeature(Codec<ProbabilityFeatureConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<ProbabilityFeatureConfiguration> context) {
        boolean bl = false;
        BlockPos blockPos = context.origin();
        WorldGenLevel level = context.level();
        BlockPos s = blockPos.atY(level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, blockPos.getX(), blockPos.getZ()));
        int y = s.getY();
        RandomSource random = level.getRandom();
        int radius = random.nextIntBetweenInclusive(4, 10);
        //DISK
        BlockPos.MutableBlockPos mutableDisk = s.mutable();
        int bx = s.getX();
        int bz = s.getZ();
        for (int x = bx - radius; x <= bx + radius; x++) {
            for (int z = bz - radius; z <= bz + radius; z++) {
                double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)));
                if (distance < radius * radius) {
                    mutableDisk.set(x, y, z);
                    boolean fade = !mutableDisk.closerThan(s, radius * 0.8);
                    boolean hasGeneratedThisRound = false;
                    if (level.getBlockState(mutableDisk.below()).is(Blocks.WATER) && level.getFluidState(mutableDisk).isEmpty() && level.getBlockState(mutableDisk).isAir()) {
                        if (random.nextFloat() > 0.2F) {
                            hasGeneratedThisRound = true;
                            if (fade) {
                                if (random.nextFloat() > 0.5F) {
                                    level.setBlock(mutableDisk, RegisterBlocks.ALGAE.defaultBlockState(), 3);
                                    bl = true;
                                }
                            } else {
                                level.setBlock(mutableDisk, RegisterBlocks.ALGAE.defaultBlockState(), 3);
                                bl = true;
                            }
                        }
                    } else {
                        for (int aY = 0; aY < 3; aY++) {
                            mutableDisk.set(x, y + aY, z);
                            if (level.getBlockState(mutableDisk.below()).is(Blocks.WATER) && level.getFluidState(mutableDisk).isEmpty() && level.getBlockState(mutableDisk).isAir()) {
                                hasGeneratedThisRound = true;
                                if (random.nextFloat() > 0.2F) {
                                    if (fade) {
                                        if (random.nextFloat() > 0.5F) {
                                            level.setBlock(mutableDisk, RegisterBlocks.ALGAE.defaultBlockState(), 3);
                                            bl = true;
                                        }
                                    } else {
                                        level.setBlock(mutableDisk, RegisterBlocks.ALGAE.defaultBlockState(), 3);
                                        bl = true;
                                    }
                                }
                            }
                        }
                    }
                    if (!hasGeneratedThisRound) {
                        for (int aY = -3; aY < 0; aY++) {
                            mutableDisk.set(x, y + aY, z);
                            if (level.getBlockState(mutableDisk.below()).is(Blocks.WATER) && level.getFluidState(mutableDisk).isEmpty() && level.getBlockState(mutableDisk).isAir()) {
								if (random.nextFloat() > 0.2F) {
                                    if (fade) {
                                        if (random.nextFloat() > 0.5F) {
                                            level.setBlock(mutableDisk, RegisterBlocks.ALGAE.defaultBlockState(), 3);
                                            bl = true;
                                        }
                                    } else {
                                        level.setBlock(mutableDisk, RegisterBlocks.ALGAE.defaultBlockState(), 3);
                                        bl = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return bl;
    }

}
