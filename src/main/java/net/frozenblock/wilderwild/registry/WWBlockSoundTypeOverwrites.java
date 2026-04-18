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

import net.frozenblock.lib.block.sound.api.BlockSoundTypeOverwrites;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.world.level.block.SoundType;

public final class WWBlockSoundTypeOverwrites {

	public static void init() {
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_GRASS, WWSoundTypes.SHORT_GRASS, WWBlockConfig.GRASS_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_FROZEN_GRASS, WWSoundTypes.FROZEN_GRASS, WWBlockConfig.GRASS_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_DRY_GRASS, WWSoundTypes.DRY_GRASS, WWBlockConfig.GRASS_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_FLOWER, SoundType.PINK_PETALS, WWBlockConfig.FLOWER_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_LEAVES, SoundType.AZALEA_LEAVES, WWBlockConfig.LEAF_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_CONIFER_LEAVES, WWSoundTypes.CONIFER_LEAVES, WWBlockConfig.LEAF_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_CONIFER_LEAF_LITTER, WWSoundTypes.CONIFER_LEAF_LITTER, WWBlockConfig.LEAF_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_SAPLING, WWSoundTypes.SAPLING, WWBlockConfig.SAPLING_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_CACTUS, WWSoundTypes.CACTUS, WWBlockConfig.CACTUS_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_COARSE_DIRT, WWSoundTypes.COARSE_DIRT, WWBlockConfig.COARSE_DIRT_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_ICE, WWSoundTypes.ICE, WWBlockConfig.ICE_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_FROSTED_ICE, WWSoundTypes.FROSTED_ICE, WWBlockConfig.FROSTED_ICE_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_MUSHROOM, WWSoundTypes.MUSHROOM, WWBlockConfig.MUSHROOM_BLOCK_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_MUSHROOM_BLOCK, WWSoundTypes.MUSHROOM_BLOCK, WWBlockConfig.MUSHROOM_BLOCK_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_SANDSTONE, WWSoundTypes.SANDSTONE, WWBlockConfig.SANDSTONE_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_LILY_PAD, WWSoundTypes.LILY_PAD, WWBlockConfig.LILY_PAD_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_MELON, WWSoundTypes.MELON, WWBlockConfig.MELON_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_MELON_STEM, SoundType.CROP, WWBlockConfig.MELON_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_GRAVEL, WWSoundTypes.GRAVEL, WWBlockConfig.GRAVEL_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_CLAY, WWSoundTypes.CLAY, WWBlockConfig.CLAY_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_DEAD_BUSH, SoundType.NETHER_SPROUTS, WWBlockConfig.DEAD_BUSH_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_PODZOL, SoundType.ROOTED_DIRT, WWBlockConfig.PODZOL_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_REINFORCED_DEEPSLATE, WWSoundTypes.REINFORCED_DEEPSLATE, WWBlockConfig.REINFORCED_DEEPSLATE_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_SUGAR_CANE, WWSoundTypes.SUGARCANE, WWBlockConfig.SUGAR_CANE_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_WITHER_ROSE, SoundType.SWEET_BERRY_BUSH, WWBlockConfig.WITHER_ROSE_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_MAGMA_BLOCK, WWSoundTypes.MAGMA, WWBlockConfig.MAGMA_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_AUBURN_MOSS, WWSoundTypes.AUBURN_MOSS, WWBlockConfig.MOSS_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_AUBURN_MOSS_CARPET, WWSoundTypes.AUBURN_MOSS_CARPET, WWBlockConfig.MOSS_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_PALE_MOSS, WWSoundTypes.PALE_MOSS, WWBlockConfig.MOSS_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_PALE_MOSS_CARPET, WWSoundTypes.PALE_MOSS_CARPET, WWBlockConfig.MOSS_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_COCONUT, WWSoundTypes.COCONUT, () -> true);

		// PALE OAK
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_PALE_OAK_LEAVES, WWSoundTypes.PALE_OAK_LEAVES, () -> {
			return WWBlockConfig.PALE_OAK_SOUNDS.get() && WWBlockConfig.LEAF_SOUNDS.get();
		});
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_PALE_OAK_LEAVES, SoundType.AZALEA_LEAVES, () -> {
			return !WWBlockConfig.PALE_OAK_SOUNDS.get() && WWBlockConfig.LEAF_SOUNDS.get();
		});
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_PALE_OAK_LEAF_LITTER, WWSoundTypes.PALE_OAK_LEAF_LITTER, () -> {
			return WWBlockConfig.PALE_OAK_SOUNDS.get() && WWBlockConfig.LEAF_SOUNDS.get();
		});
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_PALE_OAK_WOOD, WWSoundTypes.PALE_OAK_WOOD, WWBlockConfig.PALE_OAK_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_HOLLOWED_PALE_OAK_WOOD, WWSoundTypes.HOLLOWED_PALE_OAK_LOG, WWBlockConfig.PALE_OAK_SOUNDS::get);
		BlockSoundTypeOverwrites.addBlockTag(WWBlockTags.SOUND_PALE_OAK_WOOD_HANGING_SIGN, WWSoundTypes.PALE_OAK_WOOD_HANGING_SIGN, WWBlockConfig.PALE_OAK_SOUNDS::get);
	}
}
