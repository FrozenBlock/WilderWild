/*
 * Copyright 2025 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.item.tooltip;

import java.util.function.Consumer;
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.component.TooltipProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

	@Shadow
	@Final
	public PatchedDataComponentMap components;

	@Inject(
		method = "addDetailsToTooltip",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/item/ItemStack;addToTooltip(Lnet/minecraft/core/component/DataComponentType;Lnet/minecraft/world/item/Item$TooltipContext;Lnet/minecraft/world/item/component/TooltipDisplay;Ljava/util/function/Consumer;Lnet/minecraft/world/item/TooltipFlag;)V",
			ordinal = 0,
			shift = At.Shift.AFTER
		)
	)
	public void wilderWild$addDetailsToTooltip(
		Item.TooltipContext context,
		TooltipDisplay display,
		Player player,
		TooltipFlag flag,
		Consumer<Component> consumer,
		CallbackInfo info
	) {
		this.wilderWild$addToTooltipFromHolder(WWDataComponents.FIREFLY_COLOR, context, display, consumer, flag);
		this.wilderWild$addToTooltipFromHolder(WWDataComponents.BUTTERFLY_VARIANT, context, display, consumer, flag);
	}

	@Unique
	public <T extends TooltipProvider> void wilderWild$addToTooltipFromHolder(
		DataComponentType<Holder<T>> type,
		Item.TooltipContext context,
		TooltipDisplay display,
		Consumer<Component> consumer,
		TooltipFlag flag
	) {
		final Holder<T> possibleVariantHolder = ItemStack.class.cast(this).get(type);
		if (possibleVariantHolder == null || !(possibleVariantHolder.value() instanceof TooltipProvider tooltipProvider) || !(display.shows(type))) return;
		tooltipProvider.addToTooltip(context, consumer, flag, this.components);
	}

}
