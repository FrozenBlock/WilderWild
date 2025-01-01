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
import net.frozenblock.lib.integration.api.ModIntegration;
import static net.frozenblock.lib.sound.api.block_sound_group.BlockSoundGroupOverwrites.addBlock;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import static net.frozenblock.wilderwild.registry.WWSoundTypes.*;

public class BetterNetherIntegration extends ModIntegration {
	public BetterNetherIntegration() {
		super("betternether");
	}

	@Override
	public void init() {
		BooleanSupplier leavesCondition = () -> WWBlockConfig.get().blockSounds.leafSounds;
		addBlock(id("willow_leaves"), LEAVES, leavesCondition);
		addBlock(id("rubeus_leaves"), LEAVES, leavesCondition);
		addBlock(id("anchor_tree_leaves"), LEAVES, leavesCondition);
		addBlock(id("nether_sakura_leaves"), LEAVES, leavesCondition);

		BooleanSupplier flowerCondition = () -> WWBlockConfig.get().blockSounds.flowerSounds;
		addBlock(id("soul_lily"), FLOWER, flowerCondition);
		addBlock(id("soul_lily_sapling"), FLOWER, flowerCondition);

		BooleanSupplier mushroomCondition = () -> WWBlockConfig.get().blockSounds.mushroomBlockSounds;
		addBlock(id("red_large_mushroom"), MUSHROOM_BLOCK, mushroomCondition);
		addBlock(id("brown_large_mushroom"), MUSHROOM_BLOCK, mushroomCondition);
		addBlock(id("wall_mushroom_red"), MUSHROOM_BLOCK, mushroomCondition);
		addBlock(id("wall_mushroom_brown"), MUSHROOM_BLOCK, mushroomCondition);
		addBlock(id("lucis_mushroom"), MUSHROOM_BLOCK, mushroomCondition);

		BooleanSupplier cactusCondition = () -> WWBlockConfig.get().blockSounds.cactusSounds;
		addBlock(id("barrel_cactus"), CACTUS, cactusCondition);
		addBlock(id("nether_cactus"), CACTUS, cactusCondition);
	}
}
