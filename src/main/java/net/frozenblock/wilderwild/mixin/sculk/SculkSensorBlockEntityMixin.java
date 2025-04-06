/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.mixin.sculk;

import net.frozenblock.wilderwild.block.entity.impl.SculkSensorInterface;
import net.frozenblock.wilderwild.registry.WWGameEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CalibratedSculkSensorBlock;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SculkSensorPhase;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SculkSensorBlockEntity.class)
public abstract class SculkSensorBlockEntityMixin extends BlockEntity implements SculkSensorInterface {

	@Unique
	public int wilderWild$animTicks;
	@Unique
	public int wilderWild$prevAnimTicks;
	@Unique
	public int wilderWild$age;
	@Unique
	public boolean wilderWild$active;
	@Unique
	public boolean wilderWild$prevActive;
	@Unique
	public Direction wilderWild$facing = Direction.NORTH;

	private SculkSensorBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Inject(
		method = "<init>(Lnet/minecraft/world/level/block/entity/BlockEntityType;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V",
		at = @At("TAIL")
	)
	private void wilderWild$init(BlockEntityType blockEntityType, BlockPos blockPos, BlockState blockState, CallbackInfo info) {
		if (blockEntityType == BlockEntityType.CALIBRATED_SCULK_SENSOR && blockState.getBlock() instanceof CalibratedSculkSensorBlock) {
			this.wilderWild$facing = blockState.getValue(CalibratedSculkSensorBlock.FACING);
		}
	}

	@Unique
	@Override
	public void wilderWild$tickClient(Level level, BlockPos pos, @NotNull BlockState state) {
		if (state.hasProperty(CalibratedSculkSensorBlock.FACING)) {
			this.wilderWild$facing = state.getValue(CalibratedSculkSensorBlock.FACING);
		}
		boolean active = this.wilderWild$isActive();
		this.wilderWild$prevActive = active;
		this.wilderWild$active = SculkSensorBlock.getPhase(state) != SculkSensorPhase.INACTIVE;
		int animTicks = this.wilderWild$getAnimTicks();
		this.wilderWild$prevAnimTicks = animTicks;
		if (animTicks > 0) animTicks -= 1;
		this.wilderWild$animTicks = animTicks;
		this.wilderWild$age = this.wilderWild$getAge() + 1;
	}

	@Unique
	@Override
	public int wilderWild$getAge() {
		return this.wilderWild$age;
	}

	@Unique
	@Override
	public int wilderWild$getAnimTicks() {
		return this.wilderWild$animTicks;
	}

	@Unique
	@Override
	public int wilderWild$getPrevAnimTicks() {
		return this.wilderWild$prevAnimTicks;
	}

	@Unique
	@Override
	public boolean wilderWild$isActive() {
		return this.wilderWild$active;
	}

	@Unique
	@Override
	public void wilderWild$setAnimTicks(int i) {
		this.wilderWild$animTicks = i;
	}

	@Unique
	@Override
	public Direction wilderWild$getFacing() {
		return this.wilderWild$facing;
	}

	@Mixin(SculkSensorBlockEntity.VibrationUser.class)
	public static class VibrationUserMixin {

		@Shadow
		@Final
		protected BlockPos blockPos;

		@Inject(
			method = "onReceiveVibration",
			at = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/world/level/block/SculkSensorBlock;activate(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;II)V",
				shift = At.Shift.AFTER
			)
		)
		private void wilderWild$onReceiveVibration(
			ServerLevel world, BlockPos pos, Holder<GameEvent> gameEvent, @Nullable Entity entity, @Nullable Entity entity2, float f, CallbackInfo info
		) {
			world.gameEvent(entity, WWGameEvents.SCULK_SENSOR_ACTIVATE, this.blockPos);
		}
	}

}
