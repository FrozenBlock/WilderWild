/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.particle;

import net.frozenblock.wilderwild.block.api.FallingLeafRegistry;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.CherryParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import org.jetbrains.annotations.NotNull;

public class LeafParticle extends CherryParticle {
	public LeafParticle(
		ClientLevel world,
		double x, double y, double z,
		double velX, double velY, double velZ,
		float quadSize,
		float gravityScale,
		float rotScale,
		SpriteSet spriteProvider
	) {
		super(world, x, y, z, spriteProvider);
		this.quadSize = quadSize;
		this.gravity *= gravityScale;
		this.rotSpeed *= rotScale;
		this.xd = velX;
		this.yd = velY;
		this.zd = velZ;
	}

	public static @NotNull Particle createFallingLeafParticle(
		ParticleOptions particleOptions,
		ClientLevel level,
		double x, double y, double z,
		double velocityX, double velocityY, double velocityZ,
		SpriteSet spriteProvider
	) {
		FallingLeafRegistry.LeafParticleData leafParticleData = FallingLeafRegistry.getLeafParticleData(particleOptions);
		return new LeafParticle(
			level,
			x, y, z,
			velocityX, velocityY, velocityZ,
			leafParticleData.quadSize(), leafParticleData.particleGravityScale(), leafParticleData.particleGravityScale() * 0.5F,
			spriteProvider
		);
	}
}
