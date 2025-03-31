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

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntity.class)
public class BlockEntityMixin {

	@Inject(method = "getUpdatePacket", at = @At("HEAD"), cancellable = true)
	public void wilderWild$getUpdatePacket(CallbackInfoReturnable<Packet<ClientGamePacketListener>> info) {
		if (BlockEntity.class.cast(this) instanceof SculkSensorBlockEntity sensorBlockEntity) {
			info.setReturnValue(ClientboundBlockEntityDataPacket.create(sensorBlockEntity));
		}
	}

	@Inject(method = "getUpdateTag", at = @At("HEAD"), cancellable = true)
	public void wilderWild$getUpdateTag(HolderLookup.Provider lookupProvider, CallbackInfoReturnable<CompoundTag> info) {
		if (BlockEntity.class.cast(this) instanceof SculkSensorBlockEntity sensorBlockEntity) {
			info.setReturnValue(sensorBlockEntity.saveWithoutMetadata(lookupProvider));
		}
	}
}
