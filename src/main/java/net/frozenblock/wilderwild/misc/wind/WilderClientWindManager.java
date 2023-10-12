/*
 * Copyright 2023 FrozenBlock
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

package net.frozenblock.wilderwild.misc.wind;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import static net.frozenblock.lib.wind.api.ClientWindManager.*;
import net.frozenblock.lib.wind.api.ClientWindManagerExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class WilderClientWindManager implements ClientWindManagerExtension {

	public static double prevCloudX;

	public static double prevCloudY;

	public static double prevCloudZ;

	public static double cloudX;

	public static double cloudY;

	public static double cloudZ;

	@Override
	public void clientTick() {
		prevCloudX = cloudX;
		prevCloudY = cloudY;
		prevCloudZ = cloudZ;

		cloudX += (laggedWindX * 0.007);
		cloudY += (laggedWindY * 0.01);
		cloudZ += (laggedWindZ * 0.007);
	}

	@Override
	public void baseTick() {
	}

	@Override
	public void receiveSyncPacket(@NotNull FriendlyByteBuf byteBuf, @NotNull Minecraft minecraft) {
		double cloudX = byteBuf.readDouble();
		double cloudY = byteBuf.readDouble();
		double cloudZ = byteBuf.readDouble();

		minecraft.execute(() -> {
			if (minecraft.level != null) {
				WilderClientWindManager.cloudX = cloudX;
				WilderClientWindManager.cloudY = cloudY;
				WilderClientWindManager.cloudZ = cloudZ;
			}
		});
	}

	public static double getCloudX(float partialTick) {
		return Mth.lerp(partialTick, prevCloudX, cloudX);
	}

	public static double getCloudY(float partialTick) {
		return Mth.lerp(partialTick, prevCloudY, cloudY);
	}

	public static double getCloudZ(float partialTick) {
		return Mth.lerp(partialTick, prevCloudZ, cloudZ);
	}
}
