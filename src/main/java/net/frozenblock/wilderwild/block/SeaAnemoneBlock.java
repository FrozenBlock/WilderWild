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
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWEnvironmentAttributes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class SeaAnemoneBlock extends VegetationBlock implements LiquidBlockContainer {
	public static final MapCodec<SeaAnemoneBlock> CODEC = simpleCodec(SeaAnemoneBlock::new);
	private static final VoxelShape SHAPE = Block.box(4D, 0D, 4D, 12D, 8D, 12D);
	private static final BooleanProperty GLOWING = WWBlockStateProperties.GLOWING;
	public static final int LIGHT_LEVEL = 4;

	public SeaAnemoneBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(GLOWING, false));
	}

	@Override
	public MapCodec<SeaAnemoneBlock> codec() {
		return CODEC;
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
		return state.isFaceSturdy(level, pos, Direction.UP) && !state.is(Blocks.MAGMA_BLOCK) && !state.is(WWBlocks.GEYSER);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.isValidWaterToReplace(context.getLevel(), context.getClickedPos()) ? super.getStateForPlacement(context) : null;
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
		final BlockState updatedShape = super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
		if (!updatedShape.isAir()) scheduledTickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		return updatedShape;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(GLOWING);
	}

	@Override
	protected void randomTick(BlockState state, ServerLevel level, BlockPos blockPos, RandomSource randomSource) {
		if (this.tryChangingState(state, level, blockPos, randomSource)) {
			level.sendParticles(
				ParticleTypes.BUBBLE,
				blockPos.getX() + 0.5D, blockPos.getY() + 0.5125D, blockPos.getZ() + 0.5D,
				randomSource.nextInt(1, 3),
				0D, 0D, 0D,
				0.05D
			);
		}

		super.randomTick(state, level, blockPos, randomSource);
	}

	@Override
	protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (this.tryChangingState(state, level, pos, random)) {
			level.sendParticles(
				ParticleTypes.BUBBLE,
				pos.getX() + 0.5D, pos.getY() + 0.5125D, pos.getZ() + 0.5D,
				random.nextInt(1, 3),
				0D, 0D, 0D,
				0.05D
			);
		}

		super.tick(state, level, pos, random);
	}

	public static boolean isGlowing(BlockState state) {
		return state.getValue(GLOWING);
	}

	private boolean tryChangingState(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		final boolean shouldGlow = level.environmentAttributes().getValue(WWEnvironmentAttributes.SEA_ANEMONE_GLOWING, pos);
		if (shouldGlow == isGlowing(state)) return false;

		level.setBlockAndUpdate(pos, state.setValue(GLOWING, shouldGlow));
		level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(state));
		BlockPos.betweenClosed(pos.offset(-3, -2, -3), pos.offset(3, 2, 3)).forEach(blockPos2 -> {
			final BlockState foundState = level.getBlockState(blockPos2);
			if (foundState != state) return;
			final double distance = Math.sqrt(pos.distSqr(blockPos2));
			final int delay = random.nextIntBetweenInclusive((int) (distance * 40D), (int) (distance * 60D));
			level.scheduleTick(blockPos2, state.getBlock(), delay);
		});
		return true;
	}

	@Override
	public boolean canPlaceLiquid(@Nullable LivingEntity livingEntity, BlockGetter level, BlockPos pos, BlockState state, Fluid fluid) {
		return false;
	}

	@Override
	public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
		return false;
	}

	@Override
	protected FluidState getFluidState(BlockState state) {
		return Fluids.WATER.getSource(false);
	}

	private boolean isValidWaterToReplace(LevelReader level, BlockPos pos) {
		final BlockState blockState = level.getBlockState(pos);
		final FluidState fluidState = blockState.getFluidState();
		return (blockState.is(Blocks.WATER) || (blockState.canBeReplaced() && fluidState.is(FluidTags.WATER))) && fluidState.getAmount() == FluidState.AMOUNT_FULL;
	}
}
