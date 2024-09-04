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

package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.WilderConstants;
import net.frozenblock.wilderwild.block.impl.FallingLeafUtil;

public final class WWFallingLeaves {
	private WWFallingLeaves() {
		throw new UnsupportedOperationException("RegisterFallingLeaves contains only static declarations.");
	}

	public static void register() {
		WilderConstants.logWithModId("Registering Falling Leaves for", WilderConstants.UNSTABLE_LOGGING);
		FallingLeafUtil.registerFallingLeaf(
			WWBlocks.MAPLE_LEAVES,
			WWBlocks.MAPLE_LEAF_LITTER,
			0.04F,
			WWParticles.MAPLE_LEAVES
		);
	}

}
