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

package net.frozenblock.wilderwild.config;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.frozenblock.lib.FrozenBools;
import net.frozenblock.lib.config.api.sync.SyncBehavior;
import net.frozenblock.lib.config.v2.config.ConfigData;
import net.frozenblock.lib.config.v2.config.ConfigSettings;
import net.frozenblock.lib.config.v2.entry.ConfigEntry;
import net.frozenblock.lib.config.v2.entry.EntryType;
import net.frozenblock.lib.config.v2.registry.ID;
import net.frozenblock.wilderwild.WWConstants;

public final class WWBlockConfig {
	public static final ConfigData<?> CONFIG = ConfigData.createAndRegister(ID.of(WWConstants.id("block")), ConfigSettings.JSON5);

	public static final ConfigEntry<Boolean> REACH_BOOST_BEACON = CONFIG.entryBuilder("reachBoostBeacon", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> POLLEN_PARTICLES = CONFIG.unsyncableEntry("pollenParticles", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> LOG_HOLLOWING = CONFIG.entry("logHollowing", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> CACTUS_PLACEMENT = CONFIG.entry("cactusPlacement", EntryType.BOOL, false);
	public static final ConfigEntry<Boolean> NEW_REINFORCED_DEEPSLATE = CONFIG.entry("newReinforcedDeepslate", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> FROGLIGHT_GOOP_GROWTH = CONFIG.entry("froglightGoopGrowth", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> FROSTED_ICE_CRACKING = CONFIG.entry("frostedIceCracking", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> CHEST_BUBBLING = CONFIG.entry("chestBubbling", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> THICK_BIG_FUNGUS_GROWTH = CONFIG.entry("thickBigFungusGrowth", EntryType.BOOL, true);

	//SCULK
	public static final ConfigEntry<Boolean> SHRIEKER_GARGLING = CONFIG.unsyncableEntry("sculk/shriekerGargling", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> SHRIEKER_OUTLINE = CONFIG.unsyncableEntry("sculk/shriekerOutline", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> BILLBOARD_TENDRILS = CONFIG.unsyncableEntry("sculk/billboardTendrils", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> TENDRILS_CARRY_EVENTS = CONFIG.entry("sculk/tendrilsCarryEvents", EntryType.BOOL, false);
	public static final ConfigEntry<Boolean> TENDRIL_GENERATION = CONFIG.entry("sculk/tendrilGeneration", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> OSSEOUS_SCULK_GENERATION = CONFIG.entry("sculk/osseousSculkGeneration", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> SCULK_BUILDING_BLOCKS_GENERATION = CONFIG.entry("sculk/sculkBuildingBlocksGeneration", EntryType.BOOL, true);

	//MESOGLEA
	public static final ConfigEntry<Boolean> MESOGLEA_RENDERS_AS_FLUID = CONFIG.unsyncableEntry("mesoglea/mesogleaFluid", EntryType.BOOL, false);
	public static final ConfigEntry<Boolean> MESOGLEA_BUBBLE_COLUMNS = CONFIG.entry("mesoglea/mesogleaBubbleColumns", EntryType.BOOL, true);

	// TERMITE
	public static final ConfigEntry<Boolean> TERMITE_ONLY_EATS_NATURAL_BLOCKS = CONFIG.entryBuilder("termite/onlyEatNaturalBlocks", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Integer> TERMITE_MAX_DISTANCE = CONFIG.entry("termite/maxDistance", EntryType.INT, 32);
	public static final ConfigEntry<Integer> TERMITE_MAX_NATURAL_DISTANCE = CONFIG.entry("termite/maxNaturalDistance", EntryType.INT, 10);

	// FLOWER
	public static final ConfigEntry<Boolean> BONE_MEAL_DANDELIONS = CONFIG.entry("flower/bonemealDandelions", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> SHEAR_SEEDING_DANDELIONS = CONFIG.entry("flower/shearSeedingDandelions", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> BONE_MEAL_LILY_PADS = CONFIG.entry("flower/bonemealLilypads", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> SHEAR_FLOWERING_LILY_PADS = CONFIG.entry("flower/shearFloweringLilypads", EntryType.BOOL, true);

	// STONE CHEST
	public static final ConfigEntry<Integer> STONE_CHEST_TIMER = CONFIG.entry("stoneChest/stoneChestTimer", EntryType.INT, 100);
	public static final ConfigEntry<Boolean> ADD_STONE_CHESTS = CONFIG.entry("stoneChest/addStoneChests", EntryType.BOOL, true);

	@Override
	public void onSync(WWBlockConfig syncInstance) {
		final var config = this.config();
		MESOGLEA_BUBBLE_COLUMNS = config.mesoglea.mesogleaBubbleColumns;
		FIRE_MAGMA_PARTICLES = config.fire.extraMagmaParticles;
		SNOWLOGGING = config.snowlogging.snowlogging && !FabricLoader.getInstance().isModLoaded("antique-atlas");
		SNOWLOG_WALLS = SNOWLOGGING && config.snowlogging.snowlogWalls;
		NATURAL_SNOWLOGGING = SNOWLOGGING && config.snowlogging.naturalSnowlogging;
		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			Client.SOUL_FIRE_SOUNDS = config.fire.soulFireSounds;
		}
	}

	public static volatile boolean FIRE_MAGMA_PARTICLES = true;
	public static volatile boolean SNOWLOGGING = true;
	public static volatile boolean SNOWLOG_WALLS = false;
	public static volatile boolean NATURAL_SNOWLOGGING = true;
	public static boolean canSnowlog() {
		return SNOWLOGGING && !FrozenBools.IS_DATAGEN;
	}

	public static boolean canSnowlogWalls() {
		return canSnowlog() && SNOWLOG_WALLS;
	}

	public static boolean canSnowlogNaturally() {
		return canSnowlog() && NATURAL_SNOWLOGGING;
	}

	public static final class Client {
		public static volatile boolean SOUL_FIRE_SOUNDS = true;}

	public final BlockSoundsConfig blockSounds = new BlockSoundsConfig();

	public final SnowloggingConfig snowlogging = new SnowloggingConfig();

	public final FireConfig fire = new FireConfig();
	public final FlowerConfig flower = new FlowerConfig();

	public static class BlockSoundsConfig {
		@EntrySyncData("cactusSounds")
		public boolean cactusSounds = true;

		@EntrySyncData("claySounds")
		public boolean claySounds = true;

		@EntrySyncData("coarseDirtSounds")
		public boolean coarseDirtSounds = true;

		@EntrySyncData("deadBushSounds")
		public boolean deadBushSounds = true;

		@EntrySyncData("flowerSounds")
		public boolean flowerSounds = true;

		@EntrySyncData("grassSounds")
		public boolean grassSounds = true;

		@EntrySyncData("magmaSounds")
		public boolean magmaSounds = true;

		@EntrySyncData("saplingSounds")
		public boolean saplingSounds = true;

		@EntrySyncData("iceSounds")
		public boolean iceSounds = true;

		@EntrySyncData("frostedIceSounds")
		public boolean frostedIceSounds = true;

		@EntrySyncData("gravelSounds")
		public boolean gravelSounds = true;

		@EntrySyncData("leafSounds")
		public boolean leafSounds = true;

		@EntrySyncData("lilyPadSounds")
		public boolean lilyPadSounds = true;

		@EntrySyncData("melonSounds")
		public boolean melonSounds = true;

		@EntrySyncData("mossSounds")
		public boolean mossSounds = true;

		@EntrySyncData("mushroomBlockSounds")
		public boolean mushroomBlockSounds = true;

		@EntrySyncData("paleOakSounds")
		public boolean paleOakSounds = true;

		@EntrySyncData("podzolSounds")
		public boolean podzolSounds = true;

		@EntrySyncData("reinforcedDeepslateSounds")
		public boolean reinforcedDeepslateSounds = true;

		@EntrySyncData("sandstoneSounds")
		public boolean sandstoneSounds = true;

		@EntrySyncData("sugarCaneSounds")
		public boolean sugarCaneSounds = true;

		@EntrySyncData("witherRoseSounds")
		public boolean witherRoseSounds = true;
	}

	public static class FireConfig {
		@EntrySyncData(value = "soulFireSounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean soulFireSounds = true;

		@EntrySyncData("extraMagmaParticles")
		public boolean extraMagmaParticles = true;
	}

	public static class SnowloggingConfig {
		@EntrySyncData("snowlogging")
		public boolean snowlogging = true;

		@EntrySyncData("snowlogWalls")
		public boolean snowlogWalls = false;

		@EntrySyncData("naturalSnowlogging")
		public boolean naturalSnowlogging = true;
	}
}
