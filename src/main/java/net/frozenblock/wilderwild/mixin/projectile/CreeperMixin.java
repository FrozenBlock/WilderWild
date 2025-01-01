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

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.wilderwild.config.WWItemConfig;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.monster.Creeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Creeper.class)
public final class CreeperMixin {

	@ModifyExpressionValue(
		method = "spawnLingeringCloud",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"
		)
	)
	public boolean wilderWild$spawnLingeringCloud(boolean original) {
		if (original && WWItemConfig.get().projectileLandingSounds.potionLandingSounds) {
			Creeper creeper = Creeper.class.cast(this);
			if (!creeper.getActiveEffects().isEmpty()) {
				RandomSource random = creeper.getRandom();
				creeper.playSound(WWSounds.ITEM_POTION_MAGIC, 1F, 1F + (random.nextFloat() * 0.2F));
				creeper.playSound(WWSounds.ITEM_POTION_LINGERING, 1F, 1F + (random.nextFloat() * 0.2F));
			}
		}
		return original;
	}

}
