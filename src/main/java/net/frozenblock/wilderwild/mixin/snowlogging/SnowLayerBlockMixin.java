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
 * You should have received a copy of the FrozenBlock Modding oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.mixin.snowlogging;

import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SnowLayerBlock.class)
public abstract class SnowLayerBlockMixin {

	@Shadow
	public abstract boolean canSurvive(BlockState state, LevelReader level, BlockPos pos);

	@Inject(
		method = "getStateForPlacement",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z",
			ordinal = 0,
			shift = At.Shift.BEFORE
		),
		cancellable = true
	)
	public void wilderWild$getStateForPlacement(
		BlockPlaceContext context, CallbackInfoReturnable<BlockState> info,
		@Local BlockState blockState
	) {
		if (!WWBlockConfig.canSnowlog()) return;
		if (SnowloggingUtils.supportsSnowlogging(blockState)) {
			int layers = SnowloggingUtils.getSnowLayers(blockState);
			if (layers < 8) {
				BlockState placementState = blockState.setValue(SnowloggingUtils.SNOW_LAYERS, layers + 1);
				info.setReturnValue(placementState);
			}
		}
	}

	@Inject(
		method = "canBeReplaced",
		at = @At(value = "HEAD"),
		cancellable = true
	)
	public void wilderWild$canBeReplaced(BlockState state, BlockPlaceContext useContext, CallbackInfoReturnable<Boolean> info) {
		if (!WWBlockConfig.canSnowlog()) return;
		if (useContext.getItemInHand().getItem() instanceof BlockItem blockItem && SnowloggingUtils.canSnowlog(blockItem.getBlock().defaultBlockState())) {
			BlockState placementState = blockItem.getBlock().getStateForPlacement(useContext);
			if (SnowloggingUtils.isSnowlogged(placementState)) {
				Level level = useContext.getLevel();
				BlockPos pos = useContext.getClickedPos();
				VoxelShape blockShape = placementState.getShape(level, pos);
				VoxelShape snowLayerShape = state.getShape(level, pos);
				if (blockShape.max(Direction.Axis.Y) >= snowLayerShape.max(Direction.Axis.Y)) {
					info.setReturnValue(true);
				}
			}
		}
	}

	@Inject(
		method = "canSurvive",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/tags/TagKey;)Z",
			ordinal = 0,
			shift = At.Shift.BEFORE
		),
		cancellable = true
	)
	public void wilderWild$canSurvive(
		BlockState state, LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> info,
		@Local(ordinal = 1) BlockState supportState
	) {
		if (!WWBlockConfig.canSnowlog()) return;
		if (SnowloggingUtils.isSnowlogged(supportState)) {
			int layers = SnowloggingUtils.getSnowLayers(supportState);
			if (layers == 8) {
				info.setReturnValue(true);
			}
		}
	}

}
