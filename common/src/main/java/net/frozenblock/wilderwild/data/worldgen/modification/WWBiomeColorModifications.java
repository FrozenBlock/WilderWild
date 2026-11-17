/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.wilderwild.data.worldgen.modification;

import lombok.experimental.UtilityClass;
import net.frozenblock.lib.levelgen.biome.api.modifications.data.BiomeModification;
import net.frozenblock.lib.levelgen.biome.api.modifications.data.SpecialEffectsModification;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.data.worldgen.WWSharedWorldgen;
import net.frozenblock.wilderwild.registry.WWBiomeSelectors;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.util.ARGB;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@UtilityClass
public final class WWBiomeColorModifications {

	public static void bootstrap(BootstrapContext<BiomeModification> context) {
		SpecialEffectsModification.registerFoliageColor(
			context,
			WWConstants.id("color/badlands_foliage"),
			WWBiomeTags.HAS_BADLANDS_FOLIAGE_COLOR,
			WWBiomeSelectors.FOLIAGE_COLOR_BADLANDS,
			11445290
		);
		SpecialEffectsModification.registerGrassColor(
			context,
			WWConstants.id("color/dappled_forest_grass"),
			WWBiomeTags.HAS_DAPPLED_FOREST_GRASS_COLOR,
			WWBiomeSelectors.GRASS_COLOR_DAPPLED_FOREST,
			ARGB.color(0, 229, 125, 47)
		);
		SpecialEffectsModification.registerWaterAndWaterFogColor(
			context,
			WWConstants.id("color/hot_water"),
			WWBiomeTags.HOT_WATER,
			WWBiomeSelectors.WATER_COLOR_HOT,
			WWSharedWorldgen.WARM_WATER_COLOR,
			WWSharedWorldgen.WARM_WATER_FOG_COLOR
		);
		SpecialEffectsModification.registerWaterAndWaterFogColor(
			context,
			WWConstants.id("color/lukewarm_water"),
			WWBiomeTags.LUKEWARM_WATER,
			WWBiomeSelectors.WATER_COLOR_LUKEWARM,
			WWSharedWorldgen.WARM_WATER_COLOR,
			WWSharedWorldgen.WARM_WATER_FOG_COLOR
		);
		SpecialEffectsModification.registerWaterAndWaterFogColor(
			context,
			WWConstants.id("color/snowy_water"),
			WWBiomeTags.SNOWY_WATER,
			WWBiomeSelectors.WATER_COLOR_SNOWY,
			WWSharedWorldgen.COLD_WATER_COLOR,
			WWSharedWorldgen.COLD_WATER_FOG_COLOR
		);
		SpecialEffectsModification.registerWaterAndWaterFogColor(
			context,
			WWConstants.id("color/frozen_water"),
			WWBiomeTags.FROZEN_WATER,
			WWBiomeSelectors.WATER_COLOR_FROZEN,
			WWSharedWorldgen.COLD_WATER_COLOR,
			WWSharedWorldgen.COLD_WATER_FOG_COLOR
		);
	}
}
