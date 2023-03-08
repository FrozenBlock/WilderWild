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

import net.frozenblock.wilderwild.misc.client.WilderDripSuspendedParticleInterface;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SuspendedParticle;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SuspendedParticle.class)
public abstract class SuspendedParticleMixin extends TextureSheetParticle implements WilderDripSuspendedParticleInterface {

	@Unique
	private float wilderWild$scaler = 0.15F;
	@Unique
	private float wilderWild$prevScale = 0F;
	@Unique
	private float wilderWild$scale = 0F;
	@Unique
	private float wilderWild$targetScale = 0F;

	@Unique
	private boolean wilderWild$usesWind;

	protected SuspendedParticleMixin(ClientLevel clientLevel, double d, double e, double f) {
		super(clientLevel, d, e, f);
	}

	@Unique
	@Override
	public void wilderWild$setUsesWind(boolean bl) {
		this.wilderWild$usesWind = bl;
	}

	@Unique
	@Override
	public boolean wilderWild$usesWind() {
		return this.wilderWild$usesWind;
	}

	@Override
	public float wilderWild$getScale(float partialTick) {
		return Mth.lerp(partialTick, this.wilderWild$prevScale, this.wilderWild$scale);
	}

	@Override
	public void wilderWild$calcScale() {
		this.wilderWild$prevScale = this.wilderWild$scale;
		this.wilderWild$scale += (this.wilderWild$targetScale - this.wilderWild$scale) * this.wilderWild$scaler;
	}

	@Override
	public boolean wilderWild$runScaleRemoval() {
		this.age = Mth.clamp(age + 1, 0, this.lifetime);
		if (this.age >= this.lifetime) {
			if (this.wilderWild$prevScale <= 0.05F) {
				return true;
			} else {
				this.wilderWild$targetScale = 0F;
			}
		} else {
			this.wilderWild$targetScale = 1F;
		}
		return false;
	}

	@Override
	public void wilderWild$setScaler(float scaler) {
		this.wilderWild$scaler = scaler;
	}
}
