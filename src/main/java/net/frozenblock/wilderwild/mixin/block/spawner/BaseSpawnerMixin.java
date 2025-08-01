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

package net.frozenblock.wilderwild.mixin.block.spawner;

import net.frozenblock.wilderwild.entity.Firefly;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BaseSpawner.class)
public class BaseSpawnerMixin {

	@Shadow
	@Nullable
	private Entity displayEntity;

	@Inject(
		method = "clientTick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/Level;getRandom()Lnet/minecraft/util/RandomSource;",
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "FIELD",
				target = "Lnet/minecraft/world/level/BaseSpawner;displayEntity:Lnet/minecraft/world/entity/Entity;",
				opcode = Opcodes.GETFIELD
			)
		)
	)
	public void wilderWild$clientTick(Level level, BlockPos blockPos, CallbackInfo info) {
		if (!(this.displayEntity instanceof Firefly firefly)) return;
		firefly.setAnimScale(2F);
		firefly.setPrevAnimScale(2F);
	}

}
