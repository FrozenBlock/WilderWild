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

package net.frozenblock.wilderwild.registry;

import java.util.function.Supplier;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.entity.ai.crab.CrabAi;
import net.frozenblock.wilderwild.entity.ai.crab.CrabAttackablesSensor;
import net.frozenblock.wilderwild.entity.ai.crab.CrabCanDigSensor;
import net.frozenblock.wilderwild.entity.ai.crab.CrabNearbyPlayerSensor;
import net.frozenblock.wilderwild.entity.ai.crab.CrabSpecificSensor;
import net.frozenblock.wilderwild.entity.ai.firefly.FireflyLeaderSensor;
import net.frozenblock.wilderwild.entity.ai.firefly.FireflySpecificSensor;
import net.frozenblock.wilderwild.entity.ai.ostrich.OstrichAi;
import net.frozenblock.wilderwild.entity.ai.ostrich.OstrichSpecificSensor;
import net.frozenblock.wilderwild.entity.ai.penguin.PenguinAi;
import net.frozenblock.wilderwild.entity.ai.penguin.PenguinAttackablesSensor;
import net.frozenblock.wilderwild.entity.ai.penguin.PenguinLandPosSensor;
import net.frozenblock.wilderwild.entity.ai.penguin.PenguinSpecificSensor;
import net.frozenblock.wilderwild.entity.ai.penguin.PenguinTrackedBoatSensor;
import net.frozenblock.wilderwild.entity.ai.penguin.PenguinWaterPosSensor;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.sensing.TemptingSensor;

public final class WWSensorTypes {

	private WWSensorTypes() {
		throw new UnsupportedOperationException("WWSensorTypes contains only static declarations.");
	}

	public static void register() {
		WWConstants.logWithModId("Registering SensorTypes for", WWConstants.UNSTABLE_LOGGING);
	}

	public static final SensorType<FireflySpecificSensor> FIREFLY_SPECIFIC_SENSOR = register("firefly_specific_sensor", FireflySpecificSensor::new);
	public static final SensorType<FireflyLeaderSensor> FIREFLY_LEADER_SENSOR = register("firefly_leader_sensor", FireflyLeaderSensor::new);
	public static final SensorType<CrabSpecificSensor> CRAB_SPECIFIC_SENSOR = register("crab_specific_sensor", CrabSpecificSensor::new);
	public static final SensorType<TemptingSensor> CRAB_TEMPTATIONS = register("crab_temptations", () -> new TemptingSensor(CrabAi.getTemptations()));
	public static final SensorType<CrabNearbyPlayerSensor> CRAB_NEARBY_PLAYER_SENSOR = register("crab_nearby_player_sensor", CrabNearbyPlayerSensor::new);
	public static final SensorType<CrabCanDigSensor> CRAB_CAN_DIG_SENSOR = register("crab_can_dig_sensor", CrabCanDigSensor::new);
	public static final SensorType<CrabAttackablesSensor> CRAB_ATTACKABLES = register("crab_attackables", CrabAttackablesSensor::new);
	public static final SensorType<OstrichSpecificSensor> OSTRICH_SPECIFIC_SENSOR = register("ostrich_specific_sensor", OstrichSpecificSensor::new);
	public static final SensorType<TemptingSensor> OSTRICH_TEMPTATIONS = register("ostrich_temptations", () -> new TemptingSensor(OstrichAi.getTemptations()));
	public static final SensorType<PenguinSpecificSensor> PENGUIN_SPECIFIC_SENSOR = register("penguin_specific_sensor", PenguinSpecificSensor::new);
	public static final SensorType<TemptingSensor> PENGUIN_TEMPTATIONS = register("penguin_temptations", () -> new TemptingSensor(PenguinAi.getTemptations()));
	public static final SensorType<PenguinAttackablesSensor> PENGUIN_ATTACKABLES = register("penguin_attackables", PenguinAttackablesSensor::new);
	public static final SensorType<PenguinLandPosSensor> LAND_POS_SENSOR = register("land_pos_sensor", PenguinLandPosSensor::new);
	public static final SensorType<PenguinWaterPosSensor> WATER_POS_SENSOR = register("water_pos_sensor", PenguinWaterPosSensor::new);
	public static final SensorType<PenguinTrackedBoatSensor> TRACKED_BOAT_SENSOR = register("tracked_boat_sensor", PenguinTrackedBoatSensor::new);

	private static <U extends Sensor<?>> SensorType<U> register(String path, Supplier<U> sensorSupplier) {
		return Registry.register(BuiltInRegistries.SENSOR_TYPE, WWConstants.id(path), new SensorType<>(sensorSupplier));
	}

}
