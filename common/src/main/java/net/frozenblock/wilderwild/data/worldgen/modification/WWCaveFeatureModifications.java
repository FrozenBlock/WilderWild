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
import static net.frozenblock.lib.levelgen.biome.api.modifications.data.FeatureModification.registerVegetalAddition;
import net.frozenblock.wilderwild.data.worldgen.placement.WWCavePlacements;
import net.frozenblock.wilderwild.tag.WWCaveFeatureBiomeTags;
import net.minecraft.data.worldgen.BootstrapContext;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@UtilityClass
public final class WWCaveFeatureModifications {

	public static void bootstrap(BootstrapContext<BiomeModification> context) {
		// ICE CAVES
		registerVegetalAddition(
			context,
			WWCaveFeatureBiomeTags.CAVE_BRITTLE_ICE_PATCH,
			WWCavePlacements.PATCH_BRITTLE_ICE,
			WWCavePlacements.PATCH_BRITTLE_ICE_CEILING
		);
	}
}
