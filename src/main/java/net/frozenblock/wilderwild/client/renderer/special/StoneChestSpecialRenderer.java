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

package net.frozenblock.wilderwild.client.renderer.special;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.StoneChestModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class StoneChestSpecialRenderer implements NoDataSpecialModelRenderer {
	public static final ResourceLocation STONE_CHEST_TEXTURE = WWConstants.id("stone");
	private final StoneChestModel model;
	private final Material material;
	private final float openness;

	public StoneChestSpecialRenderer(StoneChestModel stoneChestModel, Material material, float f) {
		this.model = stoneChestModel;
		this.material = material;
		this.openness = f;
	}

	@Override
	public void render(ItemDisplayContext itemDisplayContext, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, boolean bl) {
		VertexConsumer vertexConsumer = this.material.buffer(multiBufferSource, RenderType::entitySolid);
		this.model.setupAnim(this.openness);
		this.model.renderToBuffer(poseStack, vertexConsumer, i, j);
	}

	@Environment(EnvType.CLIENT)
	public record Unbaked(ResourceLocation texture, float openness) implements SpecialModelRenderer.Unbaked {
		public static final MapCodec<StoneChestSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
					ResourceLocation.CODEC.fieldOf("texture").forGetter(StoneChestSpecialRenderer.Unbaked::texture),
					Codec.FLOAT.optionalFieldOf("openness", 0F).forGetter(StoneChestSpecialRenderer.Unbaked::openness)
				)
				.apply(instance, StoneChestSpecialRenderer.Unbaked::new)
		);

		public Unbaked(ResourceLocation resourceLocation) {
			this(resourceLocation, 0F);
		}

		@Override
		public @NotNull MapCodec<StoneChestSpecialRenderer.Unbaked> type() {
			return MAP_CODEC;
		}

		@Override
		public @NotNull SpecialModelRenderer<?> bake(@NotNull EntityModelSet entityModelSet) {
			StoneChestModel stoneChestModel = new StoneChestModel(entityModelSet.bakeLayer(WWModelLayers.STONE_CHEST));
			Material material = new Material(Sheets.CHEST_SHEET, this.texture.withPrefix("entity/stone_chest/"));
			return new StoneChestSpecialRenderer(stoneChestModel, material, this.openness);
		}
	}
}