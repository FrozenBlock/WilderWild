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

package net.frozenblock.wilderwild.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.entity.Scorched;
import net.frozenblock.wilderwild.client.renderer.entity.layers.ScorchedGlowingLayer;
import net.frozenblock.wilderwild.client.model.ScorchedModel;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ScorchedRenderer<T extends Scorched> extends MobRenderer<T, SpiderModel<T>> {
	private static final ResourceLocation SCORCHED_LOCATION = WWConstants.id("textures/entity/scorched/scorched.png");
	private static final float SCALE = 0.9F;

	public ScorchedRenderer(EntityRendererProvider.Context context) {
		this(context, WWModelLayers.SCORCHED);
	}

	public ScorchedRenderer(EntityRendererProvider.Context context, ModelLayerLocation layer) {
		super(context, new ScorchedModel<>(context.bakeLayer(layer)), 0.8F);
		this.addLayer(new ScorchedGlowingLayer<>(this));
		this.shadowRadius *= SCALE;
	}

	@Override
	public void scale(Scorched livingEntity, @NotNull PoseStack poseStack, float partialTickTime) {
		poseStack.scale(SCALE, SCALE, SCALE);
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(Scorched entity) {
		return SCORCHED_LOCATION;
	}

}

