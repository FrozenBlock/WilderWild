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
import net.frozenblock.wilderwild.entity.Penguin;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PenguinEggBlock extends Block {
	public static final int MAX_HATCH_LEVEL = 2;
	public static final IntegerProperty HATCH = BlockStateProperties.HATCH;
	public static final MapCodec<PenguinEggBlock> CODEC = simpleCodec(PenguinEggBlock::new);
	private static final VoxelShape SHAPE = Block.box(6D, 0D, 6D, 10D, 6D, 10D);

	public PenguinEggBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(HATCH, 0));
	}

	public static boolean isSafeToHatch(Level level, BlockPos belowPos) {
		return level.getBlockState(belowPos).isFaceSturdy(level, belowPos, Direction.UP);
	}

	@Override
	protected MapCodec<? extends PenguinEggBlock> codec() {
		return CODEC;
	}

	@Override
	public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(HATCH);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	public int getHatchLevel(BlockState state) {
		return state.getValue(HATCH);
	}

	private boolean isReadyToHatch(BlockState state) {
		return this.getHatchLevel(state) == MAX_HATCH_LEVEL;
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!shouldUpdateHatchLevel(level, pos)) return;
		if (this.isReadyToHatch(state)) {
			this.hatchPenguinEgg(level, pos, random);
			return;
		}
		level.playSound(null, pos, WWSounds.BLOCK_PENGUIN_EGG_CRACK, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
		level.setBlock(pos, state.cycle(HATCH), UPDATE_CLIENTS);
	}

	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
		super.onPlace(state, level, pos, oldState, movedByPiston);
		level.levelEvent(LevelEvent.PARTICLES_EGG_CRACK, pos, 0);
	}

	private boolean shouldUpdateHatchLevel(Level level, BlockPos blockPos) {
		if (!isSafeToHatch(level, blockPos.below())) return false;
		return level.getRandom().nextInt(30) == 0;
	}

	private void hatchPenguinEgg(ServerLevel level, BlockPos pos, RandomSource random) {
		level.playSound(null, pos, WWSounds.BLOCK_PENGUIN_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
		this.destroyBlock(level, pos);
		this.spawnPenguin(level, pos, random);
	}

	private void destroyBlock(Level level, BlockPos pos) {
		level.destroyBlock(pos, false);
	}

	private void spawnPenguin(ServerLevel level, BlockPos pos, RandomSource random) {
		final Penguin penguin = WWEntityTypes.PENGUIN.create(level, EntitySpawnReason.BREEDING);
		if (penguin == null) return;
		penguin.setBaby(true);
		penguin.snapTo(
			pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D,
			random.nextFloat() * 360F, 0F
		);
		level.addFreshEntity(penguin);
	}

	@Override
	public boolean isPathfindable(BlockState state, PathComputationType type) {
		return false;
	}
}
