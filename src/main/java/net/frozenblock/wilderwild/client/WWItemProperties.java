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

package net.frozenblock.wilderwild.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.item.api.ItemBlockStateTagUtils;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

@Environment(EnvType.CLIENT)
public final class WWItemProperties {

	public static void init() {
		ItemProperties.register(WWItems.COPPER_HORN, WWConstants.vanillaId("tooting"), (itemStack, clientLevel, livingEntity, seed) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);

		ItemProperties.register(WWItems.ECHO_GLASS, WWConstants.vanillaId("damage"), (itemStack, clientLevel, livingEntity, seed) -> ((float) ItemBlockStateTagUtils.getProperty(itemStack, WWBlockStateProperties.DAMAGE, 0)) / 4F);

		ItemProperties.register(Items.BEE_NEST, WWConstants.vanillaId("honey"), (itemStack, clientLevel, livingEntity, seed) -> ((float) ItemBlockStateTagUtils.getProperty(itemStack, BlockStateProperties.LEVEL_HONEY, 0)) / 5F);
		ItemProperties.register(Items.BEEHIVE, WWConstants.vanillaId("honey"), (itemStack, clientLevel, livingEntity, seed) -> ((float) ItemBlockStateTagUtils.getProperty(itemStack, BlockStateProperties.LEVEL_HONEY, 0)) / 5F);
	}
}
