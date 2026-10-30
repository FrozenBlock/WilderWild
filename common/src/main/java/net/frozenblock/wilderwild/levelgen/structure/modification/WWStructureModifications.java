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

import java.util.ArrayList;
import java.util.List;
import net.frozenblock.lib.levelgen.structure.api.StructureSetEvents;
import net.frozenblock.lib.levelgen.structure.api.pools.RandomGroupPoolAliasEvents;
import net.frozenblock.lib.levelgen.structure.api.pools.RandomPoolAliasEvents;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.data.worldgen.structure.WWStructures;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding;

public final class WWStructureModifications {

	public static void init() {
		StructureSetEvents.ADD_OR_REMOVE_STRUCTURES.register((structures, structureSet, context) -> {
			if (!structureSet.is(BuiltinStructureSets.ABANDONED_CAMP) || !WWWorldgenConfig.MAPLE_FOREST_GENERATION.get()) return;
			structures.get(WWStructures.ABANDONED_CAMP_MAPLE_FOREST).ifPresent(abandonedCampMapleForest ->
				context.frozenLib$addOrModifyStructureSelectionEntry(abandonedCampMapleForest, 1)
			);
		});

		StructureSetEvents.ADD_OR_REMOVE_STRUCTURES.register((structures, structureSet, context) -> {
			if (!structureSet.is(BuiltinStructureSets.ABANDONED_CAMP) || !WWWorldgenConfig.DAPPLED_FOREST_REMOVAL.get()) return;
			structures.get(BuiltinStructures.ABANDONED_CAMP_DAPPLED_FOREST).ifPresent(context::frozenLib$removeStructureSelectionEntry);
		});

		RandomPoolAliasEvents.MODIFY.register((alias, context) -> {
			if (!WWEntityConfig.SCORCHED_IN_TRIAL_CHAMBERS.get() || !alias.identifier().equals(WWConstants.vanillaId("trial_chambers/spawner/contents/small_melee"))) return;

			final Identifier scorchedTarget = WWConstants.id("trial_chambers/spawner/small_melee/scorched");
			if (!context.containsTarget(scorchedTarget)) context.add(scorchedTarget, 1);
		});

		RandomGroupPoolAliasEvents.MODIFY.register(context -> {
			if (!WWEntityConfig.SUNKEN_IN_TRIAL_CHAMBERS.get()) return;

			final List<PoolAliasBinding> sunkenGroup = new ArrayList<>();

			final Identifier rangedAlias = WWConstants.vanillaId("trial_chambers/spawner/contents/ranged");
			if (context.containsSubAlias(rangedAlias)) {
				final Identifier sunkenTarget = WWConstants.id("trial_chambers/spawner/ranged/sunken");
				if (!context.containsSubTarget(sunkenTarget)) sunkenGroup.add(PoolAliasBinding.direct(Pools.createKey(rangedAlias), Pools.createKey(sunkenTarget)));
			}

			final Identifier slowRangedAlias = WWConstants.vanillaId("trial_chambers/spawner/contents/slow_ranged");
			if (context.containsSubAlias(slowRangedAlias)) {
				final Identifier sunkenTarget = WWConstants.id("trial_chambers/spawner/slow_ranged/sunken");
				if (!context.containsSubTarget(sunkenTarget)) sunkenGroup.add(PoolAliasBinding.direct(Pools.createKey(slowRangedAlias), Pools.createKey(sunkenTarget)));
			}

			if (!sunkenGroup.isEmpty()) context.add(sunkenGroup, 1);
		});
	}

	private WWStructureModifications() {}
}
