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

package net.frozenblock.wilderwild.mixin.sculk;

import net.frozenblock.wilderwild.block.entity.impl.SculkSensorInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CalibratedSculkSensorBlock;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SculkSensorPhase;
import org.spongepowered.asm.mixin.Mixin;
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
	private void wilderWild$init(BlockEntityType type, BlockPos pos, BlockState state, CallbackInfo info) {
		if (type != BlockEntityType.CALIBRATED_SCULK_SENSOR || !(state.getBlock() instanceof CalibratedSculkSensorBlock)) return;
		this.wilderWild$facing = state.getValueOrElse(CalibratedSculkSensorBlock.FACING, Direction.NORTH);
	}

	@Unique
	@Override
	public void wilderWild$tickClient(Level level, BlockPos pos, BlockState state) {
		this.wilderWild$facing = state.getValueOrElse(CalibratedSculkSensorBlock.FACING, Direction.NORTH);

		this.wilderWild$prevActive = this.wilderWild$active;
		this.wilderWild$active = SculkSensorBlock.getPhase(state) != SculkSensorPhase.INACTIVE;
		if (this.wilderWild$active && !this.wilderWild$prevActive) this.wilderWild$animTicks = 10;

		this.wilderWild$prevAnimTicks = this.wilderWild$animTicks;
		this.wilderWild$animTicks = Math.max(0, this.wilderWild$animTicks - 1);
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
}
