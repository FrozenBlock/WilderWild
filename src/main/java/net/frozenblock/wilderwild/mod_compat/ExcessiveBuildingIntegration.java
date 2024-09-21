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
import static net.frozenblock.wilderwild.registry.WWSoundTypes.LEAVES;
import static net.frozenblock.wilderwild.registry.WWSoundTypes.SAPLING;

public class ExcessiveBuildingIntegration extends ModIntegration {
	public ExcessiveBuildingIntegration() {
		super("excessive_building");
	}

	@Override
	public void init() {
		BooleanSupplier condition = () -> WWBlockConfig.get().blockSounds.leafSounds;
		addBlock(id("ancient_leaves"), LEAVES, condition);
		addBlock(id("gloom_leaves"), LEAVES, condition);

		BooleanSupplier saplingCondition = () -> WWBlockConfig.get().blockSounds.saplingSounds;
		addBlock(id("ancient_sapling"), SAPLING, saplingCondition);
		addBlock(id("gloom_sapling"), SAPLING, saplingCondition);
	}
}
