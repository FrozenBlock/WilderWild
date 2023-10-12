package net.frozenblock.wilderwild.entity.ai.crab;

import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.registry.RegisterMemoryModuleTypes;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class CrabTryToEmerge {
	public static BehaviorControl<Crab> create() {
		return BehaviorBuilder.create(instance -> instance.group(
			instance.registered(MemoryModuleType.IS_EMERGING),
			instance.present(RegisterMemoryModuleTypes.UNDERGROUND),
			instance.absent(MemoryModuleType.DIG_COOLDOWN)
		).apply(instance, (isEmerging, underground, digCooldown) -> (world, crab, l) -> {
			isEmerging.setWithExpiry(Unit.INSTANCE, Crab.EMERGE_LENGTH_IN_TICKS);
			digCooldown.setWithExpiry(Unit.INSTANCE, CrabAi.getRandomDigCooldown(crab));
			underground.erase();
			return true;
		}));
	}
}
