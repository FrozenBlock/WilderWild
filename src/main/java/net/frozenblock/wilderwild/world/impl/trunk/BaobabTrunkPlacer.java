/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.world.impl.trunk;

import com.google.common.collect.Lists;
import com.mojang.datafixers.Products;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.wilderwild.registry.RegisterFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BaobabTrunkPlacer extends TrunkPlacer {
	public static final MapCodec<BaobabTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((instance) ->
		baobabCodec(instance).apply(instance, BaobabTrunkPlacer::new));
	public static final float SIDE_DECORATION_CHANCE = 0.3F;
	public static final float BRANCH_CHANCE = 0.4F;
	public static final float BRANCH_CHANCE_TOP_ADDITIONAL = 0.25F;

	protected final BlockStateProvider insideState;

	public BaobabTrunkPlacer(int i, int j, int k, @NotNull BlockStateProvider insideState) {
		super(i, j, k);
		this.insideState = insideState;
	}

	protected static <P extends BaobabTrunkPlacer> Products.@NotNull P4<RecordCodecBuilder.Mu<P>, Integer, Integer, Integer, BlockStateProvider> baobabCodec(RecordCodecBuilder.Instance<P> builder) {
		return trunkPlacerParts(builder).and((BlockStateProvider.CODEC.fieldOf("inside_state")).forGetter(placer -> placer.insideState));
	}

	private static void terraformDirtBelow(
		@NotNull LevelSimulatedReader level,
		@NotNull BiConsumer<BlockPos, BlockState> replacer,
		@NotNull RandomSource random,
		@NotNull BlockPos startPos,
		@NotNull TreeConfiguration config,
		@NotNull List<BlockPos> logPoses
	) {
		BlockGetter bgLevel = (BlockGetter) level;
		BlockPos.MutableBlockPos pos = startPos.mutable();
		for (int y = 0; true; y++) {
			pos.setWithOffset(startPos, 0, -y, 0);
			if ((!isSolid(bgLevel, pos)) || bgLevel.getBlockState(pos).getBlock() == Blocks.GRASS_BLOCK) {
				setDirtAt(level, replacer, random, pos, config, logPoses);
			} else {
				break;
			}
		}
	}

	private static void setDirtAt(
		@NotNull LevelSimulatedReader level,
		@NotNull BiConsumer<BlockPos, BlockState> blockSetter,
		@NotNull RandomSource random,
		BlockPos pos,
		@NotNull TreeConfiguration config,
		List<BlockPos> logPoses
	) {
		if (config.forceDirt || !isDirt(level, pos)) {
			blockSetter.accept(pos, config.dirtProvider.getState(random, pos));
			logPoses.add(pos);
		}
	}

	private static boolean isSolid(@NotNull BlockGetter level, @NotNull BlockPos pos) {
		BlockState blockState = level.getBlockState(pos);
		return blockState.isFaceSturdy(level, pos, Direction.DOWN);
	}

	@Override
	@NotNull
	protected TrunkPlacerType<?> type() {
		return RegisterFeatures.BAOBAB_TRUNK_PLACER;
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
	@NotNull
	public List<FoliagePlacer.FoliageAttachment> placeTrunk(
		@NotNull LevelSimulatedReader level,
		@NotNull BiConsumer<BlockPos, BlockState> replacer,
		@NotNull RandomSource random,
		int height,
		@NotNull BlockPos startPos,
		@NotNull TreeConfiguration config
	) {
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		BlockPos.MutableBlockPos dirtPos = new BlockPos.MutableBlockPos();
		BlockPos center = new BlockPos(startPos.getX() - 1, startPos.getY(), startPos.getZ() - 1);
		List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
		List<BlockPos> placedLogs = Lists.newArrayList();

		for (int x = 0; x < 4; x++) { // X
			for (int z = 0; z < 4; z++) { // Z
				terraformDirtBelow(level, replacer, random, dirtPos.setWithOffset(center, x, -1, z), config, placedLogs);
				for (int y = 0; y <= height; y++) {
					setLog(level, replacer, random, mutable, config, center, x, y, z, placedLogs);
				}

				if (!AdvancedMath.squareBetween(x, z, 1, 2)) { // only sides
					boolean x0 = x == 0;
					boolean x3 = x == 3;
					Direction dir1 = Direction.WEST;
					Direction dir2 = null;
					if (x0) {
						if (random.nextFloat() <= SIDE_DECORATION_CHANCE) {
							setLogs(level, replacer, random, mutable, config, center, x - 1, 0, z, height / 2, placedLogs);
							setLogs(level, replacer, random, mutable, config, center, x - 2, 0, z, height / 2 - 1, placedLogs);
							terraformDirtBelow(level, replacer, random, dirtPos.setWithOffset(center, x - 1, -1, z), config, placedLogs);
							terraformDirtBelow(level, replacer, random, dirtPos.setWithOffset(center, x - 2, -1, z), config, placedLogs);
						}
					} else if (x3) {
						dir1 = Direction.EAST;
						if (random.nextFloat() <= SIDE_DECORATION_CHANCE) {
							setLogs(level, replacer, random, mutable, config, center, x + 1, 0, z, height / 2, placedLogs);
							setLogs(level, replacer, random, mutable, config, center, x + 2, 0, z, height / 2 - 1, placedLogs);
							terraformDirtBelow(level, replacer, random, dirtPos.setWithOffset(center, x + 1, -1, z), config, placedLogs);
							terraformDirtBelow(level, replacer, random, dirtPos.setWithOffset(center, x + 2, -1, z), config, placedLogs);
						}
					}
					if (z == 0) {
						dir1 = Direction.NORTH;
						if (x0) {
							dir2 = Direction.WEST;
						} else if (x3) {
							dir2 = Direction.EAST;
						}
						if (random.nextFloat() <= SIDE_DECORATION_CHANCE) {
							setLogs(level, replacer, random, mutable, config, center, x, 0, z - 1, height / 2, placedLogs);
							setLogs(level, replacer, random, mutable, config, center, x, 0, z - 2, height / 2 - 1, placedLogs);
							terraformDirtBelow(level, replacer, random, dirtPos.setWithOffset(center, x, -1, z - 1), config, placedLogs);
							terraformDirtBelow(level, replacer, random, dirtPos.setWithOffset(center, x, -1, z - 2), config, placedLogs);
						}
					} else if (z == 3) {
						dir1 = Direction.SOUTH;
						if (x0) {
							dir2 = Direction.WEST;
						} else if (x3) {
							dir2 = Direction.EAST;
						}
						if (random.nextFloat() <= SIDE_DECORATION_CHANCE) {
							setLogs(level, replacer, random, mutable, config, center, x, 0, z + 1, height / 2, placedLogs);
							setLogs(level, replacer, random, mutable, config, center, x, 0, z + 2, height / 2 - 1, placedLogs);
							terraformDirtBelow(level, replacer, random, dirtPos.setWithOffset(center, x, -1, z + 1), config, placedLogs);
							terraformDirtBelow(level, replacer, random, dirtPos.setWithOffset(center, x, -1, z + 2), config, placedLogs);
						}
					}

					if (random.nextFloat() <= BRANCH_CHANCE_TOP_ADDITIONAL) {
						FoliagePlacer.FoliageAttachment attachment = generateBranch(
							dir1,
							dir2,
							1F / 4F,
							height,
							height / 4,
							4,
							level,
							replacer,
							random,
							mutable,
							config,
							center,
							x,
							z,
							placedLogs
						);
						if (attachment != null) {
							list.add(attachment);
						}
					}

					if (random.nextFloat() <= BRANCH_CHANCE) {
						float min = 1F / 3F, max = 1F;
						float p = ((random.nextFloat() * (max - min)) + min);
						FoliagePlacer.FoliageAttachment attachment = generateBranch(
							dir1,
							dir2,
							p,
							height,
							height,
							4,
							level,
							replacer,
							random,
							mutable,
							config,
							center,
							x,
							z,
							placedLogs
						);
						if (attachment != null) {
							list.add(attachment);
						}
					}
				}
			}
		}

		BlockPos.MutableBlockPos placedLogPos = startPos.mutable();
		for (BlockPos pos : placedLogs) {
			boolean isSurrounded = true;
			for (Direction dir : Direction.values()) {
				placedLogPos.set(pos).move(dir);
				if (!placedLogs.contains(placedLogPos)) {
					isSurrounded = false;
				}
			}
			if (isSurrounded) {
				replacer.accept(pos, (BlockState) Function.identity().apply(this.insideState.getState(random, pos)));
			}
		}

		return list;
	}

	@Nullable
	private FoliagePlacer.FoliageAttachment generateBranch(
		@NotNull Direction direction,
		@Nullable Direction direction2,
		float yEquation,
		int h,
		int minh,
		int maxLength,
		@NotNull LevelSimulatedReader level,
		@NotNull BiConsumer<BlockPos, BlockState> replacer,
		@NotNull RandomSource random,
		@NotNull BlockPos.MutableBlockPos mutable,
		@NotNull TreeConfiguration config,
		@NotNull BlockPos startPos,
		int x,
		int z,
		@NotNull List<BlockPos> logPoses
	) {
		int height = (int) ((random.nextDouble() * (h - minh)) + minh);
		BlockPos.MutableBlockPos prevFPos = startPos.mutable();
		BlockPos.MutableBlockPos fPos = startPos.mutable();
		BlockPos.MutableBlockPos fPos2 = startPos.mutable();

		for (int length = 1; length <= maxLength; length++) {
			int eq = (int) Math.floor(yEquation * length);
			prevFPos.set(fPos);
			fPos.set(startPos).move(direction, length);
			if (direction2 != null) {
				fPos.move(direction2, length);
			}
			this.placeLogIfFree(
				level,
				replacer,
				random,
				mutable.setWithOffset(fPos, x, height + eq, z),
				config,
				(state) -> state.trySetValue(RotatedPillarBlock.AXIS, this.getLogAxis(prevFPos, fPos)),
				logPoses
			);
			if (length == maxLength) {
				return new FoliagePlacer.FoliageAttachment(fPos2.setWithOffset(fPos, x, height + eq + 1, z), 0, true);
			}
		}
		return null;
	}

	private void placeLogIfFree(
		@NotNull LevelSimulatedReader level,
		@NotNull BiConsumer<BlockPos, BlockState> blockSetter,
		@NotNull RandomSource random,
		@NotNull BlockPos.MutableBlockPos pos,
		@NotNull TreeConfiguration config,
		Function<BlockState, BlockState> propertySetter,
		@NotNull List<BlockPos> logPoses
	) {
		if (this.isFree(level, pos)) {
			this.placeLog(level, blockSetter, random, pos, config, propertySetter);
			logPoses.add(pos.immutable());
		}
	}

	private void placeLogIfFree(
		@NotNull LevelSimulatedReader level,
		@NotNull BiConsumer<BlockPos, BlockState> blockSetter,
		@NotNull RandomSource random,
		@NotNull BlockPos.MutableBlockPos pos,
		@NotNull TreeConfiguration config,
		@NotNull List<BlockPos> logPoses
	) {
		if (this.isFree(level, pos)) {
			this.placeLog(level, blockSetter, random, pos, config);
			logPoses.add(pos.immutable());
		}
	}

	private void setLog(
		@NotNull LevelSimulatedReader level,
		@NotNull BiConsumer<BlockPos, BlockState> replacer,
		@NotNull RandomSource random,
		@NotNull BlockPos.MutableBlockPos pos,
		@NotNull TreeConfiguration config,
		@NotNull BlockPos startPos,
		int x,
		int y,
		int z,
		boolean condition,
		List<BlockPos> logPoses
	) {
		if (condition) {
			pos.setWithOffset(startPos, x, y, z);
			this.placeLogIfFree(level, replacer, random, pos, config, logPoses);
		}
	}

	private void setLogs(
		@NotNull LevelSimulatedReader level,
		@NotNull BiConsumer<BlockPos, BlockState> replacer,
		@NotNull RandomSource random,
		@NotNull BlockPos.MutableBlockPos pos,
		@NotNull TreeConfiguration config,
		@NotNull BlockPos startPos,
		int x,
		int y,
		int z,
		int height,
		List<BlockPos> logPoses
	) {
		for (int h = 0; h <= height; h++) {
			this.setLog(level, replacer, random, pos, config, startPos, x, y + h, z, logPoses);
		}
	}

	private void setLog(
		@NotNull LevelSimulatedReader level,
		@NotNull BiConsumer<BlockPos, BlockState> replacer,
		@NotNull RandomSource random,
		@NotNull BlockPos.MutableBlockPos pos,
		@NotNull TreeConfiguration config,
		@NotNull BlockPos startPos,
		int x,
		int y,
		int z,
		List<BlockPos> logPoses
	) {
		this.setLog(level, replacer, random, pos, config, startPos, x, y, z, true, logPoses);
	}

	@NotNull
	private Direction.Axis getLogAxis(@NotNull BlockPos pos, @NotNull BlockPos otherPos) {
		Direction.Axis axis = Direction.Axis.Y;
		int i = Math.abs(otherPos.getX() - pos.getX());
		int j = Math.abs(otherPos.getZ() - pos.getZ());
		int k = Math.max(i, j);
		if (k > 0) {
			if (i == k) {
				axis = Direction.Axis.X;
			} else {
				axis = Direction.Axis.Z;
			}
		}

		return axis;
	}
}
