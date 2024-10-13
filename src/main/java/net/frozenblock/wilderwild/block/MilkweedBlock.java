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

import net.frozenblock.wilderwild.particle.options.SeedParticleOptions;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MilkweedBlock extends TallFlowerBlock {
	public static final int GROWTH_CHANCE = 5;
	public static final int MIN_SEEDS_ON_RUSTLE = 14;
	public static final int MAX_SEEDS_ON_RUSTLE = 30;
	public static final int MIN_PODS_FROM_HARVEST = 1;
	public static final int MAX_PODS_FROM_HARVEST = 3;
	public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
	private static final int MAX_AGE = 3;

	public MilkweedBlock(@NotNull Properties settings) {
		super(settings);
	}

	public static boolean isFullyGrown(@NotNull BlockState state) {
		return state.getValue(AGE) == MAX_AGE;
	}

	public static boolean isLower(@NotNull BlockState state) {
		return state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER;
	}

	public static void onShear(@NotNull Level level, BlockPos pos, @NotNull BlockState state, @Nullable Player player) {
		ItemStack stack = new ItemStack(WWItems.MILKWEED_POD);
		stack.setCount(level.getRandom().nextIntBetweenInclusive(MIN_PODS_FROM_HARVEST, MAX_PODS_FROM_HARVEST));
		popResource(level, pos, stack);
		level.playSound(null, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1F, 1F);
		level.gameEvent(player, GameEvent.SHEAR, pos);
		setAgeOnBothHalves(state.getBlock(), state, level, pos, 0, false);
	}

	public static void setAgeOnBothHalves(Block block, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, int age, boolean spawnParticles) {
		if (age > MAX_AGE) {
			return;
		}
		level.setBlockAndUpdate(pos, state.setValue(AGE, age));
		boolean isUpper = state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER;
		boolean hasSecondState = false;
		BlockPos movedPos = isUpper ? pos.below() : pos.above();
		BlockState secondState = level.getBlockState(movedPos);
		if (secondState.is(block)) {
			level.setBlockAndUpdate(movedPos, secondState.setValue(AGE, age));
			hasSecondState = true;
		}

		if (spawnParticles && level instanceof ServerLevel serverLevel) {
			int particles = (int) (serverLevel.getRandom().nextIntBetweenInclusive(MIN_SEEDS_ON_RUSTLE, MAX_SEEDS_ON_RUSTLE) * 0.5D);
			double firstYHeight = isUpper ? 0.3D : 0.5D;
			double firstYOffset = isUpper ? 0.3D : 0.5D;
			Vec3 offset = state.getOffset(level, pos);
			serverLevel.sendParticles(
				SeedParticleOptions.unControlled(true),
				pos.getX() + 0.5D + offset.x,
				pos.getY() + firstYHeight,
				pos.getZ() + 0.5D + offset.z,
				particles,
				0.1D,
				firstYOffset,
				0.1D,
				0D
			);

			if (hasSecondState) {
				double secondYHeight = isUpper ? -0.5D : 1.3D;
				double secondYOffset = isUpper ? 0.3D : 0.5D;

				serverLevel.sendParticles(
					SeedParticleOptions.unControlled(true),
					pos.getX() + 0.5D,
					pos.getY() + secondYHeight,
					pos.getZ() + 0.5D,
					particles,
					0.2D,
					secondYOffset,
					0.2D,
					0D
				);
			}
		}
	}

	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(AGE);
	}

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (random.nextInt(GROWTH_CHANCE) == 0 && isLower(state) && !isFullyGrown(state)) {
			setAgeOnBothHalves(this, state, level, pos, state.getValue(AGE) + 1, false);
		}
	}

	@Override
	@NotNull
	public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack.is(Items.BONE_MEAL)) {
			return InteractionResult.PASS;
		}
		if (isFullyGrown(state)) {
			if (level instanceof ServerLevel serverLevel && !serverLevel.isClientSide) {
				if (stack.is(Items.SHEARS)) {
					stack.hurtAndBreak(1, player, (playerx) -> playerx.broadcastBreakEvent(hand));
					player.awardStat(Stats.ITEM_USED.get(Items.SHEARS));
					onShear(level, pos, state, player);
				} else {
					this.pickAndSpawnSeeds(level, state, pos);
				}
			}
			return InteractionResult.SUCCESS;
		}
		return super.use(state, level, pos, player, hand, hit);
	}

	public void pickAndSpawnSeeds(@NotNull Level level, BlockState state, BlockPos pos) {
		level.playSound(null, pos, WWSounds.BLOCK_MILKWEED_RUSTLE, SoundSource.BLOCKS, 0.8F, 0.9F + (level.getRandom().nextFloat() * 0.15F));
		setAgeOnBothHalves(this, state, level, pos, 0, true);
	}

	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		if (!isFullyGrown(state)) {
			setAgeOnBothHalves(this, state, level, pos, state.getValue(AGE) + 1, false);
			return;
		}
		super.performBonemeal(level, random, pos, state);
	}
}
