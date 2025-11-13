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
import net.frozenblock.wilderwild.registry.WWEnvironmentAttributes;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class PlanktonBlock extends AlgaeBlock {
	public static final MapCodec<PlanktonBlock> CODEC = simpleCodec(PlanktonBlock::new);
	protected static final VoxelShape SHAPE = Block.box(0D, 0D, 0D, 16D, 1D, 16D);
	private static final BooleanProperty GLOWING = WWBlockStateProperties.GLOWING;
	public static final int LIGHT_LEVEL = 3;
	public static final int MIN_PARTICLE_SPAWN_WIDTH = -5;
	public static final int MAX_PARTICLE_SPAWN_WIDTH = 5;
	public static final int PARTICLE_SPAWN_SEARCH_DISTANCE = 24;
	public static final float PARTICLE_SPAWN_CHANCE = 0.5F;
	public static final float PARTICLE_SPAWN_CHANCE_GLOWING = 0.75F;

	public PlanktonBlock(@NotNull BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.getStateDefinition().any().setValue(GLOWING, Boolean.FALSE));
	}

	@NotNull
	@Override
	protected MapCodec<? extends PlanktonBlock> codec() {
		return CODEC;
	}

	@Override
	@NotNull
	public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return SHAPE;
	}

	@Override
	public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (!this.canSurvive(state, level, pos)) {
			level.destroyBlock(pos, false);
			return;
		}
		this.tryChangingState(state, level, pos);
	}

	@Override
	protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		this.tryChangingState(state, level, pos);
		super.randomTick(state, level, pos, random);
	}

	@Override
	public void animateTick(BlockState state, Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

		final boolean glowing = isGlowing(state);
		final ParticleOptions particle = glowing ? WWParticleTypes.GLOWING_PLANKTON : WWParticleTypes.PLANKTON;
		final float particleChance = glowing ? PARTICLE_SPAWN_CHANCE_GLOWING : PARTICLE_SPAWN_CHANCE;

		if (random.nextFloat() > particleChance) return;
		final BlockPos randomPos = new BlockPos(
			pos.getX() + Mth.nextInt(random, MIN_PARTICLE_SPAWN_WIDTH, MAX_PARTICLE_SPAWN_WIDTH),
			pos.getY(),
			pos.getZ() + Mth.nextInt(random, MIN_PARTICLE_SPAWN_WIDTH, MAX_PARTICLE_SPAWN_WIDTH)
		);
		mutable.set(randomPos);
		int maxPossibleDepth = 0;

		for (int steps = 1; steps <= PARTICLE_SPAWN_SEARCH_DISTANCE; steps++) {
			mutable.move(Direction.DOWN);
			final BlockState particlePosState = level.getBlockState(mutable);
			if (!particlePosState.is(Blocks.WATER) || particlePosState.getFluidState().getAmount() != FluidState.AMOUNT_FULL) break;
			maxPossibleDepth += 1;
		}

		mutable.set(randomPos);
		if (maxPossibleDepth > 0) {
			mutable.move(Direction.DOWN, random.nextIntBetweenInclusive(1, maxPossibleDepth));
			level.addParticle(
				particle,
				mutable.getX() + random.nextDouble(),
				mutable.getY() + random.nextDouble(),
				mutable.getZ() + random.nextDouble(),
				0D, 0D, 0D
			);
		}
	}

	@Override
	public void entityInside(
		@NotNull BlockState state,
		@NotNull Level level,
		@NotNull BlockPos pos,
		@NotNull Entity entity,
		InsideBlockEffectApplier insideBlockEffectApplier,
		boolean bl
	) {
		if (entity.getY() > pos.getY() + SHAPE.max(Direction.Axis.Y)) return;
		if (!(level instanceof ServerLevel serverLevel) || isGlowing(state)) return;
		this.tryChangingState(state, serverLevel, pos);
	}

	public static boolean isGlowing(@NotNull BlockState blockState) {
		return blockState.getValue(GLOWING);
	}

	private void tryChangingState(BlockState state, @NotNull ServerLevel level, BlockPos pos) {
		final boolean shouldGlow = level.environmentAttributes().getValue(WWEnvironmentAttributes.PLANKTON_GLOWING, pos);
		if (shouldGlow == isGlowing(state)) return;

		level.setBlockAndUpdate(pos, state.setValue(GLOWING, shouldGlow));
		level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(state));
		BlockPos.betweenClosed(pos.offset(-3, -2, -3), pos.offset(3, 2, 3)).forEach(blockPos2 -> {
			final BlockState foundState = level.getBlockState(blockPos2);
			if (foundState != state) return;
			int delay = (int) (Math.sqrt(pos.distSqr(blockPos2)) * 5F);
			level.scheduleTick(blockPos2, state.getBlock(), delay);
		});
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		builder.add(GLOWING);
	}
}
