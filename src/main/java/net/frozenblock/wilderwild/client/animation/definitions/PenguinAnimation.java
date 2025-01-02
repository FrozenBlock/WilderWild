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

	public static final AnimationDefinition PENGUIN_LAY_DOWN = AnimationDefinition.Builder.withLength(0.6603F)
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.3415F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.4098F, KeyframeAnimations.posVec(0F, 0.5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.4781F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("torso", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.1821F, KeyframeAnimations.degreeVec(42.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.3415F, KeyframeAnimations.degreeVec(90F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.4554F, KeyframeAnimations.degreeVec(87.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.5692F, KeyframeAnimations.degreeVec(90F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("torso", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.2505F, KeyframeAnimations.posVec(0F, -1.5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.296F, KeyframeAnimations.posVec(0F, -2.5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.3415F, KeyframeAnimations.posVec(0F, -3.5F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.1138F, KeyframeAnimations.degreeVec(-8.75F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.1821F, KeyframeAnimations.degreeVec(-20.23F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.2732F, KeyframeAnimations.degreeVec(-50F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.3643F, KeyframeAnimations.degreeVec(-57.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.5009F, KeyframeAnimations.degreeVec(-70F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.6603F, KeyframeAnimations.degreeVec(-75F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("feet", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.1821F, KeyframeAnimations.degreeVec(34.6271F, -4.6414F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.296F, KeyframeAnimations.degreeVec(92.1786F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("feet", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.0683F, KeyframeAnimations.posVec(0F, 0F, 2F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.1821F, KeyframeAnimations.posVec(0F, 2.25F, 5.5F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.296F, KeyframeAnimations.posVec(0F, 4.25F, 5.75F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_foot", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.2505F, KeyframeAnimations.degreeVec(-1.4884F, -1.6172F, -3.3083F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.3415F, KeyframeAnimations.degreeVec(2.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.4326F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_foot", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(-1.49F, -1.62F, -3.31F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.296F, KeyframeAnimations.degreeVec(-1.4884F, -1.6172F, -3.3083F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.3871F, KeyframeAnimations.degreeVec(2.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
		new Keyframe(0.4781F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		)).build();
}
