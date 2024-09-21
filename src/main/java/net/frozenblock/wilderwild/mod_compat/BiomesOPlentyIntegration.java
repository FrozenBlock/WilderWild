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
import net.frozenblock.wilderwild.config.WWBlockConfig;
import static net.frozenblock.wilderwild.registry.WWSoundTypes.*;

public class BiomesOPlentyIntegration extends ModIntegration {
	public BiomesOPlentyIntegration() {
		super("biomesoplenty");
	}

	@Override
	public void init() {
		BooleanSupplier leavesCondition = () -> WWBlockConfig.get().blockSounds.leafSounds;
		addBlock(id("bramble_leaves"), LEAVES, leavesCondition);
		addBlock(id("cypress_leaves"), LEAVES, leavesCondition);
		addBlock(id("dead_leaves"), LEAVES, leavesCondition);
		addBlock(id("empyreal_leaves"), LEAVES, leavesCondition);
		addBlock(id("fir_leaves"), LEAVES, leavesCondition);
		addBlock(id("flowering_oak_leaves"), LEAVES, leavesCondition);
		addBlock(id("hellbark_leaves"), LEAVES, leavesCondition);
		addBlock(id("jacaranda_leaves"), LEAVES, leavesCondition);
		addBlock(id("magic_leaves"), LEAVES, leavesCondition);
		addBlock(id("mahogany_leaves"), LEAVES, leavesCondition);
		addBlock(id("null_leaves"), NULL_BLOCK, leavesCondition);
		addBlock(id("orange_maple_leaves"), LEAVES, leavesCondition);
		addBlock(id("palm_leaves"), LEAVES, leavesCondition);
		addBlock(id("pine_leaves"), LEAVES, leavesCondition);
		addBlock(id("rainbow_birch_leaves"), LEAVES, leavesCondition);
		addBlock(id("red_maple_leaves"), LEAVES, leavesCondition);
		addBlock(id("redwood_leaves"), LEAVES, leavesCondition);
		addBlock(id("snowblossom_leaves"), LEAVES, leavesCondition);
		addBlock(id("umbran_leaves"), LEAVES, leavesCondition);
		addBlock(id("willow_leaves"), LEAVES, leavesCondition);
		addBlock(id("yellow_maple_leaves"), LEAVES, leavesCondition);

		BooleanSupplier saplingCondition = () -> WWBlockConfig.get().blockSounds.saplingSounds;
		addBlock(id("cypress_sapling"), SAPLING, saplingCondition);
		addBlock(id("dead_sapling"), SAPLING, saplingCondition);
		addBlock(id("empyreal_sapling"), SAPLING, saplingCondition);
		addBlock(id("fir_sapling"), SAPLING, saplingCondition);
		addBlock(id("flowering_oak_sapling"), SAPLING, saplingCondition);
		addBlock(id("hellbark_sapling"), SAPLING, saplingCondition);
		addBlock(id("jacaranda_sapling"), SAPLING, saplingCondition);
		addBlock(id("magic_sapling"), SAPLING, saplingCondition);
		addBlock(id("mahogany_sapling"), SAPLING, saplingCondition);
		addBlock(id("orange_maple_sapling"), SAPLING, saplingCondition);
		addBlock(id("palm_sapling"), SAPLING, saplingCondition);
		addBlock(id("pine_sapling"), SAPLING, saplingCondition);
		addBlock(id("rainbow_birch_sapling"), SAPLING, saplingCondition);
		addBlock(id("red_maple_sapling"), SAPLING, saplingCondition);
		addBlock(id("redwood_sapling"), SAPLING, saplingCondition);
		addBlock(id("snowblossom_sapling"), SAPLING, saplingCondition);
		addBlock(id("umbran_sapling"), SAPLING, saplingCondition);
		addBlock(id("willow_sapling"), SAPLING, saplingCondition);
		addBlock(id("yellow_maple_sapling"), SAPLING, saplingCondition);

		BooleanSupplier mushroomCondition = () -> WWBlockConfig.get().blockSounds.mushroomBlockSounds;
		addBlock(id("glowshroom"), MUSHROOM, mushroomCondition);
		addBlock(id("glowshroom_block"), MUSHROOM_BLOCK, mushroomCondition);

		BooleanSupplier flowerCondition = () -> WWBlockConfig.get().blockSounds.flowerSounds;
		addBlock(id("white_petals"), FLOWER, flowerCondition);
		addBlock(id("wildflower"), FLOWER, flowerCondition);
		addBlock(id("rose"), FLOWER, flowerCondition);
		addBlock(id("violet"), FLOWER, flowerCondition);
		addBlock(id("lavender"), FLOWER, flowerCondition);
		addBlock(id("white_lavender"), FLOWER, flowerCondition);
		addBlock(id("orange_cosmos"), FLOWER, flowerCondition);
		addBlock(id("pink_daffodil"), FLOWER, flowerCondition);
		addBlock(id("pink_hibiscus"), FLOWER, flowerCondition);
		addBlock(id("waterlily"), FLOWER, flowerCondition);
		addBlock(id("glowflower"), FLOWER, flowerCondition);
		addBlock(id("wilted_lily"), FLOWER, flowerCondition);
		addBlock(id("burning_blossom"), FLOWER, flowerCondition);
		addBlock(id("endbloom"), FLOWER, flowerCondition);
		addBlock(id("tall_lavender"), FLOWER, flowerCondition);
		addBlock(id("tall_white_lavender"), FLOWER, flowerCondition);
		addBlock(id("blue_hydrangea"), FLOWER, flowerCondition);
		addBlock(id("goldenrod"), FLOWER, flowerCondition);
		addBlock(id("icy_iris"), FLOWER, flowerCondition);


	}
}
