/*
 * Copyright 2022-2023 FrozenBlock
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

import static net.frozenblock.wilderwild.registry.RegisterSounds.*;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;

public final class RegisterBlockSoundTypes {
	private RegisterBlockSoundTypes() {
		throw new UnsupportedOperationException("RegisterBlockEntities contains only static declarations.");
	}

	public static final SoundType ALGAE = new SoundType(0.8F, 1.0F,
			BLOCK_ALGAE_BREAK,
			BLOCK_ALGAE_STEP,
			BLOCK_ALGAE_PLACE,
			BLOCK_ALGAE_HIT,
			BLOCK_ALGAE_FALL
	);

    public static final SoundType BAOBAB_NUT = new SoundType(1.0F, 1.0F,
            BLOCK_BAOBAB_NUT_BREAK,
            BLOCK_BAOBAB_NUT_STEP,
            BLOCK_BAOBAB_NUT_PLACE,
            BLOCK_BAOBAB_NUT_HIT,
            BLOCK_BAOBAB_NUT_FALL
    );

	public static final SoundType COCONUT = new SoundType(1.0F, 1.0F,
			BLOCK_COCONUT_BREAK,
			BLOCK_COCONUT_STEP,
			BLOCK_COCONUT_PLACE,
			BLOCK_COCONUT_HIT,
			BLOCK_COCONUT_FALL
	);

    public static final SoundType CLAY_BLOCK = new SoundType(0.9F, 1.0F,
            BLOCK_CLAY_BREAK,
            BLOCK_CLAY_STEP,
            BLOCK_CLAY_PLACE,
            BLOCK_CLAY_HIT,
            BLOCK_CLAY_FALL
    );

	public static final SoundType CACTI = new SoundType(0.8F, 1.0F,
			BLOCK_CACTUS_BREAK,
			BLOCK_CACTUS_STEP,
			BLOCK_CACTUS_PLACE,
			BLOCK_CACTUS_HIT,
			BLOCK_CACTUS_FALL
	);

    public static final SoundType GRAVELSOUNDS = new SoundType(0.8F, 1.0F,
            BLOCK_GRAVEL_BREAK,
            BLOCK_GRAVEL_STEP,
            BLOCK_GRAVEL_PLACE,
            BLOCK_GRAVEL_HIT,
            BLOCK_GRAVEL_FALL
    );

    public static final SoundType OSSEOUS_SCULK = new SoundType(1.0F, 1.0F,
            BLOCK_OSSEOUS_SCULK_BREAK,
            BLOCK_OSSEOUS_SCULK_STEP,
            BLOCK_OSSEOUS_SCULK_PLACE,
            BLOCK_OSSEOUS_SCULK_HIT,
            BLOCK_OSSEOUS_SCULK_FALL
    );

    public static final SoundType NEMATOCYST = new SoundType(1.0F, 1.0F,
            BLOCK_NEMATOCYST_BREAK,
            BLOCK_NEMATOCYST_STEP,
            BLOCK_NEMATOCYST_PLACE,
            BLOCK_NEMATOCYST_HIT,
            BLOCK_NEMATOCYST_FALL
    );

    public static final SoundType NULL_BLOCK = new SoundType(1.0F, 1.0F,
            BLOCK_NULL_BLOCK_BREAK,
            BLOCK_NULL_BLOCK_STEP,
            BLOCK_NULL_BLOCK_PLACE,
            BLOCK_NULL_BLOCK_HIT,
            BLOCK_NULL_BLOCK_FALL
    );

    public static final SoundType HANGING_TENDRIL = new SoundType(1.0F, 1.25F,
            BLOCK_HANGING_TENDRIL_BREAK,
            BLOCK_HANGING_TENDRIL_STEP,
            BLOCK_HANGING_TENDRIL_PLACE,
            BLOCK_HANGING_TENDRIL_HIT,
            BLOCK_HANGING_TENDRIL_FALL
    );

    public static final SoundType HOLLOWED_LOG = new SoundType(1.0F, 1.0F,
            BLOCK_HOLLOWED_LOG_BREAK,
            BLOCK_HOLLOWED_LOG_STEP,
            BLOCK_HOLLOWED_LOG_PLACE,
            BLOCK_HOLLOWED_LOG_HIT,
            BLOCK_HOLLOWED_LOG_FALL
    );

	public static final SoundType HOLLOWED_STEM = new SoundType(1.0F, 1.0F,
			BLOCK_HOLLOWED_STEM_BREAK,
			BLOCK_HOLLOWED_STEM_STEP,
			BLOCK_HOLLOWED_STEM_PLACE,
			BLOCK_HOLLOWED_STEM_HIT,
			BLOCK_HOLLOWED_STEM_FALL
	);

    public static final SoundType ECHO_GLASS = new SoundType(0.8F, 1.25F,
            BLOCK_ECHO_GLASS_BREAK,
            BLOCK_ECHO_GLASS_STEP,
            BLOCK_ECHO_GLASS_PLACE,
            BLOCK_ECHO_GLASS_CRACK,
            BLOCK_ECHO_GLASS_FALL
    );

    public static final SoundType MESOGLEA = new SoundType(0.8F, 1.0F,
            BLOCK_MESOGLEA_BREAK,
            BLOCK_MESOGLEA_STEP,
            BLOCK_MESOGLEA_PLACE,
            BLOCK_MESOGLEA_HIT,
            BLOCK_MESOGLEA_FALL
    );

    public static final SoundType MUSHROOM = new SoundType(1.0F, 1.0F,
            BLOCK_MUSHROOM_BREAK,
            BLOCK_MUSHROOM_STEP,
            BLOCK_MUSHROOM_PLACE,
            BLOCK_MUSHROOM_HIT,
            BLOCK_MUSHROOM_FALL
    );

    public static final SoundType MUSHROOM_BLOCK = new SoundType(1.0F, 1.0F,
            BLOCK_MUSHROOM_BLOCK_BREAK,
            BLOCK_MUSHROOM_BLOCK_STEP,
            BLOCK_MUSHROOM_BLOCK_PLACE,
            BLOCK_MUSHROOM_BLOCK_HIT,
            BLOCK_MUSHROOM_BLOCK_FALL
    );

    public static final SoundType ICE_BLOCKS = new SoundType(1.0F, 1.25F,
            BLOCK_ICE_BREAK,
            BLOCK_ICE_STEP,
            BLOCK_ICE_PLACE,
            BLOCK_ICE_HIT,
            BLOCK_ICE_FALL
    );

    public static final SoundType LEAVES = new SoundType(1.0F, 1.0F,
            BLOCK_LEAVES_BREAK,
            BLOCK_LEAVES_STEP,
            BLOCK_LEAVES_PLACE,
            BLOCK_LEAVES_HIT,
            BLOCK_LEAVES_FALL
    );

    public static final SoundType FLOWER = new SoundType(0.8F, 1.0F,
            BLOCK_FLOWER_BREAK,
            BLOCK_FLOWER_STEP,
            BLOCK_FLOWER_PLACE,
            BLOCK_FLOWER_HIT,
            BLOCK_FLOWER_FALL
    );

    public static final SoundType WEB = new SoundType(1.0F, 1.5F,
            BLOCK_COBWEB_BREAK,
            BLOCK_COBWEB_STEP,
            BLOCK_COBWEB_PLACE,
            BLOCK_COBWEB_HIT,
            BLOCK_COBWEB_FALL
    );

    public static final SoundType LILYPAD = new SoundType(1.0F, 1.0F,
            SoundEvents.BIG_DRIPLEAF_BREAK,
            SoundEvents.BIG_DRIPLEAF_STEP,
            SoundEvents.LILY_PAD_PLACE,
            SoundEvents.BIG_DRIPLEAF_HIT,
            SoundEvents.BIG_DRIPLEAF_FALL
    );

	public static final SoundType SAPLING = new SoundType(1.0F, 1.0F,
			BLOCK_SAPLING_BREAK,
			BLOCK_SAPLING_STEP,
			BLOCK_SAPLING_PLACE,
			BLOCK_SAPLING_HIT,
			BLOCK_SAPLING_FALL
	);

    public static final SoundType SUGARCANE = new SoundType(1.0F, 1.0F,
            BLOCK_SUGAR_CANE_BREAK,
            BLOCK_SUGAR_CANE_STEP,
            BLOCK_SUGAR_CANE_PLACE,
            BLOCK_SUGAR_CANE_HIT,
            BLOCK_SUGAR_CANE_FALL
    );

    public static final SoundType COARSEDIRT = new SoundType(0.8F, 1.0F,
            BLOCK_COARSE_DIRT_BREAK,
            BLOCK_COARSE_DIRT_STEP,
            BLOCK_COARSE_DIRT_PLACE,
            BLOCK_COARSE_DIRT_HIT,
            BLOCK_COARSE_DIRT_FALL
    );

	public static final SoundType SAND_STONE = new SoundType(0.7F, 1.1F,
			BLOCK_SANDSTONE_BREAK,
			BLOCK_SANDSTONE_STEP,
			BLOCK_SANDSTONE_PLACE,
			BLOCK_SANDSTONE_HIT,
			BLOCK_SANDSTONE_FALL
	);
	public static final SoundType SCORCHEDSAND = new SoundType(0.8F, 1.0F,
			BLOCK_SCORCHED_SAND_BREAK,
			BLOCK_SCORCHED_SAND_STEP,
			BLOCK_SCORCHED_SAND_PLACE,
			BLOCK_SCORCHED_SAND_HIT,
			BLOCK_SCORCHED_SAND_FALL
	);

	public static final SoundType TERMITEMOUND = new SoundType(0.8F, 1.0F,
			BLOCK_TERMITE_MOUND_BREAK,
			BLOCK_TERMITE_MOUND_STEP,
			BLOCK_TERMITE_MOUND_PLACE,
			BLOCK_TERMITE_MOUND_HIT,
			BLOCK_TERMITE_MOUND_FALL
	);

    public static final SoundType REINFORCEDDEEPSLATE = new SoundType(1.0F, 1.0F,
            BLOCK_REINFORCED_DEEPSLATE_BREAK,
            BLOCK_REINFORCED_DEEPSLATE_STEP,
            BLOCK_REINFORCED_DEEPSLATE_PLACE,
            BLOCK_REINFORCED_DEEPSLATE_HIT,
            BLOCK_REINFORCED_DEEPSLATE_FALL
    );

	public static final SoundType TUMBLEWEED_PLANT = new SoundType(1.0F, 1.0F,
			BLOCK_TUMBLEWEED_PLANT_BREAK,
			BLOCK_TUMBLEWEED_PLANT_STEP,
			BLOCK_TUMBLEWEED_PLANT_PLACE,
			BLOCK_TUMBLEWEED_PLANT_HIT,
			BLOCK_TUMBLEWEED_PLANT_FALL
	);

	public static final SoundType PALM_CROWN = new SoundType(1.0F, 1.0F,
			BLOCK_PALM_CROWN_BREAK,
			BLOCK_PALM_CROWN_STEP,
			BLOCK_PALM_CROWN_PLACE,
			BLOCK_PALM_CROWN_HIT,
			BLOCK_PALM_CROWN_FALL
	);

    public static void init() {
        //Just to make sure this class gets loaded.
    }
}
