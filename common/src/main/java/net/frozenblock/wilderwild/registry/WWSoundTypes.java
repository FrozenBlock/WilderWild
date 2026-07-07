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

package net.frozenblock.wilderwild.registry;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;

public final class WWSoundTypes {
	//WILDER WILD SOUNDS
	public static final SoundType ALGAE = new SoundType(0.8F, 1F,
		WWSounds.BLOCK_ALGAE_BREAK.get(),
		WWSounds.BLOCK_ALGAE_STEP.get(),
		WWSounds.BLOCK_ALGAE_PLACE.get(),
		WWSounds.BLOCK_ALGAE_HIT.get(),
		WWSounds.BLOCK_ALGAE_FALL.get()
	);
	public static final SoundType BAOBAB_NUT = new SoundType(1F, 1F,
		WWSounds.BLOCK_BAOBAB_NUT_BREAK.get(),
		WWSounds.BLOCK_BAOBAB_NUT_STEP.get(),
		WWSounds.BLOCK_BAOBAB_NUT_PLACE.get(),
		WWSounds.BLOCK_BAOBAB_NUT_HIT.get(),
		WWSounds.BLOCK_BAOBAB_NUT_FALL.get()
	);
	public static final SoundType COCONUT = new SoundType(1F, 1F,
		WWSounds.BLOCK_COCONUT_BREAK.get(),
		WWSounds.BLOCK_COCONUT_STEP.get(),
		WWSounds.BLOCK_COCONUT_PLACE.get(),
		WWSounds.BLOCK_COCONUT_HIT.get(),
		WWSounds.BLOCK_COCONUT_FALL.get()
	);
	public static final SoundType OSSEOUS_SCULK = new SoundType(1F, 1F,
		WWSounds.BLOCK_OSSEOUS_SCULK_BREAK.get(),
		WWSounds.BLOCK_OSSEOUS_SCULK_STEP.get(),
		WWSounds.BLOCK_OSSEOUS_SCULK_PLACE.get(),
		WWSounds.BLOCK_OSSEOUS_SCULK_HIT.get(),
		WWSounds.BLOCK_OSSEOUS_SCULK_FALL.get()
	);
	public static final SoundType NEMATOCYST = new SoundType(1F, 1F,
		WWSounds.BLOCK_NEMATOCYST_BREAK.get(),
		WWSounds.BLOCK_NEMATOCYST_STEP.get(),
		WWSounds.BLOCK_NEMATOCYST_PLACE.get(),
		WWSounds.BLOCK_NEMATOCYST_HIT.get(),
		WWSounds.BLOCK_NEMATOCYST_FALL.get()
	);
	public static final SoundType NULL_BLOCK = new SoundType(1F, 1F,
		WWSounds.BLOCK_NULL_BLOCK_BREAK.get(),
		WWSounds.BLOCK_NULL_BLOCK_STEP.get(),
		WWSounds.BLOCK_NULL_BLOCK_PLACE.get(),
		WWSounds.BLOCK_NULL_BLOCK_HIT.get(),
		WWSounds.BLOCK_NULL_BLOCK_FALL.get()
	);
	public static final SoundType HANGING_TENDRIL = new SoundType(1F, 1.25F,
		WWSounds.BLOCK_HANGING_TENDRIL_BREAK.get(),
		WWSounds.BLOCK_HANGING_TENDRIL_STEP.get(),
		WWSounds.BLOCK_HANGING_TENDRIL_PLACE.get(),
		WWSounds.BLOCK_HANGING_TENDRIL_HIT.get(),
		WWSounds.BLOCK_HANGING_TENDRIL_FALL.get()
	);
	public static final SoundType HOLLOWED_LOG = new SoundType(1F, 1F,
		WWSounds.BLOCK_HOLLOWED_LOG_BREAK.get(),
		WWSounds.BLOCK_HOLLOWED_LOG_STEP.get(),
		WWSounds.BLOCK_HOLLOWED_LOG_PLACE.get(),
		WWSounds.BLOCK_HOLLOWED_LOG_HIT.get(),
		WWSounds.BLOCK_HOLLOWED_LOG_FALL.get()
	);
	public static final SoundType HOLLOWED_CHERRY_LOG = new SoundType(1F, 1F,
		WWSounds.BLOCK_HOLLOWED_CHERRY_LOG_BREAK.get(),
		WWSounds.BLOCK_HOLLOWED_CHERRY_LOG_STEP.get(),
		WWSounds.BLOCK_HOLLOWED_CHERRY_LOG_PLACE.get(),
		WWSounds.BLOCK_HOLLOWED_CHERRY_LOG_HIT.get(),
		WWSounds.BLOCK_HOLLOWED_CHERRY_LOG_FALL.get()
	);
	public static final SoundType HOLLOWED_MAPLE_LOG = new SoundType(1F, 1F,
		WWSounds.BLOCK_HOLLOWED_MAPLE_LOG_BREAK.get(),
		WWSounds.BLOCK_HOLLOWED_MAPLE_LOG_STEP.get(),
		WWSounds.BLOCK_HOLLOWED_MAPLE_LOG_PLACE.get(),
		WWSounds.BLOCK_HOLLOWED_MAPLE_LOG_HIT.get(),
		WWSounds.BLOCK_HOLLOWED_MAPLE_LOG_FALL.get()
	);
	public static final SoundType HOLLOWED_PALE_OAK_LOG = new SoundType(1F, 1F,
		WWSounds.BLOCK_HOLLOWED_PALE_OAK_LOG_BREAK.get(),
		WWSounds.BLOCK_HOLLOWED_PALE_OAK_LOG_STEP.get(),
		WWSounds.BLOCK_HOLLOWED_PALE_OAK_LOG_PLACE.get(),
		WWSounds.BLOCK_HOLLOWED_PALE_OAK_LOG_HIT.get(),
		WWSounds.BLOCK_HOLLOWED_PALE_OAK_LOG_FALL.get()
	);
	public static final SoundType HOLLOWED_STEM = new SoundType(1F, 1F,
		WWSounds.BLOCK_HOLLOWED_STEM_BREAK.get(),
		WWSounds.BLOCK_HOLLOWED_STEM_STEP.get(),
		WWSounds.BLOCK_HOLLOWED_STEM_PLACE.get(),
		WWSounds.BLOCK_HOLLOWED_STEM_HIT.get(),
		WWSounds.BLOCK_HOLLOWED_STEM_FALL.get()
	);
	public static final SoundType ECHO_GLASS = new SoundType(0.8F, 1.25F,
		WWSounds.BLOCK_ECHO_GLASS_BREAK.get(),
		WWSounds.BLOCK_ECHO_GLASS_STEP.get(),
		WWSounds.BLOCK_ECHO_GLASS_PLACE.get(),
		WWSounds.BLOCK_ECHO_GLASS_CRACK.get(),
		WWSounds.BLOCK_ECHO_GLASS_FALL.get()
	);
	public static final SoundType GABBRO = new SoundType(1F, 1F,
		WWSounds.BLOCK_GABBRO_BREAK.get(),
		WWSounds.BLOCK_GABBRO_STEP.get(),
		WWSounds.BLOCK_GABBRO_PLACE.get(),
		WWSounds.BLOCK_GABBRO_HIT.get(),
		WWSounds.BLOCK_GABBRO_FALL.get()
	);
	public static final SoundType GABBRO_BRICKS = new SoundType(1F, 1F,
		WWSounds.BLOCK_GABBRO_BRICKS_BREAK.get(),
		WWSounds.BLOCK_GABBRO_BRICKS_STEP.get(),
		WWSounds.BLOCK_GABBRO_BRICKS_PLACE.get(),
		WWSounds.BLOCK_GABBRO_BRICKS_HIT.get(),
		WWSounds.BLOCK_GABBRO_BRICKS_FALL.get()
	);
	public static final SoundType GEOTHERMAL_VENT = new SoundType(1F, 1F,
		WWSounds.BLOCK_GEOTHERMAL_VENT_BREAK.get(),
		WWSounds.BLOCK_GEOTHERMAL_VENT_STEP.get(),
		WWSounds.BLOCK_GEOTHERMAL_VENT_PLACE.get(),
		WWSounds.BLOCK_GEOTHERMAL_VENT_HIT.get(),
		WWSounds.BLOCK_GEOTHERMAL_VENT_FALL.get()
	);
	public static final SoundType MESOGLEA = new SoundType(0.8F, 1F,
		WWSounds.BLOCK_MESOGLEA_BREAK.get(),
		WWSounds.BLOCK_MESOGLEA_STEP.get(),
		WWSounds.BLOCK_MESOGLEA_PLACE.get(),
		WWSounds.BLOCK_MESOGLEA_HIT.get(),
		WWSounds.BLOCK_MESOGLEA_FALL.get()
	);
	public static final SoundType POLLEN = new SoundType(0.8F, 1.2F,
		WWSounds.BLOCK_POLLEN_BREAK.get(),
		WWSounds.BLOCK_POLLEN_STEP.get(),
		WWSounds.BLOCK_POLLEN_PLACE.get(),
		WWSounds.BLOCK_POLLEN_HIT.get(),
		WWSounds.BLOCK_POLLEN_FALL.get()
	);
	public static final SoundType TERMITE_MOUND = new SoundType(0.8F, 1F,
		WWSounds.BLOCK_TERMITE_MOUND_BREAK.get(),
		WWSounds.BLOCK_TERMITE_MOUND_STEP.get(),
		WWSounds.BLOCK_TERMITE_MOUND_PLACE.get(),
		WWSounds.BLOCK_TERMITE_MOUND_HIT.get(),
		WWSounds.BLOCK_TERMITE_MOUND_FALL.get()
	);
	public static final SoundType TUMBLEWEED_PLANT = new SoundType(1F, 1F,
		WWSounds.BLOCK_TUMBLEWEED_PLANT_BREAK.get(),
		WWSounds.BLOCK_TUMBLEWEED_PLANT_STEP.get(),
		WWSounds.BLOCK_TUMBLEWEED_PLANT_PLACE.get(),
		WWSounds.BLOCK_TUMBLEWEED_PLANT_HIT.get(),
		WWSounds.BLOCK_TUMBLEWEED_PLANT_FALL.get()
	);
	public static final SoundType AUBURN_MOSS = new SoundType(1F, 1F,
		WWSounds.BLOCK_AUBURN_MOSS_BREAK.get(),
		WWSounds.BLOCK_AUBURN_MOSS_STEP.get(),
		WWSounds.BLOCK_AUBURN_MOSS_PLACE.get(),
		WWSounds.BLOCK_AUBURN_MOSS_HIT.get(),
		WWSounds.BLOCK_AUBURN_MOSS_FALL.get()
	);
	public static final SoundType AUBURN_MOSS_CARPET = new SoundType(1F, 1F,
		WWSounds.BLOCK_AUBURN_MOSS_CARPET_BREAK.get(),
		WWSounds.BLOCK_AUBURN_MOSS_CARPET_STEP.get(),
		WWSounds.BLOCK_AUBURN_MOSS_CARPET_PLACE.get(),
		WWSounds.BLOCK_AUBURN_MOSS_CARPET_HIT.get(),
		WWSounds.BLOCK_AUBURN_MOSS_CARPET_FALL.get()
	);
	public static final SoundType MAPLE_LEAVES = new SoundType(1F, 1F,
		WWSounds.BLOCK_MAPLE_LEAVES_BREAK.get(),
		WWSounds.BLOCK_MAPLE_LEAVES_STEP.get(),
		WWSounds.BLOCK_MAPLE_LEAVES_PLACE.get(),
		WWSounds.BLOCK_MAPLE_LEAVES_HIT.get(),
		WWSounds.BLOCK_MAPLE_LEAVES_FALL.get()
	);
	public static final SoundType MAPLE_LEAF_LITTER = new SoundType(1F, 1F,
		WWSounds.BLOCK_MAPLE_LEAF_LITTER_BREAK.get(),
		WWSounds.BLOCK_MAPLE_LEAF_LITTER_STEP.get(),
		WWSounds.BLOCK_MAPLE_LEAF_LITTER_PLACE.get(),
		WWSounds.BLOCK_MAPLE_LEAF_LITTER_HIT.get(),
		WWSounds.BLOCK_MAPLE_LEAF_LITTER_FALL.get()
	);
	public static final SoundType MAPLE_WOOD = new SoundType(1F, 1F,
		WWSounds.BLOCK_MAPLE_WOOD_BREAK.get(),
		WWSounds.BLOCK_MAPLE_WOOD_STEP.get(),
		WWSounds.BLOCK_MAPLE_WOOD_PLACE.get(),
		WWSounds.BLOCK_MAPLE_WOOD_HIT.get(),
		WWSounds.BLOCK_MAPLE_WOOD_FALL.get()
	);
	public static final SoundType MAPLE_WOOD_HANGING_SIGN = new SoundType(1F, 1F,
		WWSounds.BLOCK_MAPLE_WOOD_HANGING_SIGN_BREAK.get(),
		WWSounds.BLOCK_MAPLE_WOOD_HANGING_SIGN_STEP.get(),
		WWSounds.BLOCK_MAPLE_WOOD_HANGING_SIGN_PLACE.get(),
		WWSounds.BLOCK_MAPLE_WOOD_HANGING_SIGN_HIT.get(),
		WWSounds.BLOCK_MAPLE_WOOD_HANGING_SIGN_FALL.get()
	);
	public static final SoundType CHERRY_LEAF_LITTER = new SoundType(1F, 1F,
		WWSounds.BLOCK_CHERRY_LEAF_LITTER_BREAK.get(),
		WWSounds.BLOCK_CHERRY_LEAF_LITTER_STEP.get(),
		WWSounds.BLOCK_CHERRY_LEAF_LITTER_PLACE.get(),
		WWSounds.BLOCK_CHERRY_LEAF_LITTER_HIT.get(),
		WWSounds.BLOCK_CHERRY_LEAF_LITTER_FALL.get()
	);
	public static final SoundType PALE_OAK_LEAF_LITTER = new SoundType(1F, 1F,
		WWSounds.BLOCK_PALE_OAK_LEAF_LITTER_BREAK.get(),
		WWSounds.BLOCK_PALE_OAK_LEAF_LITTER_STEP.get(),
		WWSounds.BLOCK_PALE_OAK_LEAF_LITTER_PLACE.get(),
		WWSounds.BLOCK_PALE_OAK_LEAF_LITTER_HIT.get(),
		WWSounds.BLOCK_PALE_OAK_LEAF_LITTER_FALL.get()
	);
	public static final SoundType CONIFER_LEAF_LITTER = new SoundType(1F, 1F,
		WWSounds.BLOCK_CONIFER_LEAF_LITTER_BREAK.get(),
		WWSounds.BLOCK_CONIFER_LEAF_LITTER_STEP.get(),
		WWSounds.BLOCK_CONIFER_LEAF_LITTER_PLACE.get(),
		WWSounds.BLOCK_CONIFER_LEAF_LITTER_HIT.get(),
		WWSounds.BLOCK_CONIFER_LEAF_LITTER_FALL.get()
	);

	//VANILLA SOUNDS
	public static final SoundType CLAY = new SoundType(0.9F, 1F,
		WWSounds.BLOCK_CLAY_BREAK.get(),
		WWSounds.BLOCK_CLAY_STEP.get(),
		WWSounds.BLOCK_CLAY_PLACE.get(),
		WWSounds.BLOCK_CLAY_HIT.get(),
		WWSounds.BLOCK_CLAY_FALL.get()
	);
	public static final SoundType CACTUS = new SoundType(0.8F, 1F,
		WWSounds.BLOCK_CACTUS_BREAK.get(),
		WWSounds.BLOCK_CACTUS_STEP.get(),
		WWSounds.BLOCK_CACTUS_PLACE.get(),
		WWSounds.BLOCK_CACTUS_HIT.get(),
		WWSounds.BLOCK_CACTUS_FALL.get()
	);
	public static final SoundType GRAVEL = new SoundType(0.8F, 1F,
		WWSounds.BLOCK_GRAVEL_BREAK.get(),
		WWSounds.BLOCK_GRAVEL_STEP.get(),
		WWSounds.BLOCK_GRAVEL_PLACE.get(),
		WWSounds.BLOCK_GRAVEL_HIT.get(),
		WWSounds.BLOCK_GRAVEL_FALL.get()
	);
	public static final SoundType MUSHROOM = new SoundType(1F, 1F,
		WWSounds.BLOCK_MUSHROOM_BREAK.get(),
		WWSounds.BLOCK_MUSHROOM_STEP.get(),
		WWSounds.BLOCK_MUSHROOM_PLACE.get(),
		WWSounds.BLOCK_MUSHROOM_HIT.get(),
		WWSounds.BLOCK_MUSHROOM_FALL.get()
	);
	public static final SoundType MUSHROOM_BLOCK = new SoundType(1F, 1F,
		WWSounds.BLOCK_MUSHROOM_BLOCK_BREAK.get(),
		WWSounds.BLOCK_MUSHROOM_BLOCK_STEP.get(),
		WWSounds.BLOCK_MUSHROOM_BLOCK_PLACE.get(),
		WWSounds.BLOCK_MUSHROOM_BLOCK_HIT.get(),
		WWSounds.BLOCK_MUSHROOM_BLOCK_FALL.get()
	);
	public static final SoundType ICE = new SoundType(1F, 1F,
		WWSounds.BLOCK_ICE_BREAK.get(),
		WWSounds.BLOCK_ICE_STEP.get(),
		WWSounds.BLOCK_ICE_PLACE.get(),
		WWSounds.BLOCK_ICE_HIT.get(),
		WWSounds.BLOCK_ICE_FALL.get()
	);
	public static final SoundType FROSTED_ICE = new SoundType(1F, 1F,
		WWSounds.BLOCK_FROSTED_ICE_BREAK.get(),
		WWSounds.BLOCK_FROSTED_ICE_STEP.get(),
		WWSounds.BLOCK_FROSTED_ICE_PLACE.get(),
		WWSounds.BLOCK_FROSTED_ICE_HIT.get(),
		WWSounds.BLOCK_FROSTED_ICE_FALL.get()
	);
	public static final SoundType CONIFER_LEAVES = new SoundType(1F, 1F,
		WWSounds.BLOCK_CONIFER_LEAVES_BREAK.get(),
		WWSounds.BLOCK_CONIFER_LEAVES_STEP.get(),
		WWSounds.BLOCK_CONIFER_LEAVES_PLACE.get(),
		WWSounds.BLOCK_CONIFER_LEAVES_HIT.get(),
		WWSounds.BLOCK_CONIFER_LEAVES_FALL.get()
	);
	public static final SoundType PALE_OAK_LEAVES = new SoundType(1F, 1F,
		WWSounds.BLOCK_PALE_OAK_LEAVES_BREAK.get(),
		WWSounds.BLOCK_PALE_OAK_LEAVES_STEP.get(),
		WWSounds.BLOCK_PALE_OAK_LEAVES_PLACE.get(),
		WWSounds.BLOCK_PALE_OAK_LEAVES_HIT.get(),
		WWSounds.BLOCK_PALE_OAK_LEAVES_FALL.get()
	);
	public static final SoundType PALE_MOSS = new SoundType(1F, 1F,
		WWSounds.BLOCK_PALE_MOSS_BREAK.get(),
		WWSounds.BLOCK_PALE_MOSS_STEP.get(),
		WWSounds.BLOCK_PALE_MOSS_PLACE.get(),
		WWSounds.BLOCK_PALE_MOSS_HIT.get(),
		WWSounds.BLOCK_PALE_MOSS_FALL.get()
	);
	public static final SoundType PALE_MOSS_CARPET = new SoundType(1F, 1F,
		WWSounds.BLOCK_PALE_MOSS_CARPET_BREAK.get(),
		WWSounds.BLOCK_PALE_MOSS_CARPET_STEP.get(),
		WWSounds.BLOCK_PALE_MOSS_CARPET_PLACE.get(),
		WWSounds.BLOCK_PALE_MOSS_CARPET_HIT.get(),
		WWSounds.BLOCK_PALE_MOSS_CARPET_FALL.get()
	);
	public static final SoundType PALE_OAK_WOOD = new SoundType(1F, 1F,
		WWSounds.BLOCK_PALE_OAK_WOOD_BREAK.get(),
		WWSounds.BLOCK_PALE_OAK_WOOD_STEP.get(),
		WWSounds.BLOCK_PALE_OAK_WOOD_PLACE.get(),
		WWSounds.BLOCK_PALE_OAK_WOOD_HIT.get(),
		WWSounds.BLOCK_PALE_OAK_WOOD_FALL.get()
	);
	public static final SoundType PALE_OAK_WOOD_HANGING_SIGN = new SoundType(1F, 1F,
		WWSounds.BLOCK_PALE_OAK_WOOD_HANGING_SIGN_BREAK.get(),
		WWSounds.BLOCK_PALE_OAK_WOOD_HANGING_SIGN_STEP.get(),
		WWSounds.BLOCK_PALE_OAK_WOOD_HANGING_SIGN_PLACE.get(),
		WWSounds.BLOCK_PALE_OAK_WOOD_HANGING_SIGN_HIT.get(),
		WWSounds.BLOCK_PALE_OAK_WOOD_HANGING_SIGN_FALL.get()
	);
	public static final SoundType LILY_PAD = new SoundType(1F, 1F,
		SoundEvents.BIG_DRIPLEAF_BREAK,
		SoundEvents.BIG_DRIPLEAF_STEP,
		SoundEvents.LILY_PAD_PLACE,
		SoundEvents.BIG_DRIPLEAF_HIT,
		SoundEvents.BIG_DRIPLEAF_FALL
	);
	public static final SoundType SAPLING = new SoundType(1F, 1F,
		WWSounds.BLOCK_SAPLING_BREAK.get(),
		WWSounds.BLOCK_SAPLING_STEP.get(),
		WWSounds.BLOCK_SAPLING_PLACE.get(),
		WWSounds.BLOCK_SAPLING_HIT.get(),
		WWSounds.BLOCK_SAPLING_FALL.get()
	);
	public static final SoundType SUGAR_CANE = new SoundType(1F, 1F,
		WWSounds.BLOCK_SUGAR_CANE_BREAK.get(),
		WWSounds.BLOCK_SUGAR_CANE_STEP.get(),
		WWSounds.BLOCK_SUGAR_CANE_PLACE.get(),
		WWSounds.BLOCK_SUGAR_CANE_HIT.get(),
		WWSounds.BLOCK_SUGAR_CANE_FALL.get()
	);
	public static final SoundType COARSE_DIRT = new SoundType(0.8F, 1F,
		WWSounds.BLOCK_COARSE_DIRT_BREAK.get(),
		WWSounds.BLOCK_COARSE_DIRT_STEP.get(),
		WWSounds.BLOCK_COARSE_DIRT_PLACE.get(),
		WWSounds.BLOCK_COARSE_DIRT_HIT.get(),
		WWSounds.BLOCK_COARSE_DIRT_FALL.get()
	);
	public static final SoundType SANDSTONE = new SoundType(0.7F, 1.1F,
		WWSounds.BLOCK_SANDSTONE_BREAK.get(),
		WWSounds.BLOCK_SANDSTONE_STEP.get(),
		WWSounds.BLOCK_SANDSTONE_PLACE.get(),
		WWSounds.BLOCK_SANDSTONE_HIT.get(),
		WWSounds.BLOCK_SANDSTONE_FALL.get()
	);
	public static final SoundType SCORCHED_SAND = new SoundType(0.8F, 1F,
		WWSounds.BLOCK_SCORCHED_SAND_BREAK.get(),
		WWSounds.BLOCK_SCORCHED_SAND_STEP.get(),
		WWSounds.BLOCK_SCORCHED_SAND_PLACE.get(),
		WWSounds.BLOCK_SCORCHED_SAND_HIT.get(),
		WWSounds.BLOCK_SCORCHED_SAND_FALL.get()
	);
	public static final SoundType REINFORCED_DEEPSLATE = new SoundType(1F, 1F,
		WWSounds.BLOCK_REINFORCED_DEEPSLATE_BREAK.get(),
		WWSounds.BLOCK_REINFORCED_DEEPSLATE_STEP.get(),
		WWSounds.BLOCK_REINFORCED_DEEPSLATE_PLACE.get(),
		WWSounds.BLOCK_REINFORCED_DEEPSLATE_HIT.get(),
		WWSounds.BLOCK_REINFORCED_DEEPSLATE_FALL.get()
	);
	public static final SoundType MAGMA = new SoundType(1F, 0.9F,
		WWSounds.BLOCK_MAGMA_BREAK.get(),
		WWSounds.BLOCK_MAGMA_STEP.get(),
		WWSounds.BLOCK_MAGMA_PLACE.get(),
		WWSounds.BLOCK_MAGMA_HIT.get(),
		WWSounds.BLOCK_MAGMA_FALL.get()
	);
	public static final SoundType MELON = new SoundType(1F, 1F,
		WWSounds.BLOCK_MELON_BREAK.get(),
		WWSounds.BLOCK_MELON_STEP.get(),
		WWSounds.BLOCK_MELON_PLACE.get(),
		WWSounds.BLOCK_MELON_HIT.get(),
		WWSounds.BLOCK_MELON_FALL.get()
	);
	public static final SoundType SHORT_GRASS = new SoundType(1F, 1F,
		WWSounds.BLOCK_SHORT_GRASS_BREAK.get(),
		SoundEvents.GRASS_STEP,
		WWSounds.BLOCK_SHORT_GRASS_PLACE.get(),
		SoundEvents.GRASS_HIT,
		SoundEvents.GRASS_FALL
	);
	public static final SoundType FROZEN_GRASS = new SoundType(1F, 1F,
		WWSounds.BLOCK_FROZEN_GRASS_BREAK.get(),
		SoundEvents.GRASS_STEP,
		WWSounds.BLOCK_FROZEN_GRASS_PLACE.get(),
		SoundEvents.GRASS_HIT,
		SoundEvents.GRASS_FALL
	);
	public static final SoundType DRY_GRASS = new SoundType(1F, 1F,
		WWSounds.BLOCK_DRY_GRASS_BREAK.get(),
		SoundEvents.GRASS_STEP,
		WWSounds.BLOCK_DRY_GRASS_PLACE.get(),
		SoundEvents.GRASS_HIT,
		SoundEvents.GRASS_FALL
	);
	public static final SoundType BARNACLES = new SoundType(1F, 1F,
		WWSounds.BLOCK_BARNACLES_BREAK.get(),
		SoundEvents.CORAL_BLOCK_STEP,
		WWSounds.BLOCK_BARNACLES_PLACE.get(),
		SoundEvents.CORAL_BLOCK_HIT,
		SoundEvents.CORAL_BLOCK_FALL
	);
	public static final SoundType SEA_ANEMONE = new SoundType(1F, 1F,
		WWSounds.BLOCK_SEA_ANEMONE_BREAK.get(),
		WWSounds.BLOCK_ALGAE_STEP.get(),
		WWSounds.BLOCK_SEA_ANEMONE_PLACE.get(),
		WWSounds.BLOCK_ALGAE_HIT.get(),
		WWSounds.BLOCK_ALGAE_FALL.get()
	);
	public static final SoundType TUBE_WORMS = new SoundType(1F, 1F,
		WWSounds.BLOCK_TUBE_WORM_BREAK.get(),
		SoundEvents.CORAL_BLOCK_STEP,
		WWSounds.BLOCK_TUBE_WORMS_PLACE.get(),
		SoundEvents.CORAL_BLOCK_HIT,
		SoundEvents.CORAL_BLOCK_FALL
	);

	// must be called after register on neoforge
	public static void setup() {}
}
