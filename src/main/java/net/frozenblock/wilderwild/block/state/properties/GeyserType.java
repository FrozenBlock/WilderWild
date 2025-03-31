/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.block.state.properties;

import com.mojang.serialization.Codec;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

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
	@NotNull
	public String getSerializedName() {
		return this.name;
	}
}
