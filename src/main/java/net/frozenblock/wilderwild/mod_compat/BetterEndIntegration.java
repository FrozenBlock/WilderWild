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

package net.frozenblock.wilderwild.mod_compat;

import java.util.function.BooleanSupplier;
import net.frozenblock.lib.integration.api.ModIntegration;
import static net.frozenblock.lib.sound.api.block_sound_group.BlockSoundGroupOverwrites.addBlock;
import static net.frozenblock.wilderwild.registry.WWSoundTypes.*;
import net.frozenblock.wilderwild.config.WWBlockConfig;

public class BetterEndIntegration extends ModIntegration {
	public BetterEndIntegration() {
		super("betterend");
	}

	@Override
	public void init() {
		BooleanSupplier leavesCondition = () -> WWBlockConfig.get().blockSounds.leafSounds;
		addBlock(id("pythadendron_leaves"), LEAVES, leavesCondition);
		addBlock(id("lacugrove_leaves"), LEAVES, leavesCondition);
		addBlock(id("dragon_tree_leaves"), LEAVES, leavesCondition);
		addBlock(id("tenanea_leaves"), LEAVES, leavesCondition);
		addBlock(id("helix_tree_leaves"), LEAVES, leavesCondition);
		addBlock(id("lucernia_leaves"), LEAVES, leavesCondition);
		addBlock(id("lucernia_outer_leaves"), LEAVES, leavesCondition);
		addBlock(id("glowing_pillar_leaves"), LEAVES, leavesCondition);
		addBlock(id("cave_bush"), LEAVES, leavesCondition);
		addBlock(id("end_lotus_leaf"), LEAVES, leavesCondition);

		BooleanSupplier saplingCondition = () -> WWBlockConfig.get().blockSounds.saplingSounds;
		addBlock(id("pythadendron_sapling"), SAPLING, saplingCondition);
		addBlock(id("lacugrove_sapling"), SAPLING, saplingCondition);
		addBlock(id("dragon_tree_sapling"), SAPLING, saplingCondition);
		addBlock(id("tenanea_sapling"), SAPLING, saplingCondition);
		addBlock(id("helix_tree_sapling"), SAPLING, saplingCondition);
		addBlock(id("umbrella_tree_sapling"), SAPLING, saplingCondition);
		addBlock(id("lucernia_sapling"), SAPLING, saplingCondition);

		BooleanSupplier mushroomCondition = () -> WWBlockConfig.get().blockSounds.mushroomBlockSounds;
		addBlock(id("mossy_glowshroom_sapling"), MUSHROOM, mushroomCondition);
		addBlock(id("mossy_glowshroom_cap"), MUSHROOM_BLOCK, mushroomCondition);
		addBlock(id("mossy_glowshroom_fur"), MUSHROOM_BLOCK, mushroomCondition);
		addBlock(id("mossy_glowshroom_sapling"), MUSHROOM_BLOCK, mushroomCondition);
		addBlock(id("small_jellyshroom"), MUSHROOM, mushroomCondition);
		addBlock(id("jellyshroom_cap_purple"), MUSHROOM_BLOCK, mushroomCondition);
		addBlock(id("bolux_mushroom"), MUSHROOM, mushroomCondition);

		BooleanSupplier flowerCondition = () -> WWBlockConfig.get().blockSounds.flowerSounds;
		addBlock(id("hydralux_petal_block"), FLOWER, flowerCondition);
		addBlock(id("end_lotus_flower"), FLOWER, flowerCondition);
		addBlock(id("tenanea_flowers"), FLOWER, flowerCondition);
	}
}
