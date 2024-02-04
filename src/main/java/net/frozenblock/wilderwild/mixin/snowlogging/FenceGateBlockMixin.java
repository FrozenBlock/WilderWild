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

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FenceGateBlock.class)
public abstract class FenceGateBlockMixin extends HorizontalDirectionalBlock {

	@Shadow
	protected abstract BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos);

	@Unique
	private static final IntegerProperty WILDERWILD$SNOW_LAYERS = RegisterProperties.SNOW_LAYERS;

	public FenceGateBlockMixin(Properties properties) {
		super(properties);
	}

	@Unique
	@Override
	protected boolean isRandomlyTicking(BlockState state) {
		return super.isRandomlyTicking(state) || (RegisterProperties.isSnowlogged(state));
	}

	@Unique
	@Override
	protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
		int layers;
		return ((RegisterProperties.canBeSnowlogged(state) && context.getItemInHand().is(Blocks.SNOW.asItem()))
			&& ((layers = RegisterProperties.getSnowLayers(state)) <= 0 || (context.replacingClickedOnBlock() && context.getClickedFace() == Direction.UP && layers < 8))
		) || super.canBeReplaced(state, context);
	}

	@ModifyExpressionValue(
		method = "getStateForPlacement",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/FenceGateBlock;defaultBlockState()Lnet/minecraft/world/level/block/state/BlockState;"
		)
	)
	public BlockState getStateForPlacement(BlockState original, BlockPlaceContext context) {
		BlockState blockState = context.getLevel().getBlockState(context.getClickedPos());
		BlockState placementState = original;
		if (placementState != null && RegisterProperties.canBeSnowlogged(placementState) && blockState.is(Blocks.SNOW)) {
			int layers = blockState.getValue(BlockStateProperties.LAYERS);
			if (layers <= 7) {
				placementState = placementState.setValue(WILDERWILD$SNOW_LAYERS, layers);
			}
		}
		return placementState;
	}

	@Override
	public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
		boolean snowlogged = RegisterProperties.isSnowlogged(state);
		BlockState stateWithoutSnow = snowlogged ? state.setValue(WILDERWILD$SNOW_LAYERS, 0) : state;
		super.destroy(level, pos, stateWithoutSnow);
		if (snowlogged) {
			level.setBlock(pos, RegisterProperties.getSnowEquivalent(state), Block.UPDATE_ALL);
		}
	}

	@Override
	public void playerDestroy(@NotNull Level level, @NotNull Player player, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable BlockEntity blockEntity, @NotNull ItemStack stack) {
		BlockState stateWithoutSnow = RegisterProperties.isSnowlogged(state) ? state.setValue(WILDERWILD$SNOW_LAYERS, 0) : state;
		super.playerDestroy(level, player, pos, stateWithoutSnow, blockEntity, stack);
	}

	/*
	@Unique
	@Override
	public SoundType getSoundType(BlockState state) {
		return RegisterProperties.isSnowlogged(state) ? Blocks.SNOW.getSoundType(RegisterProperties.getSnowEquivalent(state)) : super.getSoundType(state);
	}
	 */

	@Unique
	@Override
	protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		super.randomTick(state, level, pos, random);
		if (RegisterProperties.isSnowlogged(state)) {
			if (level.getBrightness(LightLayer.BLOCK, pos) > 11) {
				dropResources(RegisterProperties.getSnowEquivalent(state), level, pos);
				level.setBlock(pos, state.setValue(WILDERWILD$SNOW_LAYERS, 0), Block.UPDATE_ALL);
			}
		}
	}

	@Inject(method = "updateShape", at = @At(value = "HEAD"), cancellable = true)
	public void wilderWild$updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> info) {
		if (RegisterProperties.isSnowlogged(state)) {
			BlockState snowEquivalent = RegisterProperties.getSnowEquivalent(state);
			if (!Blocks.SNOW.canSurvive(snowEquivalent, level, pos)) {
				level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(snowEquivalent));
				state = state.setValue(WILDERWILD$SNOW_LAYERS, 0);
				info.setReturnValue(this.updateShape(state, direction, neighborState, level, pos, neighborPos));
			}
		}
	}

	@Inject(method = "createBlockStateDefinition", at = @At(value = "TAIL"))
	public void wilderWild$createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder, CallbackInfo info) {
		builder.add(WILDERWILD$SNOW_LAYERS);
	}

}
