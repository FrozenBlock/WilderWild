package net.frozenblock.wilderwild.entity.ai.crab;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.registry.RegisterMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;

public class CrabCanDigSensor extends Sensor<Crab> {
	@Override
	public Set<MemoryModuleType<?>> requires() {
		return ImmutableSet.of(RegisterMemoryModuleTypes.CAN_DIG);
	}

	@Override
	protected void doTick(ServerLevel level, Crab crab) {
		Brain<?> brain = crab.getBrain();
		if (crab.canHideOnGround()) {
			brain.setMemory(RegisterMemoryModuleTypes.CAN_DIG, true);
		} else {
			brain.eraseMemory(RegisterMemoryModuleTypes.CAN_DIG);
		}
	}

}
