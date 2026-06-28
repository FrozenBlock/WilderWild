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

package net.frozenblock.wilderwild.registry;

import java.util.function.Function;
import net.frozenblock.lib.block.api.modification.BlockRegistryModificationEvents;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.minecraft.references.BlockItemIds;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class WWBlockModifications implements BlockRegistryModificationEvents.ModifyProperties, BlockRegistryModificationEvents.ReplaceFactory {

	@Override
	public BlockBehaviour.Properties modifyProperties(
		ResourceKey<Block> id,
		BlockBehaviour.Properties properties
	) {
		if (id == BlockItemIds.OCHRE_FROGLIGHT.block() || id == BlockItemIds.VERDANT_FROGLIGHT.block() || id == BlockItemIds.PEARLESCENT_FROGLIGHT.block()) {
			return properties.randomTicks();
		}
		return null;
	}

	@Override
	public Function<BlockBehaviour.Properties, Block> replaceFactory(
		ResourceKey<Block> id,
		BlockBehaviour.Properties properties,
		Function<BlockBehaviour.Properties, Block> factory
	) {
		if (id == BlockItemIds.REINFORCED_DEEPSLATE.block() && WWBlockConfig.NEW_REINFORCED_DEEPSLATE.get()) return RotatedPillarBlock::new;
		return null;
	}
}
