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
import net.frozenblock.lib.config.api.annotation.FieldIdentifier;
import net.frozenblock.lib.config.api.annotation.UnsyncableEntry;
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

	@FieldIdentifier(identifier = "betaBeaches")
	public boolean betaBeaches = true;

	@FieldIdentifier(identifier = "dyingTrees")
	public boolean dyingTrees = true;

	@FieldIdentifier(identifier = "fallenLogs")
	public boolean fallenLogs = true;


	@FieldIdentifier(identifier = "snappedLogs")
	public boolean snappedLogs = true;

	@FieldIdentifier(identifier = "wilderWildTreeGen")
	public boolean wilderWildTreeGen = true;

	@FieldIdentifier(identifier = "wilderWildGrassGen")
	public boolean wilderWildGrassGen = true;

	@FieldIdentifier(identifier = "wilderWildFlowerGen")
	public boolean wilderWildFlowerGen = true;

	@FieldIdentifier(identifier = "wilderWildBushGen")
	public boolean wilderWildBushGen = true;

	@FieldIdentifier(identifier = "wilderWildCactusGen")
	public boolean wilderWildCactusGen = true;

	@FieldIdentifier(identifier = "wilderWildMushroomGen")
	public boolean wilderWildMushroomGen = true;

	@FieldIdentifier(identifier = "tumbleweed")
	public boolean tumbleweed = true;

	@FieldIdentifier(identifier = "algae")
	public boolean algae = true;

	@FieldIdentifier(identifier = "termiteGen")
	public boolean termiteGen = true;

	@FieldIdentifier(identifier = "surfaceDecoration")
	public boolean surfaceDecoration = true;

	@FieldIdentifier(identifier = "snowBelowTrees")
	public boolean snowBelowTrees = true;

	@FieldIdentifier(identifier = "surfaceTransitions")
	public boolean surfaceTransitions = true;

	@FieldIdentifier(identifier = "newWitchHuts")
	public boolean newWitchHuts = true;

	public static WorldgenConfig get() {
		return get(false);
	}

	public static WorldgenConfig get(boolean real) {
		if (real)
			return INSTANCE.instance();
		return INSTANCE.config();
	}

	public static WorldgenConfig getWithSync() {
		return INSTANCE.configWithSync();
	}

	public static class BiomePlacement {
		@FieldIdentifier(identifier = "modifyWindsweptSavannaPlacement")
		public boolean modifyWindsweptSavannaPlacement = true;

		@FieldIdentifier(identifier = "modifyJunglePlacement")
		public boolean modifyJunglePlacement = true;

		@FieldIdentifier(identifier = "modifySwampPlacement")
		public boolean modifySwampPlacement = true;

		@FieldIdentifier(identifier = "modifyMangroveSwampPlacement")
		public boolean modifyMangroveSwampPlacement = true;

		@FieldIdentifier(identifier = "modifyCherryGrovePlacement")
		public boolean modifyCherryGrovePlacement = true;

		@FieldIdentifier(identifier = "modifyStonyShorePlacement")
		public boolean modifyStonyShorePlacement = true;
	}

	public static class BiomeGeneration {
		@FieldIdentifier(identifier = "generateCypressWetlands")
		public boolean generateCypressWetlands = true;

		@FieldIdentifier(identifier = "generateJellyfishCaves")
		public boolean generateJellyfishCaves = true;

		@FieldIdentifier(identifier = "generateMixedForest")
		public boolean generateMixedForest = true;

		@FieldIdentifier(identifier = "generateOasis")
		public boolean generateOasis = true;

		@FieldIdentifier(identifier = "generateWarmRiver")
		public boolean generateWarmRiver = true;

		@FieldIdentifier(identifier = "generateWarmBeach")
		public boolean generateWarmBeach = true;

		@FieldIdentifier(identifier = "generateBirchTaiga")
		public boolean generateBirchTaiga = true;

		@FieldIdentifier(identifier = "generateOldGrowthBirchTaiga")
		public boolean generateOldGrowthBirchTaiga = true;

		@FieldIdentifier(identifier = "generateFlowerField")
		public boolean generateFlowerField = true;

		@FieldIdentifier(identifier = "generateAridSavanna")
		public boolean generateAridSavanna = true;

		@FieldIdentifier(identifier = "generateParchedForest")
		public boolean generateParchedForest = true;

		@FieldIdentifier(identifier = "generateAridForest")
		public boolean generateAridForest = true;

		@FieldIdentifier(identifier = "generateOldGrowthSnowyTaiga")
		public boolean generateOldGrowthSnowyTaiga = true;

		@FieldIdentifier(identifier = "generateBirchJungle")
		public boolean generateBirchJungle = true;

		@FieldIdentifier(identifier = "generateSparseBirchJungle")
		public boolean generateSparseBirchJungle = true;

		@FieldIdentifier(identifier = "generateOldGrowthDarkForest")
		public boolean generateOldGrowthDarkForest = true;

		@FieldIdentifier(identifier = "generateDarkBirchForest")
		public boolean generateDarkBirchForest = true;

		@FieldIdentifier(identifier = "generateSemiBirchForest")
		public boolean generateSemiBirchForest = true;

		@FieldIdentifier(identifier = "generateTemperateRainforest")
		public boolean generateTemperateRainforest = true;

		@FieldIdentifier(identifier = "generateRainforest")
		public boolean generateRainforest = true;

		@FieldIdentifier(identifier = "generateDarkTaiga")
		public boolean generateDarkTaiga = true;
	}

	public static class WaterColors {
		@UnsyncableEntry
		public boolean modifyLukewarmWater = true;

		@UnsyncableEntry
		public boolean modifyHotWater = true;

		@UnsyncableEntry
		public boolean modifySnowyWater = true;

		@UnsyncableEntry
		public boolean modifyFrozenWater = true;
	}
}
