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

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class WideFlowerBlock extends FlowerBlock {
	protected static final VoxelShape WIDE_SHAPE = Block.box(2D, 0D, 2D, 14D, 12D, 14D);

	public WideFlowerBlock(Holder<MobEffect> holder, float f, Properties properties) {
		super(holder, f, properties);
	}

	@Override
	protected @NotNull VoxelShape getShape(@NotNull BlockState state, BlockGetter level, BlockPos pos, CollisionContext collisionContext) {
		final Vec3 offset = state.getOffset(pos);
		return WIDE_SHAPE.move(offset.x, offset.y, offset.z);
	}
}
