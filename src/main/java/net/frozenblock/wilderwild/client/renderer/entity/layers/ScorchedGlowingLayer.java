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
 * You should have received a copy of the FrozenBlock Modding oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.client.renderer.entity.layers;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class ScorchedGlowingLayer<T extends Entity, M extends SpiderModel<T>> extends EyesLayer<T, M> {
	private static final RenderType SCORCHED_GLOWING = RenderType.eyes(WWConstants.id("textures/entity/scorched/scorched_glowing.png"));

	public ScorchedGlowingLayer(RenderLayerParent<T, M> renderLayerParent) {
		super(renderLayerParent);
	}

	@NotNull
	@Override
	public RenderType renderType() {
		return SCORCHED_GLOWING;
	}
}
