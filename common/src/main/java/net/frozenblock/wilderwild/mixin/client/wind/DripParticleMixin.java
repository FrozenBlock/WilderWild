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

package net.frozenblock.wilderwild.mixin.client.wind;

import net.frozenblock.wilderwild.particle.impl.WilderDripSuspendedParticleInterface;
import net.frozenblock.wilderwild.wind.WWWindManagerExtension;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.DripParticle;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClientOnly
@Mixin(DripParticle.class)
public abstract class DripParticleMixin extends SingleQuadParticle implements WilderDripSuspendedParticleInterface {

	@Unique
	private boolean wilderWild$usesWind = true;

	protected DripParticleMixin(ClientLevel level, double x, double y, double z, TextureAtlasSprite sprite) {
		super(level, x, y, z, sprite);
	}

	@Unique
	@Override
	public void wilderWild$setUsesWind(boolean usesWind) {
		this.wilderWild$usesWind = usesWind;
	}

	@Unique
	@Override
	public boolean wilderWild$usesWind() {
		return this.wilderWild$usesWind;
	}

	@Inject(method = "tick", at = @At("HEAD"))
	public void wilderWild$tick(CallbackInfo info) {
		if (!this.wilderWild$usesWind()) return;

		WWWindManagerExtension.getParticleWindMovement(this.level, this.x, this.y, this.z).apply(
			d -> this.xd += d,
			d -> this.yd += d,
			d -> this.zd += d,
			0.001D, 0.00005D, 0.01D
		);
	}
}
