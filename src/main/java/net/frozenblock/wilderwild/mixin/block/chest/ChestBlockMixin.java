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

package net.frozenblock.wilderwild.mixin.block.chest;

import java.util.function.Supplier;
import net.frozenblock.wilderwild.block.entity.impl.ChestBlockEntityInterface;
import net.frozenblock.wilderwild.config.EntityConfig;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.AbstractChestBlock;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChestBlock.class)
public abstract class ChestBlockMixin extends AbstractChestBlock<ChestBlockEntity> {

	protected ChestBlockMixin(Properties properties, Supplier<BlockEntityType<? extends ChestBlockEntity>> blockEntityType) {
		super(properties, blockEntityType);
	}

	@Unique
	@Nullable
	private static ChestBlockEntity wilderWild$getOtherChest(@NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockState state) {
		BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
		ChestType chestType = state.getValue(ChestBlock.TYPE);
		if (chestType == ChestType.RIGHT) {
			mutableBlockPos.move(ChestBlock.getConnectedDirection(state));
		} else if (chestType == ChestType.LEFT) {
			mutableBlockPos.move(ChestBlock.getConnectedDirection(state));
		} else {
			return null;
		}
		if (level.getBlockEntity(mutableBlockPos) instanceof ChestBlockEntity chest) {
			return chest;
		}
		return null;
	}

	@Inject(
		method = "use",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/player/Player;openMenu(Lnet/minecraft/world/MenuProvider;)Ljava/util/OptionalInt;",
			shift = At.Shift.BEFORE
		)
	)
	public void wilderWild$useBeforeOpenMenu(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> info) {
		if (level.getBlockEntity(pos) instanceof ChestBlockEntity sourceChest) {
			if (
				sourceChest.lootTable != null &&
				state.hasProperty(BlockStateProperties.WATERLOGGED) &&
				state.getValue(BlockStateProperties.WATERLOGGED) &&
				sourceChest.lootTable.getPath().toLowerCase().contains("shipwreck") &&
				level.random.nextInt(0, 3) == 1
			) {
				if (EntityConfig.get().jellyfish.spawnJellyfish) {
					Jellyfish.spawnFromChest(level, state, pos, true);
				}
			}
			((ChestBlockEntityInterface) sourceChest).wilderWild$bubble(level, pos, state);
		}
	}

	@Inject(method = "updateShape", at = @At(value = "RETURN"))
	public void wilderWild$updateShape(BlockState blockStateUnneeded, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> info) {
		BlockState state = info.getReturnValue();
		ChestBlockEntity otherChest = wilderWild$getOtherChest(level, currentPos, state);
		if (level.getBlockEntity(currentPos) instanceof ChestBlockEntity chest) {
			if (otherChest != null) {
				BlockState otherState = level.getBlockState(otherChest.getBlockPos());
				boolean wasLogged = blockStateUnneeded.getValue(BlockStateProperties.WATERLOGGED);
				if (wasLogged != state.getValue(BlockStateProperties.WATERLOGGED) && wasLogged) {
					if (!otherState.getValue(BlockStateProperties.WATERLOGGED)) {
						((ChestBlockEntityInterface) chest).wilderWild$setCanBubble(true);
						((ChestBlockEntityInterface) otherChest).wilderWild$setCanBubble(true);
					} else if (!((ChestBlockEntityInterface) otherChest).wilderWild$getCanBubble()) {
						((ChestBlockEntityInterface) chest).wilderWild$setCanBubble(false);
					}
				}
			} else {
				boolean wasLogged = blockStateUnneeded.getValue(BlockStateProperties.WATERLOGGED);
				if (wasLogged != state.getValue(BlockStateProperties.WATERLOGGED) && wasLogged) {
					((ChestBlockEntityInterface) chest).wilderWild$setCanBubble(true);
				}
			}
			if (otherChest != null && level.getBlockEntity(currentPos) instanceof ChestBlockEntity sourceChest) {
				((ChestBlockEntityInterface) sourceChest).wilderWild$syncBubble(sourceChest, otherChest);
			}
		}
	}

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/Containers;dropContents(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/Container;)V", shift = At.Shift.BEFORE), method = "onRemove")
	public void onRemove(BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean isMoving, CallbackInfo info) {
		if (level.getBlockEntity(pos) instanceof ChestBlockEntity chestBlockEntity) {
			((ChestBlockEntityInterface) chestBlockEntity).wilderWild$bubbleBurst(state);
		}
	}

}
