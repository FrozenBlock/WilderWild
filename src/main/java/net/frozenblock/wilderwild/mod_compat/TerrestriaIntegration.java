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

public class TerrestriaIntegration extends ModIntegration {
	public TerrestriaIntegration() {
		super("terrestria");
	}

	@Override
	public void init() {
		BooleanSupplier leavesCondition = () -> WWBlockConfig.get().blockSounds.leafSounds;
		addBlock(id("cypress_leaves"), LEAVES, leavesCondition);
		addBlock(id("dark_japanese_maple_leaves"), LEAVES, leavesCondition);
		addBlock(id("hemlock_leaves"), LEAVES, leavesCondition);
		addBlock(id("japanese_maple_leaves"), LEAVES, leavesCondition);
		addBlock(id("japanese_maple_shrub_leaves"), LEAVES, leavesCondition);
		addBlock(id("jungle_palm_leaves"), LEAVES, leavesCondition);
		addBlock(id("rainbow_eucalyptus_leaves"), LEAVES, leavesCondition);
		addBlock(id("redwood_leaves"), LEAVES, leavesCondition);
		addBlock(id("rubber_leaves"), LEAVES, leavesCondition);
		addBlock(id("sakura_leaves"), NULL_BLOCK, leavesCondition);
		addBlock(id("willow_leaves"), LEAVES, leavesCondition);
		addBlock(id("yucca_palm_leaves"), LEAVES, leavesCondition);

		BooleanSupplier saplingCondition = () -> WWBlockConfig.get().blockSounds.saplingSounds;
		addBlock(id("bryce_sapling"), SAPLING, saplingCondition);
		addBlock(id("cypress_sapling"), SAPLING, saplingCondition);
		addBlock(id("dark_japanese_maple_sapling"), SAPLING, saplingCondition);
		addBlock(id("hemlock_sapling"), SAPLING, saplingCondition);
		addBlock(id("japanese_maple_sapling"), SAPLING, saplingCondition);
		addBlock(id("japanese_maple_shrub_sapling"), SAPLING, saplingCondition);
		addBlock(id("jungle_palm_sapling"), SAPLING, saplingCondition);
		addBlock(id("rainbow_eucalyptus_sapling"), SAPLING, saplingCondition);
		addBlock(id("redwood_sapling"), SAPLING, saplingCondition);
		addBlock(id("rubber_sapling"), SAPLING, saplingCondition);
		addBlock(id("sakura_sapling"), NULL_BLOCK, saplingCondition);
		addBlock(id("willow_sapling"), SAPLING, saplingCondition);
		addBlock(id("yucca_palm_sapling"), SAPLING, saplingCondition);

		BooleanSupplier flowerCondition = () -> WWBlockConfig.get().blockSounds.flowerSounds;
		addBlock(id("indian_paintbrush"), FLOWER, flowerCondition);
		addBlock(id("monsteras"), FLOWER, flowerCondition);

		BooleanSupplier cactusCondition = () -> WWBlockConfig.get().blockSounds.cactusSounds;
		addBlock(id("tiny_cactus"), CACTUS, cactusCondition);
		addBlock(id("saguaro_cactus_sapling"), CACTUS, cactusCondition);
		addBlock(id("saguaro_cactus"), CACTUS, cactusCondition);
	}
}
