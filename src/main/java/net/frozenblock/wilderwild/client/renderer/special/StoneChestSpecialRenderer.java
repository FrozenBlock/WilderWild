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

package net.frozenblock.wilderwild.client.renderer.special;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Set;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.StoneChestModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public class StoneChestSpecialRenderer implements NoDataSpecialModelRenderer {
	public static final ResourceLocation STONE_CHEST_TEXTURE = WWConstants.id("stone");
	private final MaterialSet materials;
	private final StoneChestModel model;
	private final Material material;
	private final float openness;

	public StoneChestSpecialRenderer(MaterialSet materialSet, StoneChestModel stoneChestModel, Material material, float openness) {
		this.materials = materialSet;
		this.model = stoneChestModel;
		this.material = material;
		this.openness = openness;
	}

	@Override
	public void submit(
		ItemDisplayContext itemDisplayContext,
		PoseStack poseStack,
		@NotNull SubmitNodeCollector submitNodeCollector,
		int light,
		int overlay,
		boolean bl
	) {
		submitNodeCollector.submitModel(
			this.model,
			this.openness,
			poseStack,
			this.material.renderType(RenderType::entitySolid),
			light,
			overlay,
			-1,
			this.materials.get(this.material),
			0,
			null
		);
	}

	@Override
	public void getExtents(@NotNull Set<Vector3f> set) {
		PoseStack poseStack = new PoseStack();
		this.model.setupAnim(this.openness);
		this.model.root().getExtentsForGui(poseStack, set);
	}

	@Environment(EnvType.CLIENT)
	public record Unbaked(ResourceLocation texture, float openness) implements SpecialModelRenderer.Unbaked {
		public static final MapCodec<StoneChestSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
				ResourceLocation.CODEC.fieldOf("texture").forGetter(StoneChestSpecialRenderer.Unbaked::texture),
				Codec.FLOAT.optionalFieldOf("openness", 0F).forGetter(StoneChestSpecialRenderer.Unbaked::openness)
			).apply(instance, StoneChestSpecialRenderer.Unbaked::new)
		);

		public Unbaked(ResourceLocation resourceLocation) {
			this(resourceLocation, 0F);
		}

		@Override
		public @NotNull MapCodec<StoneChestSpecialRenderer.Unbaked> type() {
			return MAP_CODEC;
		}

		@Override
		public @NotNull SpecialModelRenderer<?> bake(@NotNull BakingContext bakingContext) {
			StoneChestModel stoneChestModel = new StoneChestModel(bakingContext.entityModelSet().bakeLayer(WWModelLayers.STONE_CHEST));
			Material material = new Material(Sheets.CHEST_SHEET, this.texture.withPrefix("entity/stone_chest/"));
			return new StoneChestSpecialRenderer(bakingContext.materials(), stoneChestModel, material, this.openness);
		}
	}
}
