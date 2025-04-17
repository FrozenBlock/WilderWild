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

package net.frozenblock.wilderwild.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import net.frozenblock.wilderwild.client.animation.definitions.impl.WilderWarden;
import net.minecraft.client.model.WardenModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.WardenEmissiveLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.monster.warden.Warden;
import org.jetbrains.annotations.NotNull;

public class StellaWardenLayer<T extends Warden, M extends WardenModel<T>> extends WardenEmissiveLayer<T, M> {
	public StellaWardenLayer(@NotNull RenderLayerParent<T, M> context, @NotNull ResourceLocation texture, @NotNull AlphaFunction<T> animationAngleAdjuster, @NotNull DrawSelector<T, M> modelPartVisibility) {
		super(context, texture, animationAngleAdjuster, modelPartVisibility);
	}

	@Override
	public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource vertexConsumerProvider, int i, @NotNull T wardenEntity, float f, float g, float partialTick, float j, float k, float l) {
		if (!wardenEntity.isInvisible() && ((WilderWarden) wardenEntity).wilderWild$isStella()) {
			this.onlyDrawSelectedParts();
			VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderType.entityTranslucentEmissive(this.texture));
			float alpha = this.alphaFunction.apply(wardenEntity, partialTick, j);
			this.getParentModel()
				.renderToBuffer(
					poseStack,
					vertexConsumer,
					i,
					LivingEntityRenderer.getOverlayCoords(wardenEntity, 0F),
					FastColor.ARGB32.colorFromFloat(alpha, 1F, 1F, 1F)
				);
			this.resetDrawForAllParts();
		}
	}

	private void onlyDrawSelectedParts() {
		List<ModelPart> list = this.drawSelector.getPartsToDraw(this.getParentModel());
		this.getParentModel().root().getAllParts().forEach(part -> part.skipDraw = true);
		list.forEach(part -> part.skipDraw = false);
	}

	private void resetDrawForAllParts() {
		this.getParentModel().root().getAllParts().forEach(part -> part.skipDraw = false);
	}
}
