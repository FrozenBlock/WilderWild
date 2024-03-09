/*
 * Copyright 2023-2024 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.datagen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

final class WWExperimentBlockTagProvider extends FabricTagProvider.BlockTagProvider {

	WWExperimentBlockTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	private static ResourceKey<Block> key(@NotNull Block block) {
		return BuiltInRegistries.BLOCK.getResourceKey(block).orElseThrow();
	}

	@Override
	protected void addTags(@NotNull HolderLookup.Provider arg) {
		this.tag(BlockTags.CEILING_HANGING_SIGNS)
			.add(key(RegisterBlocks.BAOBAB_HANGING_SIGN))
			.add(key(RegisterBlocks.CYPRESS_HANGING_SIGN));

		this.tag(BlockTags.WALL_HANGING_SIGNS)
			.add(key(RegisterBlocks.BAOBAB_WALL_HANGING_SIGN))
			.add(key(RegisterBlocks.CYPRESS_WALL_HANGING_SIGN));
	}
}
