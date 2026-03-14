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

package net.frozenblock.wilderwild.datagen.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.frozenblock.wilderwild.worldgen.features.configured.WWConfiguredFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.FeatureTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public final class WWFeatureTagProvider extends FabricTagsProvider<ConfiguredFeature<?, ?>> {

	public WWFeatureTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, Registries.CONFIGURED_FEATURE, registries);
	}

	@Override
	public void addTags(HolderLookup.Provider arg) {
		this.builder(FeatureTags.CAN_SPAWN_FROM_BONE_MEAL)
			.add(WWConfiguredFeatures.CLOVER.getKey())
			.add(WWConfiguredFeatures.PHLOX.getKey())
			.add(WWConfiguredFeatures.LANTANAS.getKey())
			.add(WWConfiguredFeatures.WILDFLOWERS.getKey())
			.add(WWConfiguredFeatures.WILDFLOWERS_AND_PHLOX.getKey())
			.add(WWConfiguredFeatures.WILDFLOWERS_AND_LANTANAS.getKey())
			.add(WWConfiguredFeatures.LANTANAS_AND_PHLOX.getKey())
			.add(WWConfiguredFeatures.SEEDING_DANDELION.getKey())
			.add(WWConfiguredFeatures.CARNATION.getKey())
			.add(WWConfiguredFeatures.MARIGOLD.getKey())
			.add(WWConfiguredFeatures.EYEBLOSSOM.getKey())
			.add(WWConfiguredFeatures.PINK_TULIP.getKey())
			.add(WWConfiguredFeatures.ALLIUM.getKey())
			.add(WWConfiguredFeatures.DATURA.getKey())
			.add(WWConfiguredFeatures.ROSE_BUSH.getKey())
			.add(WWConfiguredFeatures.PEONY.getKey())
			.add(WWConfiguredFeatures.LILAC.getKey())
			.add(WWConfiguredFeatures.FLOWER_GENERIC.getKey())
			.add(WWConfiguredFeatures.FLOWER_GENERIC_NO_CARNATION.getKey())
			.add(WWConfiguredFeatures.FLOWER_PLAINS.getKey())
			.add(WWConfiguredFeatures.FLOWER_SNOWY_PLAINS.getKey())
			.add(WWConfiguredFeatures.FLOWER_TUNDRA.getKey())
			.add(WWConfiguredFeatures.FLOWER_BIRCH.getKey())
			.add(WWConfiguredFeatures.FLOWER_MEADOW.getKey())
			.add(WWConfiguredFeatures.MILKWEED.getKey())
			.add(WWConfiguredFeatures.MILKWEED_SWAMP.getKey())
			.add(WWConfiguredFeatures.HIBISCUS.getKey())
			.add(WWConfiguredFeatures.HIBISCUS_JUNGLE.getKey())
			.add(WWConfiguredFeatures.FLOWER_FLOWER_FIELD.getKey())
			.add(WWConfiguredFeatures.FLOWER_CYPRESS_WETLANDS.getKey())
			.add(WWConfiguredFeatures.TALL_FLOWER_CYPRESS_WETLANDS.getKey())
			.add(WWConfiguredFeatures.FLOWER_TEMPERATE_RAINFOREST.getKey())
			.add(WWConfiguredFeatures.TALL_FLOWER_TEMPERATE_RAINFOREST.getKey())
			.add(WWConfiguredFeatures.FLOWER_TEMPERATE_RAINFOREST_VANILLA.getKey())
			.add(WWConfiguredFeatures.TALL_FLOWER_TEMPERATE_RAINFOREST_VANILLA.getKey())
			.add(WWConfiguredFeatures.FLOWER_RAINFOREST.getKey())
			.add(WWConfiguredFeatures.TALL_FLOWER_RAINFOREST.getKey())
			.add(WWConfiguredFeatures.FLOWER_RAINFOREST_VANILLA.getKey())
			.add(WWConfiguredFeatures.TALL_FLOWER_RAINFOREST_VANILLA.getKey())
			.add(WWConfiguredFeatures.FLOWER_JUNGLE.getKey())
			.add(WWConfiguredFeatures.TALL_FLOWER_JUNGLE.getKey())
			.add(WWConfiguredFeatures.TALL_FLOWER_FLOWER_FIELD.getKey())
			.add(WWConfiguredFeatures.FLOWER_CHERRY.getKey())
			.add(WWConfiguredFeatures.FLOWER_SUNFLOWER_PLAINS.getKey())
			.add(WWConfiguredFeatures.FLOWER_BIRCH_CLEARING.getKey())
			.add(WWConfiguredFeatures.FLOWER_FOREST_CLEARING.getKey());
	}
}
