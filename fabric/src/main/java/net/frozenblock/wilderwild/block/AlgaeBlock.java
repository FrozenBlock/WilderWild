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
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.frozenblock.wilderwild.tag.WWEntityTypeTags;
import net.frozenblock.wilderwild.tag.WWFluidTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AlgaeBlock extends VegetationBlock implements BonemealableBlock {
	public static final double ENTITY_SLOWDOWN = 0.8D;
	protected static final VoxelShape SHAPE = Block.box(0D, 0D, 0D, 16D, 1D, 16D);
	protected static final VoxelShape ENTITY_INSIDE_SHAPE = Block.box(0D, -0.5D, 0D, 16D, 0D, 16D);
	public static final MapCodec<AlgaeBlock> CODEC = simpleCodec(AlgaeBlock::new);

	public AlgaeBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected MapCodec<? extends AlgaeBlock> codec() {
		return CODEC;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	protected VoxelShape getEntityInsideCollisionShape(BlockState state, BlockGetter level, BlockPos pos, Entity entity) {
		return ENTITY_INSIDE_SHAPE;
	}

	@Override
	protected boolean mayPlaceOn(final BlockState state, final BlockGetter level, final BlockPos pos) {
		final FluidState fluidAbove = level.getFluidState(pos.above());
		return (state.getFluidState().is(this.fluidSupportTag()) || state.is(this.blockSupportTag())) && fluidAbove.is(Fluids.EMPTY);
	}

	public TagKey<Fluid> fluidSupportTag() {
		return WWFluidTags.SUPPORTS_ALGAE;
	}

	public TagKey<Block> blockSupportTag() {
		return WWBlockTags.SUPPORTS_ALGAE;
	}

	@Override
	protected BlockState updateShape(
		BlockState state,
		LevelReader level,
		ScheduledTickAccess ticks,
		BlockPos pos,
		Direction direction,
		BlockPos neighborPos,
		BlockState neighborState,
		RandomSource random
	) {
		return !this.canSurvive(state, level, pos)
			? Blocks.AIR.defaultBlockState()
			: super.updateShape(state, level, ticks, pos, direction, neighborPos, neighborState, random);
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!this.canSurvive(state, level, pos)) level.destroyBlock(pos, false);
	}

	@Override
	public void entityInside(
		BlockState state,
		Level level,
		BlockPos pos,
		Entity entity,
		InsideBlockEffectApplier effectApplier,
		boolean isPrecise
	) {
		if (entity.is(EntityTypes.FALLING_BLOCK)) level.destroyBlock(pos, false);
		if (!entity.is(WWEntityTypeTags.CAN_SWIM_IN_ALGAE)) {
			if (entity instanceof Player player && player.getAbilities().flying) return;
			entity.resetFallDistance();
			entity.setDeltaMovement(entity.getDeltaMovement().scale(ENTITY_SLOWDOWN));
		}
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		for (Direction direction : Direction.Plane.HORIZONTAL) {
			mutable.setWithOffset(pos, direction);
			if (level.getBlockState(mutable).isAir() && this.canSurvive(this.defaultBlockState(), level, mutable)) return true;
		}
		return false;
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		for (Direction direction : Direction.Plane.HORIZONTAL.shuffledCopy(random)) {
			mutable.setWithOffset(pos, direction);
			if (!level.getBlockState(mutable).isAir() || !this.canSurvive(this.defaultBlockState(), level, mutable)) continue;
			level.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, mutable, 0);
			level.setBlockAndUpdate(mutable, this.defaultBlockState());
			return;
		}
	}

	public static boolean hasNearbyAlgae(LevelAccessor level, BlockPos pos, int distance, int threshold) {
		final Iterator<BlockPos> posesToCheck = BlockPos.betweenClosed(pos.offset(-distance, 0, -distance), pos.offset(distance, 0, distance)).iterator();
		int count = 0;
		while (count < threshold) {
			if (!posesToCheck.hasNext()) return false;
			if (level.getBlockState(posesToCheck.next()).is(WWBlocks.ALGAE)) count = count + 1;
		}
		return true;
	}
}
