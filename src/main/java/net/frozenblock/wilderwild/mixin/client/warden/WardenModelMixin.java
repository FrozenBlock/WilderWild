/*
 * Copyright 2023 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.client.warden;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.config.EntityConfig;
import net.frozenblock.wilderwild.entity.render.animations.CustomWardenAnimations;
import net.frozenblock.wilderwild.entity.render.animations.WilderWarden;
import net.minecraft.client.model.WardenModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.monster.warden.Warden;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Environment(EnvType.CLIENT)
@Mixin(WardenModel.class)
public class WardenModelMixin<T extends Warden> {

	@Unique
	private static final float WILDERWILD$RAD = (float) (Math.PI / 180);
	@Unique
	@SuppressWarnings("unchecked")
	private final WardenModel<T> wilderWild$model = WardenModel.class.cast(this);
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

	@Inject(at = @At("TAIL"), method = "animateTendrils", locals = LocalCapture.CAPTURE_FAILHARD, require = 0)
	private void wilderWild$animateCustomTendrils(T warden, float animationProgress, float tickDelta, CallbackInfo info, float cos) { //CUSTOM TENDRIL ANIMATION
		if (EntityConfig.get().warden.wardenCustomTendrils) {
			this.leftTendril.xRot = cos;
			this.rightTendril.xRot = cos;

			float sinDiv = (warden.getTendrilAnimation(tickDelta) * (float) (-Math.sin(animationProgress * 2.25D) * Math.PI * 0.1F)) / 2F;
			this.leftTendril.yRot = sinDiv;
			this.rightTendril.yRot = -sinDiv;

			float cosDiv = cos / 2F;
			this.leftTendril.zRot = cosDiv;
			this.rightTendril.zRot = -cosDiv;
		}
	}

	@Inject(
		method = "setupAnim(Lnet/minecraft/world/entity/monster/warden/Warden;FFFFF)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/model/WardenModel;animate(Lnet/minecraft/world/entity/AnimationState;Lnet/minecraft/client/animation/AnimationDefinition;F)V",
			ordinal = 0,
			shift = At.Shift.BEFORE
		),
		require = 0
	)
	private void wilderWild$setupAnim(T warden, float angle, float distance, float anim, float headYaw, float headPitch, CallbackInfo info) {
		if (EntityConfig.get().warden.swimAndAnimationConfigEnabled() && wilderWild$isSubmerged(warden)) {
			this.wilderWild$animateSwimming(
				warden,
				angle,
				distance,
				anim,
				headYaw,
				headPitch,
				!warden.hasPose(Pose.ROARING) && !warden.hasPose(Pose.EMERGING) && !warden.hasPose(Pose.DIGGING),
				!warden.hasPose(Pose.EMERGING)
					&& !warden.hasPose(Pose.DIGGING)
					&& !warden.hasPose(Pose.DYING)
					&& !((WilderWarden) warden).wilderWild$getSwimmingDyingAnimationState().isStarted()
					&& !((WilderWarden) warden).wilderWild$getKirbyDeathAnimationState().isStarted()
			);
		}
		wilderWild$model.animate(((WilderWarden) warden).wilderWild$getDyingAnimationState(), CustomWardenAnimations.DYING, anim);
		wilderWild$model.animate(((WilderWarden) warden).wilderWild$getSwimmingDyingAnimationState(), CustomWardenAnimations.WATER_DYING, anim);
		wilderWild$model.animate(((WilderWarden) warden).wilderWild$getKirbyDeathAnimationState(), CustomWardenAnimations.KIRBY_DEATH, anim);
	}

	@Unique
	private static final float WILDERWILD$FAST_ANGLE = (float) (Math.PI * 0.2F);

	@Unique
	private void wilderWild$animateSwimming(@NotNull T warden, float angle, float distance, float anim, float headYaw, float headPitch, boolean moveLimbs, boolean canSwim) {
		float swimming = (warden.isVisuallySwimming() && distance > 0) ? 1F : 0F;
		float notSwimming = Math.abs(swimming - 1F);

		float submerged = wilderWild$isSubmerged(warden) ? 1F : 0F;
		float notSubmerged = Math.abs(submerged - 1F);

		float lerpTime = anim - (float) warden.tickCount;
		float submergedLerp = Mth.rotLerp(warden.getSwimAmount(lerpTime), notSubmerged, submerged);
		float speedDelta = Math.min(distance / 0.3F, 1F) * submergedLerp;
		float swimLerp = Mth.rotLerp(warden.getSwimAmount(lerpTime), notSwimming, swimming) * speedDelta;

		if (((warden.isVisuallySwimming() && canSwim) || (swimLerp > 0)) && distance > 0) {
			//TODO: make swim animation last until lerp is done when exiting water. how.
			float angles = angle * (WILDERWILD$FAST_ANGLE);

			float cos = (float) Math.cos(angles);
			float sin = (float) Math.sin(angles);

			float cos2 = (float) Math.cos(angles * 4F) * 2F;

			this.bone.xRot = Mth.rotLerp(swimLerp, this.bone.xRot, (headPitch * 0.017453292F + 1.5708F));
			this.bone.yRot = Mth.rotLerp(swimLerp, this.bone.yRot, (headYaw * 0.017453292F));
			this.bone.y = Mth.lerp(swimLerp, this.bone.z, 21) + 3;

			float legCos = cos * 35F;
			this.leftLeg.xRot = Mth.rotLerp(swimLerp, this.leftLeg.xRot, ((-legCos - 5F) * WILDERWILD$RAD));
			this.rightLeg.xRot = Mth.rotLerp(swimLerp, this.rightLeg.xRot, ((legCos - 5F) * WILDERWILD$RAD));
			if (moveLimbs) {
				this.head.xRot = Mth.rotLerp(swimLerp, this.head.xRot, ((sin * -10F - 60F) * WILDERWILD$RAD));
				this.head.zRot = Mth.rotLerp(swimLerp, this.head.zRot, 0);
				this.head.yRot = Mth.rotLerp(swimLerp, this.head.yRot, 0);

				this.body.xRot = Mth.rotLerp(swimLerp, this.body.xRot, ((sin * 15F - 10F) * WILDERWILD$RAD));
				this.body.yRot = Mth.rotLerp(swimLerp, this.body.yRot, (((float) Math.sin(angles * 0.5F) * 5F) * WILDERWILD$RAD));

				this.body.y = Mth.lerp(swimLerp, this.body.y + 21F, 0F);
				this.body.z = Mth.lerp(swimLerp, this.body.z, (cos * 2F));

				float armSin = sin * 90F;
				float cos25 = cos * 25F;
				this.rightArm.xRot = Mth.rotLerp(swimLerp, this.rightArm.xRot, 0F);
				this.rightArm.yRot = Mth.rotLerp(swimLerp, this.rightArm.yRot, ((-cos25) * WILDERWILD$RAD));
				this.rightArm.zRot = Mth.rotLerp(swimLerp, this.rightArm.zRot, ((-armSin + 90F) * WILDERWILD$RAD));

				this.rightArm.x = Mth.lerp(swimLerp, this.rightArm.x, ((cos2 + 2F) - 13F));

				this.leftArm.xRot = Mth.rotLerp(swimLerp, this.leftArm.xRot, 0F);
				this.leftArm.yRot = Mth.rotLerp(swimLerp, this.leftArm.yRot, ((cos25) * WILDERWILD$RAD));
				this.leftArm.zRot = Mth.rotLerp(swimLerp, this.leftArm.zRot, ((armSin - 90F) * WILDERWILD$RAD));

				this.leftArm.x = Mth.lerp(swimLerp, this.leftArm.x, ((-cos2 - 2F) + 13F));
			} else {
				this.body.y = 0F;
			}
			this.rightLeg.y = 8F;
			this.leftLeg.y = 8F;
		} else if (this.wilderWild$isSubmerged(warden) && distance <= 0) {
			this.body.y = 0;
			wilderWild$model.root().getAllParts().forEach(ModelPart::resetPose);
		} else {
			float time = anim * 0.1F;

			float timeCos = (float) Math.cos(time);
			float timeSin = (float) Math.sin(time);
			this.bone.y += timeCos;

			float timeSin5 = timeSin * 5F;
			this.head.xRot += (-timeSin5) * WILDERWILD$RAD;

			this.body.xRot += ((timeCos * -5F) * WILDERWILD$RAD);

			this.leftArm.zRot += ((timeSin5 - 5F) * WILDERWILD$RAD);
			this.rightArm.zRot += (-timeSin5 + 5F) * WILDERWILD$RAD;

			float timeSin15 = timeSin * 15F;
			this.leftLeg.xRot += (timeSin15 + 15F) * WILDERWILD$RAD;
			this.rightLeg.xRot += (-timeSin15 + 15F) * WILDERWILD$RAD;
		}
	}

	@Unique
	private boolean wilderWild$isSubmerged(@NotNull Warden warden) {
		return warden.isInWaterOrBubble() || warden.isEyeInFluid(FluidTags.LAVA);
	}

}
