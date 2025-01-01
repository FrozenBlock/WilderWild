/*
 * Copyright 2024-2025 FrozenBlock
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

package net.frozenblock.wilderwild.entity.variant.jellyfish;

import java.util.List;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.frozenblock.wilderwild.tag.WWItemTags;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

public final class JellyfishVariants {
	public static final ResourceKey<JellyfishVariant> BLUE = createKey("blue");
	public static final ResourceKey<JellyfishVariant> LIME = createKey("lime");
	public static final ResourceKey<JellyfishVariant> PINK = createKey("pink");
	public static final ResourceKey<JellyfishVariant> RED = createKey("red");
	public static final ResourceKey<JellyfishVariant> YELLOW = createKey("yellow");
	public static final ResourceKey<JellyfishVariant> PEARLESCENT_BLUE = createKey("pearlescent_blue");
	public static final ResourceKey<JellyfishVariant> PEARLESCENT_PURPLE = createKey("pearlescent_purple");
	public static final ResourceKey<JellyfishVariant> DEFAULT = PINK;

	private static @NotNull ResourceKey<JellyfishVariant> createKey(String string) {
		return ResourceKey.create(WilderWildRegistries.JELLYFISH_VARIANT, WWConstants.id(string));
	}

	private static void register(
		@NotNull BootstrapContext<JellyfishVariant> bootstrapContext,
		ResourceKey<JellyfishVariant> resourceKey,
		String textureName,
		boolean pearlescent,
		TagKey<Biome> biomes,
		TagKey<Item> items
	) {
		ResourceLocation textureLocation = WWConstants.id("entity/jellyfish/" + textureName);
		bootstrapContext.register(
			resourceKey,
			new JellyfishVariant(
				textureLocation,
				pearlescent,
				bootstrapContext.lookup(Registries.BIOME).getOrThrow(biomes),
				bootstrapContext.lookup(Registries.ITEM).getOrThrow(items)
			)
		);
	}

	public static Holder<JellyfishVariant> getSpawnVariant(@NotNull RegistryAccess registryAccess, Holder<Biome> holder, RandomSource random) {
		Registry<JellyfishVariant> registry = registryAccess.lookupOrThrow(WilderWildRegistries.JELLYFISH_VARIANT);
		List<Holder.Reference<JellyfishVariant>> variants = registry.listElements()
			.filter(reference -> (reference.value()).biomes().contains(holder))
			.toList();

		if (!variants.isEmpty()) {
			return Util.getRandom(variants, random);
		} else {
			return registry.get(DEFAULT).orElse(registry.getRandom(random).orElseThrow());
		}
	}

	public static void bootstrap(BootstrapContext<JellyfishVariant> bootstrapContext) {
		register(bootstrapContext, BLUE, "jellyfish_blue", false, WWBiomeTags.BLUE_JELLYFISH, WWItemTags.JELLYFISH_FOOD);
		register(bootstrapContext, LIME, "jellyfish_lime", false, WWBiomeTags.LIME_JELLYFISH, WWItemTags.JELLYFISH_FOOD);
		register(bootstrapContext, PINK, "jellyfish_pink", false, WWBiomeTags.PINK_JELLYFISH, WWItemTags.JELLYFISH_FOOD);
		register(bootstrapContext, RED, "jellyfish_red", false, WWBiomeTags.RED_JELLYFISH, WWItemTags.JELLYFISH_FOOD);
		register(bootstrapContext, YELLOW, "jellyfish_yellow", false, WWBiomeTags.YELLOW_JELLYFISH, WWItemTags.JELLYFISH_FOOD);

		register(bootstrapContext, PEARLESCENT_BLUE, "jellyfish_pearlescent_blue", true, WWBiomeTags.PEARLESCENT_JELLYFISH, WWItemTags.PEARLESCENT_JELLYFISH_FOOD);
		register(bootstrapContext, PEARLESCENT_PURPLE, "jellyfish_pearlescent_purple", true, WWBiomeTags.PEARLESCENT_JELLYFISH, WWItemTags.PEARLESCENT_JELLYFISH_FOOD);
	}
}
