/*
 * Copyright 2024 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.entity.easter;

import net.frozenblock.lib.spotting_icons.impl.EntitySpottingIconInterface;
import net.frozenblock.wilderwild.WilderConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {

	@Unique
	private static final ResourceLocation WILDER_WILD$STELLA_TEXTURE = WilderConstants.id("textures/spotting_icons/stella.png");
	@Unique
	private static final ResourceLocation WILDER_WILD$STELLA = WilderConstants.id("stella");

	@Inject(method = "setCustomName", at = @At(value = "HEAD"))
	public void wilderWild$setCustomName(@Nullable Component name, CallbackInfo info) {
		if (name != null && name.getString().equalsIgnoreCase("Stella")) {
			((EntitySpottingIconInterface) Entity.class.cast(this)).getSpottingIconManager()
				.setIcon(WILDER_WILD$STELLA_TEXTURE, 5F, 8F, WILDER_WILD$STELLA);
		}
	}
}
