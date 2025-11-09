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

package net.frozenblock.wilderwild.client.renderer.blockentity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.block.client.entity.BillboardBlockEntityRenderer;
import net.frozenblock.wilderwild.block.entity.HangingTendrilBlockEntity;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.renderer.blockentity.state.HangingTendrilRenderState;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class HangingTendrilRenderer<T extends HangingTendrilBlockEntity> extends BillboardBlockEntityRenderer<T, HangingTendrilRenderState> {

	public HangingTendrilRenderer(@NotNull Context ctx) {
		super(ctx);
	}

	@NotNull
	public static LayerDefinition getTexturedModelData() {
		return BillboardBlockEntityRenderer.getTexturedModelData();
	}

	@Override
	@NotNull
	public ModelPart getRoot(@NotNull Context ctx) {
		return ctx.bakeLayer(WWModelLayers.HANGING_TENDRIL);
	}

	@Override
	@NotNull
	public Identifier getTexture(@NotNull HangingTendrilRenderState renderState) {
		return renderState.texture;
	}

	@Override
	public @NotNull HangingTendrilRenderState createRenderState() {
		return new HangingTendrilRenderState();
	}

	@Override
	public void extractRenderState(
		@NotNull T hangingTendril,
		@NotNull HangingTendrilRenderState renderState,
		float partialTicks,
		@NotNull Vec3 cameraPos,
		@Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay
	) {
		super.extractRenderState(hangingTendril, renderState, partialTicks, cameraPos, crumblingOverlay);
		renderState.texture = hangingTendril.getClientTexture();
	}
}
