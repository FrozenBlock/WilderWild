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
import net.minecraft.world.level.block.state.BlockBehaviour;
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
import org.jetbrains.annotations.NotNull;
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

	public IcicleBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(
			this.stateDefinition.any().setValue(TIP_DIRECTION, Direction.UP).setValue(THICKNESS, DripstoneThickness.TIP).setValue(WATERLOGGED, false)
		);
	}

	@Override
	protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		builder.add(TIP_DIRECTION, THICKNESS, WATERLOGGED);
		SnowloggingUtils.appendSnowlogProperties(builder);
	}

	@Override
	public boolean canSurvive(@NotNull BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
		return isValidIciclePlacement(levelReader, blockPos, blockState.getValue(TIP_DIRECTION));
	}

	@Override
	protected @NotNull BlockState updateShape(
		@NotNull BlockState blockState,
		LevelReader levelReader,
		ScheduledTickAccess tickAccess,
		BlockPos blockPos,
		Direction direction,
		BlockPos blockPos2,
		BlockState blockState2,
		RandomSource randomSource
	) {
		if (blockState.getValue(WATERLOGGED)) tickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
		if (direction != Direction.UP && direction != Direction.DOWN) return blockState;

		Direction tipDirection = blockState.getValue(TIP_DIRECTION);
		if (tipDirection == Direction.DOWN && tickAccess.getBlockTicks().hasScheduledTick(blockPos, this)) {
			return blockState;
		} else if (direction == tipDirection.getOpposite() && !this.canSurvive(blockState, levelReader, blockPos)) {
			tickAccess.scheduleTick(blockPos, this, tipDirection == Direction.DOWN ? DELAY_BEFORE_FALLING : 1);
			return blockState;
		} else {
			boolean merged = blockState.getValue(THICKNESS) == DripstoneThickness.TIP_MERGE;
			DripstoneThickness icicleThickness = calculateIcicleThickness(levelReader, blockPos, tipDirection, merged);
			return blockState.setValue(THICKNESS, icicleThickness);
		}
	}

	@Override
	protected void onProjectileHit(@NotNull Level level, BlockState blockState, BlockHitResult blockHitResult, Projectile projectile) {
		if (!level.isClientSide) {
			BlockPos blockPos = blockHitResult.getBlockPos();
			if (level instanceof ServerLevel serverLevel
				&& projectile.mayInteract(serverLevel, blockPos)
				&& projectile.mayBreak(serverLevel)
				&& projectile.getDeltaMovement().length() > 0.4D) {
				level.destroyBlock(blockPos, true);
			}
		}
	}

	public void triggerFall(@NotNull Level level, @NotNull BlockPos blockPos) {
		level.scheduleTick(blockPos, this, DELAY_BEFORE_FALLING);
	}

	@Override
	protected void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
		if (isIceSpike(blockState) && !this.canSurvive(blockState, serverLevel, blockPos)) {
			serverLevel.destroyBlock(blockPos, true);
		} else {
			spawnFallingIcicle(blockState, serverLevel, blockPos);
		}
	}

	@Override
	protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, @NotNull RandomSource randomSource) {
		if (isHangingIcicleStartPos(blockState, serverLevel, blockPos)) {
			if (randomSource.nextFloat() <= 0.165F) {
				growIcicleIfPossible(blockState, serverLevel, blockPos);
			} else if (this.canRandomFall(serverLevel, blockPos, randomSource)) {
				this.triggerFall(serverLevel, blockPos);
			} else if (randomSource.nextFloat() <= 0.135F) {
				placeIciclesNearby(serverLevel, blockPos, 3, randomSource.nextInt(1, 2));
			}
		}
	}

	public static void placeIciclesNearby(@NotNull ServerLevel level, @NotNull BlockPos blockPos, int distance, int maxNewIcicles) {
		if (canGrow(level.getBlockState(blockPos.above()), level.getBlockState(blockPos.above(2)))) {
			Iterator<BlockPos> posesToCheck = BlockPos.betweenClosed(blockPos.offset(-distance, -distance, -distance), blockPos.offset(distance, distance, distance)).iterator();
			int count = 0;
			while (count < maxNewIcicles) {
				if (!posesToCheck.hasNext()) return;

				BlockPos nextPos = posesToCheck.next();
				if (level.getBlockState(nextPos).is(WWBlockTags.ICICLE_GROWS_WHEN_UNDER)) {
					if (IcicleUtils.spreadIcicleOnRandomTick(level, nextPos)) count++;
				}
			}
		}
	}


	private boolean canRandomFall(ServerLevel level, BlockPos pos, @NotNull RandomSource random) {
		if (random.nextFloat() <= 0.075F && level.getBlockState(pos.above()).is(WWBlockTags.ICICLE_FALLS_FROM)) {
			Vec3 centerPos = Vec3.atCenterOf(pos);
			Player player = level.getNearestPlayer(TARGETING_CONDITIONS, centerPos.x(), centerPos.y(), centerPos.z());
			if (player != null) {
				boolean isPlayerAbove = player.blockPosition().getY() > pos.getY();
				double distance = player.distanceToSqr(centerPos);
				return Math.sqrt(distance) <= 32D && (!isPlayerAbove || random.nextFloat() <= 0.25F);
			}
			return random.nextFloat() <= 0.05F;
		}
		return false;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
		LevelAccessor levelAccessor = blockPlaceContext.getLevel();
		BlockPos blockPos = blockPlaceContext.getClickedPos();
		Direction direction = blockPlaceContext.getNearestLookingVerticalDirection().getOpposite();
		Direction direction2 = calculateTipDirection(levelAccessor, blockPos, direction);
		if (direction2 == null) {
			return null;
		} else {
			DripstoneThickness icicleThickness = calculateIcicleThickness(levelAccessor, blockPos, direction2, !blockPlaceContext.isSecondaryUseActive());
			return icicleThickness == null
				? null
				: SnowloggingUtils.getSnowPlacementState(
					this.defaultBlockState()
						.setValue(TIP_DIRECTION, direction2)
						.setValue(THICKNESS, icicleThickness)
						.setValue(WATERLOGGED, levelAccessor.getFluidState(blockPos).getType() == Fluids.WATER),
				blockPlaceContext
			);
		}
	}

	@Override
	protected @NotNull FluidState getFluidState(@NotNull BlockState blockState) {
		return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
	}

	@Override
	protected VoxelShape getOcclusionShape(BlockState blockState) {
		return Shapes.empty();
	}

	@Override
	protected @NotNull VoxelShape getShape(@NotNull BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		DripstoneThickness icicleThickness = blockState.getValue(THICKNESS);
		VoxelShape voxelShape;
		if (icicleThickness == DripstoneThickness.TIP_MERGE) {
			voxelShape = TIP_MERGE_SHAPE;
		} else if (icicleThickness == DripstoneThickness.TIP) {
			if (blockState.getValue(TIP_DIRECTION) == Direction.DOWN) {
				voxelShape = TIP_SHAPE_DOWN;
			} else {
				voxelShape = TIP_SHAPE_UP;
			}
		} else if (icicleThickness == DripstoneThickness.FRUSTUM) {
			voxelShape = FRUSTUM_SHAPE;
		} else if (icicleThickness == DripstoneThickness.MIDDLE) {
			voxelShape = MIDDLE_SHAPE;
		} else {
			voxelShape = BASE_SHAPE;
		}

		Vec3 vec3 = blockState.getOffset(blockPos);
		return voxelShape.move(vec3.x, 0D, vec3.z);
	}

	@Override
	protected @NotNull VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return this.getShape(blockState, blockGetter, blockPos, collisionContext);
	}

	@Override
	protected boolean isCollisionShapeFullBlock(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return false;
	}

	@Override
	protected float getMaxHorizontalOffset() {
		return 0.175F;
	}

	@Override
	public void onBrokenAfterFall(@NotNull Level level, BlockPos blockPos, @NotNull FallingBlockEntity fallingBlockEntity) {
		level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, blockPos, Block.getId(fallingBlockEntity.getBlockState()));
	}

	@Override
	public @NotNull DamageSource getFallDamageSource(@NotNull Entity entity) {
		return entity.damageSources().source(WWDamageTypes.FALLING_ICICLE, entity);
	}

	private static void spawnFallingIcicle(BlockState blockState, ServerLevel serverLevel, @NotNull BlockPos blockPos) {
		BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();
		BlockState blockState2 = blockState;

		while (isHangingIcicle(blockState2)) {
			FallingBlockEntity fallingBlockEntity = FallingBlockEntity.fall(serverLevel, mutableBlockPos, blockState2);
			fallingBlockEntity.disableDrop();
			if (isTip(blockState2, true)) {
				int i = Math.max(1 + blockPos.getY() - mutableBlockPos.getY(), 6);
				fallingBlockEntity.setHurtsEntities(i, 10);
				break;
			}

			mutableBlockPos.move(Direction.DOWN);
			blockState2 = serverLevel.getBlockState(mutableBlockPos);
		}
	}

	@VisibleForTesting
	public static void growIcicleIfPossible(BlockState blockState, @NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos) {
		BlockState aboveState = serverLevel.getBlockState(blockPos.above(1));
		BlockState aboveTwiceState = serverLevel.getBlockState(blockPos.above(2));
		if (canGrow(aboveState, aboveTwiceState)) {
			BlockPos tipPos = findTip(blockState, serverLevel, blockPos, 7, false);
			if (tipPos != null) {
				BlockState tipState = serverLevel.getBlockState(tipPos);
				if (canDrip(tipState) && canTipGrow(tipState, serverLevel, tipPos)) {
					grow(serverLevel, tipPos, Direction.DOWN);
				}
			}
		}
	}

	public static void grow(@NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos, Direction direction) {
		BlockPos blockPos2 = blockPos.relative(direction);
		BlockState blockState = serverLevel.getBlockState(blockPos2);
		if (isUnmergedTipWithDirection(blockState, direction.getOpposite())) {
			createMergedTips(blockState, serverLevel, blockPos2);
		} else if (blockState.isAir() || blockState.is(Blocks.WATER)) {
			createIcicle(serverLevel, blockPos2, direction, DripstoneThickness.TIP);
		}
	}

	private static void createIcicle(@NotNull LevelAccessor levelAccessor, BlockPos blockPos, Direction direction, DripstoneThickness icicleThickness) {
		BlockState blockState = WWBlocks.ICICLE
			.defaultBlockState()
			.setValue(TIP_DIRECTION, direction)
			.setValue(THICKNESS, icicleThickness)
			.setValue(WATERLOGGED, levelAccessor.getFluidState(blockPos).getType() == Fluids.WATER);
		levelAccessor.setBlock(blockPos, blockState, UPDATE_ALL);
	}

	private static void createMergedTips(@NotNull BlockState blockState, LevelAccessor levelAccessor, BlockPos blockPos) {
		boolean isUp = blockState.getValue(TIP_DIRECTION) == Direction.UP;
		createIcicle(levelAccessor, isUp ? blockPos.above() : blockPos, Direction.DOWN, DripstoneThickness.TIP_MERGE);
		createIcicle(levelAccessor, isUp ? blockPos : blockPos.below(), Direction.UP, DripstoneThickness.TIP_MERGE);
	}

	@Nullable
	public static BlockPos findTip(BlockState blockState, LevelAccessor levelAccessor, BlockPos blockPos, int i, boolean bl) {
		if (isTip(blockState, bl)) return blockPos;
		Direction direction = blockState.getValue(TIP_DIRECTION);
		BiPredicate<BlockPos, BlockState> biPredicate = (blockPosx, blockStatex) -> blockStatex.is(WWBlocks.ICICLE)
			&& blockStatex.getValue(TIP_DIRECTION) == direction;

		return findBlockVertical(levelAccessor, blockPos, direction.getAxisDirection(), biPredicate, blockStatex -> isTip(blockStatex, bl), i).orElse(null);
	}

	@Nullable
	private static Direction calculateTipDirection(LevelReader levelReader, BlockPos blockPos, Direction direction) {
		if (isValidIciclePlacement(levelReader, blockPos, direction)) return direction;
		if (!isValidIciclePlacement(levelReader, blockPos, direction.getOpposite())) return null;
		return direction.getOpposite();
	}

	private static DripstoneThickness calculateIcicleThickness(@NotNull LevelReader levelReader, @NotNull BlockPos blockPos, @NotNull Direction direction, boolean bl) {
		Direction direction2 = direction.getOpposite();
		BlockState blockState = levelReader.getBlockState(blockPos.relative(direction));
		if (isIcicleWithDirection(blockState, direction2)) {
			return !bl && blockState.getValue(THICKNESS) != DripstoneThickness.TIP_MERGE ? DripstoneThickness.TIP : DripstoneThickness.TIP_MERGE;
		} else if (!isIcicleWithDirection(blockState, direction)) {
			return DripstoneThickness.TIP;
		} else {
			DripstoneThickness dripstoneThickness = blockState.getValue(THICKNESS);
			if (dripstoneThickness != DripstoneThickness.TIP && dripstoneThickness != DripstoneThickness.TIP_MERGE) {
				BlockState blockState2 = levelReader.getBlockState(blockPos.relative(direction2));
				return !isIcicleWithDirection(blockState2, direction) ? DripstoneThickness.BASE : DripstoneThickness.MIDDLE;
			} else {
				return DripstoneThickness.FRUSTUM;
			}
		}
	}

	public static boolean canDrip(BlockState blockState) {
		return isHangingIcicle(blockState) && blockState.getValue(THICKNESS) == DripstoneThickness.TIP && !blockState.getValue(WATERLOGGED);
	}

	public static boolean canTipGrow(@NotNull BlockState blockState, @NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos) {
		Direction direction = blockState.getValue(TIP_DIRECTION);
		BlockPos blockPos2 = blockPos.relative(direction);
		BlockState blockState2 = serverLevel.getBlockState(blockPos2);
		if (!blockState2.getFluidState().isEmpty()) {
			return false;
		} else {
			return blockState2.isAir() || isUnmergedTipWithDirection(blockState2, direction.getOpposite());
		}
	}

	private static boolean isValidIciclePlacement(@NotNull LevelReader levelReader, @NotNull BlockPos blockPos, @NotNull Direction direction) {
		BlockPos blockPos2 = blockPos.relative(direction.getOpposite());
		BlockState blockState = levelReader.getBlockState(blockPos2);
		return blockState.isFaceSturdy(levelReader, blockPos2, direction) || isIcicleWithDirection(blockState, direction);
	}

	private static boolean isTip(@NotNull BlockState blockState, boolean bl) {
		if (!blockState.is(WWBlocks.ICICLE)) return false;
		DripstoneThickness dripstoneThickness = blockState.getValue(THICKNESS);
		return dripstoneThickness == DripstoneThickness.TIP || bl && dripstoneThickness == DripstoneThickness.TIP_MERGE;
	}

	private static boolean isUnmergedTipWithDirection(BlockState blockState, Direction direction) {
		return isTip(blockState, false) && blockState.getValue(TIP_DIRECTION) == direction;
	}

	private static boolean isHangingIcicle(BlockState blockState) {
		return isIcicleWithDirection(blockState, Direction.DOWN);
	}

	private static boolean isIceSpike(BlockState blockState) {
		return isIcicleWithDirection(blockState, Direction.UP);
	}

	private static boolean isHangingIcicleStartPos(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
		return isHangingIcicle(blockState) && !levelReader.getBlockState(blockPos.above()).is(WWBlocks.ICICLE);
	}

	@Override
	protected boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType) {
		return false;
	}

	private static boolean isIcicleWithDirection(@NotNull BlockState blockState, Direction direction) {
		return blockState.is(WWBlocks.ICICLE) && blockState.getValue(TIP_DIRECTION) == direction;
	}

	public static boolean canGrow(@NotNull BlockState blockState, BlockState aboveState) {
		return blockState.is(WWBlocks.FRAGILE_ICE) || (blockState.is(WWBlockTags.ICICLE_GROWS_WHEN_UNDER) && isValidWaterForGrowing(aboveState));
	}

	public static boolean canSpreadTo(@NotNull BlockState blockState) {
		return blockState.is(WWBlocks.FRAGILE_ICE) || blockState.is(WWBlockTags.ICICLE_GROWS_WHEN_UNDER);
	}

	public static boolean isValidWaterForGrowing(@NotNull BlockState blockState) {
		return blockState.is(Blocks.WATER) && blockState.getFluidState().isSource();
	}

	private static Optional<BlockPos> findBlockVertical(
		LevelAccessor levelAccessor,
		@NotNull BlockPos blockPos,
		Direction.AxisDirection axisDirection,
		BiPredicate<BlockPos, BlockState> biPredicate,
		Predicate<BlockState> predicate,
		int i
	) {
		Direction direction = Direction.get(axisDirection, Direction.Axis.Y);
		BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();

		for(int j = 1; j < i; ++j) {
			mutableBlockPos.move(direction);
			BlockState blockState = levelAccessor.getBlockState(mutableBlockPos);
			if (predicate.test(blockState)) {
				return Optional.of(mutableBlockPos.immutable());
			}

			if (levelAccessor.isOutsideBuildHeight(mutableBlockPos.getY()) || !biPredicate.test(mutableBlockPos, blockState)) {
				return Optional.empty();
			}
		}

		return Optional.empty();
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new IcicleBlockEntity(blockPos, blockState);
	}

	@Override
	protected @NotNull RenderShape getRenderShape(BlockState blockState) {
		return RenderShape.MODEL;
	}

	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, BlockState blockState, BlockEntityType<T> type) {
		return !level.isClientSide ? createTickerHelper(type, WWBlockEntityTypes.ICICLE, (worldx, pos, statex, blockEntity) ->
			blockEntity.serverTick(worldx, pos, statex)
		) : null;
	}
}
