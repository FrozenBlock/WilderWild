/*
 * Copyright 2025 FrozenBlock
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

package net.frozenblock.wilderwild.client.renderer.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.entity.variant.jellyfish.JellyfishVariant;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

@Environment(EnvType.CLIENT)
public class JellyfishRenderState extends LivingEntityRenderState {
	public int tickCount;
	public boolean isRGB;
	public JellyfishVariant variant;
	public float windTime;

	public float jellyXRot;
	public float tentXRot;
	public float armXRot;
	public float jellyScale;
}
