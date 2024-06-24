/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.loot;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.ByteTag;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LootTable.class)
public final class LootTableMixin {

	@Unique
	private boolean isStoneChest = false;

	@Inject(method = "fill", at = @At("HEAD"))
	public void wilderWild$fill(Container container, LootParams parameterSet, long seed, CallbackInfo ci) {
		this.isStoneChest = container instanceof StoneChestBlockEntity;
	}

	@WrapOperation(
		method = "fill",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/Container;setItem(ILnet/minecraft/world/item/ItemStack;)V",
			ordinal = 1
		)
	)
	public void wilderWild$setStoneItem(Container instance, int i, ItemStack itemStack, Operation<Void> original) {
		if (this.isStoneChest) {
			CustomData.update(DataComponents.CUSTOM_DATA, itemStack, compoundTag -> {
				compoundTag.put("wilderwild_is_ancient", ByteTag.valueOf(true));
			});
		}
		original.call(instance, i, itemStack);
	}

}
