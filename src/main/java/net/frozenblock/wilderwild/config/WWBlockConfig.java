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

import net.frozenblock.lib.FrozenBools;
import net.frozenblock.lib.config.v2.config.ConfigData;
import net.frozenblock.lib.config.v2.config.ConfigSettings;
import net.frozenblock.lib.config.v2.entry.ConfigEntry;
import net.frozenblock.lib.config.v2.entry.EntryType;
import net.frozenblock.lib.config.v2.entry.property.VisibilityPredicate;
import net.frozenblock.lib.config.v2.registry.ID;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;

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

	// SNOWLOGGING
	public static final ConfigEntry<Boolean> SNOWLOGGING = CONFIG.entryBuilder("snowlogging/snowlogging", EntryType.BOOL, true)
		.visibilityPredicate(VisibilityPredicate.of(() -> !SnowloggingUtils.HAS_ANTIQUE_ATLAS))
		.requireRestart()
		.build();
	public static final ConfigEntry<Boolean> SNOWLOG_WALLS = CONFIG.entryBuilder("snowlogging/snowlogWalls", EntryType.BOOL, true)
		.visibilityPredicate(VisibilityPredicate.of(() -> !SnowloggingUtils.HAS_ANTIQUE_ATLAS))
		.requireRestart()
		.build();
	public static final ConfigEntry<Boolean> NATURAL_SNOWLOGGING = CONFIG.entryBuilder("snowlogging/naturalSnowlogging", EntryType.BOOL, true)
		.visibilityPredicate(VisibilityPredicate.of(() -> !SnowloggingUtils.HAS_ANTIQUE_ATLAS))
		.requireRestart()
		.build();

	public static boolean canSnowlog() {
		return SNOWLOGGING.get() && !SnowloggingUtils.HAS_ANTIQUE_ATLAS && !FrozenBools.IS_DATAGEN;
	}

	public static boolean canSnowlogWalls() {
		return canSnowlog() && SNOWLOG_WALLS.get();
	}

	public static boolean canSnowlogNaturally() {
		return canSnowlog() && NATURAL_SNOWLOGGING.get();
	}

	// FIRE
	public static final ConfigEntry<Boolean> FIRE_SOUL_FIRE_SOUNDS = CONFIG.unsyncableEntry("fire/soulFireSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> FIRE_EXTRA_MAGMA_PARTICLES = CONFIG.entry("fire/extraMagmaParticles", EntryType.BOOL, true);

	// BLOCK SOUNDS
	public static final ConfigEntry<Boolean> CACTUS_SOUNDS = CONFIG.entry("blockSounds/cactusSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> CLAY_SOUNDS = CONFIG.entry("blockSounds/claySounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> COARSE_DIRT_SOUNDS = CONFIG.entry("blockSounds/coarseDirtSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> DEAD_BUSH_SOUNDS = CONFIG.entry("blockSounds/deadBushSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> FLOWER_SOUNDS = CONFIG.entry("blockSounds/flowerSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> GRASS_SOUNDS = CONFIG.entry("blockSounds/grassSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> MAGMA_SOUNDS = CONFIG.entry("blockSounds/magmaSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> SAPLING_SOUNDS = CONFIG.entry("blockSounds/saplingSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> ICE_SOUNDS = CONFIG.entry("blockSounds/iceSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> FROSTED_ICE_SOUNDS = CONFIG.entry("blockSounds/frostedIceSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> GRAVEL_SOUNDS = CONFIG.entry("blockSounds/gravelSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> LEAF_SOUNDS = CONFIG.entry("blockSounds/leafSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> LILY_PAD_SOUNDS = CONFIG.entry("blockSounds/lilyPadSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> MELON_SOUNDS = CONFIG.entry("blockSounds/melonSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> MOSS_SOUNDS = CONFIG.entry("blockSounds/mossSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> MUSHROOM_BLOCK_SOUNDS = CONFIG.entry("blockSounds/mushroomBlockSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> PALE_OAK_SOUNDS = CONFIG.entry("blockSounds/paleOakSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> PODZOL_SOUNDS = CONFIG.entry("blockSounds/podzolSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> REINFORCED_DEEPSLATE_SOUNDS = CONFIG.entry("blockSounds/reinforcedDeepslateSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> SANDSTONE_SOUNDS = CONFIG.entry("blockSounds/sandstoneSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> SUGAR_CANE_SOUNDS = CONFIG.entry("blockSounds/sugarCaneSounds", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> WITHER_ROSE_SOUNDS = CONFIG.entry("blockSounds/witherRoseSounds", EntryType.BOOL, true);
}
