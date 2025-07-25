/*
 * Copyright 2025 FrozenBlock
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
		WWSounds.BLOCK_ALGAE_BREAK,
		WWSounds.BLOCK_ALGAE_STEP,
		WWSounds.BLOCK_ALGAE_PLACE,
		WWSounds.BLOCK_ALGAE_HIT,
		WWSounds.BLOCK_ALGAE_FALL
	);
	public static final SoundType BAOBAB_NUT = new SoundType(1F, 1F,
		WWSounds.BLOCK_BAOBAB_NUT_BREAK,
		WWSounds.BLOCK_BAOBAB_NUT_STEP,
		WWSounds.BLOCK_BAOBAB_NUT_PLACE,
		WWSounds.BLOCK_BAOBAB_NUT_HIT,
		WWSounds.BLOCK_BAOBAB_NUT_FALL
	);
	public static final SoundType COCONUT = new SoundType(1F, 1F,
		WWSounds.BLOCK_COCONUT_BREAK,
		WWSounds.BLOCK_COCONUT_STEP,
		WWSounds.BLOCK_COCONUT_PLACE,
		WWSounds.BLOCK_COCONUT_HIT,
		WWSounds.BLOCK_COCONUT_FALL
	);
	public static final SoundType OSSEOUS_SCULK = new SoundType(1F, 1F,
		WWSounds.BLOCK_OSSEOUS_SCULK_BREAK,
		WWSounds.BLOCK_OSSEOUS_SCULK_STEP,
		WWSounds.BLOCK_OSSEOUS_SCULK_PLACE,
		WWSounds.BLOCK_OSSEOUS_SCULK_HIT,
		WWSounds.BLOCK_OSSEOUS_SCULK_FALL
	);
	public static final SoundType NEMATOCYST = new SoundType(1F, 1F,
		WWSounds.BLOCK_NEMATOCYST_BREAK,
		WWSounds.BLOCK_NEMATOCYST_STEP,
		WWSounds.BLOCK_NEMATOCYST_PLACE,
		WWSounds.BLOCK_NEMATOCYST_HIT,
		WWSounds.BLOCK_NEMATOCYST_FALL
	);
	public static final SoundType NULL_BLOCK = new SoundType(1F, 1F,
		WWSounds.BLOCK_NULL_BLOCK_BREAK,
		WWSounds.BLOCK_NULL_BLOCK_STEP,
		WWSounds.BLOCK_NULL_BLOCK_PLACE,
		WWSounds.BLOCK_NULL_BLOCK_HIT,
		WWSounds.BLOCK_NULL_BLOCK_FALL
	);
	public static final SoundType HANGING_TENDRIL = new SoundType(1F, 1.25F,
		WWSounds.BLOCK_HANGING_TENDRIL_BREAK,
		WWSounds.BLOCK_HANGING_TENDRIL_STEP,
		WWSounds.BLOCK_HANGING_TENDRIL_PLACE,
		WWSounds.BLOCK_HANGING_TENDRIL_HIT,
		WWSounds.BLOCK_HANGING_TENDRIL_FALL
	);
	public static final SoundType HOLLOWED_LOG = new SoundType(1F, 1F,
		WWSounds.BLOCK_HOLLOWED_LOG_BREAK,
		WWSounds.BLOCK_HOLLOWED_LOG_STEP,
		WWSounds.BLOCK_HOLLOWED_LOG_PLACE,
		WWSounds.BLOCK_HOLLOWED_LOG_HIT,
		WWSounds.BLOCK_HOLLOWED_LOG_FALL
	);
	public static final SoundType HOLLOWED_CHERRY_LOG = new SoundType(1F, 1F,
		WWSounds.BLOCK_HOLLOWED_CHERRY_LOG_BREAK,
		WWSounds.BLOCK_HOLLOWED_CHERRY_LOG_STEP,
		WWSounds.BLOCK_HOLLOWED_CHERRY_LOG_PLACE,
		WWSounds.BLOCK_HOLLOWED_CHERRY_LOG_HIT,
		WWSounds.BLOCK_HOLLOWED_CHERRY_LOG_FALL
	);
	public static final SoundType HOLLOWED_MAPLE_LOG = new SoundType(1F, 1F,
		WWSounds.BLOCK_HOLLOWED_MAPLE_LOG_BREAK,
		WWSounds.BLOCK_HOLLOWED_MAPLE_LOG_STEP,
		WWSounds.BLOCK_HOLLOWED_MAPLE_LOG_PLACE,
		WWSounds.BLOCK_HOLLOWED_MAPLE_LOG_HIT,
		WWSounds.BLOCK_HOLLOWED_MAPLE_LOG_FALL
	);
	public static final SoundType HOLLOWED_STEM = new SoundType(1F, 1F,
		WWSounds.BLOCK_HOLLOWED_STEM_BREAK,
		WWSounds.BLOCK_HOLLOWED_STEM_STEP,
		WWSounds.BLOCK_HOLLOWED_STEM_PLACE,
		WWSounds.BLOCK_HOLLOWED_STEM_HIT,
		WWSounds.BLOCK_HOLLOWED_STEM_FALL
	);
	public static final SoundType ECHO_GLASS = new SoundType(0.8F, 1.25F,
		WWSounds.BLOCK_ECHO_GLASS_BREAK,
		WWSounds.BLOCK_ECHO_GLASS_STEP,
		WWSounds.BLOCK_ECHO_GLASS_PLACE,
		WWSounds.BLOCK_ECHO_GLASS_CRACK,
		WWSounds.BLOCK_ECHO_GLASS_FALL
	);
	public static final SoundType GABBRO = new SoundType(1F, 1F,
		WWSounds.BLOCK_GABBRO_BREAK,
		WWSounds.BLOCK_GABBRO_STEP,
		WWSounds.BLOCK_GABBRO_PLACE,
		WWSounds.BLOCK_GABBRO_HIT,
		WWSounds.BLOCK_GABBRO_FALL
	);
	public static final SoundType GABBRO_BRICKS = new SoundType(1F, 1F,
		WWSounds.BLOCK_GABBRO_BRICKS_BREAK,
		WWSounds.BLOCK_GABBRO_BRICKS_STEP,
		WWSounds.BLOCK_GABBRO_BRICKS_PLACE,
		WWSounds.BLOCK_GABBRO_BRICKS_HIT,
		WWSounds.BLOCK_GABBRO_BRICKS_FALL
	);
	public static final SoundType MESOGLEA = new SoundType(0.8F, 1F,
		WWSounds.BLOCK_MESOGLEA_BREAK,
		WWSounds.BLOCK_MESOGLEA_STEP,
		WWSounds.BLOCK_MESOGLEA_PLACE,
		WWSounds.BLOCK_MESOGLEA_HIT,
		WWSounds.BLOCK_MESOGLEA_FALL
	);
	public static final SoundType POLLEN = new SoundType(0.8F, 1.2F,
		WWSounds.BLOCK_POLLEN_BREAK,
		WWSounds.BLOCK_POLLEN_STEP,
		WWSounds.BLOCK_POLLEN_PLACE,
		WWSounds.BLOCK_POLLEN_HIT,
		WWSounds.BLOCK_POLLEN_FALL
	);
	public static final SoundType TERMITE_MOUND = new SoundType(0.8F, 1F,
		WWSounds.BLOCK_TERMITE_MOUND_BREAK,
		WWSounds.BLOCK_TERMITE_MOUND_STEP,
		WWSounds.BLOCK_TERMITE_MOUND_PLACE,
		WWSounds.BLOCK_TERMITE_MOUND_HIT,
		WWSounds.BLOCK_TERMITE_MOUND_FALL
	);
	public static final SoundType TUMBLEWEED_PLANT = new SoundType(1F, 1F,
		WWSounds.BLOCK_TUMBLEWEED_PLANT_BREAK,
		WWSounds.BLOCK_TUMBLEWEED_PLANT_STEP,
		WWSounds.BLOCK_TUMBLEWEED_PLANT_PLACE,
		WWSounds.BLOCK_TUMBLEWEED_PLANT_HIT,
		WWSounds.BLOCK_TUMBLEWEED_PLANT_FALL
	);
	public static final SoundType AUBURN_MOSS = new SoundType(1F, 1F,
		WWSounds.BLOCK_AUBURN_MOSS_BREAK,
		WWSounds.BLOCK_AUBURN_MOSS_STEP,
		WWSounds.BLOCK_AUBURN_MOSS_PLACE,
		WWSounds.BLOCK_AUBURN_MOSS_HIT,
		WWSounds.BLOCK_AUBURN_MOSS_FALL
	);
	public static final SoundType AUBURN_MOSS_CARPET = new SoundType(1F, 1F,
		WWSounds.BLOCK_AUBURN_MOSS_CARPET_BREAK,
		WWSounds.BLOCK_AUBURN_MOSS_CARPET_STEP,
		WWSounds.BLOCK_AUBURN_MOSS_CARPET_PLACE,
		WWSounds.BLOCK_AUBURN_MOSS_CARPET_HIT,
		WWSounds.BLOCK_AUBURN_MOSS_CARPET_FALL
	);
	public static final SoundType MAPLE_LEAVES = new SoundType(1F, 1F,
		WWSounds.BLOCK_MAPLE_LEAVES_BREAK,
		WWSounds.BLOCK_MAPLE_LEAVES_STEP,
		WWSounds.BLOCK_MAPLE_LEAVES_PLACE,
		WWSounds.BLOCK_MAPLE_LEAVES_HIT,
		WWSounds.BLOCK_MAPLE_LEAVES_FALL
	);
	public static final SoundType MAPLE_WOOD = new SoundType(1F, 1F,
		WWSounds.BLOCK_MAPLE_WOOD_BREAK,
		WWSounds.BLOCK_MAPLE_WOOD_STEP,
		WWSounds.BLOCK_MAPLE_WOOD_PLACE,
		WWSounds.BLOCK_MAPLE_WOOD_HIT,
		WWSounds.BLOCK_MAPLE_WOOD_FALL
	);
	public static final SoundType MAPLE_WOOD_HANGING_SIGN = new SoundType(1F, 1F,
		WWSounds.BLOCK_MAPLE_WOOD_HANGING_SIGN_BREAK,
		WWSounds.BLOCK_MAPLE_WOOD_HANGING_SIGN_STEP,
		WWSounds.BLOCK_MAPLE_WOOD_HANGING_SIGN_PLACE,
		WWSounds.BLOCK_MAPLE_WOOD_HANGING_SIGN_HIT,
		WWSounds.BLOCK_MAPLE_WOOD_HANGING_SIGN_FALL
	);

	//VANILLA SOUNDS
	public static final SoundType CLAY = new SoundType(0.9F, 1F,
		WWSounds.BLOCK_CLAY_BREAK,
		WWSounds.BLOCK_CLAY_STEP,
		WWSounds.BLOCK_CLAY_PLACE,
		WWSounds.BLOCK_CLAY_HIT,
		WWSounds.BLOCK_CLAY_FALL
	);
	public static final SoundType CACTUS = new SoundType(0.8F, 1F,
		WWSounds.BLOCK_CACTUS_BREAK,
		WWSounds.BLOCK_CACTUS_STEP,
		WWSounds.BLOCK_CACTUS_PLACE,
		WWSounds.BLOCK_CACTUS_HIT,
		WWSounds.BLOCK_CACTUS_FALL
	);
	public static final SoundType GRAVEL = new SoundType(0.8F, 1F,
		WWSounds.BLOCK_GRAVEL_BREAK,
		WWSounds.BLOCK_GRAVEL_STEP,
		WWSounds.BLOCK_GRAVEL_PLACE,
		WWSounds.BLOCK_GRAVEL_HIT,
		WWSounds.BLOCK_GRAVEL_FALL
	);
	public static final SoundType MUSHROOM = new SoundType(1F, 1F,
		WWSounds.BLOCK_MUSHROOM_BREAK,
		WWSounds.BLOCK_MUSHROOM_STEP,
		WWSounds.BLOCK_MUSHROOM_PLACE,
		WWSounds.BLOCK_MUSHROOM_HIT,
		WWSounds.BLOCK_MUSHROOM_FALL
	);
	public static final SoundType MUSHROOM_BLOCK = new SoundType(1F, 1F,
		WWSounds.BLOCK_MUSHROOM_BLOCK_BREAK,
		WWSounds.BLOCK_MUSHROOM_BLOCK_STEP,
		WWSounds.BLOCK_MUSHROOM_BLOCK_PLACE,
		WWSounds.BLOCK_MUSHROOM_BLOCK_HIT,
		WWSounds.BLOCK_MUSHROOM_BLOCK_FALL
	);
	public static final SoundType ICE = new SoundType(1F, 1F,
		WWSounds.BLOCK_ICE_BREAK,
		WWSounds.BLOCK_ICE_STEP,
		WWSounds.BLOCK_ICE_PLACE,
		WWSounds.BLOCK_ICE_HIT,
		WWSounds.BLOCK_ICE_FALL
	);
	public static final SoundType FROSTED_ICE = new SoundType(1F, 1F,
		WWSounds.BLOCK_FROSTED_ICE_BREAK,
		WWSounds.BLOCK_FROSTED_ICE_STEP,
		WWSounds.BLOCK_FROSTED_ICE_PLACE,
		WWSounds.BLOCK_FROSTED_ICE_HIT,
		WWSounds.BLOCK_FROSTED_ICE_FALL
	);
	public static final SoundType LILY_PAD = new SoundType(1F, 1F,
		SoundEvents.BIG_DRIPLEAF_BREAK,
		SoundEvents.BIG_DRIPLEAF_STEP,
		SoundEvents.LILY_PAD_PLACE,
		SoundEvents.BIG_DRIPLEAF_HIT,
		SoundEvents.BIG_DRIPLEAF_FALL
	);
	public static final SoundType SAPLING = new SoundType(1F, 1F,
		WWSounds.BLOCK_SAPLING_BREAK,
		WWSounds.BLOCK_SAPLING_STEP,
		WWSounds.BLOCK_SAPLING_PLACE,
		WWSounds.BLOCK_SAPLING_HIT,
		WWSounds.BLOCK_SAPLING_FALL
	);
	public static final SoundType SUGARCANE = new SoundType(1F, 1F,
		WWSounds.BLOCK_SUGAR_CANE_BREAK,
		WWSounds.BLOCK_SUGAR_CANE_STEP,
		WWSounds.BLOCK_SUGAR_CANE_PLACE,
		WWSounds.BLOCK_SUGAR_CANE_HIT,
		WWSounds.BLOCK_SUGAR_CANE_FALL
	);
	public static final SoundType COARSE_DIRT = new SoundType(0.8F, 1F,
		WWSounds.BLOCK_COARSE_DIRT_BREAK,
		WWSounds.BLOCK_COARSE_DIRT_STEP,
		WWSounds.BLOCK_COARSE_DIRT_PLACE,
		WWSounds.BLOCK_COARSE_DIRT_HIT,
		WWSounds.BLOCK_COARSE_DIRT_FALL
	);
	public static final SoundType SANDSTONE = new SoundType(0.7F, 1.1F,
		WWSounds.BLOCK_SANDSTONE_BREAK,
		WWSounds.BLOCK_SANDSTONE_STEP,
		WWSounds.BLOCK_SANDSTONE_PLACE,
		WWSounds.BLOCK_SANDSTONE_HIT,
		WWSounds.BLOCK_SANDSTONE_FALL
	);
	public static final SoundType SCORCHED_SAND = new SoundType(0.8F, 1F,
		WWSounds.BLOCK_SCORCHED_SAND_BREAK,
		WWSounds.BLOCK_SCORCHED_SAND_STEP,
		WWSounds.BLOCK_SCORCHED_SAND_PLACE,
		WWSounds.BLOCK_SCORCHED_SAND_HIT,
		WWSounds.BLOCK_SCORCHED_SAND_FALL
	);
	public static final SoundType REINFORCED_DEEPSLATE = new SoundType(1F, 1F,
		WWSounds.BLOCK_REINFORCED_DEEPSLATE_BREAK,
		WWSounds.BLOCK_REINFORCED_DEEPSLATE_STEP,
		WWSounds.BLOCK_REINFORCED_DEEPSLATE_PLACE,
		WWSounds.BLOCK_REINFORCED_DEEPSLATE_HIT,
		WWSounds.BLOCK_REINFORCED_DEEPSLATE_FALL
	);
	public static final SoundType MAGMA = new SoundType(1F, 0.9F,
		WWSounds.BLOCK_MAGMA_BREAK,
		WWSounds.BLOCK_MAGMA_STEP,
		WWSounds.BLOCK_MAGMA_PLACE,
		WWSounds.BLOCK_MAGMA_HIT,
		WWSounds.BLOCK_MAGMA_FALL
	);
	public static final SoundType MELON = new SoundType(1F, 1F,
		WWSounds.BLOCK_MELON_BREAK,
		WWSounds.BLOCK_MELON_STEP,
		WWSounds.BLOCK_MELON_PLACE,
		WWSounds.BLOCK_MELON_HIT,
		WWSounds.BLOCK_MELON_FALL
	);
	public static final SoundType SHORT_GRASS = new SoundType(1F, 1F,
		WWSounds.BLOCK_SHORT_GRASS_BREAK,
		SoundEvents.GRASS_STEP,
		WWSounds.BLOCK_SHORT_GRASS_PLACE,
		SoundEvents.GRASS_HIT,
		SoundEvents.GRASS_FALL
	);

	private WWSoundTypes() {
		throw new UnsupportedOperationException("WWSoundTypes contains only static declarations.");
	}

	public static void init() {
	}
}
