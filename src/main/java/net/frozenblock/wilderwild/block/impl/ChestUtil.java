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

package net.frozenblock.wilderwild.block.impl;

import java.util.Optional;
import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.frozenblock.wilderwild.block.entity.impl.ChestBlockEntityInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.material.Fluids;

public class ChestUtil {

	public static Optional<ChestBlockEntity> getCoupledChestBlockEntity(LevelReader level, BlockPos pos, BlockState state) {
		final BlockPos.MutableBlockPos mutable = pos.mutable();
		final ChestType chestType = state.getValue(ChestBlock.TYPE);
		if (chestType == ChestType.RIGHT) {
			mutable.move(ChestBlock.getConnectedDirection(state));
		} else if (chestType == ChestType.LEFT) {
			mutable.move(ChestBlock.getConnectedDirection(state));
		} else {
			return Optional.empty();
		}
		if (level.getBlockEntity(mutable) instanceof ChestBlockEntity chest) return Optional.of(chest);
		return Optional.empty();
	}

	public static Optional<StoneChestBlockEntity> getCoupledStoneChestBlockEntity(LevelAccessor level, BlockPos pos, BlockState state) {
		final Optional<ChestBlockEntity> possibleCoupledChest = getCoupledChestBlockEntity(level, pos, state);
		if (possibleCoupledChest.isPresent() && possibleCoupledChest.get() instanceof StoneChestBlockEntity stoneChest) return Optional.of(stoneChest);
		return Optional.empty();
	}

	public static void updateBubbles(BlockState oldState, BlockState state, LevelReader level, BlockPos pos) {
		if (!(level.getBlockEntity(pos) instanceof ChestBlockEntity chest && chest instanceof ChestBlockEntityInterface chestInterface)) return;

		final Optional<ChestBlockEntity> possibleCoupledChest = ChestUtil.getCoupledChestBlockEntity(level, pos, state);
		if (possibleCoupledChest.isPresent()) {
			final ChestBlockEntity coupledChest = possibleCoupledChest.get();
			if (coupledChest instanceof ChestBlockEntityInterface coupledChestInterface) {
				final BlockState otherState = level.getBlockState(coupledChest.getBlockPos());
				final boolean wasLogged = oldState.getFluidState().is(Fluids.WATER);

				if (wasLogged != state.getFluidState().is(Fluids.WATER) && wasLogged) {
					if (!otherState.getFluidState().is(Fluids.WATER)) {
						chestInterface.wilderWild$setCanBubble(true);
						coupledChestInterface.wilderWild$setCanBubble(true);
					} else if (!coupledChestInterface.wilderWild$getCanBubble()) {
						chestInterface.wilderWild$setCanBubble(false);
					}
				}
			}
		} else {
			final boolean wasLogged = oldState.getFluidState().is(Fluids.WATER);
			if (wasLogged && !state.getFluidState().is(Fluids.WATER)) chestInterface.wilderWild$setCanBubble(true);
		}
		possibleCoupledChest.ifPresent(chestBlockEntity -> chestInterface.wilderWild$syncBubble(chest, chestBlockEntity));
	}
}
