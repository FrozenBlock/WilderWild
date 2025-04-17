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

package net.frozenblock.wilderwild.client.renderer.entity;

import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.ButterflyModel;
import net.frozenblock.wilderwild.entity.Butterfly;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ButterflyRenderer<T extends Butterfly> extends MobRenderer<T, EntityModel<T>> {

	public ButterflyRenderer(EntityRendererProvider.Context context) {
		super(context, new ButterflyModel<>(context.bakeLayer(WWModelLayers.BUTTERFLY)), 0.25F);
	}

	@Override
	@NotNull
	public ResourceLocation getTextureLocation(@NotNull T butterfly) {
		return butterfly.getVariantForRendering().texture();
	}

}

