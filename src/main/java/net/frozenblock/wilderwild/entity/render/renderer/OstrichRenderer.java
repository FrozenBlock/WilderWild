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

package net.frozenblock.wilderwild.entity.render.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.frozenblock.wilderwild.WilderConstants;
import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.entity.Ostrich;
import net.frozenblock.wilderwild.entity.render.model.OstrichInbredModel;
import net.frozenblock.wilderwild.entity.render.model.OstrichModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class OstrichRenderer<T extends Ostrich> extends MobRenderer<T, EntityModel<T>> {
	private static final ResourceLocation OSTRICH_LOCATION = WilderConstants.id("textures/entity/ostrich/ostrich.png");
	private static final ResourceLocation OSTRICH_SADDLE_LOCATION = WilderConstants.id("textures/entity/ostrich/ostrich_saddle.png");

	private boolean isInbred = false;
	private final EntityModel<T> normalModel = this.getModel();
	private final EntityModel<T> inbredModel;

	public OstrichRenderer(EntityRendererProvider.Context context) {
		super(context, new OstrichModel<>(context.bakeLayer(WilderWildClient.OSTRICH)), 0.75F);
		this.inbredModel = new OstrichInbredModel<>(context.bakeLayer(WilderWildClient.OSTRICH_INBRED));
		this.addLayer(new SaddleLayer<>(this, new OstrichModel<>(context.bakeLayer(WilderWildClient.OSTRICH_SADDLE)), OSTRICH_SADDLE_LOCATION));
	}

	@Override
	public void render(@NotNull T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
		boolean isOstrichInbred = entity.isInbred();
		if (this.isInbred != isOstrichInbred) {
			this.model = !isOstrichInbred ? this.normalModel : this.inbredModel;
			this.isInbred = isOstrichInbred;
		}
		super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
	}


	@Override
	@NotNull
	public ResourceLocation getTextureLocation(@NotNull T entity) {
		return OSTRICH_LOCATION;
	}

}

