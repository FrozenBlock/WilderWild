/*
 * Copyright 2026 FrozenBlock
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
import net.frozenblock.wilderwild.tag.WWPlacedFeatureTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class WWPlacedFeatureTagsProvider extends FabricTagsProvider<PlacedFeature> {

	public WWPlacedFeatureTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, Registries.PLACED_FEATURE, registries);
	}

	@Override
	public void addTags(HolderLookup.Provider arg) {
		this.builder(WWPlacedFeatureTags.FOREST_DEFAULT_FLOWERS)
			.add(VegetationPlacements.FOREST_FLOWERS, VegetationPlacements.FLOWER_DEFAULT);

		this.builder(WWPlacedFeatureTags.WARM_FOREST_DEFAULT_FLOWERS)
			.addTag(WWPlacedFeatureTags.FOREST_DEFAULT_FLOWERS)
			.add(VegetationPlacements.FLOWER_WARM);

		this.builder(WWPlacedFeatureTags.PLAINS_DEFAULT_FLOWERS)
			.add(VegetationPlacements.FLOWER_PLAINS, VegetationPlacements.FLOWER_DEFAULT);

		this.builder(WWPlacedFeatureTags.FLOWER_FOREST_FLOWERS)
			.addTag(WWPlacedFeatureTags.FOREST_DEFAULT_FLOWERS)
			.add(VegetationPlacements.FLOWER_FOREST_FLOWERS, VegetationPlacements.FLOWER_FLOWER_FOREST);
	}
}
