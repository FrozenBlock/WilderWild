/*
 * Copyright 2025-2026 FrozenBlock
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
import net.frozenblock.wilderwild.client.model.object.chest.StoneChestModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiblockChestResources;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.properties.ChestType;
import org.joml.Vector3fc;

@Environment(EnvType.CLIENT)
public class StoneChestSpecialRenderer implements NoDataSpecialModelRenderer {
	public static final MultiblockChestResources<Identifier> STONE = createDefaultTextures("stone");
	public static final MultiblockChestResources<Identifier> STONE_SCULK = createDefaultTextures("stone_sculk");
	private final SpriteGetter sprites;
	private final StoneChestModel model;
	private final SpriteId sprite;
	private final float openness;

	public StoneChestSpecialRenderer(SpriteGetter sprites, StoneChestModel stoneChestModel, SpriteId sprite, float openness) {
		this.sprites = sprites;
		this.model = stoneChestModel;
		this.sprite = sprite;
		this.openness = openness;
	}

	@Override
	public void submit(
		PoseStack poseStack,
		SubmitNodeCollector collector,
		int lightCoords,
		int overlayCoords,
		boolean hasFoil,
		int outlineColor
	) {
		collector.submitModel(
			this.model,
			this.openness,
			poseStack,
			this.sprite.renderType(RenderTypes::entitySolid),
			lightCoords,
			overlayCoords,
			-1,
			this.sprites.get(this.sprite),
			outlineColor,
			null
		);
	}

	@Override
	public void getExtents(Consumer<Vector3fc> consumer) {
		final PoseStack poseStack = new PoseStack();
		this.model.setupAnim(this.openness);
		this.model.root().getExtentsForGui(poseStack, consumer);
	}

	private static MultiblockChestResources<Identifier> createDefaultTextures(String prefix) {
		return new MultiblockChestResources<>(WWConstants.id(prefix), WWConstants.id(prefix + "_left"), WWConstants.id(prefix + "_right"));
	}

	public record Unbaked(Identifier texture, float openness, ChestType chestType) implements NoDataSpecialModelRenderer.Unbaked {
		public static final MapCodec<StoneChestSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(
			instance -> instance.group(
				Identifier.CODEC.fieldOf("texture").forGetter(StoneChestSpecialRenderer.Unbaked::texture),
				Codec.FLOAT.optionalFieldOf("openness", 0F).forGetter(StoneChestSpecialRenderer.Unbaked::openness),
				ChestType.CODEC.optionalFieldOf("chest_type", ChestType.SINGLE).forGetter(StoneChestSpecialRenderer.Unbaked::chestType)
			).apply(instance, StoneChestSpecialRenderer.Unbaked::new)
		);

		public Unbaked(Identifier texture, ChestType chestType) {
			this(texture, 0F, chestType);
		}

		public Unbaked(Identifier texture) {
			this(texture, 0F, ChestType.SINGLE);
		}

		@Override
		public MapCodec<StoneChestSpecialRenderer.Unbaked> type() {
			return MAP_CODEC;
		}

		@Override
		public StoneChestSpecialRenderer bake(BakingContext context) {
			final StoneChestModel model = new StoneChestModel(context.entityModelSet().bakeLayer(getModel(this.chestType)));
			final SpriteId fullTexture = Sheets.CHEST_MAPPER.apply(this.texture);
			return new StoneChestSpecialRenderer(context.sprites(), model, fullTexture, this.openness);
		}

		private static ModelLayerLocation getModel(final ChestType type) {
			if (type == ChestType.SINGLE) return WWModelLayers.STONE_CHEST;
			if (type == ChestType.RIGHT) return WWModelLayers.DOUBLE_STONE_CHEST_RIGHT;
			if (type == ChestType.LEFT) return WWModelLayers.DOUBLE_STONE_CHEST_LEFT;
			throw new IllegalStateException("No valid ChestType found!");
		}
	}
}
