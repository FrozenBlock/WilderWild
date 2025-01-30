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
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TumbleweedPlantBlock extends VegetationBlock implements BonemealableBlock {
	public static final MapCodec<TumbleweedPlantBlock> CODEC = simpleCodec(TumbleweedPlantBlock::new);
	public static final int MAX_AGE = 3;
	public static final int AGE_FOR_SOLID_COLLISION = 2;
	public static final int RANDOM_TICK_CHANCE = 2;
	public static final int SNAP_CHANCE = 3;
	public static final int REPRODUCTION_CHANCE_PEACEFUL = 20;
	public static final int REPRODUCTION_CHANCE_DIVIDER_BY_DIFFICULTY = 15;
	public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
	private static final VoxelShape[] SHAPES = new VoxelShape[]{
		Block.box(3D, 0D, 3D, 12D, 9D, 12D),
		Block.box(2D, 0D, 2D, 14D, 12D, 14D),
		Block.box(1D, 0D, 1D, 15D, 14D, 15D),
		Block.box(1D, 0D, 1D, 15D, 14D, 15D)
	};

	public TumbleweedPlantBlock(@NotNull BlockBehaviour.Properties properties) {
		super(properties);
	}

	public static boolean isFullyGrown(@NotNull BlockState state) {
		return state.getValue(AGE) == MAX_AGE;
	}

	@NotNull
	@Override
	protected MapCodec<? extends TumbleweedPlantBlock> codec() {
		return CODEC;
	}

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (random.nextInt(RANDOM_TICK_CHANCE) == 0) {
			if (isFullyGrown(state)) {
				if (random.nextInt(SNAP_CHANCE) == 0) {
					level.setBlock(pos, state.cycle(AGE), UPDATE_CLIENTS);
					Tumbleweed weed = new Tumbleweed(WWEntityTypes.TUMBLEWEED, level);
					level.addFreshEntity(weed);
					weed.setPos(Vec3.atBottomCenterOf(pos));
					int diff = level.getDifficulty().getId();
					if (level.getRandom().nextInt(diff == 0 ? REPRODUCTION_CHANCE_PEACEFUL : (REPRODUCTION_CHANCE_DIVIDER_BY_DIFFICULTY / diff)) == 0) {
						weed.setItem(new ItemStack(WWBlocks.TUMBLEWEED_PLANT), true);
					}
					level.playSound(null, pos, WWSounds.ENTITY_TUMBLEWEED_DAMAGE, SoundSource.BLOCKS, 1F, 1F);
					level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(state));
					level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
				}
			} else {
				level.setBlock(pos, state.cycle(AGE), UPDATE_CLIENTS);
			}
		}
	}

	@NotNull
	@Override
	public VoxelShape getCollisionShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext) {
		return blockState.getValue(AGE) < AGE_FOR_SOLID_COLLISION ? Shapes.empty() : super.getCollisionShape(blockState, blockGetter, blockPos, collisionContext);
	}

	@NotNull
	@Override
	public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return SHAPES[state.getValue(AGE)];
	}

	@Override
	protected boolean mayPlaceOn(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
		return state.is(BlockTags.DEAD_BUSH_MAY_PLACE_ON) || state.is(WWBlockTags.SHRUB_MAY_PLACE_ON) || super.mayPlaceOn(state, level, pos);
	}

	@Override
	public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
		return !isFullyGrown(state);
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
	public InteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
		if (stack.is(Items.SHEARS) && onShear(level, pos, state, player)) {
			stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
			return InteractionResult.SUCCESS;
		} else {
			return super.useItemOn(stack, state, level, pos, player, hand, hit);
		}
	}

	public static boolean onShear(Level level, BlockPos pos, @NotNull BlockState state, @Nullable Entity entity) {
		if (isFullyGrown(state)) {
			if (!level.isClientSide) {
				Tumbleweed.spawnFromShears(level, pos);
				level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(state));
				level.setBlockAndUpdate(pos, state.setValue(AGE, 0));
				level.gameEvent(entity, GameEvent.SHEAR, pos);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(AGE);
	}
}
