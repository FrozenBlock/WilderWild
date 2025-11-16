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

package net.frozenblock.wilderwild.mixin.entity.turtle;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.entity.ai.turtle.TurtleNearestAttackableGoal;
import net.frozenblock.wilderwild.entity.impl.TurtleCooldownInterface;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Turtle.class, priority = 990)
public class TurtleMixin implements TurtleCooldownInterface {

	@Unique
	private int wilderWild$attackCooldown;

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

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	public void wilderWild$addAdditionalSaveData(ValueOutput valueOutput, CallbackInfo info) {
		valueOutput.putInt("AttackCooldown", this.wilderWild$attackCooldown);
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void wilderWild$readAdditionalSaveData(ValueInput valueInput, CallbackInfo info) {
		this.wilderWild$attackCooldown = valueInput.getIntOr("AttackCooldown", 0);
	}

	@Inject(method = "aiStep", at = @At("TAIL"))
	public void wilderWild$aiStep(CallbackInfo info) {
		if (this.wilderWild$attackCooldown > 0) this.wilderWild$attackCooldown -= 1;
	}

	@Unique
	@Override
	public int wilderWild$getAttackCooldown() {
		return this.wilderWild$attackCooldown;
	}

	@Unique
	@Override
	public void wilderWild$setAttackCooldown(int i) {
		this.wilderWild$attackCooldown = i;
	}

}
