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

package net.frozenblock.wilderwild.mixin.trailiertales.missing;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import java.util.List;
import net.frozenblock.wilderwild.WWFeatureFlags;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

	@Unique
	private static final Component WILDERWILD$TRAILIERTALES_DISABLED_ITEM_TOOLTIP = Component.translatable("item.disabled.trailiertales").withStyle(ChatFormatting.RED);

	@Shadow
	public abstract Item getItem();

	@ModifyReturnValue(
		method = "getTooltipLines",
		at = @At(
			value = "RETURN",
			ordinal = 1
		)
	)
	public List<Component> wilderWild$appendTrailierTalesRequirementTooltip(List<Component> original) {
		if (this.getItem().requiredFeatures().contains(WWFeatureFlags.TRAILIER_TALES_COMPAT)) {
			original.add(WILDERWILD$TRAILIERTALES_DISABLED_ITEM_TOOLTIP);
		}
		return original;
	}
}
