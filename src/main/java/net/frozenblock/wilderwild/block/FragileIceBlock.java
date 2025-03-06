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
import net.frozenblock.wilderwild.tag.WWEntityTags;
import net.frozenblock.wilderwild.worldgen.impl.util.IcicleUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.redstone.Orientation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FragileIceBlock extends HalfTransparentBlock {
	public static final MapCodec<FragileIceBlock> CODEC = simpleCodec(FragileIceBlock::new);
	public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
	public static final IntProvider SHEET_SHATTER_DELAY = UniformInt.of(1, 5);
	public static final float NEIGHBOR_CHANGE_CHANCE = 0.55F;
	public static final int DELAY_BETWEEN_CRACKS = 20;

	@Override
	public @NotNull MapCodec<FragileIceBlock> codec() {
		return CODEC;
	}

	public FragileIceBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
	}

	private void crackOrDestroy(@NotNull BlockState blockState, Level level, BlockPos blockPos) {
		int age = blockState.getValue(AGE);
		if (age < 2) {
			level.setBlock(blockPos, blockState.setValue(AGE, age + 1), UPDATE_CLIENTS);
			SoundType soundType = this.getSoundType(blockState);
			level.playSound(null, blockPos, soundType.getBreakSound(), SoundSource.BLOCKS, 0.1F, (soundType.getPitch() + 0.2F) + level.getRandom().nextFloat() * 0.2F);
			if (level instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(
					new BlockParticleOption(ParticleTypes.BLOCK, blockState),
					blockPos.getX() + 0.5D,
					blockPos.getY() + 0.5D,
					blockPos.getZ() + 0.5D,
					level.random.nextInt(20, 30),
					0.3F,
					0.3F,
					0.3F,
					0.05D
				);
			}
		} else {
			level.destroyBlock(blockPos, false);
		}
	}

	public void scheduleCrackIfNotScheduled(@NotNull Level level, BlockPos blockPos) {
		if (!level.getBlockTicks().hasScheduledTick(blockPos, this)) {
			level.scheduleTick(blockPos, this, DELAY_BETWEEN_CRACKS);
		}
	}

	@Override
	protected void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
		this.crackOrDestroy(blockState, serverLevel, blockPos);
	}

	// TODO: Fix this
	/*@Override
	protected void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, @Nullable Orientation orientation, boolean bl) {
		if (block.defaultBlockState().is(this) && blockState.isAir() && level.getRandom().nextFloat() <= NEIGHBOR_CHANGE_CHANCE) {
			this.scheduleShatter(level, blockPos, blockState, level.getRandom());
		}
		super.neighborChanged(blockState, level, blockPos, block, orientation, bl);
	}

	@Override
	protected void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, @NotNull Block block, BlockPos blockPos2, boolean bl) {
		if (block.defaultBlockState().is(this) && level.getBlockState(blockPos2).isAir() && level.getRandom().nextFloat() <= NEIGHBOR_CHANGE_CHANCE) {
			this.scheduleShatter(level, blockPos, blockState, level.getRandom());
		}

		super.neighborChanged(blockState, level, blockPos, block, blockPos2, bl);
	}*/

	@Override
	protected void randomTick(@NotNull BlockState blockState, @NotNull ServerLevel serverLevel, BlockPos blockPos, @NotNull RandomSource randomSource) {
		if (randomSource.nextFloat() <= 0.075F) {
			IcicleUtils.growIcicleOnRandomTick(serverLevel, blockPos);
		} else {
			this.heal(blockState, serverLevel, blockPos);
		}
	}

	@Override
	public void stepOn(Level level, BlockPos blockPos, BlockState blockState, @NotNull Entity entity) {
		if (entity.getType().is(WWEntityTags.FRAGILE_ICE_UNWALKABLE_MOBS)) {
			this.scheduleCrackIfNotScheduled(level, blockPos);
		}
	}

	@Override
	public void fallOn(Level level, BlockState blockState, BlockPos blockPos, Entity entity, float fallDistance) {
		super.fallOn(level, blockState, blockPos, entity, fallDistance);
		if (!entity.getType().is(WWEntityTags.FRAGILE_ICE_DOESNT_CRACK_ON_FALL)) {
			if (fallDistance >= 4F) level.destroyBlock(blockPos, false);
		}
	}

	public void scheduleShatter(@NotNull Level level, BlockPos blockPos, @NotNull BlockState blockState, RandomSource randomSource) {
		level.setBlock(blockPos, blockState.setValue(AGE, 2), UPDATE_CLIENTS);
		level.scheduleTick(blockPos, this, SHEET_SHATTER_DELAY.sample(randomSource));
	}

	private void heal(@NotNull BlockState blockState, Level level, BlockPos blockPos) {
		if (blockState.getValue(AGE) > 0) {
			level.setBlock(blockPos, blockState.setValue(AGE,  0), UPDATE_CLIENTS);
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}
}
