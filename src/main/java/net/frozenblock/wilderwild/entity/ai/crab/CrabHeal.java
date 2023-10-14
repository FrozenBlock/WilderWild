package net.frozenblock.wilderwild.entity.ai.crab;

import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.registry.RegisterMemoryModuleTypes;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class CrabHeal {
	public static BehaviorControl<Crab> create() {
		return BehaviorBuilder.create(instance -> instance.group(
			instance.present(RegisterMemoryModuleTypes.IS_UNDERGROUND),
			instance.present(MemoryModuleType.DIG_COOLDOWN),
			instance.registered(RegisterMemoryModuleTypes.HEAL_COOLDOWN_TICKS)
		).apply(instance, (underground, digCooldown, healCooldown) -> (world, crab, l) -> {
			if (crab.getBrain().getMemory(RegisterMemoryModuleTypes.HEAL_COOLDOWN_TICKS).isPresent()) {
				int cooldownTicks = crab.getBrain().getMemory(RegisterMemoryModuleTypes.HEAL_COOLDOWN_TICKS).get();
				if (cooldownTicks > 0) {
					healCooldown.setWithExpiry(cooldownTicks - 1, 5L);
					return true;
				}
			}
			healCooldown.setWithExpiry(20, 5L);
			crab.heal(0.05F);
			return true;
		}));
	}
}
