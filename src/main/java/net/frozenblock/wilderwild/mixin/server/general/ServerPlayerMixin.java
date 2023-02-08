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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

	@Shadow
	public ServerGamePacketListenerImpl connection;
	@Shadow
	private boolean isChangingDimension;

	@Unique
	public Optional<ArrayList<SaveableItemCooldowns.SaveableCooldownInstance>> wilderWild$savedItemCooldowns = Optional.empty();
	@Unique @Nullable
	private CompoundTag wilderWild$savedCooldownTag;

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
		if (this.wilderWild$savedItemCooldowns.isPresent() && this.connection != null && this.connection.getConnection().isConnected() && !this.isChangingDimension) {
			SaveableItemCooldowns.setCooldowns(this.wilderWild$savedItemCooldowns.get(), ServerPlayer.class.cast(this));
			this.wilderWild$savedItemCooldowns = Optional.empty();
		}
	}

	@Inject(method = "changeDimension", at = @At(value = "HEAD"))
	public void wilderWild$changeDimensionSaveCooldowns(ServerLevel destination, CallbackInfoReturnable<Entity> info) {
		CompoundTag tempTag = new CompoundTag();
		SaveableItemCooldowns.saveCooldowns(tempTag, ServerPlayer.class.cast(this));
		this.wilderWild$savedCooldownTag = tempTag;
	}

	@Inject(method = "changeDimension", at = @At(value = "RETURN"))
	public void wilderWild$changeDimensionLoadCooldowns(ServerLevel destination, CallbackInfoReturnable<Entity> info) {
		if (this.wilderWild$savedCooldownTag != null) {
			this.wilderWild$savedItemCooldowns = Optional.of(SaveableItemCooldowns.readCooldowns(this.wilderWild$savedCooldownTag));
		}
	}

}
