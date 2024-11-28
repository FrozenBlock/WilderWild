/*
 * Copyright 2024 FrozenBlock
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

package net.frozenblock.wilderwild.client.renderer.entity.layers;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.model.ScorchedModel;
import net.frozenblock.wilderwild.client.renderer.entity.state.ScorchedRenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class ScorchedGlowingLayer extends EyesLayer<ScorchedRenderState, ScorchedModel> {
	private static final RenderType SCORCHED_GLOWING = RenderType.eyes(WWConstants.id("textures/entity/scorched/scorched_glowing.png"));

	public ScorchedGlowingLayer(RenderLayerParent<ScorchedRenderState, ScorchedModel> renderLayerParent) {
		super(renderLayerParent);
	}

	@NotNull
	@Override
	public RenderType renderType() {
		return SCORCHED_GLOWING;
	}
}
