/*
 * Copyright 2026 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.wind.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public sealed interface CloudWindPositioner permits CloudWindPositioner.Pass, CloudWindPositioner.Success {
	CloudWindPositioner PASS = new Pass();

	record Pass() implements CloudWindPositioner {}

	record Success(double cloudX, double cloudZ) implements CloudWindPositioner {
		public double modifyCloudX(double cameraX) {
			return cameraX - (this.cloudX * 18D);
		}

		public double modifyCloudZ(double cameraZ) {
			return cameraZ - (this.cloudZ * 18D);
		}
	}
}
