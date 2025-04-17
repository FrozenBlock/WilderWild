/*
 * Copyright 2025 FrozenBlock
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

package net.frozenblock.wilderwild.entity.variant.butterfly;

import java.util.List;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;
import net.minecraft.Util;

public final class ButterflyVariants {
	public static final ResourceKey<ButterflyVariant> MONARCH = createKey("monarch");
	public static final ResourceKey<ButterflyVariant> RED_LACEWING = createKey("red_lacewing");
	public static final ResourceKey<ButterflyVariant> MARBLED = createKey("marbled");
	public static final ResourceKey<ButterflyVariant> MORPHO_BLUE = createKey("morpho_blue");
	public static final ResourceKey<ButterflyVariant> GREEN_HAIRSTREAK = createKey("green_hairstreak");
	public static final ResourceKey<ButterflyVariant> CLOUDED_YELLOW = createKey("clouded_yellow");
	public static final ResourceKey<ButterflyVariant> DUSKWING = createKey("duskwing");
	public static final ResourceKey<ButterflyVariant> DEFAULT = MONARCH;

	private static @NotNull ResourceKey<ButterflyVariant> createKey(String string) {
		return ResourceKey.create(WilderWildRegistries.BUTTERFLY_VARIANT, WWConstants.id(string));
	}

	private static void register(
		@NotNull BootstrapContext<ButterflyVariant> bootstrapContext,
		ResourceKey<ButterflyVariant> resourceKey,
		String textureName,
		TagKey<Biome> biomes
	) {
		ResourceLocation textureLocation = WWConstants.id("entity/butterfly/" + textureName);
		bootstrapContext.register(
			resourceKey,
			new ButterflyVariant(
				textureLocation,
				bootstrapContext.lookup(Registries.BIOME).getOrThrow(biomes)
			)
		);
	}

	public static Holder<ButterflyVariant> getSpawnVariant(@NotNull RegistryAccess registryAccess, Holder<Biome> holder, RandomSource random) {
		Registry<ButterflyVariant> registry = registryAccess.lookupOrThrow(WilderWildRegistries.BUTTERFLY_VARIANT);
		List<Holder.Reference<ButterflyVariant>> variants = registry.listElements()
			.filter(reference -> (reference.value()).biomes().contains(holder))
			.toList();

		if (!variants.isEmpty()) {
			return Util.getRandom(variants, random);
		} else {
			return registry.getRandom(random).orElseThrow();
		}
	}

	public static void bootstrap(BootstrapContext<ButterflyVariant> bootstrapContext) {
		register(bootstrapContext, MONARCH, "butterfly_monarch", WWBiomeTags.BUTTERFLY_MONARCH);
		register(bootstrapContext, RED_LACEWING, "butterfly_red_lacewing", WWBiomeTags.BUTTERFLY_RED_LACEWING);
		register(bootstrapContext, MARBLED, "butterfly_marbled", WWBiomeTags.BUTTERFLY_MARBLED);
		register(bootstrapContext, MORPHO_BLUE, "butterfly_morpho_blue", WWBiomeTags.BUTTERFLY_MORPHO_BLUE);
		register(bootstrapContext, GREEN_HAIRSTREAK, "butterfly_green_hairstreak", WWBiomeTags.BUTTERFLY_GREEN_HAIRSTREAK);
		register(bootstrapContext, CLOUDED_YELLOW, "butterfly_clouded_yellow", WWBiomeTags.BUTTERFLY_CLOUDED_YELLOW);
		register(bootstrapContext, DUSKWING, "butterfly_duskwing", WWBiomeTags.BUTTERFLY_DUSKWING);
	}
}
