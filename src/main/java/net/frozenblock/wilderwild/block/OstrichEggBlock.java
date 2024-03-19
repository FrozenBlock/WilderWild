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

import net.frozenblock.wilderwild.entity.Ostrich;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class OstrichEggBlock extends Block {
	public static final int MAX_HATCH_LEVEL = 2;
	public static final IntegerProperty HATCH = BlockStateProperties.HATCH;
	private static final VoxelShape SHAPE = Block.box(5D, 0D, 5D, 11D, 8D, 11D);

	public OstrichEggBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(HATCH, 0));
	}

	public static boolean isSafeToHatch(@NotNull Level level, @NotNull BlockPos belowPos) {
		return level.getBlockState(belowPos).isFaceSturdy(level, belowPos, Direction.UP);
	}

	@Override
	public void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		builder.add(HATCH);
	}

	@NotNull
	@Override
	public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return SHAPE;
	}

	public int getHatchLevel(@NotNull BlockState state) {
		return state.getValue(HATCH);
	}

	private boolean isReadyToHatch(BlockState state) {
		return this.getHatchLevel(state) == MAX_HATCH_LEVEL;
	}

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (shouldUpdateHatchLevel(level, pos)) {
			if (!this.isReadyToHatch(state)) {
				level.playSound(null, pos, RegisterSounds.BLOCK_OSTRICH_EGG_CRACK, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
				level.setBlock(pos, state.cycle(HATCH), UPDATE_CLIENTS);
			} else {
				this.hatchOstrichEgg(level, pos, random);
			}
		}
	}

	@Override
	public void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean movedByPiston) {
		super.onPlace(state, level, pos, oldState, movedByPiston);
		level.levelEvent(LevelEvent.PARTICLES_EGG_CRACK, pos, 0);
	}

	private boolean shouldUpdateHatchLevel(@NotNull Level level, @NotNull BlockPos blockPos) {
		if (!isSafeToHatch(level, blockPos.below())) return false;
		if (level.isDay()) {
			return true;
		} else {
			return level.getRandom().nextInt(500) == 0;
		}
	}

	private void hatchOstrichEgg(@NotNull ServerLevel level, BlockPos pos, @NotNull RandomSource random) {
		level.playSound(null, pos, RegisterSounds.BLOCK_OSTRICH_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
		this.destroyBlock(level, pos);
		this.spawnOstrich(level, pos, random);
	}

	private void destroyBlock(@NotNull Level level, BlockPos pos) {
		level.destroyBlock(pos, false);
	}

	private void spawnOstrich(ServerLevel level, BlockPos pos, @NotNull RandomSource random) {
		Ostrich ostrich = RegisterEntities.OSTRICH.create(level);
		if (ostrich != null) {
			ostrich.setBaby(true);
			ostrich.moveTo(
				pos.getX() + 0.5D,
				pos.getY(),
				pos.getZ() + 0.5D,
				random.nextInt(1, 361),
				0F
			);
			ostrich.setTamed(true);
			level.addFreshEntity(ostrich);
		}
	}

	@Override
	public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
		return false;
	}

}
