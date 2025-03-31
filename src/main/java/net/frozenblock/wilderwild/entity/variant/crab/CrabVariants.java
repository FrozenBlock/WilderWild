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

package net.frozenblock.wilderwild.entity.variant.crab;

import java.util.Optional;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.variant.BiomeCheck;
import net.minecraft.world.entity.variant.PriorityProvider;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

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
		HolderSet<Biome> holderSet
	) {
		String texturePath = "entity/crab/" + textureName;
		bootstrapContext.register(
			resourceKey,
			new CrabVariant(new ClientAsset(WWConstants.id(texturePath)), SpawnPrioritySelectors.single(new BiomeCheck(holderSet), 1))
		);
	}

	public static @NotNull Optional<Holder.Reference<CrabVariant>> selectVariantToSpawn(
		RandomSource randomSource,
		@NotNull RegistryAccess registryAccess,
		SpawnContext spawnContext
	) {
		return PriorityProvider.pick(registryAccess.lookupOrThrow(WilderWildRegistries.CRAB_VARIANT).listElements(), Holder::value, randomSource, spawnContext);
	}

	public static void bootstrap(BootstrapContext<CrabVariant> bootstrapContext) {
		register(bootstrapContext, CRAB, "crab", HolderSet.empty());
	}
}
