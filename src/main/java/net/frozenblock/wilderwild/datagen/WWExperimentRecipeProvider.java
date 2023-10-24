/*
 * Copyright 2023 FrozenBlock
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

package net.frozenblock.wilderwild.datagen;

import net.frozenblock.wilderwild.misc.WilderFeatureFlags;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import org.jetbrains.annotations.NotNull;

final class WWExperimentRecipeProvider extends RecipeProvider {

	WWExperimentRecipeProvider(@NotNull PackOutput packOutput) {
		super(packOutput);
	}

	@Override
	public void buildRecipes(final @NotNull RecipeOutput output) {
		generateForEnabledBlockFamilies(output, FeatureFlagSet.of(WilderFeatureFlags.UPDATE_1_20_ADDITIONS));
		hangingSign(output, RegisterItems.BAOBAB_HANGING_SIGN, RegisterBlocks.STRIPPED_BAOBAB_LOG);
		hangingSign(output, RegisterItems.CYPRESS_HANGING_SIGN, RegisterBlocks.STRIPPED_CYPRESS_LOG);
	}
}
