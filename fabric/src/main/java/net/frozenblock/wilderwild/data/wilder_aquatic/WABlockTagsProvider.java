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
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.BlockItemTagsProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class WABlockTagsProvider extends FabricTagsProvider.BlockTagsProvider {

	public WABlockTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		new WABlockItemTagsProvider(tagId -> BlockItemTagsProvider.wrapForBlocks(this.tag(tagId.block()))).run();
		this.generateMinecraft(registries);
	}

	private TagKey<Block> getTag(String id) {
		return TagKey.create(this.registryKey, Identifier.parse(id));
	}

	private ResourceKey<Block> getKey(String namespace, String path) {
		return ResourceKey.create(this.registryKey, Identifier.fromNamespaceAndPath(namespace, path));
	}

	private void generateMinecraft(HolderLookup.Provider registries) {
		this.builder(BlockTags.MINEABLE_WITH_PICKAXE)
			.add(WWBlockItemIds.PEARL_BLOCK)
			// TODO: polished
			/*
			.add(
				WWBlockItemIds.POLISHED_PEARL_BLOCK,
				WWBlockItemIds.POLISHED_PEARL_STAIRS,
				WWBlockItemIds.POLISHED_PEARL_SLAB
			)
			 */
			.add(
				WWBlockItemIds.CUT_POLISHED_PEARL,
				WWBlockItemIds.CUT_POLISHED_PEARL_STAIRS,
				WWBlockItemIds.CUT_POLISHED_PEARL_SLAB
			);

		this.builder(BlockTags.NEEDS_STONE_TOOL)
			.add(WWBlockItemIds.PEARL_BLOCK)
			// TODO: polished
			/*
			.add(
				WWBlockItemIds.POLISHED_PEARL_BLOCK,
				WWBlockItemIds.POLISHED_PEARL_STAIRS,
				WWBlockItemIds.POLISHED_PEARL_SLAB
			)
			 */
			.add(
				WWBlockItemIds.CUT_POLISHED_PEARL,
				WWBlockItemIds.CUT_POLISHED_PEARL_STAIRS,
				WWBlockItemIds.CUT_POLISHED_PEARL_SLAB
			);

		this.builder(BlockTags.BLOCKS_MOTION_NO_LEAVES)
			.add(WWBlockItemIds.PEARL_BLOCK, WWBlockItemIds.CUT_POLISHED_PEARL);
			// TODO: polished
			//add(WWBlockItemIds.PEARL_BLOCK, WWBlockItemIds.POLISHED_PEARL_BLOCK,, WWBlockItemIds.CUT_POLISHED_PEARL);
	}
}
