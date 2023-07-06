/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.entity.render.animations;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.AnimationDefinition.Builder;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Put custom Warden animations in here. for example, death, flying, swimming. Not for overrides.
 */
@Environment(EnvType.CLIENT)
public final class CustomWardenAnimations {

	public static final AnimationDefinition DYING = Builder.withLength(3.5F)
		.addAnimation(
			"bone",
			new AnimationChannel(
				AnimationChannel.Targets.ROTATION,
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.56F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.44F, KeyframeAnimations.degreeVec(-37.7838F, -14.24F, -17.6045F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"bone",
			new AnimationChannel(
				AnimationChannel.Targets.POSITION,
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.36F, KeyframeAnimations.posVec(0.0F, -100F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.92F, KeyframeAnimations.posVec(0.0F, -190F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(3.48F, KeyframeAnimations.posVec(0.0F, -500F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"body",
			new AnimationChannel(
				AnimationChannel.Targets.ROTATION,
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.56F, KeyframeAnimations.degreeVec(-12.59F, 0F, 6.67F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.2F, KeyframeAnimations.degreeVec(-42.5F, 0.0F, 22.5F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"body",
			new AnimationChannel(
				AnimationChannel.Targets.POSITION,
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.36F, KeyframeAnimations.posVec(5.7F, 0F, 4F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.44F, KeyframeAnimations.posVec(12.0F, 0F, 11F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.44F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"head",
			new AnimationChannel(
				AnimationChannel.Targets.ROTATION,
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.52F, KeyframeAnimations.degreeVec(9.5459F, 2.9932F, -17.25F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.32F, KeyframeAnimations.degreeVec(-17.5093F, -0.434F, 2.4621F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.88F, KeyframeAnimations.degreeVec(37.3656F, -0.1965F, -5.1569F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.28F, KeyframeAnimations.degreeVec(37.3656F, -0.1965F, -5.1569F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"head",
			new AnimationChannel(
				AnimationChannel.Targets.POSITION,
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"right_arm",
			new AnimationChannel(
				AnimationChannel.Targets.ROTATION,
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.48F, KeyframeAnimations.degreeVec(-52.5F, -0F, 50F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.96F, KeyframeAnimations.degreeVec(-59.5544F, 33.4855F, 67.9672F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.68F, KeyframeAnimations.degreeVec(-73.7812F, -48.0134F, 72.9396F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.12F, KeyframeAnimations.degreeVec(-72.5422F, 17.6713F, -9.7739F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"right_arm",
			new AnimationChannel(
				AnimationChannel.Targets.POSITION,
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.48F, KeyframeAnimations.posVec(1F, 0.0F, 2F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.12F, KeyframeAnimations.posVec(2F, -1F, 1F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.68F, KeyframeAnimations.posVec(2.66F, -0.22F, 0.34F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.12F, KeyframeAnimations.posVec(2F, -1F, 3F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"left_arm",
			new AnimationChannel(
				AnimationChannel.Targets.ROTATION,
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.2F, KeyframeAnimations.degreeVec(0, 0, -12.5F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.56F, KeyframeAnimations.degreeVec(-17.1773F, -40.7763F, -47.1799F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.0F, KeyframeAnimations.degreeVec(-24.0177F, 6.4316F, -116.2232F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.68F, KeyframeAnimations.degreeVec(-79.9936F, -13.4086F, -27.2842F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.08F, KeyframeAnimations.degreeVec(-80.2279F, -3.0828F, -21.3852F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"left_arm",
			new AnimationChannel(
				AnimationChannel.Targets.POSITION,
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.88F, KeyframeAnimations.posVec(-3.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"right_leg",
			new AnimationChannel(
				AnimationChannel.Targets.ROTATION,
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.16F, KeyframeAnimations.degreeVec(-20.2134F, 4.3897F, 7.6436F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.36F, KeyframeAnimations.degreeVec(-48.42F, 5.21F, -0.33F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.88F, KeyframeAnimations.degreeVec(-133.8547F, 12.8091F, -12.025F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"right_leg",
			new AnimationChannel(
				AnimationChannel.Targets.POSITION,
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.36F, KeyframeAnimations.posVec(4.78F, 6F, 0.42F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.84F, KeyframeAnimations.posVec(8.0F, 6F, 2F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.44F, KeyframeAnimations.posVec(12F, 8F, 5F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.16F, KeyframeAnimations.degreeVec(1F, 6F, -1F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"left_leg",
			new AnimationChannel(
				AnimationChannel.Targets.ROTATION,
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.36F, KeyframeAnimations.degreeVec(-12.4952F, -9.8466F, -5.9812F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.88F, KeyframeAnimations.degreeVec(-77.4825F, -0.8548F, 20.1506F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.52F, KeyframeAnimations.degreeVec(-107.48F, -0.85F, 20.15F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"left_leg",
			new AnimationChannel(
				AnimationChannel.Targets.POSITION,
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.08F, KeyframeAnimations.posVec(10F, 0F, 6F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.44F, KeyframeAnimations.posVec(0F, 3F, -6F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.build();

	public static final AnimationDefinition WATER_DYING = Builder.withLength(3.5F)
		.addAnimation(
			"bone",
			new AnimationChannel(
				AnimationChannel.Targets.ROTATION,
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.36F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(3.4F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"bone",
			new AnimationChannel(
				AnimationChannel.Targets.POSITION,
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.16F, KeyframeAnimations.posVec(0.0F, 0.0F, 6.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.08F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.72F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"body",
			new AnimationChannel(
				AnimationChannel.Targets.ROTATION,
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.12F, KeyframeAnimations.degreeVec(25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.44F, KeyframeAnimations.degreeVec(-15F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.48F, KeyframeAnimations.degreeVec(-27.4615F, -4.7235F, 10.7F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"body",
			new AnimationChannel(
				AnimationChannel.Targets.POSITION,
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.44F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.48F, KeyframeAnimations.posVec(1F, -1F, 1F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"head",
			new AnimationChannel(
				AnimationChannel.Targets.ROTATION,
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.04F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.24F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.68F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.6F, KeyframeAnimations.degreeVec(-30.0214F, 0.3262F, -7.4929F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.84F, KeyframeAnimations.degreeVec(-37.5379F, -0.434F, 9.9907F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(3.28F, KeyframeAnimations.degreeVec(-37.5379F, -0.434F, 9.9907F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"right_arm",
			new AnimationChannel(
				AnimationChannel.Targets.ROTATION,
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.28F, KeyframeAnimations.degreeVec(-90.6636F, 9.917F, -1.8476F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.84F, KeyframeAnimations.degreeVec(-4.1947F, 21.3089F, 6.3256F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.44F, KeyframeAnimations.degreeVec(42.5707F, -3.6382F, 15.171F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.28F, KeyframeAnimations.degreeVec(49.4331F, -3.0042F, 15.7028F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(3.28F, KeyframeAnimations.degreeVec(79.4684F, 3.3016F, 9.7983F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"right_arm",
			new AnimationChannel(
				AnimationChannel.Targets.POSITION,
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.28F, KeyframeAnimations.posVec(1F, 0F, -4F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(3.28F, KeyframeAnimations.posVec(0F, -3F, -4F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"left_arm",
			new AnimationChannel(
				AnimationChannel.Targets.ROTATION,
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.24F, KeyframeAnimations.degreeVec(-55.6563F, -5.082F, -1.6751F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.24F, KeyframeAnimations.degreeVec(47.9174F, 14.298F, -25.7058F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.28F, KeyframeAnimations.degreeVec(47.9174F, 14.298F, -25.7058F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(3.28F, KeyframeAnimations.degreeVec(76.9331F, 3.0042F, -15.7028F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"left_arm",
			new AnimationChannel(
				AnimationChannel.Targets.POSITION,
				new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.28F, KeyframeAnimations.posVec(-2F, 0F, -4F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"right_leg",
			new AnimationChannel(
				AnimationChannel.Targets.ROTATION,
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.2F, KeyframeAnimations.degreeVec(-80.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.72F, KeyframeAnimations.degreeVec(-42.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.24F, KeyframeAnimations.degreeVec(35F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.72F, KeyframeAnimations.degreeVec(35F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.44F, KeyframeAnimations.degreeVec(42.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.96F, KeyframeAnimations.degreeVec(35F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"left_leg",
			new AnimationChannel(
				AnimationChannel.Targets.ROTATION,
				new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.08F, KeyframeAnimations.degreeVec(-80.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.36F, KeyframeAnimations.degreeVec(-42.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.84F, KeyframeAnimations.degreeVec(35F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.36F, KeyframeAnimations.degreeVec(35F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.4F, KeyframeAnimations.degreeVec(42.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.92F, KeyframeAnimations.degreeVec(35F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.build();
	public static final AnimationDefinition KIRBY_DEATH = Builder.withLength(3.5F)
		.addAnimation(
			"bone",
			new AnimationChannel(
				AnimationChannel.Targets.POSITION,
				new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.16F, KeyframeAnimations.posVec(0F, -1F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.28F, KeyframeAnimations.posVec(0F, 32F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.68F, KeyframeAnimations.posVec(0F, 29F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.92F, KeyframeAnimations.posVec(0F, -12F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.04F, KeyframeAnimations.posVec(0F, -12F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.2F, KeyframeAnimations.posVec(0F, -12F, 0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.32F, KeyframeAnimations.posVec(0F, -12F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.08F, KeyframeAnimations.posVec(0F, 50F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.96F, KeyframeAnimations.posVec(0F, -500F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(3.28F, KeyframeAnimations.posVec(0F, -500F, 0F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"bone",
			new AnimationChannel(
				AnimationChannel.Targets.ROTATION,
				new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.32F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(3.48F, KeyframeAnimations.degreeVec(0F, 0F, -2465F), AnimationChannel.Interpolations.LINEAR)
			)
		)
		.addAnimation(
			"body",
			new AnimationChannel(
				AnimationChannel.Targets.POSITION,
				new Keyframe(0.0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.32F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.48F, KeyframeAnimations.posVec(0F, -21F, 0F), AnimationChannel.Interpolations.LINEAR)
			)
		)
		.addAnimation(
			"body",
			new AnimationChannel(
				AnimationChannel.Targets.ROTATION,
				new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.16F, KeyframeAnimations.degreeVec(35F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.32F, KeyframeAnimations.degreeVec(-7.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.92F, KeyframeAnimations.degreeVec(2.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.96F, KeyframeAnimations.degreeVec(-32.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.12F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"head",
			new AnimationChannel(
				AnimationChannel.Targets.ROTATION,
				new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.2F, KeyframeAnimations.degreeVec(47.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.32F, KeyframeAnimations.degreeVec(-15F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.92F, KeyframeAnimations.degreeVec(-5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.96F, KeyframeAnimations.degreeVec(15F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.04F, KeyframeAnimations.degreeVec(-25F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.2F, KeyframeAnimations.degreeVec(35F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.32F, KeyframeAnimations.degreeVec(35F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.52F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"right_arm",
			new AnimationChannel(
				AnimationChannel.Targets.POSITION,
				new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.36F, KeyframeAnimations.posVec(2F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.76F, KeyframeAnimations.posVec(3F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.24F, KeyframeAnimations.posVec(3F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"right_arm",
			new AnimationChannel(
				AnimationChannel.Targets.ROTATION,
				new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.24F, KeyframeAnimations.degreeVec(-35.6253F, -25.29298F, -11.45438F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.36F, KeyframeAnimations.degreeVec(4.33362F, 22.13475F, 109.16203F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.4F, KeyframeAnimations.degreeVec(-19.18171F, 12.05536F, 38.76962F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.44F, KeyframeAnimations.degreeVec(10.48032F, 10.94952F, 120.74846F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.48F, KeyframeAnimations.degreeVec(-11.56478F, 9.79544F, 26.24686F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.56F, KeyframeAnimations.degreeVec(10.9453F, 10.48472F, 123.2485F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.68F, KeyframeAnimations.degreeVec(-8.64542F, 12.44278F, 41.29697F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.76F, KeyframeAnimations.degreeVec(4.18854F, 14.5334F, 92.77552F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.96F, KeyframeAnimations.degreeVec(-5.09172F, 14.2477F, 56.60443F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1F, KeyframeAnimations.degreeVec(-0.47662F, 15.10509F, 74.678F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.04F, KeyframeAnimations.degreeVec(-14.59871F, 12.24024F, 67.32805F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.12F, KeyframeAnimations.degreeVec(-28.94953F, 12.06189F, 63.50492F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.24F, KeyframeAnimations.degreeVec(-14.6F, 12.24F, 67.33F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"left_arm",
			new AnimationChannel(
				AnimationChannel.Targets.POSITION,
				new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.36F, KeyframeAnimations.posVec(-2F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.76F, KeyframeAnimations.posVec(-3F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.24F, KeyframeAnimations.posVec(-3F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"left_arm",
			new AnimationChannel(
				AnimationChannel.Targets.ROTATION,
				new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.24F, KeyframeAnimations.degreeVec(-35.6253F, 25.29298F, 11.45438F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.36F, KeyframeAnimations.degreeVec(4.33362F, -22.13475F, -109.16203F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.4F, KeyframeAnimations.degreeVec(-19.18171F, -12.05536F, -38.76962F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.44F, KeyframeAnimations.degreeVec(10.48032F, -10.94952F, -120.74846F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.48F, KeyframeAnimations.degreeVec(-11.56478F, -9.79544F, -26.24686F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.56F, KeyframeAnimations.degreeVec(10.9453F, -10.48472F, -123.2485F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.68F, KeyframeAnimations.degreeVec(-8.64542F, -12.44278F, -41.29697F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.76F, KeyframeAnimations.degreeVec(4.18854F, -14.5334F, -92.77552F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.96F, KeyframeAnimations.degreeVec(-5.09172F, -14.2477F, -56.60443F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1F, KeyframeAnimations.degreeVec(-0.47662F, -15.10509F, -74.678F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.04F, KeyframeAnimations.degreeVec(15.20535F, -12.39428F, -63.49328F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.12F, KeyframeAnimations.degreeVec(-28.94953F, -12.06189F, -63.50492F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.24F, KeyframeAnimations.degreeVec(-14.6F, -12.24F, -67.33F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"right_leg",
			new AnimationChannel(
				AnimationChannel.Targets.POSITION,
				new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.8F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.88F, KeyframeAnimations.posVec(-2F, 13F, -3F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.96F, KeyframeAnimations.posVec(-2F, 8F, -8F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1F, KeyframeAnimations.posVec(-2F, 2F, -5F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.2F, KeyframeAnimations.posVec(-2F, 2F, -5F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.32F, KeyframeAnimations.posVec(-2F, 2F, -5F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.48F, KeyframeAnimations.posVec(-2F, -18F, -3F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"right_leg",
			new AnimationChannel(
				AnimationChannel.Targets.ROTATION,
				new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.16F, KeyframeAnimations.degreeVec(12.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.24F, KeyframeAnimations.degreeVec(-22.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.28F, KeyframeAnimations.degreeVec(12.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.32F, KeyframeAnimations.degreeVec(-37.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.36F, KeyframeAnimations.degreeVec(42.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.4F, KeyframeAnimations.degreeVec(-27.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.44F, KeyframeAnimations.degreeVec(17.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.48F, KeyframeAnimations.degreeVec(-52.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.52F, KeyframeAnimations.degreeVec(32.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.64F, KeyframeAnimations.degreeVec(-20F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.68F, KeyframeAnimations.degreeVec(20F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.76F, KeyframeAnimations.degreeVec(2.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.84F, KeyframeAnimations.degreeVec(2.44081F, -0.54094F, 12.48848F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.04F, KeyframeAnimations.degreeVec(-92.48338F, 2.99703F, 16.02295F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.24F, KeyframeAnimations.degreeVec(-92.48F, 3F, 16.02F), AnimationChannel.Interpolations.LINEAR)
			)
		)
		.addAnimation(
			"left_leg",
			new AnimationChannel(
				AnimationChannel.Targets.POSITION,
				new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.8F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.96F, KeyframeAnimations.posVec(0F, 10F, -3F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.04F, KeyframeAnimations.posVec(0F, 8F, -10F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(1.08F, KeyframeAnimations.posVec(0F, 2F, -6F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.28F, KeyframeAnimations.posVec(0F, 2F, -6F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.36F, KeyframeAnimations.posVec(0F, 2F, -6F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.48F, KeyframeAnimations.posVec(0F, -18F, -3F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"left_leg",
			new AnimationChannel(
				AnimationChannel.Targets.ROTATION,
				new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.12F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.16F, KeyframeAnimations.degreeVec(10F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.2F, KeyframeAnimations.degreeVec(-5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.24F, KeyframeAnimations.degreeVec(45F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.28F, KeyframeAnimations.degreeVec(-35F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.32F, KeyframeAnimations.degreeVec(35F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.36F, KeyframeAnimations.degreeVec(-37.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.4F, KeyframeAnimations.degreeVec(25F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.44F, KeyframeAnimations.degreeVec(-40F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.48F, KeyframeAnimations.degreeVec(52.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.52F, KeyframeAnimations.degreeVec(-40F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.64F, KeyframeAnimations.degreeVec(37.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.76F, KeyframeAnimations.degreeVec(-15F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.84F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1F, KeyframeAnimations.degreeVec(-33.43738F, -19.53723F, -9.46391F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.08F, KeyframeAnimations.degreeVec(-88.43738F, -19.53723F, -9.46391F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.12F, KeyframeAnimations.degreeVec(-100.93738F, -19.53723F, -9.46391F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.32F, KeyframeAnimations.degreeVec(-100.94F, -19.54F, -9.46F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.build();
}
