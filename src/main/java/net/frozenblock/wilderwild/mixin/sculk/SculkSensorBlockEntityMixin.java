/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.sculk;

import java.util.Objects;
import net.frozenblock.wilderwild.block.entity.impl.SculkSensorTickInterface;
import net.frozenblock.wilderwild.registry.WWGameEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CalibratedSculkSensorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CalibratedSculkSensorBlockEntity;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SculkSensorPhase;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
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
public abstract class SculkSensorBlockEntityMixin extends BlockEntity implements SculkSensorTickInterface {

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

	@Shadow
	public abstract VibrationSystem.User getVibrationUser();

	@Shadow
	public abstract VibrationSystem.Data getVibrationData();

	@Unique
	@Override
	public void wilderWild$tickServer(ServerLevel level, BlockPos pos, @NotNull BlockState state) {
		SculkSensorBlockEntity sensor = SculkSensorBlockEntity.class.cast(this);
		VibrationSystem.Ticker.tick(level, this.getVibrationData(), this.getVibrationUser());
		int animTicks = this.wilderWild$getAnimTicks();
		this.wilderWild$setPrevAnimTicks(animTicks);
		if (this.wilderWild$getAnimTicks() > 0) {
			this.wilderWild$setAnimTicks(animTicks -= 1);
		}
		this.wilderWild$setAge(this.wilderWild$getAge() + 1);
		this.wilderWild$setActive(state.getValue(BlockStateProperties.SCULK_SENSOR_PHASE) != SculkSensorPhase.INACTIVE);
		if (this.wilderWild$isActive() != this.wilderWild$isPrevActive() || animTicks == 10) {
			this.wilderWild$markForUpdate();
		}
		this.wilderWild$setPrevActive(this.wilderWild$isActive());
		if (sensor instanceof CalibratedSculkSensorBlockEntity) {
			if (state.hasProperty(CalibratedSculkSensorBlock.FACING)) {
				this.wilderWild$setFacing(state.getValue(CalibratedSculkSensorBlock.FACING));
			}
		}
	}

	@Unique
	@Override
	public void wilderWild$tickClient(Level level, BlockPos pos, BlockState state) {
		int animTicks = this.wilderWild$getAnimTicks();
		this.wilderWild$setPrevAnimTicks(animTicks);
		if (animTicks > 0) {
			animTicks -= 1;
		}
		this.wilderWild$setAnimTicks(animTicks);
		this.wilderWild$setAge(this.wilderWild$getAge() + 1);
	}

	@Unique
	private void wilderWild$markForUpdate() {
		this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS);
	}

	@Unique
	@Override
	public int wilderWild$getAge() {
		return this.wilderWild$age;
	}

	@Unique
	@Override
	public void wilderWild$setAge(int i) {
		this.wilderWild$age = i;
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

	@Override
	public void wilderWild$setPrevAnimTicks(int i) {
		this.wilderWild$prevAnimTicks = i;
	}

	@Unique
	@Override
	public boolean wilderWild$isActive() {
		return this.wilderWild$active;
	}

	@Unique
	@Override
	public void wilderWild$setActive(boolean active) {
		this.wilderWild$active = active;
	}

	@Unique
	@Override
	public boolean wilderWild$isPrevActive() {
		return this.wilderWild$prevActive;
	}

	@Unique
	@Override
	public void wilderWild$setPrevActive(boolean active) {
		this.wilderWild$prevActive = active;
	}

	@Unique
	@Override
	public void wilderWild$setAnimTicks(int i) {
		this.wilderWild$animTicks = i;
	}

	@Unique
	@Override
	public void wilderWild$setFacing(Direction facing) {
		this.wilderWild$facing = facing;
	}

	@Unique
	@Override
	public Direction wilderWild$getFacing() {
		return this.wilderWild$facing;
	}

	@Inject(method = "loadAdditional", at = @At("TAIL"))
	private void wilderWild$load(CompoundTag nbt, HolderLookup.Provider provider, CallbackInfo info) {
		this.wilderWild$setAge(nbt.getInt("age"));
		this.wilderWild$setAnimTicks(nbt.getInt("animTicks"));
		this.wilderWild$setPrevAnimTicks(nbt.getInt("prevAnimTicks"));
		this.wilderWild$setActive(nbt.getBoolean("active"));
		this.wilderWild$setPrevActive(nbt.getBoolean("prevActive"));

		Direction facing = Direction.byName(nbt.getString("facing"));
		this.wilderWild$setFacing(Objects.requireNonNullElse(facing, Direction.NORTH));
	}

	@Inject(method = "saveAdditional", at = @At("TAIL"))
	private void wilderWild$saveAdditional(CompoundTag nbt, HolderLookup.Provider provider, CallbackInfo info) {
		nbt.putInt("age", this.wilderWild$getAge());
		nbt.putInt("animTicks", this.wilderWild$getAnimTicks());
		nbt.putInt("prevAnimTicks", this.wilderWild$getPrevAnimTicks());
		nbt.putBoolean("active", this.wilderWild$isActive());
		nbt.putBoolean("prevActive", this.wilderWild$isPrevActive());
		nbt.putString("facing", this.wilderWild$getFacing().getName());
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
