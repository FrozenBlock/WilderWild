/*
 * Copyright 2022-2023 FrozenBlock
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

import java.util.HashMap;
import java.util.Map;
import net.frozenblock.lib.item.api.ItemBlockStateTagUtils;
import net.frozenblock.wilderwild.misc.mod_compat.FrozenLibIntegration;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;

public class ScorchedSandBlock extends Block {
	public static final Map<BlockState, BlockState> SCORCH_MAP = new HashMap<>();
	public static final Map<BlockState, BlockState> HYDRATE_MAP = new HashMap<>();
	public static final IntegerProperty CRACKEDNESS = RegisterProperties.CRACKEDNESS;
	private final int dustColor;

	public final BlockState wetState;

	public ScorchedSandBlock(Properties settings, BlockState wetState, int dustColor) {
		super(settings);
		this.registerDefaultState(this.stateDefinition.any().setValue(CRACKEDNESS, 0));
		this.wetState = wetState;
		this.fillScorchMap(wetState, this.defaultBlockState());
		this.dustColor = dustColor;
	}

	public void fillScorchMap(BlockState wetState, BlockState defaultState) {
		SCORCH_MAP.put(wetState, defaultState);
		SCORCH_MAP.put(defaultState, defaultState.setValue(RegisterProperties.CRACKEDNESS, 1));
		HYDRATE_MAP.put(defaultState, wetState);
		HYDRATE_MAP.put(defaultState.setValue(RegisterProperties.CRACKEDNESS, 1), defaultState);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		builder.add(CRACKEDNESS);
	}

	protected static boolean shouldHandlePrecipitation(Level level, Biome.Precipitation precipitation) {
		if (precipitation == Biome.Precipitation.RAIN) {
			return level.getRandom().nextFloat() < 0.75F;
		} else {
			return false;
		}
	}

	@Override
	public void handlePrecipitation(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Biome.@NotNull Precipitation precipitation) {
		if (shouldHandlePrecipitation(level, precipitation) && precipitation == Biome.Precipitation.RAIN) {
			hydrate(state, level, pos);
		}
	}

	public static boolean canScorch(@NotNull BlockState state) {
		return SCORCH_MAP.containsKey(state);
	}

	public static void scorch(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
		if (canScorch(state)) {
			level.setBlock(pos, SCORCH_MAP.get(state), 3);
			level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
		}
	}

	public static boolean canHydrate(@NotNull BlockState state) {
		return HYDRATE_MAP.containsKey(state);
	}

	public static void hydrate(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
		if (canHydrate(state)) {
			level.setBlockAndUpdate(pos, HYDRATE_MAP.get(state));
			level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
		}
	}

	public static boolean isFree(BlockState state) {
		Material material = state.getMaterial();
		return state.isAir() || state.is(BlockTags.FIRE) || material.isLiquid() || material.isReplaceable();
	}

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		FluidState fluidState = level.getFluidState(pos.above());
		if (fluidState.is(FluidTags.WATER)) {
			hydrate(state, level, pos);
		}
	}

	@Override
	public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		Fluid fluid = FrozenLibIntegration.getDripstoneFluid(level, pos);
		if (fluid == Fluids.LAVA) {
			if (random.nextBoolean()) {
				scorch(state, level, pos);
			}
		} else if (fluid == Fluids.WATER) {
			hydrate(state, level, pos);
		}
	}

	@Override
	public ItemStack getCloneItemStack(@NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull BlockState state) {
		ItemStack superStack = super.getCloneItemStack(level, pos, state);
		if (state.getValue(RegisterProperties.CRACKEDNESS) == 1) {
			ItemBlockStateTagUtils.setIntProperty(superStack, RegisterProperties.CRACKEDNESS, 1);
		}
		return superStack;
	}

	@Override
	public void fillItemCategory(@NotNull CreativeModeTab tab, @NotNull NonNullList<ItemStack> items) {
		super.fillItemCategory(tab, items);
		ItemStack secondStack = new ItemStack(this);
		ItemBlockStateTagUtils.setIntProperty(secondStack, RegisterProperties.CRACKEDNESS, 1);
		items.add(secondStack);
	}

	@Override
	public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, RandomSource random) {
		if (random.nextInt(16) == 0) {
			BlockPos blockPos = pos.below();
			if (isFree(level.getBlockState(blockPos))) {
				double d = (double)pos.getX() + random.nextDouble();
				double e = (double)pos.getY() - 0.05;
				double f = (double)pos.getZ() + random.nextDouble();
				level.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, state), d, e, f, 0.0, 0.0, 0.0);
			}
		}
	}
}

