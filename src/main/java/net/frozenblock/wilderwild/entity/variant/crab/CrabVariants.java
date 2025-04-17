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

package net.frozenblock.wilderwild.entity.variant.crab;

import java.util.List;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
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

public final class CrabVariants {
	public static final ResourceKey<CrabVariant> CRAB = createKey("crab");
	public static final ResourceKey<CrabVariant> DEFAULT = CRAB;

	private static @NotNull ResourceKey<CrabVariant> createKey(String string) {
		return ResourceKey.create(WilderWildRegistries.CRAB_VARIANT, WWConstants.id(string));
	}

	private static void register(
		@NotNull BootstrapContext<CrabVariant> bootstrapContext,
		ResourceKey<CrabVariant> resourceKey,
		String textureName,
		HolderSet<Biome> biomes
	) {
		ResourceLocation textureLocation = WWConstants.id("entity/crab/" + textureName);
		bootstrapContext.register(
			resourceKey,
			new CrabVariant(
				textureLocation,
				biomes
			)
		);
	}

	private static void register(
		@NotNull BootstrapContext<CrabVariant> bootstrapContext,
		ResourceKey<CrabVariant> resourceKey,
		String textureName,
		TagKey<Biome> biomes
	) {
		register(bootstrapContext, resourceKey, textureName, bootstrapContext.lookup(Registries.BIOME).getOrThrow(biomes));
	}

	public static Holder<CrabVariant> getSpawnVariant(@NotNull RegistryAccess registryAccess, Holder<Biome> holder, RandomSource random) {
		Registry<CrabVariant> registry = registryAccess.registryOrThrow(WilderWildRegistries.CRAB_VARIANT);
		List<Holder.Reference<CrabVariant>> variants = registry.holders()
			.filter(reference -> (reference.value()).biomes().contains(holder))
			.toList();

		if (!variants.isEmpty()) {
			return Util.getRandom(variants, random);
		} else {
			return registry.getRandom(random).orElseThrow();
		}
	}

	public static void bootstrap(BootstrapContext<CrabVariant> bootstrapContext) {
		register(bootstrapContext, CRAB, "crab", HolderSet.empty());
	}
}
