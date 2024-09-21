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

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.User;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.resources.SplashManager;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(SplashManager.class)
public class ScottTheWozMixin {

	@Shadow
	@Final
	private static RandomSource RANDOM;

	@Shadow
	@Final
	private List<String> splashes;

	@Shadow
	@Final
	private User user;

	@Inject(method = "getSplash", at = @At("TAIL"), cancellable = true, require = 0)
	public void getSplash(CallbackInfoReturnable<SplashRenderer> info) {
		String lowerName = this.user.getName().toLowerCase();
		if (this.user != null && (RANDOM.nextInt(this.splashes.size()) == 42 ||
			((lowerName.contains("scot") || lowerName.contains("skot") || lowerName.contains("sct") || lowerName.contains("skt")) && RANDOM.nextFloat() < 0.05F))) {
			info.setReturnValue(new SplashRenderer("Hey all, " + this.user.getName() + " here."));
		}
	}
}
