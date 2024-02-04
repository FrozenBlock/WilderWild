/*
 * Copyright 2024 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.snowlogging;

import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(SnowLayerBlock.class)
public class SnowLayerBlockMixin {

	@Inject(
		method = "getStateForPlacement",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z",
			ordinal = 0,
			shift = At.Shift.BEFORE
		),
		cancellable = true,
		locals = LocalCapture.CAPTURE_FAILHARD
	)
	public void wilderWild$getStateForPlacement(BlockPlaceContext context, CallbackInfoReturnable<BlockState> info, BlockState blockState) {
		if (RegisterProperties.canBeSnowlogged(blockState)) {
			int layers = RegisterProperties.getSnowLayers(blockState);
			if (layers < 7) {
				BlockState placementState = blockState.setValue(RegisterProperties.SNOW_LAYERS, layers + 1);
				BlockState equivalentState = RegisterProperties.getSnowEquivalent(placementState);
				VoxelShape plantShape = placementState.setValue(RegisterProperties.SNOW_LAYERS, 0).getShape(context.getLevel(), context.getClickedPos());
				VoxelShape snowLayerShape = equivalentState.getShape(context.getLevel(), context.getClickedPos());
				if (plantShape.max(Direction.Axis.Y) <= snowLayerShape.max(Direction.Axis.Y)) {
					if (!context.getLevel().isClientSide()) {
						context.getLevel().destroyBlock(context.getClickedPos(), true);
					}
					placementState = equivalentState;
				}
				info.setReturnValue(placementState);
			} else {
				context.getLevel().destroyBlock(context.getClickedPos(), true);
				info.setReturnValue(SnowLayerBlock.class.cast(this).defaultBlockState().setValue(BlockStateProperties.LAYERS, 8));
			}
		}
	}

	@Inject(
		method = "canBeReplaced",
		at = @At(value = "HEAD"),
		cancellable = true
	)
	public void wilderWild$canBeReplaced(BlockState state, BlockPlaceContext useContext, CallbackInfoReturnable<Boolean> info) {
		if (useContext.getItemInHand().getItem() instanceof BlockItem blockItem) {
			BlockState placementState = blockItem.getBlock().getStateForPlacement(useContext);
			if (placementState != null && RegisterProperties.isSnowlogged(placementState)) {
				VoxelShape plantShape = placementState.getShape(useContext.getLevel(), useContext.getClickedPos());
				VoxelShape snowLayerShape = state.getShape(useContext.getLevel(), useContext.getClickedPos());
				if (plantShape.max(Direction.Axis.Y) > snowLayerShape.max(Direction.Axis.Y)) {
					info.setReturnValue(true);
				}
			}
		}
	}

}
