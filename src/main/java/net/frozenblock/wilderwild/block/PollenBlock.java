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
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
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
	public static final MapCodec<PollenBlock> CODEC = simpleCodec(PollenBlock::new);
	public static final int MIN_PARTICLE_SPAWN_WIDTH = -10;
	public static final int MAX_PARTICLE_SPAWN_WIDTH = 10;
	public static final int MIN_PARTICLE_SPAWN_HEIGHT = -10;
	public static final int MAX_PARTICLE_SPAWN_HEIGHT = 7;
	public static final int PARTICLE_SPAWN_ATTEMPTS = 5;
	private final MultifaceSpreader spreader = new MultifaceSpreader(new PollenSpreaderConfig());

	public PollenBlock(Properties properties) {
		super(properties);
	}

	public static boolean canAttachToNoWater(BlockGetter level, Direction direction, BlockPos pos, BlockState state) {
		return canAttachTo(level, direction, pos, state) && !level.getBlockState(pos).is(Blocks.WATER);
	}

	@Override
	public MapCodec<? extends PollenBlock> codec() {
		return CODEC;
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
		if (!WWBlockConfig.Client.POLLEN_ENABLED) return;

		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		for (int l = 0; l < PARTICLE_SPAWN_ATTEMPTS; ++l) {
			mutable.setWithOffset(
				pos,
				Mth.nextInt(random, MIN_PARTICLE_SPAWN_WIDTH, MAX_PARTICLE_SPAWN_WIDTH),
				Mth.nextInt(random, MIN_PARTICLE_SPAWN_HEIGHT, MAX_PARTICLE_SPAWN_HEIGHT),
				Mth.nextInt(random, MIN_PARTICLE_SPAWN_WIDTH, MAX_PARTICLE_SPAWN_WIDTH)
			);
			final BlockState spawningInState = level.getBlockState(mutable);
			if (spawningInState.isCollisionShapeFullBlock(level, mutable) || level.isRainingAt(mutable)) continue;
			level.addParticle(
				WWParticleTypes.POLLEN,
				mutable.getX() + random.nextDouble(),
				mutable.getY() + random.nextDouble(),
				mutable.getZ() + random.nextDouble(),
				0D, 0D, 0D
			);
		}
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
		public boolean stateCanBeReplaced(BlockGetter view, BlockPos posA, BlockPos posB, Direction direction, BlockState state) {
			return state.getFluidState().isEmpty() && super.stateCanBeReplaced(view, posA, posB, direction, state);
		}
	}
}
