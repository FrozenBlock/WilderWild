/*
 * Copyright 2024-2025 FrozenBlock
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

package net.frozenblock.wilderwild.block.impl;

import java.util.Optional;
import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.frozenblock.wilderwild.block.entity.impl.ChestBlockEntityInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

public class ChestUtil {

	public static @NotNull Optional<ChestBlockEntity> getCoupledChestBlockEntity(@NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockState state) {
		BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
		ChestType chestType = state.getValue(ChestBlock.TYPE);
		if (chestType == ChestType.RIGHT) {
			mutableBlockPos.move(ChestBlock.getConnectedDirection(state));
		} else if (chestType == ChestType.LEFT) {
			mutableBlockPos.move(ChestBlock.getConnectedDirection(state));
		} else {
			return Optional.empty();
		}
		if (level.getBlockEntity(mutableBlockPos) instanceof ChestBlockEntity chest) {
			return Optional.of(chest);
		}
		return Optional.empty();
	}

	public static @NotNull Optional<StoneChestBlockEntity> getCoupledStoneChestBlockEntity(@NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockState state) {
		Optional<ChestBlockEntity> possibleCoupledChest = getCoupledChestBlockEntity(level, pos, state);
		if (possibleCoupledChest.isPresent()) {
			ChestBlockEntity chest = possibleCoupledChest.get();
			if (chest instanceof StoneChestBlockEntity stoneChestBlockEntity) {
				return Optional.of(stoneChestBlockEntity);
			}
		}
		return Optional.empty();
	}

	public static void updateBubbles(@NotNull BlockState oldState, @NotNull BlockState state, @NotNull LevelAccessor level, @NotNull BlockPos currentPos) {
		if (level.getBlockEntity(currentPos) instanceof ChestBlockEntity chest && chest instanceof ChestBlockEntityInterface chestBlockEntityInterface) {
			Optional<ChestBlockEntity> possibleCoupledChest = ChestUtil.getCoupledChestBlockEntity(level, currentPos, state);

			if (possibleCoupledChest.isPresent()) {
				ChestBlockEntity coupledChest = possibleCoupledChest.get();
				if (coupledChest instanceof ChestBlockEntityInterface coupledChestInterface) {
					BlockState otherState = level.getBlockState(coupledChest.getBlockPos());
					boolean wasLogged = oldState.getFluidState().is(Fluids.WATER);

					if (wasLogged != state.getFluidState().is(Fluids.WATER) && wasLogged) {
						if (!otherState.getFluidState().is(Fluids.WATER)) {
							chestBlockEntityInterface.wilderWild$setCanBubble(true);
							coupledChestInterface.wilderWild$setCanBubble(true);
						} else if (!coupledChestInterface.wilderWild$getCanBubble()) {
							chestBlockEntityInterface.wilderWild$setCanBubble(false);
						}
					}

				}
			} else {
				boolean wasLogged = oldState.getFluidState().is(Fluids.WATER);
				if (wasLogged != state.getFluidState().is(Fluids.WATER) && wasLogged) {
					chestBlockEntityInterface.wilderWild$setCanBubble(true);
				}
			}
			possibleCoupledChest.ifPresent(chestBlockEntity -> chestBlockEntityInterface.wilderWild$syncBubble(chest, chestBlockEntity));
		}
	}
}
