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

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import net.frozenblock.lib.block.api.dripstone.DripstoneDripApi;
import net.frozenblock.lib.item.api.ItemBlockStateTagUtils;
import net.frozenblock.wilderwild.block.entity.ScorchedBlockEntity;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
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
	public static final float RAIN_HYDRATION_CHANCE = 0.75F;
	public static final Map<BlockState, BlockState> SCORCH_MAP = new Object2ObjectOpenHashMap<>();
	public static final Map<BlockState, BlockState> HYDRATE_MAP = new Object2ObjectOpenHashMap<>();
	private static final BooleanProperty CRACKEDNESS = WWBlockStateProperties.CRACKED;
	public static final MapCodec<ScorchedBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
		BlockState.CODEC.fieldOf("previous_state").forGetter((scorchedBlock) -> scorchedBlock.wetState),
		Codec.BOOL.fieldOf("brushable").forGetter((scorchedBlock) -> scorchedBlock.canBrush),
		SoundEvent.DIRECT_CODEC.fieldOf("brush_sound").forGetter((scorchedBlock) -> scorchedBlock.brushSound),
		SoundEvent.DIRECT_CODEC.fieldOf("brush_completed_sound").forGetter((scorchedBlock) -> scorchedBlock.brushCompletedSound),
		propertiesCodec()
	).apply(instance, ScorchedBlock::new));
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
			level.setBlockAndUpdate(pos, SCORCH_MAP.get(state));
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

	@NotNull
	@Override
	protected MapCodec<? extends ScorchedBlock> codec() {
		return CODEC;
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
		Fluid fluid = DripstoneDripApi.getDripstoneFluid(level, pos);
		if (fluid == Fluids.LAVA) {
			if (random.nextBoolean()) scorch(state, level, pos);
		} else if (fluid == Fluids.WATER) {
			hydrate(state, level, pos);
		}
		if (level.getBlockEntity(pos) instanceof ScorchedBlockEntity scorchedBlock) {
			scorchedBlock.checkReset();
		}
	}

	@Override
	@NotNull
	public ItemStack getCloneItemStack(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
		ItemStack superStack = super.getCloneItemStack(level, pos, state);
		if (state.getValue(WWBlockStateProperties.CRACKED)) {
			ItemBlockStateTagUtils.setProperty(superStack, WWBlockStateProperties.CRACKED, true);
		}
		return superStack;
	}

	@Override
	@NotNull
	public RenderShape getRenderShape(@NotNull BlockState blockState) {
		return RenderShape.MODEL;
	}
}

