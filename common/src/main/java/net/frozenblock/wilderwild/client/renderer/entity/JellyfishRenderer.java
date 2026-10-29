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

package net.frozenblock.wilderwild.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.frozenblock.lib.FrozenLibEarlyConstants;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.animal.jellyfish.BabyJellyfishModel;
import net.frozenblock.wilderwild.client.model.animal.jellyfish.JellyfishModel;
import net.frozenblock.wilderwild.client.renderer.entity.state.JellyfishRenderState;
import net.frozenblock.wilderwild.entity.animal.jellyfish.Jellyfish;
import net.frozenblock.wilderwild.mod_compat.WWIrisCompat;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;

@ClientOnly
public class JellyfishRenderer extends AgeableMobRenderer<Jellyfish, JellyfishRenderState, JellyfishModel> {
	private static final Identifier WHITE_TEXTURE = WWConstants.id("textures/entity/jellyfish/jellyfish_white.png");
	private static final Identifier WHITE_TEXTURE_BABY = WWConstants.id("textures/entity/jellyfish/jellyfish_white_baby.png");

	public JellyfishRenderer(Context context) {
		super(
			context,
			new JellyfishModel(context.bakeLayer(WWModelLayers.JELLYFISH)),
			new BabyJellyfishModel(context.bakeLayer(WWModelLayers.JELLYFISH_BABY)),
			0.3F
		);
	}

	@Override
	protected void setupRotations(JellyfishRenderState state, PoseStack poseStack, float bodyRot, float entityScale) {
		super.setupRotations(state, poseStack, bodyRot, entityScale);
		poseStack.translate(0F, -1F * state.ageScale, 0F);
	}

	@Override
	protected int getModelTint(JellyfishRenderState state) {
		if (state.isRGB) return ARGB.color(
			(int) (Math.clamp(Math.abs((state.levelTime % 6) - 3) - 1, 0, 1) * 255),
			(int) (Math.clamp(Math.abs(((state.levelTime - 2) % 6) - 3) - 1, 0, 1) * 255),
			(int) (Math.clamp(Math.abs(((state.levelTime - 4) % 6) - 3) - 1, 0, 1) * 255)
		);
		return super.getModelTint(state);
	}

	@Override
	protected void scale(JellyfishRenderState state, PoseStack poseStack) {
		super.scale(state, poseStack);
		final float scaleAmount = !state.isBaby ? 0.8F : 0.6F;
		poseStack.scale(scaleAmount, scaleAmount, scaleAmount);
		poseStack.scale(state.jellyScale, state.jellyScale, state.jellyScale);
	}

	@Override
	protected int getBlockLightLevel(Jellyfish jellyfish, BlockPos pos) {
		if (isUsingIrisShaderPack()) return 15; // Emission has to be "faked" with Iris, as we cannot use our fixed emissive RenderType.
		return super.getBlockLightLevel(jellyfish, pos);
	}

	@Override
	public Identifier getTextureLocation(JellyfishRenderState state) {
		if (state.isRGB || state.variant == null) return state.isBaby ? WHITE_TEXTURE_BABY : WHITE_TEXTURE;
		return (state.isBaby ? state.variant.babyTexture() : state.variant.texture()).texturePath();
	}

	@Override
	public JellyfishRenderState createRenderState() {
		return new JellyfishRenderState();
	}

	@Override
	public void extractRenderState(Jellyfish jellyfish, JellyfishRenderState state, float partialTicks) {
		super.extractRenderState(jellyfish, state, partialTicks);
		state.tickCount = jellyfish.tickCount;
		state.isRGB = jellyfish.isRGB();
		state.variant = jellyfish.wilderWild$getVariantOr(null);

		final Level level = jellyfish.level();
		if (level != null) state.levelTime = (level.getGameTime() + partialTicks) * 0.05F;

		state.jellyXRot = -(jellyfish.xRot1 + partialTicks * (jellyfish.xBodyRot - jellyfish.xRot1)) * Mth.DEG_TO_RAD;
		state.tentXRot = -(jellyfish.xRot6 + partialTicks * (jellyfish.xRot5 - jellyfish.xRot6)) * Mth.DEG_TO_RAD;
		state.armXRot = -(jellyfish.xRot9 + partialTicks * (jellyfish.xRot8 - jellyfish.xRot9)) * Mth.DEG_TO_RAD;
		state.jellyScale = jellyfish.prevScale + partialTicks * (jellyfish.scale - jellyfish.prevScale);
	}

	public static boolean isUsingIrisShaderPack() {
		return FrozenLibEarlyConstants.HAS_IRIS && WWIrisCompat.usingShaderPack();
	}
}
