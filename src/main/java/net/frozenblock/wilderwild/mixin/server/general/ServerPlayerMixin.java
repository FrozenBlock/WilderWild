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

import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.item.ItemCooldowns;
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
	public int wilderWild$ancientHornCooldown;

	@Inject(method = "readAdditionalSaveData", at = @At(value = "TAIL"))
	public void wilderWild$readAdditionalSaveData(CompoundTag compound, CallbackInfo info) {
		this.wilderWild$ancientHornCooldown = compound.getInt("WilderWildAncientHornCooldown");
	}

	@Inject(method = "addAdditionalSaveData", at = @At(value = "TAIL"))
	public void wilderWild$addAdditionalSaveData(CompoundTag compound, CallbackInfo info) {
		ServerPlayer player = ServerPlayer.class.cast(this);
		if (player.getCooldowns().isOnCooldown(RegisterItems.ANCIENT_HORN)) {
			ItemCooldowns.CooldownInstance cooldownInstance = player.getCooldowns().cooldowns.get(RegisterItems.ANCIENT_HORN);
			compound.putInt("WilderWildAncientHornCooldown", cooldownInstance.endTime - cooldownInstance.startTime);
		} else {
			compound.putInt("WilderWildAncientHornCooldown", 0);
		}
	}

	@Inject(method = "tick", at = @At(value = "TAIL"))
	public void wilderWild$tick(CallbackInfo info) {
		if (this.wilderWild$ancientHornCooldown > 0 && this.connection != null && this.connection.getConnection().isConnected()) {
			ServerPlayer.class.cast(this).getCooldowns().addCooldown(RegisterItems.ANCIENT_HORN, this.wilderWild$ancientHornCooldown);
			this.wilderWild$ancientHornCooldown = 0;
		}
	}

}
