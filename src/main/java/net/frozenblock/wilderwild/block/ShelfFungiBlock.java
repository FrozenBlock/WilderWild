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
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ShelfFungiBlock extends FaceAttachedHorizontalDirectionalBlock implements SimpleWaterloggedBlock, BonemealableBlock {
	public static final int GROWTH_BRIGHTNESS_OFFSET = 2;
	public static final int MAX_STAGE = 4;
	public static final int MAX_AGE = 2;
	public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
	public static final IntegerProperty STAGE = WWBlockStateProperties.FUNGUS_STAGE;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final MapCodec<ShelfFungiBlock> CODEC = RecordCodecBuilder.mapCodec(
		instance ->
			instance.group(
				ResourceKey.codec(Registries.LOOT_TABLE).fieldOf("shearing_loot_table").forGetter(shelfFungiBlock -> shelfFungiBlock.shearingLootTable),
				propertiesCodec()
			).apply(instance, ShelfFungiBlock::new)
	);
	protected static final VoxelShape NORTH_WALL_SHAPE = Block.box(0D, 0D, 13D, 16D, 16D, 16D);
	protected static final VoxelShape SOUTH_WALL_SHAPE = Block.box(0D, 0D, 0D, 16D, 16D, 3D);
	protected static final VoxelShape WEST_WALL_SHAPE = Block.box(13D, 0D, 0D, 16D, 16D, 16D);
	protected static final VoxelShape EAST_WALL_SHAPE = Block.box(0D, 0D, 0D, 3D, 16D, 16D);
	protected static final VoxelShape FLOOR_SHAPE = Block.box(0D, 0D, 0D, 16D, 3D, 16D);
	protected static final VoxelShape CEILING_SHAPE = Block.box(0D, 13D, 0D, 16D, 16D, 16D);
	private final ResourceKey<LootTable> shearingLootTable;

	public ShelfFungiBlock(ResourceKey<LootTable> shearingLootTable, Properties properties) {
		super(properties);
		this.shearingLootTable = shearingLootTable;
		this.registerDefaultState(
			this.stateDefinition.any().
				setValue(FACING, Direction.NORTH)
				.setValue(WATERLOGGED, false)
				.setValue(FACE, AttachFace.WALL)
				.setValue(STAGE, 1)
		);
	}

	@Override
	protected boolean isRandomlyTicking(BlockState state) {
		return super.isRandomlyTicking(state) || SnowloggingUtils.isSnowlogged(state);
	}

	public static AttachFace getFace(Direction direction) {
		if (direction.getAxis() == Direction.Axis.Y) return direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR;
		return AttachFace.WALL;
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	private static boolean isFullyGrown(BlockState state) {
		return state.getValue(STAGE) == MAX_STAGE;
	}

	@Override
	protected MapCodec<? extends ShelfFungiBlock> codec() {
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
		if (stack.is(Items.SHEARS) && onShear(level, pos, state, stack, player)) {
			stack.hurtAndBreak(1, player, hand);
			return InteractionResult.SUCCESS;
		}
		return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}

	public static boolean onShear(Level level, BlockPos pos, BlockState state, ItemStack stack, @Nullable Entity entity) {
		final int stage = state.getValue(STAGE);
		if (stage <= 1) return false;
		level.setBlockAndUpdate(pos, state.setValue(STAGE, stage - 1));
		level.playSound(null, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1F, 1F);
		if (level instanceof ServerLevel serverLevel) dropShelfFungi(serverLevel, stack, state, null, entity, pos);
		level.gameEvent(entity, GameEvent.SHEAR, pos);
		return true;
	}

	public static void dropShelfFungi(
		ServerLevel level, ItemStack stack, BlockState state, @Nullable BlockEntity blockEntity, @Nullable Entity entity, BlockPos pos
	) {
		if (!(state.getBlock() instanceof  ShelfFungiBlock shelfFungiBlock)) return;
		dropFromBlockInteractLootTable(
			level,
			shelfFungiBlock.getShearingLootTable(),
			state,
			blockEntity,
			stack,
			entity,
			(serverLevelx, itemStackx) -> popResource(serverLevelx, pos, itemStackx)
		);
	}

	public ResourceKey<LootTable> getShearingLootTable() {
		return this.shearingLootTable;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACE, FACING, AGE, STAGE, WATERLOGGED);
		SnowloggingUtils.appendSnowlogProperties(builder);
	}

	@Override
	public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
		return !context.isSecondaryUseActive() && context.getItemInHand().is(this.asItem()) && state.getValue(STAGE) < MAX_STAGE || super.canBeReplaced(state, context);
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		final BlockState insideState = context.getLevel().getBlockState(context.getClickedPos());
		if (insideState.is(this)) return insideState.setValue(STAGE, Math.min(MAX_STAGE, insideState.getValue(STAGE) + 1));

		boolean waterlogged = insideState.hasProperty(BlockStateProperties.WATERLOGGED) ? insideState.getValue(BlockStateProperties.WATERLOGGED) : false;
		if (!waterlogged) waterlogged = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;

		for (Direction direction : context.getNearestLookingDirections()) {
			BlockState blockState;
			if (direction.getAxis() == Direction.Axis.Y) {
				blockState = this.defaultBlockState().setValue(FACE, direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR).setValue(FACING, context.getHorizontalDirection()).setValue(WATERLOGGED, waterlogged);
			} else {
				blockState = this.defaultBlockState().setValue(FACE, AttachFace.WALL).setValue(FACING, direction.getOpposite()).setValue(WATERLOGGED, waterlogged);
			}
			if (blockState.canSurvive(context.getLevel(), context.getClickedPos())) return SnowloggingUtils.getSnowPlacementState(blockState, context);
		}
		return null;
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
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return switch (state.getValue(FACE)) {
			case FLOOR -> FLOOR_SHAPE;
			case WALL -> switch (state.getValue(FACING)) {
				case EAST -> EAST_WALL_SHAPE;
				case WEST -> WEST_WALL_SHAPE;
				case SOUTH -> SOUTH_WALL_SHAPE;
				default -> NORTH_WALL_SHAPE;
			};
			case CEILING -> CEILING_SHAPE;
		};
	}

	public boolean isMaxAge(BlockState state) {
		return state.getValue(AGE) == MAX_AGE;
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (random.nextInt(level.getMaxLocalRawBrightness(pos) + GROWTH_BRIGHTNESS_OFFSET) == 1) {
			if (!isMaxAge(state)) {
				level.setBlock(pos, state.cycle(AGE), UPDATE_CLIENTS);
			} else if (!isFullyGrown(state)) {
				level.setBlock(pos, state.cycle(STAGE).setValue(AGE, 0), UPDATE_CLIENTS);
			}
		}
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
		return !isFullyGrown(state);
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		level.setBlock(pos, state.cycle(STAGE).setValue(AGE, 0), UPDATE_CLIENTS);
	}

	@Override
	public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
		if (SnowloggingUtils.isSnowlogged(state)) {
			super.destroy(level, pos, SnowloggingUtils.getSnowEquivalent(state));
		} else {
			super.destroy(level, pos, state);
		}
	}

	@Override
	public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
		if (SnowloggingUtils.isSnowlogged(state)) {
			final BlockState snowEquivalent = SnowloggingUtils.getSnowEquivalent(state);
			if (player.hasCorrectToolForDrops(snowEquivalent)) super.playerDestroy(level, player, pos, snowEquivalent, blockEntity, stack);
		} else {
			super.playerDestroy(level, player, pos, state, blockEntity, stack);
		}
	}
}
