/*
 * Copyright 2025 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.feature_flag;

import net.frozenblock.lib.FrozenBools;
import net.frozenblock.wilderwild.WWFeatureFlags;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = FeatureFlags.class, priority = 2001)
public class FeatureFlagsMixin {

	@Final
	@Shadow
	@Mutable
	public static FeatureFlagSet DEFAULT_FLAGS;

	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void wilderWild$modifyDefaultSet(CallbackInfo info) {
		if (FrozenBools.hasMod("trailiertales")) {
			DEFAULT_FLAGS = DEFAULT_FLAGS.join(WWFeatureFlags.TRAILIER_TALES_COMPAT_FLAG_SET);
		}
	}
}
