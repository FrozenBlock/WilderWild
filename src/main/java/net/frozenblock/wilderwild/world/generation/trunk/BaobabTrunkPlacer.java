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

package net.frozenblock.wilderwild.world.generation.trunk;

import com.google.common.collect.Lists;
import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BaobabTrunkPlacer extends TrunkPlacer {
    public static final Codec<BaobabTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) ->
			baobabCodec(instance).apply(instance, BaobabTrunkPlacer::new));

	protected static <P extends BaobabTrunkPlacer> Products.P4<RecordCodecBuilder.Mu<P>, Integer, Integer, Integer, BlockStateProvider> baobabCodec(RecordCodecBuilder.Instance<P> builder) {
		return trunkPlacerParts(builder).and((BlockStateProvider.CODEC.fieldOf("inside_block_state")).forGetter(placer -> placer.insideBlockState));
	}

	final BlockStateProvider insideBlockState;

    public BaobabTrunkPlacer(int i, int j, int k, BlockStateProvider insideBlockState) {
        super(i, j, k);
		this.insideBlockState = insideBlockState;
    }

    protected TrunkPlacerType<?> type() {
        return WilderWild.BAOBAB_TRUNK_PLACER;
    }


    /**
     * Baobab Tree Generator
     * Made By LiukRast (Yes im alive!)
     * Process:
     * 1- Generate the main trunk (Just a big parallelepiped)
     * 2- Add Roots (External Vertical Parts)
     * - Step 1: Choose how many of them should be generated (Make a list and then remove random members)
     * - Step 2: Generate them
     * 3- Add Branches and Foliage
     * Easy, I guess ._.
     **/

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(@NotNull LevelSimulatedReader level, @NotNull BiConsumer<BlockPos, BlockState> replacer, @NotNull RandomSource random, int height, @NotNull BlockPos startPos, @NotNull TreeConfiguration config) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        BlockPos center = new BlockPos(startPos.getX() - 1, startPos.getY(), startPos.getZ() - 1);
        List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
		List<BlockPos> placedLogs = Lists.newArrayList();

        double percentage = 30;
        double branchpercentage = 40;
        float toppercentage = 25;

        for (int x = 0; x < 4; x++) { // X
            for (int z = 0; z < 4; z++) { // Z

                terraformDirtBelow(level, replacer, random, new BlockPos(center.getX() + x, startPos.getY() - 1, center.getZ() + z), config, placedLogs);
                for (int y = 0; y <= height; y++) {
                    setLog(level, replacer, random, mutable, config, center, x, y, z, placedLogs);
                }


                if (!AdvancedMath.squareBetween(x, z, 1, 2)) { // only sides
                    if (random.nextDouble() <= percentage / 100) {
						switch (x) {
							case 0 -> {
								setLogs(level, replacer, random, mutable, config, center, x - 1, 0, z, height / 2, placedLogs);
								setLogs(level, replacer, random, mutable, config, center, x - 2, 0, z, height / 2 - 1, placedLogs);
								terraformDirtBelow(level, replacer, random, new BlockPos(center.getX() + x - 1, startPos.getY() - 1, center.getZ() + z), config, placedLogs);
								terraformDirtBelow(level, replacer, random, new BlockPos(center.getX() + x - 2, startPos.getY() - 1, center.getZ() + z), config, placedLogs);
							}
							case 3 -> {
								setLogs(level, replacer, random, mutable, config, center, x + 1, 0, z, height / 2, placedLogs);
								setLogs(level, replacer, random, mutable, config, center, x + 2, 0, z, height / 2 - 1, placedLogs);
								terraformDirtBelow(level, replacer, random, new BlockPos(center.getX() + x + 1, startPos.getY() - 1, center.getZ() + z), config, placedLogs);
								terraformDirtBelow(level, replacer, random, new BlockPos(center.getX() + x + 2, startPos.getY() - 1, center.getZ() + z), config, placedLogs);
							}
						}
						switch (z) {
							case 0 -> {
								setLogs(level, replacer, random, mutable, config, center, x, 0, z - 1, height / 2, placedLogs);
								setLogs(level, replacer, random, mutable, config, center, x, 0, z - 2, height / 2 - 1, placedLogs);
								terraformDirtBelow(level, replacer, random, new BlockPos(center.getX() + x, startPos.getY() - 1, center.getZ() + z - 1), config, placedLogs);
								terraformDirtBelow(level, replacer, random, new BlockPos(center.getX() + x, startPos.getY() - 1, center.getZ() + z - 2), config, placedLogs);
							}
							case 3 -> {
								setLogs(level, replacer, random, mutable, config, center, x, 0, z + 1, height / 2, placedLogs);
								setLogs(level, replacer, random, mutable, config, center, x, 0, z + 2, height / 2 - 1, placedLogs);
								terraformDirtBelow(level, replacer, random, new BlockPos(center.getX() + x, startPos.getY() - 1, center.getZ() + z + 1), config, placedLogs);
								terraformDirtBelow(level, replacer, random, new BlockPos(center.getX() + x, startPos.getY() - 1, center.getZ() + z + 2), config, placedLogs);
							}
						}
                    }

                    Direction dir1 = Direction.WEST;
                    Direction dir2 = null;

                    if (x == 3) {
                        dir1 = Direction.EAST;
                    }
                    if (z == 0) {
                        dir1 = Direction.NORTH;
                    }
                    if (z == 3) {
                        dir1 = Direction.SOUTH;
                    }
                    if (x == 0 && z == 0) {
                        dir1 = Direction.WEST;
                        dir2 = Direction.NORTH;
                    }
                    if (x == 3 && z == 0) {
                        dir1 = Direction.EAST;
                        dir2 = Direction.NORTH;
                    }
                    if (x == 0 && z == 3) {
                        dir1 = Direction.WEST;
                        dir2 = Direction.SOUTH;
                    }
                    if (x == 3 && z == 3) {
                        dir1 = Direction.EAST;
                        dir2 = Direction.SOUTH;
                    }
                    if (random.nextDouble() <= toppercentage / 100) {
                        var attachment = generateBranch(dir1, dir2, 1F / 4F, height, height / 4, 4, level, replacer, random, mutable, config, center, x, z, placedLogs);
                        if (attachment != null) list.add(attachment);
                    }
                    if (random.nextDouble() <= branchpercentage / 100) {
                        float min = 1F / 3F, max = 1F;
                        float p = ((random.nextFloat() * (max - min)) + min);
                        var attachment = generateBranch(dir1, dir2, p, height, height, 4, level, replacer, random, mutable, config, center, x, z, placedLogs);
                        if (attachment != null) list.add(attachment);
                    }
                }
            }
        }

		BlockPos.MutableBlockPos placedLogPos = startPos.mutable();
		for (BlockPos pos : placedLogs) {
			boolean isSurrounded = true;
			for (Direction dir : Direction.values()) {
				placedLogPos.set(pos.relative(dir));
				if (!placedLogs.contains(placedLogPos)) {
					isSurrounded = false;
				}
			}
			if (isSurrounded) {
				replacer.accept(pos, (BlockState) Function.identity().apply(this.insideBlockState.getState(random, pos)));
			}
		}

        return list;
    }

    @Nullable
    private FoliagePlacer.FoliageAttachment generateBranch(Direction dir1, Direction dir2, float yequation, int h, int minh, int l, LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos.MutableBlockPos mutable, TreeConfiguration config, BlockPos startPos, int x, int z, List<BlockPos> logPoses) {
        int height = (int) ((random.nextDouble() * (h - minh)) + minh);

        for (int l1 = 1; l1 <= l; l1++) {
            int eq = (int) Math.floor(yequation * l1);

            BlockPos fpos = AdvancedMath.offset(startPos, dir1, l1);
            if (dir2 != null) fpos = AdvancedMath.offset(fpos, dir2, l1);

            BlockPos fpos2 = new BlockPos(fpos.getX() + x, fpos.getY() + height + eq + 1, fpos.getZ() + z);
            setLog(level, replacer, random, mutable, config, fpos, x, height + eq, z, logPoses);

            if (l1 == l)
                return new FoliagePlacer.FoliageAttachment(fpos2, 0, true);
        }
        return null;
    }

	private void placeLogIfFree(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, BlockPos.MutableBlockPos pos, TreeConfiguration config, List<BlockPos> logPoses) {
		if (this.isFree(level, pos)) {
			this.placeLog(level, blockSetter, random, pos, config);
			logPoses.add(pos.immutable());
		}
	}

    private void setLog(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos.MutableBlockPos pos, TreeConfiguration config, BlockPos startPos, int x, int y, int z, boolean condition, List<BlockPos> logPoses) {
        if (condition) {
            pos.setWithOffset(startPos, x, y, z);
            this.placeLogIfFree(level, replacer, random, pos, config, logPoses);
        }
    }

    private void setLogs(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos.MutableBlockPos pos, TreeConfiguration config, BlockPos startPos, int x, int y, int z, int height, List<BlockPos> logPoses) {
        for (int h = 0; h <= height; h++) {
			this.setLog(level, replacer, random, pos, config, startPos, x, y + h, z, logPoses);
        }
    }

    private void setLog(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos.MutableBlockPos pos, TreeConfiguration config, BlockPos startPos, int x, int y, int z, List<BlockPos> logPoses) {
		this.setLog(level, replacer, random, pos, config, startPos, x, y, z, true, logPoses);
    }

    private static void terraformDirtBelow(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> replacer, RandomSource random, BlockPos startPos, TreeConfiguration config, List<BlockPos> logPoses) {
        BlockGetter bgLevel = (BlockGetter) level;

        for (int y = 0; true; y++) {
            if ((!isSolid(bgLevel, startPos.below(y))) || bgLevel.getBlockState(startPos.below(y)).getBlock() == Blocks.GRASS_BLOCK) {
                setDirtAt(level, replacer, random, startPos.below(y), config, logPoses);
            } else {
                break;
            }
        }
    }

	private static void setDirtAt(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, BlockPos pos, TreeConfiguration config, List<BlockPos> logPoses) {
		if (config.forceDirt || !isDirt(level, pos)) {
			blockSetter.accept(pos, config.dirtProvider.getState(random, pos));
			logPoses.add(pos);
		}
	}

    private static boolean isSolid(BlockGetter level, BlockPos pos) {
        BlockState blockState = level.getBlockState(pos);
        return blockState.isFaceSturdy(level, pos, Direction.DOWN);
    }
}
