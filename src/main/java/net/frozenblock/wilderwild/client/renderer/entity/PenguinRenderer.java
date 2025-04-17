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

