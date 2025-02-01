/*
 * Copyright 2023-2025 FrozenBlock
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
import net.frozenblock.lib.block.sound.api.BlockSoundTypeOverwrites;
import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import static net.frozenblock.wilderwild.registry.WWSoundTypes.*;

public class BiomesOPlentyIntegration extends ModIntegration {
	public BiomesOPlentyIntegration() {
		super("biomesoplenty");
	}

	@Override
	public void init() {
		BooleanSupplier leavesCondition = () -> WWBlockConfig.get().blockSounds.leafSounds;
		BlockSoundTypeOverwrites.addBlock(id("bramble_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("cypress_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("dead_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("empyreal_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("fir_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("flowering_oak_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("hellbark_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("jacaranda_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("magic_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("mahogany_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("null_leaves"), NULL_BLOCK, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("orange_maple_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("palm_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("pine_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("rainbow_birch_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("red_maple_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("redwood_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("snowblossom_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("umbran_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("willow_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("yellow_maple_leaves"), LEAVES, leavesCondition);

		BooleanSupplier saplingCondition = () -> WWBlockConfig.get().blockSounds.saplingSounds;
		BlockSoundTypeOverwrites.addBlock(id("cypress_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("dead_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("empyreal_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("fir_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("flowering_oak_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("hellbark_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("jacaranda_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("magic_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("mahogany_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("orange_maple_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("palm_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("pine_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("rainbow_birch_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("red_maple_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("redwood_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("snowblossom_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("umbran_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("willow_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("yellow_maple_sapling"), SAPLING, saplingCondition);

		BooleanSupplier mushroomCondition = () -> WWBlockConfig.get().blockSounds.mushroomBlockSounds;
		BlockSoundTypeOverwrites.addBlock(id("glowshroom"), MUSHROOM, mushroomCondition);
		BlockSoundTypeOverwrites.addBlock(id("glowshroom_block"), MUSHROOM_BLOCK, mushroomCondition);

		BooleanSupplier flowerCondition = () -> WWBlockConfig.get().blockSounds.flowerSounds;
		BlockSoundTypeOverwrites.addBlock(id("white_petals"), FLOWER, flowerCondition);
		BlockSoundTypeOverwrites.addBlock(id("wildflower"), FLOWER, flowerCondition);
		BlockSoundTypeOverwrites.addBlock(id("rose"), FLOWER, flowerCondition);
		BlockSoundTypeOverwrites.addBlock(id("violet"), FLOWER, flowerCondition);
		BlockSoundTypeOverwrites.addBlock(id("lavender"), FLOWER, flowerCondition);
		BlockSoundTypeOverwrites.addBlock(id("white_lavender"), FLOWER, flowerCondition);
		BlockSoundTypeOverwrites.addBlock(id("orange_cosmos"), FLOWER, flowerCondition);
		BlockSoundTypeOverwrites.addBlock(id("pink_daffodil"), FLOWER, flowerCondition);
		BlockSoundTypeOverwrites.addBlock(id("pink_hibiscus"), FLOWER, flowerCondition);
		BlockSoundTypeOverwrites.addBlock(id("waterlily"), FLOWER, flowerCondition);
		BlockSoundTypeOverwrites.addBlock(id("glowflower"), FLOWER, flowerCondition);
		BlockSoundTypeOverwrites.addBlock(id("wilted_lily"), FLOWER, flowerCondition);
		BlockSoundTypeOverwrites.addBlock(id("burning_blossom"), FLOWER, flowerCondition);
		BlockSoundTypeOverwrites.addBlock(id("endbloom"), FLOWER, flowerCondition);
		BlockSoundTypeOverwrites.addBlock(id("tall_lavender"), FLOWER, flowerCondition);
		BlockSoundTypeOverwrites.addBlock(id("tall_white_lavender"), FLOWER, flowerCondition);
		BlockSoundTypeOverwrites.addBlock(id("blue_hydrangea"), FLOWER, flowerCondition);
		BlockSoundTypeOverwrites.addBlock(id("goldenrod"), FLOWER, flowerCondition);
		BlockSoundTypeOverwrites.addBlock(id("icy_iris"), FLOWER, flowerCondition);


	}
}
