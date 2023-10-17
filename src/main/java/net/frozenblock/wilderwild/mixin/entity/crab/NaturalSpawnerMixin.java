/*
 * Copyright 2023 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.entity.crab;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.lib.mobcategory.api.FrozenMobCategories;
import net.frozenblock.wilderwild.misc.WilderEnumValues;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NaturalSpawner.class)
public class NaturalSpawnerMixin {

	@WrapOperation(
		method = "spawnCategoryForPosition(Lnet/minecraft/world/entity/MobCategory;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/ChunkAccess;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/NaturalSpawner$SpawnPredicate;Lnet/minecraft/world/level/NaturalSpawner$AfterSpawnCallback;)V",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos$MutableBlockPos;set(III)Lnet/minecraft/core/BlockPos$MutableBlockPos;", ordinal = 0)
	)
	private static BlockPos.MutableBlockPos wilderWild$spawnCategoryForPosition(BlockPos.MutableBlockPos mutableBlockPos, int x, int y, int z, Operation<BlockPos.MutableBlockPos> operation, MobCategory category, ServerLevel level, ChunkAccess chunk, BlockPos pos, NaturalSpawner.SpawnPredicate filter, NaturalSpawner.AfterSpawnCallback callback) {
		if (category.getName().equals("wilderwildcrab")) {
			return mutableBlockPos.set(x, level.getHeight(WilderEnumValues.OCEAN_FLOOR_NO_LEAVES, x, z), z);
		}
		return operation.call(mutableBlockPos, x, y, z);
	}

	@WrapOperation(
		method = "spawnCategoryForPosition(Lnet/minecraft/world/entity/MobCategory;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/ChunkAccess;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/NaturalSpawner$SpawnPredicate;Lnet/minecraft/world/level/NaturalSpawner$AfterSpawnCallback;)V",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;moveTo(DDDFF)V", ordinal = 0)
	)
	private static void wilderWild$spawnCategoryForPosition(Mob mob, double x, double y, double z, float yRot, float xRot, Operation<Void> operation, MobCategory category, ServerLevel level, ChunkAccess chunk, BlockPos pos, NaturalSpawner.SpawnPredicate filter, NaturalSpawner.AfterSpawnCallback callback) {
		if (category.getName().equals("wilderwildcrab")) {
			operation.call(mob, x, (double)level.getHeight(WilderEnumValues.OCEAN_FLOOR_NO_LEAVES, (int)x, (int)z), z, yRot, xRot);
		} else {
			operation.call(mob, x, y, z, yRot, xRot);
		}
	}

}
