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

package net.frozenblock.wilderwild.worldgen.modification;

import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.frozenblock.wilderwild.worldgen.WWSharedWorldgen;

public final class WWWaterColors {
	private WWWaterColors() {
		throw new UnsupportedOperationException("WWWaterColors contains only static declarations.");
	}

	public static void stirWater() {
		WWConstants.logWithModId("Overriding Water Colors for", true);

		BiomeModifications.create(WWConstants.id("modify_water")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.all(),
			(biomeSelectionContext, modificationContext) -> {
				WWAmbienceAndMiscConfig.WaterColorConfig waterColorConfig = WWAmbienceAndMiscConfig.get().waterColors;

				if (waterColorConfig.modifyHotWater && biomeSelectionContext.hasTag(WWBiomeTags.HOT_WATER)) {
					BiomeModificationContext.EffectsContext context = modificationContext.getEffects();
					context.setWaterColor(WWSharedWorldgen.WARM_WATER_COLOR);
					context.setWaterFogColor(WWSharedWorldgen.WARM_WATER_FOG_COLOR);
				}

				if (waterColorConfig.modifyLukewarmWater && biomeSelectionContext.hasTag(WWBiomeTags.LUKEWARM_WATER)) {
					BiomeModificationContext.EffectsContext context = modificationContext.getEffects();
					context.setWaterColor(WWSharedWorldgen.WARM_WATER_COLOR);
					context.setWaterFogColor(WWSharedWorldgen.WARM_WATER_FOG_COLOR);
				}

				if (waterColorConfig.modifySnowyWater && biomeSelectionContext.hasTag(WWBiomeTags.SNOWY_WATER)) {
					BiomeModificationContext.EffectsContext context = modificationContext.getEffects();
					context.setWaterColor(WWSharedWorldgen.COLD_WATER_COLOR);
					context.setWaterFogColor(WWSharedWorldgen.COLD_WATER_FOG_COLOR);
				}

				if (waterColorConfig.modifyFrozenWater && biomeSelectionContext.hasTag(WWBiomeTags.FROZEN_WATER)) {
					BiomeModificationContext.EffectsContext context = modificationContext.getEffects();
					context.setWaterColor(WWSharedWorldgen.COLD_WATER_COLOR);
					context.setWaterFogColor(WWSharedWorldgen.COLD_WATER_FOG_COLOR);
				}
			});
	}
}
