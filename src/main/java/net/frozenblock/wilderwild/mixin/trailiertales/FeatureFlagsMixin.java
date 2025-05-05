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

package net.frozenblock.wilderwild.mixin.trailiertales;

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
		if (FrozenBools.HAS_TRAILIERTALES) DEFAULT_FLAGS = DEFAULT_FLAGS.join(WWFeatureFlags.TRAILIER_TALES_COMPAT_FLAG_SET);
	}
}
