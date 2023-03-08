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

package net.frozenblock.wilderwild.mixin.client.general;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.misc.client.WilderDripSuspendedParticleInterface;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SuspendedParticle;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(Particle.class)
public class ParticleMixin {

	@Shadow
	public int age;
	@Shadow
	public int lifetime;

	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	public void wilderWild$suspendedParticleScaling(CallbackInfo info) {
		if (SingleQuadParticle.class.cast(this) instanceof SuspendedParticle suspendedParticle) {
			((WilderDripSuspendedParticleInterface)suspendedParticle).wilderWild$calcScale();
			float scale = ((WilderDripSuspendedParticleInterface)suspendedParticle).wilderWild$getScale(0F);
			this.age = Mth.clamp(age - 1, 0, this.lifetime);
			if (scale < 0.075F) {
				this.age = Mth.clamp(age - 1, 0, this.lifetime);
			}
		}
	}

	@Inject(method = "tick", at = @At("TAIL"), cancellable = true)
	public void wilderWild$removeOnceSmallEnough(CallbackInfo info) {
		if (SingleQuadParticle.class.cast(this) instanceof SuspendedParticle suspendedParticle) {
			if (((WilderDripSuspendedParticleInterface)suspendedParticle).wilderWild$runScaleRemoval()) {
				suspendedParticle.remove();
				info.cancel();
			}
		}
	}
}
