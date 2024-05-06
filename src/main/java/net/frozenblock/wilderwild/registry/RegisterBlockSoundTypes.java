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

package net.frozenblock.wilderwild.registry;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;

public final class RegisterBlockSoundTypes {
	//WILDER WILD SOUNDS
	public static final SoundType ALGAE = new SoundType(0.8F, 1.0F,
		RegisterSounds.BLOCK_ALGAE_BREAK,
		RegisterSounds.BLOCK_ALGAE_STEP,
		RegisterSounds.BLOCK_ALGAE_PLACE,
		RegisterSounds.BLOCK_ALGAE_HIT,
		RegisterSounds.BLOCK_ALGAE_FALL
	);
	public static final SoundType BAOBAB_NUT = new SoundType(1.0F, 1.0F,
		RegisterSounds.BLOCK_BAOBAB_NUT_BREAK,
		RegisterSounds.BLOCK_BAOBAB_NUT_STEP,
		RegisterSounds.BLOCK_BAOBAB_NUT_PLACE,
		RegisterSounds.BLOCK_BAOBAB_NUT_HIT,
		RegisterSounds.BLOCK_BAOBAB_NUT_FALL
	);
	public static final SoundType COCONUT = new SoundType(1.0F, 1.0F,
		RegisterSounds.BLOCK_COCONUT_BREAK,
		RegisterSounds.BLOCK_COCONUT_STEP,
		RegisterSounds.BLOCK_COCONUT_PLACE,
		RegisterSounds.BLOCK_COCONUT_HIT,
		RegisterSounds.BLOCK_COCONUT_FALL
	);
	public static final SoundType OSSEOUS_SCULK = new SoundType(1.0F, 1.0F,
		RegisterSounds.BLOCK_OSSEOUS_SCULK_BREAK,
		RegisterSounds.BLOCK_OSSEOUS_SCULK_STEP,
		RegisterSounds.BLOCK_OSSEOUS_SCULK_PLACE,
		RegisterSounds.BLOCK_OSSEOUS_SCULK_HIT,
		RegisterSounds.BLOCK_OSSEOUS_SCULK_FALL
	);
	public static final SoundType NEMATOCYST = new SoundType(1.0F, 1.0F,
		RegisterSounds.BLOCK_NEMATOCYST_BREAK,
		RegisterSounds.BLOCK_NEMATOCYST_STEP,
		RegisterSounds.BLOCK_NEMATOCYST_PLACE,
		RegisterSounds.BLOCK_NEMATOCYST_HIT,
		RegisterSounds.BLOCK_NEMATOCYST_FALL
	);
	public static final SoundType NULL_BLOCK = new SoundType(1.0F, 1.0F,
		RegisterSounds.BLOCK_NULL_BLOCK_BREAK,
		RegisterSounds.BLOCK_NULL_BLOCK_STEP,
		RegisterSounds.BLOCK_NULL_BLOCK_PLACE,
		RegisterSounds.BLOCK_NULL_BLOCK_HIT,
		RegisterSounds.BLOCK_NULL_BLOCK_FALL
	);
	public static final SoundType HANGING_TENDRIL = new SoundType(1.0F, 1.25F,
		RegisterSounds.BLOCK_HANGING_TENDRIL_BREAK,
		RegisterSounds.BLOCK_HANGING_TENDRIL_STEP,
		RegisterSounds.BLOCK_HANGING_TENDRIL_PLACE,
		RegisterSounds.BLOCK_HANGING_TENDRIL_HIT,
		RegisterSounds.BLOCK_HANGING_TENDRIL_FALL
	);
	public static final SoundType HOLLOWED_LOG = new SoundType(1.0F, 1.0F,
		RegisterSounds.BLOCK_HOLLOWED_LOG_BREAK,
		RegisterSounds.BLOCK_HOLLOWED_LOG_STEP,
		RegisterSounds.BLOCK_HOLLOWED_LOG_PLACE,
		RegisterSounds.BLOCK_HOLLOWED_LOG_HIT,
		RegisterSounds.BLOCK_HOLLOWED_LOG_FALL
	);
	public static final SoundType HOLLOWED_CHERRY_LOG = new SoundType(1.0F, 1.0F,
		RegisterSounds.BLOCK_HOLLOWED_CHERRY_LOG_BREAK,
		RegisterSounds.BLOCK_HOLLOWED_CHERRY_LOG_STEP,
		RegisterSounds.BLOCK_HOLLOWED_CHERRY_LOG_PLACE,
		RegisterSounds.BLOCK_HOLLOWED_CHERRY_LOG_HIT,
		RegisterSounds.BLOCK_HOLLOWED_CHERRY_LOG_FALL
	);
	public static final SoundType HOLLOWED_STEM = new SoundType(1.0F, 1.0F,
		RegisterSounds.BLOCK_HOLLOWED_STEM_BREAK,
		RegisterSounds.BLOCK_HOLLOWED_STEM_STEP,
		RegisterSounds.BLOCK_HOLLOWED_STEM_PLACE,
		RegisterSounds.BLOCK_HOLLOWED_STEM_HIT,
		RegisterSounds.BLOCK_HOLLOWED_STEM_FALL
	);
	public static final SoundType ECHO_GLASS = new SoundType(0.8F, 1.25F,
		RegisterSounds.BLOCK_ECHO_GLASS_BREAK,
		RegisterSounds.BLOCK_ECHO_GLASS_STEP,
		RegisterSounds.BLOCK_ECHO_GLASS_PLACE,
		RegisterSounds.BLOCK_ECHO_GLASS_CRACK,
		RegisterSounds.BLOCK_ECHO_GLASS_FALL
	);
	public static final SoundType GEYSER = new SoundType(1.0F, 1.0F,
		RegisterSounds.BLOCK_GEYSER_BREAK,
		RegisterSounds.BLOCK_GEYSER_STEP,
		RegisterSounds.BLOCK_GEYSER_PLACE,
		RegisterSounds.BLOCK_GEYSER_HIT,
		RegisterSounds.BLOCK_GEYSER_FALL
	);
	public static final SoundType MAGMA = new SoundType(1.0F, 1.0F,
		RegisterSounds.BLOCK_MAGMA_BREAK,
		RegisterSounds.BLOCK_MAGMA_STEP,
		RegisterSounds.BLOCK_MAGMA_PLACE,
		RegisterSounds.BLOCK_MAGMA_HIT,
		RegisterSounds.BLOCK_MAGMA_FALL
	);
	public static final SoundType MESOGLEA = new SoundType(0.8F, 1.0F,
		RegisterSounds.BLOCK_MESOGLEA_BREAK,
		RegisterSounds.BLOCK_MESOGLEA_STEP,
		RegisterSounds.BLOCK_MESOGLEA_PLACE,
		RegisterSounds.BLOCK_MESOGLEA_HIT,
		RegisterSounds.BLOCK_MESOGLEA_FALL
	);
	public static final SoundType POLLEN = new SoundType(0.5F, 1.2F,
		RegisterSounds.BLOCK_POLLEN_BREAK,
		RegisterSounds.BLOCK_POLLEN_STEP,
		RegisterSounds.BLOCK_POLLEN_PLACE,
		RegisterSounds.BLOCK_POLLEN_HIT,
		RegisterSounds.BLOCK_POLLEN_FALL
	);
	public static final SoundType TERMITEMOUND = new SoundType(0.8F, 1.0F,
		RegisterSounds.BLOCK_TERMITE_MOUND_BREAK,
		RegisterSounds.BLOCK_TERMITE_MOUND_STEP,
		RegisterSounds.BLOCK_TERMITE_MOUND_PLACE,
		RegisterSounds.BLOCK_TERMITE_MOUND_HIT,
		RegisterSounds.BLOCK_TERMITE_MOUND_FALL
	);
	public static final SoundType TUMBLEWEED_PLANT = new SoundType(1.0F, 1.0F,
		RegisterSounds.BLOCK_TUMBLEWEED_PLANT_BREAK,
		RegisterSounds.BLOCK_TUMBLEWEED_PLANT_STEP,
		RegisterSounds.BLOCK_TUMBLEWEED_PLANT_PLACE,
		RegisterSounds.BLOCK_TUMBLEWEED_PLANT_HIT,
		RegisterSounds.BLOCK_TUMBLEWEED_PLANT_FALL
	);
	public static final SoundType PALM_CROWN = new SoundType(1.0F, 1.0F,
		RegisterSounds.BLOCK_PALM_CROWN_BREAK,
		RegisterSounds.BLOCK_PALM_CROWN_STEP,
		RegisterSounds.BLOCK_PALM_CROWN_PLACE,
		RegisterSounds.BLOCK_PALM_CROWN_HIT,
		RegisterSounds.BLOCK_PALM_CROWN_FALL
	);
	public static final SoundType CLAY = new SoundType(0.9F, 1.0F,
		RegisterSounds.BLOCK_CLAY_BREAK,
		RegisterSounds.BLOCK_CLAY_STEP,
		RegisterSounds.BLOCK_CLAY_PLACE,
		RegisterSounds.BLOCK_CLAY_HIT,
		RegisterSounds.BLOCK_CLAY_FALL
	);

	//VANILLA SOUNDS
	public static final SoundType CACTI = new SoundType(0.8F, 1.0F,
		RegisterSounds.BLOCK_CACTUS_BREAK,
		RegisterSounds.BLOCK_CACTUS_STEP,
		RegisterSounds.BLOCK_CACTUS_PLACE,
		RegisterSounds.BLOCK_CACTUS_HIT,
		RegisterSounds.BLOCK_CACTUS_FALL
	);
	public static final SoundType GRAVEL = new SoundType(0.8F, 1.0F,
		RegisterSounds.BLOCK_GRAVEL_BREAK,
		RegisterSounds.BLOCK_GRAVEL_STEP,
		RegisterSounds.BLOCK_GRAVEL_PLACE,
		RegisterSounds.BLOCK_GRAVEL_HIT,
		RegisterSounds.BLOCK_GRAVEL_FALL
	);
	public static final SoundType MUSHROOM = new SoundType(1.0F, 1.0F,
		RegisterSounds.BLOCK_MUSHROOM_BREAK,
		RegisterSounds.BLOCK_MUSHROOM_STEP,
		RegisterSounds.BLOCK_MUSHROOM_PLACE,
		RegisterSounds.BLOCK_MUSHROOM_HIT,
		RegisterSounds.BLOCK_MUSHROOM_FALL
	);
	public static final SoundType MUSHROOM_BLOCK = new SoundType(1.0F, 1.0F,
		RegisterSounds.BLOCK_MUSHROOM_BLOCK_BREAK,
		RegisterSounds.BLOCK_MUSHROOM_BLOCK_STEP,
		RegisterSounds.BLOCK_MUSHROOM_BLOCK_PLACE,
		RegisterSounds.BLOCK_MUSHROOM_BLOCK_HIT,
		RegisterSounds.BLOCK_MUSHROOM_BLOCK_FALL
	);
	public static final SoundType ICE = new SoundType(1.0F, 1.0F,
		RegisterSounds.BLOCK_ICE_BREAK,
		RegisterSounds.BLOCK_ICE_STEP,
		RegisterSounds.BLOCK_ICE_PLACE,
		RegisterSounds.BLOCK_ICE_HIT,
		RegisterSounds.BLOCK_ICE_FALL
	);
	public static final SoundType FROSTED_ICE = new SoundType(1.0F, 1.0F,
		RegisterSounds.BLOCK_FROSTED_ICE_BREAK,
		RegisterSounds.BLOCK_FROSTED_ICE_STEP,
		RegisterSounds.BLOCK_FROSTED_ICE_PLACE,
		RegisterSounds.BLOCK_FROSTED_ICE_HIT,
		RegisterSounds.BLOCK_FROSTED_ICE_FALL
	);
	public static final SoundType LEAVES = new SoundType(1.0F, 1.0F,
		RegisterSounds.BLOCK_LEAVES_BREAK,
		RegisterSounds.BLOCK_LEAVES_STEP,
		RegisterSounds.BLOCK_LEAVES_PLACE,
		RegisterSounds.BLOCK_LEAVES_HIT,
		RegisterSounds.BLOCK_LEAVES_FALL
	);
	public static final SoundType FLOWER = new SoundType(0.8F, 1.0F,
		RegisterSounds.BLOCK_FLOWER_BREAK,
		RegisterSounds.BLOCK_FLOWER_STEP,
		RegisterSounds.BLOCK_FLOWER_PLACE,
		RegisterSounds.BLOCK_FLOWER_HIT,
		RegisterSounds.BLOCK_FLOWER_FALL
	);
	public static final SoundType LILYPAD = new SoundType(1.0F, 1.0F,
		SoundEvents.BIG_DRIPLEAF_BREAK,
		SoundEvents.BIG_DRIPLEAF_STEP,
		SoundEvents.LILY_PAD_PLACE,
		SoundEvents.BIG_DRIPLEAF_HIT,
		SoundEvents.BIG_DRIPLEAF_FALL
	);
	public static final SoundType SAPLING = new SoundType(1.0F, 1.0F,
		RegisterSounds.BLOCK_SAPLING_BREAK,
		RegisterSounds.BLOCK_SAPLING_STEP,
		RegisterSounds.BLOCK_SAPLING_PLACE,
		RegisterSounds.BLOCK_SAPLING_HIT,
		RegisterSounds.BLOCK_SAPLING_FALL
	);
	public static final SoundType SUGARCANE = new SoundType(1.0F, 1.0F,
		RegisterSounds.BLOCK_SUGAR_CANE_BREAK,
		RegisterSounds.BLOCK_SUGAR_CANE_STEP,
		RegisterSounds.BLOCK_SUGAR_CANE_PLACE,
		RegisterSounds.BLOCK_SUGAR_CANE_HIT,
		RegisterSounds.BLOCK_SUGAR_CANE_FALL
	);
	public static final SoundType COARSEDIRT = new SoundType(0.8F, 1.0F,
		RegisterSounds.BLOCK_COARSE_DIRT_BREAK,
		RegisterSounds.BLOCK_COARSE_DIRT_STEP,
		RegisterSounds.BLOCK_COARSE_DIRT_PLACE,
		RegisterSounds.BLOCK_COARSE_DIRT_HIT,
		RegisterSounds.BLOCK_COARSE_DIRT_FALL
	);
	public static final SoundType SANDSTONE = new SoundType(0.7F, 1.1F,
		RegisterSounds.BLOCK_SANDSTONE_BREAK,
		RegisterSounds.BLOCK_SANDSTONE_STEP,
		RegisterSounds.BLOCK_SANDSTONE_PLACE,
		RegisterSounds.BLOCK_SANDSTONE_HIT,
		RegisterSounds.BLOCK_SANDSTONE_FALL
	);
	public static final SoundType SCORCHEDSAND = new SoundType(0.8F, 1.0F,
		RegisterSounds.BLOCK_SCORCHED_SAND_BREAK,
		RegisterSounds.BLOCK_SCORCHED_SAND_STEP,
		RegisterSounds.BLOCK_SCORCHED_SAND_PLACE,
		RegisterSounds.BLOCK_SCORCHED_SAND_HIT,
		RegisterSounds.BLOCK_SCORCHED_SAND_FALL
	);
	public static final SoundType REINFORCEDDEEPSLATE = new SoundType(1.0F, 1.0F,
		RegisterSounds.BLOCK_REINFORCED_DEEPSLATE_BREAK,
		RegisterSounds.BLOCK_REINFORCED_DEEPSLATE_STEP,
		RegisterSounds.BLOCK_REINFORCED_DEEPSLATE_PLACE,
		RegisterSounds.BLOCK_REINFORCED_DEEPSLATE_HIT,
		RegisterSounds.BLOCK_REINFORCED_DEEPSLATE_FALL
	);

	private RegisterBlockSoundTypes() {
		throw new UnsupportedOperationException("RegisterBlockEntities contains only static declarations.");
	}

	public static void init() {
		//Just to make sure this class gets loaded.
	}
}
