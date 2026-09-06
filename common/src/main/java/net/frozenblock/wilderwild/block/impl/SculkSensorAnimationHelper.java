/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.wilderwild.block.impl;

import net.frozenblock.lib.platform.api.attachment.DataAttachmentType;
import net.frozenblock.wilderwild.registry.WWAttachmentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CalibratedSculkSensorBlock;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public final class SculkSensorAnimationHelper {
	private static final DataAttachmentType<Integer> AGE_IN_TICKS = WWAttachmentTypes.SCULK_SENSOR_AGE_IN_TICKS;
	private static final DataAttachmentType<Integer> TENDRIL_ANIMATION0 = WWAttachmentTypes.SCULK_SENSOR_TENDRIL_ANIMATION0;
	private static final DataAttachmentType<Integer> TENDRIL_ANIMATION = WWAttachmentTypes.SCULK_SENSOR_TENDRIL_ANIMATION;
	private static final DataAttachmentType<Boolean> ACTIVE = WWAttachmentTypes.SCULK_SENSOR_ACTIVE;
	private static final DataAttachmentType<Direction> FACING = WWAttachmentTypes.SCULK_SENSOR_FACING;

	public static void tick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
		if (!state.hasProperty(SculkSensorBlock.PHASE) || !(blockEntity instanceof SculkSensorBlockEntity)) return;

		AGE_IN_TICKS.set(blockEntity, AGE_IN_TICKS.getAttachedOrElse(blockEntity, 0) + 1);
		FACING.set(blockEntity, state.getValueOrElse(CalibratedSculkSensorBlock.FACING, Direction.NORTH));

		final boolean wasActive = ACTIVE.getAttachedOrElse(blockEntity, false);
		final boolean isActive = !SculkSensorBlock.canActivate(state);
		ACTIVE.set(blockEntity, isActive);
		if (isActive && !wasActive) TENDRIL_ANIMATION.set(blockEntity, 10);

		TENDRIL_ANIMATION0.set(blockEntity, TENDRIL_ANIMATION.getAttachedOrElse(blockEntity, 0));
		TENDRIL_ANIMATION.set(blockEntity, Math.max(0, TENDRIL_ANIMATION.getAttachedOrElse(blockEntity, 0) - 1));
	}

	public static float ageInTicks(BlockEntity blockEntity, float partialTicks) {
		return AGE_IN_TICKS.getAttachedOrElse(blockEntity, 0) + partialTicks;
	}

	public static float tendrilAnimation(BlockEntity blockEntity, float partialTicks) {
		return Mth.lerp(
			partialTicks,
			TENDRIL_ANIMATION0.getAttachedOrElse(blockEntity, 0),
			TENDRIL_ANIMATION.getAttachedOrElse(blockEntity, 0)
		) * 0.1F;
	}

	public static boolean active(BlockEntity blockEntity) {
		return ACTIVE.getAttachedOrElse(blockEntity, false);
	}

	public static Direction facing(BlockEntity blockEntity) {
		return FACING.getAttachedOrElse(blockEntity, Direction.NORTH);
	}

	private SculkSensorAnimationHelper() {}
}
