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

package net.frozenblock.wilderwild.config.gui;

import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import static net.frozenblock.wilderwild.WWConstants.text;
import static net.frozenblock.wilderwild.WWConstants.tooltip;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import static net.frozenblock.wilderwild.config.gui.WWConfigGuiHelper.booleanEntry;
import static net.frozenblock.wilderwild.config.gui.WWConfigGuiHelper.intSliderEntry;

@Environment(EnvType.CLIENT)
public final class WWBlockConfigGui {

	private WWBlockConfigGui() {
		throw new UnsupportedOperationException("WWBlockConfigGui contains only static declarations.");
	}

	public static void setupEntries(ConfigCategory category, ConfigEntryBuilder builder) {
		category.addEntry(booleanEntry(builder, "reach_boost_beacon", WWBlockConfig.REACH_BOOST_BEACON));
		category.addEntry(booleanEntry(builder, "pollen_particles", WWBlockConfig.POLLEN_PARTICLES));
		category.addEntry(booleanEntry(builder, "log_hollowing", WWBlockConfig.LOG_HOLLOWING));
		category.addEntry(booleanEntry(builder, "cactus_placement", WWBlockConfig.CACTUS_PLACEMENT));
		category.addEntry(booleanEntry(builder, "azalea_from_moss", WWBlockConfig.AZALEA_FROM_MOSS));
		category.addEntry(booleanEntry(builder, "froglight_goop_growth", WWBlockConfig.FROGLIGHT_GOOP_GROWTH));
		category.addEntry(booleanEntry(builder, "new_reinforced_deepslate", WWBlockConfig.NEW_REINFORCED_DEEPSLATE));
		category.addEntry(booleanEntry(builder, "frosted_ice_cracking", WWBlockConfig.FROSTED_ICE_CRACKING));
		category.addEntry(booleanEntry(builder, "chest_bubbling", WWBlockConfig.CHEST_BUBBLING));
		category.addEntry(booleanEntry(builder, "thick_big_fungus_growth", WWBlockConfig.THICK_BIG_FUNGUS_GROWTH));

		// SCULK
		var shriekerGargling = booleanEntry(builder, "shrieker_gargling", WWBlockConfig.SHRIEKER_GARGLING);
		var shriekerOutline = booleanEntry(builder, "shrieker_outline", WWBlockConfig.SHRIEKER_OUTLINE);
		var billboardTendrils = booleanEntry(builder, "billboard_tendrils", WWBlockConfig.BILLBOARD_TENDRILS);
		var tendrilsCarryEvents = booleanEntry(builder, "tendrils_carry_events", WWBlockConfig.TENDRILS_CARRY_EVENTS);
		var tendrilGeneration = booleanEntry(builder, "hanging_tendril_generation", WWBlockConfig.TENDRIL_GENERATION);
		var osseousSculkGeneration = booleanEntry(builder, "osseous_sculk_generation", WWBlockConfig.OSSEOUS_SCULK_GENERATION);
		var sculkBuildingBlocksGeneration = booleanEntry(builder, "sculk_building_blocks_generation", WWBlockConfig.SCULK_BUILDING_BLOCKS_GENERATION);

		FrozenClothConfig.createSubCategory(builder, category, text("sculk"),
			false,
			tooltip("sculk"),
			shriekerGargling, shriekerOutline,
			tendrilsCarryEvents, billboardTendrils, tendrilGeneration,
			osseousSculkGeneration,
			sculkBuildingBlocksGeneration
		);

		// MESOGLEA
		var mesogleaFluid = booleanEntry(builder, "mesoglea_fluid", WWBlockConfig.MESOGLEA_RENDERS_AS_FLUID);
		var mesogleaBubbleColumns = booleanEntry(builder, "mesoglea_bubble_columns", WWBlockConfig.MESOGLEA_BUBBLE_COLUMNS);

		FrozenClothConfig.createSubCategory(builder, category, text("mesoglea"),
			false,
			tooltip("mesoglea"),
			mesogleaBubbleColumns, mesogleaFluid
		);

		// TERMITE
		var termitesOnlyEatNaturalBlocks = booleanEntry(builder, "termites_only_eat_natural_blocks", WWBlockConfig.TERMITE_ONLY_EATS_NATURAL_BLOCKS);
		var maxTermiteDistance = intSliderEntry(builder, "max_termite_distance", WWBlockConfig.TERMITE_MAX_DISTANCE, 1, 72);
		var maxNaturalTermiteDistance = intSliderEntry(builder, "max_natural_termite_distance", WWBlockConfig.TERMITE_MAX_DISTANCE, 1, 72);

		FrozenClothConfig.createSubCategory(builder, category, text("termite"),
			false,
			tooltip("termite"),
			termitesOnlyEatNaturalBlocks, maxTermiteDistance, maxNaturalTermiteDistance
		);

		// FLOWER
		var bonemealDandelions = booleanEntry(builder, "bone_meal_dandelions", WWBlockConfig.BONE_MEAL_DANDELIONS);
		var shearSeedingDandelions = booleanEntry(builder, "shear_seeding_dandelions", WWBlockConfig.SHEAR_SEEDING_DANDELIONS);
		var bonemealLilypads = booleanEntry(builder, "bone_meal_lilypads", WWBlockConfig.BONE_MEAL_LILY_PADS);
		var shearFloweringLilypads = booleanEntry(builder, "shear_flowering_lilypads", WWBlockConfig.SHEAR_FLOWERING_LILY_PADS);

		FrozenClothConfig.createSubCategory(builder, category, text("flower"),
			false,
			tooltip("flower"),
			bonemealDandelions, shearSeedingDandelions, bonemealLilypads, shearFloweringLilypads
		);

		// STONE CHEST
		var stoneChestTimer = intSliderEntry(builder, "stone_chest_timer", WWBlockConfig.STONE_CHEST_TIMER, 50, 200);
		var addStoneChests = booleanEntry(builder, "add_stone_chests", WWBlockConfig.ADD_STONE_CHESTS);

		FrozenClothConfig.createSubCategory(builder, category, text("stone_chest"),
			false,
			tooltip("stone_chest"),
			stoneChestTimer, addStoneChests
		);

		// SNOWLOGGING
		var allowSnowlogging = booleanEntry(builder, "allow_snowlogging", WWBlockConfig.SNOWLOGGING);
		var snowlogWalls = booleanEntry(builder, "snowlog_walls", WWBlockConfig.SNOWLOG_WALLS);
		var naturalSnowlogging = booleanEntry(builder, "natural_snowlogging", WWBlockConfig.NATURAL_SNOWLOGGING);

		FrozenClothConfig.createSubCategory(builder, category, text("snowlogging"),
			false,
			tooltip("snowlogging"),
			allowSnowlogging, snowlogWalls, naturalSnowlogging
		);

		// FIRE
		var extraMagmaParticles = booleanEntry(builder, "extra_magma_particles", WWBlockConfig.FIRE_EXTRA_MAGMA_PARTICLES);
		var soulFireSounds = booleanEntry(builder, "soul_fire_sounds", WWBlockConfig.FIRE_SOUL_FIRE_SOUNDS);

		FrozenClothConfig.createSubCategory(builder, category, text("fire"),
			false,
			tooltip("snowlogging"),
			extraMagmaParticles, soulFireSounds
		);

		// BLOCK SOUNDS
		var cactusSounds = booleanEntry(builder, "cactus_sounds", WWBlockConfig.CACTUS_SOUNDS);
		var claySounds = booleanEntry(builder, "clay_sounds", WWBlockConfig.CLAY_SOUNDS);
		var coarseDirtSounds = booleanEntry(builder, "coarse_dirt_sounds", WWBlockConfig.COARSE_DIRT_SOUNDS);
		var deadBushSounds = booleanEntry(builder, "dead_bush_sounds", WWBlockConfig.DEAD_BUSH_SOUNDS);
		var flowerSounds = booleanEntry(builder, "flower_sounds", WWBlockConfig.FLOWER_SOUNDS);
		var grassSounds = booleanEntry(builder, "grass_sounds", WWBlockConfig.GRASS_SOUNDS);
		var magmaSounds = booleanEntry(builder, "magma_sounds", WWBlockConfig.MAGMA_SOUNDS);
		var saplingSounds = booleanEntry(builder, "sapling_sounds", WWBlockConfig.SAPLING_SOUNDS);
		var gravelSounds = booleanEntry(builder, "gravel_sounds", WWBlockConfig.GRAVEL_SOUNDS);
		var iceSounds = booleanEntry(builder, "ice_sounds", WWBlockConfig.ICE_SOUNDS);
		var frostedIceSounds = booleanEntry(builder, "frosted_ice_sounds", WWBlockConfig.FROSTED_ICE_SOUNDS);
		var leafSounds = booleanEntry(builder, "leaf_sounds", WWBlockConfig.LEAF_SOUNDS);
		var lilyPadSounds = booleanEntry(builder, "lily_pad_sounds", WWBlockConfig.LILY_PAD_SOUNDS);
		var melonSounds = booleanEntry(builder, "melon_sounds", WWBlockConfig.MELON_SOUNDS);
		var mossSounds = booleanEntry(builder, "moss_sounds", WWBlockConfig.MOSS_SOUNDS);
		var mushroomBlockSounds = booleanEntry(builder, "mushroom_block_sounds", WWBlockConfig.MUSHROOM_BLOCK_SOUNDS);
		var podzolSounds = booleanEntry(builder, "podzol_sounds", WWBlockConfig.PODZOL_SOUNDS);
		var paleOakSounds = booleanEntry(builder, "pale_oak_sounds", WWBlockConfig.PALE_OAK_SOUNDS);
		var reinforcedDeepslateSounds = booleanEntry(builder, "reinforced_deepslate_sounds", WWBlockConfig.REINFORCED_DEEPSLATE_SOUNDS);
		var sandstoneSounds = booleanEntry(builder, "sandstone_sounds", WWBlockConfig.SANDSTONE_SOUNDS);
		var sugarCaneSounds = booleanEntry(builder, "sugar_cane_sounds", WWBlockConfig.SUGAR_CANE_SOUNDS);
		var witherRoseSounds = booleanEntry(builder, "wither_rose_sounds", WWBlockConfig.WITHER_ROSE_SOUNDS);

		FrozenClothConfig.createSubCategory(builder, category, text("block_sounds"),
			false,
			tooltip("block_sounds"),
			cactusSounds, claySounds, coarseDirtSounds, deadBushSounds, flowerSounds, frostedIceSounds,
			grassSounds, gravelSounds, iceSounds, leafSounds, lilyPadSounds, magmaSounds, melonSounds, mossSounds,
			mushroomBlockSounds, paleOakSounds, podzolSounds, reinforcedDeepslateSounds, sandstoneSounds, saplingSounds,
			sugarCaneSounds, witherRoseSounds
		);
	}

}
