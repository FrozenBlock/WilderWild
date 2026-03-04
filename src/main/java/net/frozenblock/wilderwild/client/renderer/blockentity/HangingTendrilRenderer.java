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

package net.frozenblock.wilderwild.client.renderer.blockentity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.renderer.blockentity.BillboardBlockEntityRenderer;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.HangingTendrilBlock;
import net.frozenblock.wilderwild.block.entity.HangingTendrilBlockEntity;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.renderer.blockentity.state.HangingTendrilRenderState;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class HangingTendrilRenderer<T extends HangingTendrilBlockEntity> extends BillboardBlockEntityRenderer<T, HangingTendrilRenderState> {
	private final TextureAtlasSprite defaultSprite;
	private final TextureAtlasSprite activeSprite;
	private final TextureAtlasSprite twitchingSprite;
	private final TextureAtlasSprite milkingSprite;

	public HangingTendrilRenderer(Context context) {
		super(context);
		this.defaultSprite = getSprite(WWConstants.id("hanging_tendril"));
		this.activeSprite = getSprite(WWConstants.id("hanging_tendril_active"));
		this.twitchingSprite = getSprite(WWConstants.id("hanging_tendril_twitch"));
		this.milkingSprite = getSprite(WWConstants.id("hanging_tendril_milk"));
	}

	@Override
	public ModelPart getRoot(Context context) {
		return context.bakeLayer(WWModelLayers.HANGING_TENDRIL);
	}

	@Override
	public TextureAtlasSprite getSprite(HangingTendrilRenderState renderState) {
		return renderState.sprite;
	}

	@Override
	public HangingTendrilRenderState createRenderState() {
		return new HangingTendrilRenderState();
	}

	@Override
	public void extractRenderState(
		T hangingTendril,
		HangingTendrilRenderState renderState,
		float partialTicks,
		Vec3 cameraPos,
		@Nullable ModelFeatureRenderer.CrumblingOverlay breakProgress
	) {
		super.extractRenderState(hangingTendril, renderState, partialTicks, cameraPos, breakProgress);

		final BlockState blockState = hangingTendril.getBlockState();
		if (blockState.getValue(HangingTendrilBlock.WRINGING_OUT)) {
			renderState.sprite = this.milkingSprite;
		} else if (!SculkSensorBlock.canActivate(blockState)) {
			renderState.sprite = this.activeSprite;
		} else if (blockState.getValue(HangingTendrilBlock.TWITCHING)) {
			renderState.sprite = this.twitchingSprite;
		} else {
			renderState.sprite = this.defaultSprite;
		}
	}
}
