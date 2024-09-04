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
import net.frozenblock.wilderwild.block.entity.impl.SculkSensorTickInterface;
import net.frozenblock.wilderwild.networking.packet.WilderSensorHiccupPacket;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWGameEvents;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CalibratedSculkSensorBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SculkSensorPhase;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(CalibratedSculkSensorBlockEntity.class)
public abstract class CalibratedSculkSensorBlockEntityMixin extends BlockEntity implements SculkSensorTickInterface {

	private CalibratedSculkSensorBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Unique
	@Override
	public void wilderWild$tickServer(ServerLevel level, BlockPos pos, @NotNull BlockState state) {
		CalibratedSculkSensorBlockEntity sensor = CalibratedSculkSensorBlockEntity.class.cast(this);
		VibrationSystem.Ticker.tick(level, sensor.getVibrationData(), sensor.createVibrationUser());
		if (state.getValue(WWBlockStateProperties.HICCUPPING)) {
			RandomSource random = level.getRandom();
			if (random.nextBoolean() && random.nextBoolean()) {
				double x = (pos.getX() - 0.1D) + (random.nextDouble() * 1.2D);
				double y = pos.getY() + random.nextDouble();
				double z = (pos.getZ() - 0.1D) + (random.nextDouble() * 1.2D);
				WilderSensorHiccupPacket.sendToAll(sensor, new Vec3(x, y, z));
			}
			if (SculkSensorBlock.canActivate(state) && random.nextInt(320) <= 1) {
				((SculkSensorBlock) state.getBlock()).activate(null, level, pos, state, random.nextInt(15), sensor.getLastVibrationFrequency());
				level.gameEvent(null, GameEvent.SCULK_SENSOR_TENDRILS_CLICKING, pos);
				level.gameEvent(null, WWGameEvents.SCULK_SENSOR_ACTIVATE, pos);
				level.playSound(null, pos, WWSounds.BLOCK_SCULK_SENSOR_HICCUP, SoundSource.BLOCKS, 1F, random.nextFloat() * 0.1F + 0.7F);
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
		return ClientboundBlockEntityDataPacket.create(CalibratedSculkSensorBlockEntity.class.cast(this));
	}

	@Unique
	@NotNull
	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
		return this.saveWithoutMetadata(provider);
	}

}
