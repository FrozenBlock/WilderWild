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

package net.frozenblock.wilderwild.mixin.client.easter;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Pig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(PigRenderer.class)
public final class UppyBalloo {

	@Unique
	private static final ResourceLocation WILDERWILD$UPPY_BALLOO = WilderConstants.id("textures/entity/pig/uppy_balloo.png");

	@Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/animal/Pig;)Lnet/minecraft/resources/ResourceLocation;", at = @At("RETURN"), cancellable = true, require = 0)
	public void getTextureLocation(Pig pig, CallbackInfoReturnable<ResourceLocation> info) {
		String string = ChatFormatting.stripFormatting(pig.getName().getString());
		if (string != null && string.equalsIgnoreCase("a view from the top")) {
			info.setReturnValue(WILDERWILD$UPPY_BALLOO);
		}
	}
}
