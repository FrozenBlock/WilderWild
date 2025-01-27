/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.projectile;

import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.wilderwild.config.WWItemConfig;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.world.entity.projectile.AbstractThrownPotion;
import net.minecraft.world.entity.projectile.ThrownSplashPotion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractThrownPotion.class)
public class AbstractThrownPotionMixin {

	@Inject(
		method = "onHit",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/level/ServerLevel;levelEvent(ILnet/minecraft/core/BlockPos;I)V"
		)
	)
	public void wilderWild$onHit(
		HitResult hitResult, CallbackInfo info,
		@Local PotionContents potionContents
	) {
		if (WWItemConfig.get().projectileLandingSounds.potionLandingSounds) {
			ThrownSplashPotion potion = ThrownSplashPotion.class.cast(this);

			potion.playSound(WWSounds.ITEM_POTION_SPLASH, 1F, 1F);

			if (potionContents.hasEffects()) {
				potion.playSound(WWSounds.ITEM_POTION_MAGIC, 1F, 1F + (potion.getRandom().nextFloat() * 0.2F));
			}
		}
	}

}
