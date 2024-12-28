/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.datagen;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import org.jetbrains.annotations.NotNull;

final class WWRegistryProvider extends FabricDynamicRegistryProvider {

	WWRegistryProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(@NotNull HolderLookup.Provider registries, @NotNull Entries entries) {
		final var damageTypes = asLookup(entries.getLookup(Registries.DAMAGE_TYPE));
		final var configuredFeatures = asLookup(entries.getLookup(Registries.CONFIGURED_FEATURE));
		final var placedFeatures = asLookup(entries.placedFeatures());
		final var biomes = asLookup(entries.getLookup(Registries.BIOME));
		final var noises = asLookup(entries.getLookup(Registries.NOISE));
		final var processorLists = asLookup(entries.getLookup(Registries.PROCESSOR_LIST));
		final var templatePools = asLookup(entries.getLookup(Registries.TEMPLATE_POOL));
		final var structures = asLookup(entries.getLookup(Registries.STRUCTURE));
		final var structureSets = asLookup(entries.getLookup(Registries.STRUCTURE_SET));

		WWConstants.log("Adding finalized damage types to datagen", true);
		entries.addAll(damageTypes);
		WWConstants.log("Adding finalized configured features to datagen", true);
		entries.addAll(configuredFeatures);
		WWConstants.log("Adding finalized placed features to datagen", true);
		entries.addAll(placedFeatures);
		WWConstants.log("Adding finalized biomes to datagen", true);
		entries.addAll(biomes);
		WWConstants.log("Adding finalized noises to datagen", true);
		entries.addAll(noises);
		WWConstants.log("Adding finalized processor lists to datagen", true);
		entries.addAll(processorLists);
		WWConstants.log("Adding finalized template pools to datagen", true);
		entries.addAll(templatePools);
		WWConstants.log("Adding finalized structures to datagen", true);
		entries.addAll(structures);
		WWConstants.log("Adding finalized structure sets to datagen", true);
		entries.addAll(structureSets);

		// Wilder Wild Dynamic Registries
		final var fireflyColors = asLookup(entries.getLookup(WilderWildRegistries.FIREFLY_COLOR));
		WWConstants.log("Adding finalized firefly colors to datagen", true);
		entries.addAll(fireflyColors);
	}


	public static <T> HolderLookup.RegistryLookup<T> asLookup(HolderGetter<T> getter) {
		return (HolderLookup.RegistryLookup<T>) getter;
	}

	@Override
	@NotNull
	public String getName() {
		return "Wilder Wild Dynamic Registries";
	}
}
