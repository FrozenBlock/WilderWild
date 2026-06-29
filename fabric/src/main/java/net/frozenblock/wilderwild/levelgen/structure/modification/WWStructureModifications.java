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

package net.frozenblock.wilderwild.levelgen.structure.modification;

import net.frozenblock.lib.levelgen.structure.api.RandomPoolAliasApi;
import net.frozenblock.lib.levelgen.structure.api.StructureSetApi;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.data.worldgen.structure.WWStructures;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;

public final class WWStructureModifications {

	public static void init() {
		StructureSetApi.ADD_ADDITIONAL_STRUCTURES.register((structures, structureSet, context) -> {
			if (!structureSet.is(BuiltinStructureSets.ABANDONED_CAMP)) return;
			structures.get(WWStructures.ABANDONED_CAMP_MAPLE_FOREST).ifPresent(abandonedCampMapleForest ->
				context.frozenLib$addOrModifyStructureSelectionEntry(abandonedCampMapleForest, 1)
			);
		});

		if (WWEntityConfig.SCORCHED_IN_TRIAL_CHAMBERS.get()) {
			RandomPoolAliasApi.addTarget(
				WWConstants.vanillaId("trial_chambers/spawner/contents/small_melee"),
				WWConstants.id("trial_chambers/spawner/small_melee/scorched"),
				1
			);
		}
	}
}
