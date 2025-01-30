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

package net.frozenblock.wilderwild.wind;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.lib.wind.api.WindManagerExtension;
import net.frozenblock.lib.wind.impl.networking.WindSyncPacket;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.networking.packet.WWWindPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class WWWindManager extends WindManagerExtension {
	private static final String WW_WIND_MANAGER_EXTENSION_FILE_ID = createSaveId(WWConstants.id("clouds"));
	public static final Codec<WWWindManager> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
				Codec.DOUBLE.fieldOf("cloudX").forGetter(windManager -> windManager.cloudX),
				Codec.DOUBLE.fieldOf("cloudY").forGetter(windManager -> windManager.cloudY),
				Codec.DOUBLE.fieldOf("cloudZ").forGetter(windManager -> windManager.cloudZ)
			)
			.apply(instance, WWWindManager::createFromCodec)
	);
	public static final SavedDataType<WWWindManager> TYPE = new SavedDataType<>(
		WW_WIND_MANAGER_EXTENSION_FILE_ID,
		WWWindManager::new,
		CODEC,
		DataFixTypes.SAVED_DATA_RANDOM_SEQUENCES
	);

	public double cloudX;
	public double cloudY;
	public double cloudZ;

	public WWWindManager() {
	}

	public static @NotNull WWWindManager createFromCodec(double cloudX, double cloudY, double cloudZ) {
		WWWindManager manager = new WWWindManager();
		manager.cloudX = cloudX;
		manager.cloudY = cloudY;
		manager.cloudZ = cloudZ;
		return manager;
	}

	@Override
	public void tick(ServerLevel level) {
		this.cloudX += (this.getWindManager().laggedWindX * 0.007D);
		this.cloudY += (this.getWindManager().laggedWindY * 0.01D);
		this.cloudZ += (this.getWindManager().laggedWindZ * 0.007D);
	}

	@Override
	public void baseTick(ServerLevel level) {
	}

	@Override
	public boolean runResetsIfNeeded() {
		boolean needsReset = false;

		if (this.cloudX == Double.MAX_VALUE || this.cloudX == Double.MIN_VALUE) {
			needsReset = true;
			this.cloudX = 0D;
		}
		if (this.cloudY == Double.MAX_VALUE || this.cloudY == Double.MIN_VALUE) {
			needsReset = true;
			this.cloudY = 0D;
		}
		if (this.cloudZ == Double.MAX_VALUE || this.cloudZ == Double.MIN_VALUE) {
			needsReset = true;
			this.cloudZ = 0D;
		}

		return needsReset;
	}

	@Contract("_ -> new")
	@Override
	public @NotNull CustomPacketPayload syncPacket(WindSyncPacket packet) {
		return new WWWindPacket(new Vec3(this.cloudX, this.cloudY, this.cloudZ));
	}
}
