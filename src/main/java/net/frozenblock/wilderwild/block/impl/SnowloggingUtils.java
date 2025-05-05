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

import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SnowloggingUtils {
	public static final IntegerProperty SNOW_LAYERS = WWBlockStateProperties.SNOW_LAYERS;
	public static final int MAX_LAYERS = 8;
	private static final boolean CONFIG_SNOWLOGGING_ON_BOOT = WWBlockConfig.get().snowlogging.snowlogging;
	private static final boolean CONFIG_SNOWLOG_BLOCKADES_ON_BOOT = WWBlockConfig.get().snowlogging.snowlogWalls;

	public static void appendSnowlogProperties(StateDefinition.Builder<Block, BlockState> builder) {
		if (!CONFIG_SNOWLOGGING_ON_BOOT) return;
		builder.add(SNOW_LAYERS);
	}

	public static void appendSnowlogPropertiesToBlockade(StateDefinition.Builder<Block, BlockState> builder) {
		if (!CONFIG_SNOWLOGGING_ON_BOOT || !CONFIG_SNOWLOG_BLOCKADES_ON_BOOT) return;
		builder.add(SNOW_LAYERS);
	}

	public static boolean supportsSnowlogging(@Nullable BlockState state) {
		if (!WWBlockConfig.SNOWLOGGING) return false;
		//noinspection ConstantValue
		return state != null && state.getValues() != null && state.hasProperty(SNOW_LAYERS);
	}

	public static boolean canSnowlog(@Nullable BlockState state) {
		//noinspection DataFlowIssue
		return supportsSnowlogging(state) && state.getFluidState().isEmpty();
	}

	public static int getSnowLayers(@NotNull BlockState state) {
		return state.getValue(SNOW_LAYERS);
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
		return (canSnowlog(state) && isItemSnow(context.getItemInHand())) &&
			Blocks.SNOW.canSurvive(Blocks.SNOW.defaultBlockState(), context.getLevel(), context.getClickedPos()) &&
			((layers = getSnowLayers(state)) <= 0 || (context.replacingClickedOnBlock() && context.getClickedFace() == Direction.UP && layers < MAX_LAYERS));
	}

	@Nullable
	public static BlockState onUpdateShape(BlockState state, LevelReader level, BlockPos pos) {
		if (isSnowlogged(state)) {
			BlockState snowEquivalent = getSnowEquivalent(state);
			if (!Blocks.SNOW.canSurvive(snowEquivalent, level, pos)) {
				if (level instanceof LevelAccessor levelAccessor) {
					levelAccessor.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(snowEquivalent));
				}
				state = state.setValue(SNOW_LAYERS, 0);
			}
		}
		return state;
	}

	public static BlockState getSnowPlacementState(BlockState state, @NotNull BlockPlaceContext context) {
		return getSnowloggedState(state, context.getLevel().getBlockState(context.getClickedPos()));
	}

	public static BlockState getSnowloggedState(BlockState state, BlockState snowState) {
		if (state != null && snowState != null && canSnowlog(state) && snowState.is(Blocks.SNOW)) {
			int layers = snowState.getValue(BlockStateProperties.LAYERS);
			if (layers < MAX_LAYERS) state = state.setValue(SNOW_LAYERS, layers);
		}
		return state;
	}

	public static float getSnowDestroySpeed(BlockState state, BlockGetter level, BlockPos pos) {
		return getSnowEquivalent(state).getDestroySpeed(level, pos);
	}

	public static void onRandomTick(BlockState state, ServerLevel level, BlockPos pos) {
		if (isSnowlogged(state)) {
			if (level.getBrightness(LightLayer.BLOCK, pos) > 11) {
				Block.dropResources(getSnowEquivalent(state), level, pos);
				level.setBlockAndUpdate(pos, state.setValue(SNOW_LAYERS, 0));
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

}
