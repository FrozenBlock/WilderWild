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

package net.frozenblock.wilderwild.data.wilder_aquatic;

import java.util.function.Function;
import net.frozenblock.wilderwild.references.WWBlockItemIds;
import net.minecraft.data.tags.BlockItemTagsProvider;
import net.minecraft.tags.BlockItemTagId;
import net.minecraft.tags.BlockItemTags;

public final class WABlockItemTagsProvider extends BlockItemTagsProvider {

	WABlockItemTagsProvider(Function<BlockItemTagId, CombinedAppender> tagSupplier) {
		super(tagSupplier);
	}

	@Override
	protected void run() {
		// TODO: polished
		this.tag(BlockItemTags.STAIRS)
			//.add(WWBlockItemIds.POLISHED_PEARL_STAIRS)
			.add(WWBlockItemIds.CUT_POLISHED_PEARL_STAIRS);

		// TODO: polished
		this.tag(BlockItemTags.SLABS)
			//.add(WWBlockItemIds.POLISHED_PEARL_SLAB)
			.add(WWBlockItemIds.CUT_POLISHED_PEARL_SLAB);
	}
}
