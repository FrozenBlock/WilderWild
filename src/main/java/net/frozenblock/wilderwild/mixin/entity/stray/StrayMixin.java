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

package net.frozenblock.wilderwild.mixin.entity.stray;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Stray.class)
public class StrayMixin {

	@WrapOperation(
		method = "checkStraySpawnRules",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/ServerLevelAccessor;canSeeSky(Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	private static boolean wilderWild$spawnInFrozenCaves(ServerLevelAccessor instance, BlockPos pos, Operation<Boolean> original) {
		return original.call(instance, pos) || instance.getBiome(pos).is(WWBiomeTags.STRAYS_CAN_SPAWN_UNDERGROUND);
	}

}
