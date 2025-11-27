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
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.frozenblock.wilderwild.block.impl.FroglightTypeHolder;
import net.frozenblock.wilderwild.block.state.properties.FroglightType;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class FroglightGoopBlock extends GrowingPlantHeadBlock implements FroglightTypeHolder {
	public static final MapCodec<FroglightGoopBlock> CODEC = RecordCodecBuilder.mapCodec(instance ->
		instance.group(
			FroglightType.CODEC.fieldOf("froglight_type").forGetter(froglightGoopBlock -> froglightGoopBlock.froglightType),
			propertiesCodec()
		).apply(instance, FroglightGoopBlock::new)
	);
	protected static final VoxelShape SHAPE = Block.box(2D, 6D, 2D, 14D, 16D, 14D);
	private static final float GROWTH_FROM_FROGLIGHT_CHANCE = 0.035F;
	private final FroglightType froglightType;

	public FroglightGoopBlock(FroglightType froglightType, Properties properties) {
		super(properties, Direction.DOWN, SHAPE, false, 0D);
		this.froglightType = froglightType;
	}

	@Override
	public @NotNull MapCodec<FroglightGoopBlock> codec() {
		return CODEC;
	}

	@Override
	public @NotNull FroglightType getFroglightType() {
		return this.froglightType;
	}

	@Override
	protected @NotNull Block getBodyBlock() {
		return this.froglightType.getBodyBlock();
	}

	public static void growFromFroglight(@NotNull BlockState state, ServerLevel level, BlockPos pos, @NotNull RandomSource random) {
		if (random.nextFloat() > GROWTH_FROM_FROGLIGHT_CHANCE || !WWBlockConfig.get().froglightGoopGrowth) return;

		final Optional<FroglightType> optionalType = FroglightType.getFromBaseBlock(state.getBlock());
		if (optionalType.isEmpty()) return;

		final BlockPos abovePos = pos.above();
		final BlockState aboveState = level.getBlockState(abovePos);
		if (!aboveState.getFluidState().is(FluidTags.WATER)) return;

		final FroglightType froglightType = optionalType.get();
		final BlockPos belowPos = pos.below();
		final BlockState belowState = level.getBlockState(belowPos);
		final Block belowBlock = belowState.getBlock();
		if (belowState.isAir()) {
			final Block headBlock = froglightType.getHeadBlock();
			if (!(headBlock instanceof FroglightGoopBlock)) return;
			level.setBlockAndUpdate(belowPos, headBlock.defaultBlockState().setValue(AGE, random.nextInt(10, MAX_AGE)));
		} else if (belowBlock instanceof FroglightTypeHolder froglightTypeHolder) {
			if (froglightTypeHolder.getFroglightType() != froglightType) return;
			if (!(belowBlock instanceof BonemealableBlock bonemealableBlock)) return;
			bonemealableBlock.performBonemeal(level, random, belowPos, belowState);
		}
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
		return false;
	}

	@Override
	protected int getBlocksToGrowWhenBonemealed(RandomSource random) {
		return 1;
	}

	@Override
	protected boolean canGrowInto(@NotNull BlockState state) {
		return state.isAir();
	}
}
