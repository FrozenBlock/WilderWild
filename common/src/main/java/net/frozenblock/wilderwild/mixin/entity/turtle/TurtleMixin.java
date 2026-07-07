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

package net.frozenblock.wilderwild.mixin.entity.turtle;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.entity.ai.turtle.TurtleNearestAttackableGoal;
import net.frozenblock.wilderwild.registry.WWAttachmentTypes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.animal.turtle.Turtle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Turtle.class, priority = 990)
public class TurtleMixin {

	@ModifyReturnValue(method = "createAttributes", at = @At("RETURN"))
	private static AttributeSupplier.Builder wilderWild$createAttributes(AttributeSupplier.Builder original) {
		original.add(Attributes.ATTACK_DAMAGE, 3D);
		return original;
	}

	@Inject(method = "registerGoals", at = @At("TAIL"))
	public void wilderWild$registerGoals(CallbackInfo info) {
		final Turtle turtle = Turtle.class.cast(this);
		turtle.goalSelector.addGoal(3, new MeleeAttackGoal(turtle, 1D, true));
		turtle.targetSelector.addGoal(10, new TurtleNearestAttackableGoal<>(turtle, Jellyfish.class, false));
	}

	@Inject(method = "aiStep", at = @At("TAIL"))
	public void wilderWild$aiStep(CallbackInfo info) {
		Turtle.class.cast(this).frozenLib$modifyAttached(WWAttachmentTypes.TURTLE_HUNT_COOLDOWN, cooldown -> cooldown == null ? 0 : Math.max(cooldown - 1, 0));
	}
}
