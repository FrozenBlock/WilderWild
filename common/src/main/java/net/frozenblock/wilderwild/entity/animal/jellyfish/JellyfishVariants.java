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

import lombok.experimental.UtilityClass;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.impl.MesogleaCollection;
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
import org.jetbrains.annotations.ApiStatus;

@UtilityClass
public final class JellyfishVariants {
	public static final MesogleaCollection<ResourceKey<JellyfishVariant>> VARIANT = MesogleaCollection.NAMES.map(JellyfishVariants::createKey);
	public static final ResourceKey<JellyfishVariant> DEFAULT = VARIANT.pink();

	private static ResourceKey<JellyfishVariant> createKey(String name) {
		return ResourceKey.create(WilderWildRegistries.JELLYFISH_VARIANT, WWConstants.id(name));
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

	@ApiStatus.Internal
	public static void bootstrap(BootstrapContext<JellyfishVariant> context) {
		register(context, VARIANT.pearlescentBlue(), "pearlescent_blue", true, WWBiomeTags.PEARLESCENT_JELLYFISH, WWItemTags.PEARLESCENT_JELLYFISH_FOOD);
		register(context, VARIANT.pearlescentPurple(), "pearlescent_purple", true, WWBiomeTags.PEARLESCENT_JELLYFISH, WWItemTags.PEARLESCENT_JELLYFISH_FOOD);
		register(context, VARIANT.blue(), "blue", false, WWBiomeTags.BLUE_JELLYFISH, WWItemTags.JELLYFISH_FOOD);
		register(context, VARIANT.pink(), "pink", false, WWBiomeTags.PINK_JELLYFISH, WWItemTags.JELLYFISH_FOOD);
		register(context, VARIANT.red(), "red", false, WWBiomeTags.RED_JELLYFISH, WWItemTags.JELLYFISH_FOOD);
		register(context, VARIANT.yellow(), "yellow", false, WWBiomeTags.YELLOW_JELLYFISH, WWItemTags.JELLYFISH_FOOD);
		register(context, VARIANT.lime(), "lime", false, WWBiomeTags.LIME_JELLYFISH, WWItemTags.JELLYFISH_FOOD);
	}
}
