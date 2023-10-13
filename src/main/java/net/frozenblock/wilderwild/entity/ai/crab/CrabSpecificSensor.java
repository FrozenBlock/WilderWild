package net.frozenblock.wilderwild.entity.ai.crab;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.registry.RegisterMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;

public class CrabSpecificSensor extends Sensor<LivingEntity> {
	@Override
	public Set<MemoryModuleType<?>> requires() {
		return ImmutableSet.of(RegisterMemoryModuleTypes.NEARBY_CRABS, MemoryModuleType.NEAREST_LIVING_ENTITIES);
	}

	@Override
	protected void doTick(ServerLevel level, LivingEntity entity) {
		Brain<?> brain = entity.getBrain();
		ArrayList<Crab> crabs = Lists.newArrayList();
		List<LivingEntity> entities = brain.getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES).orElse(ImmutableList.of());
		for (LivingEntity livingEntity : entities) {
			if (livingEntity instanceof Crab crab) {
				crabs.add(crab);
			}
		}
		brain.setMemory(RegisterMemoryModuleTypes.NEARBY_CRABS, crabs);
	}

}
