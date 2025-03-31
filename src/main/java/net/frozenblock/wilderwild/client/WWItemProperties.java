/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.item.api.ItemBlockStateTagUtils;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.entity.variant.firefly.FireflyColor;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

@Environment(EnvType.CLIENT)
public final class WWItemProperties {

	public static void init() {
		ItemProperties.register(WWItems.COPPER_HORN, WWConstants.vanillaId("tooting"), (itemStack, clientLevel, livingEntity, seed) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F);

		ItemProperties.register(WWItems.SCORCHED_SAND, WWConstants.vanillaId("cracked"), (itemStack, clientLevel, livingEntity, seed) -> ItemBlockStateTagUtils.getBoolProperty(itemStack, WWBlockStateProperties.CRACKED, false) ? 1F : 0F);
		ItemProperties.register(WWItems.SCORCHED_RED_SAND, WWConstants.vanillaId("cracked"), (itemStack, clientLevel, livingEntity, seed) -> ItemBlockStateTagUtils.getBoolProperty(itemStack, WWBlockStateProperties.CRACKED, false) ? 1F : 0F);
		ItemProperties.register(WWItems.ECHO_GLASS, WWConstants.vanillaId("damage"), (itemStack, clientLevel, livingEntity, seed) -> ((float) ItemBlockStateTagUtils.getProperty(itemStack, WWBlockStateProperties.DAMAGE, 0)) / 4F);

		ItemProperties.register(Items.BEE_NEST, WWConstants.vanillaId("honey"), (itemStack, clientLevel, livingEntity, seed) -> ((float) ItemBlockStateTagUtils.getProperty(itemStack, BlockStateProperties.LEVEL_HONEY, 0)) / 5F);
		ItemProperties.register(Items.BEEHIVE, WWConstants.vanillaId("honey"), (itemStack, clientLevel, livingEntity, seed) -> ((float) ItemBlockStateTagUtils.getProperty(itemStack, BlockStateProperties.LEVEL_HONEY, 0)) / 5F);
		ItemProperties.register(
			WWItems.FIREFLY_BOTTLE,
			WWConstants.vanillaId("color"),
			(itemStack, clientLevel, livingEntity, seed) -> {
				CustomData bottleData = itemStack.get(WWDataComponents.BOTTLE_ENTITY_DATA);
				if (clientLevel != null && bottleData != null && !bottleData.isEmpty()) {
					CompoundTag tag = bottleData.copyTag();
					return clientLevel.registryAccess().lookupOrThrow(WilderWildRegistries.FIREFLY_COLOR)
						.getOptional(ResourceLocation.parse(tag.getString("FireflyBottleVariantTag")))
						.map(FireflyColor::modelPredicate).orElse(0F);
				}
				return 0F;
			}
		);

	}
}
