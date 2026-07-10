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

package net.frozenblock.wilderwild.wind.disturbance;

import com.mojang.serialization.MapCodec;
import net.frozenblock.lib.wind.disturbance.WindDisturbance;
import net.frozenblock.lib.wind.disturbance.WindDisturbanceResult;
import net.frozenblock.lib.wind.disturbance.WindDisturbanceType;
import net.frozenblock.wilderwild.block.entity.impl.WWPotentSulfurWindAccess;
import net.frozenblock.wilderwild.registry.WWWindDisturbances;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.PotentSulfurBlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class GeyserWindDisturbance implements WindDisturbance<PotentSulfurBlockEntity> {
	public static final GeyserWindDisturbance INSTANCE = new GeyserWindDisturbance();
	public static final MapCodec<GeyserWindDisturbance> CODEC = MapCodec.unit(() -> INSTANCE);
	public static final StreamCodec<RegistryFriendlyByteBuf, GeyserWindDisturbance> STREAM_CODEC = StreamCodec.unit(INSTANCE);
	private static final WindDisturbanceResult ACTIVE_RESULT = WindDisturbanceResult.success(1D, 1D, new Vec3(0D, 0.2D, 0D).scale(40D));

	@Override
	public Vec3 origin(PotentSulfurBlockEntity source, Level level) {
		return Vec3.atCenterOf(source.getBlockPos());
	}

	@Override
	public AABB area(PotentSulfurBlockEntity source, Level level, Vec3 origin, Vec3 target, double scale) {
		return ((WWPotentSulfurWindAccess) source).wilderWild$getWindArea();
	}

	@Override
	public WindDisturbanceResult get(PotentSulfurBlockEntity source, Level level, Vec3 target) {
		if (!((WWPotentSulfurWindAccess) source).wilderWild$isWindActive(level.getGameTime())) return WindDisturbanceResult.PASS;
		return WindDisturbance.super.get(source, level, target);
	}

	@Override
	public WindDisturbanceResult get(PotentSulfurBlockEntity source, Level level, Vec3 origin, AABB area, Vec3 target, double scale) {
		return ACTIVE_RESULT;
	}

	@Override
	public boolean expired(PotentSulfurBlockEntity source, Level level) {
		return source.isRemoved() || (!level.isClientSide() && !((WWPotentSulfurWindAccess) source).wilderWild$isWindActive(level.getGameTime()));
	}

	@Override
	public WindDisturbanceType<?> type() {
		return WWWindDisturbances.GEYSER;
	}
}
