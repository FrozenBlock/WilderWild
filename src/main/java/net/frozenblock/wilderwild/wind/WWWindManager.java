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

import net.frozenblock.lib.wind.api.WindManager;
import net.frozenblock.lib.wind.api.WindManagerExtension;
import net.frozenblock.lib.wind.impl.networking.WindSyncPacket;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.networking.packet.WWWindPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class WWWindManager implements WindManagerExtension {
	private static final ResourceLocation ID = WWConstants.id("cloud_extension");

	private final WindManager manager;
	public double cloudX;
	public double cloudY;
	public double cloudZ;

	public WWWindManager(WindManager manager) {
		this.manager = manager;
	}

	@Override
	public ResourceLocation extensionID() {
		return ID;
	}

	@Override
	public void tick(ServerLevel level) {
		this.cloudX += (manager.laggedWindX * 0.007D);
		this.cloudY += (manager.laggedWindY * 0.01D);
		this.cloudZ += (manager.laggedWindZ * 0.007D);
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

	@Override
	public void load(@NotNull CompoundTag compoundTag) {
		this.cloudX = compoundTag.getDouble(WWConstants.safeString("cloudX"));
		this.cloudY = compoundTag.getDouble(WWConstants.safeString("cloudY"));
		this.cloudZ = compoundTag.getDouble(WWConstants.safeString("cloudZ"));
	}

	@Override
	public void save(@NotNull CompoundTag compoundTag) {
		compoundTag.putDouble(WWConstants.safeString("cloudX"), this.cloudX);
		compoundTag.putDouble(WWConstants.safeString("cloudY"), this.cloudY);
		compoundTag.putDouble(WWConstants.safeString("cloudZ"), this.cloudZ);
	}
}
