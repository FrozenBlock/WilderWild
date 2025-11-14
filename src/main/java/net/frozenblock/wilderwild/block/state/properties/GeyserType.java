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

package net.frozenblock.wilderwild.block.state.properties;

import com.mojang.serialization.Codec;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.StringRepresentable;

public enum GeyserType implements StringRepresentable {
	NONE("none", SoundEvents.EMPTY),
	AIR("air", WWSounds.BLOCK_GEYSER_ERUPT_AIR),
	WATER("water", WWSounds.BLOCK_GEYSER_ERUPT_WATER),
	LAVA("lava", WWSounds.BLOCK_GEYSER_ERUPT_LAVA),
	HYDROTHERMAL_VENT("hydrothermal_vent", WWSounds.BLOCK_GEYSER_VENT_AMBIENT);

	public static final Codec<GeyserType> CODEC = StringRepresentable.fromEnum(GeyserType::values);

	private final String name;
	private final SoundEvent eruptionSound;

	GeyserType(String name, SoundEvent eruptionSound) {
		this.name = name;
		this.eruptionSound = eruptionSound;
	}

	public SoundEvent getEruptionSound() {
		return this.eruptionSound;
	}

	public boolean isWater() {
		return this == GeyserType.WATER || this == GeyserType.HYDROTHERMAL_VENT;
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
