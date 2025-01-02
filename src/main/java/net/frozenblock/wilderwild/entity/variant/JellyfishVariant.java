/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.entity.variant;

import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.frozenblock.wilderwild.tag.WWItemTags;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public record JellyfishVariant(@NotNull ResourceLocation key, @NotNull ResourceLocation texture, boolean pearlescent, @NotNull TagKey<Item> reproductionFood) {
	public static final JellyfishVariant BLUE = register(WWConstants.id("blue"), WWConstants.id("textures/entity/jellyfish/blue.png"), false, WWItemTags.JELLYFISH_FOOD);
	public static final JellyfishVariant LIME = register(WWConstants.id("lime"), WWConstants.id("textures/entity/jellyfish/lime.png"), false, WWItemTags.JELLYFISH_FOOD);
	public static final JellyfishVariant PINK = register(WWConstants.id("pink"), WWConstants.id("textures/entity/jellyfish/pink.png"), false, WWItemTags.JELLYFISH_FOOD);
	public static final JellyfishVariant RED = register(WWConstants.id("red"), WWConstants.id("textures/entity/jellyfish/red.png"), false, WWItemTags.JELLYFISH_FOOD);
	public static final JellyfishVariant YELLOW = register(WWConstants.id("yellow"), WWConstants.id("textures/entity/jellyfish/yellow.png"), false, WWItemTags.JELLYFISH_FOOD);

	public static final JellyfishVariant PEARLESCENT_BLUE = register(WWConstants.id("pearlescent_blue"), WWConstants.id("textures/entity/jellyfish/pearlescent_blue.png"), true, WWItemTags.PEARLESCENT_JELLYFISH_FOOD);
	public static final JellyfishVariant PEARLESCENT_PURPLE = register(WWConstants.id("pearlescent_purple"), WWConstants.id("textures/entity/jellyfish/pearlescent_purple.png"), true, WWItemTags.PEARLESCENT_JELLYFISH_FOOD);

	public JellyfishVariant(@NotNull ResourceLocation key, @NotNull ResourceLocation texture, boolean pearlescent, TagKey<Item> reproductionFood) {
		this.key = key;
		this.texture = texture;
		this.pearlescent = pearlescent;
		this.reproductionFood = reproductionFood;
	}

	@NotNull
	public static JellyfishVariant register(@NotNull ResourceLocation key, @NotNull ResourceLocation texture, boolean pearlescent, TagKey<Item> reproductionFood) {
		return Registry.register(WilderWildRegistries.JELLYFISH_VARIANT, key, new JellyfishVariant(key, texture, pearlescent, reproductionFood));
	}

	public static void init() {
	}

	@NotNull
	public ResourceLocation key() {
		return this.key;
	}

	@NotNull
	public ResourceLocation texture() {
		return this.texture;
	}

	public boolean isNormal() {
		return !this.pearlescent;
	}
}
