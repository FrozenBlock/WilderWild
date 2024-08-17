/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.client.allay;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.config.EntityConfig;
import net.frozenblock.wilderwild.entity.render.animations.CustomAllayAnimations;
import net.frozenblock.wilderwild.entity.render.animations.WilderAllay;
import net.minecraft.client.model.AllayModel;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.world.entity.animal.allay.Allay;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(AllayModel.class)
public abstract class AllayModelMixin extends HierarchicalModel<Allay> implements ArmedModel {

	@ModifyExpressionValue(
		method = "setupAnim(Lnet/minecraft/world/entity/animal/allay/Allay;FFFFF)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/animal/allay/Allay;isDancing()Z",
			ordinal = 0
		),
		require = 0
	)
	private boolean wilderWild$alterDanceCheck(boolean original) {
		return original && !EntityConfig.Client.KEYFRAME_ALLAY_DANCE;
	}

	@Inject(
		method = "setupAnim(Lnet/minecraft/world/entity/animal/allay/Allay;FFFFF)V",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/client/model/AllayModel;right_wing:Lnet/minecraft/client/model/geom/ModelPart;",
			opcode = Opcodes.GETFIELD,
			ordinal = 0,
			shift = At.Shift.BEFORE
		),
		require = 0
	)
	private void wilderWild$runKeyframeDance(Allay allay, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo info) {
		if (EntityConfig.Client.KEYFRAME_ALLAY_DANCE && allay instanceof WilderAllay wilderAllay) {
			this.animate(wilderAllay.wilderWild$getDancingAnimationState(), CustomAllayAnimations.DANCING, ageInTicks);
		}
	}
}
