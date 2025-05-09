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

package net.frozenblock.wilderwild.mixin.block.frozen_vegetation;

import java.util.function.Predicate;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.world.entity.ai.goal.EatBlockGoal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EatBlockGoal.class)
public class EatBlockGoalMixin {

	@Shadow
	@Final
	@Mutable
	private static Predicate<BlockState> IS_TALL_GRASS;

	@Inject(method = "<clinit>", at = @At(value = "TAIL"))
	private static void wilderWild$fixTallGrassPredicateForSnowy(CallbackInfo info) {
		IS_TALL_GRASS = IS_TALL_GRASS.or(BlockStatePredicate.forBlock(WWBlocks.FROZEN_SHORT_GRASS));
	}

}
