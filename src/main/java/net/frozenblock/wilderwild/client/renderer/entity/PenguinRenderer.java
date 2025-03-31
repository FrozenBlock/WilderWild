/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.client.renderer.entity;

import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.PenguinModel;
import net.frozenblock.wilderwild.entity.Penguin;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class PenguinRenderer<T extends Penguin> extends MobRenderer<T, EntityModel<T>> {
	private static final ResourceLocation TEXTURE = WWConstants.id("textures/entity/penguin/penguin.png");
	private static final ResourceLocation LINUX = WWConstants.id("textures/entity/penguin/penguin_linux.png");

	public PenguinRenderer(EntityRendererProvider.Context context) {
		super(context, new PenguinModel<>(context.bakeLayer(WWModelLayers.PENGUIN)), 0.5F);
	}

	@Override
	@NotNull
	public ResourceLocation getTextureLocation(@NotNull T entity) {
		return entity.isLinux() ? LINUX : TEXTURE;
	}

}

