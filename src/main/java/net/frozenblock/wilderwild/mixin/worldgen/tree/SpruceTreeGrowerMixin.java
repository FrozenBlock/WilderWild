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

import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.worldgen.feature.configured.WWTreeConfigured;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.SpruceTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SpruceTreeGrower.class, priority = 69420)
public class SpruceTreeGrowerMixin {

	@Inject(method = "getConfiguredFeature", at = @At("RETURN"), cancellable = true)
	public void getConfiguredFeature(RandomSource random, boolean bees, CallbackInfoReturnable<ResourceKey<ConfiguredFeature<?, ?>>> info) {
		if (WWWorldgenConfig.get().treeGeneration) {
			info.setReturnValue(random.nextFloat() < 0.1F ? WWTreeConfigured.SPRUCE_SHORT.getKey() : WWTreeConfigured.SPRUCE.getKey());
		}
	}

	@Inject(method = "getConfiguredMegaFeature", at = @At("RETURN"), cancellable = true)
	public void wilderWild$getConfiguredMegaFeature(RandomSource random, CallbackInfoReturnable<ResourceKey<? extends ConfiguredFeature<?, ?>>> info) {
		if (WWWorldgenConfig.get().treeGeneration) {
			if (random.nextFloat() < 0.25F) {
				info.setReturnValue(WWTreeConfigured.SHORT_MEGA_SPRUCE.getKey());
			}
		}
	}

}
