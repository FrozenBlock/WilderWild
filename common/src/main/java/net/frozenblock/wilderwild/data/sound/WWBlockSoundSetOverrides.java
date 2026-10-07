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

package net.frozenblock.wilderwild.data.sound;

import java.util.Optional;
import net.frozenblock.lib.block.api.sound.BlockSoundSetOverrides;
import net.frozenblock.lib.block.impl.sound.BlockSoundSetOverride;
import net.frozenblock.lib.config.v2.entry.ConfigEntry;
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.registry.WWBlockSoundSets;
import net.frozenblock.wilderwild.registry.WWConfigPredicates;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.sounds.BlockSoundSet;
import net.minecraft.world.level.block.sounds.BlockSoundSets;

public final class WWBlockSoundSetOverrides {

	public static void bootstrap(BootstrapContext<BlockSoundSetOverride> context) {
		register(context, "grass", WWBlockTags.SOUND_GRASS, WWBlockSoundSets.SHORT_GRASS, WWConfigPredicates.SOUND_OVERRIDE_GRASS);
		register(context, "frozen_grass", WWBlockTags.SOUND_FROZEN_GRASS, WWBlockSoundSets.FROZEN_GRASS, WWConfigPredicates.SOUND_OVERRIDE_GRASS);
		register(context, "dry_grass", WWBlockTags.SOUND_DRY_GRASS, WWBlockSoundSets.DRY_GRASS, WWConfigPredicates.SOUND_OVERRIDE_GRASS);
		register(context, "dead_bush", WWBlockTags.SOUND_DEAD_BUSH, BlockSoundSets.NETHER_SPROUTS, WWBlockConfig.DEAD_BUSH_SOUNDS);
		register(context, "flower", WWBlockTags.SOUND_FLOWER, BlockSoundSets.PINK_PETALS, WWBlockConfig.FLOWER_SOUNDS);
		register(context, "wither_rose", WWBlockTags.SOUND_WITHER_ROSE, BlockSoundSets.SWEET_BERRY_BUSH, WWBlockConfig.WITHER_ROSE_SOUNDS);
		register(context, "mushroom", WWBlockTags.SOUND_MUSHROOM, WWBlockSoundSets.MUSHROOM, WWConfigPredicates.SOUND_OVERRIDE_MUSHROOM);
		register(context, "mushroom_block", WWBlockTags.SOUND_MUSHROOM_BLOCK, WWBlockSoundSets.MUSHROOM_BLOCK, WWConfigPredicates.SOUND_OVERRIDE_MUSHROOM);
		register(context, "leaves", WWBlockTags.SOUND_LEAVES, BlockSoundSets.AZALEA_LEAVES, WWConfigPredicates.SOUND_OVERRIDE_LEAF);
		register(context, "conifer_leaves", WWBlockTags.SOUND_CONIFER_LEAVES, WWBlockSoundSets.CONIFER_LEAVES, WWConfigPredicates.SOUND_OVERRIDE_LEAF);
		register(context, "conifer_leaf_litter", WWBlockTags.SOUND_CONIFER_LEAF_LITTER, WWBlockSoundSets.CONIFER_LEAF_LITTER, WWConfigPredicates.SOUND_OVERRIDE_LEAF);
		register(context, "sapling", WWBlockTags.SOUND_SAPLING, WWBlockSoundSets.SAPLING, WWBlockConfig.SAPLING_SOUNDS);
		register(context, "coconut", WWBlockTags.SOUND_COCONUT, WWBlockSoundSets.COCONUT);
		register(context, "cactus", WWBlockTags.SOUND_CACTUS, WWBlockSoundSets.CACTUS, WWBlockConfig.CACTUS_SOUNDS);
		register(context, "sugar_cane", WWBlockTags.SOUND_SUGAR_CANE, WWBlockSoundSets.SUGAR_CANE, WWBlockConfig.SUGAR_CANE_SOUNDS);
		register(context, "lily_pad", WWBlockTags.SOUND_LILY_PAD, WWBlockSoundSets.LILY_PAD, WWBlockConfig.LILY_PAD_SOUNDS);
		register(context, "melon", WWBlockTags.SOUND_MELON, WWBlockSoundSets.MELON, WWConfigPredicates.SOUND_OVERRIDE_MELON);
		register(context, "melon_stem", WWBlockTags.SOUND_MELON_STEM, BlockSoundSets.CROP, WWConfigPredicates.SOUND_OVERRIDE_MELON);

		register(context, "auburn_moss", WWBlockTags.SOUND_AUBURN_MOSS, WWBlockSoundSets.AUBURN_MOSS, WWConfigPredicates.SOUND_OVERRIDE_MOSS);
		register(context, "auburn_moss_carpet", WWBlockTags.SOUND_AUBURN_MOSS_CARPET, WWBlockSoundSets.AUBURN_MOSS_CARPET, WWConfigPredicates.SOUND_OVERRIDE_MOSS);
		register(context, "pale_moss", WWBlockTags.SOUND_PALE_MOSS, WWBlockSoundSets.PALE_MOSS, WWConfigPredicates.SOUND_OVERRIDE_MOSS);
		register(context, "pale_moss_carpet", WWBlockTags.SOUND_PALE_MOSS_CARPET, WWBlockSoundSets.PALE_MOSS_CARPET, WWConfigPredicates.SOUND_OVERRIDE_MOSS);

		register(context, "coarse_dirt", WWBlockTags.SOUND_COARSE_DIRT, WWBlockSoundSets.COARSE_DIRT, WWBlockConfig.COARSE_DIRT_SOUNDS);
		register(context, "podzol", WWBlockTags.SOUND_PODZOL, BlockSoundSets.ROOTED_DIRT, WWBlockConfig.PODZOL_SOUNDS);
		register(context, "gravel", WWBlockTags.SOUND_GRAVEL, WWBlockSoundSets.GRAVEL, WWBlockConfig.GRAVEL_SOUNDS);
		register(context, "clay", WWBlockTags.SOUND_CLAY, WWBlockSoundSets.CLAY, WWBlockConfig.CLAY_SOUNDS);
		register(context, "sandstone", WWBlockTags.SOUND_SANDSTONE, WWBlockSoundSets.SANDSTONE, WWBlockConfig.SANDSTONE_SOUNDS);
		register(context, "magma", WWBlockTags.SOUND_MAGMA_BLOCK, WWBlockSoundSets.MAGMA, WWBlockConfig.MAGMA_SOUNDS);
		register(context, "ice", WWBlockTags.SOUND_ICE, WWBlockSoundSets.ICE, WWBlockConfig.ICE_SOUNDS);
		register(context, "frosted_ice", WWBlockTags.SOUND_FROSTED_ICE, WWBlockSoundSets.FROSTED_ICE, WWBlockConfig.FROSTED_ICE_SOUNDS);

		register(context, "reinforced_deepslate", WWBlockTags.SOUND_REINFORCED_DEEPSLATE, WWBlockSoundSets.REINFORCED_DEEPSLATE, WWBlockConfig.REINFORCED_DEEPSLATE_SOUNDS);

		// PALE OAK
		register(
			context,
			"pale_oak_leaves_with_pale_oak_enabled",
			WWBlockTags.SOUND_PALE_OAK_LEAVES,
			WWBlockSoundSets.PALE_OAK_LEAVES,
			WWConfigPredicates.SOUND_OVERRIDE_PALE_OAK_LEAVES
		);
		register(
			context,
			"pale_oak_leaves_with_pale_oak_disabled",
			WWBlockTags.SOUND_PALE_OAK_LEAVES,
			BlockSoundSets.AZALEA_LEAVES,
			WWConfigPredicates.SOUND_OVERRIDE_PALE_OAK_LEAVES_DEFAULT
		);
		register(
			context,
			"pale_oak_leaf_litter",
			WWBlockTags.SOUND_PALE_OAK_LEAF_LITTER,
			WWBlockSoundSets.PALE_OAK_LEAF_LITTER,
			WWConfigPredicates.SOUND_OVERRIDE_PALE_OAK_LEAVES
		);
		register(context, "pale_oak_wood", WWBlockTags.SOUND_PALE_OAK_WOOD, WWBlockSoundSets.PALE_OAK_WOOD, WWConfigPredicates.SOUND_OVERRIDE_PALE_OAK);
		register(context, "hollowed_pale_oak_wood", WWBlockTags.SOUND_HOLLOWED_PALE_OAK_WOOD, WWBlockSoundSets.HOLLOWED_PALE_OAK_LOG, WWConfigPredicates.SOUND_OVERRIDE_PALE_OAK);
		register(context, "pale_oak_wood_hanging_sign", WWBlockTags.SOUND_PALE_OAK_WOOD_HANGING_SIGN, WWBlockSoundSets.PALE_OAK_WOOD_HANGING_SIGN, WWConfigPredicates.SOUND_OVERRIDE_PALE_OAK);
	}

	private static void register(
		BootstrapContext<BlockSoundSetOverride> context,
		String name,
		TagKey<Block> tagKey,
		ResourceKey<BlockSoundSet> soundSet
	) {
		BlockSoundSetOverrides.register(
			context,
			createKey(name),
			context.lookup(Registries.BLOCK).getOrThrow(tagKey),
			context.lookup(Registries.BLOCK_SOUND_SET).getOrThrow(soundSet),
			Optional.empty()
		);
	}

	private static void register(
		BootstrapContext<BlockSoundSetOverride> context,
		String name,
		TagKey<Block> tagKey,
		ResourceKey<BlockSoundSet> soundSet,
		ConfigEntry<Boolean> configEntry
	) {
		register(context, name, tagKey, soundSet, configEntry.equalTo(true));
	}

	private static void register(
		BootstrapContext<BlockSoundSetOverride> context,
		String name,
		TagKey<Block> tagKey,
		ResourceKey<BlockSoundSet> soundSet,
		ConfigPredicate configPredicate
	) {
		BlockSoundSetOverrides.register(
			context,
			createKey(name),
			context.lookup(Registries.BLOCK).getOrThrow(tagKey),
			context.lookup(Registries.BLOCK_SOUND_SET).getOrThrow(soundSet),
			configPredicate.asHolder()
		);
	}

	private static void register(
		BootstrapContext<BlockSoundSetOverride> context,
		String name,
		TagKey<Block> tagKey,
		ResourceKey<BlockSoundSet> soundSet,
		ResourceKey<ConfigPredicate> configPredicate
	) {
		BlockSoundSetOverrides.register(
			context,
			createKey(name),
			context.lookup(Registries.BLOCK).getOrThrow(tagKey),
			context.lookup(Registries.BLOCK_SOUND_SET).getOrThrow(soundSet),
			context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER).getOrThrow(configPredicate)
		);
	}

	private static ResourceKey<BlockSoundSetOverride> createKey(String name) {
		return BlockSoundSetOverrides.createKey(WWConstants.id(name));
	}

	private WWBlockSoundSetOverrides() {}
}
