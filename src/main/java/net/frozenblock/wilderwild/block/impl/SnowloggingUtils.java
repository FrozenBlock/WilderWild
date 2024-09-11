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

package net.frozenblock.wilderwild.block.impl;

import net.frozenblock.wilderwild.config.BlockConfig;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SnowloggingUtils {
	public static final IntegerProperty SNOW_LAYERS = RegisterProperties.SNOW_LAYERS;
	public static final int MAX_LAYERS = 8;

	public static boolean supportsSnowlogging(@Nullable BlockState state) {
		if (!BlockConfig.SNOWLOGGING) return false;
		//noinspection ConstantValue
		return state != null && state.getValues() != null && state.hasProperty(SNOW_LAYERS);
	}

	public static boolean canSnowlog(@Nullable BlockState state) {
		//noinspection DataFlowIssue
		return supportsSnowlogging(state) && state.getFluidState().isEmpty();
	}

	public static StateDefinition.Builder<Block, BlockState> addSnowLayersToDefinitionAndBlock(
		StateDefinition.Builder<Block, BlockState> builder,
		BlockBehaviour blockBehaviour
	) {
		if (BlockConfig.get().snowlogging.snowlogging) {
			builder.add(SnowloggingUtils.SNOW_LAYERS);
			if (blockBehaviour instanceof BlockBehaviourSnowloggingInterface snowloggingInterface) {
				snowloggingInterface.wilderWild$enableSnowlogging();
			}
		}
		return builder;
	}

	/**
	 * Gets the snow layers of a snowloggable block.
	 *
	 * @return snow layers of a snowloggable block.
	 */
	public static int getSnowLayers(@NotNull BlockState state) {
		return state.getValue(SNOW_LAYERS);
	}

	/**
	 * Gets the snow layers of a snowloggable block or of a snow layer block.
	 *
	 * @return snow layers of a snowloggable block or of a snow layer block.
	 */
	public static int getAnySnowLayers(@NotNull BlockState state) {
		if (supportsSnowlogging(state)) return state.getValue(SNOW_LAYERS);
		if (state.is(Blocks.SNOW)) return state.getValue(SnowLayerBlock.LAYERS);
		return 0;
	}

	/**
	 * Takes the snow layers of the source
	 * and returns a version of the destination with the same number of snow layers if possible.
	 *
	 * @param source      The blockstate whose snow layers are getting copied.
	 * @param destination The blockstate which is getting snow layers pasted onto.
	 * @return A version of destination with snow layers copied from the source.
	 */
	public static BlockState copySnowLayers(@NotNull BlockState source, @NotNull BlockState destination) {
		if (supportsSnowlogging(destination)) {
			return destination.setValue(SNOW_LAYERS, getAnySnowLayers(source));
		}
		return destination;
	}

	public static boolean isSnowlogged(@Nullable BlockState state) {
		return state != null && supportsSnowlogging(state) && getSnowLayers(state) > 0;
	}

	@NotNull
	public static BlockState getSnowEquivalent(BlockState state) {
		return Blocks.SNOW.defaultBlockState().setValue(BlockStateProperties.LAYERS, Math.max(1, getSnowLayers(state)));
	}

	@Nullable
	public static BlockState getStateWithoutSnow(@Nullable BlockState state) {
		return isSnowlogged(state) ? state.setValue(SNOW_LAYERS, 0) : state;
	}

	public static boolean isItemSnow(@Nullable ItemStack stack) {
		return stack != null && stack.is(Blocks.SNOW.asItem());
	}

	public static boolean canBeReplacedWithSnow(BlockState state, BlockPlaceContext context) {
		int layers;
		if (SnowloggingUtils.canSnowlog(state) && isItemSnow(context.getItemInHand()) && Blocks.SNOW.canSurvive(Blocks.SNOW.defaultBlockState(), context.getLevel(), context.getClickedPos())) {
			layers = SnowloggingUtils.getSnowLayers(state);
			if (layers >= MAX_LAYERS) return false;
			if (layers <= 0) return true;
			if (context.replacingClickedOnBlock()) {
				return (context.getClickedFace() == Direction.UP) || (!SnowloggingUtils.shouldHitSnow(state, context.getClickedPos(), context.getLevel(), context.getClickLocation()));
			}
		}
		return false;
	}

	@Nullable
	public static BlockState onUpdateShape(BlockState state, LevelAccessor level, BlockPos pos) {
		if (isSnowlogged(state)) {
			BlockState snowEquivalent = SnowloggingUtils.getSnowEquivalent(state);
			if (!Blocks.SNOW.canSurvive(snowEquivalent, level, pos)) {
				level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(snowEquivalent));
				state = state.setValue(SNOW_LAYERS, 0);
			}
		}
		return state;
	}

	public static BlockState getSnowPlacementState(BlockState state, @NotNull BlockPlaceContext context) {
		BlockState blockState;
		BlockState placementState = state;
		if (placementState != null && SnowloggingUtils.supportsSnowlogging(placementState)) {
			blockState = context.getLevel().getBlockState(context.getClickedPos());
			if (blockState.is(Blocks.SNOW)) {
				int layers = blockState.getValue(BlockStateProperties.LAYERS);
				if (layers < 8) {
					placementState = placementState.setValue(SNOW_LAYERS, layers);
				}
			} else if (SnowloggingUtils.isSnowlogged(blockState)) {
				int layers = blockState.getValue(SnowloggingUtils.SNOW_LAYERS);
				if (layers < 8) {
					placementState = placementState.setValue(SNOW_LAYERS, layers);
				}
			}
		}
		return placementState;
	}

	public static float getSnowDestroySpeed(BlockState state, BlockGetter level, BlockPos pos) {
		return getSnowEquivalent(state).getDestroySpeed(level, pos);
	}

	public static void onRandomTick(BlockState state, ServerLevel level, BlockPos pos) {
		if (SnowloggingUtils.isSnowlogged(state)) {
			if (level.getBrightness(LightLayer.BLOCK, pos) > 11) {
				Block.dropResources(SnowloggingUtils.getSnowEquivalent(state), level, pos);
				level.setBlock(pos, state.setValue(SNOW_LAYERS, 0), Block.UPDATE_ALL);
			}
		}
	}

	public static boolean isOriginalBlockCovered(BlockState state, BlockGetter level, BlockPos pos) {
		if (isSnowlogged(state)) {
			VoxelShape blockShape = state.getShape(level, pos);
			VoxelShape snowLayerShape = getSnowEquivalent(state).getShape(level, pos);
			return blockShape.max(Direction.Axis.Y) <= snowLayerShape.max(Direction.Axis.Y);
		}
		return false;
	}

	/**
	 * Returns if the hit location is at or beneath the snow level.
	 *
	 * @return if the hit location is at or beneath the snow level.
	 */
	public static boolean shouldHitSnow(BlockState state, BlockPos pos, Level level, Vec3 hitLocation) {
		if (isSnowlogged(state)) {
			VoxelShape snowLayerShape = getSnowEquivalent(state).getShape(level, pos);
			return (pos.getY() + snowLayerShape.max(Direction.Axis.Y)) >= hitLocation.y();
		}
		return false;
	}

	/**
	 * Returns if a block is hit and the location is at or beneath the snow level.
	 *
	 * @return if a block is hit and the location is at or beneath the snow level.
	 */
	public static boolean shouldHitSnow(BlockState state, BlockPos pos, Level level, HitResult hitResult) {
		if (hitResult != null && hitResult.getType() == HitResult.Type.BLOCK) {
			return shouldHitSnow(state, pos, level, hitResult.getLocation());
		}
		return false;
	}

	/**
	 * Returns if the location the player is looking at is at or beneath the snow level.
	 *
	 * @return if the location the player is looking at is at or beneath the snow level.
	 */
	public static boolean shouldHitSnow(BlockState state, BlockPos pos, Level level, @NotNull Player player) {
		HitResult hitResult = player.pick(player.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE), 0, false);
		return shouldHitSnow(state, pos, level, hitResult);
	}

	/**
	 * Returns the snow layers if the player is looking at them, or the original block if not.
	 *
	 * @return the snow layers if the player is looking at them, or the original block if not.
	 */
	public static BlockState getHitState(BlockState state, BlockPos pos, Level level, @NotNull Player player) {
		return shouldHitSnow(state, pos, level, player) ? getSnowEquivalent(state) : getStateWithoutSnow(state);
	}

	/**
	 * Returns the original block if the player is looking at the snow layers, and vice versa.
	 *
	 * @return the original block if the player is looking at the snow layers, and vice versa.
	 */
	public static BlockState getUnhitState(BlockState state, BlockPos pos, Level level, @NotNull Player player) {
		return shouldHitSnow(state, pos, level, player) ? getStateWithoutSnow(state) : getSnowEquivalent(state);
	}

	/**
	 * Returns the original block if the player is looking at the snow layers, and vice versa.
	 *
	 * @return the original block if the player is looking at the snow layers, and vice versa.
	 */
	public static BlockState getUnhitState(BlockState state, BlockPos pos, Level level, HitResult hitResult) {
		return shouldHitSnow(state, pos, level, hitResult) ? getStateWithoutSnow(state) : getSnowEquivalent(state);
	}
}
