/*
 * Copyright 2023 FrozenBlock
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

package net.frozenblock.wilderwild.entity.ai.ostrich;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import org.jetbrains.annotations.NotNull;

public class OstrichTryLayEggOnLand {

	public OstrichTryLayEggOnLand() {
	}

	@NotNull
	public static BehaviorControl<LivingEntity> create(Block spawnBlock) {
		return BehaviorBuilder.create((instance) -> instance.group(instance.absent(MemoryModuleType.ATTACK_TARGET), instance.present(MemoryModuleType.WALK_TARGET), instance.present(MemoryModuleType.IS_PREGNANT))
			.apply(instance, (attackTarget, walkTarget, isPregnant) -> (world, livingEntity, l) -> {
			if (!livingEntity.isInWater() && livingEntity.onGround()) {
				BlockPos blockPos = livingEntity.getOnPos();
				if (attemptPlace(livingEntity, world, spawnBlock, blockPos.above())) {
					isPregnant.erase();
					return true;
				}

				for (Direction direction : Plane.HORIZONTAL) {
					if (attemptPlace(livingEntity, world, spawnBlock, blockPos.relative(direction))) {
						isPregnant.erase();
						return true;
					}
				}

				return true;
			} else {
				return false;
			}
		}));
	}

	private static boolean attemptPlace(LivingEntity livingEntity, @NotNull Level level, Block block, BlockPos placePos) {
		BlockState blockState = level.getBlockState(placePos);
		BlockPos belowPos = placePos.below();
		BlockState belowState = level.getBlockState(belowPos);
		if (blockState.isAir() && belowState.isFaceSturdy(level, belowPos, Direction.UP)) {
			BlockState placementState = block.defaultBlockState();
			level.setBlock(placePos, placementState, 3);
			level.gameEvent(GameEvent.BLOCK_PLACE, placePos, Context.of(livingEntity, placementState));
			//TODO: Ostrich lay sounds
			level.playSound(null, livingEntity, SoundEvents.FROG_LAY_SPAWN, SoundSource.BLOCKS, 1.0F, 1.0F);
			return true;
		}
		return false;
	}
}
