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

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import net.frozenblock.lib.block.api.dripstone.DripstoneUtils;
import net.frozenblock.lib.item.api.ItemBlockStateTagUtils;
import net.frozenblock.wilderwild.block.entity.ScorchedBlockEntity;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ScorchedBlock extends BaseEntityBlock {
	public static final int TICK_DELAY = 2;
	public static final float RAIN_HYDRATION_CHANCE = 0.75F;
	public static final Map<BlockState, BlockState> SCORCH_MAP = new Object2ObjectOpenHashMap<>();
	public static final Map<BlockState, BlockState> HYDRATE_MAP = new Object2ObjectOpenHashMap<>();
	private static final BooleanProperty CRACKEDNESS = RegisterProperties.CRACKED;
	private static final IntegerProperty DUSTED = BlockStateProperties.DUSTED;
	public final boolean canBrush;
	public final BlockState wetState;
	public final SoundEvent brushSound;
	public final SoundEvent brushCompletedSound;

	public ScorchedBlock(@NotNull BlockState wetState, boolean canBrush, @NotNull SoundEvent brushSound, @NotNull SoundEvent brushCompletedSound, @NotNull Properties settings) {
		super(settings);
		this.registerDefaultState(this.stateDefinition.any().setValue(CRACKEDNESS, false));
		this.canBrush = canBrush;
		this.brushSound = brushSound;
		this.brushCompletedSound = brushCompletedSound;
		this.wetState = wetState;
		this.fillScorchMap(this.wetState, this.defaultBlockState(), this.defaultBlockState().setValue(CRACKEDNESS, true));
	}

	public static boolean canScorch(@NotNull BlockState state) {
		return SCORCH_MAP.containsKey(stateWithoutDusting(state));
	}

	public static void scorch(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
		state = stateWithoutDusting(state);
		if (canScorch(state)) {
			level.setBlock(pos, SCORCH_MAP.get(state), UPDATE_ALL);
			level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
		}
	}

	public static boolean canHydrate(@NotNull BlockState state) {
		return HYDRATE_MAP.containsKey(stateWithoutDusting(state));
	}

	public static void hydrate(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
		state = stateWithoutDusting(state);
		if (canHydrate(state)) {
			level.setBlockAndUpdate(pos, HYDRATE_MAP.get(state));
			level.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
		}
	}

	@NotNull
	private static BlockState stateWithoutDusting(@NotNull BlockState state) {
		return state.hasProperty(DUSTED) ? state.setValue(DUSTED, 0) : state;
	}

	public void fillScorchMap(BlockState wetState, BlockState defaultState, BlockState defaultStateCracked) {
		SCORCH_MAP.put(wetState, defaultState);
		SCORCH_MAP.put(defaultState, defaultStateCracked);
		HYDRATE_MAP.put(defaultState, wetState);
		HYDRATE_MAP.put(defaultStateCracked, defaultState);
	}

	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(CRACKEDNESS, DUSTED);
	}

	@Override
	public void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean movedByPiston) {
		level.scheduleTick(pos, this, TICK_DELAY);
	}

	@Override
	public BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos) {
		level.scheduleTick(currentPos, this, TICK_DELAY);
		return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
	}

	@Override
	public void handlePrecipitation(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Biome.@NotNull Precipitation precipitation) {
		if (precipitation == Biome.Precipitation.RAIN && level.getRandom().nextFloat() < RAIN_HYDRATION_CHANCE) {
			hydrate(state, level, pos);
		}
	}

	@Override
	@Nullable
	public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
		return new ScorchedBlockEntity(blockPos, blockState);
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
		Fluid fluid = DripstoneUtils.getDripstoneFluid(level, pos);
		if (fluid == Fluids.LAVA) {
			if (random.nextBoolean()) {
				scorch(state, level, pos);
			}
		} else if (fluid == Fluids.WATER) {
			hydrate(state, level, pos);
		}
		BlockEntity blockEntity = level.getBlockEntity(pos);
		if (blockEntity instanceof ScorchedBlockEntity scorchedBlock) {
			scorchedBlock.checkReset();
		}
	}

	@Override
	@NotNull
	public ItemStack getCloneItemStack(@NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull BlockState state) {
		ItemStack superStack = super.getCloneItemStack(level, pos, state);
		if (state.getValue(RegisterProperties.CRACKED)) {
			ItemBlockStateTagUtils.setProperty(superStack, RegisterProperties.CRACKED, true);
		}
		return superStack;
	}

	@Override
	@NotNull
	public RenderShape getRenderShape(@NotNull BlockState blockState) {
		return RenderShape.MODEL;
	}
}

