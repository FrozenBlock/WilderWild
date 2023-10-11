/*
 * Copyright 2023 FrozenBlock
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

package net.frozenblock.wilderwild.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.api.instance.json.JsonConfig;
import net.frozenblock.lib.config.api.instance.json.JsonType;
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.wilderwild.config.defaults.DefaultWorldgenConfig;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.MOD_ID;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.configPath;

public final class WorldgenConfig {

	private static final Config<WorldgenConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			MOD_ID,
			WorldgenConfig.class,
			configPath("worldgen", true),
			JsonType.JSON5
		)
	);

	@CollapsibleObject
	public final BiomeGeneration biomeGeneration = new BiomeGeneration();

	@CollapsibleObject
	public final BiomePlacement biomePlacement = new BiomePlacement();

	@CollapsibleObject
	public final WaterColors waterColors = new WaterColors();
	public boolean betaBeaches = DefaultWorldgenConfig.BETA_BEACHES;
	public boolean dyingTrees = DefaultWorldgenConfig.DYING_TREES;
	public boolean fallenLogs = DefaultWorldgenConfig.FALLEN_LOGS;
	public boolean snappedLogs = DefaultWorldgenConfig.SNAPPED_LOGS;
	public boolean wilderWildTreeGen = DefaultWorldgenConfig.WILDER_WILD_TREE_GEN;
	public boolean wilderWildGrassGen = DefaultWorldgenConfig.WILDER_WILD_GRASS_GEN;
	public boolean wilderWildFlowerGen = DefaultWorldgenConfig.WILDER_WILD_FLOWER_GEN;
	public boolean wilderWildBushGen = DefaultWorldgenConfig.WILDER_WILD_BUSH_GEN;
	public boolean wilderWildCactusGen = DefaultWorldgenConfig.WILDER_WILD_CACTUS_GEN;
	public boolean wilderWildMushroomGen = DefaultWorldgenConfig.WILDER_WILD_MUSHROOM_GEN;
	public boolean tumbleweed = DefaultWorldgenConfig.TUMBLEWEED_GEN;
	public boolean algae = DefaultWorldgenConfig.ALGAE_GEN;
	public boolean termiteGen = DefaultWorldgenConfig.TERMITE_GEN;
	public boolean surfaceDecoration = DefaultWorldgenConfig.SURFACE_DECORATION;
	public boolean snowBelowTrees = DefaultWorldgenConfig.SNOW_BELOW_TREES;
	public boolean surfaceTransitions = DefaultWorldgenConfig.SURFACE_TRANSITIONS;
	public boolean newWitchHuts = DefaultWorldgenConfig.NEW_WITCH_HUTS;

	public static WorldgenConfig get() {
		return INSTANCE.config();
	}

	public static Config<WorldgenConfig> getConfigInstance() {
		return INSTANCE;
	}

	public static class BiomePlacement {
		public boolean modifyWindsweptSavannaPlacement = DefaultWorldgenConfig.BiomePlacement.MODIFY_WINDSWEPT_SAVANNA_PLACEMENT;
		public boolean modifyJunglePlacement = DefaultWorldgenConfig.BiomePlacement.MODIFY_JUNGLE_PLACEMENT;
		public boolean modifySwampPlacement = DefaultWorldgenConfig.BiomePlacement.MODIFY_SWAMP_PLACEMENT;
		public boolean modifyMangroveSwampPlacement = DefaultWorldgenConfig.BiomePlacement.MODIFY_MANGROVE_SWAMP_PLACEMENT;
		public boolean modifyCherryGrovePlacement = DefaultWorldgenConfig.BiomePlacement.MODIFY_CHERRY_GROVE_PLACEMENT;
		public boolean modifyStonyShorePlacement = DefaultWorldgenConfig.BiomePlacement.MODIFY_STONY_SHORE_PLACEMENT;
	}

	public static class BiomeGeneration {
		public boolean generateCypressWetlands = DefaultWorldgenConfig.BiomeGeneration.GENERATE_CYPRESS_WETLANDS;
		public boolean generateJellyfishCaves = DefaultWorldgenConfig.BiomeGeneration.GENERATE_JELLYFISH_CAVES;
		public boolean generateMixedForest = DefaultWorldgenConfig.BiomeGeneration.GENERATE_MIXED_FOREST;
		public boolean generateOasis = DefaultWorldgenConfig.BiomeGeneration.GENERATE_OASIS;
		public boolean generateWarmRiver = DefaultWorldgenConfig.BiomeGeneration.GENERATE_WARM_RIVER;
		public boolean generateWarmBeach = DefaultWorldgenConfig.BiomeGeneration.GENERATE_WARM_BEACH;
		public boolean generateBirchTaiga = DefaultWorldgenConfig.BiomeGeneration.GENERATE_BIRCH_TAIGA;
		public boolean generateOldGrowthBirchTaiga = DefaultWorldgenConfig.BiomeGeneration.GENERATE_OLD_GROWTH_BIRCH_TAIGA;
		public boolean generateFlowerField = DefaultWorldgenConfig.BiomeGeneration.GENERATE_FLOWER_FIELD;
		public boolean generateAridSavanna = DefaultWorldgenConfig.BiomeGeneration.GENERATE_ARID_SAVANNA;
		public boolean generateParchedForest = DefaultWorldgenConfig.BiomeGeneration.GENERATE_PARCHED_FOREST;
		public boolean generateAridForest = DefaultWorldgenConfig.BiomeGeneration.GENERATE_ARID_FOREST;
		public boolean generateOldGrowthSnowyTaiga = DefaultWorldgenConfig.BiomeGeneration.GENERATE_OLD_GROWTH_SNOWY_TAIGA;
		public boolean generateBirchJungle = DefaultWorldgenConfig.BiomeGeneration.GENERATE_BIRCH_JUNGLE;
		public boolean generateSparseBirchJungle = DefaultWorldgenConfig.BiomeGeneration.GENERATE_SPARSE_BIRCH_JUNGLE;
		public boolean generateOldGrowthDarkForest = DefaultWorldgenConfig.BiomeGeneration.GENERATE_OLD_GROWTH_DARK_FOREST;
		public boolean generateDarkBirchForest = DefaultWorldgenConfig.BiomeGeneration.GENERATE_DARK_BIRCH_FOREST;
		public boolean generateSemiBirchForest = DefaultWorldgenConfig.BiomeGeneration.GENERATE_SEMI_BIRCH_FOREST;
		public boolean generateTemperateRainforest = DefaultWorldgenConfig.BiomeGeneration.GENERATE_TEMPERATE_RAINFOREST;
		public boolean generateRainforest = DefaultWorldgenConfig.BiomeGeneration.GENERATE_RAINFOREST;
		public boolean generateDarkTaiga = DefaultWorldgenConfig.BiomeGeneration.GENERATE_DARK_TAIGA;
	}

	public static class WaterColors {
		public boolean modifyLukewarmWater = DefaultWorldgenConfig.WaterColors.LUKEWARM_BIOMES;
		public boolean modifyHotWater = DefaultWorldgenConfig.WaterColors.HOT_BIOMES;
		public boolean modifySnowyWater = DefaultWorldgenConfig.WaterColors.SNOWY_BIOMES;
		public boolean modifyFrozenWater = DefaultWorldgenConfig.WaterColors.FROZEN_BIOMES;
	}
}
