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

package net.frozenblock.wilderwild.mixin.sculk;

import net.frozenblock.wilderwild.block.entity.impl.SculkShriekerTickInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SculkShriekerBlock.class)
public abstract class SculkShriekerBlockMixin extends BaseEntityBlock {

	private SculkShriekerBlockMixin(Properties settings) {
		super(settings);
	}

	@Override
	@NotNull
	public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return Shapes.or(
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
			Block.box(1.0D, 8.0D, 1.0D, 15.0D, 15.0D, 15.0D)
		);
	}

	@Inject(at = @At("HEAD"), method = "getTicker", cancellable = true)
	public <T extends BlockEntity> void wilderWild$getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType, CallbackInfoReturnable<BlockEntityTicker<T>> info) {
		if (!level.isClientSide) {
			info.setReturnValue(
				BaseEntityBlock.createTickerHelper(
					blockEntityType, BlockEntityType.SCULK_SHRIEKER,
					(_world, pos, _state, blockEntity) -> {
						if (blockEntity instanceof SculkShriekerTickInterface shriekerTickInterface) {
							shriekerTickInterface.wilderWild$tickServer(level, pos);
						}
					}
					)
			);
		}
	}

	@Shadow
	public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		throw new AssertionError("Mixin injection failed - Wilder Wild SculkShriekerBlockMixin.");
	}

}
