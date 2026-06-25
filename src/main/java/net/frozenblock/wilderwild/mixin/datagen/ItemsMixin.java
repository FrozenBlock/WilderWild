/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.datagen;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.WWFeatureFlags;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Items.class, priority = 2000)
public class ItemsMixin {

	@Inject(
		method = "registerItem(Lnet/minecraft/resources/ResourceKey;Ljava/util/function/Function;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/Item;",
		at = @At("HEAD")
	)
	private static void wilderWild$appendFeatureFlag(
		CallbackInfoReturnable<Block> info,
		@Local(argsOnly = true) ResourceKey<Item> id,
		@Local(argsOnly = true) LocalRef<Item.Properties> properties
	) {
		if (id.identifier().getNamespace().equals(WWConstants.MOD_ID)) properties.set(properties.get().requiredFeatures(WWFeatureFlags.FEATURE_FLAG));
	}
}
