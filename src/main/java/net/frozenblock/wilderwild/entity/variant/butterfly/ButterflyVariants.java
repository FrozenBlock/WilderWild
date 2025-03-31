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

package net.frozenblock.wilderwild.entity.variant.butterfly;

import java.util.Optional;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
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
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

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
		String name,
		TagKey<Biome> biomeTag
	) {
		String texturePath = "entity/butterfly/butterfly_" + name;
		register(bootstrapContext, resourceKey, texturePath, name, biomeTag);
	}

	private static void register(
		@NotNull BootstrapContext<ButterflyVariant> bootstrapContext,
		ResourceKey<ButterflyVariant> resourceKey,
		String texturePath,
		String name,
		TagKey<Biome> biomeTag
	) {
		HolderSet<Biome> holderSet = bootstrapContext.lookup(Registries.BIOME).getOrThrow(biomeTag);
		bootstrapContext.register(
			resourceKey,
			new ButterflyVariant(new ClientAsset(WWConstants.id(texturePath)), SpawnPrioritySelectors.single(new BiomeCheck(holderSet), 1), name)
		);
	}

	public static @NotNull Optional<Holder.Reference<ButterflyVariant>> selectVariantToSpawn(
		RandomSource randomSource,
		@NotNull RegistryAccess registryAccess,
		SpawnContext spawnContext
	) {
		return PriorityProvider.pick(registryAccess.lookupOrThrow(WilderWildRegistries.BUTTERFLY_VARIANT).listElements(), Holder::value, randomSource, spawnContext);
	}

	public static void bootstrap(BootstrapContext<ButterflyVariant> bootstrapContext) {
		register(bootstrapContext, MONARCH, "monarch", WWBiomeTags.BUTTERFLY_MONARCH);
		register(bootstrapContext, RED_LACEWING, "red_lacewing", WWBiomeTags.BUTTERFLY_RED_LACEWING);
		register(bootstrapContext, MARBLED, "marbled", WWBiomeTags.BUTTERFLY_MARBLED);
		register(bootstrapContext, MORPHO_BLUE, "morpho_blue", WWBiomeTags.BUTTERFLY_MORPHO_BLUE);
		register(bootstrapContext, GREEN_HAIRSTREAK, "green_hairstreak", WWBiomeTags.BUTTERFLY_GREEN_HAIRSTREAK);
		register(bootstrapContext, CLOUDED_YELLOW, "clouded_yellow", WWBiomeTags.BUTTERFLY_CLOUDED_YELLOW);
		register(bootstrapContext, DUSKWING, "duskwing", WWBiomeTags.BUTTERFLY_DUSKWING);
	}
}
