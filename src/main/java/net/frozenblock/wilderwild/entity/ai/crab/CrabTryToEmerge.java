package net.frozenblock.wilderwild.entity.ai.crab;

import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.registry.RegisterMemoryModuleTypes;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class CrabTryToEmerge {
	public static BehaviorControl<Crab> create() {
		return BehaviorBuilder.create(instance -> instance.group(
			instance.registered(MemoryModuleType.IS_EMERGING),
			instance.present(RegisterMemoryModuleTypes.IS_UNDERGROUND),
			instance.registered(MemoryModuleType.DIG_COOLDOWN),
			instance.registered(MemoryModuleType.NEAREST_PLAYERS)
		).apply(instance, (isEmerging, underground, digCooldown, players) -> (world, crab, l) -> {
			if (crab.canEmerge() &&
				(
					crab.getBrain().checkMemory(MemoryModuleType.DIG_COOLDOWN, MemoryStatus.VALUE_ABSENT) ||
					(
						crab.getBrain().getMemory(MemoryModuleType.NEAREST_PLAYERS).isPresent()
						&& crab.getBrain().getMemory(MemoryModuleType.NEAREST_PLAYERS).get().stream().anyMatch(player -> player.distanceTo(crab) < CrabAi.UNDERGROUND_PLAYER_RANGE)
					) ||
					!crab.canHideOnGround()
				)
			) {
				isEmerging.setWithExpiry(Unit.INSTANCE, Crab.EMERGE_LENGTH_IN_TICKS);
				digCooldown.setWithExpiry(Unit.INSTANCE, CrabAi.getRandomDigCooldown(crab));
				underground.erase();
				return true;
			} else {
				digCooldown.setWithExpiry(Unit.INSTANCE, 40L);
				return false;
			}
		}));
	}
}
