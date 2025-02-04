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

import net.frozenblock.lib.block.sound.api.BlockSoundTypeOverwrites;
import net.frozenblock.lib.integration.api.ModIntegration;
import static net.frozenblock.wilderwild.registry.WWSoundTypes.COCONUT;

public class NaturesSpiritIntegration extends ModIntegration {
	public NaturesSpiritIntegration() {
		super("natures_spirit");
	}

	@Override
	public void init() {
		BlockSoundTypeOverwrites.addBlock(id("coconut"), COCONUT, () -> true);
		BlockSoundTypeOverwrites.addBlock(id("young_coconut"), COCONUT, () -> true);
	}
}
