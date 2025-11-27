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

public class PenguinLayEgg extends Behavior<Penguin> {
	private final Block eggBlock;

	public PenguinLayEgg(Block eggBlock) {
		super(ImmutableMap.of(MemoryModuleType.IS_PREGNANT, MemoryStatus.VALUE_PRESENT));
		this.eggBlock = eggBlock;
	}

	private static boolean attemptPlace(Penguin penguin, Level level, Block block, BlockPos placePos) {
		final BlockState state = level.getBlockState(placePos);
		final BlockPos belowPos = placePos.below();
		final BlockState belowState = level.getBlockState(belowPos);
		if (state.isAir() && belowState.isFaceSturdy(level, belowPos, Direction.UP)) {
			final BlockState placementState = block.defaultBlockState();
			level.setBlockAndUpdate(placePos, placementState);
			level.gameEvent(GameEvent.BLOCK_PLACE, placePos, GameEvent.Context.of(penguin, placementState));
			level.playSound(null, penguin, penguin.isLinux() ? WWSounds.ENTITY_LINUX_LAY_EGG : WWSounds.ENTITY_PENGUIN_LAY_EGG, SoundSource.BLOCKS, 1F, 1F);
			return true;
		}
		return false;
	}

	@Override
	public boolean checkExtraStartConditions(ServerLevel level, Penguin owner) {
		return true;
	}

	@Override
	public boolean canStillUse(ServerLevel level, Penguin penguin, long gameTime) {
		return penguin.isPregnant();
	}

	@Override
	public void start(ServerLevel level, Penguin penguin, long gameTime) {
	}

	@Override
	public void stop(ServerLevel level, Penguin penguin, long gameTime) {
	}

	@Override
	public void tick(ServerLevel level, Penguin penguin, long gameTime) {
		if (penguin.isInWater() || !penguin.onGround()) return;

		final BlockPos pos = penguin.getOnPos().above();
		if (attemptPlace(penguin, level, this.eggBlock, pos)) {
			penguin.revokePregnancy();
			return;
		}

		for (Direction direction : Direction.Plane.HORIZONTAL) {
			if (!attemptPlace(penguin, level, this.eggBlock, pos.relative(direction))) continue;
			penguin.revokePregnancy();
			return;
		}
	}

}
