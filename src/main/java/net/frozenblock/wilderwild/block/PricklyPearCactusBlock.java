/*
 * Copyright 2023 FrozenBlock
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
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
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

public class PricklyPearCactusBlock extends BushBlock implements BonemealableBlock {
	public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
	protected static final VoxelShape OUTLINE_SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 13.0D, 13.0D);

	public PricklyPearCactusBlock(@NotNull BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	protected MapCodec<? extends BushBlock> codec() {
		return null;
	}

	public static boolean isFullyGrown(@NotNull BlockState state) {
		return state.getValue(AGE) == 3;
	}

	@Override
	public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
		return !isFullyGrown(state);
	}

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (!isFullyGrown(state) && random.nextInt(0, 3) == 0) {
			level.setBlockAndUpdate(pos, state.setValue(AGE, state.getValue(AGE) + 1));
		}
	}

	@Override
	@NotNull
	public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return OUTLINE_SHAPE;
	}

	@Override
	public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
		entity.makeStuckInBlock(state, new Vec3(0.8, 0.75, 0.8));
		if (!(entity instanceof ItemEntity)) {
			entity.hurt(level.damageSources().cactus(), 0.5F);
		}
	}

	@Override
	public boolean isPathfindable(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull PathComputationType type) {
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
		level.setBlockAndUpdate(pos, state.setValue(AGE, Math.min(3, state.getValue(AGE) + random.nextIntBetweenInclusive(1, 2))));
	}

	@Override
	@NotNull
	public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
		ItemStack itemStack = player.getItemInHand(hand);
		if (isFullyGrown(state)) {
			level.setBlockAndUpdate(pos, state.setValue(AGE, 0));
			ItemStack stack = new ItemStack(RegisterItems.PRICKLY_PEAR);
			stack.setCount(level.random.nextIntBetweenInclusive(1, 2));
			popResource(level, pos, stack);
			if (!level.isClientSide) {
				if (itemStack.is(Items.SHEARS)) {
					level.playSound(null, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
					level.playSound(null, pos, RegisterSounds.BLOCK_PRICKLY_PEAR_PICK, SoundSource.BLOCKS, 1.0F, 0.95F + (level.random.nextFloat() * 0.1F));
					itemStack.hurtAndBreak(1, player, (playerx) -> playerx.broadcastBreakEvent(hand));
					level.gameEvent(player, GameEvent.SHEAR, pos);
				} else {
					level.playSound(null, pos, RegisterSounds.BLOCK_PRICKLY_PEAR_PICK, SoundSource.BLOCKS, 1.0F, 0.95F + (level.random.nextFloat() * 0.1F));
					level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
					player.hurt(level.damageSources().cactus(), 1F);
				}
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		} else {
			return super.use(state, level, pos, player, hand, hit);
		}
	}

	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}
}
