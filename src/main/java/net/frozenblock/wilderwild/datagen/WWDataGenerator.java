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

package net.frozenblock.wilderwild.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.frozenblock.lib.feature_flag.api.FeatureFlagApi;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.termite.TermiteBlockBehaviors;
import net.frozenblock.wilderwild.datagen.advancement.WWAdvancementProvider;
import net.frozenblock.wilderwild.datagen.loot.WWBlockInteractionLootProvider;
import net.frozenblock.wilderwild.datagen.loot.WWBlockLootProvider;
import net.frozenblock.wilderwild.datagen.loot.WWEntityLootProvider;
import net.frozenblock.wilderwild.datagen.model.WWModelProvider;
import net.frozenblock.wilderwild.datagen.recipe.WWRecipeProvider;
import net.frozenblock.wilderwild.datagen.tag.WWBiomeTagsProvider;
import net.frozenblock.wilderwild.datagen.tag.WWBlockTagsProvider;
import net.frozenblock.wilderwild.datagen.tag.WWDamageTypeTagsProvider;
import net.frozenblock.wilderwild.datagen.tag.WWEntityTagProvider;
import net.frozenblock.wilderwild.datagen.tag.WWFeatureTagsProvider;
import net.frozenblock.wilderwild.datagen.tag.WWFluidTagsProvider;
import net.frozenblock.wilderwild.datagen.tag.WWGameEventTagsProvider;
import net.frozenblock.wilderwild.datagen.tag.WWItemTagsProvider;
import net.frozenblock.wilderwild.datagen.tag.WWTimelineTagsProvider;
import net.frozenblock.wilderwild.datagen.tag.WWVillagerTradesTagsProvider;
import net.frozenblock.wilderwild.datagen.trading.WWVillagerTrades;
import net.frozenblock.wilderwild.entity.variant.butterfly.ButterflyVariants;
import net.frozenblock.wilderwild.entity.variant.crab.CrabVariants;
import net.frozenblock.wilderwild.entity.variant.firefly.FireflyColors;
import net.frozenblock.wilderwild.entity.variant.jellyfish.JellyfishVariants;
import net.frozenblock.wilderwild.entity.variant.moobloom.MoobloomVariants;
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.frozenblock.wilderwild.registry.WWDamageTypes;
import net.frozenblock.wilderwild.registry.WWTimelines;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.frozenblock.wilderwild.worldgen.impl.noise.WWNoise;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public final class WWDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
		FeatureFlagApi.rebuild();
		final FabricDataGenerator.Pack pack = dataGenerator.createPack();

		// ASSETS
		pack.addProvider(WWModelProvider::new);

		// DATA
		// When adding a registry to generate, don't forget this!
		pack.addProvider(WWRegistryProvider::new);

		pack.addProvider(WWBlockLootProvider::new);
		pack.addProvider(WWBlockInteractionLootProvider::new);
		pack.addProvider(WWBiomeTagsProvider::new);
		pack.addProvider(WWBlockTagsProvider::new);
		pack.addProvider(WWFluidTagsProvider::new);
		pack.addProvider(WWDamageTypeTagsProvider::new);
		pack.addProvider(WWItemTagsProvider::new);
		pack.addProvider(WWEntityTagProvider::new);
		pack.addProvider(WWGameEventTagsProvider::new);
		pack.addProvider(WWVillagerTradesTagsProvider::new);
		pack.addProvider(WWFeatureTagsProvider::new);
		pack.addProvider(WWTimelineTagsProvider::new);
		pack.addProvider(WWEntityLootProvider::new);
		pack.addProvider(WWRecipeProvider::new);
		pack.addProvider(WWAdvancementProvider::new);
	}

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		WWConstants.logWithModId("Generating dynamic registries for", WWConstants.UNSTABLE_LOGGING);

		registryBuilder.add(Registries.DAMAGE_TYPE, WWDamageTypes::bootstrap);
		registryBuilder.add(Registries.CONFIGURED_FEATURE, WWFeatureBootstrap::bootstrapConfigured);
		registryBuilder.add(Registries.PLACED_FEATURE, WWFeatureBootstrap::bootstrapPlaced);
		registryBuilder.add(Registries.BIOME, WWBiomes::bootstrap);
		registryBuilder.add(Registries.NOISE, WWNoise::bootstrap);
		registryBuilder.add(Registries.VILLAGER_TRADE, WWVillagerTrades::bootstrap);
		registryBuilder.add(Registries.TIMELINE, WWTimelines::bootstrap);

		// Wilder Wild Registries
		registryBuilder.add(WilderWildRegistries.FIREFLY_COLOR, FireflyColors::bootstrap);
		registryBuilder.add(WilderWildRegistries.BUTTERFLY_VARIANT, ButterflyVariants::bootstrap);
		registryBuilder.add(WilderWildRegistries.JELLYFISH_VARIANT, JellyfishVariants::bootstrap);
		registryBuilder.add(WilderWildRegistries.CRAB_VARIANT, CrabVariants::bootstrap);
		registryBuilder.add(WilderWildRegistries.MOOBLOOM_VARIANT, MoobloomVariants::bootstrap);
		registryBuilder.add(WilderWildRegistries.TERMITE_BLOCK_BEHAVIOR, TermiteBlockBehaviors::bootstrap);
	}

	@Override
	public String getEffectiveModId() {
		return WWConstants.MOD_ID;
	}
}
