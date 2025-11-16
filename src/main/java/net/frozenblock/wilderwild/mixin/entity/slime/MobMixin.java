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

package net.frozenblock.wilderwild.mixin.entity.slime;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.wilderwild.block.AlgaeBlock;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Mob.class)
public class MobMixin {

	@ModifyExpressionValue(
		method = "checkSpawnObstruction",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/LevelReader;containsAnyLiquid(Lnet/minecraft/world/phys/AABB;)Z"
		)
	)
	public boolean wilderWild$checkSpawnObstruction(boolean original) {
		if (!(Mob.class.cast(this) instanceof Mob mob) || mob.getType() != EntityType.SLIME) return  original;
		return original && !AlgaeBlock.hasNearbyAlgae(mob.level(), mob.blockPosition(), 1, 3);
	}

}
