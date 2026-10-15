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

package net.frozenblock.wilderwild.block.state.properties;

import com.mojang.serialization.Codec;
import java.util.Optional;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.StringRepresentable;

public enum GeothermalVentType implements StringRepresentable {
	NONE("none", false, Optional.empty()),
	AIR("air", true, WWSounds.BLOCK_GEOTHERMAL_VENT_ERUPT_AIR.asHolder()),
	WATER("water", true, WWSounds.BLOCK_GEOTHERMAL_VENT_ERUPT_WATER.asHolder()),
	LAVA("lava", true, WWSounds.BLOCK_GEOTHERMAL_VENT_ERUPT_LAVA.asHolder()),
	HYDROTHERMAL_VENT("hydrothermal_vent", false, WWSounds.BLOCK_GEOTHERMAL_VENT_VENT_AMBIENT.asHolder());
	public static final Codec<GeothermalVentType> CODEC = StringRepresentable.fromEnum(GeothermalVentType::values);

	private final String name;
	private final boolean hasEruption;
	private final Optional<Holder<SoundEvent>> eruptionSound;

	GeothermalVentType(String name, boolean hasEruption, Optional<Holder<SoundEvent>> eruptionSound) {
		this.name = name;
		this.hasEruption = hasEruption;
		this.eruptionSound = eruptionSound;
	}

	GeothermalVentType(String name, boolean hasEruption, Holder<SoundEvent> eruptionSound) {
		this(name, hasEruption, Optional.of(eruptionSound));
	}

	public boolean hasEruption() {
		return this.hasEruption;
	}

	public Optional<Holder<SoundEvent>> eruptionSound() {
		return this.eruptionSound;
	}

	public boolean isWater() {
		return this == GeothermalVentType.WATER || this == GeothermalVentType.HYDROTHERMAL_VENT;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public String getSerializedName() {
		return this.name;
	}
}
