/*
 * Copyright 2023-2025 FrozenBlock
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
import static net.frozenblock.wilderwild.WWConstants.MOD_ID;
import net.frozenblock.wilderwild.WWPreLoadConstants;
import net.frozenblock.wilderwild.worldgen.impl.conditionsource.BetaBeachConditionSource;
import net.frozenblock.wilderwild.worldgen.impl.conditionsource.SnowUnderMountainConditionSource;

public final class WWWorldgenConfig {

	public static final Config<WWWorldgenConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			MOD_ID,
			WWWorldgenConfig.class,
			WWPreLoadConstants.configPath("worldgen", true),
			JsonType.JSON5
		) {
			@Override
			public void onSave() throws Exception {
				super.onSave();
				this.onSync(null);
			}

			@Override
			public void onSync(WWWorldgenConfig sync) {
				var config = this.config();
				BetaBeachConditionSource.GENERATE = config.betaBeaches;
				SnowUnderMountainConditionSource.GENERATE = config.snowUnderMountains;
				GENERATE_POLLEN = config.vegetation.pollen;
				BIRCH_BRANCHES = config.treeGeneration.birchBranches;
				OAK_BRANCHES = config.treeGeneration.oakBranches;
				DARK_OAK_BRANCHES = config.treeGeneration.darkOakBranches;
				PALE_OAK_BRANCHES = config.treeGeneration.paleOakBranches;
				NEW_WITCH_HUTS = config.structure.newWitchHuts;
			}
		}
	);

	public static volatile boolean GENERATE_POLLEN = true;
	public static volatile boolean BIRCH_BRANCHES = true;
	public static volatile boolean OAK_BRANCHES = true;
	public static volatile boolean DARK_OAK_BRANCHES = true;
	public static volatile boolean PALE_OAK_BRANCHES = true;
	public static volatile boolean NEW_WITCH_HUTS = true;

	@CollapsibleObject
	public final BiomeGeneration biomeGeneration = new BiomeGeneration();

	@CollapsibleObject
	public final BiomePlacement biomePlacement = new BiomePlacement();

	@CollapsibleObject
	public final TreeGeneration treeGeneration = new TreeGeneration();

	@CollapsibleObject
	public final Vegetation vegetation = new Vegetation();

	@CollapsibleObject
	public final AquaticGeneration aquaticGeneration = new AquaticGeneration();

	@CollapsibleObject
	public final TransitionGeneration transitionGeneration = new TransitionGeneration();

	@CollapsibleObject
	public final Structure structure = new Structure();

	@CollapsibleObject
	public final SurfaceDecoration surfaceDecoration = new SurfaceDecoration();

	@EntrySyncData("betaBeaches")
	public boolean betaBeaches = true;

	@EntrySyncData("snowUnderMountains")
	public boolean snowUnderMountains = false;

	@EntrySyncData("termiteGen")
	public boolean termiteGen = true;

	@EntrySyncData("netherGeyserGen")
	public boolean netherGeyserGen = true;

	@EntrySyncData("snowBelowTrees")
	public boolean snowBelowTrees = true;

	public static WWWorldgenConfig get() {
		return get(false);
	}

	public static WWWorldgenConfig get(boolean real) {
		if (real)
			return INSTANCE.instance();
		return INSTANCE.config();
	}

	public static WWWorldgenConfig getWithSync() {
		return INSTANCE.configWithSync();
	}

	public static class Vegetation {
		@EntrySyncData("grassGeneration")
		public boolean grassGeneration = true;

		@EntrySyncData("flowerGeneration")
		public boolean flowerGeneration = true;

		@EntrySyncData("shrubGeneration")
		public boolean shrubGeneration = true;

		@EntrySyncData("cactusGeneration")
		public boolean cactusGeneration = true;

		@EntrySyncData("mushroomGeneration")
		public boolean mushroomGeneration = true;

		@EntrySyncData("paleMushroomGeneration")
		public boolean paleMushroomGeneration = true;

		@EntrySyncData("pollen")
		public boolean pollen = true;

		@EntrySyncData("tumbleweed")
		public boolean tumbleweed = true;

		@EntrySyncData("fireflyBushGen")
		public boolean fireflyBushGen = true;

		@EntrySyncData("leafLitterGen")
		public boolean leafLitterGen = true;

		@EntrySyncData("pumpkin")
		public boolean pumpkin = true;
	}

	public static class SurfaceDecoration {
		@EntrySyncData("coarseDecoration")
		public boolean coarseDecoration = true;

		@EntrySyncData("gravelDecoration")
		public boolean gravelDecoration = true;

		@EntrySyncData("mudDecoration")
		public boolean mudDecoration = true;

		@EntrySyncData("packedMudDecoration")
		public boolean packedMudDecoration = true;

		@EntrySyncData("stoneDecoration")
		public boolean stoneDecoration = true;

		@EntrySyncData("mossDecoration")
		public boolean mossDecoration = true;

		@EntrySyncData("redMossDecoration")
		public boolean redMossDecoration = true;

		@EntrySyncData("paleMossDecoration")
		public boolean paleMossDecoration = true;

		@EntrySyncData("scorchedSandDecoration")
		public boolean scorchedSandDecoration = true;

		@EntrySyncData("scorchedRedSandDecoration")
		public boolean scorchedRedSandDecoration = true;

		@EntrySyncData("sandstoneDecoration")
		public boolean sandstoneDecoration = true;

		@EntrySyncData("clayDecoration")
		public boolean clayDecoration = true;

		@EntrySyncData("clearingDecoration")
		public boolean clearingDecoration = true;

		@EntrySyncData("snowPiles")
		public boolean snowPiles = true;

		@EntrySyncData("fragileIceDecoration")
		public boolean fragileIceDecoration = true;

		@EntrySyncData("icicleDecoration")
		public boolean icicleDecoration = true;

		@EntrySyncData("taigaBoulders")
		public boolean taigaBoulders = true;

		@EntrySyncData("lakes")
		public boolean lakes = true;

		@EntrySyncData("basins")
		public boolean basins = true;
	}

	public static class Structure {
		@EntrySyncData("newWitchHuts")
		public boolean newWitchHuts = true;

		@EntrySyncData("decayTrailRuins")
		public boolean decayTrailRuins = true;

		@EntrySyncData("newDesertVillages")
		public boolean newDesertVillages = true;
	}

	public static class TransitionGeneration {
		@EntrySyncData("sandTransitions")
		public boolean sandTransitions = true;

		@EntrySyncData("redSandTransitions")
		public boolean redSandTransitions = true;

		@EntrySyncData("coarseTransitions")
		public boolean coarseTransitions = true;

		@EntrySyncData("gravelTransitions")
		public boolean gravelTransitions = true;

		@EntrySyncData("mudTransitions")
		public boolean mudTransitions = true;

		@EntrySyncData("stoneTransitions")
		public boolean stoneTransitions = true;

		@EntrySyncData("snowTransitions")
		public boolean snowTransitions = true;
	}

	public static class TreeGeneration {
		@EntrySyncData("treeGeneration")
		public boolean treeGeneration = true;

		@EntrySyncData("fallenTrees")
		public boolean fallenTrees = true;

		@EntrySyncData("snappedTrees")
		public boolean snappedTrees = true;

		@EntrySyncData("baobab")
		public boolean baobab = true;

		@EntrySyncData("palm")
		public boolean palm = true;

		@EntrySyncData("willow")
		public boolean willow = true;

		@EntrySyncData("birchBranches")
		public boolean birchBranches = true;

		@EntrySyncData("oakBranches")
		public boolean oakBranches = true;

		@EntrySyncData("darkOakBranches")
		public boolean darkOakBranches = true;

		@EntrySyncData("paleOakBranches")
		public boolean paleOakBranches = true;
	}

	public static class AquaticGeneration {
		@EntrySyncData("riverPool")
		public boolean riverPool = false;

		@EntrySyncData("cattail")
		public boolean cattail = true;

		@EntrySyncData("algae")
		public boolean algae = true;

		@EntrySyncData("plankton")
		public boolean plankton = true;

		@EntrySyncData("seagrass")
		public boolean seagrass = true;

		@EntrySyncData("spongeBud")
		public boolean spongeBud = true;

		@EntrySyncData("barnacle")
		public boolean barnacle = true;

		@EntrySyncData("seaAnemone")
		public boolean seaAnemone = true;

		@EntrySyncData("seaWhip")
		public boolean seaWhip = true;

		@EntrySyncData("tubeWorm")
		public boolean tubeWorm = true;

		@EntrySyncData("hydrothermalVent")
		public boolean hydrothermalVent = true;

		@EntrySyncData("oceanMossGeneration")
		public boolean oceanMossGeneration = true;

		@EntrySyncData("oceanRedMossGeneration")
		public boolean oceanRedMossGeneration = true;
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

		@EntrySyncData("modifyTundraPlacement")
		public boolean modifyTundraPlacement = false;
	}

	public static class BiomeGeneration {
		@EntrySyncData("generateCypressWetlands")
		public boolean generateCypressWetlands = true;

		@EntrySyncData("generateMesogleaCaves")
		public boolean generateMesogleaCaves = true;

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

		@EntrySyncData("generateMapleForest")
		public boolean generateMapleForest = true;

		@EntrySyncData("generateSparseForest")
		public boolean generateSparseForest = true;

		@EntrySyncData("generateTundra")
		public boolean generateTundra = true;
	}
}
