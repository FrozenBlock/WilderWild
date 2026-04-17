/*
 * Copyright 2025-2026 FrozenBlock
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
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CalibratedSculkSensorBlock;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SculkSensorBlockEntity.class)
public abstract class SculkSensorBlockEntityMixin extends BlockEntity implements SculkSensorInterface {

	@Unique
	public int wilderWild$age;
	@Unique
	public boolean wilderWild$active;
	@Unique
	public Direction wilderWild$facing = Direction.NORTH;
	@Unique
	public int wilderWild$tendrilAnimation0;
	@Unique
	public int wilderWild$tendrilAnimation;

	private SculkSensorBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Inject(
		method = "<init>(Lnet/minecraft/world/level/block/entity/BlockEntityType;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V",
		at = @At("TAIL")
	)
	private void wilderWild$init(BlockEntityType type, BlockPos worldPosition, BlockState blockState, CallbackInfo info) {
		if (type != BlockEntityTypes.CALIBRATED_SCULK_SENSOR || !(blockState.getBlock() instanceof CalibratedSculkSensorBlock)) return;
		this.wilderWild$facing = blockState.getValueOrElse(CalibratedSculkSensorBlock.FACING, Direction.NORTH);
	}

	@Unique
	@Override
	public void wilderWild$tickClient(Level level, BlockPos pos, BlockState state) {
		this.wilderWild$age += 1;
		this.wilderWild$facing = state.getValueOrElse(CalibratedSculkSensorBlock.FACING, Direction.NORTH);

		final boolean wasActive = this.wilderWild$active;
		this.wilderWild$active = !SculkSensorBlock.canActivate(state);
		if (this.wilderWild$active && !wasActive) this.wilderWild$tendrilAnimation = 10;

		this.wilderWild$tendrilAnimation0 = this.wilderWild$tendrilAnimation;
		this.wilderWild$tendrilAnimation = Math.max(0, this.wilderWild$tendrilAnimation - 1);
	}

	@Unique
	@Override
	public float wilderWild$getAgeInTicks(float partialTicks) {
		return this.wilderWild$age + partialTicks;
	}

	@Unique
	@Override
	public float wilderWild$getTendrilAnimation(float partialTicks) {
		return Mth.lerp(partialTicks, this.wilderWild$tendrilAnimation0, this.wilderWild$tendrilAnimation) * 0.1F;
	}

	@Unique
	@Override
	public boolean wilderWild$isActive() {
		return this.wilderWild$active;
	}

	@Unique
	@Override
	public Direction wilderWild$getFacing() {
		return this.wilderWild$facing;
	}
}
