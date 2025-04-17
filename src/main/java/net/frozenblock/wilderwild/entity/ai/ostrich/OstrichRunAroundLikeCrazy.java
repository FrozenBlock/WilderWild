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
import net.frozenblock.wilderwild.entity.Ostrich;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class OstrichRunAroundLikeCrazy extends Behavior<Ostrich> {
	private final float speedMultiplier;
	private double posX;
	private double posY;
	private double posZ;

	public OstrichRunAroundLikeCrazy(float speedMultiplier) {
		super(ImmutableMap.of(), 100, 120);
		this.speedMultiplier = speedMultiplier;
	}

	@Override
	public boolean checkExtraStartConditions(@NotNull ServerLevel level, @NotNull Ostrich owner) {
		if (!owner.isTamed() && owner.isVehicle()) {
			Vec3 vec3 = DefaultRandomPos.getPos(owner, 5, 4);
			if (vec3 == null) {
				return false;
			} else {
				this.posX = vec3.x;
				this.posY = vec3.y;
				this.posZ = vec3.z;
				return true;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean canStillUse(@NotNull ServerLevel level, @NotNull Ostrich entity, long gameTime) {
		return !entity.isTamed() && !entity.getNavigation().isDone() && entity.isVehicle();
	}

	@Override
	public void start(@NotNull ServerLevel level, @NotNull Ostrich entity, long gameTime) {
		entity.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new Vec3(this.posX, this.posY, this.posZ), this.speedMultiplier, 0));
	}

	@Override
	public void stop(@NotNull ServerLevel level, @NotNull Ostrich entity, long gameTime) {
		Brain<?> brain = entity.getBrain();
		brain.eraseMemory(MemoryModuleType.IS_PANICKING);
	}

	@Override
	public void tick(@NotNull ServerLevel level, @NotNull Ostrich owner, long gameTime) {
		if (!owner.isTamed() && owner.getRandom().nextInt(50) == 0) {
			Entity entity = owner.getFirstPassenger();
			if (entity == null) {
				return;
			}

			if (entity instanceof Player player) {
				int i = owner.getTemper();
				int j = owner.getMaxTemper();
				if (j > 0 && owner.getRandom().nextInt(j) < i) {
					owner.tameWithName(player);
					return;
				}

				owner.modifyTemper(5);
			}

			owner.ejectPassengers();
			owner.makeMad();
			level.broadcastEntityEvent(owner, (byte) 6);
		}
	}

}
