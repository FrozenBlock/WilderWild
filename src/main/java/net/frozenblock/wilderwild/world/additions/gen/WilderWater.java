/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.world.additions.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.generation.WilderSharedWorldgen;

public final class WilderWater {
	private WilderWater() {
		throw new UnsupportedOperationException("WilderWater contains only static declarations.");
	}

	public static void stirWater() {
		WilderSharedConstants.logWild("Overriding Water Colors for Wilder Wild", true);
		BiomeModifications.create(WilderSharedConstants.id("modify_hot_water")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeRegistryEntry().is(WilderBiomeTags.HOT_WATER),
			(selectionContext, modificationContext) -> {
				if (WilderSharedConstants.config().hotWater()) {
					modificationContext.getEffects().setWaterColor(WilderSharedWorldgen.WarmRiver.NEW_WATER_COLOR);
					modificationContext.getEffects().setWaterFogColor(WilderSharedWorldgen.WarmRiver.NEW_WATER_FOG_COLOR);
				}
			});
		BiomeModifications.create(WilderSharedConstants.id("modify_lukewarm_water")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeRegistryEntry().is(WilderBiomeTags.LUKEWARM_WATER),
			(selectionContext, modificationContext) -> {
				if (WilderSharedConstants.config().lukewarmWater()) {
					modificationContext.getEffects().setWaterColor(WilderSharedWorldgen.WarmRiver.NEW_WATER_COLOR);
					modificationContext.getEffects().setWaterFogColor(WilderSharedWorldgen.WarmRiver.NEW_WATER_FOG_COLOR);
				}
			});
		BiomeModifications.create(WilderSharedConstants.id("modify_snowy_water")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeRegistryEntry().is(WilderBiomeTags.SNOWY_WATER),
			(selectionContext, modificationContext) -> {
				if (WilderSharedConstants.config().snowyWater()) {
					modificationContext.getEffects().setWaterColor(WilderSharedWorldgen.OldGrowthSnowySpruceTaiga.WATER_COLOR);
					modificationContext.getEffects().setWaterFogColor(WilderSharedWorldgen.OldGrowthSnowySpruceTaiga.WATER_FOG_COLOR);
				}
			});
		BiomeModifications.create(WilderSharedConstants.id("modify_frozen_water")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeRegistryEntry().is(WilderBiomeTags.FROZEN_WATER),
			(selectionContext, modificationContext) -> {
				if (WilderSharedConstants.config().frozenWater()) {
					modificationContext.getEffects().setWaterColor(WilderSharedWorldgen.OldGrowthSnowySpruceTaiga.WATER_COLOR);
					modificationContext.getEffects().setWaterFogColor(WilderSharedWorldgen.OldGrowthSnowySpruceTaiga.WATER_FOG_COLOR);
				}
			});
	}
}
