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
	public static final ResourceKey<BlockSoundSet> TUMBLEWEED_PLANT = createKey("tumbleweed_plant");
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
	public static final ResourceKey<BlockSoundSet> WEAK_ICE = createKey("weak_ice");
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
		context.register(ALGAE, new BlockSoundSet(0.8F, 1F, WWSounds.ALGAE));
		context.register(BAOBAB_NUT, new BlockSoundSet(WWSounds.BAOBAB_NUT));
		context.register(COCONUT, new BlockSoundSet(WWSounds.COCONUT));
		context.register(OSSEOUS_SCULK, new BlockSoundSet(WWSounds.OSSEOUS_SCULK));
		context.register(NEMATOCYST, new BlockSoundSet(WWSounds.NEMATOCYST));
		context.register(NULL_BLOCK, new BlockSoundSet(WWSounds.NULL_BLOCK));
		context.register(HANGING_TENDRIL, new BlockSoundSet(1F, 1.25F, WWSounds.HANGING_TENDRIL));
		context.register(HOLLOWED_LOG, new BlockSoundSet(WWSounds.HOLLOWED_LOG));
		context.register(HOLLOWED_CHERRY_LOG, new BlockSoundSet(WWSounds.HOLLOWED_CHERRY_LOG));
		context.register(HOLLOWED_MAPLE_LOG, new BlockSoundSet(WWSounds.HOLLOWED_MAPLE_LOG));
		context.register(HOLLOWED_PALE_OAK_LOG, new BlockSoundSet(WWSounds.HOLLOWED_PALE_OAK_LOG));
		context.register(HOLLOWED_STEM, new BlockSoundSet(WWSounds.HOLLOWED_STEM));
		context.register(ECHO_GLASS, new BlockSoundSet(0.8F, 1.25F, WWSounds.ECHO_GLASS));
		context.register(GABBRO, new BlockSoundSet(WWSounds.GABBRO));
		context.register(GABBRO_BRICKS, new BlockSoundSet(WWSounds.GABBRO_BRICKS));
		context.register(GEOTHERMAL_VENT, new BlockSoundSet(WWSounds.GEOTHERMAL_VENT));
		context.register(MESOGLEA, new BlockSoundSet(0.8F, 1F, WWSounds.MESOGLEA));
		context.register(POLLEN, new BlockSoundSet(0.8F, 1.2F, WWSounds.POLLEN));
		context.register(TERMITE_MOUND, new BlockSoundSet(0.8F, 1F, WWSounds.TERMITE_MOUND));
		context.register(TUMBLEWEED_PLANT, new BlockSoundSet(WWSounds.TUMBLEWEED_PLANT));
		context.register(AUBURN_MOSS, new BlockSoundSet(WWSounds.AUBURN_MOSS));
		context.register(AUBURN_MOSS_CARPET, new BlockSoundSet(WWSounds.AUBURN_MOSS_CARPET));
		context.register(MAPLE_LEAVES, new BlockSoundSet(WWSounds.MAPLE_LEAVES));
		context.register(MAPLE_LEAF_LITTER, new BlockSoundSet(WWSounds.MAPLE_LEAF_LITTER));
		context.register(MAPLE_WOOD, new BlockSoundSet(WWSounds.MAPLE_WOOD));
		context.register(MAPLE_WOOD_HANGING_SIGN, new BlockSoundSet(WWSounds.MAPLE_WOOD_HANGING_SIGN));
		context.register(CHERRY_LEAF_LITTER, new BlockSoundSet(WWSounds.CHERRY_LEAF_LITTER));
		context.register(PALE_OAK_LEAF_LITTER, new BlockSoundSet(WWSounds.PALE_OAK_LEAF_LITTER));
		context.register(CONIFER_LEAF_LITTER, new BlockSoundSet(WWSounds.CONIFER_LEAF_LITTER));
		context.register(CLAY, new BlockSoundSet(0.9F, 1F, WWSounds.CLAY));
		context.register(CACTUS, new BlockSoundSet(0.8F, 1F, WWSounds.CACTUS));
		context.register(GRAVEL, new BlockSoundSet(0.8F, 1F, WWSounds.GRAVEL));
		context.register(MUSHROOM, new BlockSoundSet(WWSounds.MUSHROOM));
		context.register(MUSHROOM_BLOCK, new BlockSoundSet(WWSounds.MUSHROOM_BLOCK));
		context.register(ICE, new BlockSoundSet(WWSounds.ICE));
		context.register(WEAK_ICE, new BlockSoundSet(WWSounds.WEAK_ICE));
		context.register(CONIFER_LEAVES, new BlockSoundSet(WWSounds.CONIFER_LEAVES));
		context.register(PALE_OAK_LEAVES, new BlockSoundSet(WWSounds.PALE_OAK_LEAVES));
		context.register(PALE_MOSS, new BlockSoundSet(WWSounds.PALE_MOSS));
		context.register(PALE_MOSS_CARPET, new BlockSoundSet(WWSounds.PALE_MOSS_CARPET));
		context.register(PALE_OAK_WOOD, new BlockSoundSet(WWSounds.PALE_OAK_WOOD));
		context.register(PALE_OAK_WOOD_HANGING_SIGN, new BlockSoundSet(WWSounds.PALE_OAK_WOOD_HANGING_SIGN));
		context.register(LILY_PAD, new BlockSoundSet(SoundEvents.BIG_DRIPLEAF));
		context.register(SAPLING, new BlockSoundSet(WWSounds.SAPLING));
		context.register(SUGAR_CANE, new BlockSoundSet(WWSounds.SUGAR_CANE));
		context.register(COARSE_DIRT, new BlockSoundSet(0.8F, 1F, WWSounds.COARSE_DIRT));
		context.register(SANDSTONE, new BlockSoundSet(0.7F, 1.1F, WWSounds.SANDSTONE));
		context.register(SCORCHED_SAND, new BlockSoundSet(0.8F, 1F, WWSounds.SCORCHED_SAND));
		context.register(REINFORCED_DEEPSLATE, new BlockSoundSet(WWSounds.REINFORCED_DEEPSLATE));
		context.register(MAGMA, new BlockSoundSet(1F, 0.9F, WWSounds.MAGMA));
		context.register(MELON, new BlockSoundSet(WWSounds.MELON));
		context.register(SHORT_GRASS, new BlockSoundSet(WWSounds.SHORT_GRASS));
		context.register(FROZEN_GRASS, new BlockSoundSet(WWSounds.FROZEN_GRASS));
		context.register(DRY_GRASS, new BlockSoundSet(WWSounds.DRY_GRASS));
		context.register(BARNACLES, new BlockSoundSet(WWSounds.BARNACLES));
		context.register(SEA_ANEMONE, new BlockSoundSet(WWSounds.SEA_ANEMONE));
		context.register(TUBE_WORMS, new BlockSoundSet(WWSounds.TUBE_WORMS));
	}

	private static ResourceKey<BlockSoundSet> createKey(String name) {
		return ResourceKey.create(Registries.BLOCK_SOUND_SET, WWConstants.id(name));
	}

	private WWBlockSoundSets() {}
}
