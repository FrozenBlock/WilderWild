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
import net.frozenblock.wilderwild.entity.monster.skeleton.Sunken;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Monster.class)
public class MonsterMixin {

	@ModifyExpressionValue(
		method = "getProjectile",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/world/item/Items;ARROW:Lnet/minecraft/world/item/Item;",
			opcode = Opcodes.GETSTATIC
		)
	)
	public Item wilderWild$sunkenUsesHarpoon(
		Item original,
		@Local(argsOnly = true) ItemStack heldWeapon
	) {
		if (Monster.class.cast(this) instanceof Sunken && heldWeapon.is(Items.CROSSBOW)) return WWItems.HARPOON.get();
		return original;
	}
}
