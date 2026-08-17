package net.frozenblock.wilderwild.registry;

import java.util.function.Supplier;
import net.frozenblock.lib.platform.api.registry.DeferredHolder;
import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.lib.platform.api.registry.DeferredSensorType;
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
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.sensing.TemptingSensor;

public final class WWSensorTypes {
	private static final DeferredRegister.SensorTypes REGISTER = DeferredRegister.createSensorTypes(WWConstants.MOD_ID);

	public static final DeferredSensorType<FireflySpecificSensor> FIREFLY_SPECIFIC_SENSOR = register("firefly_specific_sensor", FireflySpecificSensor::new);
	public static final DeferredSensorType<FireflyLeaderSensor> FIREFLY_LEADER_SENSOR = register("firefly_leader_sensor", FireflyLeaderSensor::new);
	public static final DeferredSensorType<CrabSpecificSensor> CRAB_SPECIFIC_SENSOR = register("crab_specific_sensor", CrabSpecificSensor::new);
	public static final DeferredSensorType<TemptingSensor> CRAB_TEMPTATIONS = register("crab_temptations", () -> new TemptingSensor(CrabAi.getTemptations()));
	public static final DeferredSensorType<CrabNearbyPlayerSensor> CRAB_NEARBY_PLAYER_SENSOR = register("crab_nearby_player_sensor", CrabNearbyPlayerSensor::new);
	public static final DeferredSensorType<CrabCanDigSensor> CRAB_CAN_DIG_SENSOR = register("crab_can_dig_sensor", CrabCanDigSensor::new);
	public static final DeferredSensorType<CrabAttackablesSensor> CRAB_ATTACKABLES = register("crab_attackables", CrabAttackablesSensor::new);
	public static final DeferredSensorType<OstrichSpecificSensor> OSTRICH_SPECIFIC_SENSOR = register("ostrich_specific_sensor", OstrichSpecificSensor::new);
	public static final DeferredSensorType<TemptingSensor> OSTRICH_TEMPTATIONS = register("ostrich_temptations", () -> new TemptingSensor(OstrichAi.getTemptations()));
	public static final DeferredSensorType<PenguinSpecificSensor> PENGUIN_SPECIFIC_SENSOR = register("penguin_specific_sensor", PenguinSpecificSensor::new);
	public static final DeferredSensorType<TemptingSensor> PENGUIN_TEMPTATIONS = register("penguin_temptations", () -> new TemptingSensor(PenguinAi.getTemptations()));
	public static final DeferredSensorType<PenguinAttackablesSensor> PENGUIN_ATTACKABLES = register("penguin_attackables", PenguinAttackablesSensor::new);
	public static final DeferredSensorType<PenguinLandPosSensor> LAND_POS_SENSOR = register("land_pos_sensor", PenguinLandPosSensor::new);
	public static final DeferredSensorType<PenguinTrackedBoatSensor> TRACKED_BOAT_SENSOR = register("tracked_boat_sensor", PenguinTrackedBoatSensor::new);

	static {
		REGISTER.register();
	}

	public static void init() {}

	private static <U extends Sensor<?>> DeferredSensorType<U> register(String name, Supplier<U> factory) {
		return REGISTER.registerSensorType(name, factory);
	}

	private WWSensorTypes() {}
}
