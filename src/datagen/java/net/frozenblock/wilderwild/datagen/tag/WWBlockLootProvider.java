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

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.frozenblock.wilderwild.block.DisplayLanternBlock;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyBlockState;
import net.minecraft.world.level.storage.loot.functions.CopyCustomDataFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

public final class WWBlockLootProvider extends FabricBlockLootTableProvider {

	public WWBlockLootProvider(@NotNull FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
		super(dataOutput, registryLookup);
	}

	@Override
	public void generate() {
		this.dropSelf(RegisterBlocks.BAOBAB_HANGING_SIGN);
		this.dropSelf(RegisterBlocks.CYPRESS_HANGING_SIGN); //TODO: Fix removal upon datagen
		this.dropSelf(RegisterBlocks.PALM_HANGING_SIGN);

		this.dropPottedContents(RegisterBlocks.POTTED_GRASS); //TODO: Fix removal upon datagen

		this.add(RegisterBlocks.DISPLAY_LANTERN, //TODO: Fix removal upon datagen
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1F))
						.add(
							LootItem.lootTableItem(RegisterBlocks.DISPLAY_LANTERN).when(BlockLootSubProvider.HAS_SILK_TOUCH)
								.apply(CopyCustomDataFunction.copyData(LootContext.EntityTarget.THIS).copy("FireFlies", "BlockEntityTag.Fireflies"))
								.apply(CopyBlockState.copyState(RegisterBlocks.DISPLAY_LANTERN).copy(DisplayLanternBlock.DISPLAY_LIGHT))
								.otherwise(LootItem.lootTableItem(RegisterBlocks.DISPLAY_LANTERN))
						)
				)
		);
	}

}
