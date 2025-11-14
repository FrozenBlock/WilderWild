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
import net.frozenblock.wilderwild.registry.WWLootTables;
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
import net.minecraft.world.level.block.entity.BlockEntity;
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
import org.jetbrains.annotations.Nullable;

public class PricklyPearCactusBlock extends VegetationBlock implements BonemealableBlock {
	public static final MapCodec<PricklyPearCactusBlock> CODEC = simpleCodec(PricklyPearCactusBlock::new);
	public static final int GROWTH_CHANCE = 16;
	public static final Vec3 ENTITY_SLOWDOWN_VEC3 = new Vec3(0.8D, 0.75D, 0.8D);
	public static final float DAMAGE = 0.5F;
	public static final float USE_ON_DAMAGE = 1F;
	public static final int MAX_AGE = 3;
	public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
	protected static final VoxelShape OUTLINE_SHAPE = Block.box(3D, 0D, 3D, 13D, 13D, 13D);

	public PricklyPearCactusBlock(Properties properties) {
		super(properties);
	}

	public static boolean isFullyGrown(BlockState state) {
		return state.getValue(AGE) == MAX_AGE;
	}

	@Override
	protected MapCodec<? extends PricklyPearCactusBlock> codec() {
		return CODEC;
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
		return !isFullyGrown(state);
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (level.getRawBrightness(pos, 0) < 9 || isFullyGrown(state) || random.nextInt(GROWTH_CHANCE) != 0) return;
		level.setBlock(pos, state.cycle(AGE), UPDATE_CLIENTS);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return OUTLINE_SHAPE;
	}

	@Override
	public void entityInside(
		BlockState state,
		Level level,
		BlockPos pos,
		Entity entity,
		InsideBlockEffectApplier insideBlockEffectApplier,
		boolean bl
	) {
		entity.makeStuckInBlock(state, ENTITY_SLOWDOWN_VEC3);
		if (!(entity instanceof ItemEntity)) entity.hurt(level.damageSources().cactus(), DAMAGE);
	}

	@Override
	public boolean isPathfindable(BlockState state, PathComputationType type) {
		return false;
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
		return state.is(BlockTags.SAND) || state.is(BlockTags.DIRT);
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
		if (!isFullyGrown(state)) return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
		onPlayerPick(level, pos, state, player, hand, stack);
		return InteractionResult.SUCCESS;
	}

	private static void basePick(Level level, BlockPos pos, BlockState state, ItemStack stack, @Nullable Entity entity) {
		level.setBlockAndUpdate(pos, state.setValue(AGE, 0));
		if (level instanceof ServerLevel serverLevel) dropPricklyPear(serverLevel, stack, state, null, entity, pos);
	}

	public static void onPlayerPick(Level level, BlockPos pos, BlockState state, Player player, InteractionHand hand, ItemStack stack) {
		if (level.isClientSide()) return;

		final boolean shears = stack.is(Items.SHEARS);
		onPricklyPearPick(level, pos, state, shears, stack, player);
		if (shears) {
			stack.hurtAndBreak(1, player, hand);
		} else {
			player.hurt(level.damageSources().cactus(), USE_ON_DAMAGE);
		}
	}

	public static void onPricklyPearPick(Level level, BlockPos pos, BlockState state, boolean shears, ItemStack stack, @Nullable Entity entity) {
		basePick(level, pos, state, stack, entity);
		if (level.isClientSide()) return;
		if (shears) {
			level.playSound(null, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1F, 1F);
			level.playSound(null, pos, WWSounds.BLOCK_PRICKLY_PEAR_PICK, SoundSource.BLOCKS, 1F, 0.95F + (level.random.nextFloat() * 0.1F));
			level.gameEvent(entity, GameEvent.SHEAR, pos);
		} else {
			level.playSound(null, pos, WWSounds.BLOCK_PRICKLY_PEAR_PICK, SoundSource.BLOCKS, 1F, 0.95F + (level.random.nextFloat() * 0.1F));
		}
	}

	public static void dropPricklyPear(ServerLevel level, ItemStack stack, BlockState state, @Nullable BlockEntity blockEntity, @Nullable Entity entity, BlockPos pos) {
		dropFromBlockInteractLootTable(
			level,
			WWLootTables.SHEAR_PRICKLY_PEAR,
			state,
			blockEntity,
			stack,
			entity,
			(serverLevelx, itemStackx) -> popResource(serverLevelx, pos, itemStackx)
		);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(AGE);
	}
}
