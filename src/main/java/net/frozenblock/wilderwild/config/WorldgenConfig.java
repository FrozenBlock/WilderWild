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

package net.frozenblock.wilderwild.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.api.instance.json.JsonConfig;
import net.frozenblock.lib.config.api.instance.json.JsonType;
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.lib.config.api.sync.annotation.EntrySyncData;
import static net.frozenblock.wilderwild.WilderConstants.MOD_ID;
import static net.frozenblock.wilderwild.WilderConstants.configPath;
import net.frozenblock.wilderwild.world.impl.conditionsource.BetaBeachConditionSource;
import net.frozenblock.wilderwild.world.impl.conditionsource.SnowUnderMountainConditionSource;

public final class WorldgenConfig {

	public static final Config<WorldgenConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			MOD_ID,
			WorldgenConfig.class,
			configPath("worldgen", true),
			JsonType.JSON5,
			null,
			null
		) {
			@Override
			public void onSave() throws Exception {
				super.onSave();
				this.onSync(null);
			}

			@Override
			public void onSync(WorldgenConfig sync) {
				var config = this.config();
				BetaBeachConditionSource.GENERATE = config.betaBeaches;
				SnowUnderMountainConditionSource.GENERATE = config.snowUnderMountains;
			}
		}
	);

	@CollapsibleObject
	public final BiomeGeneration biomeGeneration = new BiomeGeneration();

	@CollapsibleObject
	public final BiomePlacement biomePlacement = new BiomePlacement();

	@EntrySyncData("betaBeaches")
	public boolean betaBeaches = true;

	@EntrySyncData("snowUnderMountains")
	public boolean snowUnderMountains = true;

	@EntrySyncData("fallenTrees")
	public boolean fallenTrees = true;

	@EntrySyncData("snappedTrees")
	public boolean snappedTrees = true;

	@EntrySyncData("treeGeneration")
	public boolean treeGeneration = true;

	@EntrySyncData("grassGeneration")
	public boolean grassGeneration = true;

	@EntrySyncData("flowerGeneration")
	public boolean flowerGeneration = true;

	@EntrySyncData("bushGeneration")
	public boolean bushGeneration = true;

	@EntrySyncData("cactusGeneration")
	public boolean cactusGeneration = true;

	@EntrySyncData("mushroomGeneration")
	public boolean mushroomGeneration = true;

	@EntrySyncData("tumbleweed")
	public boolean tumbleweed = true;

	@EntrySyncData("algae")
	public boolean algae = true;

	@EntrySyncData("termiteGen")
	public boolean termiteGen = true;

	@EntrySyncData("netherGeyserGen")
	public boolean netherGeyserGen = true;

	@EntrySyncData("surfaceDecoration")
	public boolean surfaceDecoration = true;

	@EntrySyncData("snowBelowTrees")
	public boolean snowBelowTrees = true;

	@EntrySyncData("surfaceTransitions")
	public boolean surfaceTransitions = true;

	@EntrySyncData("newWitchHuts")
	public boolean newWitchHuts = true;

	@EntrySyncData("decayTrailRuins")
	public boolean decayTrailRuins = true;

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
		@EntrySyncData("modifyWindsweptSavannaPlacement")
		public boolean modifyWindsweptSavannaPlacement = true;

		@EntrySyncData("modifyJunglePlacement")
		public boolean modifyJunglePlacement = true;

		@EntrySyncData("modifySwampPlacement")
		public boolean modifySwampPlacement = true;

		@EntrySyncData("modifyMangroveSwampPlacement")
		public boolean modifyMangroveSwampPlacement = true;

		@EntrySyncData("modifyCherryGrovePlacement")
		public boolean modifyCherryGrovePlacement = false;

		@EntrySyncData("modifyStonyShorePlacement")
		public boolean modifyStonyShorePlacement = true;
	}

	public static class BiomeGeneration {
		@EntrySyncData("generateCypressWetlands")
		public boolean generateCypressWetlands = true;

		@EntrySyncData("generateJellyfishCaves")
		public boolean generateJellyfishCaves = true;

		@EntrySyncData("generateMixedForest")
		public boolean generateMixedForest = true;

		@EntrySyncData("generateOasis")
		public boolean generateOasis = true;

		@EntrySyncData("generateWarmRiver")
		public boolean generateWarmRiver = true;

		@EntrySyncData("generateWarmBeach")
		public boolean generateWarmBeach = true;

		@EntrySyncData("generateBirchTaiga")
		public boolean generateBirchTaiga = true;

		@EntrySyncData("generateOldGrowthBirchTaiga")
		public boolean generateOldGrowthBirchTaiga = true;

		@EntrySyncData("generateFlowerField")
		public boolean generateFlowerField = true;

		@EntrySyncData("generateAridSavanna")
		public boolean generateAridSavanna = true;

		@EntrySyncData("generateParchedForest")
		public boolean generateParchedForest = true;

		@EntrySyncData("generateAridForest")
		public boolean generateAridForest = true;

		@EntrySyncData("generateOldGrowthSnowyTaiga")
		public boolean generateOldGrowthSnowyTaiga = true;

		@EntrySyncData("generateBirchJungle")
		public boolean generateBirchJungle = true;

		@EntrySyncData("generateSparseBirchJungle")
		public boolean generateSparseBirchJungle = true;

		@EntrySyncData("generateOldGrowthDarkForest")
		public boolean generateOldGrowthDarkForest = true;

		@EntrySyncData("generateDarkBirchForest")
		public boolean generateDarkBirchForest = true;

		@EntrySyncData("generateSemiBirchForest")
		public boolean generateSemiBirchForest = true;

		@EntrySyncData("generateTemperateRainforest")
		public boolean generateTemperateRainforest = true;

		@EntrySyncData("generateRainforest")
		public boolean generateRainforest = true;

		@EntrySyncData("generateDarkTaiga")
		public boolean generateDarkTaiga = true;

		@EntrySyncData("generateDyingForest")
		public boolean generateDyingForest = true;

		@EntrySyncData("generateSnowyDyingForest")
		public boolean generateSnowyDyingForest = true;

		@EntrySyncData("generateDyingMixedForest")
		public boolean generateDyingMixedForest = true;

		@EntrySyncData("generateSnowyDyingMixedForest")
		public boolean generateSnowyDyingMixedForest = true;

		@EntrySyncData("generateMagmaticCaves")
		public boolean generateMagmaticCaves = true;

		@EntrySyncData("generateFrozenCaves")
		public boolean generateFrozenCaves = true;
	}
}
