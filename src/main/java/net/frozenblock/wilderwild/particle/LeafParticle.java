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

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.CherryParticle;
import net.minecraft.client.particle.SpriteSet;

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
}
