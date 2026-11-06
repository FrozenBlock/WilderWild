/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.entity.aquatic_movement_fix.frog;

import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.datafixers.util.Pair;
import net.frozenblock.lib.platform.ModLoader;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.animal.frog.FrogAi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(value = FrogAi.class, priority = 950)
public class FrogAiMixin {

	/**
	 * Fixes <a href="https://bugs.mojang.com/browse/MC/issues/MC-251529">MC-251529</a>
	 * @see wilderWild$fixFrogSwimmingB
	 */
	@ModifyExpressionValue(
		method = "initSwimActivity",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/ai/behavior/RandomStroll;swim(F)Lnet/minecraft/world/entity/ai/behavior/BehaviorControl;"
		)
	)
	private static BehaviorControl wilderWild$fixFrogSwimmingA(BehaviorControl<PathfinderMob> original) {
		if (!WWEntityConfig.FROG_IDLE_IN_WATER_FIX.get()) return original;

		return new RunOne<>(
			ImmutableList.of(
				Pair.of(original, 1),
				Pair.of(new DoNothing(30, 60), 3)
			)
		);
	}

	/**
	 * Fixes <a href="https://bugs.mojang.com/browse/MC/issues/MC-251529">MC-251529</a>
	 * @see wilderWild$fixFrogSwimmingA
	 */
	@WrapOperation(
		method = "initSwimActivity",
		at = @At(
			value = "INVOKE",
			target = "Lcom/mojang/datafixers/util/Pair;of(Ljava/lang/Object;Ljava/lang/Object;)Lcom/mojang/datafixers/util/Pair;",
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/world/entity/ai/behavior/RandomStroll;stroll(FZ)Lnet/minecraft/world/entity/ai/behavior/OneShot;"
			)
		)
	)
	private static Pair wilderWild$fixFrogSwimmingB(
		Object first,
		Object second,
		Operation<Pair> original
	) {
		if (!WWEntityConfig.FROG_IDLE_IN_WATER_FIX.get()) return original.call(first, second);
		if (ModLoader.isDevelopmentEnvironment() && !(first instanceof OneShot<?>)) throw new IllegalStateException("FROG SWIMMING FIX B BROKEN");

		return original.call(
			new RunOne<>(
				ImmutableList.of(
					Pair.of((BehaviorControl) first, 1),
					Pair.of(new DoNothing(30, 60), 3)
				)
			),
			second
		);
	}
}
