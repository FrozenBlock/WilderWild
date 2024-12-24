package net.frozenblock.wilderwild.entity.ai.penguin;

import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
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

							boolean foundLand = false;
							for (BlockPos blockPos2 : BlockPos.withinManhattan(blockPos, searchRange, searchRange, searchRange)) {
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
								for (BlockPos blockPos2 : BlockPos.withinManhattan(blockPos, searchRange, searchRange, searchRange)) {
									if (blockPos2.getX() != blockPos.getX() || blockPos2.getZ() != blockPos.getZ()) {
										BlockState blockState = serverLevel.getBlockState(blockPos2);
										BlockState aboveState = serverLevel.getBlockState(mutableBlockPos.setWithOffset(blockPos2, Direction.UP));

										if (blockState.is(Blocks.WATER)) {
											if (aboveState.isAir()) {
												BlockPos shallowPos = blockPos2.immutable().above();
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
}
