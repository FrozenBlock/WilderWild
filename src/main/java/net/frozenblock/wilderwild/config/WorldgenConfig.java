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
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.MOD_ID;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.configPath;

public final class WorldgenConfig {

	public static final Config<WorldgenConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			MOD_ID,
			WorldgenConfig.class,
			configPath("worldgen", true),
			JsonType.JSON5,
			null,
			null
		)
	);

	@CollapsibleObject
	public final BiomeGeneration biomeGeneration = new BiomeGeneration();

	@CollapsibleObject
	public final BiomePlacement biomePlacement = new BiomePlacement();

	@CollapsibleObject
	public final WaterColors waterColors = new WaterColors();

	public boolean betaBeaches = true;

	public boolean dyingTrees = true;

	public boolean fallenLogs = true;

	public boolean snappedLogs = true;

	public boolean wilderWildTreeGen = true;

	public boolean wilderWildGrassGen = true;

	public boolean wilderWildFlowerGen = true;

	public boolean wilderWildBushGen = true;

	public boolean wilderWildCactusGen = true;

	public boolean wilderWildMushroomGen = true;

	public boolean tumbleweed = true;

	public boolean algae = true;

	public boolean termiteGen = true;

	public boolean surfaceDecoration = true;

	public boolean snowBelowTrees = true;

	public boolean surfaceTransitions = true;

	public boolean newWitchHuts = true;

	public static WorldgenConfig get() {
		return get(false);
	}

	public static WorldgenConfig get(boolean real) {
		if (real)
			return INSTANCE.instance();
		return INSTANCE.config();
	}

	public static class BiomePlacement {
		public boolean modifyWindsweptSavannaPlacement = true;

		public boolean modifyJunglePlacement = true;

		public boolean modifySwampPlacement = true;

		public boolean modifyMangroveSwampPlacement = true;

		public boolean modifyCherryGrovePlacement = true;

		public boolean modifyStonyShorePlacement = true;
	}

	public static class BiomeGeneration {
		public boolean generateCypressWetlands = true;

		public boolean generateJellyfishCaves = true;

		public boolean generateMixedForest = true;

		public boolean generateOasis = true;

		public boolean generateWarmRiver = true;

		public boolean generateWarmBeach = true;

		public boolean generateBirchTaiga = true;

		public boolean generateOldGrowthBirchTaiga = true;

		public boolean generateFlowerField = true;

		public boolean generateAridSavanna = true;

		public boolean generateParchedForest = true;

		public boolean generateAridForest = true;

		public boolean generateOldGrowthSnowyTaiga = true;

		public boolean generateBirchJungle = true;

		public boolean generateSparseBirchJungle = true;

		public boolean generateOldGrowthDarkForest = true;

		public boolean generateDarkBirchForest = true;

		public boolean generateSemiBirchForest = true;

		public boolean generateTemperateRainforest = true;

		public boolean generateRainforest = true;

		public boolean generateDarkTaiga = true;
	}

	public static class WaterColors {
		public boolean modifyLukewarmWater = true;

		public boolean modifyHotWater = true;

		public boolean modifySnowyWater = true;

		public boolean modifyFrozenWater = true;
	}
}
