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

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.frozenblock.lib.feature_flag.api.FeatureFlagApi;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.leaves.FallingLeafDatas;
import net.frozenblock.wilderwild.block.termite.TermiteBlockBehaviors;
import net.frozenblock.wilderwild.data.advancement.WWAdvancementProvider;
import net.frozenblock.wilderwild.data.loot.WWBlockInteractionLootProvider;
import net.frozenblock.wilderwild.data.loot.WWBlockLootProvider;
import net.frozenblock.wilderwild.data.loot.WWEntityLootProvider;
import net.frozenblock.wilderwild.data.model.WWModelProvider;
import net.frozenblock.wilderwild.data.recipe.WWRecipeProvider;
import net.frozenblock.wilderwild.data.sound.WWPlayerDamageTypeSounds;
import net.frozenblock.wilderwild.data.sound.WWSoundTypeOverrides;
import net.frozenblock.wilderwild.data.tag.WWBiomeTagsProvider;
import net.frozenblock.wilderwild.data.tag.WWBlockTagsProvider;
import net.frozenblock.wilderwild.data.tag.WWDamageTypeTagsProvider;
import net.frozenblock.wilderwild.data.tag.WWEntityTypeTagsProvider;
import net.frozenblock.wilderwild.data.tag.WWFeatureTagsProvider;
import net.frozenblock.wilderwild.data.tag.WWFluidTagsProvider;
import net.frozenblock.wilderwild.data.tag.WWGameEventTagsProvider;
import net.frozenblock.wilderwild.data.tag.WWItemTagsProvider;
import net.frozenblock.wilderwild.data.tag.WWStructureTagsProvider;
import net.frozenblock.wilderwild.data.tag.WWTimelineTagsProvider;
import net.frozenblock.wilderwild.data.tag.WWVillagerTradesTagsProvider;
import net.frozenblock.wilderwild.data.trading.WWVillagerTrades;
import net.frozenblock.wilderwild.data.worldgen.WWMaterialRules;
import net.frozenblock.wilderwild.data.worldgen.noise.WWNoise;
import net.frozenblock.wilderwild.data.worldgen.structure.WWAbandonedCampStructurePools;
import net.frozenblock.wilderwild.data.worldgen.structure.WWStructureMusic;
import net.frozenblock.wilderwild.data.worldgen.structure.WWStructureProcessorListAdditions;
import net.frozenblock.wilderwild.data.worldgen.structure.WWStructures;
import net.frozenblock.wilderwild.entity.variant.butterfly.ButterflyVariants;
import net.frozenblock.wilderwild.entity.variant.crab.CrabVariants;
import net.frozenblock.wilderwild.entity.variant.firefly.FireflyColors;
import net.frozenblock.wilderwild.entity.variant.jellyfish.JellyfishVariants;
import net.frozenblock.wilderwild.entity.variant.moobloom.MoobloomVariants;
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.frozenblock.wilderwild.registry.WWClipGroups;
import net.frozenblock.wilderwild.registry.WWDamageTypes;
import net.frozenblock.wilderwild.registry.WWTimelines;
import net.frozenblock.wilderwild.registry.WWVariantSpawnInjections;
import net.frozenblock.wilderwild.registry.WWWaterLikeTypes;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
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
		pack.addProvider(WWEntityTypeTagsProvider::new);
		pack.addProvider(WWGameEventTagsProvider::new);
		pack.addProvider(WWVillagerTradesTagsProvider::new);
		pack.addProvider(WWFeatureTagsProvider::new);
		pack.addProvider(WWStructureTagsProvider::new);
		pack.addProvider(WWTimelineTagsProvider::new);
		pack.addProvider(WWEntityLootProvider::new);
		pack.addProvider(WWRecipeProvider::new);
		pack.addProvider(WWAdvancementProvider::new);
	}

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		WWConstants.logWithModId("Generating dynamic registries for", WWConstants.UNSTABLE_LOGGING);

		registryBuilder.add(Registries.DAMAGE_TYPE, WWDamageTypes::bootstrap);
		registryBuilder.add(Registries.FEATURE, WWFeatureBootstrap::bootstrapConfigured);
		registryBuilder.add(Registries.PLACED_FEATURE, WWFeatureBootstrap::bootstrapPlaced);
		registryBuilder.add(Registries.BIOME, WWBiomes::bootstrap);
		registryBuilder.add(Registries.NOISE, WWNoise::bootstrap);
		registryBuilder.add(Registries.VILLAGER_TRADE, WWVillagerTrades::bootstrap);
		registryBuilder.add(Registries.TIMELINE, WWTimelines::bootstrap);
		registryBuilder.add(Registries.TEMPLATE_POOL, WWAbandonedCampStructurePools::bootstrap);
		registryBuilder.add(Registries.STRUCTURE, WWStructures::bootstrap);

		// FrozenLib Registries
		registryBuilder.add(FrozenLibRegistries.SOUND_TYPE_OVERRIDE, WWSoundTypeOverrides::bootstrap);
		registryBuilder.add(FrozenLibRegistries.CLIP_GROUP, WWClipGroups::bootstrap);
		registryBuilder.add(FrozenLibRegistries.WATER_LIKE_TYPE, WWWaterLikeTypes::bootstrap);
		registryBuilder.add(FrozenLibRegistries.STRUCTURE_MUSIC, WWStructureMusic::bootstrap);
		registryBuilder.add(FrozenLibRegistries.RULE_SOURCE_ADDITION, WWMaterialRules::bootstrap);
		registryBuilder.add(FrozenLibRegistries.PLAYER_DAMAGE_TYPE_SOUND, WWPlayerDamageTypeSounds::bootstrap);
		registryBuilder.add(FrozenLibRegistries.STRUCTURE_PROCESSOR_LIST_ADDITION, WWStructureProcessorListAdditions::bootstrap);
		registryBuilder.add(FrozenLibRegistries.VARIANT_SPAWN_INJECTION, WWVariantSpawnInjections::bootstrap);

		// Wilder Wild Registries
		registryBuilder.add(WilderWildRegistries.FIREFLY_COLOR, FireflyColors::bootstrap);
		registryBuilder.add(WilderWildRegistries.BUTTERFLY_VARIANT, ButterflyVariants::bootstrap);
		registryBuilder.add(WilderWildRegistries.JELLYFISH_VARIANT, JellyfishVariants::bootstrap);
		registryBuilder.add(WilderWildRegistries.CRAB_VARIANT, CrabVariants::bootstrap);
		registryBuilder.add(WilderWildRegistries.MOOBLOOM_VARIANT, MoobloomVariants::bootstrap);
		registryBuilder.add(WilderWildRegistries.TERMITE_BLOCK_BEHAVIOR, TermiteBlockBehaviors::bootstrap);
		registryBuilder.add(WilderWildRegistries.FALLING_LEAF, FallingLeafDatas::bootstrap);
	}

	@Override
	public String getEffectiveModId() {
		return WWConstants.MOD_ID;
	}
}
