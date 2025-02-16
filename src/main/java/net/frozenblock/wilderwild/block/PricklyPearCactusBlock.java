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
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PricklyPearCactusBlock extends VegetationBlock implements BonemealableBlock {
	public static final MapCodec<PricklyPearCactusBlock> CODEC = simpleCodec(PricklyPearCactusBlock::new);
	public static final int GROWTH_CHANCE = 16;
	public static final Vec3 ENTITY_SLOWDOWN_VEC3 = new Vec3(0.8D, 0.75D, 0.8D);
	public static final float DAMAGE = 0.5F;
	public static final float USE_ON_DAMAGE = 1F;
	public static final int MIN_PEARS_FROM_HARVEST = 1;
	public static final int MAX_PEARS_FROM_HARVEST = 2;
	public static final int MAX_AGE = 3;
	public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
	protected static final VoxelShape OUTLINE_SHAPE = Block.box(3D, 0D, 3D, 13D, 13D, 13D);

	public PricklyPearCactusBlock(@NotNull BlockBehaviour.Properties properties) {
		super(properties);
	}

	public static boolean isFullyGrown(@NotNull BlockState state) {
		return state.getValue(AGE) == MAX_AGE;
	}

	@NotNull
	@Override
	protected MapCodec<? extends PricklyPearCactusBlock> codec() {
		return CODEC;
	}

	@Override
	public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
		return !isFullyGrown(state);
	}

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (level.getRawBrightness(pos, 0) >= 9 && !isFullyGrown(state) && random.nextInt(GROWTH_CHANCE) == 0) {
			level.setBlock(pos, state.cycle(AGE), UPDATE_CLIENTS);
		}
	}

	@Override
	@NotNull
	public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return OUTLINE_SHAPE;
	}

	@Override
	public void entityInside(
		@NotNull BlockState state,
		@NotNull Level level,
		@NotNull BlockPos pos,
		@NotNull Entity entity,
		InsideBlockEffectApplier insideBlockEffectApplier
	) {
		entity.makeStuckInBlock(state, ENTITY_SLOWDOWN_VEC3);
		if (!(entity instanceof ItemEntity)) {
			entity.hurt(level.damageSources().cactus(), DAMAGE);
		}
	}

	@Override
	public boolean isPathfindable(@NotNull BlockState state, @NotNull PathComputationType type) {
		return false;
	}

	@Override
	protected boolean mayPlaceOn(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
		return state.is(BlockTags.SAND) || state.is(BlockTags.DIRT);
	}

	@Override
	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		level.setBlockAndUpdate(pos, state.setValue(AGE, Math.min(MAX_AGE, state.getValue(AGE) + random.nextIntBetweenInclusive(1, 2))));
	}


	@Override
	@NotNull
	public InteractionResult useItemOn(
		@NotNull ItemStack stack,
		@NotNull BlockState state,
		@NotNull Level level,
		@NotNull BlockPos pos,
		@NotNull Player player,
		@NotNull InteractionHand hand,
		@NotNull BlockHitResult hit
	) {
		if (isFullyGrown(state)) {
			onPlayerPick(level, pos, state, player, hand, stack);
			return InteractionResult.SUCCESS;
		} else {
			return super.useItemOn(stack, state, level, pos, player, hand, hit);
		}
	}

	private static void basePick(@NotNull Level level, BlockPos pos, @NotNull BlockState state) {
		level.setBlockAndUpdate(pos, state.setValue(AGE, 0));
		ItemStack pear = new ItemStack(WWItems.PRICKLY_PEAR);
		pear.setCount(level.getRandom().nextIntBetweenInclusive(MIN_PEARS_FROM_HARVEST, MAX_PEARS_FROM_HARVEST));
		popResource(level, pos, pear);
	}

	public static void onPlayerPick(@NotNull Level level, BlockPos pos, @NotNull BlockState state, @NotNull Player player, @NotNull InteractionHand hand, @NotNull ItemStack stack) {
		if (!level.isClientSide) {
			boolean shears = stack.is(Items.SHEARS);
			onPricklyPearPick(level, pos, state, shears, player);
			if (shears) {
				stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
			} else {
				player.hurt(level.damageSources().cactus(), USE_ON_DAMAGE);
			}
		}
	}

	public static void onPricklyPearPick(@NotNull Level level, BlockPos pos, BlockState state, boolean shears, @Nullable Entity entity) {
		basePick(level, pos, state);
		if (!level.isClientSide) {
			if (shears) {
				level.playSound(null, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1F, 1F);
				level.playSound(null, pos, WWSounds.BLOCK_PRICKLY_PEAR_PICK, SoundSource.BLOCKS, 1F, 0.95F + (level.random.nextFloat() * 0.1F));
				level.gameEvent(entity, GameEvent.SHEAR, pos);
			} else {
				level.playSound(null, pos, WWSounds.BLOCK_PRICKLY_PEAR_PICK, SoundSource.BLOCKS, 1F, 0.95F + (level.random.nextFloat() * 0.1F));
			}
		}
	}

	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(AGE);
	}
}
