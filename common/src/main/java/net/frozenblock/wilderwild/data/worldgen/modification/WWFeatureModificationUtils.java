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

package net.frozenblock.wilderwild.data.worldgen.modification;

import net.frozenblock.lib.levelgen.biome.api.modifications.ModificationPhase;
import net.frozenblock.lib.levelgen.biome.api.modifications.data.BiomeModification;
import net.frozenblock.lib.levelgen.biome.api.modifications.data.FeatureModification;
import net.frozenblock.lib.levelgen.biome.api.modifications.data.selector.BiomeSelector;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibPlacedFeature;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import java.util.Arrays;

public final class WWFeatureModificationUtils {

	public static void registerVegetalAddition(
		BootstrapContext<BiomeModification> context,
		TagKey<Biome> biomes,
		FrozenLibPlacedFeature... features
	) {
		registerAddition(context, biomes, GenerationStep.Decoration.VEGETAL_DECORATION, features);
	}

	public static void registerLakeAddition(
		BootstrapContext<BiomeModification> context,
		TagKey<Biome> biomes,
		FrozenLibPlacedFeature... features
	) {
		registerAddition(context, biomes, GenerationStep.Decoration.LAKES, features);
	}

	public static void registerFluidSpringAddition(
		BootstrapContext<BiomeModification> context,
		TagKey<Biome> biomes,
		FrozenLibPlacedFeature... features
	) {
		registerAddition(context, biomes, GenerationStep.Decoration.FLUID_SPRINGS, features);
	}

	public static void registerAddition(
		BootstrapContext<BiomeModification> context,
		TagKey<Biome> biomes,
		GenerationStep.Decoration step,
		FrozenLibPlacedFeature... features
	) {
		context.register(
			WWFeatureModificationUtils.createKey(biomes),
			FeatureModification.forAddition(
				Holder.direct(BiomeSelector.inBiomeTag(context, biomes)),
				ModificationPhase.ADDITIONS,
				step,
				Arrays.stream(features).map(FrozenLibPlacedFeature::getKey).toList()
			)
		);
	}

	public static ResourceKey<BiomeModification> createKey(TagKey<Biome> biomeTag) {
		return ResourceKey.create(FrozenLibRegistries.BIOME_MODIFICATION, WWConstants.id(biomeTag.location().getPath()));
	}

	private WWFeatureModificationUtils() {}
}
