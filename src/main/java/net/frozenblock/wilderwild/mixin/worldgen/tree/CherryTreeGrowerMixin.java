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
import net.frozenblock.wilderwild.world.feature.WilderTreeConfigured;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.CherryTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = CherryTreeGrower.class, priority = 69420)
public class CherryTreeGrowerMixin {

	@Inject(method = "getConfiguredFeature", at = @At("RETURN"), cancellable = true)
	public void wilderWild$getConfiguredFeature(RandomSource random, boolean bees, CallbackInfoReturnable<ResourceKey<? extends ConfiguredFeature<?, ?>>> info) {
		if (WorldgenConfig.get().treeGeneration) {
			if (random.nextFloat() < 0.15F) {
				info.setReturnValue(bees ? WilderTreeConfigured.TALL_CHERRY_BEES_025.getKey() : WilderTreeConfigured.TALL_CHERRY_TREE.getKey());
			} else {
				info.setReturnValue(bees ? WilderTreeConfigured.CHERRY_BEES_025.getKey() : WilderTreeConfigured.CHERRY_TREE.getKey());
			}
		}
	}

}
