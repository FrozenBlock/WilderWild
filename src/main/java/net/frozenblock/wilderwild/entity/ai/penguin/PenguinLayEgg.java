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

import com.google.common.collect.ImmutableMap;
import net.frozenblock.wilderwild.entity.Penguin;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class PenguinLayEgg extends Behavior<Penguin> {
	private final Block eggBlock;

	public PenguinLayEgg(Block eggBlock) {
		super(ImmutableMap.of(MemoryModuleType.IS_PREGNANT, MemoryStatus.VALUE_PRESENT));
		this.eggBlock = eggBlock;
	}

	private static boolean attemptPlace(Penguin entity, @NotNull Level level, Block block, BlockPos placePos) {
		BlockState blockState = level.getBlockState(placePos);
		BlockPos belowPos = placePos.below();
		BlockState belowState = level.getBlockState(belowPos);
		if (blockState.isAir() && belowState.isFaceSturdy(level, belowPos, Direction.UP)) {
			BlockState placementState = block.defaultBlockState();
			level.setBlockAndUpdate(placePos, placementState);
			level.gameEvent(GameEvent.BLOCK_PLACE, placePos, GameEvent.Context.of(entity, placementState));
			level.playSound(null, entity, entity.isLinux() ? WWSounds.ENTITY_LINUX_LAY_EGG : WWSounds.ENTITY_PENGUIN_LAY_EGG, SoundSource.BLOCKS, 1F, 1F);
			return true;
		}
		return false;
	}

	@Override
	public boolean checkExtraStartConditions(@NotNull ServerLevel level, @NotNull Penguin owner) {
		return true;
	}

	@Override
	public boolean canStillUse(@NotNull ServerLevel level, @NotNull Penguin entity, long gameTime) {
		return entity.isPregnant();
	}

	@Override
	public void start(@NotNull ServerLevel level, @NotNull Penguin entity, long gameTime) {
	}

	@Override
	public void stop(@NotNull ServerLevel level, @NotNull Penguin entity, long gameTime) {
	}

	@Override
	public void tick(@NotNull ServerLevel level, @NotNull Penguin owner, long gameTime) {
		if (!owner.isInWater() && owner.onGround()) {
			BlockPos blockPos = owner.getOnPos().above();
			if (attemptPlace(owner, level, this.eggBlock, blockPos)) {
				owner.revokePregnancy();
				return;
			}

			for (Direction direction : Direction.Plane.HORIZONTAL) {
				if (attemptPlace(owner, level, this.eggBlock, blockPos.relative(direction))) {
					owner.revokePregnancy();
					return;
				}
			}
		}
	}

}
