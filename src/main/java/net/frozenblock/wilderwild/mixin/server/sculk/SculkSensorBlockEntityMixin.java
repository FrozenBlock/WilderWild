/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.server.sculk;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.wilderwild.misc.interfaces.SculkSensorTickInterface;
import net.frozenblock.wilderwild.misc.server.EasyPacket;
import net.frozenblock.wilderwild.registry.RegisterGameEvents;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SculkSensorBlockEntity.class)
public final class SculkSensorBlockEntityMixin extends BlockEntity implements SculkSensorTickInterface {

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

	@Mixin(SculkSensorBlockEntity.VibrationConfig.class)
	public static class VibrationConfigMixin {
		@Shadow
		@Final
		protected SculkSensorBlockEntity sculkSensor;

		@Inject(at = @At("HEAD"), method = "onSignalReceive")
		private void wilderWild$onSignalReceive(ServerLevel level, GameEventListener listener, BlockPos pos, GameEvent event, @Nullable Entity entity, @Nullable Entity sourceEntity, float f, CallbackInfo info) {
			BlockState blockState = this.sculkSensor.getBlockState();
			if (SculkSensorBlock.canActivate(blockState)) {
				level.gameEvent(entity, RegisterGameEvents.SCULK_SENSOR_ACTIVATE, this.sculkSensor.getBlockPos());
				BlockState state = level.getBlockState(this.sculkSensor.getBlockPos());
				level.setBlockAndUpdate(this.sculkSensor.getBlockPos(), state.setValue(RegisterProperties.HICCUPPING, false));
			}
		}
	}

	@Unique
    @Override
    public void wilderWild$tickServer(ServerLevel level, BlockPos pos, BlockState state) {
        SculkSensorBlockEntity sensor = SculkSensorBlockEntity.class.cast(this);
        sensor.getListener().tick(level);
        boolean bl2 = level.random.nextBoolean();
        if (state.getValue(RegisterProperties.HICCUPPING)) {
            if (bl2) {
                double x = (pos.getX() - 0.1) + (level.random.nextFloat() * 1.2);
                double y = pos.getY() + level.random.nextFloat();
                double z = (pos.getZ() - 0.1) + (level.random.nextFloat() * 1.2);
                EasyPacket.EasySensorHiccupPacket.createParticle(level, new Vec3(x, y, z));
            }
            if (SculkSensorBlock.canActivate(state) && level.random.nextInt(320) <= 1) {
                SculkSensorBlock.activate(null, level, pos, state, AdvancedMath.random().nextInt(15), sensor.getLastVibrationFrequency());
                level.gameEvent(null, GameEvent.SCULK_SENSOR_TENDRILS_CLICKING, pos);
                level.gameEvent(null, RegisterGameEvents.SCULK_SENSOR_ACTIVATE, pos);
                level.playSound(null, pos, RegisterSounds.BLOCK_SCULK_SENSOR_HICCUP, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.1F + 0.7F);
            }
        }
        this.wilderWild$prevAnimTicks = this.wilderWild$animTicks;
        if (this.wilderWild$animTicks > 0) {
            --this.wilderWild$animTicks;
        }
        ++this.wilderWild$age;
        this.wilderWild$active = state.getValue(BlockStateProperties.SCULK_SENSOR_PHASE) == SculkSensorPhase.ACTIVE;
        if (this.wilderWild$active != this.wilderWild$prevActive || this.wilderWild$animTicks == 10) {
            for (ServerPlayer player : PlayerLookup.tracking(level, pos)) {
                player.connection.send(sensor.getUpdatePacket());
            }
        }
        this.wilderWild$prevActive = this.wilderWild$active;
    }

	@Unique
    @Override
    public void wilderWild$tickClient(Level level, BlockPos pos, BlockState state) {
		this.wilderWild$prevAnimTicks = this.wilderWild$animTicks;
        if (this.wilderWild$animTicks > 0) {
            --this.wilderWild$animTicks;
        }
        ++this.wilderWild$age;
    }

	@Unique
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(SculkSensorBlockEntity.class.cast(this));
    }

	@Unique
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
    public void wilderWild$setActive(boolean active) {
        this.wilderWild$active = active;
    }

	@Unique
    @Override
    public void wilderWild$setAnimTicks(int i) {
        this.wilderWild$animTicks = i;
    }

    @Inject(at = @At("TAIL"), method = "load")
    private void wilderWild$load(CompoundTag nbt, CallbackInfo info) {
        this.wilderWild$age = nbt.getInt("age");
        this.wilderWild$animTicks = nbt.getInt("animTicks");
        this.wilderWild$prevAnimTicks = nbt.getInt("prevAnimTicks");
        this.wilderWild$active = nbt.getBoolean("active");
        this.wilderWild$prevActive = nbt.getBoolean("prevActive");
    }

    @Inject(at = @At("TAIL"), method = "saveAdditional")
	private void wilderWild$saveAdditional(CompoundTag nbt, CallbackInfo info) {
        nbt.putInt("age", this.wilderWild$age);
        nbt.putInt("animTicks", this.wilderWild$animTicks);
        nbt.putInt("prevAnimTicks", this.wilderWild$prevAnimTicks);
        nbt.putBoolean("active", this.wilderWild$active);
        nbt.putBoolean("prevActive", this.wilderWild$prevActive);
    }

}
