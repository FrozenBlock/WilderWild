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

package net.frozenblock.wilderwild.entity.variant.jellyfish;

import java.util.Optional;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.frozenblock.wilderwild.tag.WWItemTags;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.variant.BiomeCheck;
import net.minecraft.world.entity.variant.PriorityProvider;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;
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
		String name,
		boolean pearlescent,
		TagKey<Biome> biomeTag,
		TagKey<Item> items
	) {
		String texturePath = "entity/jellyfish/jellyfish_" + name;
		HolderSet<Biome> holderSet = bootstrapContext.lookup(Registries.BIOME).getOrThrow(biomeTag);
		bootstrapContext.register(
			resourceKey,
			new JellyfishVariant(
				new ClientAsset(WWConstants.id(texturePath)),
				pearlescent,
				SpawnPrioritySelectors.single(new BiomeCheck(holderSet), 1),
				bootstrapContext.lookup(Registries.ITEM).getOrThrow(items)
			)
		);
	}

	public static @NotNull Optional<Holder.Reference<JellyfishVariant>> selectVariantToSpawn(
		RandomSource randomSource,
		@NotNull RegistryAccess registryAccess,
		SpawnContext spawnContext
	) {
		return PriorityProvider.pick(registryAccess.lookupOrThrow(WilderWildRegistries.JELLYFISH_VARIANT).listElements(), Holder::value, randomSource, spawnContext);
	}

	public static void bootstrap(BootstrapContext<JellyfishVariant> bootstrapContext) {
		register(bootstrapContext, BLUE, "blue", false, WWBiomeTags.BLUE_JELLYFISH, WWItemTags.JELLYFISH_FOOD);
		register(bootstrapContext, LIME, "lime", false, WWBiomeTags.LIME_JELLYFISH, WWItemTags.JELLYFISH_FOOD);
		register(bootstrapContext, PINK, "pink", false, WWBiomeTags.PINK_JELLYFISH, WWItemTags.JELLYFISH_FOOD);
		register(bootstrapContext, RED, "red", false, WWBiomeTags.RED_JELLYFISH, WWItemTags.JELLYFISH_FOOD);
		register(bootstrapContext, YELLOW, "yellow", false, WWBiomeTags.YELLOW_JELLYFISH, WWItemTags.JELLYFISH_FOOD);

		register(bootstrapContext, PEARLESCENT_BLUE, "pearlescent_blue", true, WWBiomeTags.PEARLESCENT_JELLYFISH, WWItemTags.PEARLESCENT_JELLYFISH_FOOD);
		register(bootstrapContext, PEARLESCENT_PURPLE, "pearlescent_purple", true, WWBiomeTags.PEARLESCENT_JELLYFISH, WWItemTags.PEARLESCENT_JELLYFISH_FOOD);
	}
}
