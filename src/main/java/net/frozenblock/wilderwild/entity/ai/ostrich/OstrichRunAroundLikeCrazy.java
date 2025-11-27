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

package net.frozenblock.wilderwild.entity.ai.ostrich;

import com.google.common.collect.ImmutableMap;
import net.frozenblock.wilderwild.entity.AbstractOstrich;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class OstrichRunAroundLikeCrazy extends Behavior<AbstractOstrich> {
	private final float speedMultiplier;
	private final boolean zombie;
	private double posX;
	private double posY;
	private double posZ;

	public OstrichRunAroundLikeCrazy(float speedMultiplier, boolean zombie) {
		super(ImmutableMap.of(), 100, 120);
		this.speedMultiplier = speedMultiplier;
		this.zombie = zombie;
	}

	@Override
	public boolean checkExtraStartConditions(ServerLevel level, AbstractOstrich ostrich) {
		if (ostrich.isTamed() || !ostrich.isVehicle() || (this.zombie && ostrich.isMobControlled())) return false;

		final Vec3 vec3 = DefaultRandomPos.getPos(ostrich, 5, 4);
		if (vec3 == null) return false;
		this.posX = vec3.x;
		this.posY = vec3.y;
		this.posZ = vec3.z;
		return true;
	}

	@Override
	public boolean canStillUse(ServerLevel level, AbstractOstrich ostrich, long gameTime) {
		return !ostrich.isTamed() && !ostrich.getNavigation().isDone() && ostrich.isVehicle();
	}

	@Override
	public void start(ServerLevel level, AbstractOstrich ostrich, long gameTime) {
		ostrich.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new Vec3(this.posX, this.posY, this.posZ), this.speedMultiplier, 0));
	}

	@Override
	public void stop(ServerLevel level, AbstractOstrich ostrich, long gameTime) {
		ostrich.getBrain().eraseMemory(MemoryModuleType.IS_PANICKING);
	}

	@Override
	public void tick(ServerLevel level, AbstractOstrich ostrich, long gameTime) {
		if (ostrich.isTamed() || ostrich.getRandom().nextInt(50) != 0) return;

		final Entity passenger = ostrich.getFirstPassenger();
		if (passenger == null) return;

		if (passenger instanceof Player player) {
			final int maxTemper = ostrich.getMaxTemper();
			if (maxTemper > 0 && ostrich.getRandom().nextInt(maxTemper) < ostrich.getTemper()) {
				ostrich.tameWithName(player);
				return;
			}
			ostrich.modifyTemper(5);
		}

		ostrich.ejectPassengers();
		ostrich.makeMad();
		level.broadcastEntityEvent(ostrich, EntityEvent.TAMING_FAILED);
	}

}
