/*
 * Copyright 2023-2025 FrozenBlock
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

/**
 * Put custom Allay animations in here. for example, dancing, swimming. Not for overrides
 */
@Environment(EnvType.CLIENT)
public final class WWAllayAnimation {

	public static final AnimationDefinition DANCING = AnimationDefinition.Builder.withLength(2.75F).looping()
		.addAnimation(
			"head",
			new AnimationChannel(
				AnimationChannel.Targets.ROTATION,
				new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0F, -30F, -30F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.5833F, KeyframeAnimations.degreeVec(0F, 30F, 30F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.2917F, KeyframeAnimations.degreeVec(0.63F, 540F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.75F, KeyframeAnimations.degreeVec(0F, 720F, 0F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"body", new AnimationChannel(
				AnimationChannel.Targets.ROTATION,
				new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(0.4167F, KeyframeAnimations.degreeVec(10F, -16F, -16F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(1.5833F, KeyframeAnimations.degreeVec(-10F, 16F, 16F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.2917F, KeyframeAnimations.degreeVec(0.63F, 540F, 0F), AnimationChannel.Interpolations.CATMULLROM),
				new Keyframe(2.75F, KeyframeAnimations.degreeVec(0F, 720F, 0F), AnimationChannel.Interpolations.CATMULLROM)
			)
		)
		.addAnimation(
			"left_wing",
			new AnimationChannel(
				AnimationChannel.Targets.ROTATION,
				new Keyframe(2F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.1667F, KeyframeAnimations.degreeVec(45F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5833F, KeyframeAnimations.degreeVec(45F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.75F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
			)
		)
		.addAnimation(
			"right_wing",
			new AnimationChannel(
				AnimationChannel.Targets.ROTATION,
				new Keyframe(2F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.1667F, KeyframeAnimations.degreeVec(45F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.5833F, KeyframeAnimations.degreeVec(45F, 0F, 0F), AnimationChannel.Interpolations.LINEAR),
				new Keyframe(2.75F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
			)
		)
		.build();

}
