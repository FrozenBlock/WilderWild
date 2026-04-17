/*
 * Copyright 2025-2026 FrozenBlock
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
import java.util.Iterator;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.block.entity.IcicleBlockEntity;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.frozenblock.wilderwild.registry.WWBlockEntityTypes;
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
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SpeleothemBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class IcicleBlock extends SpeleothemBlock implements EntityBlock, Fallable, SimpleWaterloggedBlock {
	private static final TargetingConditions TARGETING_CONDITIONS = TargetingConditions.forNonCombat().ignoreInvisibilityTesting().ignoreLineOfSight().range(32D);
	private static final VoxelShape SHAPE_TIP_MERGE = Block.box(6D, 0D, 6D, 10D, 16D, 10D);
	private static final VoxelShape SHAPE_TIP_UP = Block.box(6D, 0D, 6D, 10D, 12D, 10D);
	private static final VoxelShape SHAPE_TIP_DOWN = Block.box(6D, 4D, 6D, 10D, 16D, 10D);
	private static final VoxelShape SHAPE_FRUSTUM = Block.box(5D, 0D, 5D, 11D, 16D, 11D);
	private static final VoxelShape SHAPE_MIDDLE = Block.box(5D, 0D, 5D, 11D, 16D, 11D);
	private static final VoxelShape SHAPE_BASE = Block.box(3D, 0D, 3D, 13D, 16D, 13D);
	private static final float MAX_HORIZONTAL_OFFSET = (float) SHAPE_BASE.min(Direction.Axis.X);
	public static final MapCodec<IcicleBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		BlockState.CODEC.fieldOf("block_to_grow_on").forGetter(icicleBlock -> icicleBlock.blockToGrowOn),
		propertiesCodec()
	).apply(instance, IcicleBlock::new));

	public IcicleBlock(BlockState blockToGrowOn, Properties properties) {
		super(blockToGrowOn, properties);
	}

	@Override
	public MapCodec<? extends IcicleBlock> codec() {
		return CODEC;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		SnowloggingUtils.appendSnowlogProperties(builder);
	}

	@Override
	protected void onProjectileHit(Level level, BlockState state, BlockHitResult hitResult, Projectile projectile) {
		if (level.isClientSide() || !(level instanceof ServerLevel serverLevel)) return;
		final BlockPos pos = hitResult.getBlockPos();
		if (!projectile.mayInteract(serverLevel, pos) || !projectile.mayBreak(serverLevel) || projectile.getDeltaMovement().length() <= 0.4D) return;
		level.destroyBlock(pos, true);
	}

	public void triggerFall(Level level, BlockPos blockPos) {
		level.scheduleTick(blockPos, this, DELAY_BEFORE_FALLING);
	}

	@Override
	protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!isStalactiteStartPos(state, level, pos)) return;
		if (random.nextFloat() <= 0.165F) {
			this.growStalactiteOrStalagmiteIfPossible(state, level, pos, random);
		} else if (this.canRandomFall(level, pos, random)) {
			this.triggerFall(level, pos);
		} else if (random.nextFloat() <= 0.135F) {
			this.placeIciclesNearby(level, pos, 3, random.nextInt(1, 2));
		}
	}

	public void placeIciclesNearby(ServerLevel level, BlockPos pos, int distance, int maxNewIcicles) {
		if (!this.canGrow(level, pos)) return;

		final Iterator<BlockPos> posesToCheck = BlockPos.betweenClosed(pos.offset(-distance, -distance, -distance), pos.offset(distance, distance, distance)).iterator();
		int count = 0;
		while (count < maxNewIcicles) {
			if (!posesToCheck.hasNext()) return;
			final BlockPos nextPos = posesToCheck.next();
			if (level.getBlockState(nextPos).is(WWBlockTags.ICICLE_REPLACEABLE) && IcicleUtils.spreadIcicleOnRandomTick(level, nextPos)) count++;
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

	@Override
	protected VoxelShape getOcclusionShape(BlockState state) {
		return Shapes.empty();
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		VoxelShape shape = switch (state.getValue(THICKNESS)) {
			case TIP_MERGE -> SHAPE_TIP_MERGE;
			case TIP -> state.getValue(TIP_DIRECTION) == Direction.DOWN ? SHAPE_TIP_DOWN : SHAPE_TIP_UP;
			case FRUSTUM -> SHAPE_FRUSTUM;
			case MIDDLE -> SHAPE_MIDDLE;
			case BASE -> SHAPE_BASE;
		};
		return shape.move(state.getOffset(pos));
	}

	@Override
	protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return this.getShape(state, level, pos, context);
	}

	@Override
	protected float getMaxHorizontalOffset() {
		return MAX_HORIZONTAL_OFFSET;
	}

	@Override
	public void onBrokenAfterFall(Level level, BlockPos pos, FallingBlockEntity fallingBlock) {
		level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(fallingBlock.getBlockState()));
	}

	@Override
	public DamageSource getFallDamageSource(Entity entity) {
		return entity.damageSources().source(WWDamageTypes.FALLING_ICICLE, entity);
	}

	@Override
	protected boolean canGrow(LevelReader level, BlockPos pos) {
		final BlockState rootState = level.getBlockState(pos.above(1));
		final BlockState stateAbove = level.getBlockState(pos.above(2));
		final FluidState fluidState = stateAbove.getFluidState();
		return rootState.is(this.blockToGrowOn.getBlock()) || (rootState.is(WWBlockTags.ICICLE_GROWS_WHEN_UNDER) && (fluidState.is(Fluids.WATER) && fluidState.isSource()));
	}

	public boolean canSpreadTo(BlockState state) {
		return state.is(this.blockToGrowOn.getBlock()) || state.is(WWBlockTags.ICICLE_GROWS_WHEN_UNDER);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new IcicleBlockEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		if (level.isClientSide() || type != WWBlockEntityTypes.ICICLE) return null;
		return (levelx, pos, statex, blockEntity) -> ((IcicleBlockEntity)blockEntity).serverTick(levelx, pos, statex);
	}
}
