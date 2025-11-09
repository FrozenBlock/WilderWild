/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
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
import net.minecraft.util.Util;

public class PenguinFindEscapePos {
	public static @NotNull BehaviorControl<PathfinderMob> create(int searchRange, float speedModifier) {
		MutableLong mutableLong = new MutableLong(0L);
		return BehaviorBuilder.create(
			instance -> instance.group(
					instance.absent(WWMemoryModuleTypes.DIVE_TICKS),
					instance.absent(MemoryModuleType.WALK_TARGET),
					instance.registered(MemoryModuleType.LOOK_TARGET)
			).apply(instance, (diveTicks, walkTarget, lookTarget) -> (serverLevel, pathfinderMob, l) -> {
				if (!serverLevel.getFluidState(pathfinderMob.blockPosition().above()).is(FluidTags.WATER)) return false;
				if (l < mutableLong.getValue()) {
					mutableLong.setValue(l + 60L);
					return true;
				}

				final BlockPos penguinPos = pathfinderMob.blockPosition();
				final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
				final CollisionContext collisionContext = CollisionContext.of(pathfinderMob);

				final List<BlockPos> possiblePoses = shuffleAndOrderByFarthest(penguinPos, searchRange, serverLevel.random);

				boolean foundLand = false;
				for (BlockPos possiblePos : possiblePoses) {
					if (possiblePos.getX() != penguinPos.getX() || possiblePos.getZ() != penguinPos.getZ()) {
						final BlockState state = serverLevel.getBlockState(possiblePos);
						final BlockState belowState = serverLevel.getBlockState(mutableBlockPos.setWithOffset(possiblePos, Direction.DOWN));
						if (!state.is(Blocks.WATER)
							&& serverLevel.getFluidState(possiblePos).isEmpty()
							&& state.getCollisionShape(serverLevel, possiblePos, collisionContext).isEmpty()
							&& belowState.isFaceSturdy(serverLevel, mutableBlockPos, Direction.UP)
						) {
							final BlockPos aboveState = possiblePos.immutable().above();
							lookTarget.set(new BlockPosTracker(aboveState));
							walkTarget.set(new WalkTarget(new BlockPosTracker(aboveState), speedModifier, 1));
							foundLand = true;
							break;
						}
					}
				}

				boolean foundWater = false;
				if (!foundLand) {
					for (BlockPos possiblePos : possiblePoses) {
						if (possiblePos.getX() != penguinPos.getX() || possiblePos.getZ() != penguinPos.getZ()) {
							final BlockState state = serverLevel.getBlockState(possiblePos);
							final BlockState aboveState = serverLevel.getBlockState(mutableBlockPos.setWithOffset(possiblePos, Direction.UP));
							if (!state.is(Blocks.WATER) || !aboveState.isAir()) continue;

							final BlockPos shallowPos = possiblePos.immutable().relative(Direction.UP, 3);
							lookTarget.set(new BlockPosTracker(shallowPos));
							walkTarget.set(new WalkTarget(new BlockPosTracker(shallowPos), speedModifier, 1));
							foundWater = true;
							break;
						}
					}
				}

				if (!foundWater) {
					BlockPos severeEscapePos = penguinPos.relative(Direction.UP, 3);
					lookTarget.set(new BlockPosTracker(severeEscapePos));
					walkTarget.set(new WalkTarget(new BlockPosTracker(severeEscapePos), speedModifier, 1));
					mutableLong.setValue(l + 20L);
					return true;
				}
				mutableLong.setValue(l + 60L);return true;
			})
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
