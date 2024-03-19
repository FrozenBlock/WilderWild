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

package net.frozenblock.wilderwild.mixin.worldgen.tree;

import net.frozenblock.wilderwild.config.WorldgenConfig;
import net.frozenblock.wilderwild.world.features.feature.WilderTreeConfigured;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.DarkOakTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = DarkOakTreeGrower.class, priority = 69420)
public class DarkOakTreeGrowerMixin {

	@Inject(method = "getConfiguredMegaFeature", at = @At("RETURN"), cancellable = true)
	public void getConfiguredMegaFeature(RandomSource randomSource, CallbackInfoReturnable<ResourceKey<ConfiguredFeature<?, ?>>> info) {
		if (WorldgenConfig.get().treeGeneration) {
			if (randomSource.nextFloat() < 0.2F) {
				info.setReturnValue(WilderTreeConfigured.TALL_DARK_OAK.getKey());
			} else if (randomSource.nextFloat() < 0.2F) {
				info.setReturnValue(WilderTreeConfigured.FANCY_TALL_DARK_OAK.getKey());
			}
		}
	}

}
