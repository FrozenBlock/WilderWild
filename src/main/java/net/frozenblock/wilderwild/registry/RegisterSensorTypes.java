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

package net.frozenblock.wilderwild.registry;

import java.util.function.Supplier;
import net.frozenblock.wilderwild.entity.ai.crab.CrabSpecificSensor;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import org.jetbrains.annotations.NotNull;

public final class RegisterSensorTypes {
	private RegisterSensorTypes() {
		throw new UnsupportedOperationException("RegisterSensorTypes contains only static declarations.");
	}

	public static void register() {
		WilderSharedConstants.logWild("Registering SensorTypes for", WilderSharedConstants.UNSTABLE_LOGGING);
	}

	public static final SensorType<CrabSpecificSensor> CRAB_SPECIFIC_SENSOR = register("crab_specific_sensor", CrabSpecificSensor::new);

	@NotNull
	private static <U extends Sensor<?>> SensorType<U> register(String key, Supplier<U> sensorSupplier) {
		return Registry.register(BuiltInRegistries.SENSOR_TYPE, WilderSharedConstants.id(key), new SensorType<>(sensorSupplier));
	}

}
