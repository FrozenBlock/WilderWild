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
 * You should have received a copy of the FrozenBlock Modding oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.client.animation.definitions;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

// Animations by DaDolphin! Edited by AViewFromTheTop.
@Environment(EnvType.CLIENT)
public class PenguinAnimation {

	public static final AnimationDefinition PENGUIN_LAY_DOWN = AnimationDefinition.Builder.withLength(0.6635F)
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3981F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4777F, KeyframeAnimations.posVec(0F, 0.5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5573F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("torso", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2123F, KeyframeAnimations.degreeVec(42.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3981F, KeyframeAnimations.degreeVec(90F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5308F, KeyframeAnimations.degreeVec(87.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6635F, KeyframeAnimations.degreeVec(90F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("torso", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2919F, KeyframeAnimations.posVec(0F, -1.5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.345F, KeyframeAnimations.posVec(0F, -2.5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3981F, KeyframeAnimations.posVec(0F, -3.5F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.1327F, KeyframeAnimations.degreeVec(-8.75F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2123F, KeyframeAnimations.degreeVec(-20.23F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3185F, KeyframeAnimations.degreeVec(-50F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4246F, KeyframeAnimations.degreeVec(-67.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5308F, KeyframeAnimations.degreeVec(-77.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6635F, KeyframeAnimations.degreeVec(-90F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.2123F, KeyframeAnimations.posVec(0F, -0.04F, -0.51F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5308F, KeyframeAnimations.posVec(0F, 2.61F, -1.6F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.6635F, KeyframeAnimations.posVec(0F, 3F, -1.6F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("feet", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2123F, KeyframeAnimations.degreeVec(34.6271F, -4.6414F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.345F, KeyframeAnimations.degreeVec(92.1786F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("feet", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.0796F, KeyframeAnimations.posVec(0F, 0F, 2F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2123F, KeyframeAnimations.posVec(0F, 2.25F, 5.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.345F, KeyframeAnimations.posVec(0F, 4.25F, 5.75F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_foot", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2919F, KeyframeAnimations.degreeVec(-1.4884F, -1.6172F, -3.3083F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3981F, KeyframeAnimations.degreeVec(2.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5042F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_foot", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0.1F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_foot", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.345F, KeyframeAnimations.degreeVec(-1.4884F, -1.6172F, -3.3083F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4512F, KeyframeAnimations.degreeVec(2.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5573F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_foot", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0.1F, 0F), AnimationChannel.Interpolations.LINEAR)
		)).build();

	public static final AnimationDefinition PENGUIN_STAND_UP = AnimationDefinition.Builder.withLength(2.381F)
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2834F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4819F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0771F, KeyframeAnimations.degreeVec(-80.75F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4456F, KeyframeAnimations.degreeVec(-92.75F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.6156F, KeyframeAnimations.degreeVec(-90F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2834F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4819F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.822F, KeyframeAnimations.posVec(0F, 3F, 0.91F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.1338F, KeyframeAnimations.posVec(0F, -0.25F, 4F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.2472F, KeyframeAnimations.posVec(0F, -0.6F, 4F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4172F, KeyframeAnimations.posVec(0F, -0.75F, 3.5F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("torso", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(90F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5669F, KeyframeAnimations.degreeVec(96.27F, -2.34F, -0.05F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6803F, KeyframeAnimations.degreeVec(98.6608F, -2.7189F, 2.4235F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7653F, KeyframeAnimations.degreeVec(86.4331F, -2.8208F, -5.0474F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.3039F, KeyframeAnimations.degreeVec(87.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.729F, KeyframeAnimations.degreeVec(95F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.0408F, KeyframeAnimations.degreeVec(90F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("torso", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, -3.5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4172F, KeyframeAnimations.posVec(0F, -3.5F, -0.75F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(-90F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.2551F, KeyframeAnimations.degreeVec(-79F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6803F, KeyframeAnimations.degreeVec(10F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0204F, KeyframeAnimations.degreeVec(1.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.3605F, KeyframeAnimations.degreeVec(-6F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7007F, KeyframeAnimations.degreeVec(4F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.381F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 3F, -1.6F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.2268F, KeyframeAnimations.posVec(0F, 3F, -1.6F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.6803F, KeyframeAnimations.posVec(0F, 0F, -0.1F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0204F, KeyframeAnimations.posVec(0F, -0.25F, -0.1F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.2755F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.381F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_flipper", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.1701F, KeyframeAnimations.degreeVec(3.739F, 24.9701F, 84.281F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2834F, KeyframeAnimations.degreeVec(-22.4223F, 41.5496F, 91.4274F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4535F, KeyframeAnimations.degreeVec(-99.7843F, 68.0904F, 6.5591F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5669F, KeyframeAnimations.degreeVec(-88.2828F, 35.7061F, 16.0036F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7086F, KeyframeAnimations.degreeVec(-70.7439F, 20.575F, 16.3278F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.1338F, KeyframeAnimations.degreeVec(-2.48F, 3.23F, 16.94F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4739F, KeyframeAnimations.degreeVec(-11.3298F, 1.984F, 27.3032F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.8991F, KeyframeAnimations.degreeVec(6.1702F, 1.984F, 27.3032F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.381F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_flipper", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4535F, KeyframeAnimations.posVec(0.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5669F, KeyframeAnimations.posVec(0.27F, 0F, 2F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7086F, KeyframeAnimations.posVec(0.09F, 0F, -1.18F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9921F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_flipper", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.1984F, KeyframeAnimations.degreeVec(2.4611F, -24.1629F, -97.0713F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2834F, KeyframeAnimations.degreeVec(-26.2128F, -44.185F, -84.4962F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4535F, KeyframeAnimations.degreeVec(-93.8992F, -68.9155F, -10.2238F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5669F, KeyframeAnimations.degreeVec(-77.3469F, -36.4117F, -15.5803F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.737F, KeyframeAnimations.degreeVec(-53.3795F, -25.1919F, -13.471F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9354F, KeyframeAnimations.degreeVec(-22.9822F, -8.8161F, -14.784F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.1621F, KeyframeAnimations.degreeVec(2.7392F, -0.9123F, -30.8984F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4172F, KeyframeAnimations.degreeVec(7.608F, 2.4569F, -36.6673F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.8141F, KeyframeAnimations.degreeVec(-15.7605F, -1.189F, -27.597F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.381F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_flipper", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4535F, KeyframeAnimations.posVec(-0.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5669F, KeyframeAnimations.posVec(-0.48F, 0F, 2F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.737F, KeyframeAnimations.posVec(-0.41F, 0F, -1.18F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4172F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.381F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("feet", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(92.1786F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7007F, KeyframeAnimations.degreeVec(90F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("feet", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 4.25F, 5.75F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7007F, KeyframeAnimations.posVec(0F, 4F, 5.75F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_foot", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.1984F, KeyframeAnimations.degreeVec(-0.32F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3968F, KeyframeAnimations.degreeVec(-13.13F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5952F, KeyframeAnimations.degreeVec(22.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7937F, KeyframeAnimations.degreeVec(-3.12F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.907F, KeyframeAnimations.degreeVec(-5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0204F, KeyframeAnimations.degreeVec(-14.7F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0488F, KeyframeAnimations.degreeVec(-12.75F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0771F, KeyframeAnimations.degreeVec(-11F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.1054F, KeyframeAnimations.degreeVec(-9.2F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.2472F, KeyframeAnimations.degreeVec(1F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.3605F, KeyframeAnimations.degreeVec(1.7F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5023F, KeyframeAnimations.degreeVec(3F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.6156F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_foot", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.1984F, KeyframeAnimations.posVec(0F, 0F, -0.06F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3968F, KeyframeAnimations.posVec(0F, -1F, -0.86F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5952F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7937F, KeyframeAnimations.posVec(0F, 0F, 1.25F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.907F, KeyframeAnimations.posVec(0F, 0F, -0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0204F, KeyframeAnimations.posVec(0F, -0.95F, -0.55F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0488F, KeyframeAnimations.posVec(0F, -0.8F, -0.49F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0771F, KeyframeAnimations.posVec(0F, -0.62F, -0.41F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.1054F, KeyframeAnimations.posVec(0F, -0.5F, -0.28F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.2472F, KeyframeAnimations.posVec(0F, 0.05F, 0.25F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.3605F, KeyframeAnimations.posVec(0F, -0.12F, 0.33F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4172F, KeyframeAnimations.posVec(0F, -0.05F, 0.3F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4456F, KeyframeAnimations.posVec(0F, -0.05F, 0.25F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.559F, KeyframeAnimations.posVec(0F, 0F, 0.2F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.644F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.6723F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_foot", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5102F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6803F, KeyframeAnimations.degreeVec(25.54F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7937F, KeyframeAnimations.degreeVec(27.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0204F, KeyframeAnimations.degreeVec(-2.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.3322F, KeyframeAnimations.degreeVec(-15.5041F, 14.4775F, -3.9671F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7007F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_foot", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5102F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.7937F, KeyframeAnimations.posVec(0F, 0F, 1.75F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0204F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.3322F, KeyframeAnimations.posVec(0F, 0.25F, -3F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.7007F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		)).build();

	public static final AnimationDefinition PENGUIN_CALL = AnimationDefinition.Builder.withLength(2.5F)
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0.25F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("torso", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.125F, KeyframeAnimations.degreeVec(5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-8F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.0417F, KeyframeAnimations.degreeVec(-12.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.625F, KeyframeAnimations.degreeVec(2.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.25F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.0833F, KeyframeAnimations.degreeVec(2.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.25F, KeyframeAnimations.degreeVec(-7.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-2.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.75F, KeyframeAnimations.degreeVec(-7.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.0833F, KeyframeAnimations.degreeVec(-10F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.4583F, KeyframeAnimations.degreeVec(-15F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.9583F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_flipper", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.2083F, KeyframeAnimations.degreeVec(0F, 0F, 5F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0F, 0F, 7.5F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.2917F, KeyframeAnimations.degreeVec(0F, 0F, 25F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.4583F, KeyframeAnimations.degreeVec(0F, 0F, 7.5F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.625F, KeyframeAnimations.degreeVec(0F, 0F, 32.5F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.7917F, KeyframeAnimations.degreeVec(0F, 0F, 10F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.4583F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_flipper", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.2083F, KeyframeAnimations.degreeVec(0F, 0F, -7.5F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.375F, KeyframeAnimations.degreeVec(-0.1026F, -1.172F, -4.8519F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.2917F, KeyframeAnimations.degreeVec(0.6543F, -4.9571F, -25.0283F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.4583F, KeyframeAnimations.degreeVec(0F, 0F, -5F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.625F, KeyframeAnimations.degreeVec(0.6543F, -4.9571F, -25.0283F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.7917F, KeyframeAnimations.degreeVec(0F, 0F, -5F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.build();
}
