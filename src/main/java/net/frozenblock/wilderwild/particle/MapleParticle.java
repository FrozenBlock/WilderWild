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

public class MapleParticle extends CherryParticle {
	public MapleParticle(ClientLevel world, double x, double y, double z, SpriteSet spriteProvider, float r, float g, float b) {
		super(world, x, y, z, spriteProvider);
		this.rCol = r;
		this.gCol = g;
		this.bCol = b;
		this.quadSize *= 2.5F;
	}
}