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

package net.frozenblock.wilderwild.entity.variant.jellyfish;

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

	private static ResourceKey<JellyfishVariant> createKey(String path) {
		return ResourceKey.create(WilderWildRegistries.JELLYFISH_VARIANT, WWConstants.id(path));
	}

	private static void register(
		BootstrapContext<JellyfishVariant> bootstrapContext,
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
				new ClientAsset.ResourceTexture(WWConstants.id(texturePath)),
				new ClientAsset.ResourceTexture(WWConstants.id(texturePath + "_baby")),
				pearlescent,
				SpawnPrioritySelectors.single(new BiomeCheck(holderSet), 1),
				bootstrapContext.lookup(Registries.ITEM).getOrThrow(items)
			)
		);
	}

	@ApiStatus.Internal
	public static void bootstrap(BootstrapContext<JellyfishVariant> bootstrapContext) {
		register(bootstrapContext, VARIANT.pearlescentBlue(), "pearlescent_blue", true, WWBiomeTags.PEARLESCENT_JELLYFISH, WWItemTags.PEARLESCENT_JELLYFISH_FOOD);
		register(bootstrapContext, VARIANT.pearlescentPurple(), "pearlescent_purple", true, WWBiomeTags.PEARLESCENT_JELLYFISH, WWItemTags.PEARLESCENT_JELLYFISH_FOOD);
		register(bootstrapContext, VARIANT.blue(), "blue", false, WWBiomeTags.BLUE_JELLYFISH, WWItemTags.JELLYFISH_FOOD);
		register(bootstrapContext, VARIANT.pink(), "pink", false, WWBiomeTags.PINK_JELLYFISH, WWItemTags.JELLYFISH_FOOD);
		register(bootstrapContext, VARIANT.red(), "red", false, WWBiomeTags.RED_JELLYFISH, WWItemTags.JELLYFISH_FOOD);
		register(bootstrapContext, VARIANT.yellow(), "yellow", false, WWBiomeTags.YELLOW_JELLYFISH, WWItemTags.JELLYFISH_FOOD);
		register(bootstrapContext, VARIANT.lime(), "lime", false, WWBiomeTags.LIME_JELLYFISH, WWItemTags.JELLYFISH_FOOD);
	}
}
