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
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrownPotion.class)
public class ThrownPotionMixin {

	@Inject(method = "onHit", at = @At("HEAD"))
	public void wilderWild$onHit(HitResult result, CallbackInfo info) {
		ThrownPotion potion = ThrownPotion.class.cast(this);

		if (WilderSharedConstants.CONFIG().potionLandingSounds()) {
			potion.playSound(RegisterSounds.ITEM_POTION_SPLASH, 1.0F, 1.0F);
			if (!PotionUtils.getMobEffects(potion.getItem()).isEmpty()) {
				potion.playSound(RegisterSounds.ITEM_POTION_MAGIC, 1.0F, 1.0F + (potion.level.random.nextFloat() * 0.2F));
				if (this.isLingering()) {
					potion.playSound(RegisterSounds.ITEM_POTION_LINGERING, 1.0F, 1.0F + (potion.level.random.nextFloat() * 0.2F));
				}
			}
		}
	}

	@Shadow
	private boolean isLingering() {
		throw new AssertionError("Mixin injection failed - Wilder Wild ThrownPotionMixin");
	}

}
