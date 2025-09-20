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
import net.frozenblock.wilderwild.block.impl.FroglightTypeHolder;
import net.frozenblock.wilderwild.block.state.properties.FroglightType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class FroglightGoopBodyBlock extends GrowingPlantBodyBlock implements FroglightTypeHolder {
	public static final MapCodec<FroglightGoopBodyBlock> CODEC = RecordCodecBuilder.mapCodec(instance ->
		instance.group(
			FroglightType.CODEC.fieldOf("froglight_type").forGetter(froglightGoopBodyBlock -> froglightGoopBodyBlock.froglightType),
			propertiesCodec()
		).apply(instance, FroglightGoopBodyBlock::new)
	);
	protected static final VoxelShape SHAPE = Block.box(2D, 0D, 2D, 14D, 16D, 14D);
	private final FroglightType froglightType;

	public FroglightGoopBodyBlock(FroglightType froglightType, Properties properties) {
		super(properties, Direction.DOWN, SHAPE, false);
		this.froglightType = froglightType;
	}

	@Override
	public @NotNull MapCodec<FroglightGoopBodyBlock> codec() {
		return CODEC;
	}

	@Override
	public @NotNull FroglightType getFroglightType() {
		return this.froglightType;
	}

	@Override
	protected @NotNull GrowingPlantHeadBlock getHeadBlock() {
		return (GrowingPlantHeadBlock) this.froglightType.getHeadBlock();
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
		return false;
	}
}
