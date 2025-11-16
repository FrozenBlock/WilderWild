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
import java.util.function.Consumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.StoneChestModel;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;
import org.joml.Vector3fc;

@Environment(EnvType.CLIENT)
public class StoneChestSpecialRenderer implements NoDataSpecialModelRenderer {
	public static final Identifier STONE_CHEST_TEXTURE = WWConstants.id("stone");
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
		ItemDisplayContext context,
		PoseStack poseStack,
		SubmitNodeCollector collector,
		int light,
		int overlay,
		boolean bl,
		int outlineColor
	) {
		collector.submitModel(
			this.model,
			this.openness,
			poseStack,
			this.material.renderType(RenderTypes::entitySolid),
			light,
			overlay,
			-1,
			this.materials.get(this.material),
			outlineColor,
			null
		);
	}

	@Override
	public void getExtents(Consumer<Vector3fc> consumer) {
		PoseStack poseStack = new PoseStack();
		this.model.setupAnim(this.openness);
		this.model.root().getExtentsForGui(poseStack, consumer);
	}

	@Environment(EnvType.CLIENT)
	public record Unbaked(Identifier texture, float openness) implements SpecialModelRenderer.Unbaked {
		public static final MapCodec<StoneChestSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
				Identifier.CODEC.fieldOf("resourceTexture").forGetter(StoneChestSpecialRenderer.Unbaked::texture),
				Codec.FLOAT.optionalFieldOf("openness", 0F).forGetter(StoneChestSpecialRenderer.Unbaked::openness)
			).apply(instance, StoneChestSpecialRenderer.Unbaked::new)
		);

		public Unbaked(Identifier identifier) {
			this(identifier, 0F);
		}

		@Override
		public MapCodec<StoneChestSpecialRenderer.Unbaked> type() {
			return MAP_CODEC;
		}

		@Override
		public SpecialModelRenderer<?> bake(BakingContext bakingContext) {
			StoneChestModel stoneChestModel = new StoneChestModel(bakingContext.entityModelSet().bakeLayer(WWModelLayers.STONE_CHEST));
			Material material = new Material(Sheets.CHEST_SHEET, this.texture.withPrefix("entity/stone_chest/"));
			return new StoneChestSpecialRenderer(bakingContext.materials(), stoneChestModel, material, this.openness);
		}
	}
}
