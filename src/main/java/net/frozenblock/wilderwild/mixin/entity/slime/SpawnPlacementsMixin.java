/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.entity.slime;

import java.util.Map;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpawnPlacements.class)
public class SpawnPlacementsMixin {

	@Final
	@Shadow
	private static Map<EntityType<?>, SpawnPlacements.Data> DATA_BY_TYPE;

	@Inject(method = "register", at = @At("HEAD"), cancellable = true)
	private static <T extends Mob> void wilderWild$register(EntityType<T> type, SpawnPlacements.Type location, Heightmap.Types heightmapType, SpawnPlacements.SpawnPredicate<T> predicate, CallbackInfo info) {
		if (type == EntityType.SLIME) {
			info.cancel();
			DATA_BY_TYPE.put(type, new SpawnPlacements.Data(heightmapType, SpawnPlacements.Type.NO_RESTRICTIONS, predicate));
		}
	}

}
