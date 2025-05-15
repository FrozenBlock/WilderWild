/*
 * Copyright 2025 FrozenBlock
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
	public double cloudZ;

	public WWWindManager() {
	}

	public static @NotNull WWWindManager createFromCodec(double cloudX, double cloudZ) {
		WWWindManager manager = new WWWindManager();
		manager.cloudX = cloudX;
		manager.cloudZ = cloudZ;
		return manager;
	}

	@Override
	public void tick(ServerLevel level) {
		this.cloudX += (this.getWindManager().laggedWindX * 0.007D);
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
		if (this.cloudZ == Double.MAX_VALUE || this.cloudZ == Double.MIN_VALUE) {
			needsReset = true;
			this.cloudZ = 0D;
		}

		return needsReset;
	}

	@Contract("_ -> new")
	@Override
	public @NotNull CustomPacketPayload syncPacket(WindSyncPacket packet) {
		return new WWWindPacket(this.cloudX, this.cloudZ);
	}
}
