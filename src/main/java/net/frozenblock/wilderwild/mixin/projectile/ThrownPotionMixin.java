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

import net.frozenblock.wilderwild.config.WWItemConfig;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrownPotion.class)
public abstract class ThrownPotionMixin {

	@Inject(
		method = "onHit",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/level/ServerLevel;levelEvent(ILnet/minecraft/core/BlockPos;I)V",
			ordinal = 0

		)
	)
	public void wilderWild$onHit(HitResult result, CallbackInfo info) {
		if (WWItemConfig.get().projectileLandingSounds.potionLandingSounds) {
			ThrownPotion potion = ThrownPotion.class.cast(this);
			potion.playSound(WWSounds.ITEM_POTION_SPLASH, 1F, 1F);
			if (potion.getItem().getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getAllEffects().iterator().hasNext()) {
				potion.playSound(WWSounds.ITEM_POTION_MAGIC, 1F, 1F + (potion.getRandom().nextFloat() * 0.2F));
				if (this.isLingering()) {
					potion.playSound(WWSounds.ITEM_POTION_LINGERING, 1F, 1F + (potion.getRandom().nextFloat() * 0.2F));
				}
			}
		}
	}

	@Shadow
	protected abstract boolean isLingering();

}
