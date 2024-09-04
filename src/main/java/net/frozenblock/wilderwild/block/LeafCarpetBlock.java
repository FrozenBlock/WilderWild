/*
 * Copyright 2023-2024 FrozenBlock
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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;

public class LeafCarpetBlock extends CarpetBlock {
	public static final MapCodec<LeafCarpetBlock> CODEC = simpleCodec(LeafCarpetBlock::new);
	public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;

	public LeafCarpetBlock(Properties settings) {
		super(settings);
		this.registerDefaultState(this.stateDefinition.any().setValue(PERSISTENT, false));
	}

	@Override
	public @NotNull MapCodec<? extends LeafCarpetBlock> codec() {
		return CODEC;
	}

	@Override
	protected boolean isRandomlyTicking(@NotNull BlockState state) {
		return this.decaying(state);
	}

	@Override
	protected void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		if (this.decaying(state) && random.nextInt(40) == 0) {
			dropResources(state, world, pos);
			world.removeBlock(pos, false);
		}
	}

	protected boolean decaying(@NotNull BlockState state) {
		return !state.getValue(PERSISTENT);
	}

	@Override
	protected void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
		super.onRemove(state, world, pos, newState, moved);
		this.spawnLeafParticles(world, pos, null, true);
	}

	@Override
	protected void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
		super.entityInside(state, world, pos, entity);
		this.spawnLeafParticles(world, pos, entity, false);
	}

	public void spawnLeafParticles(Level level, BlockPos pos, Entity entity, boolean destroy) {
		if (level instanceof ServerLevel serverLevel) {
			float chance = destroy ? 1F : 0.005F;
			int count = destroy ? 4 : 1;
			if (!destroy && entity != null) {
				chance = (float) (entity.getDeltaMovement().length() * 0.15D);
			}
			if (serverLevel.random.nextFloat() <= chance) {
				Optional<ParticleOptions> particle = LeafParticleRegistry.getParticleForCarpet(this);
				particle.ifPresent(particleOptions -> serverLevel.sendParticles(
					particleOptions,
					pos.getX() + 0.5D,
					pos.getY() + 0.1D,
					pos.getZ() + 0.5D,
					count,
					0.3D,
					0D,
					0.3D,
					0.05D
				));
			}
		}
	}

	@Override
	public boolean canSurvive(BlockState state, @NotNull LevelReader world, @NotNull BlockPos pos) {
		BlockState belowState = world.getBlockState(pos.below());
		return Block.isFaceFull(belowState.getCollisionShape(world, pos.below()), Direction.UP);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		builder.add(PERSISTENT);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(PERSISTENT, true);
	}

	public static class LeafParticleRegistry {
		private static final Map<LeafCarpetBlock, ParticleOptions> LEAF_PARTICLE_MAP = new LinkedHashMap<>();

		public static ParticleOptions registerLeafParticle(@NotNull LeafCarpetBlock leafCarpet, @NotNull ParticleOptions particleOptions) {
			LEAF_PARTICLE_MAP.put(leafCarpet, particleOptions);
			return particleOptions;
		}

		public static Optional<ParticleOptions> getParticleForCarpet(@NotNull LeafCarpetBlock leafCarpet) {
			return Optional.ofNullable(LEAF_PARTICLE_MAP.getOrDefault(leafCarpet, null));
		}
	}
}
