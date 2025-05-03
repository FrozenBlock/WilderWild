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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class PollenBlock extends MultifaceBlock {
	public static final MapCodec<PollenBlock> CODEC = simpleCodec(PollenBlock::new);
	public static final int MIN_PARTICLE_SPAWN_WIDTH = -10;
	public static final int MAX_PARTICLE_SPAWN_WIDTH = 10;
	public static final int MIN_PARTICLE_SPAWN_HEIGHT = -10;
	public static final int MAX_PARTICLE_SPAWN_HEIGHT = 7;
	public static final int PARTICLE_SPAWN_ATTEMPTS = 5;
	private final MultifaceSpreader spreader = new MultifaceSpreader(new PollenSpreaderConfig());

	public PollenBlock(@NotNull Properties settings) {
		super(settings);
	}

	public static boolean canAttachToNoWater(@NotNull BlockGetter level, @NotNull Direction direction, @NotNull BlockPos pos, @NotNull BlockState state) {
		return Block.isFaceFull(state.getBlockSupportShape(level, pos), direction.getOpposite())
			|| Block.isFaceFull(state.getCollisionShape(level, pos), direction.getOpposite())
			&& !level.getBlockState(pos).is(Blocks.WATER);
	}

	@NotNull
	@Override
	protected MapCodec<? extends PollenBlock> codec() {
		return CODEC;
	}

	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
		boolean bl = false;
		if (level.getBlockState(pos).is(Blocks.WATER)) return false;
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		for (Direction direction : DIRECTIONS) {
			if (hasFace(state, direction)) {
				if (!canAttachToNoWater(level, direction, mutableBlockPos.setWithOffset(pos, direction), level.getBlockState(mutableBlockPos))) return false;
				bl = true;
			}
		}
		return bl;
	}

	@Override
	public boolean canBeReplaced(@NotNull BlockState state, @NotNull BlockPlaceContext context) {
		return !context.getItemInHand().is(state.getBlock().asItem()) || super.canBeReplaced(state, context);
	}

	@Override
	public boolean isValidStateForPlacement(BlockGetter view, @NotNull BlockState state, BlockPos pos, Direction dir) {
		if (!state.getFluidState().isEmpty()) return false;
		return super.isValidStateForPlacement(view, state, pos, dir);
	}

	@Override
	public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (WWBlockConfig.Client.POLLEN_ENABLED) {
			int i = pos.getX();
			int j = pos.getY();
			int k = pos.getZ();
			BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
			for (int l = 0; l < PARTICLE_SPAWN_ATTEMPTS; ++l) {
				mutable.set(
					i + Mth.nextInt(random, MIN_PARTICLE_SPAWN_WIDTH, MAX_PARTICLE_SPAWN_WIDTH),
					j + Mth.nextInt(random, MIN_PARTICLE_SPAWN_HEIGHT, MAX_PARTICLE_SPAWN_HEIGHT),
					k + Mth.nextInt(random, MIN_PARTICLE_SPAWN_WIDTH, MAX_PARTICLE_SPAWN_WIDTH)
				);
				BlockState blockState = level.getBlockState(mutable);
				if (!blockState.isCollisionShapeFullBlock(level, mutable) && !level.isRainingAt(mutable)) {
					level.addParticle(
						WWParticleTypes.POLLEN,
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
	}

	@Override
	@NotNull
	public MultifaceSpreader getSpreader() {
		return this.spreader;
	}

	public class PollenSpreaderConfig extends MultifaceSpreader.DefaultSpreaderConfig {
		public PollenSpreaderConfig() {
			super(PollenBlock.this);
		}

		@Override
		public boolean stateCanBeReplaced(BlockGetter view, BlockPos posA, BlockPos posB, Direction dir, @NotNull BlockState state) {
			return state.getFluidState().isEmpty() && super.stateCanBeReplaced(view, posA, posB, dir, state);
		}
	}
}
