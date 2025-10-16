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
import org.jetbrains.annotations.NotNull;

public class ChestUtil {

	public static @NotNull Optional<ChestBlockEntity> getCoupledChestBlockEntity(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
		final BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
		final ChestType chestType = state.getValue(ChestBlock.TYPE);
		if (chestType == ChestType.RIGHT) {
			mutableBlockPos.move(ChestBlock.getConnectedDirection(state));
		} else if (chestType == ChestType.LEFT) {
			mutableBlockPos.move(ChestBlock.getConnectedDirection(state));
		} else {
			return Optional.empty();
		}
		if (level.getBlockEntity(mutableBlockPos) instanceof ChestBlockEntity chest) return Optional.of(chest);
		return Optional.empty();
	}

	public static @NotNull Optional<StoneChestBlockEntity> getCoupledStoneChestBlockEntity(@NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockState state) {
		final Optional<ChestBlockEntity> possibleCoupledChest = getCoupledChestBlockEntity(level, pos, state);
		if (possibleCoupledChest.isPresent()) {
			if (possibleCoupledChest.get() instanceof StoneChestBlockEntity stoneChestBlockEntity) return Optional.of(stoneChestBlockEntity);
		}
		return Optional.empty();
	}

	public static void updateBubbles(@NotNull BlockState oldState, @NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos currentPos) {
		if (!(level.getBlockEntity(currentPos) instanceof ChestBlockEntity chest && chest instanceof ChestBlockEntityInterface chestBlockEntityInterface)) return;

		final Optional<ChestBlockEntity> possibleCoupledChest = ChestUtil.getCoupledChestBlockEntity(level, currentPos, state);
		if (possibleCoupledChest.isPresent()) {
			final ChestBlockEntity coupledChest = possibleCoupledChest.get();
			if (coupledChest instanceof ChestBlockEntityInterface coupledChestInterface) {
				final BlockState otherState = level.getBlockState(coupledChest.getBlockPos());
				final boolean wasLogged = oldState.getFluidState().is(Fluids.WATER);

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
			if (wasLogged != state.getFluidState().is(Fluids.WATER) && wasLogged) chestBlockEntityInterface.wilderWild$setCanBubble(true);
		}
		possibleCoupledChest.ifPresent(chestBlockEntity -> chestBlockEntityInterface.wilderWild$syncBubble(chest, chestBlockEntity));
	}
}
