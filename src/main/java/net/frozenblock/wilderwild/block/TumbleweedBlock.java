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

package net.frozenblock.wilderwild.block;

import com.mojang.serialization.MapCodec;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DryVegetationBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class TumbleweedBlock extends DryVegetationBlock implements SimpleWaterloggedBlock {
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final MapCodec<TumbleweedBlock> CODEC = simpleCodec(TumbleweedBlock::new);
	protected static final VoxelShape COLLISION_SHAPE = Block.box(1D, 0D, 1D, 15D, 14D, 15D);
	protected static final VoxelShape OUTLINE_SHAPE = Block.box(1D, 0D, 1D, 15D, 14D, 15D);

	public TumbleweedBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
	}

	@Override
	public MapCodec<? extends TumbleweedBlock> codec() {
		return CODEC;
	}

	@Override
	public InteractionResult useItemOn(
		ItemStack stack,
		BlockState state,
		Level level,
		BlockPos pos,
		Player player,
		InteractionHand hand,
		BlockHitResult hitResult
	) {
		if (stack.is(Items.SHEARS)) {
			onShear(level, pos, player);
			stack.hurtAndBreak(1, player, hand);
			return InteractionResult.SUCCESS;
		}
		return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}

	public static boolean onShear(Level level, BlockPos pos, @Nullable Entity entity) {
		if (level.isClientSide()) return true;
		Tumbleweed.spawnFromShears(level, pos);
		level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
		level.gameEvent(entity, GameEvent.SHEAR, pos);
		return true;
	}

	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		final BlockState state = SnowloggingUtils.getSnowPlacementState(super.getStateForPlacement(context), context);
		if (state == null) return null;
		final FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
		return state.setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
	}

	@Override
	protected BlockState updateShape(
		BlockState state,
		LevelReader level,
		ScheduledTickAccess scheduledTickAccess,
		BlockPos pos,
		Direction direction,
		BlockPos neighborPos,
		BlockState neighborState,
		RandomSource random
	) {
		if (state.getValue(WATERLOGGED)) scheduledTickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		return super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return COLLISION_SHAPE;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return OUTLINE_SHAPE;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(WATERLOGGED);
	}
}
