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
import net.frozenblock.lib.block.api.sound.SoundTypeOverrides;
import net.frozenblock.lib.block.impl.sound.SoundTypeOverride;
import net.frozenblock.lib.config.v2.entry.ConfigEntry;
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.registry.WWSoundTypes;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public final class WWSoundTypeOverrides {

	public static void bootstrap(BootstrapContext<SoundTypeOverride> context) {
		final HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);

		register(context, "grass", WWBlockTags.SOUND_GRASS, WWSoundTypes.SHORT_GRASS, WWBlockConfig.GRASS_SOUNDS);
		register(context, "frozen_grass", WWBlockTags.SOUND_FROZEN_GRASS, WWSoundTypes.FROZEN_GRASS, WWBlockConfig.GRASS_SOUNDS);
		register(context, "dry_grass", WWBlockTags.SOUND_DRY_GRASS, WWSoundTypes.DRY_GRASS, WWBlockConfig.GRASS_SOUNDS);
		register(context, "dead_bush", WWBlockTags.SOUND_DEAD_BUSH, SoundType.NETHER_SPROUTS, WWBlockConfig.DEAD_BUSH_SOUNDS);
		register(context, "flower", WWBlockTags.SOUND_FLOWER, SoundType.PINK_PETALS, WWBlockConfig.FLOWER_SOUNDS);
		register(context, "wither_rose", WWBlockTags.SOUND_WITHER_ROSE, SoundType.SWEET_BERRY_BUSH, WWBlockConfig.WITHER_ROSE_SOUNDS);
		register(context, "mushroom", WWBlockTags.SOUND_MUSHROOM, WWSoundTypes.MUSHROOM, WWBlockConfig.MUSHROOM_BLOCK_SOUNDS);
		register(context, "mushroom_block", WWBlockTags.SOUND_MUSHROOM_BLOCK, WWSoundTypes.MUSHROOM_BLOCK, WWBlockConfig.MUSHROOM_BLOCK_SOUNDS);
		register(context, "leaves", WWBlockTags.SOUND_LEAVES, SoundType.AZALEA_LEAVES, WWBlockConfig.LEAF_SOUNDS);
		register(context, "conifer_leaves", WWBlockTags.SOUND_CONIFER_LEAVES, WWSoundTypes.CONIFER_LEAVES, WWBlockConfig.LEAF_SOUNDS);
		register(context, "conifer_leaf_litter", WWBlockTags.SOUND_CONIFER_LEAF_LITTER, WWSoundTypes.CONIFER_LEAF_LITTER, WWBlockConfig.LEAF_SOUNDS);
		register(context, "sapling", WWBlockTags.SOUND_SAPLING, WWSoundTypes.SAPLING, WWBlockConfig.SAPLING_SOUNDS);
		register(context, "coconut", WWBlockTags.SOUND_COCONUT, WWSoundTypes.COCONUT);
		register(context, "cactus", WWBlockTags.SOUND_CACTUS, WWSoundTypes.CACTUS, WWBlockConfig.CACTUS_SOUNDS);
		register(context, "sugar_cane", WWBlockTags.SOUND_SUGAR_CANE, WWSoundTypes.SUGAR_CANE, WWBlockConfig.SUGAR_CANE_SOUNDS);
		register(context, "lily_pad", WWBlockTags.SOUND_LILY_PAD, WWSoundTypes.LILY_PAD, WWBlockConfig.LILY_PAD_SOUNDS);
		register(context, "melon", WWBlockTags.SOUND_MELON, WWSoundTypes.MELON, WWBlockConfig.MELON_SOUNDS);
		register(context, "melon_stem", WWBlockTags.SOUND_MELON_STEM, SoundType.CROP, WWBlockConfig.MELON_SOUNDS);

		register(context, "auburn_moss", WWBlockTags.SOUND_AUBURN_MOSS, WWSoundTypes.AUBURN_MOSS, WWBlockConfig.MOSS_SOUNDS);
		register(context, "auburn_moss_carpet", WWBlockTags.SOUND_AUBURN_MOSS_CARPET, WWSoundTypes.AUBURN_MOSS_CARPET, WWBlockConfig.MOSS_SOUNDS);
		register(context, "pale_moss", WWBlockTags.SOUND_PALE_MOSS, WWSoundTypes.PALE_MOSS, WWBlockConfig.MOSS_SOUNDS);
		register(context, "pale_moss_carpet", WWBlockTags.SOUND_PALE_MOSS_CARPET, WWSoundTypes.PALE_MOSS_CARPET, WWBlockConfig.MOSS_SOUNDS);

		register(context, "coarse_dirt", WWBlockTags.SOUND_COARSE_DIRT, WWSoundTypes.COARSE_DIRT, WWBlockConfig.COARSE_DIRT_SOUNDS);
		register(context, "podzol", WWBlockTags.SOUND_PODZOL, SoundType.ROOTED_DIRT, WWBlockConfig.PODZOL_SOUNDS);
		register(context, "gravel", WWBlockTags.SOUND_GRAVEL, WWSoundTypes.GRAVEL, WWBlockConfig.GRAVEL_SOUNDS);
		register(context, "clay", WWBlockTags.SOUND_CLAY, WWSoundTypes.CLAY, WWBlockConfig.CLAY_SOUNDS);
		register(context, "sandstone", WWBlockTags.SOUND_SANDSTONE, WWSoundTypes.SANDSTONE, WWBlockConfig.SANDSTONE_SOUNDS);
		register(context, "magma", WWBlockTags.SOUND_MAGMA_BLOCK, WWSoundTypes.MAGMA, WWBlockConfig.MAGMA_SOUNDS);
		register(context, "ice", WWBlockTags.SOUND_ICE, WWSoundTypes.ICE, WWBlockConfig.ICE_SOUNDS);
		register(context, "frosted_ice", WWBlockTags.SOUND_FROSTED_ICE, WWSoundTypes.FROSTED_ICE, WWBlockConfig.FROSTED_ICE_SOUNDS);

		register(context, "reinforced_deepslate", WWBlockTags.SOUND_REINFORCED_DEEPSLATE, WWSoundTypes.REINFORCED_DEEPSLATE, WWBlockConfig.REINFORCED_DEEPSLATE_SOUNDS);

		// PALE OAK
		register(
			context,
			"pale_oak_leaves_with_pale_oak_enabled",
			WWBlockTags.SOUND_PALE_OAK_LEAVES,
			WWSoundTypes.PALE_OAK_LEAVES,
			ConfigPredicate.allOf(
				ConfigPredicate.equalTo(WWBlockConfig.PALE_OAK_SOUNDS, true),
				ConfigPredicate.equalTo(WWBlockConfig.LEAF_SOUNDS, true)
			)
		);
		register(
			context,
			"pale_oak_leaves_with_pale_oak_disabled",
			WWBlockTags.SOUND_PALE_OAK_LEAVES,
			SoundType.AZALEA_LEAVES,
			ConfigPredicate.allOf(
				ConfigPredicate.equalTo(WWBlockConfig.PALE_OAK_SOUNDS, false),
				ConfigPredicate.equalTo(WWBlockConfig.LEAF_SOUNDS, true)
			)
		);
		register(
			context,
			"pale_oak_leaf_litter",
			WWBlockTags.SOUND_PALE_OAK_LEAF_LITTER,
			WWSoundTypes.PALE_OAK_LEAF_LITTER,
			ConfigPredicate.allOf(
				ConfigPredicate.equalTo(WWBlockConfig.PALE_OAK_SOUNDS, true),
				ConfigPredicate.equalTo(WWBlockConfig.LEAF_SOUNDS, true)
			)
		);
		register(context, "pale_oak_wood", WWBlockTags.SOUND_PALE_OAK_WOOD, WWSoundTypes.PALE_OAK_WOOD, WWBlockConfig.PALE_OAK_SOUNDS);
		register(context, "hollowed_pale_oak_wood", WWBlockTags.SOUND_HOLLOWED_PALE_OAK_WOOD, WWSoundTypes.HOLLOWED_PALE_OAK_LOG, WWBlockConfig.PALE_OAK_SOUNDS);
		register(context, "pale_oak_wood_hanging_sign", WWBlockTags.SOUND_PALE_OAK_WOOD_HANGING_SIGN, WWSoundTypes.PALE_OAK_WOOD_HANGING_SIGN, WWBlockConfig.PALE_OAK_SOUNDS);
	}

	private static void register(
		BootstrapContext<SoundTypeOverride> context,
		String name,
		TagKey<Block> tagKey,
		SoundType soundType
	) {
		SoundTypeOverrides.register(context, key(name), context.lookup(Registries.BLOCK).getOrThrow(tagKey), soundType, Optional.empty());
	}

	private static void register(
		BootstrapContext<SoundTypeOverride> context,
		String name,
		TagKey<Block> tagKey,
		SoundType soundType,
		ConfigEntry<Boolean> configEntry
	) {
		register(context, name, tagKey, soundType, ConfigPredicate.equalTo(configEntry, true));
	}

	private static void register(
		BootstrapContext<SoundTypeOverride> context,
		String name,
		TagKey<Block> tagKey,
		SoundType soundType,
		ConfigPredicate configPredicate
	) {
		SoundTypeOverrides.register(context, key(name), context.lookup(Registries.BLOCK).getOrThrow(tagKey), soundType, configPredicate);
	}

	private static ResourceKey<SoundTypeOverride> key(String name) {
		return SoundTypeOverrides.createKey(WWConstants.id(name));
	}

	private WWSoundTypeOverrides() {}
}
