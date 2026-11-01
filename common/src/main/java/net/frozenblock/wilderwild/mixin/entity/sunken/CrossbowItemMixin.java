/*
 * Copyright 2026 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.mixin.entity.sunken;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {

	@ModifyExpressionValue(
		method = "shootProjectile",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/sounds/SoundEvents;CROSSBOW_SHOOT:Lnet/minecraft/sounds/SoundEvent;",
			opcode = Opcodes.GETSTATIC
		)
	)
	public SoundEvent wilderWild$useSunkenShootSoundEvent(
		SoundEvent original,
		@Local(argsOnly = true, ordinal = 0) LivingEntity livingEntity
	) {
		if (livingEntity.is(WWEntityTypes.SUNKEN.get())) return WWSounds.ENTITY_SUNKEN_SHOOT.get();
		return original;
	}
}
