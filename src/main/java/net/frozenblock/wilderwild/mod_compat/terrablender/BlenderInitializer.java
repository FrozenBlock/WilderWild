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

package net.frozenblock.wilderwild.mod_compat.terrablender;

import net.frozenblock.wilderwild.WilderConstants;
import terrablender.api.Regions;
import terrablender.api.TerraBlenderApi;

public final class BlenderInitializer implements TerraBlenderApi {

	@Override
	public void onTerraBlenderInitialized() {
		Regions.register(new WilderOverworldRegion(WilderConstants.id("overworld"), 1));
	}
}
