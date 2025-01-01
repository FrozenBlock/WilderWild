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

package net.frozenblock.wilderwild.mixin.client.easter;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.client.renderer.entity.state.PigRenderState;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(PigRenderer.class)
public final class UppyBalloo {
	@Unique
	private static final ResourceLocation WILDERWILD$UPPY_BALLOO = WWConstants.id("textures/entity/pig/uppy_balloo.png");

	@Inject(
		method = "getTextureLocation(Lnet/minecraft/client/renderer/entity/state/PigRenderState;)Lnet/minecraft/resources/ResourceLocation;",
		at = @At("RETURN"),
		cancellable = true,
		require = 0
	)
	public void wilderWild$getTextureLocation(PigRenderState pigRenderState, CallbackInfoReturnable<ResourceLocation> info) {
		if (pigRenderState.customName != null) {
			if (ChatFormatting.stripFormatting(pigRenderState.customName.getString()).equalsIgnoreCase("a view from the top")) {
				info.setReturnValue(WILDERWILD$UPPY_BALLOO);
			}
		}
	}
}
