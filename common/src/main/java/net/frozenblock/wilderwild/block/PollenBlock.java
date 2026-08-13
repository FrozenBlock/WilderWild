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

import net.frozenblock.lib.particle.api.ParticleSpawner;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceSpreadeableBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.state.BlockState;

public class PollenBlock extends MultifaceSpreadeableBlock {
	public static final ParticleSpawner PARTICLE_SPAWNER = new ParticleSpawner(1F, 5, 10, -10, 7) {
		@Override
		public boolean canSpawnAtPos(Level level, BlockPos pos) {
			return !level.isRainingAt(pos);
		}

		@Override
		public ParticleOptions selectParticleOptions(Level level, BlockPos pos, RandomSource random) {
			return WWParticleTypes.POLLEN.get();
		}
	};
	private final MultifaceSpreader spreader = new MultifaceSpreader(new PollenSpreaderConfig());

	public PollenBlock(Properties properties) {
		super(properties);
	}

	public static boolean canAttachToNoWater(BlockGetter level, Direction direction, BlockPos pos, BlockState state) {
		return canAttachTo(level, direction, pos, state) && !level.getBlockState(pos).is(Blocks.WATER);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		if (level.getBlockState(pos).is(Blocks.WATER)) return false;
		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		for (Direction direction : DIRECTIONS) {
			if (!hasFace(state, direction)) continue;
			return canAttachToNoWater(level, direction, mutable.setWithOffset(pos, direction), level.getBlockState(mutable));
		}
		return false;
	}

	@Override
	public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
		return !context.getItemInHand().is(state.getBlock().asItem()) || super.canBeReplaced(state, context);
	}

	@Override
	public boolean isValidStateForPlacement(BlockGetter level, BlockState state, BlockPos pos, Direction direction) {
		if (!state.getFluidState().isEmpty()) return false;
		return super.isValidStateForPlacement(level, state, pos, direction);
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (!WWBlockConfig.POLLEN_PARTICLES.get()) return;
		PARTICLE_SPAWNER.tick(level, pos, random);
	}

	@Override
	public MultifaceSpreader getSpreader() {
		return this.spreader;
	}

	public class PollenSpreaderConfig extends MultifaceSpreader.DefaultSpreaderConfig {

		public PollenSpreaderConfig() {
			super(PollenBlock.this);
		}

		@Override
		public boolean stateCanBeReplaced(BlockGetter level, BlockPos sourcePos, BlockPos placementPos, Direction placementDirection, BlockState existingState) {
			return existingState.getFluidState().isEmpty() && super.stateCanBeReplaced(level, sourcePos, placementPos, placementDirection, existingState);
		}
	}
}
