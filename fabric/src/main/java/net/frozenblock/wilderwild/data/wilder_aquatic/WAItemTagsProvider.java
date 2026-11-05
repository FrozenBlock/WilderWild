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
import net.frozenblock.wilderwild.references.WWBlockItemIds;
import net.frozenblock.wilderwild.references.WWItemIds;
import net.frozenblock.wilderwild.tag.WWBlockItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class WAItemTagsProvider extends FabricTagsProvider.ItemTagsProvider {

	public WAItemTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	private TagKey<Item> getTag(String id) {
		return TagKey.create(this.registryKey, Identifier.parse(id));
	}

	private ResourceKey<Item> getKey(String namespace, String path) {
		return ResourceKey.create(this.registryKey, Identifier.fromNamespaceAndPath(namespace, path));
	}

	@Override
	protected void addTags(HolderLookup.Provider arg) {
		// TRIMS
		this.builder(ItemTags.TRIM_MATERIALS)
			.add(WWItemIds.PEARL);

		// SULFUR CUBE
		this.builder(ItemTags.SULFUR_CUBE_ARCHETYPE_BOUNCY)
			.addOptionalTag(WWBlockItemTags.HOLLOWED_LOGS.item());

		this.builder(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_BOUNCY)
			.add(
				WWBlockItemIds.PEARL_BLOCK
			);
	}
}
