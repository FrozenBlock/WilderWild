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

package net.frozenblock.wilderwild.entity.ambient.butterfly;

import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.variant.BiomeCheck;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;
import net.minecraft.world.level.biome.Biome;

public final class ButterflyVariants {
	public static final ResourceKey<ButterflyVariant> MONARCH = createKey("monarch");
	public static final ResourceKey<ButterflyVariant> RED_LACEWING = createKey("red_lacewing");
	public static final ResourceKey<ButterflyVariant> MARBLED = createKey("marbled");
	public static final ResourceKey<ButterflyVariant> MORPHO_BLUE = createKey("morpho_blue");
	public static final ResourceKey<ButterflyVariant> GREEN_HAIRSTREAK = createKey("green_hairstreak");
	public static final ResourceKey<ButterflyVariant> CLOUDED_YELLOW = createKey("clouded_yellow");
	public static final ResourceKey<ButterflyVariant> DUSKWING = createKey("duskwing");
	public static final ResourceKey<ButterflyVariant> DEFAULT = MONARCH;

	private static ResourceKey<ButterflyVariant> createKey(String name) {
		return ResourceKey.create(WilderWildRegistries.BUTTERFLY_VARIANT, WWConstants.id(name));
	}

	public static void bootstrap(BootstrapContext<ButterflyVariant> context) {
		register(context, MONARCH, "monarch", WWBiomeTags.BUTTERFLY_MONARCH);
		register(context, RED_LACEWING, "red_lacewing", WWBiomeTags.BUTTERFLY_RED_LACEWING);
		register(context, MARBLED, "marbled", WWBiomeTags.BUTTERFLY_MARBLED);
		register(context, MORPHO_BLUE, "morpho_blue", WWBiomeTags.BUTTERFLY_MORPHO_BLUE);
		register(context, GREEN_HAIRSTREAK, "green_hairstreak", WWBiomeTags.BUTTERFLY_GREEN_HAIRSTREAK);
		register(context, CLOUDED_YELLOW, "clouded_yellow", WWBiomeTags.BUTTERFLY_CLOUDED_YELLOW);
		register(context, DUSKWING, "duskwing", WWBiomeTags.BUTTERFLY_DUSKWING);
	}

	private static void register(
		BootstrapContext<ButterflyVariant> context,
		ResourceKey<ButterflyVariant> name,
		String variantName,
		TagKey<Biome> spawnBiome
	) {
		final String texturePath = "entity/butterfly/butterfly_" + variantName;
		HolderSet<Biome> biomes = context.lookup(Registries.BIOME).getOrThrow(spawnBiome);
		context.register(
			name,
			new ButterflyVariant(new ClientAsset.ResourceTexture(WWConstants.id(texturePath)), SpawnPrioritySelectors.single(new BiomeCheck(biomes), 1), variantName)
		);
	}
}
