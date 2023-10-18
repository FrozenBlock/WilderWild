package net.frozenblock.wilderwild.entity.ai.crab;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.registry.RegisterMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;

public class CrabNearbyPlayerSensor extends Sensor<Crab> {
	@Override
	public Set<MemoryModuleType<?>> requires() {
		return ImmutableSet.of(MemoryModuleType.NEAREST_PLAYERS, RegisterMemoryModuleTypes.IS_PLAYER_NEARBY);
	}

	@Override
	protected void doTick(ServerLevel level, Crab crab) {
		Brain<?> brain = crab.getBrain();
		if (brain.hasMemoryValue(MemoryModuleType.NEAREST_PLAYERS) && !brain.getMemory(MemoryModuleType.NEAREST_PLAYERS).get().isEmpty()) {
			brain.setMemory(RegisterMemoryModuleTypes.IS_PLAYER_NEARBY, true);
		} else {
			brain.eraseMemory(RegisterMemoryModuleTypes.IS_PLAYER_NEARBY);
		}
	}

}
