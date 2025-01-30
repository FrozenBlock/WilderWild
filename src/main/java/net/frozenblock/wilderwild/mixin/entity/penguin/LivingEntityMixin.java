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

package net.frozenblock.wilderwild.mixin.entity.penguin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.wilderwild.entity.Penguin;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

	@WrapOperation(
		method = "travelInAir",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/Block;getFriction()F"
		)
	)
	public float wilderWild$discardPenguinFriction(Block instance, Operation<Float> original) {
		if (LivingEntity.class.cast(this) instanceof Penguin) {
			if (instance.builtInRegistryHolder().is(WWBlockTags.PENGUIN_IGNORE_FRICTION)) {
				return 0.6F;
			}
		}
		return original.call(instance);
	}

}
