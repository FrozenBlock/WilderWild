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

import lombok.experimental.UtilityClass;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.sounds.BlockSoundSet;
import org.jetbrains.annotations.ApiStatus;

@UtilityClass
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
	public static final ResourceKey<BlockSoundSet> PEARL = createKey("pearl");
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
	public static final ResourceKey<BlockSoundSet> PRISMARINE = createKey("prismarine");
	public static final ResourceKey<BlockSoundSet> PRISMARINE_BRICKS = createKey("prismarine_bricks");
	public static final ResourceKey<BlockSoundSet> REINFORCED_DEEPSLATE = createKey("reinforced_deepslate");
	public static final ResourceKey<BlockSoundSet> MAGMA = createKey("magma");
	public static final ResourceKey<BlockSoundSet> MELON = createKey("melon");
	public static final ResourceKey<BlockSoundSet> SHORT_GRASS = createKey("short_grass");
	public static final ResourceKey<BlockSoundSet> FROZEN_GRASS = createKey("frozen_grass");
	public static final ResourceKey<BlockSoundSet> DRY_GRASS = createKey("dry_grass");
	public static final ResourceKey<BlockSoundSet> BARNACLES = createKey("barnacles");
	public static final ResourceKey<BlockSoundSet> SEA_ANEMONE = createKey("sea_anemone");
	public static final ResourceKey<BlockSoundSet> TUBE_WORMS = createKey("tube_worms");

	@ApiStatus.Internal
	public static void bootstrap(BootstrapContext<BlockSoundSet> context) {
		context.register(ALGAE, new BlockSoundSet(0.8F, 1F, WWSoundEvents.ALGAE));
		context.register(BAOBAB_NUT, new BlockSoundSet(WWSoundEvents.BAOBAB_NUT));
		context.register(COCONUT, new BlockSoundSet(WWSoundEvents.COCONUT));
		context.register(OSSEOUS_SCULK, new BlockSoundSet(WWSoundEvents.OSSEOUS_SCULK));
		context.register(NEMATOCYST, new BlockSoundSet(WWSoundEvents.NEMATOCYST));
		context.register(NULL_BLOCK, new BlockSoundSet(WWSoundEvents.NULL_BLOCK));
		context.register(HANGING_TENDRIL, new BlockSoundSet(1F, 1.25F, WWSoundEvents.HANGING_TENDRIL));
		context.register(HOLLOWED_LOG, new BlockSoundSet(WWSoundEvents.HOLLOWED_LOG));
		context.register(HOLLOWED_CHERRY_LOG, new BlockSoundSet(WWSoundEvents.HOLLOWED_CHERRY_LOG));
		context.register(HOLLOWED_MAPLE_LOG, new BlockSoundSet(WWSoundEvents.HOLLOWED_MAPLE_LOG));
		context.register(HOLLOWED_PALE_OAK_LOG, new BlockSoundSet(WWSoundEvents.HOLLOWED_PALE_OAK_LOG));
		context.register(HOLLOWED_STEM, new BlockSoundSet(WWSoundEvents.HOLLOWED_STEM));
		context.register(ECHO_GLASS, new BlockSoundSet(0.8F, 1.25F, WWSoundEvents.ECHO_GLASS));
		context.register(GABBRO, new BlockSoundSet(WWSoundEvents.GABBRO));
		context.register(GABBRO_BRICKS, new BlockSoundSet(WWSoundEvents.GABBRO_BRICKS));
		context.register(GEOTHERMAL_VENT, new BlockSoundSet(WWSoundEvents.GEOTHERMAL_VENT));
		context.register(MESOGLEA, new BlockSoundSet(0.8F, 1F, WWSoundEvents.MESOGLEA));
		context.register(PEARL, new BlockSoundSet(0.95F, 1.2F, WWSoundEvents.PEARL));
		context.register(POLLEN, new BlockSoundSet(0.8F, 1.2F, WWSoundEvents.POLLEN));
		context.register(TERMITE_MOUND, new BlockSoundSet(0.8F, 1F, WWSoundEvents.TERMITE_MOUND));
		context.register(TUMBLEWEED_PLANT, new BlockSoundSet(WWSoundEvents.TUMBLEWEED_PLANT));
		context.register(AUBURN_MOSS, new BlockSoundSet(WWSoundEvents.AUBURN_MOSS));
		context.register(AUBURN_MOSS_CARPET, new BlockSoundSet(WWSoundEvents.AUBURN_MOSS_CARPET));
		context.register(MAPLE_LEAVES, new BlockSoundSet(WWSoundEvents.MAPLE_LEAVES));
		context.register(MAPLE_LEAF_LITTER, new BlockSoundSet(WWSoundEvents.MAPLE_LEAF_LITTER));
		context.register(MAPLE_WOOD, new BlockSoundSet(WWSoundEvents.MAPLE_WOOD));
		context.register(MAPLE_WOOD_HANGING_SIGN, new BlockSoundSet(WWSoundEvents.MAPLE_WOOD_HANGING_SIGN));
		context.register(CHERRY_LEAF_LITTER, new BlockSoundSet(WWSoundEvents.CHERRY_LEAF_LITTER));
		context.register(PALE_OAK_LEAF_LITTER, new BlockSoundSet(WWSoundEvents.PALE_OAK_LEAF_LITTER));
		context.register(CONIFER_LEAF_LITTER, new BlockSoundSet(WWSoundEvents.CONIFER_LEAF_LITTER));
		context.register(CLAY, new BlockSoundSet(0.9F, 1F, WWSoundEvents.CLAY));
		context.register(CACTUS, new BlockSoundSet(0.8F, 1F, WWSoundEvents.CACTUS));
		context.register(GRAVEL, new BlockSoundSet(0.8F, 1F, WWSoundEvents.GRAVEL));
		context.register(MUSHROOM, new BlockSoundSet(WWSoundEvents.MUSHROOM));
		context.register(MUSHROOM_BLOCK, new BlockSoundSet(WWSoundEvents.MUSHROOM_BLOCK));
		context.register(ICE, new BlockSoundSet(WWSoundEvents.ICE));
		context.register(WEAK_ICE, new BlockSoundSet(WWSoundEvents.WEAK_ICE));
		context.register(CONIFER_LEAVES, new BlockSoundSet(WWSoundEvents.CONIFER_LEAVES));
		context.register(PALE_OAK_LEAVES, new BlockSoundSet(WWSoundEvents.PALE_OAK_LEAVES));
		context.register(PALE_MOSS, new BlockSoundSet(WWSoundEvents.PALE_MOSS));
		context.register(PALE_MOSS_CARPET, new BlockSoundSet(WWSoundEvents.PALE_MOSS_CARPET));
		context.register(PALE_OAK_WOOD, new BlockSoundSet(WWSoundEvents.PALE_OAK_WOOD));
		context.register(PALE_OAK_WOOD_HANGING_SIGN, new BlockSoundSet(WWSoundEvents.PALE_OAK_WOOD_HANGING_SIGN));
		context.register(LILY_PAD, new BlockSoundSet(SoundEvents.BIG_DRIPLEAF));
		context.register(SAPLING, new BlockSoundSet(WWSoundEvents.SAPLING));
		context.register(SUGAR_CANE, new BlockSoundSet(WWSoundEvents.SUGAR_CANE));
		context.register(COARSE_DIRT, new BlockSoundSet(0.8F, 1F, WWSoundEvents.COARSE_DIRT));
		context.register(SANDSTONE, new BlockSoundSet(0.7F, 1.1F, WWSoundEvents.SANDSTONE));
		context.register(SCORCHED_SAND, new BlockSoundSet(0.8F, 1F, WWSoundEvents.SCORCHED_SAND));
		context.register(PRISMARINE, new BlockSoundSet(WWSoundEvents.PRISMARINE));
		context.register(PRISMARINE_BRICKS, new BlockSoundSet(WWSoundEvents.PRISMARINE_BRICKS));
		context.register(REINFORCED_DEEPSLATE, new BlockSoundSet(WWSoundEvents.REINFORCED_DEEPSLATE));
		context.register(MAGMA, new BlockSoundSet(1F, 0.9F, WWSoundEvents.MAGMA));
		context.register(MELON, new BlockSoundSet(WWSoundEvents.MELON));
		context.register(SHORT_GRASS, new BlockSoundSet(WWSoundEvents.SHORT_GRASS));
		context.register(FROZEN_GRASS, new BlockSoundSet(WWSoundEvents.FROZEN_GRASS));
		context.register(DRY_GRASS, new BlockSoundSet(WWSoundEvents.DRY_GRASS));
		context.register(BARNACLES, new BlockSoundSet(WWSoundEvents.BARNACLES));
		context.register(SEA_ANEMONE, new BlockSoundSet(WWSoundEvents.SEA_ANEMONE));
		context.register(TUBE_WORMS, new BlockSoundSet(WWSoundEvents.TUBE_WORMS));
	}

	private static ResourceKey<BlockSoundSet> createKey(String name) {
		return ResourceKey.create(Registries.BLOCK_SOUND_SET, WWConstants.id(name));
	}
}
