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
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.monster.warden.WardenModel;
import net.minecraft.client.renderer.entity.state.WardenRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;
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
	@Shadow
	@Final
	private KeyframeAnimation diggingAnimation;
	@Shadow
	@Final
	private KeyframeAnimation emergeAnimation;
	@Shadow
	@Final
	private KeyframeAnimation sniffAnimation;

	protected WardenModelMixin(ModelPart root) {
		super(root);
	}

	@Unique
	private KeyframeAnimation wilderWild$digAnimation;
	@Unique
	private KeyframeAnimation wilderWild$emergeAnimation;
	@Unique
	private KeyframeAnimation wilderWild$sniffAnimation;
	@Unique
	private KeyframeAnimation wilderWild$dyingAnimation;
	@Unique
	private KeyframeAnimation wilderWild$waterDyingAnimation;
	@Unique
	private KeyframeAnimation wilderWild$kirbyDyingAnimation;

	@Inject(method = "<init>", at = @At("TAIL"))
	public void wilderWild$init(ModelPart root, CallbackInfo info) {
		this.wilderWild$digAnimation = WWWardenAnimation.WARDEN_DIG.bake(root);
		this.wilderWild$emergeAnimation = WWWardenAnimation.WARDEN_EMERGE.bake(root);
		this.wilderWild$sniffAnimation = WWWardenAnimation.WARDEN_SNIFF.bake(root);
		this.wilderWild$dyingAnimation = WWWardenAnimation.DYING.bake(root);
		this.wilderWild$waterDyingAnimation = WWWardenAnimation.WATER_DYING.bake(root);
		this.wilderWild$kirbyDyingAnimation = WWWardenAnimation.KIRBY_DEATH.bake(root);
	}

	@Inject(at = @At("TAIL"), method = "animateTendrils", require = 0)
	private void wilderWild$animateCustomTendrils(
		WardenRenderState warden, float animationProgress, CallbackInfo info,
		@Local(ordinal = 1) float cos
	) {
		if (!WWEntityConfig.Client.WARDEN_CUSTOM_TENDRIL_ANIMATION) return;
		this.leftTendril.xRot = cos;
		this.rightTendril.xRot = cos;

		final float yRot = (warden.tendrilAnimation * (-Mth.sin(animationProgress * 2.25D) * Mth.PI * 0.1F)) / 2F;
		this.leftTendril.yRot = yRot;
		this.rightTendril.yRot = -yRot;

		final float zRot = cos / 2F;
		this.leftTendril.zRot = zRot;
		this.rightTendril.zRot = -zRot;
	}

	@ModifyExpressionValue(
		method = "setupAnim*",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/client/model/monster/warden/WardenModel;diggingAnimation:Lnet/minecraft/client/animation/KeyframeAnimation;"
		),
		require = 0
	)
	private KeyframeAnimation wilderWild$newDigAnim(KeyframeAnimation original) {
		if (WWEntityConfig.Client.WARDEN_IMPROVED_DIG_ANIMATION && this.diggingAnimation != null) return this.wilderWild$digAnimation;
		return original;
	}

	@ModifyExpressionValue(
		method = "setupAnim*",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/client/model/monster/warden/WardenModel;emergeAnimation:Lnet/minecraft/client/animation/KeyframeAnimation;"
		),
		require = 0
	)
	private KeyframeAnimation wilderWild$newEmergeAnim(KeyframeAnimation original) {
		if (WWEntityConfig.Client.WARDEN_IMPROVED_EMERGE_ANIMATION && this.emergeAnimation != null) return this.wilderWild$emergeAnimation;
		return original;
	}

	@ModifyExpressionValue(
		method = "setupAnim*",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/client/model/monster/warden/WardenModel;sniffAnimation:Lnet/minecraft/client/animation/KeyframeAnimation;"
		),
		require = 0
	)
	private KeyframeAnimation wilderWild$bedrockSniffAnim(KeyframeAnimation original) {
		if (WWEntityConfig.Client.WARDEN_IMPROVED_SNIFF_ANIMATION && this.sniffAnimation != null) return this.wilderWild$sniffAnimation;
		return original;
	}

	@WrapOperation(
		method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/WardenRenderState;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/model/monster/warden/WardenModel;animateHeadLookTarget(FF)V"
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
			final float swimAmount = swimmingState.wilderWild$getSwimAmount();
			final float wadeProgress = swimmingState.wilderWild$getWadingProgress();
			wilderWild$animateSwimming.set(wadeProgress > 0F);
			wilderWild$swimAmount.set(swimAmount);
			wilderWild$wadeAmount.set(wadeProgress);

			final float notSwimmingAmount = 1F - swimAmount;
			pitch *= notSwimmingAmount;
			yaw *= notSwimmingAmount;
		}
		original.call(instance, pitch, yaw);
	}

	@WrapOperation(
		method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/WardenRenderState;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/model/monster/warden/WardenModel;animateWalk(FF)V"
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
			target = "Lnet/minecraft/client/animation/KeyframeAnimation;apply(Lnet/minecraft/world/entity/AnimationState;F)V",
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
		if (!(warden instanceof WilderWarden wilderWarden)) return;
		if (wilderWild$animateSwimming.get()) {
			this.wilderWild$animateSwimming(
				warden,
				!warden.hasPose(Pose.ROARING) && !warden.hasPose(Pose.EMERGING) && !warden.hasPose(Pose.DIGGING),
				!warden.hasPose(Pose.EMERGING)
					&& !warden.hasPose(Pose.DIGGING)
					&& !warden.hasPose(Pose.DYING)
					&& !wilderWarden.wilderWild$swimmingDyingAnimationState().isStarted()
					&& !wilderWarden.wilderWild$kirbyDeathAnimationState().isStarted(),
				wilderWild$swimAmount.get(),
				wilderWild$wadeAmount.get()
			);
		}
		if (this.wilderWild$dyingAnimation != null) this.wilderWild$dyingAnimation.apply(wilderWarden.wilderWild$dyingAnimationState(), warden.ageInTicks);
		if (this.wilderWild$waterDyingAnimation != null) this.wilderWild$waterDyingAnimation.apply(wilderWarden.wilderWild$swimmingDyingAnimationState(), warden.ageInTicks);
		if (this.wilderWild$kirbyDyingAnimation != null) this.wilderWild$kirbyDyingAnimation.apply(wilderWarden.wilderWild$kirbyDeathAnimationState(), warden.ageInTicks);
	}

	@Unique
	private void wilderWild$animateSwimming(
		WardenRenderState renderState,
		boolean moveLimbs,
		boolean canSwim,
		float swimAmount,
		float wadeAmount
	) {
		final float angle = renderState.walkAnimationPos;
		final float distance = renderState.walkAnimationSpeed;
		final float animationProgress = renderState.ageInTicks;
		final float headYaw = renderState.yRot;
		final float headPitch = renderState.xRot;
		final float speedDelta = Math.min(distance / 0.3F, 1F) * swimAmount;
		final float swimLerp = swimAmount * speedDelta;

		if ((canSwim && swimLerp > 0) && distance > 0) {
			final float angles = angle * (WILDERWILD$PI_02);
			final float cos = Mth.cos(angles);
			final float sin = Mth.sin(angles);
			final float cos2 = Mth.cos(angles * 4F) * 2F;

			this.bone.xRot = Mth.rotLerp(swimLerp, this.bone.xRot, (headPitch * 0.017453292F + 1.5708F));
			this.bone.yRot = Mth.rotLerp(swimLerp, this.bone.yRot, (headYaw * 0.017453292F));
			this.bone.y = Mth.lerp(swimLerp, this.bone.z, 21F) + 3F;

			final float legCos = cos * 35F;
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

				final float armSin = sin * 90F;
				final float cos25 = cos * 25F;
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
		final float time = animationProgress * 0.1F;

		final float timeCos = Mth.cos(time) * wadeAmount;
		final float timeSin = Mth.sin(time) * wadeAmount;
		this.bone.y += timeCos;

		final float timeSin5 = timeSin * 5F;
		this.head.xRot += (-timeSin5) * Mth.DEG_TO_RAD;

		this.body.xRot += ((timeCos * -5F) * Mth.DEG_TO_RAD);

		this.leftArm.zRot += ((timeSin5 - 5F) * Mth.DEG_TO_RAD);
		this.rightArm.zRot += (-timeSin5 + 5F) * Mth.DEG_TO_RAD;

		final float timeSin15 = timeSin * 15F;
		this.leftLeg.xRot += (timeSin15 + 15F) * Mth.DEG_TO_RAD;
		this.rightLeg.xRot += (-timeSin15 + 15F) * Mth.DEG_TO_RAD;
	}

}
