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

package net.frozenblock.wilderwild.entity.ai.penguin;

import java.util.Optional;
import java.util.UUID;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class PenguinMeetCaller {

	@Contract(" -> new")
	public static @NotNull OneShot<LivingEntity> create() {
		return BehaviorBuilder.create(
			instance -> instance.group(instance.absent(MemoryModuleType.LOOK_TARGET), instance.present(WWMemoryModuleTypes.CALLER))
				.apply(instance, (lookTarget, callerUUID) -> (serverLevel, livingEntity, l) -> {
					UUID uuid = instance.get(callerUUID);
					Optional<LivingEntity> caller = PenguinAi.getCaller(livingEntity, uuid);
					if (caller.isPresent()) {
						lookTarget.set(new PenguinCallerTracker(caller.get(), true));
						return true;
					}
					return false;
				})
		);
	}
}
