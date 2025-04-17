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
 * You should have received a copy of the FrozenBlock Modding oasis License
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

		BiomeModifications.create(WWConstants.id("modify_hot_water")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HOT_WATER),
			(selectionContext, modificationContext) -> {
				if (WWAmbienceAndMiscConfig.get().waterColors.modifyHotWater) {
					BiomeModificationContext.EffectsContext context = modificationContext.getEffects();
					context.setWaterColor(WWSharedWorldgen.WARM_WATER_COLOR);
					context.setWaterFogColor(WWSharedWorldgen.WARM_WATER_FOG_COLOR);
				}
			});

		BiomeModifications.create(WWConstants.id("modify_lukewarm_water")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.LUKEWARM_WATER),
			(selectionContext, modificationContext) -> {
				if (WWAmbienceAndMiscConfig.get().waterColors.modifyLukewarmWater) {
					BiomeModificationContext.EffectsContext context = modificationContext.getEffects();
					context.setWaterColor(WWSharedWorldgen.WARM_WATER_COLOR);
					context.setWaterFogColor(WWSharedWorldgen.WARM_WATER_FOG_COLOR);
				}
			});

		BiomeModifications.create(WWConstants.id("modify_snowy_water")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.SNOWY_WATER),
			(selectionContext, modificationContext) -> {
				if (WWAmbienceAndMiscConfig.get().waterColors.modifySnowyWater) {
					BiomeModificationContext.EffectsContext context = modificationContext.getEffects();
					context.setWaterColor(WWSharedWorldgen.COLD_WATER_COLOR);
					context.setWaterFogColor(WWSharedWorldgen.COLD_WATER_FOG_COLOR);
				}
			});

		BiomeModifications.create(WWConstants.id("modify_frozen_water")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.FROZEN_WATER),
			(selectionContext, modificationContext) -> {
				if (WWAmbienceAndMiscConfig.get().waterColors.modifyFrozenWater) {
					BiomeModificationContext.EffectsContext context = modificationContext.getEffects();
					context.setWaterColor(WWSharedWorldgen.COLD_WATER_COLOR);
					context.setWaterFogColor(WWSharedWorldgen.COLD_WATER_FOG_COLOR);
				}
			});
	}
}
