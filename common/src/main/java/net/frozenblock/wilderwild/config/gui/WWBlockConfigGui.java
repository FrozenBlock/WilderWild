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
import static net.frozenblock.lib.config.clothconfig.FrozenLibClothConfigGuiHelper.*;
import static net.frozenblock.wilderwild.WWConstants.text;
import static net.frozenblock.wilderwild.WWConstants.tooltip;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.mehvahdjukaar.candlelight.api.ClientOnly;

@ClientOnly
public final class WWBlockConfigGui {

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
		createSubCategory(builder, category, text("sculk"), tooltip("sculk"),
			booleanEntry(builder, "shrieker_gargling", WWBlockConfig.SHRIEKER_GARGLING),
			booleanEntry(builder, "shrieker_outline", WWBlockConfig.SHRIEKER_OUTLINE),
			booleanEntry(builder, "tendrils_carry_events", WWBlockConfig.TENDRILS_CARRY_EVENTS),
			booleanEntry(builder, "billboard_tendrils", WWBlockConfig.BILLBOARD_TENDRILS),
			booleanEntry(builder, "hanging_tendril_generation", WWBlockConfig.TENDRIL_GENERATION),
			booleanEntry(builder, "osseous_sculk_generation", WWBlockConfig.OSSEOUS_SCULK_GENERATION),
			booleanEntry(builder, "sculk_building_blocks_generation", WWBlockConfig.SCULK_BUILDING_BLOCKS_GENERATION)
		);

		// MESOGLEA
		createSubCategory(builder, category, text("mesoglea"), tooltip("mesoglea"),
			booleanEntry(builder, "mesoglea_bubble_columns", WWBlockConfig.MESOGLEA_BUBBLE_COLUMNS),
			booleanEntry(builder, "mesoglea_fluid", WWBlockConfig.MESOGLEA_RENDERS_AS_FLUID)
		);

		// TERMITE
		createSubCategory(builder, category, text("termite"), tooltip("termite"),
			booleanEntry(builder, "termites_only_eat_natural_blocks", WWBlockConfig.TERMITE_ONLY_EATS_NATURAL_BLOCKS),
			intSliderEntry(builder, "max_termite_distance", WWBlockConfig.TERMITE_MAX_DISTANCE, 1, 72),
			intSliderEntry(builder, "max_natural_termite_distance", WWBlockConfig.TERMITE_MAX_DISTANCE, 1, 72)
		);

		// FLOWER
		createSubCategory(builder, category, text("flower"), tooltip("flower"),
			booleanEntry(builder, "bone_meal_dandelions", WWBlockConfig.BONE_MEAL_DANDELIONS),
			booleanEntry(builder, "shear_seeding_dandelions", WWBlockConfig.SHEAR_SEEDING_DANDELIONS),
			booleanEntry(builder, "bone_meal_lilypads", WWBlockConfig.BONE_MEAL_LILY_PADS),
			booleanEntry(builder, "shear_flowering_lilypads", WWBlockConfig.SHEAR_FLOWERING_LILY_PADS)
		);

		// STONE CHEST
		createSubCategory(builder, category, text("stone_chest"), tooltip("stone_chest"),
			intSliderEntry(builder, "stone_chest_timer", WWBlockConfig.STONE_CHEST_TIMER, 50, 200),
			booleanEntry(builder, "add_stone_chests", WWBlockConfig.ADD_STONE_CHESTS)
		);

		// SNOWLOGGING
		createSubCategory(builder, category, text("snowlogging"), tooltip("snowlogging"),
			booleanEntry(builder, "allow_snowlogging", WWBlockConfig.SNOWLOGGING),
			booleanEntry(builder, "snowlog_walls", WWBlockConfig.SNOWLOG_WALLS),
			booleanEntry(builder, "natural_snowlogging", WWBlockConfig.NATURAL_SNOWLOGGING)
		);

		// FIRE
		createSubCategory(builder, category, text("fire"), tooltip("snowlogging"),
			booleanEntry(builder, "extra_magma_particles", WWBlockConfig.FIRE_EXTRA_MAGMA_PARTICLES),
			booleanEntry(builder, "soul_fire_sounds", WWBlockConfig.FIRE_SOUL_FIRE_SOUNDS)
		);

		// BLOCK SOUNDS
		createSubCategory(builder, category, text("block_sounds"), tooltip("block_sounds"),
			booleanEntry(builder, "cactus_sounds", WWBlockConfig.CACTUS_SOUNDS),
			booleanEntry(builder, "clay_sounds", WWBlockConfig.CLAY_SOUNDS),
			booleanEntry(builder, "coarse_dirt_sounds", WWBlockConfig.COARSE_DIRT_SOUNDS),
			booleanEntry(builder, "dead_bush_sounds", WWBlockConfig.DEAD_BUSH_SOUNDS),
			booleanEntry(builder, "flower_sounds", WWBlockConfig.FLOWER_SOUNDS),
			booleanEntry(builder, "frosted_ice_sounds", WWBlockConfig.FROSTED_ICE_SOUNDS),
			booleanEntry(builder, "grass_sounds", WWBlockConfig.GRASS_SOUNDS),
			booleanEntry(builder, "gravel_sounds", WWBlockConfig.GRAVEL_SOUNDS),
			booleanEntry(builder, "ice_sounds", WWBlockConfig.ICE_SOUNDS),
			booleanEntry(builder, "leaf_sounds", WWBlockConfig.LEAF_SOUNDS),
			booleanEntry(builder, "lily_pad_sounds", WWBlockConfig.LILY_PAD_SOUNDS),
			booleanEntry(builder, "magma_sounds", WWBlockConfig.MAGMA_SOUNDS),
			booleanEntry(builder, "melon_sounds", WWBlockConfig.MELON_SOUNDS),
			booleanEntry(builder, "moss_sounds", WWBlockConfig.MOSS_SOUNDS),
			booleanEntry(builder, "mushroom_block_sounds", WWBlockConfig.MUSHROOM_BLOCK_SOUNDS),
			booleanEntry(builder, "pale_oak_sounds", WWBlockConfig.PALE_OAK_SOUNDS),
			booleanEntry(builder, "podzol_sounds", WWBlockConfig.PODZOL_SOUNDS),
			booleanEntry(builder, "reinforced_deepslate_sounds", WWBlockConfig.REINFORCED_DEEPSLATE_SOUNDS),
			booleanEntry(builder, "sandstone_sounds", WWBlockConfig.SANDSTONE_SOUNDS),
			booleanEntry(builder, "sapling_sounds", WWBlockConfig.SAPLING_SOUNDS),
			booleanEntry(builder, "sugar_cane_sounds", WWBlockConfig.SUGAR_CANE_SOUNDS),
			booleanEntry(builder, "wither_rose_sounds", WWBlockConfig.WITHER_ROSE_SOUNDS)
		);
	}

}
