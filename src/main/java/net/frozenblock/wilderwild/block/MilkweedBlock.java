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

import net.frozenblock.wilderwild.particle.options.SeedParticleOptions;
import net.frozenblock.wilderwild.registry.WWLootTables;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
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
	public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
	private static final int MAX_AGE = 3;

	public MilkweedBlock(@NotNull Properties properties) {
		super(properties);
	}

	public static boolean isFullyGrown(@NotNull BlockState state) {
		return state.getValue(AGE) == MAX_AGE;
	}

	public static boolean isLower(@NotNull BlockState state) {
		return state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER;
	}

	public static void onShear(@NotNull Level level, BlockPos pos, @NotNull BlockState state, ItemStack stack, @Nullable Entity entity) {
		level.playSound(null, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1F, 1F);
		if (level instanceof ServerLevel serverLevel) dropMilkweed(serverLevel, stack, state, null, entity, pos);
		level.gameEvent(entity, GameEvent.SHEAR, pos);
		setAgeOnBothHalves(state.getBlock(), state, level, pos, 0, false);
	}

	public static void dropMilkweed(ServerLevel level, ItemStack stack, BlockState state, @Nullable BlockEntity blockEntity, @Nullable Entity entity, BlockPos pos) {
		dropFromBlockInteractLootTable(
			level,
			WWLootTables.SHEAR_MILKWEED,
			state,
			blockEntity,
			stack,
			entity,
			(serverLevelx, itemStackx) -> popResource(serverLevelx, pos, itemStackx)
		);
	}

	public static void setAgeOnBothHalves(Block block, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, int age, boolean spawnParticles) {
		if (age > MAX_AGE) return;

		level.setBlockAndUpdate(pos, state.setValue(AGE, age));
		final boolean isUpper = state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER;
		boolean hasSecondState = false;
		final BlockPos movedPos = isUpper ? pos.below() : pos.above();
		final BlockState movedState = level.getBlockState(movedPos);
		if (movedState.is(block)) {
			level.setBlockAndUpdate(movedPos, movedState.setValue(AGE, age));
			hasSecondState = true;
		}

		if (!spawnParticles || !(level instanceof ServerLevel serverLevel)) return;
		final int particles = (int) (serverLevel.getRandom().nextIntBetweenInclusive(MIN_SEEDS_ON_RUSTLE, MAX_SEEDS_ON_RUSTLE) * 0.5D);
		final double firstYHeight = isUpper ? 0.3D : 0.5D;
		final double firstYOffset = isUpper ? 0.3D : 0.5D;
		Vec3 offset = state.getOffset(pos);
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

		if (!hasSecondState) return;
		final double secondYHeight = isUpper ? -0.5D : 1.3D;
		final double secondYOffset = isUpper ? 0.3D : 0.5D;
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

	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(AGE);
	}

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (random.nextInt(GROWTH_CHANCE) != 0 || !isLower(state) || isFullyGrown(state)) return;
		setAgeOnBothHalves(this, state, level, pos, state.getValue(AGE) + 1, false);
	}

	@Override
	public InteractionResult useItemOn(
		@NotNull ItemStack stack,
		@NotNull BlockState state,
		@NotNull Level level,
		@NotNull BlockPos pos,
		@NotNull Player player,
		@NotNull InteractionHand hand,
		@NotNull BlockHitResult hitResult
	) {
		if (stack.is(Items.BONE_MEAL)) return InteractionResult.TRY_WITH_EMPTY_HAND;
		if (!isFullyGrown(state)) return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
		if (!(level instanceof ServerLevel)) return InteractionResult.SUCCESS;
		if (stack.is(Items.SHEARS)) {
			stack.hurtAndBreak(1, player, hand);
			player.awardStat(Stats.ITEM_USED.get(Items.SHEARS));
			onShear(level, pos, state, stack, player);
		} else {
			this.pickAndSpawnSeeds(level, state, pos);
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	@NotNull
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player entity, BlockHitResult hitResult) {
		if (!isFullyGrown(state)) return super.useWithoutItem(state, level, pos, entity, hitResult);
		if (level instanceof ServerLevel) this.pickAndSpawnSeeds(level, state, pos);
		return InteractionResult.SUCCESS;
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
