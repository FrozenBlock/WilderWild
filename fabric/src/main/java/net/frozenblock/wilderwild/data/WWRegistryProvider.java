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

package net.frozenblock.wilderwild.data;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;

final class WWRegistryProvider extends FabricDynamicRegistryProvider {

	WWRegistryProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(HolderLookup.Provider registries, Entries entries) {
		WWConstants.log("Adding finalized damage types to datagen", true);
		entries.addAll(asLookup(entries.getLookup(Registries.DAMAGE_TYPE)));
		WWConstants.log("Adding finalized instruments to datagen", true);
		entries.addAll(asLookup(entries.getLookup(Registries.INSTRUMENT)));
		WWConstants.log("Adding finalized features to datagen", true);
		entries.addAll(asLookup(entries.getLookup(Registries.FEATURE)));
		WWConstants.log("Adding finalized placed features to datagen", true);
		entries.addAll(asLookup(entries.placedFeatures()));
		WWConstants.log("Adding finalized biomes to datagen", true);
		entries.addAll(asLookup(entries.getLookup(Registries.BIOME)));
		WWConstants.log("Adding finalized noises to datagen", true);
		entries.addAll(asLookup(entries.getLookup(Registries.NOISE)));
		WWConstants.log("Adding finalized villager trades to datagen", true);
		entries.addAll(asLookup(entries.getLookup(Registries.VILLAGER_TRADE)));
		WWConstants.log("Adding finalized timelines to datagen", true);
		entries.addAll(asLookup(entries.getLookup(Registries.TIMELINE)));
		WWConstants.log("Adding finalized template pools to datagen", true);
		entries.addAll(asLookup(entries.getLookup(Registries.TEMPLATE_POOL)));
		WWConstants.log("Adding finalized structures to datagen", true);
		entries.addAll(asLookup(entries.getLookup(Registries.STRUCTURE)));

		// FrozenLib Dynamic Registries
		WWConstants.log("Adding finalized sound type overrides to datagen", true);
		entries.addAll(asLookup(entries.getLookup(FrozenLibRegistries.SOUND_TYPE_OVERRIDE)));
		WWConstants.log("Adding finalized clip groups to datagen", true);
		entries.addAll(asLookup(entries.getLookup(FrozenLibRegistries.CLIP_GROUP)));
		WWConstants.log("Adding finalized water-like types to datagen", true);
		entries.addAll(asLookup(entries.getLookup(FrozenLibRegistries.WATER_LIKE_TYPE)));
		WWConstants.log("Adding finalized structure music to datagen", true);
		entries.addAll(asLookup(entries.getLookup(FrozenLibRegistries.STRUCTURE_MUSIC)));
		WWConstants.log("Adding finalized rule source additions to datagen", true);
		entries.addAll(asLookup(entries.getLookup(FrozenLibRegistries.RULE_SOURCE_ADDITION)));
		WWConstants.log("Adding finalized player damage type sounds to datagen", true);
		entries.addAll(asLookup(entries.getLookup(FrozenLibRegistries.PLAYER_DAMAGE_TYPE_SOUND)));
		WWConstants.log("Adding finalized structure processor list additions to datagen", true);
		entries.addAll(asLookup(entries.getLookup(FrozenLibRegistries.STRUCTURE_PROCESSOR_LIST_ADDITION)));

		// Wilder Wild Dynamic Registries
		WWConstants.log("Adding finalized firefly colors to datagen", true);
		entries.addAll(asLookup(entries.getLookup(WilderWildRegistries.FIREFLY_COLOR)));
		WWConstants.log("Adding finalized butterfly variants to datagen", true);
		entries.addAll(asLookup(entries.getLookup(WilderWildRegistries.BUTTERFLY_VARIANT)));
		WWConstants.log("Adding finalized jellyfish variants to datagen", true);
		entries.addAll(asLookup(entries.getLookup(WilderWildRegistries.JELLYFISH_VARIANT)));
		WWConstants.log("Adding finalized crab variants to datagen", true);
		entries.addAll(asLookup(entries.getLookup(WilderWildRegistries.CRAB_VARIANT)));
		WWConstants.log("Adding finalized moobloom variants to datagen", true);
		entries.addAll(asLookup(entries.getLookup(WilderWildRegistries.MOOBLOOM_VARIANT)));
		WWConstants.log("Adding finalized termite block behaviors to datagen", true);
		entries.addAll(asLookup(entries.getLookup(WilderWildRegistries.TERMITE_BLOCK_BEHAVIOR)));
	}

	public static <T> HolderLookup.RegistryLookup<T> asLookup(HolderGetter<T> getter) {
		return (HolderLookup.RegistryLookup<T>) getter;
	}

	@Override
	public String getName() {
		return "Wilder Wild Dynamic Registries";
	}
}
