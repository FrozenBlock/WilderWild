/*
 * Copyright 2023-2025 FrozenBlock
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

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.frozenblock.lib.feature_flag.api.FrozenFeatureFlags;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.termite.TermiteBlockBehaviors;
import net.frozenblock.wilderwild.datagen.advancement.WWAdvancementProvider;
import net.frozenblock.wilderwild.datagen.loot.WWBlockLootProvider;
import net.frozenblock.wilderwild.datagen.loot.WWEntityLootProvider;
import net.frozenblock.wilderwild.datagen.model.WWModelProvider;
import net.frozenblock.wilderwild.datagen.recipe.WWRecipeProvider;
import net.frozenblock.wilderwild.datagen.tag.WWBiomeTagProvider;
import net.frozenblock.wilderwild.datagen.tag.WWBlockTagProvider;
import net.frozenblock.wilderwild.datagen.tag.WWDamageTypeTagProvider;
import net.frozenblock.wilderwild.datagen.tag.WWEntityTagProvider;
import net.frozenblock.wilderwild.datagen.tag.WWGameEventTagProvider;
import net.frozenblock.wilderwild.datagen.tag.WWInstrumentTagProvider;
import net.frozenblock.wilderwild.datagen.tag.WWItemTagProvider;
import net.frozenblock.wilderwild.entity.variant.butterfly.ButterflyVariants;
import net.frozenblock.wilderwild.entity.variant.firefly.FireflyColors;
import net.frozenblock.wilderwild.entity.variant.jellyfish.JellyfishVariants;
import net.frozenblock.wilderwild.entity.variant.moobloom.MoobloomVariants;
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.frozenblock.wilderwild.registry.WWDamageTypes;
import net.frozenblock.wilderwild.registry.WWInstruments;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.frozenblock.wilderwild.worldgen.impl.noise.WWNoise;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import org.jetbrains.annotations.NotNull;

public final class WWDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(@NotNull FabricDataGenerator dataGenerator) {
		FrozenFeatureFlags.rebuild();
		final FabricDataGenerator.Pack pack = dataGenerator.createPack();

		// ASSETS

		pack.addProvider(WWModelProvider::new);

		// DATA

		// When adding a registry to generate, don't forget this!
		pack.addProvider(WWRegistryProvider::new);

		pack.addProvider(WWBlockLootProvider::new);
		pack.addProvider(WWBiomeTagProvider::new);
		pack.addProvider(WWBlockTagProvider::new);
		pack.addProvider(WWDamageTypeTagProvider::new);
		pack.addProvider(WWItemTagProvider::new);
		pack.addProvider(WWInstrumentTagProvider::new);
		pack.addProvider(WWEntityTagProvider::new);
		pack.addProvider(WWGameEventTagProvider::new);
		pack.addProvider(WWEntityLootProvider::new);
		pack.addProvider(WWRecipeProvider::new);
		pack.addProvider(WWAdvancementProvider::new);
	}

	@Override
	public void buildRegistry(@NotNull RegistrySetBuilder registryBuilder) {
		WWConstants.logWithModId("Generating dynamic registries for", WWConstants.UNSTABLE_LOGGING);

		registryBuilder.add(Registries.DAMAGE_TYPE, WWDamageTypes::bootstrap);
		registryBuilder.add(Registries.INSTRUMENT, WWInstruments::bootstrap);
		registryBuilder.add(Registries.CONFIGURED_FEATURE, WWFeatureBootstrap::bootstrapConfigured);
		registryBuilder.add(Registries.PLACED_FEATURE, WWFeatureBootstrap::bootstrapPlaced);
		registryBuilder.add(Registries.BIOME, WWBiomes::bootstrap);
		registryBuilder.add(Registries.NOISE, WWNoise::bootstrap);

		// Wilder Wild Registries
		registryBuilder.add(WilderWildRegistries.FIREFLY_COLOR, FireflyColors::bootstrap);
		registryBuilder.add(WilderWildRegistries.BUTTERFLY_VARIANT, ButterflyVariants::bootstrap);
		registryBuilder.add(WilderWildRegistries.JELLYFISH_VARIANT, JellyfishVariants::bootstrap);
		registryBuilder.add(WilderWildRegistries.MOOBLOOM_VARIANT, MoobloomVariants::bootstrap);
		registryBuilder.add(WilderWildRegistries.TERMITE_BLOCK_BEHAVIOR, TermiteBlockBehaviors::bootstrap);
	}

	@Override
	public @NotNull String getEffectiveModId() {
		return WWConstants.MOD_ID;
	}
}
