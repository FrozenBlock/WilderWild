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

package net.frozenblock.wilderwild.mixin.client.aquatic_movement_fix;

import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.model.animal.axolotl.BabyAxolotlModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.AxolotlRenderState;
import net.minecraft.util.Mth;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClientOnly
@Mixin(BabyAxolotlModel.class)
public class BabyAxolotlModelMixin {

	@Unique
	private ModelPart wilderWild$head;

	@Inject(method = "<init>", at = @At("TAIL"))
	public void wilderWild$init(ModelPart root, CallbackInfo info) {
		this.wilderWild$head = root.getChild("root").getChild("body").getChild("head");
	}

	/**
	 * Fixes <a href="https://bugs.mojang.com/browse/MC/issues/MC-226727">MC-226727</a>
	 */
	@Inject(
		method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/AxolotlRenderState;)V",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/client/model/animal/axolotl/BabyAxolotlModel;swimAnimation:Lnet/minecraft/client/animation/KeyframeAnimation;",
			opcode = Opcodes.GETFIELD
		)
	)
	private void wilderWild$alterWalkMovementThresholdInWater(AxolotlRenderState state, CallbackInfo info) {
		if (!WWEntityConfig.AXOLOTL_HEAD_ROTATION_FIX.get()) return;

		final float notMovingProgress = 1F - state.movingFactor;
		this.wilderWild$head.xRot += state.xRot * Mth.DEG_TO_RAD * notMovingProgress;
	}
}
