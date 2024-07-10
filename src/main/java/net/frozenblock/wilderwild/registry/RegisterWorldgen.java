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

package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.WilderConstants;
import net.frozenblock.wilderwild.world.biome.AridForest;
import net.frozenblock.wilderwild.world.biome.AridSavanna;
import net.frozenblock.wilderwild.world.biome.BirchJungle;
import net.frozenblock.wilderwild.world.biome.BirchTaiga;
import net.frozenblock.wilderwild.world.biome.CypressWetlands;
import net.frozenblock.wilderwild.world.biome.DarkBirchForest;
import net.frozenblock.wilderwild.world.biome.DarkTaiga;
import net.frozenblock.wilderwild.world.biome.DyingForest;
import net.frozenblock.wilderwild.world.biome.DyingMixedForest;
import net.frozenblock.wilderwild.world.biome.FlowerField;
import net.frozenblock.wilderwild.world.biome.FrozenCaves;
import net.frozenblock.wilderwild.world.biome.JellyfishCaves;
import net.frozenblock.wilderwild.world.biome.MagmaticCaves;
import net.frozenblock.wilderwild.world.biome.MixedForest;
import net.frozenblock.wilderwild.world.biome.Oasis;
import net.frozenblock.wilderwild.world.biome.OldGrowthBirchTaiga;
import net.frozenblock.wilderwild.world.biome.OldGrowthDarkForest;
import net.frozenblock.wilderwild.world.biome.ParchedForest;
import net.frozenblock.wilderwild.world.biome.Rainforest;
import net.frozenblock.wilderwild.world.biome.SemiBirchForest;
import net.frozenblock.wilderwild.world.biome.SnowyDyingForest;
import net.frozenblock.wilderwild.world.biome.SnowyDyingMixedForest;
import net.frozenblock.wilderwild.world.biome.SnowyOldGrowthPineTaiga;
import net.frozenblock.wilderwild.world.biome.SparseBirchJungle;
import net.frozenblock.wilderwild.world.biome.TemperateRainforest;
import net.frozenblock.wilderwild.world.biome.WarmBeach;
import net.frozenblock.wilderwild.world.biome.WarmRiver;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

public final class RegisterWorldgen {
	// Main Biomes
	public static final ResourceKey<Biome> CYPRESS_WETLANDS = CypressWetlands.INSTANCE.getKey();
	public static final ResourceKey<Biome> OASIS = Oasis.INSTANCE.getKey();
	public static final ResourceKey<Biome> WARM_RIVER = WarmRiver.INSTANCE.getKey();
	public static final ResourceKey<Biome> WARM_BEACH = WarmBeach.INSTANCE.getKey();
	// Cave Biomes
	public static final ResourceKey<Biome> FROZEN_CAVES = FrozenCaves.INSTANCE.getKey();
	public static final ResourceKey<Biome> JELLYFISH_CAVES = JellyfishCaves.INSTANCE.getKey();
	public static final ResourceKey<Biome> MAGMATIC_CAVES = MagmaticCaves.INSTANCE.getKey();
	// Transition Biomes
	// HOT
	public static final ResourceKey<Biome> ARID_FOREST = AridForest.INSTANCE.getKey();
	public static final ResourceKey<Biome> ARID_SAVANNA = AridSavanna.INSTANCE.getKey();
	public static final ResourceKey<Biome> PARCHED_FOREST = ParchedForest.INSTANCE.getKey();
	// TROPICAL
	public static final ResourceKey<Biome> BIRCH_JUNGLE = BirchJungle.INSTANCE.getKey();
	public static final ResourceKey<Biome> SPARSE_BIRCH_JUNGLE = SparseBirchJungle.INSTANCE.getKey();
	// TEMPERATE
	public static final ResourceKey<Biome> BIRCH_TAIGA = BirchTaiga.INSTANCE.getKey();
	public static final ResourceKey<Biome> SEMI_BIRCH_FOREST = SemiBirchForest.INSTANCE.getKey();
	public static final ResourceKey<Biome> DARK_BIRCH_FOREST = DarkBirchForest.INSTANCE.getKey();
	public static final ResourceKey<Biome> FLOWER_FIELD = FlowerField.INSTANCE.getKey();
	public static final ResourceKey<Biome> TEMPERATE_RAINFOREST = TemperateRainforest.INSTANCE.getKey();
	public static final ResourceKey<Biome> RAINFOREST = Rainforest.INSTANCE.getKey();
	public static final ResourceKey<Biome> DARK_TAIGA = DarkTaiga.INSTANCE.getKey();
	public static final ResourceKey<Biome> MIXED_FOREST = MixedForest.INSTANCE.getKey();
	// COLD
	public static final ResourceKey<Biome> DYING_FOREST = DyingForest.INSTANCE.getKey();
	public static final ResourceKey<Biome> SNOWY_DYING_FOREST = SnowyDyingForest.INSTANCE.getKey();
	public static final ResourceKey<Biome> DYING_MIXED_FOREST = DyingMixedForest.INSTANCE.getKey();
	public static final ResourceKey<Biome> SNOWY_DYING_MIXED_FOREST = SnowyDyingMixedForest.INSTANCE.getKey();
	// OLD GROWTH
	public static final ResourceKey<Biome> OLD_GROWTH_BIRCH_TAIGA = OldGrowthBirchTaiga.INSTANCE.getKey();
	public static final ResourceKey<Biome> OLD_GROWTH_DARK_FOREST = OldGrowthDarkForest.INSTANCE.getKey();
	public static final ResourceKey<Biome> SNOWY_OLD_GROWTH_PINE_TAIGA = SnowyOldGrowthPineTaiga.INSTANCE.getKey();

	private RegisterWorldgen() {
		throw new UnsupportedOperationException("RegisterWorldgen contains only static declarations.");
	}

	public static void init() {

	}

	public static void bootstrap(@NotNull BootstrapContext<Biome> context) {
		WilderConstants.logWithModId("Registering Biomes for", WilderConstants.UNSTABLE_LOGGING);

		// MAIN BIOMES
		register(context, CYPRESS_WETLANDS, CypressWetlands.INSTANCE.create(context));
		register(context, MIXED_FOREST, MixedForest.INSTANCE.create(context));
		register(context, DYING_FOREST, DyingForest.INSTANCE.create(context));
		register(context, SNOWY_DYING_FOREST, SnowyDyingForest.INSTANCE.create(context));
		register(context, DYING_MIXED_FOREST, DyingMixedForest.INSTANCE.create(context));
		register(context, SNOWY_DYING_MIXED_FOREST, SnowyDyingMixedForest.INSTANCE.create(context));
		register(context, OASIS, Oasis.INSTANCE.create(context));
		register(context, WARM_RIVER, WarmRiver.INSTANCE.create(context));
		register(context, WARM_BEACH, WarmBeach.INSTANCE.create(context));
		// CAVE BIOMES
		register(context, FROZEN_CAVES, FrozenCaves.INSTANCE.create(context));
		register(context, JELLYFISH_CAVES, JellyfishCaves.INSTANCE.create(context));
		register(context, MAGMATIC_CAVES, MagmaticCaves.INSTANCE.create(context));
		// TRANSITION BIOMES
		// HOT
		register(context, ARID_FOREST, AridForest.INSTANCE.create(context));
		register(context, ARID_SAVANNA, AridSavanna.INSTANCE.create(context));
		register(context, PARCHED_FOREST, ParchedForest.INSTANCE.create(context));
		// TROPICAL
		register(context, BIRCH_JUNGLE, BirchJungle.INSTANCE.create(context));
		register(context, SPARSE_BIRCH_JUNGLE, SparseBirchJungle.INSTANCE.create(context));
		// TEMPERATE
		register(context, BIRCH_TAIGA, BirchTaiga.INSTANCE.create(context));
		register(context, SEMI_BIRCH_FOREST, SemiBirchForest.INSTANCE.create(context));
		register(context, DARK_BIRCH_FOREST, DarkBirchForest.INSTANCE.create(context));
		register(context, FLOWER_FIELD, FlowerField.INSTANCE.create(context));
		register(context, TEMPERATE_RAINFOREST, TemperateRainforest.INSTANCE.create(context));
		register(context, RAINFOREST, Rainforest.INSTANCE.create(context));
		register(context, DARK_TAIGA, DarkTaiga.INSTANCE.create(context));
		// OLD GROWTH
		register(context, OLD_GROWTH_BIRCH_TAIGA, OldGrowthBirchTaiga.INSTANCE.create(context));
		register(context, OLD_GROWTH_DARK_FOREST, OldGrowthDarkForest.INSTANCE.create(context));
		register(context, SNOWY_OLD_GROWTH_PINE_TAIGA, SnowyOldGrowthPineTaiga.INSTANCE.create(context));
	}

	private static void register(@NotNull BootstrapContext<Biome> entries, @NotNull ResourceKey<Biome> key, @NotNull Biome biome) {
		WilderConstants.log("Registering biome " + key.location(), true);
		entries.register(key, biome);
	}
}
