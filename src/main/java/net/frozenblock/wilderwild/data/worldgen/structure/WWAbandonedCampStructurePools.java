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

package net.frozenblock.wilderwild.data.worldgen.structure;

import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.AbandonedCampStructurePools;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class WWAbandonedCampStructurePools {
	public static final AbandonedCampStructurePools.AbandonedCampStructure MAPLE_FOREST = new AbandonedCampStructurePools.AbandonedCampStructure(
		Pools.parseKey(WWConstants.string("abandoned_camp/camp/maple_forest")),
		Pools.parseKey(WWConstants.string("abandoned_camp/tent/maple_forest")),
		"maple_forest"
	);

	public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
		final HolderGetter<StructureTemplatePool> pools = context.lookup(Registries.TEMPLATE_POOL);
		final Holder<StructureTemplatePool> empty = pools.getOrThrow(Pools.EMPTY);
		bootstrapCampsitePools(context, empty);
	}

	private static void bootstrapCampsitePools(BootstrapContext<StructureTemplatePool> context, Holder<StructureTemplatePool> empty) {
		AbandonedCampStructurePools.registerTentPool(context, empty, MAPLE_FOREST);
		AbandonedCampStructurePools.registerCampsitePool(context, empty, MAPLE_FOREST);
	}
}
