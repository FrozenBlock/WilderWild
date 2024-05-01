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

package net.frozenblock.wilderwild.mixin.sculk;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.frozenblock.wilderwild.misc.interfaces.SculkSensorTickInterface;
import net.frozenblock.wilderwild.networking.packet.WilderSensorHiccupPacket;
import net.frozenblock.wilderwild.registry.RegisterGameEvents;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SculkSensorPhase;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.phys.Vec3;
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
		if (state.getValue(RegisterProperties.HICCUPPING)) {
			RandomSource random = level.getRandom();
			if (random.nextBoolean() && random.nextBoolean()) {
				double x = (pos.getX() - 0.1) + (random.nextFloat() * 1.2);
				double y = pos.getY() + random.nextFloat();
				double z = (pos.getZ() - 0.1) + (random.nextFloat() * 1.2);
				WilderSensorHiccupPacket.sendToAll(sensor, new Vec3(x, y, z));
			}
			if (SculkSensorBlock.canActivate(state) && random.nextInt(320) <= 1) {
				((SculkSensorBlock) state.getBlock()).activate(null, level, pos, state, random.nextInt(15), sensor.getLastVibrationFrequency());
				level.gameEvent(null, GameEvent.SCULK_SENSOR_TENDRILS_CLICKING, pos);
				level.gameEvent(null, RegisterGameEvents.SCULK_SENSOR_ACTIVATE, pos);
				level.playSound(null, pos, RegisterSounds.BLOCK_SCULK_SENSOR_HICCUP, SoundSource.BLOCKS, 1.0F, random.nextFloat() * 0.1F + 0.7F);
			}
		}
		int animTicks = this.wilderWild$getAnimTicks();
		this.wilderWild$setPrevAnimTicks(animTicks);
		if (this.wilderWild$getAnimTicks() > 0) {
			this.wilderWild$setAnimTicks(animTicks -= 1);
		}
		this.wilderWild$setAge(this.wilderWild$getAge() + 1);
		this.wilderWild$setActive(state.getValue(BlockStateProperties.SCULK_SENSOR_PHASE) != SculkSensorPhase.INACTIVE);
		if (this.wilderWild$isActive() != this.wilderWild$isPrevActive() || animTicks == 10) {
			Packet<ClientGamePacketListener> sensorUpdatePacket = sensor.getUpdatePacket();
			if (sensorUpdatePacket != null) {
				for (ServerPlayer player : PlayerLookup.tracking(level, pos)) {
					player.connection.send(sensorUpdatePacket);
				}
			}
		}
		this.wilderWild$setPrevActive(this.wilderWild$isActive());
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
	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(SculkSensorBlockEntity.class.cast(this));
	}

	@Unique
	@NotNull
	@Override
	public CompoundTag getUpdateTag() {
		return this.saveWithoutMetadata();
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

	@Inject(at = @At("TAIL"), method = "load")
	private void wilderWild$load(CompoundTag nbt, CallbackInfo info) {
		this.wilderWild$setAge(nbt.getInt("age"));
		this.wilderWild$setAnimTicks(nbt.getInt("animTicks"));
		this.wilderWild$setPrevAnimTicks(nbt.getInt("prevAnimTicks"));
		this.wilderWild$setActive(nbt.getBoolean("active"));
		this.wilderWild$setPrevActive(nbt.getBoolean("prevActive"));
	}

	@Inject(at = @At("TAIL"), method = "saveAdditional")
	private void wilderWild$saveAdditional(CompoundTag nbt, CallbackInfo info) {
		nbt.putInt("age", this.wilderWild$getAge());
		nbt.putInt("animTicks", this.wilderWild$getAnimTicks());
		nbt.putInt("prevAnimTicks", this.wilderWild$getPrevAnimTicks());
		nbt.putBoolean("active", this.wilderWild$isActive());
		nbt.putBoolean("prevActive", this.wilderWild$isPrevActive());
	}

	@Mixin(SculkSensorBlockEntity.VibrationUser.class)
	public static class VibrationUserMixin {

		@Shadow
		@Final
		protected BlockPos blockPos;

		@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/SculkSensorBlock;activate(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;II)V", shift = At.Shift.AFTER), method = "onReceiveVibration")
		private void wilderWild$onReceiveVibration(ServerLevel world, BlockPos pos, GameEvent gameEvent, @Nullable Entity entity, @Nullable Entity entity2, float f, CallbackInfo info) {
			world.gameEvent(entity, RegisterGameEvents.SCULK_SENSOR_ACTIVATE, this.blockPos);
			BlockState blockState = world.getBlockState(this.blockPos);
			if (blockState.hasProperty(RegisterProperties.HICCUPPING)) {
				world.setBlockAndUpdate(this.blockPos, blockState.setValue(RegisterProperties.HICCUPPING, false));
			}
		}
	}

}
