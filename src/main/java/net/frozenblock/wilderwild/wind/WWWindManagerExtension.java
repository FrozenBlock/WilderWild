/*
 * Copyright 2025-2026 FrozenBlock
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

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.lib.wind.WindManager;
import net.frozenblock.lib.wind.extension.WindManagerExtension;
import net.frozenblock.lib.wind.extension.WindManagerExtensionType;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;

public final class WWWindManagerExtension implements WindManagerExtension {
	public static final MapCodec<WWWindManagerExtension> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
				com.mojang.serialization.Codec.DOUBLE.fieldOf("cloudX").forGetter(extension -> extension.cloudX),
				com.mojang.serialization.Codec.DOUBLE.fieldOf("cloudZ").forGetter(extension -> extension.cloudZ)
			)
			.apply(instance, WWWindManagerExtension::createFromCodec)
	);
	public static final StreamCodec<RegistryFriendlyByteBuf, WWWindManagerExtension> STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.DOUBLE, extension -> extension.cloudX,
		ByteBufCodecs.DOUBLE, extension -> extension.cloudZ,
		WWWindManagerExtension::createFromCodec
	);
	public static final WindManagerExtensionType<WWWindManagerExtension> TYPE = WindManagerExtensionType.register(
		WWConstants.id("clouds"),
		1000,
		WWWindManagerExtension::new,
		CODEC,
		STREAM_CODEC
	);

	public double cloudX;
	public double cloudZ;
	public double prevCloudX;
	public double prevCloudZ;

	public WWWindManagerExtension() {
	}

	private static WWWindManagerExtension createFromCodec(double cloudX, double cloudZ) {
		final WWWindManagerExtension extension = new WWWindManagerExtension();
		extension.cloudX = cloudX;
		extension.cloudZ = cloudZ;
		extension.prevCloudX = cloudX;
		extension.prevCloudZ = cloudZ;
		return extension;
	}

	public static void init() {
	}

	public static WWWindManagerExtension get(Level level) {
		for (WindManagerExtension extension : WindManager.getOrCreate(level).extensions) {
			if (extension instanceof WWWindManagerExtension ww) return ww;
		}
		throw new IllegalStateException("WWWindManagerExtension was not registered for level " + level);
	}

	public static double getCloudX(Level level, float partialTick) {
		final WWWindManagerExtension extension = get(level);
		return Mth.lerp(partialTick, extension.prevCloudX, extension.cloudX);
	}

	public static double getCloudZ(Level level, float partialTick) {
		final WWWindManagerExtension extension = get(level);
		return Mth.lerp(partialTick, extension.prevCloudZ, extension.cloudZ);
	}

	public static boolean shouldUseWind(Level level) {
		return WWAmbienceAndMiscConfig.CLOUD_MOVEMENT.get() && WindManager.getOrCreate(level).usable();
	}

	@Override
	public WindManagerExtensionType<?> type() {
		return TYPE;
	}

	@Override
	public void tick(Level level) {
		this.prevCloudX = this.cloudX;
		this.prevCloudZ = this.cloudZ;

		final WindManager windManager = WindManager.getOrCreate(level);
		this.cloudX += windManager.laggedWindX * 0.007D;
		this.cloudZ += windManager.laggedWindZ * 0.007D;

		if (level instanceof ClientLevel clientLevel) {
			WWWindParticles.animateTick(clientLevel);
		}
	}

	@Override
	public void baseTick(Level level) {
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
}
