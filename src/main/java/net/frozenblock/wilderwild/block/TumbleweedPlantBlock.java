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
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DryVegetationBlock;
import net.minecraft.world.level.block.LevelEvent;
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
import org.jetbrains.annotations.Nullable;

public class TumbleweedPlantBlock extends DryVegetationBlock implements BonemealableBlock {
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

	public TumbleweedPlantBlock(Properties properties) {
		super(properties);
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public static boolean isFullyGrown(BlockState state) {
		return state.getValue(AGE) == MAX_AGE;
	}

	@Override
	public MapCodec<? extends TumbleweedPlantBlock> codec() {
		return CODEC;
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (random.nextInt(RANDOM_TICK_CHANCE) != 0) return;

		if (!isFullyGrown(state)) {
			level.setBlock(pos, state.cycle(AGE), UPDATE_CLIENTS);
			return;
		}

		if (random.nextInt(SNAP_CHANCE) != 0) return;
		level.setBlock(pos, state.cycle(AGE), UPDATE_CLIENTS);

		final Tumbleweed tumbleweed = new Tumbleweed(WWEntityTypes.TUMBLEWEED, level);
		level.addFreshEntity(tumbleweed);
		tumbleweed.setPos(Vec3.atBottomCenterOf(pos));

		final int difficulty = level.getDifficulty().getId();
		if (level.getRandom().nextInt(difficulty == 0 ? REPRODUCTION_CHANCE_PEACEFUL : (REPRODUCTION_CHANCE_DIVIDER_BY_DIFFICULTY / difficulty)) == 0) {
			tumbleweed.setItem(new ItemStack(WWBlocks.TUMBLEWEED_PLANT), true);
		}
		level.playSound(null, pos, WWSounds.ENTITY_TUMBLEWEED_DAMAGE, SoundSource.BLOCKS, 1F, 1F);
		level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, getId(state));
		level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return state.getValue(AGE) < AGE_FOR_SOLID_COLLISION ? Shapes.empty() : super.getCollisionShape(state, level, pos, context);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPES[state.getValue(AGE)];
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
		return state.is(WWBlockTags.SHRUB_MAY_PLACE_ON) || super.mayPlaceOn(state, level, pos);
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
		return !isFullyGrown(state);
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		level.setBlockAndUpdate(pos, state.setValue(AGE, Math.min(MAX_AGE, state.getValue(AGE) + random.nextIntBetweenInclusive(1, 2))));
	}

	@Override
	public InteractionResult useItemOn(
		ItemStack stack,
		BlockState state,
		Level level,
		BlockPos pos,
		Player player,
		InteractionHand hand,
		BlockHitResult hitResult
	) {
		if (stack.is(Items.SHEARS) && onShear(level, pos, state, player)) {
			stack.hurtAndBreak(1, player, hand);
			return InteractionResult.SUCCESS;
		}
		return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}

	public static boolean onShear(Level level, BlockPos pos, BlockState state, @Nullable Entity entity) {
		if (!isFullyGrown(state)) return false;
		if (level.isClientSide()) return true;
		Tumbleweed.spawnFromShears(level, pos);
		level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(state));
		level.setBlockAndUpdate(pos, state.setValue(AGE, 0));
		level.gameEvent(entity, GameEvent.SHEAR, pos);
		return true;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(AGE);
	}
}
