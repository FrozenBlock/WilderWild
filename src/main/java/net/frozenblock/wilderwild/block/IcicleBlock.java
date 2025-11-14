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

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.MapCodec;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import net.frozenblock.wilderwild.block.entity.IcicleBlockEntity;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.frozenblock.wilderwild.registry.WWBlockEntityTypes;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWDamageTypes;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.frozenblock.wilderwild.worldgen.impl.util.IcicleUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class IcicleBlock extends BaseEntityBlock implements Fallable, SimpleWaterloggedBlock {
	public static final MapCodec<IcicleBlock> CODEC = simpleCodec(IcicleBlock::new);
	private static final TargetingConditions TARGETING_CONDITIONS = TargetingConditions.forNonCombat().ignoreInvisibilityTesting().ignoreLineOfSight().range(32D);
	public static final EnumProperty<Direction> TIP_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
	public static final EnumProperty<DripstoneThickness> THICKNESS = BlockStateProperties.DRIPSTONE_THICKNESS;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private static final int DELAY_BEFORE_FALLING = 2;
	private static final VoxelShape TIP_MERGE_SHAPE = Block.box(6D, 0D, 6D, 10D, 16D, 10D);
	private static final VoxelShape TIP_SHAPE_UP = Block.box(6D, 0D, 6D, 10D, 12D, 10D);
	private static final VoxelShape TIP_SHAPE_DOWN = Block.box(6D, 4D, 6D, 10D, 16D, 10D);
	private static final VoxelShape FRUSTUM_SHAPE = Block.box(5D, 0D, 5D, 11D, 16D, 11D);
	private static final VoxelShape MIDDLE_SHAPE = Block.box(5D, 0D, 5D, 11D, 16D, 11D);
	private static final VoxelShape BASE_SHAPE = Block.box(3D, 0D, 3D, 13D, 16D, 13D);

	public IcicleBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(
			this.stateDefinition.any().setValue(TIP_DIRECTION, Direction.UP).setValue(THICKNESS, DripstoneThickness.TIP).setValue(WATERLOGGED, false)
		);
	}

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(TIP_DIRECTION, THICKNESS, WATERLOGGED);
		SnowloggingUtils.appendSnowlogProperties(builder);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos blockPos) {
		return isValidIciclePlacement(level, blockPos, state.getValue(TIP_DIRECTION));
	}

	@Override
	protected BlockState updateShape(
		BlockState state,
		LevelReader level,
		ScheduledTickAccess tickAccess,
		BlockPos pos,
		Direction direction,
		BlockPos neighborPos,
		BlockState neighborState,
		RandomSource random
	) {
		if (state.getValue(WATERLOGGED)) tickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		if (direction != Direction.UP && direction != Direction.DOWN) return state;

		final Direction tipDirection = state.getValue(TIP_DIRECTION);
		if (tipDirection == Direction.DOWN && tickAccess.getBlockTicks().hasScheduledTick(pos, this)) return state;
		if (direction == tipDirection.getOpposite() && !this.canSurvive(state, level, pos)) {
			tickAccess.scheduleTick(pos, this, tipDirection == Direction.DOWN ? DELAY_BEFORE_FALLING : 1);
			return state;
		}
		final boolean merged = state.getValue(THICKNESS) == DripstoneThickness.TIP_MERGE;
		final DripstoneThickness thickness = calculateIcicleThickness(level, pos, tipDirection, merged);
		return state.setValue(THICKNESS, thickness);
	}

	@Override
	protected void onProjectileHit(Level level, BlockState state, BlockHitResult hitResult, Projectile projectile) {
		if (level.isClientSide() || !(level instanceof ServerLevel serverLevel)) return;
		final BlockPos blockPos = hitResult.getBlockPos();
		if (!projectile.mayInteract(serverLevel, blockPos) || !projectile.mayBreak(serverLevel) || projectile.getDeltaMovement().length() <= 0.4D) return;
		level.destroyBlock(blockPos, true);
	}

	public void triggerFall(Level level, BlockPos blockPos) {
		level.scheduleTick(blockPos, this, DELAY_BEFORE_FALLING);
	}

	@Override
	protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (isIceSpike(state) && !this.canSurvive(state, level, pos)) {
			level.destroyBlock(pos, true);
			return;
		}
		spawnFallingIcicle(state, level, pos);
	}

	@Override
	protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!isHangingIcicleStartPos(state, level, pos)) return;
		if (random.nextFloat() <= 0.165F) {
			growIcicleIfPossible(state, level, pos);
		} else if (this.canRandomFall(level, pos, random)) {
			this.triggerFall(level, pos);
		} else if (random.nextFloat() <= 0.135F) {
			placeIciclesNearby(level, pos, 3, random.nextInt(1, 2));
		}
	}

	public static void placeIciclesNearby(ServerLevel level, BlockPos blockPos, int distance, int maxNewIcicles) {
		if (!canGrow(level.getBlockState(blockPos.above()), level.getBlockState(blockPos.above(2)))) return;
		final Iterator<BlockPos> posesToCheck = BlockPos.betweenClosed(blockPos.offset(-distance, -distance, -distance), blockPos.offset(distance, distance, distance)).iterator();
		int count = 0;
		while (count < maxNewIcicles) {
			if (!posesToCheck.hasNext()) return;
			final BlockPos nextPos = posesToCheck.next();
			if (level.getBlockState(nextPos).is(WWBlockTags.ICICLE_GROWS_WHEN_UNDER) && IcicleUtils.spreadIcicleOnRandomTick(level, nextPos))  count++;
		}
	}


	private boolean canRandomFall(ServerLevel level, BlockPos pos, RandomSource random) {
		if (random.nextFloat() > 0.075F || !level.getBlockState(pos.above()).is(WWBlockTags.ICICLE_FALLS_FROM)) return false;
		final Vec3 centerPos = Vec3.atCenterOf(pos);
		final Player player = level.getNearestPlayer(TARGETING_CONDITIONS, centerPos.x(), centerPos.y(), centerPos.z());
		if (player != null) {
			final boolean isPlayerAbove = player.blockPosition().getY() > pos.getY();
			final double distance = player.distanceToSqr(centerPos);
			return Math.sqrt(distance) <= 32D && (!isPlayerAbove || random.nextFloat() <= 0.25F);
		}
		return random.nextFloat() <= 0.05F;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		final LevelAccessor level = context.getLevel();
		final BlockPos pos = context.getClickedPos();
		final Direction direction = context.getNearestLookingVerticalDirection().getOpposite();
		final Direction tipDirection = calculateTipDirection(level, pos, direction);
		if (tipDirection == null) return null;

		final DripstoneThickness thickness = calculateIcicleThickness(level, pos, tipDirection, !context.isSecondaryUseActive());
		return thickness == null
			? null
			: SnowloggingUtils.getSnowPlacementState(
				this.defaultBlockState()
					.setValue(TIP_DIRECTION, tipDirection)
					.setValue(THICKNESS, thickness)
					.setValue(WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER),
			context
		);
	}

	@Override
	protected FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	protected VoxelShape getOcclusionShape(BlockState state) {
		return Shapes.empty();
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		final DripstoneThickness thickness = state.getValue(THICKNESS);
		VoxelShape voxelShape;
		if (thickness == DripstoneThickness.TIP_MERGE) {
			voxelShape = TIP_MERGE_SHAPE;
		} else if (thickness == DripstoneThickness.TIP) {
			voxelShape = state.getValue(TIP_DIRECTION) == Direction.DOWN ? TIP_SHAPE_DOWN : TIP_SHAPE_UP;
		} else if (thickness == DripstoneThickness.FRUSTUM) {
			voxelShape = FRUSTUM_SHAPE;
		} else if (thickness == DripstoneThickness.MIDDLE) {
			voxelShape = MIDDLE_SHAPE;
		} else {
			voxelShape = BASE_SHAPE;
		}

		final Vec3 offset = state.getOffset(pos);
		return voxelShape.move(offset.x, 0D, offset.z);
	}

	@Override
	protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return this.getShape(state, level, pos, context);
	}

	@Override
	protected boolean isCollisionShapeFullBlock(BlockState state, BlockGetter level, BlockPos pos) {
		return false;
	}

	@Override
	protected float getMaxHorizontalOffset() {
		return 0.175F;
	}

	@Override
	public void onBrokenAfterFall(Level level, BlockPos pos, FallingBlockEntity fallingBlock) {
		level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(fallingBlock.getBlockState()));
	}

	@Override
	public DamageSource getFallDamageSource(Entity entity) {
		return entity.damageSources().source(WWDamageTypes.FALLING_ICICLE, entity);
	}

	private static void spawnFallingIcicle(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos) {
		final BlockPos.MutableBlockPos mutable = blockPos.mutable();
		BlockState blockState2 = blockState;

		while (isHangingIcicle(blockState2)) {
			final FallingBlockEntity fallingBlock = FallingBlockEntity.fall(serverLevel, mutable, blockState2);
			fallingBlock.disableDrop();
			if (isTip(blockState2, true)) {
				int i = Math.max(1 + blockPos.getY() - mutable.getY(), 6);
				fallingBlock.setHurtsEntities(i, 10);
				break;
			}

			mutable.move(Direction.DOWN);
			blockState2 = serverLevel.getBlockState(mutable);
		}
	}

	@VisibleForTesting
	public static void growIcicleIfPossible(BlockState state, ServerLevel level, BlockPos pos) {
		final BlockState aboveState = level.getBlockState(pos.above(1));
		final BlockState aboveTwiceState = level.getBlockState(pos.above(2));
		if (!canGrow(aboveState, aboveTwiceState)) return;

		final BlockPos tipPos = findTip(state, level, pos, 7, false);
		if (tipPos == null) return;

		final BlockState tipState = level.getBlockState(tipPos);
		if (!canDrip(tipState) || !canTipGrow(tipState, level, tipPos)) return;
		grow(level, tipPos, Direction.DOWN);
	}

	public static void grow(ServerLevel level, BlockPos pos, Direction direction) {
		final BlockPos offsetPos = pos.relative(direction);
		final BlockState offsetState = level.getBlockState(offsetPos);
		if (isUnmergedTipWithDirection(offsetState, direction.getOpposite())) {
			createMergedTips(offsetState, level, offsetPos);
		} else if (offsetState.isAir() || offsetState.is(Blocks.WATER)) {
			createIcicle(level, offsetPos, direction, DripstoneThickness.TIP);
		}
	}

	private static void createIcicle(LevelAccessor level, BlockPos pos, Direction direction, DripstoneThickness thickness) {
		final BlockState blockState = WWBlocks.ICICLE
			.defaultBlockState()
			.setValue(TIP_DIRECTION, direction)
			.setValue(THICKNESS, thickness)
			.setValue(WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER);
		level.setBlock(pos, blockState, UPDATE_ALL);
	}

	private static void createMergedTips(BlockState state, LevelAccessor level, BlockPos pos) {
		final boolean isUp = state.getValue(TIP_DIRECTION) == Direction.UP;
		createIcicle(level, isUp ? pos.above() : pos, Direction.DOWN, DripstoneThickness.TIP_MERGE);
		createIcicle(level, isUp ? pos : pos.below(), Direction.UP, DripstoneThickness.TIP_MERGE);
	}

	@Nullable
	public static BlockPos findTip(BlockState state, LevelAccessor level, BlockPos pos, int i, boolean bl) {
		if (isTip(state, bl)) return pos;
		final Direction direction = state.getValue(TIP_DIRECTION);
		BiPredicate<BlockPos, BlockState> biPredicate = (blockPosx, blockStatex) -> blockStatex.is(WWBlocks.ICICLE)
			&& blockStatex.getValue(TIP_DIRECTION) == direction;

		return findBlockVertical(level, pos, direction.getAxisDirection(), biPredicate, blockStatex -> isTip(blockStatex, bl), i).orElse(null);
	}

	@Nullable
	private static Direction calculateTipDirection(LevelReader level, BlockPos pos, Direction direction) {
		if (isValidIciclePlacement(level, pos, direction)) return direction;
		if (!isValidIciclePlacement(level, pos, direction.getOpposite())) return null;
		return direction.getOpposite();
	}

	private static DripstoneThickness calculateIcicleThickness(LevelReader level, BlockPos pos, Direction direction, boolean bl) {
		final Direction oppositeDirection = direction.getOpposite();
		final BlockState state = level.getBlockState(pos.relative(direction));
		if (isIcicleWithDirection(state, oppositeDirection)) {
			return !bl && state.getValue(THICKNESS) != DripstoneThickness.TIP_MERGE ? DripstoneThickness.TIP : DripstoneThickness.TIP_MERGE;
		}
		if (!isIcicleWithDirection(state, direction)) return DripstoneThickness.TIP;

		final DripstoneThickness thickness = state.getValue(THICKNESS);
		if (thickness != DripstoneThickness.TIP && thickness != DripstoneThickness.TIP_MERGE) {
			final BlockState offsetState = level.getBlockState(pos.relative(oppositeDirection));
			return !isIcicleWithDirection(offsetState, direction) ? DripstoneThickness.BASE : DripstoneThickness.MIDDLE;
		}
		return DripstoneThickness.FRUSTUM;
	}

	public static boolean canDrip(BlockState state) {
		return isHangingIcicle(state) && state.getValue(THICKNESS) == DripstoneThickness.TIP && !state.getValue(WATERLOGGED);
	}

	public static boolean canTipGrow(BlockState state, ServerLevel level, BlockPos pos) {
		Direction direction = state.getValue(TIP_DIRECTION);
		BlockPos blockPos2 = pos.relative(direction);
		BlockState blockState2 = level.getBlockState(blockPos2);
		if (!blockState2.getFluidState().isEmpty()) return false;
		return blockState2.isAir() || isUnmergedTipWithDirection(blockState2, direction.getOpposite());
	}

	private static boolean isValidIciclePlacement(LevelReader level, BlockPos pos, Direction direction) {
		final BlockPos offsetPos = pos.relative(direction.getOpposite());
		final BlockState offsetState = level.getBlockState(offsetPos);
		return offsetState.isFaceSturdy(level, offsetPos, direction) || isIcicleWithDirection(offsetState, direction);
	}

	private static boolean isTip(BlockState state, boolean bl) {
		if (!state.is(WWBlocks.ICICLE)) return false;
		final DripstoneThickness thickness = state.getValue(THICKNESS);
		return thickness == DripstoneThickness.TIP || bl && thickness == DripstoneThickness.TIP_MERGE;
	}

	private static boolean isUnmergedTipWithDirection(BlockState state, Direction direction) {
		return isTip(state, false) && state.getValue(TIP_DIRECTION) == direction;
	}

	private static boolean isHangingIcicle(BlockState state) {
		return isIcicleWithDirection(state, Direction.DOWN);
	}

	private static boolean isIceSpike(BlockState blockState) {
		return isIcicleWithDirection(blockState, Direction.UP);
	}

	private static boolean isHangingIcicleStartPos(BlockState state, LevelReader level, BlockPos pos) {
		return isHangingIcicle(state) && !level.getBlockState(pos.above()).is(WWBlocks.ICICLE);
	}

	@Override
	protected boolean isPathfindable(BlockState state, PathComputationType type) {
		return false;
	}

	private static boolean isIcicleWithDirection(BlockState state, Direction direction) {
		return state.is(WWBlocks.ICICLE) && state.getValue(TIP_DIRECTION) == direction;
	}

	public static boolean canGrow(BlockState state, BlockState aboveState) {
		return state.is(WWBlocks.FRAGILE_ICE) || (state.is(WWBlockTags.ICICLE_GROWS_WHEN_UNDER) && isValidWaterForGrowing(aboveState));
	}

	public static boolean canSpreadTo(BlockState state) {
		return state.is(WWBlocks.FRAGILE_ICE) || state.is(WWBlockTags.ICICLE_GROWS_WHEN_UNDER);
	}

	public static boolean isValidWaterForGrowing(BlockState state) {
		return state.is(Blocks.WATER) && state.getFluidState().isSource();
	}

	private static Optional<BlockPos> findBlockVertical(
		LevelAccessor level,
		BlockPos pos,
		Direction.AxisDirection axisDirection,
		BiPredicate<BlockPos, BlockState> biPredicate,
		Predicate<BlockState> predicate,
		int i
	) {
		final Direction direction = Direction.get(axisDirection, Direction.Axis.Y);
		final BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();

		for(int j = 1; j < i; ++j) {
			mutableBlockPos.move(direction);
			final BlockState state = level.getBlockState(mutableBlockPos);
			if (predicate.test(state)) return Optional.of(mutableBlockPos.immutable());
			if (level.isOutsideBuildHeight(mutableBlockPos.getY()) || !biPredicate.test(mutableBlockPos, state)) return Optional.empty();
		}

		return Optional.empty();
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new IcicleBlockEntity(pos, state);
	}

	@Override
	protected RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return !level.isClientSide()
			? createTickerHelper(type, WWBlockEntityTypes.ICICLE, (worldx, pos, statex, blockEntity) -> blockEntity.serverTick(worldx, pos, statex))
			: null;
	}
}
