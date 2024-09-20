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

package net.frozenblock.wilderwild.mod_compat;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.lib.mobcategory.api.FrozenMobCategories;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.entity.variant.FireflyColor;
import net.frozenblock.wilderwild.registry.WWEntityTypes;

public class TerralithModIntegration extends ModIntegration {
	public TerralithModIntegration() {
		super("terralith");
	}

	@Override
	public void init() {
		Firefly.FireflyBiomeColorRegistry.addBiomeColor(id("cave/frostfire_caves"), FireflyColor.BLUE);
		Firefly.FireflyBiomeColorRegistry.addBiomeColor(id("cave/frostfire_caves"), FireflyColor.LIGHT_BLUE);

		Firefly.FireflyBiomeColorRegistry.addBiomeColor(id("cave/thermal_caves"), FireflyColor.RED);
		Firefly.FireflyBiomeColorRegistry.addBiomeColor(id("cave/thermal_caves"), FireflyColor.ORANGE);

		BiomeModifications.addSpawn(BiomeSelectors.includeByKey(getBiomeKey("cave/underground_jungle")),
			FrozenMobCategories.getCategory(WWConstants.MOD_ID, "fireflies"), WWEntityTypes.FIREFLY, 12, 2, 4);
	}
}
