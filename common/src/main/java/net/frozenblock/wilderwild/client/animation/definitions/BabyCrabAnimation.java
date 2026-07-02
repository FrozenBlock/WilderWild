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

package net.frozenblock.wilderwild.client.animation.definitions;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

@Environment(EnvType.CLIENT)
public final class BabyCrabAnimation {
	public static final AnimationDefinition BABY_CRAB_DIG = AnimationDefinition.Builder.withLength(4.7917F)
		.addAnimation("claw_top", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0F, 0F, 27.5F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("claw_top", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0.4167F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9583F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("claw_bottom", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.875F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9583F, KeyframeAnimations.degreeVec(0F, 0F, -5F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("claw_bottom", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0.9583F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("back_right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.125F, KeyframeAnimations.degreeVec(0F, 0F, 15F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.degreeVec(0F, 0F, 15F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-7.5F, 0F, 15F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.9167F, KeyframeAnimations.degreeVec(-15.1622F, 3.4124F, -11.3413F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3F, KeyframeAnimations.degreeVec(7.5F, 0F, -15F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.0833F, KeyframeAnimations.degreeVec(-20F, 0F, -15F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.125F, KeyframeAnimations.degreeVec(-32.5F, 0F, -15F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.5F, KeyframeAnimations.degreeVec(-45.8333F, 0F, -15F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.9583F, KeyframeAnimations.degreeVec(-78.3333F, 0F, -15F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.2917F, KeyframeAnimations.degreeVec(-110.8333F, 0F, 15F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.5417F, KeyframeAnimations.degreeVec(-133.3333F, 0F, 15F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("back_right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.0417F, KeyframeAnimations.posVec(0F, 1.58F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.125F, KeyframeAnimations.posVec(0F, 1F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.posVec(0F, 1F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.posVec(0F, 0.75F, 0.25F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.9167F, KeyframeAnimations.posVec(0F, 0.5F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3F, KeyframeAnimations.posVec(0F, 1.75F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.0833F, KeyframeAnimations.posVec(0F, 1F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.125F, KeyframeAnimations.posVec(0F, 0F, -0.25F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.5F, KeyframeAnimations.posVec(0F, -0.5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.9583F, KeyframeAnimations.posVec(0F, -2.25F, 0.25F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.2917F, KeyframeAnimations.posVec(0F, -3.5F, -0.25F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.5417F, KeyframeAnimations.posVec(0F, -4.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.7083F, KeyframeAnimations.posVec(0F, -6F, -0.75F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("middle_left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.125F, KeyframeAnimations.degreeVec(-7.5F, 0F, -18.57F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(-20F, 0F, -32.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-20F, 0F, -32.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-27.5F, 0F, -32.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7083F, KeyframeAnimations.degreeVec(-37.5F, 0F, -32.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-37.5F, 0F, -32.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-62.5F, 0F, -32.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.1667F, KeyframeAnimations.degreeVec(-72.5F, 0F, -32.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.25F, KeyframeAnimations.degreeVec(-72.5F, 0F, -32.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.3333F, KeyframeAnimations.degreeVec(-59.608F, 3.2313F, -28.6638F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.4167F, KeyframeAnimations.degreeVec(-46.1071F, 5.7402F, -21.3046F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.5F, KeyframeAnimations.degreeVec(-37.5F, 0F, -32.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.5F, KeyframeAnimations.degreeVec(-80F, 0F, -32.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.75F, KeyframeAnimations.degreeVec(-95.9194F, 0F, -32.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.7083F, KeyframeAnimations.degreeVec(-143.4194F, 0F, -32.5F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("middle_left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.125F, KeyframeAnimations.posVec(0F, 0F, -0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.posVec(0F, 1F, -0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3333F, KeyframeAnimations.posVec(0F, 1.5F, -0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0F, 1.5F, -0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7083F, KeyframeAnimations.posVec(0F, 0F, -0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.posVec(0F, 0F, -0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.posVec(0F, -1F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.1667F, KeyframeAnimations.posVec(0F, -1.75F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.25F, KeyframeAnimations.posVec(0F, -1.75F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.4167F, KeyframeAnimations.posVec(0F, -1F, 1.25F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.5F, KeyframeAnimations.posVec(0F, -1.75F, 1.25F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.5417F, KeyframeAnimations.posVec(0F, -3.12F, 0.66F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.5F, KeyframeAnimations.posVec(0F, -5F, -1.25F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.75F, KeyframeAnimations.posVec(0F, -5.25F, -1.25F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.7083F, KeyframeAnimations.posVec(0F, -9.75F, -3.75F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("legs", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.125F, KeyframeAnimations.degreeVec(17.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("legs", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.125F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("back_right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.0417F, KeyframeAnimations.degreeVec(0F, 0F, -40F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.125F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.degreeVec(0F, 0F, 30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.875F, KeyframeAnimations.degreeVec(-36.3571F, -12.2766F, 42.5685F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.0833F, KeyframeAnimations.degreeVec(-41.1261F, -24.9507F, 34.0787F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.4167F, KeyframeAnimations.degreeVec(-8.542F, -31.0692F, 0.4536F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.9167F, KeyframeAnimations.degreeVec(-1.3179F, -14.3638F, -28.3211F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3F, KeyframeAnimations.degreeVec(21.7357F, -20.5806F, -96.6153F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.0833F, KeyframeAnimations.degreeVec(-34.7255F, 9.0055F, 35.5401F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("back_right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.posVec(0.1F, 0.5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.875F, KeyframeAnimations.posVec(0.1F, 0.2F, 0.7F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.0833F, KeyframeAnimations.posVec(0F, 0.1F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.4167F, KeyframeAnimations.posVec(0F, -0.1F, 0.3F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.9167F, KeyframeAnimations.posVec(-0.1F, -0.15F, -0.2F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3F, KeyframeAnimations.posVec(-0.1F, -0.15F, 0.3F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.0833F, KeyframeAnimations.posVec(0.4F, -1.15F, 0.3F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.7917F, KeyframeAnimations.posVec(0.4F, -6.15F, -1.2F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("front_right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-20F, 0F, 42.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-25F, 0F, 42.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(-24.7676F, 2.1539F, 54.8159F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-23.6588F, -9.018F, 33.8412F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.0833F, KeyframeAnimations.degreeVec(-56.1588F, -9.018F, 33.8412F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("front_right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.1667F, KeyframeAnimations.posVec(0F, 0.4F, 0.25F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4167F, KeyframeAnimations.posVec(0F, 1.3F, 0.15F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.posVec(0F, 0.2F, 0.15F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.posVec(-0.1F, -0.55F, -0.35F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5833F, KeyframeAnimations.posVec(-0.11F, -0.78F, 0.22F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.875F, KeyframeAnimations.posVec(-0.1F, -0.79F, 0.47F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.0833F, KeyframeAnimations.posVec(-0.1F, -0.95F, 0.25F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.7083F, KeyframeAnimations.posVec(-0.1F, -2.18F, 0.18F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.0417F, KeyframeAnimations.posVec(-0.1F, -2.54F, -0.07F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.5F, KeyframeAnimations.posVec(-0.1F, -3.17F, -0.6F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.9167F, KeyframeAnimations.posVec(-0.1F, -4.52F, -1.18F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.4167F, KeyframeAnimations.posVec(-0.1F, -6.82F, -1.84F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.7917F, KeyframeAnimations.posVec(-0.1F, -7.45F, -2.25F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("middle_right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-8.6119F, 11.2146F, 7.53F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0F, 25F, -40F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0F, 0F, 30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(-3.5431F, -12.8083F, 28.8385F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-5.6928F, -16.8855F, 24.6653F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.875F, KeyframeAnimations.degreeVec(-20.98F, -15.8299F, -17.9424F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.0833F, KeyframeAnimations.degreeVec(-18.9532F, -16.4025F, -27.6927F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.4167F, KeyframeAnimations.degreeVec(-14.6244F, -20.8373F, -33.8672F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.5417F, KeyframeAnimations.degreeVec(15.9445F, -11.6137F, -63.4499F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.6667F, KeyframeAnimations.degreeVec(-36.397F, -10.0588F, 28.3986F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("middle_right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.1667F, KeyframeAnimations.posVec(0F, 0.2F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2917F, KeyframeAnimations.posVec(0F, 0.25F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4167F, KeyframeAnimations.posVec(0F, 0.3F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.posVec(0F, 0.2F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.875F, KeyframeAnimations.posVec(0F, -0.39F, 0.61F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.0833F, KeyframeAnimations.posVec(0F, -0.54F, 0.41F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.4167F, KeyframeAnimations.posVec(0F, -0.69F, 0.51F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.6667F, KeyframeAnimations.posVec(0F, -1.19F, 0.51F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.7917F, KeyframeAnimations.posVec(0F, -3.17F, -0.56F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.3333F, KeyframeAnimations.posVec(0F, -5.73F, -1.13F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.7917F, KeyframeAnimations.posVec(0F, -6.69F, -1.49F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("back_left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.0417F, KeyframeAnimations.degreeVec(0F, 0F, 40F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.125F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.degreeVec(0F, 0F, -30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.875F, KeyframeAnimations.degreeVec(-36.3571F, 12.2766F, -42.5685F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.0833F, KeyframeAnimations.degreeVec(-41.1261F, 24.9507F, -34.0787F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.4167F, KeyframeAnimations.degreeVec(-8.542F, 31.0692F, -0.4536F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.0833F, KeyframeAnimations.degreeVec(-1.3179F, 14.3638F, 28.3211F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.1667F, KeyframeAnimations.degreeVec(21.7357F, 20.5806F, 96.6153F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.25F, KeyframeAnimations.degreeVec(-34.7255F, 9.0055F, -35.5401F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("back_left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.posVec(0.1F, 0.5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.875F, KeyframeAnimations.posVec(0.1F, 0.2F, 0.7F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.0833F, KeyframeAnimations.posVec(0F, 0.1F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.4167F, KeyframeAnimations.posVec(0F, -0.1F, 0.3F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.0833F, KeyframeAnimations.posVec(-0.1F, -0.15F, -0.2F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.1667F, KeyframeAnimations.posVec(-0.1F, -0.15F, 0.3F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.25F, KeyframeAnimations.posVec(-0.1F, -1.15F, 0.3F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.7917F, KeyframeAnimations.posVec(0.4F, -6.15F, -1.2F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("middle_left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-8.6119F, 11.2146F, 7.53F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0F, -25F, 40F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0F, 0F, -30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(-3.5431F, 12.8083F, -28.8385F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-5.6928F, 16.8855F, -24.6653F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.875F, KeyframeAnimations.degreeVec(-20.98F, 15.8299F, 17.9424F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.0833F, KeyframeAnimations.degreeVec(-18.9532F, 16.4025F, 27.6927F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.6667F, KeyframeAnimations.degreeVec(-14.6244F, 20.8373F, 33.8672F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.7917F, KeyframeAnimations.degreeVec(15.9445F, 11.6137F, 63.4499F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.9167F, KeyframeAnimations.degreeVec(-36.397F, 10.0588F, -28.3986F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("middle_left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.1667F, KeyframeAnimations.posVec(0F, 0.2F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2917F, KeyframeAnimations.posVec(0F, 0.25F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4167F, KeyframeAnimations.posVec(0F, 0.3F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.posVec(0F, 0.2F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.875F, KeyframeAnimations.posVec(0F, -0.39F, 0.61F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.0833F, KeyframeAnimations.posVec(0F, -0.54F, 0.41F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.6667F, KeyframeAnimations.posVec(0F, -0.69F, 0.51F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.9167F, KeyframeAnimations.posVec(0F, -1.19F, 0.51F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.7917F, KeyframeAnimations.posVec(0F, -3.17F, -0.56F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.3333F, KeyframeAnimations.posVec(0F, -5.73F, -1.13F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.7917F, KeyframeAnimations.posVec(0F, -6.69F, -1.49F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("front_left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-20F, 0F, -42.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4167F, KeyframeAnimations.degreeVec(-25F, 0F, -42.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(-24.7676F, 2.1539F, -54.8159F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-23.6588F, -9.018F, -33.8412F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.0833F, KeyframeAnimations.degreeVec(-56.1588F, 9.018F, -33.8412F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("front_left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.1667F, KeyframeAnimations.posVec(0F, 0.4F, 0.25F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4167F, KeyframeAnimations.posVec(0F, 1.3F, 0.15F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.posVec(0F, 0.2F, 0.15F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.posVec(0.1F, -0.55F, -0.35F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5833F, KeyframeAnimations.posVec(0.09F, -0.78F, 0.22F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.875F, KeyframeAnimations.posVec(0.1F, -0.79F, 0.47F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.0833F, KeyframeAnimations.posVec(0.2F, -0.95F, 0.25F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.7083F, KeyframeAnimations.posVec(0F, -2.18F, 0.18F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.0417F, KeyframeAnimations.posVec(-0.1F, -2.54F, -0.07F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.5F, KeyframeAnimations.posVec(-0.1F, -3.17F, -0.6F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.9167F, KeyframeAnimations.posVec(-0.1F, -4.52F, -1.18F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.4167F, KeyframeAnimations.posVec(-0.1F, -6.82F, -1.84F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.7917F, KeyframeAnimations.posVec(-0.1F, -7.45F, -2.25F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(2.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.0833F, KeyframeAnimations.degreeVec(15F, 0F, 3F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2917F, KeyframeAnimations.degreeVec(45F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4583F, KeyframeAnimations.degreeVec(50F, 0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5833F, KeyframeAnimations.degreeVec(40F, 0F, -2F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(17F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-20.2836F, -2.5F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0417F, KeyframeAnimations.degreeVec(-21.3896F, 2.5F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.25F, KeyframeAnimations.degreeVec(-23.09F, -2.5F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-24.45F, 2.5F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.625F, KeyframeAnimations.degreeVec(-26.14F, -2.5F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-27.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.9583F, KeyframeAnimations.degreeVec(-31.12F, -2.5F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.125F, KeyframeAnimations.degreeVec(-34.32F, 2.5F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.375F, KeyframeAnimations.degreeVec(-37.17F, -2.5F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.5F, KeyframeAnimations.degreeVec(-38.6F, 2.5F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.7083F, KeyframeAnimations.degreeVec(-40.97F, -2.5F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.875F, KeyframeAnimations.degreeVec(-42.87F, 2.5F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.0833F, KeyframeAnimations.degreeVec(-45.25F, -2.5F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.25F, KeyframeAnimations.degreeVec(-47.15F, 2.5F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.5F, KeyframeAnimations.degreeVec(-50F, -2.5F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.625F, KeyframeAnimations.degreeVec(-50.83F, 2.5F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.75F, KeyframeAnimations.degreeVec(-51.67F, 1.25F, -1.25F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.8333F, KeyframeAnimations.degreeVec(-52.39F, -2.5F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4F, KeyframeAnimations.degreeVec(-53.84F, 2.5F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.2083F, KeyframeAnimations.degreeVec(-56.01F, -2.5F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.375F, KeyframeAnimations.degreeVec(-57.1F, 2.5F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.5833F, KeyframeAnimations.degreeVec(-58.91F, -2.5F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.7083F, KeyframeAnimations.degreeVec(-60F, 2.5F, -2.5F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.posVec(0F, 0F, 1F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.0833F, KeyframeAnimations.posVec(0F, -0.34F, 1F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.5F, KeyframeAnimations.posVec(0F, -2.25F, 1F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.75F, KeyframeAnimations.posVec(0F, -3F, 1F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.7083F, KeyframeAnimations.posVec(0F, -7F, 1F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.SCALE,
			new Keyframe(0F, KeyframeAnimations.scaleVec(1F, 1F, 1F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("main_claw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4167F, KeyframeAnimations.degreeVec(15F, 45F, 30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7083F, KeyframeAnimations.degreeVec(25F, 45F, -45F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-40F, 30F, -30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0417F, KeyframeAnimations.degreeVec(-115F, -10F, -20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.1667F, KeyframeAnimations.degreeVec(30F, 40F, -55F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.2917F, KeyframeAnimations.degreeVec(-40F, 30F, -30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-115F, -10F, -20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5417F, KeyframeAnimations.degreeVec(30F, 40F, -55F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-40F, 30F, -30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-115F, -10F, -20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.9167F, KeyframeAnimations.degreeVec(30F, 40F, -55F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.0417F, KeyframeAnimations.degreeVec(-40F, 30F, -30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.1667F, KeyframeAnimations.degreeVec(-115F, -10F, -20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.2917F, KeyframeAnimations.degreeVec(30F, 40F, -55F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.4167F, KeyframeAnimations.degreeVec(-40F, 30F, -30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.5417F, KeyframeAnimations.degreeVec(-115F, -10F, -20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.6667F, KeyframeAnimations.degreeVec(30F, 40F, -55F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.7917F, KeyframeAnimations.degreeVec(-40F, 30F, -30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.9167F, KeyframeAnimations.degreeVec(-115F, -10F, -20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.0417F, KeyframeAnimations.degreeVec(30F, 40F, -55F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.1667F, KeyframeAnimations.degreeVec(-40F, 30F, -30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.2917F, KeyframeAnimations.degreeVec(-115F, -10F, -20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.4167F, KeyframeAnimations.degreeVec(30F, 40F, -55F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.5417F, KeyframeAnimations.degreeVec(-40F, 30F, -30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.6667F, KeyframeAnimations.degreeVec(-115F, -10F, -20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.7917F, KeyframeAnimations.degreeVec(30F, 40F, -55F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.9167F, KeyframeAnimations.degreeVec(-40F, 30F, -30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.0417F, KeyframeAnimations.degreeVec(-115F, -10F, -20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.1667F, KeyframeAnimations.degreeVec(30F, 40F, -55F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.2917F, KeyframeAnimations.degreeVec(-40F, 30F, -30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.4167F, KeyframeAnimations.degreeVec(-115F, -10F, -20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.5417F, KeyframeAnimations.degreeVec(30F, 40F, -55F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.6667F, KeyframeAnimations.degreeVec(-40F, 30F, -30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.7917F, KeyframeAnimations.degreeVec(-115F, -10F, -20F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("main_claw", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4167F, KeyframeAnimations.posVec(-0.05F, 0.5F, -0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7083F, KeyframeAnimations.posVec(-0.2F, 0.5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.posVec(-0.2F, -0.5F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0417F, KeyframeAnimations.posVec(-0.2F, -0.5F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_claw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4167F, KeyframeAnimations.degreeVec(15F, -45F, -30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7083F, KeyframeAnimations.degreeVec(25F, -45F, 45F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0417F, KeyframeAnimations.degreeVec(-30F, -40F, 55F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-115F, 10F, 20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.2917F, KeyframeAnimations.degreeVec(40F, -30F, 30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-30F, -40F, 55F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5417F, KeyframeAnimations.degreeVec(-115F, 10F, 20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.6667F, KeyframeAnimations.degreeVec(40F, -30F, 30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.degreeVec(-30F, -40F, 55F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.9167F, KeyframeAnimations.degreeVec(-115F, 10F, 20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.0417F, KeyframeAnimations.degreeVec(40F, -30F, 30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.1667F, KeyframeAnimations.degreeVec(-30F, -40F, 55F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.2917F, KeyframeAnimations.degreeVec(-115F, 10F, 20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.4167F, KeyframeAnimations.degreeVec(40F, -30F, 30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.5417F, KeyframeAnimations.degreeVec(-30F, -40F, 55F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.6667F, KeyframeAnimations.degreeVec(-115F, 10F, 20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.7917F, KeyframeAnimations.degreeVec(40F, -30F, 30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.9167F, KeyframeAnimations.degreeVec(-30F, -40F, 55F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.0417F, KeyframeAnimations.degreeVec(-115F, 10F, 20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.1667F, KeyframeAnimations.degreeVec(40F, -30F, 30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.2917F, KeyframeAnimations.degreeVec(-30F, -40F, 55F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.4167F, KeyframeAnimations.degreeVec(-115F, 10F, 20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.5417F, KeyframeAnimations.degreeVec(40F, -30F, 30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.6667F, KeyframeAnimations.degreeVec(-30F, -40F, 55F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.7917F, KeyframeAnimations.degreeVec(-115F, 10F, 20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.9167F, KeyframeAnimations.degreeVec(40F, -30F, 30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.0417F, KeyframeAnimations.degreeVec(-30F, -40F, 55F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.1667F, KeyframeAnimations.degreeVec(-115F, 10F, 20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.2917F, KeyframeAnimations.degreeVec(40F, -30F, 30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.4167F, KeyframeAnimations.degreeVec(-30F, -40F, 55F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.5417F, KeyframeAnimations.degreeVec(-115F, 10F, 20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.6667F, KeyframeAnimations.degreeVec(40F, -30F, 30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.7917F, KeyframeAnimations.degreeVec(-30F, -40F, 55F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_claw", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4167F, KeyframeAnimations.posVec(-0.05F, 0.5F, -0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7083F, KeyframeAnimations.posVec(0.3F, 0.5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0417F, KeyframeAnimations.posVec(0.25F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.2917F, KeyframeAnimations.posVec(0.3F, -0.5F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4167F, KeyframeAnimations.posVec(0.25F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.6667F, KeyframeAnimations.posVec(0.3F, -0.5F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7917F, KeyframeAnimations.posVec(0.25F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.0417F, KeyframeAnimations.posVec(0.3F, -0.5F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.1667F, KeyframeAnimations.posVec(0.25F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.4167F, KeyframeAnimations.posVec(0.3F, -0.5F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.5417F, KeyframeAnimations.posVec(0.25F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.7917F, KeyframeAnimations.posVec(0.3F, -0.5F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.9167F, KeyframeAnimations.posVec(0.25F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.1667F, KeyframeAnimations.posVec(0.3F, -0.5F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.2917F, KeyframeAnimations.posVec(0.25F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.5417F, KeyframeAnimations.posVec(0.3F, -0.5F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.6667F, KeyframeAnimations.posVec(0.25F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.9167F, KeyframeAnimations.posVec(0.3F, -0.5F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.0417F, KeyframeAnimations.posVec(0.25F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.2917F, KeyframeAnimations.posVec(0.3F, -0.5F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.4167F, KeyframeAnimations.posVec(0.25F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.6667F, KeyframeAnimations.posVec(0.3F, -0.5F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.7917F, KeyframeAnimations.posVec(0.25F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.build();

	public static final AnimationDefinition BABY_CRAB_EMERGE = AnimationDefinition.Builder.withLength(1.5F)
		.addAnimation("claw_top", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("claw_top", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("back_right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7917F, KeyframeAnimations.degreeVec(2.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9583F, KeyframeAnimations.degreeVec(13.5929F, 13.1814F, -10.1613F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0417F, KeyframeAnimations.degreeVec(-5.9865F, 8.8755F, -18.2137F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.125F, KeyframeAnimations.degreeVec(-9.38F, 34.4857F, 10.8463F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.2917F, KeyframeAnimations.degreeVec(-6.0946F, 4.6746F, -6.4199F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.3333F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("back_right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, -9F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6667F, KeyframeAnimations.posVec(0F, -5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7917F, KeyframeAnimations.posVec(0F, -2.5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9583F, KeyframeAnimations.posVec(0F, 0.5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.125F, KeyframeAnimations.posVec(0F, 0.15F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.2083F, KeyframeAnimations.posVec(0F, 0.15F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.3333F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("middle_left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.625F, KeyframeAnimations.degreeVec(44.378F, -32.029F, 14.6355F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(-1.9702F, -37.331F, 12.8403F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.875F, KeyframeAnimations.degreeVec(-6.1601F, -29.9666F, -2.05F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-0.3267F, -17.1562F, 7.8938F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0833F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("middle_left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, -9F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4167F, KeyframeAnimations.posVec(0F, -6F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6667F, KeyframeAnimations.posVec(0F, -2F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7917F, KeyframeAnimations.posVec(0F, -1F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("back_right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7917F, KeyframeAnimations.degreeVec(2.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9583F, KeyframeAnimations.degreeVec(13.5929F, 13.1814F, -10.1613F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0417F, KeyframeAnimations.degreeVec(-5.9865F, 8.8755F, -18.2137F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.125F, KeyframeAnimations.degreeVec(-9.38F, 34.4857F, 10.8463F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.2917F, KeyframeAnimations.degreeVec(-6.0946F, 4.6746F, -6.4199F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.3333F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("back_right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, -9F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6667F, KeyframeAnimations.posVec(0F, -5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7917F, KeyframeAnimations.posVec(0F, -2.5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9583F, KeyframeAnimations.posVec(0F, 0.5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.125F, KeyframeAnimations.posVec(0F, 0.15F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.2083F, KeyframeAnimations.posVec(0F, 0.15F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.3333F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("front_right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6667F, KeyframeAnimations.degreeVec(19.378F, 32.029F, -14.6355F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-1.9702F, 37.331F, -12.8403F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-6.1601F, 29.9666F, 2.05F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0417F, KeyframeAnimations.degreeVec(-0.3267F, 17.1562F, -7.8938F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.125F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("front_right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, -9F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4167F, KeyframeAnimations.posVec(0F, -6F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6667F, KeyframeAnimations.posVec(0F, -2F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7917F, KeyframeAnimations.posVec(0F, -1F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("middle_right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7083F, KeyframeAnimations.degreeVec(0F, 0F, 25F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.875F, KeyframeAnimations.degreeVec(-16.7742F, -18.6578F, -20.0506F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-20F, 17.5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0833F, KeyframeAnimations.degreeVec(-20F, 17.5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.1667F, KeyframeAnimations.degreeVec(-11.5097F, 11.6242F, -22.0913F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.25F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("middle_right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, -9F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4583F, KeyframeAnimations.posVec(0F, -4.49F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7083F, KeyframeAnimations.posVec(0F, -2F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.875F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("back_left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(2.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.degreeVec(13.5929F, -13.1814F, 10.1613F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-5.9865F, -8.8755F, 18.2137F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0833F, KeyframeAnimations.degreeVec(-9.38F, -34.4857F, -10.8463F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.25F, KeyframeAnimations.degreeVec(-6.0946F, -4.6746F, 6.4199F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.2917F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("back_left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, -9F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6667F, KeyframeAnimations.posVec(0F, -5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7917F, KeyframeAnimations.posVec(0F, -2.5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9583F, KeyframeAnimations.posVec(0F, 0.5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.125F, KeyframeAnimations.posVec(0F, 0.15F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.2083F, KeyframeAnimations.posVec(0F, 0.15F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.3333F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("middle_left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0F, 0F, -25F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.8333F, KeyframeAnimations.degreeVec(-16.7742F, 18.6578F, 20.0506F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9583F, KeyframeAnimations.degreeVec(-20F, -17.5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0417F, KeyframeAnimations.degreeVec(-20F, -17.5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.125F, KeyframeAnimations.degreeVec(-11.5097F, -11.6242F, 22.0913F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.2083F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("middle_left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, -9F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4583F, KeyframeAnimations.posVec(0F, -4.49F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7083F, KeyframeAnimations.posVec(0F, -2F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.875F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("front_left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.625F, KeyframeAnimations.degreeVec(44.378F, -32.029F, 14.6355F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(-1.9702F, -37.331F, 12.8403F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.875F, KeyframeAnimations.degreeVec(-6.1601F, -29.9666F, -2.05F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-0.3267F, -17.1562F, 7.8938F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0833F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("front_left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, -9F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4167F, KeyframeAnimations.posVec(0F, -6F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6667F, KeyframeAnimations.posVec(0F, -2F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7917F, KeyframeAnimations.posVec(0F, -1F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.9167F, KeyframeAnimations.degreeVec(0F, 0F, -1F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1F, KeyframeAnimations.degreeVec(0F, 0F, 1F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0833F, KeyframeAnimations.degreeVec(0F, 0F, -1F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.1667F, KeyframeAnimations.degreeVec(0F, 0F, 1F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.25F, KeyframeAnimations.degreeVec(0F, 0F, -1F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.3333F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.8333F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("torso", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2083F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4167F, KeyframeAnimations.degreeVec(34.3569F, 7.1314F, -10.2928F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5417F, KeyframeAnimations.degreeVec(55F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6667F, KeyframeAnimations.degreeVec(35F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7917F, KeyframeAnimations.degreeVec(12.4539F, -1.0809F, 4.8821F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-1.25F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-0.25F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0417F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("torso", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, -9F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2083F, KeyframeAnimations.posVec(0F, -9F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4167F, KeyframeAnimations.posVec(0F, -7F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5417F, KeyframeAnimations.posVec(0F, -5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6667F, KeyframeAnimations.posVec(0F, -3F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7917F, KeyframeAnimations.posVec(0F, -1F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("main_claw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(71.8866F, 94.0614F, 14.1944F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(109.3866F, 94.0614F, 14.1944F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.375F, KeyframeAnimations.degreeVec(40.9771F, 42.5638F, -69.9781F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.7083F, KeyframeAnimations.degreeVec(40.98F, 42.56F, -69.98F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-2.3841F, -13.188F, -23.0557F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.125F, KeyframeAnimations.degreeVec(25.0407F, 104.2666F, -27.1962F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.25F, KeyframeAnimations.degreeVec(57.6758F, 53.1647F, -21.7737F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.375F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("main_claw", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, -9F, 0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.25F, KeyframeAnimations.posVec(0F, -1.5F, 0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.375F, KeyframeAnimations.posVec(0F, -1.4F, 0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.7083F, KeyframeAnimations.posVec(0F, -1.4F, 0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.8333F, KeyframeAnimations.posVec(-0.18F, -0.38F, 0.3F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.9167F, KeyframeAnimations.posVec(-0.3F, -0.2F, 0.5F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.125F, KeyframeAnimations.posVec(-0.3F, -0.4F, 0.5F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.375F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_claw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(71.8866F, -94.0614F, -14.1944F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(109.3866F, -94.0614F, -14.1944F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.375F, KeyframeAnimations.degreeVec(40.9771F, -42.5638F, 69.9781F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.7083F, KeyframeAnimations.degreeVec(40.98F, -42.56F, 69.98F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.9167F, KeyframeAnimations.degreeVec(-2.3841F, 13.188F, 23.0557F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.125F, KeyframeAnimations.degreeVec(25.0407F, -104.2666F, 27.1962F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.25F, KeyframeAnimations.degreeVec(57.6758F, -53.1647F, 21.7737F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.375F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_claw", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, -9F, 0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.25F, KeyframeAnimations.posVec(0F, -1.5F, 0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.375F, KeyframeAnimations.posVec(0F, -1.4F, 0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.7083F, KeyframeAnimations.posVec(0F, -1.4F, 0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.8333F, KeyframeAnimations.posVec(0.32F, -0.38F, 0.3F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.9167F, KeyframeAnimations.posVec(0.2F, -0.2F, 0.5F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.125F, KeyframeAnimations.posVec(0.2F, -0.4F, 0.5F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.375F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.build();

	public static final AnimationDefinition BABY_CRAB_EMERGE_WAIT = AnimationDefinition.Builder.withLength(0F)
		.addAnimation("claw_top", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("claw_top", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("back_right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("back_right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, -9F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("middle_left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("middle_left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, -9F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("back_right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("back_right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, -9F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("front_right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("front_right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, -9F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("middle_right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("middle_right_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, -9F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("back_left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("back_left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, -9F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("middle_left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("middle_left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, -9F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("front_left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("front_left_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, -9F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("torso", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("torso", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, -9F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("main_claw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(71.8866F, 94.0614F, 14.1944F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("main_claw", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, -9F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_claw", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(71.8866F, -94.0614F, -14.1944F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_claw", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, -9F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.build();
}
