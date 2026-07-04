package net.frozenblock.wilderwild.registry;

import java.util.function.Supplier;
import net.frozenblock.lib.platform.api.registry.FrozenDeferredRegister;
import net.frozenblock.lib.platform.api.registry.FrozenHolder;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.entity.ai.firefly.FireflyLeaderSensor;
import net.frozenblock.wilderwild.entity.ai.firefly.FireflySpecificSensor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;

public final class WWSensorTypes {
	private static final FrozenDeferredRegister<SensorType<?>> REGISTER = FrozenDeferredRegister.create(
		Registries.SENSOR_TYPE,
		WWConstants.MOD_ID
	);

	public static final FrozenHolder<SensorType<?>, SensorType<FireflySpecificSensor>> FIREFLY_SPECIFIC_SENSOR = register("firefly_specific_sensor", FireflySpecificSensor::new);
	public static final FrozenHolder<SensorType<?>, SensorType<FireflyLeaderSensor>> FIREFLY_LEADER_SENSOR = register("firefly_leader_sensor", FireflyLeaderSensor::new);
	//TODO NEOFORGE CRAB
	//TODO NEOFORGE OSTRICH
	//TODO NEOFORGE PENGUIN

	static {
		REGISTER.register();
	}

	public static void init() {}

	private static <U extends Sensor<?>> FrozenHolder<SensorType<?>, SensorType<U>> register(String name, Supplier<U> sensorSupplier) {
		return REGISTER.register(name, () -> new SensorType<>(sensorSupplier));
	}
}
