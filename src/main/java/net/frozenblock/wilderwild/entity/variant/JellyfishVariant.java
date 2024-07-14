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

package net.frozenblock.wilderwild.entity.variant;

import net.frozenblock.wilderwild.WilderConstants;
import net.frozenblock.wilderwild.registry.WilderRegistry;
import net.frozenblock.wilderwild.tag.WilderItemTags;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public record JellyfishVariant(@NotNull ResourceLocation key, @NotNull ResourceLocation texture, boolean pearlescent, @NotNull TagKey<Item> reproductionFood) {



	public static final JellyfishVariant BLUE = register(WilderConstants.id("blue"), WilderConstants.id("textures/entity/jellyfish/blue.png"), false, WilderItemTags.JELLYFISH_FOOD);
	public static final JellyfishVariant LIME = register(WilderConstants.id("lime"), WilderConstants.id("textures/entity/jellyfish/lime.png"), false, WilderItemTags.JELLYFISH_FOOD);
	public static final JellyfishVariant PINK = register(WilderConstants.id("pink"), WilderConstants.id("textures/entity/jellyfish/pink.png"), false, WilderItemTags.JELLYFISH_FOOD);
	public static final JellyfishVariant RED = register(WilderConstants.id("red"), WilderConstants.id("textures/entity/jellyfish/red.png"), false, WilderItemTags.JELLYFISH_FOOD);
	public static final JellyfishVariant YELLOW = register(WilderConstants.id("yellow"), WilderConstants.id("textures/entity/jellyfish/yellow.png"), false, WilderItemTags.JELLYFISH_FOOD);

	public static final JellyfishVariant PEARLESCENT_BLUE = register(WilderConstants.id("pearlescent_blue"), WilderConstants.id("textures/entity/jellyfish/pearlescent_blue.png"), true, WilderItemTags.PEARLESCENT_JELLYFISH_FOOD);
	public static final JellyfishVariant PEARLESCENT_PURPLE = register(WilderConstants.id("pearlescent_purple"), WilderConstants.id("textures/entity/jellyfish/pearlescent_purple.png"), true, WilderItemTags.PEARLESCENT_JELLYFISH_FOOD);

	public JellyfishVariant(@NotNull ResourceLocation key, @NotNull ResourceLocation texture, boolean pearlescent, TagKey<Item> reproductionFood) {
		this.key = key;
		this.texture = texture;
		this.pearlescent = pearlescent;
		this.reproductionFood = reproductionFood;
	}

	@NotNull
	public static JellyfishVariant register(@NotNull ResourceLocation key, @NotNull ResourceLocation texture, boolean pearlescent, TagKey<Item> reproductionFood) {
		return Registry.register(WilderRegistry.JELLYFISH_VARIANT, key, new JellyfishVariant(key, texture, pearlescent, reproductionFood));
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
