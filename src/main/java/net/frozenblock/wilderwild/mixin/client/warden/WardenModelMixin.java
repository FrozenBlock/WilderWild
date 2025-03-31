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

package net.frozenblock.wilderwild.mixin.client.warden;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.client.animation.definitions.WWWardenAnimation;
import net.frozenblock.wilderwild.client.animation.definitions.impl.WilderWarden;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.entity.impl.SwimmingWardenState;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.WardenModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.WardenRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.monster.warden.Warden;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(WardenModel.class)
public abstract class WardenModelMixin extends EntityModel<WardenRenderState> {
	@Unique
	private static final float WILDERWILD$PI_02 = Mth.PI * 0.2F;
	@Final
	@Shadow
	protected ModelPart bone;
	@Final
	@Shadow
	protected ModelPart body;
	@Final
	@Shadow
	protected ModelPart head;
	@Final
	@Shadow
	protected ModelPart rightTendril;
	@Final
	@Shadow
	protected ModelPart leftTendril;
	@Final
	@Shadow
	protected ModelPart leftLeg;
	@Final
	@Shadow
	protected ModelPart leftArm;
	@Final
	@Shadow
	protected ModelPart rightLeg;
	@Final
	@Shadow
	protected ModelPart rightArm;

	protected WardenModelMixin(ModelPart modelPart) {
		super(modelPart);
	}

	@Inject(at = @At("TAIL"), method = "animateTendrils", require = 0)
	private void wilderWild$animateCustomTendrils(
		WardenRenderState warden, float animationProgress, CallbackInfo info,
		@Local(ordinal = 1) float cos
	) {
		if (WWEntityConfig.Client.WARDEN_CUSTOM_TENDRIL_ANIMATION) {
			this.leftTendril.xRot = cos;
			this.rightTendril.xRot = cos;

			float sinDiv = (warden.tendrilAnimation * (float) (-Math.sin(animationProgress * 2.25D) * Mth.PI * 0.1F)) / 2F;
			this.leftTendril.yRot = sinDiv;
			this.rightTendril.yRot = -sinDiv;

			float cosDiv = cos / 2F;
			this.leftTendril.zRot = cosDiv;
			this.rightTendril.zRot = -cosDiv;
		}
	}

	@ModifyExpressionValue(
		method = "setupAnim*",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/client/animation/definitions/WardenAnimation;WARDEN_DIG:Lnet/minecraft/client/animation/AnimationDefinition;",
			opcode = Opcodes.GETSTATIC
		),
		require = 0
	)
	private AnimationDefinition wilderWild$newDigAnim(AnimationDefinition original) {
		if (WWEntityConfig.Client.WARDEN_IMPROVED_DIM_ANIMATION) {
			return WWWardenAnimation.WARDEN_DIG;
		}
		return original;
	}

	@ModifyExpressionValue(
		method = "setupAnim*",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/client/animation/definitions/WardenAnimation;WARDEN_EMERGE:Lnet/minecraft/client/animation/AnimationDefinition;",
			opcode = Opcodes.GETSTATIC
		),
		require = 0
	)
	private AnimationDefinition wilderWild$newEmergeAnim(AnimationDefinition original) {
		if (WWEntityConfig.Client.WARDEN_IMPROVED_EMERGE_ANIMATION) {
			return WWWardenAnimation.WARDEN_EMERGE;
		}
		return original;
	}

	@ModifyExpressionValue(
		method = "setupAnim*",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/client/animation/definitions/WardenAnimation;WARDEN_SNIFF:Lnet/minecraft/client/animation/AnimationDefinition;",
			opcode = Opcodes.GETSTATIC
		),
		require = 0
	)
	private AnimationDefinition wilderWild$bedrockSniffAnim(AnimationDefinition original) {
		if (WWEntityConfig.Client.WARDEN_IMPROVED_SNIFF_ANIMATION) {
			return WWWardenAnimation.WARDEN_SNIFF;
		}
		return original;
	}

	@WrapOperation(
		method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/WardenRenderState;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/model/WardenModel;animateHeadLookTarget(FF)V"
		),
		require = 0
	)
	private void wilderWild$scaleBackHeadWhenSwimming(
		WardenModel instance, float pitch, float yaw, Operation<Void> original,
		WardenRenderState warden,
		@Share("wilderWild$animateSwimming") LocalBooleanRef wilderWild$animateSwimming,
		@Share("wilderWild$swimAmount") LocalFloatRef wilderWild$swimAmount,
		@Share("wilderWild$wadeAmount") LocalFloatRef wilderWild$wadeAmount
	) {
		if (WWEntityConfig.WARDEN_SWIMS && WWEntityConfig.Client.WARDEN_SWIM_ANIMATION && warden instanceof SwimmingWardenState swimmingState) {
			float swimAmount = swimmingState.wilderWild$getSwimAmount();
			float wadeProgress = swimmingState.wilderWild$getWadingProgress();
			wilderWild$animateSwimming.set(wadeProgress > 0F);
			wilderWild$swimAmount.set(swimAmount);
			wilderWild$wadeAmount.set(wadeProgress);

			float notSwimmingAmount = 1F - swimAmount;
			pitch *= notSwimmingAmount;
			yaw *= notSwimmingAmount;
		}
		original.call(instance, pitch, yaw);
	}

	@WrapOperation(
		method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/WardenRenderState;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/model/WardenModel;animateWalk(FF)V"
		),
		require = 0
	)
	private void wilderWild$scaleBackWalkWhenSwimming(
		WardenModel instance, float angle, float distance, Operation<Void> original,
		@Share("wilderWild$swimAmount") LocalFloatRef wilderWild$swimAmount
	) {
		distance *= 1F - wilderWild$swimAmount.get();
		original.call(instance, angle, distance);
	}

	@Inject(
		method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/WardenRenderState;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/model/WardenModel;animate(Lnet/minecraft/world/entity/AnimationState;Lnet/minecraft/client/animation/AnimationDefinition;F)V",
			ordinal = 0
		),
		require = 0
	)
	private void wilderWild$setupAnim(
		WardenRenderState warden, CallbackInfo info,
		@Share("wilderWild$animateSwimming") LocalBooleanRef wilderWild$animateSwimming,
		@Share("wilderWild$swimAmount") LocalFloatRef wilderWild$swimAmount,
		@Share("wilderWild$wadeAmount") LocalFloatRef wilderWild$wadeAmount
	) {
		if (warden instanceof WilderWarden wilderWarden) {
			if (wilderWild$animateSwimming.get()) {
				this.wilderWild$animateSwimming(
					warden,
					!warden.hasPose(Pose.ROARING) && !warden.hasPose(Pose.EMERGING) && !warden.hasPose(Pose.DIGGING),
					!warden.hasPose(Pose.EMERGING)
						&& !warden.hasPose(Pose.DIGGING)
						&& !warden.hasPose(Pose.DYING)
						&& !wilderWarden.wilderWild$getSwimmingDyingAnimationState().isStarted()
						&& !wilderWarden.wilderWild$getKirbyDeathAnimationState().isStarted(),
					wilderWild$swimAmount.get(),
					wilderWild$wadeAmount.get()
				);
			}
			this.animate(wilderWarden.wilderWild$getDyingAnimationState(), WWWardenAnimation.DYING, warden.ageInTicks);
			this.animate(wilderWarden.wilderWild$getSwimmingDyingAnimationState(), WWWardenAnimation.WATER_DYING, warden.ageInTicks);
			this.animate(wilderWarden.wilderWild$getKirbyDeathAnimationState(), WWWardenAnimation.KIRBY_DEATH, warden.ageInTicks);
		}
	}

	@Unique
	private void wilderWild$animateSwimming(
		WardenRenderState renderState,
		boolean moveLimbs,
		boolean canSwim,
		float swimAmount,
		float wadeAmount
	) {
		float angle = renderState.walkAnimationPos;
		float distance = renderState.walkAnimationSpeed;
		float animationProgress = renderState.ageInTicks;
		float headYaw = renderState.yRot;
		float headPitch = renderState.xRot;
		float speedDelta = Math.min(distance / 0.3F, 1F) * swimAmount;
		float swimLerp = swimAmount * speedDelta;

		if ((canSwim && swimLerp > 0) && distance > 0) {
			float angles = angle * (WILDERWILD$PI_02);

			float cos = (float) Math.cos(angles);
			float sin = (float) Math.sin(angles);

			float cos2 = (float) Math.cos(angles * 4F) * 2F;

			this.bone.xRot = Mth.rotLerp(swimLerp, this.bone.xRot, (headPitch * 0.017453292F + 1.5708F));
			this.bone.yRot = Mth.rotLerp(swimLerp, this.bone.yRot, (headYaw * 0.017453292F));
			this.bone.y = Mth.lerp(swimLerp, this.bone.z, 21F) + 3F;

			float legCos = cos * 35F;
			this.leftLeg.xRot = Mth.rotLerp(swimLerp, this.leftLeg.xRot, ((-legCos - 5F) * Mth.DEG_TO_RAD));
			this.rightLeg.xRot = Mth.rotLerp(swimLerp, this.rightLeg.xRot, ((legCos - 5F) * Mth.DEG_TO_RAD));
			if (moveLimbs) {
				this.head.xRot = Mth.rotLerp(swimLerp, this.head.xRot, ((sin * -10F - 60F) * Mth.DEG_TO_RAD));
				this.head.zRot = Mth.rotLerp(swimLerp, this.head.zRot, 0F);
				this.head.yRot = Mth.rotLerp(swimLerp, this.head.yRot, 0F);

				this.body.xRot = Mth.rotLerp(swimLerp, this.body.xRot, ((sin * 15F - 10F) * Mth.DEG_TO_RAD));
				this.body.yRot = Mth.rotLerp(swimLerp, this.body.yRot, (((float) Math.sin(angles * 0.5F) * 5F) * Mth.DEG_TO_RAD));

				this.body.y = Mth.lerp(swimLerp, this.body.y + 21F, 0F);
				this.body.z = Mth.lerp(swimLerp, this.body.z, (cos * 2F));

				float armSin = sin * 90F;
				float cos25 = cos * 25F;
				this.rightArm.xRot = Mth.rotLerp(swimLerp, this.rightArm.xRot, 0F);
				this.rightArm.yRot = Mth.rotLerp(swimLerp, this.rightArm.yRot, ((-cos25) * Mth.DEG_TO_RAD));
				this.rightArm.zRot = Mth.rotLerp(swimLerp, this.rightArm.zRot, ((-armSin + 90F) * Mth.DEG_TO_RAD));

				this.rightArm.x = Mth.lerp(swimLerp, this.rightArm.x, ((cos2 + 2F) - 13F));

				this.leftArm.xRot = Mth.rotLerp(swimLerp, this.leftArm.xRot, 0F);
				this.leftArm.yRot = Mth.rotLerp(swimLerp, this.leftArm.yRot, ((cos25) * Mth.DEG_TO_RAD));
				this.leftArm.zRot = Mth.rotLerp(swimLerp, this.leftArm.zRot, ((armSin - 90F) * Mth.DEG_TO_RAD));

				this.leftArm.x = Mth.lerp(swimLerp, this.leftArm.x, ((-cos2 - 2F) + 13F));
			} else {
				this.body.y = 0F;
			}
			this.rightLeg.y = 8F;
			this.leftLeg.y = 8F;
		}
		float time = animationProgress * 0.1F;

		float timeCos = (float) Math.cos(time) * wadeAmount;
		float timeSin = (float) Math.sin(time) * wadeAmount;
		this.bone.y += timeCos;

		float timeSin5 = timeSin * 5F;
		this.head.xRot += (-timeSin5) * Mth.DEG_TO_RAD;

		this.body.xRot += ((timeCos * -5F) * Mth.DEG_TO_RAD);

		this.leftArm.zRot += ((timeSin5 - 5F) * Mth.DEG_TO_RAD);
		this.rightArm.zRot += (-timeSin5 + 5F) * Mth.DEG_TO_RAD;

		float timeSin15 = timeSin * 15F;
		this.leftLeg.xRot += (timeSin15 + 15F) * Mth.DEG_TO_RAD;
		this.rightLeg.xRot += (-timeSin15 + 15F) * Mth.DEG_TO_RAD;
	}

	@Unique
	private boolean wilderWild$touchingFluid(@NotNull Warden warden) {
		return warden.isInWater() || warden.isInLava();
	}

}
