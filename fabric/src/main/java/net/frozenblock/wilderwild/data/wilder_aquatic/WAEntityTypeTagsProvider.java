/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.wilderwild.data.wilder_aquatic;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.frozenblock.wilderwild.references.WWEntityTypeIds;
import net.frozenblock.wilderwild.tag.WWEntityTypeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;

public final class WAEntityTypeTagsProvider extends FabricTagsProvider.EntityTypeTagsProvider {

	public WAEntityTypeTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void addTags(HolderLookup.Provider arg) {
		this.builder(WWEntityTypeTags.GEOTHERMAL_VENT_PUSHES_FURTHER)
			.add(WWEntityTypeIds.HARPOON);

		this.builder(EntityTypeTags.IMMUNE_TO_FREEZING)
			.add(WWEntityTypeIds.SUNKEN);

		this.builder(EntityTypeTags.SKELETONS)
			.add(WWEntityTypeIds.SUNKEN);

		this.builder(EntityTypeTags.BURN_IN_DAYLIGHT)
			.add(WWEntityTypeIds.SUNKEN);

		this.builder(EntityTypeTags.AXOLOTL_ALWAYS_HOSTILES)
			.add(WWEntityTypeIds.SUNKEN);

		this.builder(EntityTypeTags.IMPACT_PROJECTILES)
			.add(WWEntityTypeIds.HARPOON);
	}

	private static ResourceKey<EntityType<?>> createKey(String namespace, String path) {
		return ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(namespace, path));
	}
}
