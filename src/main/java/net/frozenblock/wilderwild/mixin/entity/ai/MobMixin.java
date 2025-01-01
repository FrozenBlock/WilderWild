/*
 * Copyright 2023-2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.mixin.entity.ai;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public class MobMixin {

	@Shadow
	public void setPathfindingMalus(PathType pathType, float malus) {
		throw new AssertionError("Mixin injection failed - Wilder Wild MobMixin.");
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void wilderWild$addUnpassableRail(EntityType<? extends Mob> entityType, Level level, CallbackInfo info) {
		if (WWEntityConfig.get().unpassableRail) {
			this.setPathfindingMalus(PathType.UNPASSABLE_RAIL, 0F);
		}
	}

	@ModifyExpressionValue(
		method = "checkSpawnObstruction",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/LevelReader;containsAnyLiquid(Lnet/minecraft/world/phys/AABB;)Z"
		)
	)
	public boolean wilderWild$checkSpawnObstruction(boolean original) {
		if (Mob.class.cast(this) instanceof Slime slime) {
			return original || !WWBlocks.ALGAE.hasAmountNearby(slime.level(), slime.blockPosition(), 1, 3);
		}
		return original;
	}

}
