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

import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.sounds.BlockSoundSet;

public final class WWBlockSoundSets {
	public static final ResourceKey<BlockSoundSet> ALGAE = createKey("algae");
	public static final ResourceKey<BlockSoundSet> BAOBAB_NUT = createKey("baobab_nut");
	public static final ResourceKey<BlockSoundSet> COCONUT = createKey("coconut");
	public static final ResourceKey<BlockSoundSet> OSSEOUS_SCULK = createKey("osseous_sculk");
	public static final ResourceKey<BlockSoundSet> NEMATOCYST = createKey("nematocyst");
	public static final ResourceKey<BlockSoundSet> NULL_BLOCK = createKey("null_block");
	public static final ResourceKey<BlockSoundSet> HANGING_TENDRIL = createKey("hanging_tendril");
	public static final ResourceKey<BlockSoundSet> HOLLOWED_LOG = createKey("hollowed_log");
	public static final ResourceKey<BlockSoundSet> HOLLOWED_CHERRY_LOG = createKey("hollowed_cherry_log");
	public static final ResourceKey<BlockSoundSet> HOLLOWED_MAPLE_LOG = createKey("hollowed_maple_log");
	public static final ResourceKey<BlockSoundSet> HOLLOWED_PALE_OAK_LOG = createKey("hollowed_pale_oak_log");
	public static final ResourceKey<BlockSoundSet> HOLLOWED_STEM = createKey("hollowed_stem");
	public static final ResourceKey<BlockSoundSet> ECHO_GLASS = createKey("echo_glass");
	public static final ResourceKey<BlockSoundSet> GABBRO = createKey("gabbro");
	public static final ResourceKey<BlockSoundSet> GABBRO_BRICKS = createKey("gabbro_bricks");
	public static final ResourceKey<BlockSoundSet> GEOTHERMAL_VENT = createKey("geothermal_vent");
	public static final ResourceKey<BlockSoundSet> MESOGLEA = createKey("mesoglea");
	public static final ResourceKey<BlockSoundSet> POLLEN = createKey("pollen");
	public static final ResourceKey<BlockSoundSet> TERMITE_MOUND = createKey("termite_mound");
	public static final ResourceKey<BlockSoundSet> TUMBLEWEED = createKey("tumbleweed");
	public static final ResourceKey<BlockSoundSet> AUBURN_MOSS = createKey("auburn_moss");
	public static final ResourceKey<BlockSoundSet> AUBURN_MOSS_CARPET = createKey("auburn_moss_carpet");
	public static final ResourceKey<BlockSoundSet> MAPLE_LEAVES = createKey("maple_leaves");
	public static final ResourceKey<BlockSoundSet> MAPLE_LEAF_LITTER = createKey("maple_leaf_litter");
	public static final ResourceKey<BlockSoundSet> MAPLE_WOOD = createKey("maple_wood");
	public static final ResourceKey<BlockSoundSet> MAPLE_WOOD_HANGING_SIGN = createKey("maple_wood_hanging_sign");
	public static final ResourceKey<BlockSoundSet> CHERRY_LEAF_LITTER = createKey("cherry_leaf_litter");
	public static final ResourceKey<BlockSoundSet> PALE_OAK_LEAF_LITTER = createKey("pale_oak_leaf_litter");
	public static final ResourceKey<BlockSoundSet> CONIFER_LEAF_LITTER = createKey("conifer_leaf_litter");
	public static final ResourceKey<BlockSoundSet> CLAY = createKey("clay");
	public static final ResourceKey<BlockSoundSet> CACTUS = createKey("cactus");
	public static final ResourceKey<BlockSoundSet> GRAVEL = createKey("gravel");
	public static final ResourceKey<BlockSoundSet> MUSHROOM = createKey("mushroom");
	public static final ResourceKey<BlockSoundSet> MUSHROOM_BLOCK = createKey("mushroom_block");
	public static final ResourceKey<BlockSoundSet> ICE = createKey("ice");
	public static final ResourceKey<BlockSoundSet> FROSTED_ICE = createKey("frosted_ice");
	public static final ResourceKey<BlockSoundSet> CONIFER_LEAVES = createKey("conifer_leaves");
	public static final ResourceKey<BlockSoundSet> PALE_OAK_LEAVES = createKey("pale_oak_leaves");
	public static final ResourceKey<BlockSoundSet> PALE_MOSS = createKey("pale_moss");
	public static final ResourceKey<BlockSoundSet> PALE_MOSS_CARPET = createKey("pale_moss_carpet");
	public static final ResourceKey<BlockSoundSet> PALE_OAK_WOOD = createKey("pale_oak_wood");
	public static final ResourceKey<BlockSoundSet> PALE_OAK_WOOD_HANGING_SIGN = createKey("pale_oak_wood_hanging_sign");
	public static final ResourceKey<BlockSoundSet> LILY_PAD = createKey("lily_pad");
	public static final ResourceKey<BlockSoundSet> SAPLING = createKey("sapling");
	public static final ResourceKey<BlockSoundSet> SUGAR_CANE = createKey("sugar_cane");
	public static final ResourceKey<BlockSoundSet> COARSE_DIRT = createKey("coarse_dirt");
	public static final ResourceKey<BlockSoundSet> SANDSTONE = createKey("sandstone");
	public static final ResourceKey<BlockSoundSet> SCORCHED_SAND = createKey("scorched_sand");
	public static final ResourceKey<BlockSoundSet> REINFORCED_DEEPSLATE = createKey("reinforced_deepslate");
	public static final ResourceKey<BlockSoundSet> MAGMA = createKey("magma");
	public static final ResourceKey<BlockSoundSet> MELON = createKey("melon");
	public static final ResourceKey<BlockSoundSet> SHORT_GRASS = createKey("short_grass");
	public static final ResourceKey<BlockSoundSet> FROZEN_GRASS = createKey("frozen_grass");
	public static final ResourceKey<BlockSoundSet> DRY_GRASS = createKey("dry_grass");
	public static final ResourceKey<BlockSoundSet> BARNACLES = createKey("barnacles");
	public static final ResourceKey<BlockSoundSet> SEA_ANEMONE = createKey("sea_anemone");
	public static final ResourceKey<BlockSoundSet> TUBE_WORMS = createKey("tube_worms");

	public static void bootstrap(BootstrapContext<BlockSoundSet> context) {
		context.register(
			ALGAE,
			new BlockSoundSet(
				0.8F,
				1F,
				WWSounds.BLOCK_ALGAE_BREAK.asHolder(),
				WWSounds.BLOCK_ALGAE_STEP.asHolder(),
				WWSounds.BLOCK_ALGAE_PLACE.asHolder(),
				WWSounds.BLOCK_ALGAE_HIT.asHolder(),
				WWSounds.BLOCK_ALGAE_FALL.asHolder()
			)
		);
		context.register(
			BAOBAB_NUT,
			new BlockSoundSet(
				WWSounds.BLOCK_BAOBAB_NUT_BREAK.asHolder(),
				WWSounds.BLOCK_BAOBAB_NUT_STEP.asHolder(),
				WWSounds.BLOCK_BAOBAB_NUT_PLACE.asHolder(),
				WWSounds.BLOCK_BAOBAB_NUT_HIT.asHolder(),
				WWSounds.BLOCK_BAOBAB_NUT_FALL.asHolder()
			)
		);
		context.register(
			COCONUT,
			new BlockSoundSet(
				WWSounds.BLOCK_COCONUT_BREAK.asHolder(),
				WWSounds.BLOCK_COCONUT_STEP.asHolder(),
				WWSounds.BLOCK_COCONUT_PLACE.asHolder(),
				WWSounds.BLOCK_COCONUT_HIT.asHolder(),
				WWSounds.BLOCK_COCONUT_FALL.asHolder()
			)
		);
		context.register(
			OSSEOUS_SCULK,
			new BlockSoundSet(
				WWSounds.BLOCK_OSSEOUS_SCULK_BREAK.asHolder(),
				WWSounds.BLOCK_OSSEOUS_SCULK_STEP.asHolder(),
				WWSounds.BLOCK_OSSEOUS_SCULK_PLACE.asHolder(),
				WWSounds.BLOCK_OSSEOUS_SCULK_HIT.asHolder(),
				WWSounds.BLOCK_OSSEOUS_SCULK_FALL.asHolder()
			)
		);
		context.register(
			NEMATOCYST,
			new BlockSoundSet(
				WWSounds.BLOCK_NEMATOCYST_BREAK.asHolder(),
				WWSounds.BLOCK_NEMATOCYST_STEP.asHolder(),
				WWSounds.BLOCK_NEMATOCYST_PLACE.asHolder(),
				WWSounds.BLOCK_NEMATOCYST_HIT.asHolder(),
				WWSounds.BLOCK_NEMATOCYST_FALL.asHolder()
			)
		);
		context.register(
			NULL_BLOCK,
			new BlockSoundSet(
				WWSounds.BLOCK_NULL_BLOCK_BREAK.asHolder(),
				WWSounds.BLOCK_NULL_BLOCK_STEP.asHolder(),
				WWSounds.BLOCK_NULL_BLOCK_PLACE.asHolder(),
				WWSounds.BLOCK_NULL_BLOCK_HIT.asHolder(),
				WWSounds.BLOCK_NULL_BLOCK_FALL.asHolder()
			)
		);
		context.register(
			HANGING_TENDRIL,
			new BlockSoundSet(
				1F,
				1.25F,
				WWSounds.BLOCK_HANGING_TENDRIL_BREAK.asHolder(),
				WWSounds.BLOCK_HANGING_TENDRIL_STEP.asHolder(),
				WWSounds.BLOCK_HANGING_TENDRIL_PLACE.asHolder(),
				WWSounds.BLOCK_HANGING_TENDRIL_HIT.asHolder(),
				WWSounds.BLOCK_HANGING_TENDRIL_FALL.asHolder()
			)
		);
		context.register(
			HOLLOWED_LOG,
			new BlockSoundSet(
				WWSounds.BLOCK_HOLLOWED_LOG_BREAK.asHolder(),
				WWSounds.BLOCK_HOLLOWED_LOG_STEP.asHolder(),
				WWSounds.BLOCK_HOLLOWED_LOG_PLACE.asHolder(),
				WWSounds.BLOCK_HOLLOWED_LOG_HIT.asHolder(),
				WWSounds.BLOCK_HOLLOWED_LOG_FALL.asHolder()
			)
		);
		context.register(
			HOLLOWED_CHERRY_LOG,
			new BlockSoundSet(
				WWSounds.BLOCK_HOLLOWED_CHERRY_LOG_BREAK.asHolder(),
				WWSounds.BLOCK_HOLLOWED_CHERRY_LOG_STEP.asHolder(),
				WWSounds.BLOCK_HOLLOWED_CHERRY_LOG_PLACE.asHolder(),
				WWSounds.BLOCK_HOLLOWED_CHERRY_LOG_HIT.asHolder(),
				WWSounds.BLOCK_HOLLOWED_CHERRY_LOG_FALL.asHolder()
			)
		);
		context.register(
			HOLLOWED_MAPLE_LOG,
			new BlockSoundSet(
				WWSounds.BLOCK_HOLLOWED_MAPLE_LOG_BREAK.asHolder(),
				WWSounds.BLOCK_HOLLOWED_MAPLE_LOG_STEP.asHolder(),
				WWSounds.BLOCK_HOLLOWED_MAPLE_LOG_PLACE.asHolder(),
				WWSounds.BLOCK_HOLLOWED_MAPLE_LOG_HIT.asHolder(),
				WWSounds.BLOCK_HOLLOWED_MAPLE_LOG_FALL.asHolder()
			)
		);
		context.register(
			HOLLOWED_PALE_OAK_LOG,
			new BlockSoundSet(
				WWSounds.BLOCK_HOLLOWED_PALE_OAK_LOG_BREAK.asHolder(),
				WWSounds.BLOCK_HOLLOWED_PALE_OAK_LOG_STEP.asHolder(),
				WWSounds.BLOCK_HOLLOWED_PALE_OAK_LOG_PLACE.asHolder(),
				WWSounds.BLOCK_HOLLOWED_PALE_OAK_LOG_HIT.asHolder(),
				WWSounds.BLOCK_HOLLOWED_PALE_OAK_LOG_FALL.asHolder()
			)
		);
		context.register(
			HOLLOWED_STEM,
			new BlockSoundSet(
				WWSounds.BLOCK_HOLLOWED_STEM_BREAK.asHolder(),
				WWSounds.BLOCK_HOLLOWED_STEM_STEP.asHolder(),
				WWSounds.BLOCK_HOLLOWED_STEM_PLACE.asHolder(),
				WWSounds.BLOCK_HOLLOWED_STEM_HIT.asHolder(),
				WWSounds.BLOCK_HOLLOWED_STEM_FALL.asHolder()
			)
		);
		context.register(
			ECHO_GLASS,
			new BlockSoundSet(
				0.8F,
				1.25F,
				WWSounds.BLOCK_ECHO_GLASS_BREAK.asHolder(),
				WWSounds.BLOCK_ECHO_GLASS_STEP.asHolder(),
				WWSounds.BLOCK_ECHO_GLASS_PLACE.asHolder(),
				WWSounds.BLOCK_ECHO_GLASS_CRACK.asHolder(),
				WWSounds.BLOCK_ECHO_GLASS_FALL.asHolder()
			)
		);
		context.register(
			GABBRO,
			new BlockSoundSet(
				WWSounds.BLOCK_GABBRO_BREAK.asHolder(),
				WWSounds.BLOCK_GABBRO_STEP.asHolder(),
				WWSounds.BLOCK_GABBRO_PLACE.asHolder(),
				WWSounds.BLOCK_GABBRO_HIT.asHolder(),
				WWSounds.BLOCK_GABBRO_FALL.asHolder()
			)
		);
		context.register(
			GABBRO_BRICKS,
			new BlockSoundSet(
				WWSounds.BLOCK_GABBRO_BRICKS_BREAK.asHolder(),
				WWSounds.BLOCK_GABBRO_BRICKS_STEP.asHolder(),
				WWSounds.BLOCK_GABBRO_BRICKS_PLACE.asHolder(),
				WWSounds.BLOCK_GABBRO_BRICKS_HIT.asHolder(),
				WWSounds.BLOCK_GABBRO_BRICKS_FALL.asHolder()
			)
		);
		context.register(
			GEOTHERMAL_VENT,
			new BlockSoundSet(
				WWSounds.BLOCK_GEOTHERMAL_VENT_BREAK.asHolder(),
				WWSounds.BLOCK_GEOTHERMAL_VENT_STEP.asHolder(),
				WWSounds.BLOCK_GEOTHERMAL_VENT_PLACE.asHolder(),
				WWSounds.BLOCK_GEOTHERMAL_VENT_HIT.asHolder(),
				WWSounds.BLOCK_GEOTHERMAL_VENT_FALL.asHolder()
			)
		);
		context.register(
			MESOGLEA,
			new BlockSoundSet(
				0.8F,
				1F,
				WWSounds.BLOCK_MESOGLEA_BREAK.asHolder(),
				WWSounds.BLOCK_MESOGLEA_STEP.asHolder(),
				WWSounds.BLOCK_MESOGLEA_PLACE.asHolder(),
				WWSounds.BLOCK_MESOGLEA_HIT.asHolder(),
				WWSounds.BLOCK_MESOGLEA_FALL.asHolder()
			)
		);
		context.register(
			POLLEN, new BlockSoundSet(
				0.8F,
				1.2F,
				WWSounds.BLOCK_POLLEN_BREAK.asHolder(),
				WWSounds.BLOCK_POLLEN_STEP.asHolder(),
				WWSounds.BLOCK_POLLEN_PLACE.asHolder(),
				WWSounds.BLOCK_POLLEN_HIT.asHolder(),
				WWSounds.BLOCK_POLLEN_FALL.asHolder()
			)
		);
		context.register(
			TERMITE_MOUND,
			new BlockSoundSet(
				0.8F,
				1F,
				WWSounds.BLOCK_TERMITE_MOUND_BREAK.asHolder(),
				WWSounds.BLOCK_TERMITE_MOUND_STEP.asHolder(),
				WWSounds.BLOCK_TERMITE_MOUND_PLACE.asHolder(),
				WWSounds.BLOCK_TERMITE_MOUND_HIT.asHolder(),
				WWSounds.BLOCK_TERMITE_MOUND_FALL.asHolder()
			)
		);
		context.register(
			TUMBLEWEED,
			new BlockSoundSet(
				WWSounds.BLOCK_TUMBLEWEED_PLANT_BREAK.asHolder(),
				WWSounds.BLOCK_TUMBLEWEED_PLANT_STEP.asHolder(),
				WWSounds.BLOCK_TUMBLEWEED_PLANT_PLACE.asHolder(),
				WWSounds.BLOCK_TUMBLEWEED_PLANT_HIT.asHolder(),
				WWSounds.BLOCK_TUMBLEWEED_PLANT_FALL.asHolder()
			)
		);
		context.register(
			AUBURN_MOSS,
			new BlockSoundSet(
				WWSounds.BLOCK_AUBURN_MOSS_BREAK.asHolder(),
				WWSounds.BLOCK_AUBURN_MOSS_STEP.asHolder(),
				WWSounds.BLOCK_AUBURN_MOSS_PLACE.asHolder(),
				WWSounds.BLOCK_AUBURN_MOSS_HIT.asHolder(),
				WWSounds.BLOCK_AUBURN_MOSS_FALL.asHolder()
			)
		);
		context.register(
			AUBURN_MOSS_CARPET,
			new BlockSoundSet(
				WWSounds.BLOCK_AUBURN_MOSS_CARPET_BREAK.asHolder(),
				WWSounds.BLOCK_AUBURN_MOSS_CARPET_STEP.asHolder(),
				WWSounds.BLOCK_AUBURN_MOSS_CARPET_PLACE.asHolder(),
				WWSounds.BLOCK_AUBURN_MOSS_CARPET_HIT.asHolder(),
				WWSounds.BLOCK_AUBURN_MOSS_CARPET_FALL.asHolder()
			)
		);
		context.register(
			MAPLE_LEAVES,
			new BlockSoundSet(
				WWSounds.BLOCK_MAPLE_LEAVES_BREAK.asHolder(),
				WWSounds.BLOCK_MAPLE_LEAVES_STEP.asHolder(),
				WWSounds.BLOCK_MAPLE_LEAVES_PLACE.asHolder(),
				WWSounds.BLOCK_MAPLE_LEAVES_HIT.asHolder(),
				WWSounds.BLOCK_MAPLE_LEAVES_FALL.asHolder()
			)
		);
		context.register(
			MAPLE_LEAF_LITTER,
			new BlockSoundSet(
				WWSounds.BLOCK_MAPLE_LEAF_LITTER_BREAK.asHolder(),
				WWSounds.BLOCK_MAPLE_LEAF_LITTER_STEP.asHolder(),
				WWSounds.BLOCK_MAPLE_LEAF_LITTER_PLACE.asHolder(),
				WWSounds.BLOCK_MAPLE_LEAF_LITTER_HIT.asHolder(),
				WWSounds.BLOCK_MAPLE_LEAF_LITTER_FALL.asHolder()
			)
		);
		context.register(
			MAPLE_WOOD,
			new BlockSoundSet(
				WWSounds.BLOCK_MAPLE_WOOD_BREAK.asHolder(),
				WWSounds.BLOCK_MAPLE_WOOD_STEP.asHolder(),
				WWSounds.BLOCK_MAPLE_WOOD_PLACE.asHolder(),
				WWSounds.BLOCK_MAPLE_WOOD_HIT.asHolder(),
				WWSounds.BLOCK_MAPLE_WOOD_FALL.asHolder()
			)
		);
		context.register(
			MAPLE_WOOD_HANGING_SIGN,
			new BlockSoundSet(
				WWSounds.BLOCK_MAPLE_WOOD_HANGING_SIGN_BREAK.asHolder(),
				WWSounds.BLOCK_MAPLE_WOOD_HANGING_SIGN_STEP.asHolder(),
				WWSounds.BLOCK_MAPLE_WOOD_HANGING_SIGN_PLACE.asHolder(),
				WWSounds.BLOCK_MAPLE_WOOD_HANGING_SIGN_HIT.asHolder(),
				WWSounds.BLOCK_MAPLE_WOOD_HANGING_SIGN_FALL.asHolder()
			)
		);
		context.register(
			CHERRY_LEAF_LITTER,
			new BlockSoundSet(
				WWSounds.BLOCK_CHERRY_LEAF_LITTER_BREAK.asHolder(),
				WWSounds.BLOCK_CHERRY_LEAF_LITTER_STEP.asHolder(),
				WWSounds.BLOCK_CHERRY_LEAF_LITTER_PLACE.asHolder(),
				WWSounds.BLOCK_CHERRY_LEAF_LITTER_HIT.asHolder(),
				WWSounds.BLOCK_CHERRY_LEAF_LITTER_FALL.asHolder()
			)
		);
		context.register(
			PALE_OAK_LEAF_LITTER,
			new BlockSoundSet(
				WWSounds.BLOCK_PALE_OAK_LEAF_LITTER_BREAK.asHolder(),
				WWSounds.BLOCK_PALE_OAK_LEAF_LITTER_STEP.asHolder(),
				WWSounds.BLOCK_PALE_OAK_LEAF_LITTER_PLACE.asHolder(),
				WWSounds.BLOCK_PALE_OAK_LEAF_LITTER_HIT.asHolder(),
				WWSounds.BLOCK_PALE_OAK_LEAF_LITTER_FALL.asHolder()
			)
		);
		context.register(
			CONIFER_LEAF_LITTER,
			new BlockSoundSet(
				WWSounds.BLOCK_CONIFER_LEAF_LITTER_BREAK.asHolder(),
				WWSounds.BLOCK_CONIFER_LEAF_LITTER_STEP.asHolder(),
				WWSounds.BLOCK_CONIFER_LEAF_LITTER_PLACE.asHolder(),
				WWSounds.BLOCK_CONIFER_LEAF_LITTER_HIT.asHolder(),
				WWSounds.BLOCK_CONIFER_LEAF_LITTER_FALL.asHolder()
			)
		);
		context.register(
			CLAY,
			new BlockSoundSet(
				0.9F,
				1F,
				WWSounds.BLOCK_CLAY_BREAK.asHolder(),
				WWSounds.BLOCK_CLAY_STEP.asHolder(),
				WWSounds.BLOCK_CLAY_PLACE.asHolder(),
				WWSounds.BLOCK_CLAY_HIT.asHolder(),
				WWSounds.BLOCK_CLAY_FALL.asHolder()
			)
		);
		context.register(
			CACTUS,
			new BlockSoundSet(
				0.8F,
				1F,
				WWSounds.BLOCK_CACTUS_BREAK.asHolder(),
				WWSounds.BLOCK_CACTUS_STEP.asHolder(),
				WWSounds.BLOCK_CACTUS_PLACE.asHolder(),
				WWSounds.BLOCK_CACTUS_HIT.asHolder(),
				WWSounds.BLOCK_CACTUS_FALL.asHolder()
			)
		);
		context.register(
			GRAVEL,
			new BlockSoundSet(
				0.8F,
				1F,
				WWSounds.BLOCK_GRAVEL_BREAK.asHolder(),
				WWSounds.BLOCK_GRAVEL_STEP.asHolder(),
				WWSounds.BLOCK_GRAVEL_PLACE.asHolder(),
				WWSounds.BLOCK_GRAVEL_HIT.asHolder(),
				WWSounds.BLOCK_GRAVEL_FALL.asHolder()
			)
		);
		context.register(
			MUSHROOM,
			new BlockSoundSet(
				WWSounds.BLOCK_MUSHROOM_BREAK.asHolder(),
				WWSounds.BLOCK_MUSHROOM_STEP.asHolder(),
				WWSounds.BLOCK_MUSHROOM_PLACE.asHolder(),
				WWSounds.BLOCK_MUSHROOM_HIT.asHolder(),
				WWSounds.BLOCK_MUSHROOM_FALL.asHolder()
			)
		);
		context.register(
			MUSHROOM_BLOCK,
			new BlockSoundSet(
				WWSounds.BLOCK_MUSHROOM_BLOCK_BREAK.asHolder(),
				WWSounds.BLOCK_MUSHROOM_BLOCK_STEP.asHolder(),
				WWSounds.BLOCK_MUSHROOM_BLOCK_PLACE.asHolder(),
				WWSounds.BLOCK_MUSHROOM_BLOCK_HIT.asHolder(),
				WWSounds.BLOCK_MUSHROOM_BLOCK_FALL.asHolder()
			)
		);
		context.register(
			ICE,
			new BlockSoundSet(
				WWSounds.BLOCK_ICE_BREAK.asHolder(),
				WWSounds.BLOCK_ICE_STEP.asHolder(),
				WWSounds.BLOCK_ICE_PLACE.asHolder(),
				WWSounds.BLOCK_ICE_HIT.asHolder(),
				WWSounds.BLOCK_ICE_FALL.asHolder()
			)
		);
		context.register(
			FROSTED_ICE,
			new BlockSoundSet(
				WWSounds.BLOCK_FROSTED_ICE_BREAK.asHolder(),
				WWSounds.BLOCK_FROSTED_ICE_STEP.asHolder(),
				WWSounds.BLOCK_FROSTED_ICE_PLACE.asHolder(),
				WWSounds.BLOCK_FROSTED_ICE_HIT.asHolder(),
				WWSounds.BLOCK_FROSTED_ICE_FALL.asHolder()
			)
		);
		context.register(
			CONIFER_LEAVES,
			new BlockSoundSet(
				WWSounds.BLOCK_CONIFER_LEAVES_BREAK.asHolder(),
				WWSounds.BLOCK_CONIFER_LEAVES_STEP.asHolder(),
				WWSounds.BLOCK_CONIFER_LEAVES_PLACE.asHolder(),
				WWSounds.BLOCK_CONIFER_LEAVES_HIT.asHolder(),
				WWSounds.BLOCK_CONIFER_LEAVES_FALL.asHolder()
			)
		);
		context.register(
			PALE_OAK_LEAVES,
			new BlockSoundSet(
				WWSounds.BLOCK_PALE_OAK_LEAVES_BREAK.asHolder(),
				WWSounds.BLOCK_PALE_OAK_LEAVES_STEP.asHolder(),
				WWSounds.BLOCK_PALE_OAK_LEAVES_PLACE.asHolder(),
				WWSounds.BLOCK_PALE_OAK_LEAVES_HIT.asHolder(),
				WWSounds.BLOCK_PALE_OAK_LEAVES_FALL.asHolder()
			)
		);
		context.register(
			PALE_MOSS,
			new BlockSoundSet(
				WWSounds.BLOCK_PALE_MOSS_BREAK.asHolder(),
				WWSounds.BLOCK_PALE_MOSS_STEP.asHolder(),
				WWSounds.BLOCK_PALE_MOSS_PLACE.asHolder(),
				WWSounds.BLOCK_PALE_MOSS_HIT.asHolder(),
				WWSounds.BLOCK_PALE_MOSS_FALL.asHolder()
			)
		);
		context.register(
			PALE_MOSS_CARPET,
			new BlockSoundSet(
				WWSounds.BLOCK_PALE_MOSS_CARPET_BREAK.asHolder(),
				WWSounds.BLOCK_PALE_MOSS_CARPET_STEP.asHolder(),
				WWSounds.BLOCK_PALE_MOSS_CARPET_PLACE.asHolder(),
				WWSounds.BLOCK_PALE_MOSS_CARPET_HIT.asHolder(),
				WWSounds.BLOCK_PALE_MOSS_CARPET_FALL.asHolder()
			)
		);
		context.register(
			PALE_OAK_WOOD,
			new BlockSoundSet(
				WWSounds.BLOCK_PALE_OAK_WOOD_BREAK.asHolder(),
				WWSounds.BLOCK_PALE_OAK_WOOD_STEP.asHolder(),
				WWSounds.BLOCK_PALE_OAK_WOOD_PLACE.asHolder(),
				WWSounds.BLOCK_PALE_OAK_WOOD_HIT.asHolder(),
				WWSounds.BLOCK_PALE_OAK_WOOD_FALL.asHolder()
			)
		);
		context.register(
			PALE_OAK_WOOD_HANGING_SIGN,
			new BlockSoundSet(
				WWSounds.BLOCK_PALE_OAK_WOOD_HANGING_SIGN_BREAK.asHolder(),
				WWSounds.BLOCK_PALE_OAK_WOOD_HANGING_SIGN_STEP.asHolder(),
				WWSounds.BLOCK_PALE_OAK_WOOD_HANGING_SIGN_PLACE.asHolder(),
				WWSounds.BLOCK_PALE_OAK_WOOD_HANGING_SIGN_HIT.asHolder(),
				WWSounds.BLOCK_PALE_OAK_WOOD_HANGING_SIGN_FALL.asHolder()
			)
		);
		context.register(
			LILY_PAD,
			new BlockSoundSet(
				SoundEvents.BIG_DRIPLEAF_BREAK,
				SoundEvents.BIG_DRIPLEAF_STEP,
				SoundEvents.LILY_PAD_PLACE,
				SoundEvents.BIG_DRIPLEAF_HIT,
				SoundEvents.BIG_DRIPLEAF_FALL
			)
		);
		context.register(
			SAPLING,
			new BlockSoundSet(
				WWSounds.BLOCK_SAPLING_BREAK.asHolder(),
				WWSounds.BLOCK_SAPLING_STEP.asHolder(),
				WWSounds.BLOCK_SAPLING_PLACE.asHolder(),
				WWSounds.BLOCK_SAPLING_HIT.asHolder(),
				WWSounds.BLOCK_SAPLING_FALL.asHolder()
			)
		);
		context.register(
			SUGAR_CANE,
			new BlockSoundSet(
				WWSounds.BLOCK_SUGAR_CANE_BREAK.asHolder(),
				WWSounds.BLOCK_SUGAR_CANE_STEP.asHolder(),
				WWSounds.BLOCK_SUGAR_CANE_PLACE.asHolder(),
				WWSounds.BLOCK_SUGAR_CANE_HIT.asHolder(),
				WWSounds.BLOCK_SUGAR_CANE_FALL.asHolder()
			)
		);
		context.register(
			COARSE_DIRT,
			new BlockSoundSet(
				0.8F,
				1F,
				WWSounds.BLOCK_COARSE_DIRT_BREAK.asHolder(),
				WWSounds.BLOCK_COARSE_DIRT_STEP.asHolder(),
				WWSounds.BLOCK_COARSE_DIRT_PLACE.asHolder(),
				WWSounds.BLOCK_COARSE_DIRT_HIT.asHolder(),
				WWSounds.BLOCK_COARSE_DIRT_FALL.asHolder()
			)
		);
		context.register(
			SANDSTONE,
			new BlockSoundSet(
				0.7F,
				1.1F,
				WWSounds.BLOCK_SANDSTONE_BREAK.asHolder(),
				WWSounds.BLOCK_SANDSTONE_STEP.asHolder(),
				WWSounds.BLOCK_SANDSTONE_PLACE.asHolder(),
				WWSounds.BLOCK_SANDSTONE_HIT.asHolder(),
				WWSounds.BLOCK_SANDSTONE_FALL.asHolder()
			)
		);
		context.register(
			SCORCHED_SAND,
			new BlockSoundSet(
				0.8F,
				1F,
				WWSounds.BLOCK_SCORCHED_SAND_BREAK.asHolder(),
				WWSounds.BLOCK_SCORCHED_SAND_STEP.asHolder(),
				WWSounds.BLOCK_SCORCHED_SAND_PLACE.asHolder(),
				WWSounds.BLOCK_SCORCHED_SAND_HIT.asHolder(),
				WWSounds.BLOCK_SCORCHED_SAND_FALL.asHolder()
			)
		);
		context.register(
			REINFORCED_DEEPSLATE,
			new BlockSoundSet(
				WWSounds.BLOCK_REINFORCED_DEEPSLATE_BREAK.asHolder(),
				WWSounds.BLOCK_REINFORCED_DEEPSLATE_STEP.asHolder(),
				WWSounds.BLOCK_REINFORCED_DEEPSLATE_PLACE.asHolder(),
				WWSounds.BLOCK_REINFORCED_DEEPSLATE_HIT.asHolder(),
				WWSounds.BLOCK_REINFORCED_DEEPSLATE_FALL.asHolder()
			)
		);
		context.register(
			MAGMA,
			new BlockSoundSet(
				1F,
				0.9F,
				WWSounds.BLOCK_MAGMA_BREAK.asHolder(),
				WWSounds.BLOCK_MAGMA_STEP.asHolder(),
				WWSounds.BLOCK_MAGMA_PLACE.asHolder(),
				WWSounds.BLOCK_MAGMA_HIT.asHolder(),
				WWSounds.BLOCK_MAGMA_FALL.asHolder()
			)
		);
		context.register(
			MELON,
			new BlockSoundSet(
				WWSounds.BLOCK_MELON_BREAK.asHolder(),
				WWSounds.BLOCK_MELON_STEP.asHolder(),
				WWSounds.BLOCK_MELON_PLACE.asHolder(),
				WWSounds.BLOCK_MELON_HIT.asHolder(),
				WWSounds.BLOCK_MELON_FALL.asHolder()
			)
		);
		context.register(
			SHORT_GRASS,
			new BlockSoundSet(
				WWSounds.BLOCK_SHORT_GRASS_BREAK.asHolder(),
				SoundEvents.GRASS_STEP,
				WWSounds.BLOCK_SHORT_GRASS_PLACE.asHolder(),
				SoundEvents.GRASS_HIT,
				SoundEvents.GRASS_FALL
			)
		);
		context.register(
			FROZEN_GRASS,
			new BlockSoundSet(
				WWSounds.BLOCK_FROZEN_GRASS_BREAK.asHolder(),
				SoundEvents.GRASS_STEP,
				WWSounds.BLOCK_FROZEN_GRASS_PLACE.asHolder(),
				SoundEvents.GRASS_HIT,
				SoundEvents.GRASS_FALL
			)
		);
		context.register(
			DRY_GRASS,
			new BlockSoundSet(
				WWSounds.BLOCK_DRY_GRASS_BREAK.asHolder(),
				SoundEvents.GRASS_STEP,
				WWSounds.BLOCK_DRY_GRASS_PLACE.asHolder(),
				SoundEvents.GRASS_HIT,
				SoundEvents.GRASS_FALL
			)
		);
		context.register(
			BARNACLES,
			new BlockSoundSet(
				WWSounds.BLOCK_BARNACLES_BREAK.asHolder(),
				SoundEvents.CORAL_BLOCK_STEP,
				WWSounds.BLOCK_BARNACLES_PLACE.asHolder(),
				SoundEvents.CORAL_BLOCK_HIT,
				SoundEvents.CORAL_BLOCK_FALL
			)
		);
		context.register(
			SEA_ANEMONE,
			new BlockSoundSet(
				WWSounds.BLOCK_SEA_ANEMONE_BREAK.asHolder(),
				WWSounds.BLOCK_ALGAE_STEP.asHolder(),
				WWSounds.BLOCK_SEA_ANEMONE_PLACE.asHolder(),
				WWSounds.BLOCK_ALGAE_HIT.asHolder(),
				WWSounds.BLOCK_ALGAE_FALL.asHolder()
			)
		);
		context.register(
			TUBE_WORMS,
			new BlockSoundSet(
				WWSounds.BLOCK_TUBE_WORM_BREAK.asHolder(),
				SoundEvents.CORAL_BLOCK_STEP,
				WWSounds.BLOCK_TUBE_WORMS_PLACE.asHolder(),
				SoundEvents.CORAL_BLOCK_HIT,
				SoundEvents.CORAL_BLOCK_FALL
			)
		);
	}

	private static ResourceKey<BlockSoundSet> createKey(String name) {
		return ResourceKey.create(Registries.BLOCK_SOUND_SET, WWConstants.id(name));
	}

	private WWBlockSoundSets() {}
}
