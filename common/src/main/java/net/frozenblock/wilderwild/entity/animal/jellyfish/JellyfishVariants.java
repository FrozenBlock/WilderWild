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

package net.frozenblock.wilderwild.entity.animal.jellyfish;

import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.frozenblock.wilderwild.tag.WWItemTags;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.variant.BiomeCheck;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;

public final class JellyfishVariants {
	public static final ResourceKey<JellyfishVariant> BLUE = createKey("blue");
	public static final ResourceKey<JellyfishVariant> LIME = createKey("lime");
	public static final ResourceKey<JellyfishVariant> PINK = createKey("pink");
	public static final ResourceKey<JellyfishVariant> RED = createKey("red");
	public static final ResourceKey<JellyfishVariant> YELLOW = createKey("yellow");
	public static final ResourceKey<JellyfishVariant> PEARLESCENT_BLUE = createKey("pearlescent_blue");
	public static final ResourceKey<JellyfishVariant> PEARLESCENT_PURPLE = createKey("pearlescent_purple");
	public static final ResourceKey<JellyfishVariant> DEFAULT = PINK;

	private static ResourceKey<JellyfishVariant> createKey(String name) {
		return ResourceKey.create(WilderWildRegistries.JELLYFISH_VARIANT, WWConstants.id(name));
	}

	public static void bootstrap(BootstrapContext<JellyfishVariant> context) {
		register(context, BLUE, "blue", false, WWBiomeTags.BLUE_JELLYFISH, WWItemTags.JELLYFISH_FOOD);
		register(context, LIME, "lime", false, WWBiomeTags.LIME_JELLYFISH, WWItemTags.JELLYFISH_FOOD);
		register(context, PINK, "pink", false, WWBiomeTags.PINK_JELLYFISH, WWItemTags.JELLYFISH_FOOD);
		register(context, RED, "red", false, WWBiomeTags.RED_JELLYFISH, WWItemTags.JELLYFISH_FOOD);
		register(context, YELLOW, "yellow", false, WWBiomeTags.YELLOW_JELLYFISH, WWItemTags.JELLYFISH_FOOD);

		register(context, PEARLESCENT_BLUE, "pearlescent_blue", true, WWBiomeTags.PEARLESCENT_JELLYFISH, WWItemTags.PEARLESCENT_JELLYFISH_FOOD);
		register(context, PEARLESCENT_PURPLE, "pearlescent_purple", true, WWBiomeTags.PEARLESCENT_JELLYFISH, WWItemTags.PEARLESCENT_JELLYFISH_FOOD);
	}

	private static void register(
		BootstrapContext<JellyfishVariant> context,
		ResourceKey<JellyfishVariant> name,
		String textureName,
		boolean pearlescent,
		TagKey<Biome> spawnBiome,
		TagKey<Item> foodItem
	) {
		final String texturePath = "entity/jellyfish/jellyfish_" + textureName;
		final HolderSet<Biome> biomes = context.lookup(Registries.BIOME).getOrThrow(spawnBiome);
		context.register(
			name,
			new JellyfishVariant(
				new ClientAsset.ResourceTexture(WWConstants.id(texturePath)),
				new ClientAsset.ResourceTexture(WWConstants.id(texturePath + "_baby")),
				pearlescent,
				SpawnPrioritySelectors.single(new BiomeCheck(biomes), 1),
				context.lookup(Registries.ITEM).getOrThrow(foodItem)
			)
		);
	}
}
