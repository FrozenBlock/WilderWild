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

package net.frozenblock.wilderwild.client.renderer.feature;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.client.renderer.entity.FireflyRenderer;
import net.frozenblock.wilderwild.client.renderer.entity.state.FireflyRenderState;
import net.frozenblock.wilderwild.client.renderer.impl.FireflyNodeStorageInterface;
import net.frozenblock.wilderwild.client.renderer.impl.FireflySubmit;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import java.util.Comparator;
import java.util.List;

@Environment(EnvType.CLIENT)
public class FireflyFeatureRenderer {
	private final PoseStack poseStack = new PoseStack();

	public void render(SubmitNodeStorage submitNodeStorage, MultiBufferSource.BufferSource bufferSource) {
		if (!(submitNodeStorage instanceof FireflyNodeStorageInterface fireflyNodeStorage)) return;
		fireflyNodeStorage.wilderWild$getFireflySubmits()
			.sort(
				Comparator.<FireflySubmit>comparingDouble(fireflySubmit -> -fireflySubmit.position().lengthSquared())
					.thenComparingInt(FireflySubmit::order)
			);
		this.renderFireflies(bufferSource, fireflyNodeStorage.wilderWild$getFireflySubmits());
	}

	private void renderFireflies(MultiBufferSource.BufferSource bufferSource, List<FireflySubmit> submits) {
		for (FireflySubmit fireflySubmit : submits) this.renderFirefly(fireflySubmit, bufferSource);
	}

	private void renderFirefly(
		FireflySubmit fireflySubmit, MultiBufferSource.BufferSource bufferSource
	) {
		this.poseStack.pushPose();
		this.poseStack.last().set(fireflySubmit.pose());
		FireflyRenderState renderState = fireflySubmit.renderState();
		FireflyRenderer.renderFirefly(
			this.poseStack.last(),
			bufferSource,
			renderState.lightCoords,
			LivingEntityRenderer.getOverlayCoords(renderState, 0F),
			renderState.calcColor,
			fireflySubmit.renderColor() ? renderState.color.assetInfo().texturePath() : null
		);
		this.poseStack.popPose();
	}
}
