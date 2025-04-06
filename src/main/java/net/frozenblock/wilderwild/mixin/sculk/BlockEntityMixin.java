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

import net.frozenblock.wilderwild.block.entity.impl.SculkSensorInterface;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntity.class)
public class BlockEntityMixin {

	@Shadow
	@Nullable
	protected Level level;

	@Inject(method = "triggerEvent", at = @At("HEAD"), cancellable = true)
	public void wilderWild$triggerEvent(int eventId, int data, CallbackInfoReturnable<Boolean> info) {
		if (BlockEntity.class.cast(this) instanceof SculkSensorInterface sculkSensorInterface) {
			if (eventId == 1) {
				sculkSensorInterface.wilderWild$setAnimTicks(10);
				info.setReturnValue(true);
			}
		}
	}
}
