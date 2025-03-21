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
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import org.jetbrains.annotations.NotNull;
import java.util.function.Predicate;

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
		if (level.getBlockState(pos).is(Blocks.WATER)) {
			return false;
		}
		for (Direction direction : DIRECTIONS) {
			if (hasFace(state, direction)) {
				BlockPos blockPos = pos.relative(direction);
				if (!canAttachToNoWater(level, direction, blockPos, level.getBlockState(blockPos))) {
					return false;
				}
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
		if (random.nextFloat() <= 0.01F && isAttachedTo(level, pos, state, blockState -> blockState.is(BlockTags.LEAVES))) {
			level.playLocalSound(
				pos.getX() + 0.5D,
				pos.getY() + 0.5D,
				pos.getZ() + 0.5D,
				WWSounds.AMBIENT_OVERWORLD_WIND_LEAVES,
				SoundSource.AMBIENT,
				0.1F + (random.nextFloat() * 0.15F),
				0.85F + (random.nextFloat() * 0.25F),
				false
			);
		}

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

	private static boolean isAttachedTo(LevelReader level, BlockPos pos, BlockState state, Predicate<BlockState> predicate) {
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

		for (Direction direction : DIRECTIONS) {
			if (hasFace(state, direction)) {
				mutable.setWithOffset(pos, direction);
				if (state.getValue(getFaceProperty(direction))) {
					if (predicate.test(level.getBlockState(mutable))) return true;
				}
			}
		}

		return false;
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
