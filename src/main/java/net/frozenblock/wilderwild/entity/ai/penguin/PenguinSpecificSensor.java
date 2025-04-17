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
 * You should have received a copy of the FrozenBlock Modding oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.entity.ai.penguin;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import net.frozenblock.wilderwild.entity.Penguin;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class PenguinSpecificSensor extends Sensor<LivingEntity> {

	@Override
	@NotNull
	public Set<MemoryModuleType<?>> requires() {
		return ImmutableSet.of(WWMemoryModuleTypes.NEARBY_PENGUINS, WWMemoryModuleTypes.CLOSE_PENGUINS);
	}

	@Override
	protected void doTick(@NotNull ServerLevel level, @NotNull LivingEntity entity) {
		AABB aABB = entity.getBoundingBox().inflate(48D, 32D, 48D);
		List<Penguin> penguins = level.getEntitiesOfClass(Penguin.class, aABB, penguin -> penguin != entity && penguin.isAlive());
		penguins.sort(Comparator.comparingDouble(entity::distanceToSqr));
		Brain<?> brain = entity.getBrain();
		brain.setMemory(WWMemoryModuleTypes.NEARBY_PENGUINS, penguins);

		List<Penguin> closePenguins = Lists.newArrayList(penguins);
		penguins.removeIf(penguin -> entity.distanceTo(penguin) > entity.getAttributeValue(Attributes.FOLLOW_RANGE));
		closePenguins.sort(Comparator.comparingDouble(entity::distanceToSqr));
		brain.setMemory(WWMemoryModuleTypes.CLOSE_PENGUINS, closePenguins);
	}
}
