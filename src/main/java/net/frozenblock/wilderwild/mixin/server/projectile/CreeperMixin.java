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

package net.frozenblock.wilderwild.mixin.server.projectile;

import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.world.entity.monster.Creeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Creeper.class)
public final class CreeperMixin {

	@Inject(method = "spawnLingeringCloud", at = @At("HEAD"))
	public void wilderWild$spawnLingeringCloud(CallbackInfo info) {
		if (WilderSharedConstants.config().potionLandingSounds()) {
			Creeper creeper = Creeper.class.cast(this);
			if (!creeper.getActiveEffects().isEmpty()) {
				creeper.playSound(RegisterSounds.ITEM_POTION_MAGIC, 1.0F, 1.0F + (creeper.level().random.nextFloat() * 0.2F));
				creeper.playSound(RegisterSounds.ITEM_POTION_LINGERING, 1.0F, 1.0F + (creeper.level().random.nextFloat() * 0.2F));
			}
		}
	}
}
