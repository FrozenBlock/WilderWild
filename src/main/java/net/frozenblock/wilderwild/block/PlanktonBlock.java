/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.block;

import com.mojang.serialization.MapCodec;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
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
	public static final int MIN_PARTICLE_SPAWN_HEIGHT = -7;
	public static final int MAX_PARTICLE_SPAWN_HEIGHT = -1;
	public static final float PARTICLE_SPAWN_CHANCE = 0.5F;
	public static final float PARTICLE_SPAWN_CHANCE_GLOWING = 0.75F;

	public PlanktonBlock(@NotNull BlockBehaviour.Properties settings) {
		super(settings);
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
	public void animateTick(BlockState blockState, Level level, @NotNull BlockPos blockPos, @NotNull RandomSource random) {
		int i = blockPos.getX();
		int j = blockPos.getY();
		int k = blockPos.getZ();
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

		boolean glowing = isGlowing(blockState);
		ParticleOptions particle = glowing ? WWParticleTypes.GLOWING_PLANKTON : WWParticleTypes.PLANKTON;
		float particleChance = glowing ? PARTICLE_SPAWN_CHANCE_GLOWING : PARTICLE_SPAWN_CHANCE;

		if (random.nextFloat() <= particleChance) {
			mutable.set(
					i + Mth.nextInt(random, MIN_PARTICLE_SPAWN_WIDTH, MAX_PARTICLE_SPAWN_WIDTH),
					j + Mth.nextInt(random, MIN_PARTICLE_SPAWN_HEIGHT, MAX_PARTICLE_SPAWN_HEIGHT),
					k + Mth.nextInt(random, MIN_PARTICLE_SPAWN_WIDTH, MAX_PARTICLE_SPAWN_WIDTH)
			);
			BlockState particlePosState = level.getBlockState(mutable);
			if (particlePosState.is(Blocks.WATER) && particlePosState.getFluidState().getAmount() == 8) {
				level.addParticle(
						particle,
						mutable.getX() + random.nextDouble(),
						mutable.getY() + random.nextDouble(),
						mutable.getZ() + random.nextDouble(),
						0D,
						0D,
						0D
				);
			}
		}
	}

	@Override
	public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
		if (entity.getY() <= pos.getY() + SHAPE.max(Direction.Axis.Y)) {
			if (!isGlowing(state) && level instanceof ServerLevel serverLevel) {
				this.tryChangingState(state, serverLevel, pos);
			}
		}
	}

	public static boolean isGlowing(@NotNull BlockState blockState) {
		return blockState.getValue(GLOWING);
	}

	private void tryChangingState(BlockState blockState, @NotNull ServerLevel serverLevel, BlockPos blockPos) {
		if (!serverLevel.dimensionType().natural()) return;
		if (serverLevel.isDay() != isGlowing(blockState)) return;
		boolean setGlowing = !isGlowing(blockState);
		serverLevel.setBlock(blockPos, blockState.setValue(GLOWING, setGlowing), UPDATE_ALL);
		serverLevel.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(blockState));
		BlockPos.betweenClosed(blockPos.offset(-3, -2, -3), blockPos.offset(3, 2, 3)).forEach((blockPos2) -> {
			BlockState foundState = serverLevel.getBlockState(blockPos2);
			if (foundState == blockState) {
				int delay = (int) (Math.sqrt(blockPos.distSqr(blockPos2)) * 5F);
				serverLevel.scheduleTick(blockPos2, blockState.getBlock(), delay);
			}
		});
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		builder.add(GLOWING);
	}
}
