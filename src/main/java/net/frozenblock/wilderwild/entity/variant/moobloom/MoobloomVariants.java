/*
 * Copyright 2024 FrozenBlock
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

package net.frozenblock.wilderwild.entity.variant.moobloom;

import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
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
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class MoobloomVariants {
	public static final ResourceKey<MoobloomVariant> DANDELION = createKey("dandelion");
	public static final ResourceKey<MoobloomVariant> POPPY = createKey("poppy");
	public static final ResourceKey<MoobloomVariant> CORNFLOWER = createKey("cornflower");
	public static final ResourceKey<MoobloomVariant> DEFAULT = DANDELION;

	private static @NotNull ResourceKey<MoobloomVariant> createKey(String string) {
		return ResourceKey.create(WilderWildRegistries.MOOBLOOM_VARIANT, WWConstants.id(string));
	}

	private static void register(
		@NotNull BootstrapContext<MoobloomVariant> bootstrapContext,
		ResourceKey<MoobloomVariant> resourceKey,
		@NotNull Block flowerBlock,
		String textureName,
		TagKey<Biome> biomes
	) {
		ResourceLocation textureLocation = WWConstants.id("entity/moobloom/" + textureName);
		bootstrapContext.register(
			resourceKey,
			new MoobloomVariant(
				flowerBlock.defaultBlockState(),
				textureLocation,
				bootstrapContext.lookup(Registries.BIOME).getOrThrow(biomes)
			)
		);
	}

	public static Holder<MoobloomVariant> getSpawnVariant(@NotNull RegistryAccess registryAccess, Holder<Biome> holder, RandomSource random) {
		Registry<MoobloomVariant> registry = registryAccess.registryOrThrow(WilderWildRegistries.MOOBLOOM_VARIANT);
		List<Holder.Reference<MoobloomVariant>> variants = registry.holders()
			.filter(reference -> (reference.value()).biomes().contains(holder))
			.toList();

		if (!variants.isEmpty()) {
			return Util.getRandom(variants, random);
		} else {
			return registry.getRandom(random).orElseThrow();
		}
	}

	public static void bootstrap(BootstrapContext<MoobloomVariant> bootstrapContext) {
		register(bootstrapContext, DANDELION, Blocks.DANDELION, "moobloom_dandelion", WWBiomeTags.MOOBLOOM_DANDELION);
		register(bootstrapContext, POPPY, Blocks.POPPY, "moobloom_poppy", WWBiomeTags.MOOBLOOM_POPPY);
		register(bootstrapContext, CORNFLOWER, Blocks.CORNFLOWER, "moobloom_cornflower", WWBiomeTags.MOOBLOOM_CORNFLOWER);
	}
}
