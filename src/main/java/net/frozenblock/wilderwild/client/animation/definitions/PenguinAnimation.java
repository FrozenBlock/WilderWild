/*
 * Copyright 2024-2025 FrozenBlock
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

package net.frozenblock.wilderwild.client.animation.definitions;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

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
		new Keyframe(0.822F, KeyframeAnimations.posVec(0F, 2F, 0.91F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.1338F, KeyframeAnimations.posVec(0F, -0.25F, 5F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.2472F, KeyframeAnimations.posVec(0F, -0.6F, 5F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.4172F, KeyframeAnimations.posVec(0F, -0.75F, 5F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("torso", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(90F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.6803F, KeyframeAnimations.degreeVec(91.2011F, -2.4995F, -0.0524F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.3039F, KeyframeAnimations.degreeVec(87.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.729F, KeyframeAnimations.degreeVec(95F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.0408F, KeyframeAnimations.degreeVec(90F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("torso", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, -3.5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.4172F, KeyframeAnimations.posVec(0F, -3.5F, -0.5F), AnimationChannel.Interpolations.CATMULLROM)
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
		new Keyframe(2.381F, KeyframeAnimations.posVec(0F, -0.25F, -0.1F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_flipper", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 17.5F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.1701F, KeyframeAnimations.degreeVec(3.739F, 24.9701F, 84.281F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.2834F, KeyframeAnimations.degreeVec(-22.4223F, 41.5496F, 91.4274F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.4535F, KeyframeAnimations.degreeVec(-99.7843F, 68.0904F, 6.5591F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.5669F, KeyframeAnimations.degreeVec(-60.982F, 50.469F, 32.4921F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.7086F, KeyframeAnimations.degreeVec(-70.7439F, 20.575F, 16.3278F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.1338F, KeyframeAnimations.degreeVec(-2.48F, 3.23F, 16.94F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.4739F, KeyframeAnimations.degreeVec(-11.3298F, 1.984F, 27.3032F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.8991F, KeyframeAnimations.degreeVec(6.1702F, 1.984F, 27.3032F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.381F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_flipper", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, -17.5F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.1984F, KeyframeAnimations.degreeVec(2.4611F, -24.1629F, -97.0713F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.2834F, KeyframeAnimations.degreeVec(-26.2128F, -44.185F, -84.4962F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.4535F, KeyframeAnimations.degreeVec(-93.8992F, -68.9155F, -10.2238F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.5669F, KeyframeAnimations.degreeVec(-73.751F, -50.9494F, -20.7433F), AnimationChannel.Interpolations.CATMULLROM),
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
		new Keyframe(1.4172F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(2.381F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("feet", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(92.1786F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.7007F, KeyframeAnimations.degreeVec(90F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("feet", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 4.25F, 5.75F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.7007F, KeyframeAnimations.posVec(0F, 4.25F, 5.75F), AnimationChannel.Interpolations.CATMULLROM)
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
		new Keyframe(1.3322F, KeyframeAnimations.degreeVec(-7.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.7007F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_foot", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.5102F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.7937F, KeyframeAnimations.posVec(0F, 0F, 1.75F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.0204F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.3322F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(1.7007F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		)).build();
}
