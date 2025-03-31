/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.entity.ai.penguin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.apache.commons.lang3.mutable.MutableLong;
import org.jetbrains.annotations.NotNull;
import net.minecraft.Util;

public class PenguinFindEscapePos {
	public static @NotNull BehaviorControl<PathfinderMob> create(int searchRange, float speedModifier) {
		MutableLong mutableLong = new MutableLong(0L);
		return BehaviorBuilder.create(
			instance -> instance.group(
					instance.absent(WWMemoryModuleTypes.DIVE_TICKS),
					instance.absent(MemoryModuleType.WALK_TARGET),
					instance.registered(MemoryModuleType.LOOK_TARGET)
				)
				.apply(
					instance,
					(
						diveTicks,
						walkTarget,
						lookTarget
					) -> (serverLevel, pathfinderMob, l) -> {
						if (!serverLevel.getFluidState(pathfinderMob.blockPosition().above()).is(FluidTags.WATER)) {
							return false;
						} else if (l < mutableLong.getValue()) {
							mutableLong.setValue(l + 60L);
							return true;
						} else {
							BlockPos blockPos = pathfinderMob.blockPosition();
							BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
							CollisionContext collisionContext = CollisionContext.of(pathfinderMob);

							List<BlockPos> possiblePoses = shuffleAndOrderByFarthest(blockPos, searchRange, serverLevel.random);

							boolean foundLand = false;
							for (BlockPos blockPos2 : possiblePoses) {
								if (blockPos2.getX() != blockPos.getX() || blockPos2.getZ() != blockPos.getZ()) {
									BlockState blockState = serverLevel.getBlockState(blockPos2);
									BlockState blockState2 = serverLevel.getBlockState(mutableBlockPos.setWithOffset(blockPos2, Direction.DOWN));
									if (!blockState.is(Blocks.WATER)
										&& serverLevel.getFluidState(blockPos2).isEmpty()
										&& blockState.getCollisionShape(serverLevel, blockPos2, collisionContext).isEmpty()
										&& blockState2.isFaceSturdy(serverLevel, mutableBlockPos, Direction.UP)
									) {
										BlockPos blockPos3 = blockPos2.immutable().above();
										lookTarget.set(new BlockPosTracker(blockPos3));
										walkTarget.set(new WalkTarget(new BlockPosTracker(blockPos3), speedModifier, 1));
										foundLand = true;
										break;
									}
								}
							}

							boolean foundWater = false;
							if (!foundLand) {
								for (BlockPos blockPos2 : possiblePoses) {
									if (blockPos2.getX() != blockPos.getX() || blockPos2.getZ() != blockPos.getZ()) {
										BlockState blockState = serverLevel.getBlockState(blockPos2);
										BlockState aboveState = serverLevel.getBlockState(mutableBlockPos.setWithOffset(blockPos2, Direction.UP));

										if (blockState.is(Blocks.WATER)) {
											if (aboveState.isAir()) {
												BlockPos shallowPos = blockPos2.immutable().relative(Direction.UP, 3);
												lookTarget.set(new BlockPosTracker(shallowPos));
												walkTarget.set(new WalkTarget(new BlockPosTracker(shallowPos), speedModifier, 1));
												foundWater = true;
												break;
											}
										}
									}
								}
							}

							if (!foundWater) {
								BlockPos severeEscapePos = blockPos.relative(Direction.UP, 3);
								lookTarget.set(new BlockPosTracker(severeEscapePos));
								walkTarget.set(new WalkTarget(new BlockPosTracker(severeEscapePos), speedModifier, 1));
								mutableLong.setValue(l + 20L);
								return true;
							}

							mutableLong.setValue(l + 60L);
							return true;
						}
					}
				)
		);
	}

	public static List<BlockPos> shuffleAndOrderByFarthest(BlockPos pos, int range, RandomSource random) {
		List<BlockPos> poses = new ArrayList<>();
		BlockPos.withinManhattan(pos, range, range, range).forEach(poses::add);
		poses = Util.toShuffledList(poses.stream(), random);

		return poses.stream()
			.sorted(Comparator.comparingInt(blockPos2 -> range - blockPos2.distManhattan(pos)))
			.collect(Collectors.toList());
	}
}
