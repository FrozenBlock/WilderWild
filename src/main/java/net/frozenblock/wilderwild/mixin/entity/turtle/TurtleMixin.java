/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.mixin.entity.turtle;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.entity.ai.turtle.TurtleNearestAttackableGoal;
import net.frozenblock.wilderwild.entity.impl.TurtleCooldownInterface;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.animal.Turtle;
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
		Turtle turtle = Turtle.class.cast(this);
		turtle.goalSelector.addGoal(3, new MeleeAttackGoal(turtle, 1D, true));
		turtle.targetSelector.addGoal(10, new TurtleNearestAttackableGoal<>(turtle, Jellyfish.class, false));
	}

	@Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
	public void wilderWild$addAdditionalSaveData(CompoundTag compoundTag, CallbackInfo info) {
		compoundTag.putInt("AttackCooldown", this.wilderWild$attackCooldown);
	}

	@Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
	public void wilderWild$readAdditionalSaveData(CompoundTag compoundTag, CallbackInfo info) {
		this.wilderWild$attackCooldown = compoundTag.getInt("AttackCooldown");
	}

	@Inject(method = "aiStep", at = @At("TAIL"))
	public void wilderWild$aiStep(CallbackInfo info) {
		if (this.wilderWild$attackCooldown > 0) {
			this.wilderWild$attackCooldown -= 1;
		}
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
