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

package net.frozenblock.wilderwild.data.tag;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.frozenblock.wilderwild.data.worldgen.features.WWVegetationFeatures;
import net.frozenblock.wilderwild.data.worldgen.features.WWFlowerFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.FeatureTags;
import net.minecraft.world.level.levelgen.feature.Feature;

public final class WWFeatureTagsProvider extends FabricTagsProvider<Feature> {

	public WWFeatureTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, Registries.FEATURE, registries);
	}

	@Override
	public void addTags(HolderLookup.Provider arg) {
		this.builder(FeatureTags.CAN_SPAWN_FROM_BONE_MEAL)
			.add(WWVegetationFeatures.CLOVER.getKey())
			.add(WWFlowerFeatures.ALLIUM.getKey())
			.add(WWFlowerFeatures.AZURE_BLUET.getKey())
			.add(WWFlowerFeatures.BLUE_ORCHID.getKey())
			.add(WWFlowerFeatures.CARNATION.getKey())
			.add(WWFlowerFeatures.DANDELION.getKey())
			.add(WWFlowerFeatures.EYEBLOSSOM.getKey())
			.add(WWFlowerFeatures.LILY_OF_THE_VALLEY.getKey())
			.add(WWFlowerFeatures.MARIGOLD.getKey())
			.add(WWFlowerFeatures.POPPY.getKey())
			.add(WWFlowerFeatures.SEEDING_DANDELION.getKey())

			.add(WWFlowerFeatures.HIBISCUS.getKey())
			.add(WWFlowerFeatures.POPPY_AND_PASQUEFLOWER.getKey())
			.add(WWFlowerFeatures.MARIGOLD_AND_PASQUEFLOWER.getKey())
			.add(WWFlowerFeatures.MARIGOLD_AND_SEEDING_DANDELION.getKey())
			.add(WWFlowerFeatures.PASQUEFLOWER_AND_POPPY.getKey())
			.add(WWFlowerFeatures.AZURE_BLUET_AND_SEEDING_DANDELION.getKey())
			.add(WWFlowerFeatures.POPPY_AND_MARIGOLD.getKey())

			.add(WWFlowerFeatures.LANTANAS.getKey())
			.add(WWFlowerFeatures.PHLOX.getKey())
			.add(WWFlowerFeatures.WILDFLOWERS.getKey())

			.add(WWFlowerFeatures.FLOWER_DEFAULT_WITH_SEEDING_DANDELION.getKey())

			.add(WWFlowerFeatures.FLOWER_DEFAULT_PLAINS.getKey())
			.add(WWFlowerFeatures.FLOWER_PLAINS.getKey())
			.add(WWFlowerFeatures.FLOWER_DEFAULT_SNOWY_PLAINS.getKey())

			.add(WWFlowerFeatures.SEEDING_DANDELION_AND_DANDELION_SUNFLOWER_PLAINS.getKey())
			.add(WWFlowerFeatures.FLOWER_SUNFLOWER_PLAINS.getKey())

			.add(WWFlowerFeatures.FLOWER_FLOWER_FIELD_RED.getKey())
			.add(WWFlowerFeatures.FLOWER_FLOWER_FIELD_PINK.getKey())
			.add(WWFlowerFeatures.FLOWER_FLOWER_FIELD_ORANGE.getKey())
			.add(WWFlowerFeatures.FLOWER_FLOWER_FIELD_YELLOW.getKey())
			.add(WWFlowerFeatures.FLOWER_FLOWER_FIELD_WHITE.getKey())
			.add(WWFlowerFeatures.FLOWER_FLOWER_FIELD_BLUE.getKey())
			.add(WWFlowerFeatures.FLOWER_FLOWER_FIELD_PURPLE.getKey())

			.add(WWFlowerFeatures.FLOWER_FOREST.getKey())
			.add(WWFlowerFeatures.FLOWER_RAINFOREST.getKey())
			.add(WWFlowerFeatures.FLOWER_FLOWER_FOREST_1.getKey())
			.add(WWFlowerFeatures.FLOWER_FLOWER_FOREST_2.getKey())

			.add(WWFlowerFeatures.FLOWER_DARK_FOREST.getKey())
			.add(WWFlowerFeatures.FLOWER_DARK_TAIGA.getKey())

			.add(WWFlowerFeatures.FLOWER_JUNGLE.getKey())

			.add(WWFlowerFeatures.FLOWER_CHERRY_GROVE_1.getKey())
			.add(WWFlowerFeatures.FLOWER_CHERRY_GROVE_2.getKey())

			.add(WWFlowerFeatures.FLOWER_MAPLE_FOREST.getKey())

			.add(WWFlowerFeatures.FLOWER_MEADOW.getKey())

			.add(WWFlowerFeatures.FLOWER_TEMPERATE_RAINFOREST.getKey());
	}
}
