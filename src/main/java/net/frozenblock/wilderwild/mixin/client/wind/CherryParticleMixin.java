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

package net.frozenblock.wilderwild.mixin.client.wind;

import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.CherryParticle;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CherryParticle.class)
public abstract class CherryParticleMixin extends TextureSheetParticle {

	protected CherryParticleMixin(ClientLevel clientLevel, double d, double e, double f) {
		super(clientLevel, d, e, f);
	}

	@Unique
	private double wilderWild$movementWithWindX;

	@Unique
	private double wilderWild$movementWithWindY;

	@Unique
	private double wilderWild$movementWithWindZ;

	@Inject(method = "tick", at = @At("HEAD"))
	public void wilderWild$tick(CallbackInfo info) {
		Vec3 wind = ClientWindManager.getWindMovement(this.level, BlockPos.containing(this.x, this.y, this.z), 1.55).scale(WilderSharedConstants.config().particleWindMovement());
		this.wilderWild$movementWithWindX = this.xd + wind.x * 0.001;
		this.wilderWild$movementWithWindY = this.yd + wind.y * 0.00001;
		this.wilderWild$movementWithWindZ = this.zd + wind.z * 0.001;
	}

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/CherryParticle;move(DDD)V", shift = At.Shift.BEFORE))
	public void wilderWild$fixMove(CallbackInfo info) {
		this.xd = this.wilderWild$movementWithWindX;
		this.yd = this.wilderWild$movementWithWindY;
		this.zd = this.wilderWild$movementWithWindZ;
	}
}
