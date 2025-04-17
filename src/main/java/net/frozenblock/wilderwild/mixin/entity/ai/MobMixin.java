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

package net.frozenblock.wilderwild.mixin.entity.ai;

import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
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

}
