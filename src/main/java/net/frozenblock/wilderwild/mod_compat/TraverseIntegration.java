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
import static net.frozenblock.wilderwild.registry.WWSoundTypes.LEAVES;
import static net.frozenblock.wilderwild.registry.WWSoundTypes.SAPLING;

public class TraverseIntegration extends ModIntegration {
	public TraverseIntegration() {
		super("traverse");
	}

	@Override
	public void init() {
		BooleanSupplier leavesCondition = () -> WWBlockConfig.get().blockSounds.leafSounds;
		addBlock(id("brown_autumnal_leaves"), LEAVES, leavesCondition);
		addBlock(id("fir_leaves"), LEAVES, leavesCondition);
		addBlock(id("orange_autumnal_leaves"), LEAVES, leavesCondition);
		addBlock(id("red_autumnal_leaves"), LEAVES, leavesCondition);
		addBlock(id("yellow_autumnal_leaves"), LEAVES, leavesCondition);

		BooleanSupplier saplingCondition = () -> WWBlockConfig.get().blockSounds.saplingSounds;
		addBlock(id("brown_autumnal_sapling"), SAPLING, saplingCondition);
		addBlock(id("fir_sapling"), SAPLING, saplingCondition);
		addBlock(id("orange_autumnal_sapling"), SAPLING, saplingCondition);
		addBlock(id("red_autumnal_sapling"), SAPLING, saplingCondition);
		addBlock(id("yellow_autumnal_sapling"), SAPLING, saplingCondition);
	}
}
