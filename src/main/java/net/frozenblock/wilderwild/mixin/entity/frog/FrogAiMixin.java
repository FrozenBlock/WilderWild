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

package net.frozenblock.wilderwild.mixin.entity.frog;

import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.animal.frog.FrogAi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(value = FrogAi.class, priority = 950)
public class FrogAiMixin {

	@ModifyExpressionValue(
		method = "initSwimActivity",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/ai/behavior/RandomStroll;swim(F)Lnet/minecraft/world/entity/ai/behavior/BehaviorControl;"
		)
	)
	private static BehaviorControl wilderWild$fixFrogSwimmingA(BehaviorControl<PathfinderMob> original) {
		// TODO: Config
		return new RunOne<>(
			ImmutableList.of(
				Pair.of(original, 1),
				Pair.of(new DoNothing(30, 60), 3)
			)
		);
	}

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
		// TODO: Config
		return original.call(
			new RunOne<>(
				ImmutableList.of(
					Pair.of((BehaviorControl)first, 1),
					Pair.of(new DoNothing(30, 60), 3)
				)
			),
			second
		);
	}

}
