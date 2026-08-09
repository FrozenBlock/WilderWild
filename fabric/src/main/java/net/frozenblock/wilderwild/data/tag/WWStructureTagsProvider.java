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

package net.frozenblock.wilderwild.data.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.frozenblock.wilderwild.data.worldgen.structure.WWStructures;
import net.frozenblock.wilderwild.tag.WWStructureTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.level.levelgen.structure.Structure;

public final class WWStructureTagsProvider extends FabricTagsProvider<Structure> {

	public WWStructureTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, Registries.STRUCTURE, registries);
	}

	@Override
	public void addTags(HolderLookup.Provider arg) {
		this.builder(StructureTags.ABANDONED_CAMP)
			.add(WWStructures.ABANDONED_CAMP_MAPLE_FOREST);

		this.builder(WWStructureTags.ON_ABANDONED_CAMP_MAPLE_FOREST_MAPS)
			.add(WWStructures.ABANDONED_CAMP_MAPLE_FOREST);
	}
}
