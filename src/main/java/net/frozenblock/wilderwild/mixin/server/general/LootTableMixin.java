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

package net.frozenblock.wilderwild.mixin.server.general;

import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.minecraft.nbt.ByteTag;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(LootTable.class)
public final class LootTableMixin {

	@Unique
	private boolean wilderWild$isStoneChest = false;

	@Inject(at = @At("HEAD"), method = "fill")
	public void wilderWild$fill(Container container, LootParams lootParams, long l, CallbackInfo ci) {
		this.wilderWild$isStoneChest = container instanceof StoneChestBlockEntity;
	}

	@ModifyArgs(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/Container;setItem(ILnet/minecraft/world/item/ItemStack;)V", ordinal = 1), method = "fill")
	public void wilderWild$setStoneItem(Args args) {
		if (this.wilderWild$isStoneChest) {
			ItemStack itemStack = args.get(1);
			itemStack.addTagElement("wilderwild_is_ancient", ByteTag.valueOf(true));
		}
	}

}
