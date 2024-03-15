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

package net.frozenblock.wilderwild.world.modification;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.frozenblock.wilderwild.config.AmbienceAndMiscConfig;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.WilderSharedWorldgen;

public final class WilderWaterColors {
	private WilderWaterColors() {
		throw new UnsupportedOperationException("WilderWater contains only static declarations.");
	}

	public static void stirWater() {
		WilderSharedConstants.logWithModId("Overriding Water Colors for", true);

		BiomeModifications.create(WilderSharedConstants.id("modify_hot_water")).add(
			ModificationPhase.POST_PROCESSING, (context) -> context.getBiomeRegistryEntry().is(WilderBiomeTags.HOT_WATER),
			(selectionContext, modificationContext) -> {
				if (AmbienceAndMiscConfig.get().waterColors.modifyHotWater) {
					modificationContext.getEffects().setWaterColor(WilderSharedWorldgen.WARM_WATER_COLOR);
					modificationContext.getEffects().setWaterFogColor(WilderSharedWorldgen.WARM_WATER_FOG_COLOR);
				}
			});

		BiomeModifications.create(WilderSharedConstants.id("modify_lukewarm_water")).add(
			ModificationPhase.POST_PROCESSING, (context) -> context.getBiomeRegistryEntry().is(WilderBiomeTags.LUKEWARM_WATER),
			(selectionContext, modificationContext) -> {
				if (AmbienceAndMiscConfig.get().waterColors.modifyLukewarmWater) {
					modificationContext.getEffects().setWaterColor(WilderSharedWorldgen.WARM_WATER_COLOR);
					modificationContext.getEffects().setWaterFogColor(WilderSharedWorldgen.WARM_WATER_FOG_COLOR);
				}
			});

		BiomeModifications.create(WilderSharedConstants.id("modify_snowy_water")).add(
			ModificationPhase.POST_PROCESSING, (context) -> context.getBiomeRegistryEntry().is(WilderBiomeTags.SNOWY_WATER),
			(selectionContext, modificationContext) -> {
				if (AmbienceAndMiscConfig.get().waterColors.modifySnowyWater) {
					modificationContext.getEffects().setWaterColor(WilderSharedWorldgen.COLD_WATER_COLOR);
					modificationContext.getEffects().setWaterFogColor(WilderSharedWorldgen.COLD_WATER_FOG_COLOR);
				}
			});

		BiomeModifications.create(WilderSharedConstants.id("modify_frozen_water")).add(
			ModificationPhase.POST_PROCESSING, (context) -> context.getBiomeRegistryEntry().is(WilderBiomeTags.FROZEN_WATER),
			(selectionContext, modificationContext) -> {
				if (AmbienceAndMiscConfig.get().waterColors.modifyFrozenWater) {
					modificationContext.getEffects().setWaterColor(WilderSharedWorldgen.COLD_WATER_COLOR);
					modificationContext.getEffects().setWaterFogColor(WilderSharedWorldgen.COLD_WATER_FOG_COLOR);
				}
			});
	}
}
