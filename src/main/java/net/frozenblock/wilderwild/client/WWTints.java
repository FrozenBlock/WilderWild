/*
 * Copyright 2024-2025 FrozenBlock
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

package net.frozenblock.wilderwild.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.frozenblock.lib.block.client.TintRegistryHelper;
import net.frozenblock.wilderwild.registry.WWBlocks;

@Environment(EnvType.CLIENT)
public final class WWTints {

	public static void applyTints() {
		ColorProviderRegistry.BLOCK.register(
			((state, level, pos, tintIndex) -> level == null || pos == null ? 7455580 : 2129968),
			WWBlocks.FLOWERING_LILY_PAD
		);

		TintRegistryHelper.registerAverageFoliageColorForBlock(WWBlocks.BAOBAB_LEAVES);
		TintRegistryHelper.registerAverageFoliageColorForBlock(WWBlocks.CYPRESS_LEAVES);
		TintRegistryHelper.registerAverageFoliageColorForBlock(WWBlocks.PALM_FRONDS);
		TintRegistryHelper.registerAverageFoliageColorForBlock(WWBlocks.POTTED_SHORT_GRASS);
		TintRegistryHelper.registerAverageFoliageColorForBlock(WWBlocks.BUSH);
		TintRegistryHelper.registerAverageFoliageColorForBlock(WWBlocks.POTTED_BUSH);
	}
}
