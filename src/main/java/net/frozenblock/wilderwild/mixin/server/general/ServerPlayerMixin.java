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

package net.frozenblock.wilderwild.mixin.server.general;

import java.util.ArrayList;
import java.util.Optional;
import net.frozenblock.wilderwild.item.cooldown.SaveableItemCooldowns;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

	@Shadow
	public ServerGamePacketListenerImpl connection;

	@Unique
	public Optional<ArrayList<SaveableItemCooldowns.SaveableCooldownInstance>> wilderWild$savedItemCooldowns = Optional.empty();

	@Inject(method = "readAdditionalSaveData", at = @At(value = "TAIL"))
	public void wilderWild$readAdditionalSaveData(CompoundTag compound, CallbackInfo info) {
		this.wilderWild$savedItemCooldowns = Optional.of(SaveableItemCooldowns.readCooldowns(compound));
	}

	@Inject(method = "addAdditionalSaveData", at = @At(value = "TAIL"))
	public void wilderWild$addAdditionalSaveData(CompoundTag compound, CallbackInfo info) {
		SaveableItemCooldowns.saveCooldowns(compound, ServerPlayer.class.cast(this));
	}

	@Inject(method = "tick", at = @At(value = "TAIL"))
	public void wilderWild$tick(CallbackInfo info) {
		if (this.wilderWild$savedItemCooldowns.isPresent() && this.connection != null && this.connection.getConnection().isConnected()) {
			SaveableItemCooldowns.setCooldowns(this.wilderWild$savedItemCooldowns.get(), ServerPlayer.class.cast(this));
			this.wilderWild$savedItemCooldowns = Optional.empty();
		}
	}

}
