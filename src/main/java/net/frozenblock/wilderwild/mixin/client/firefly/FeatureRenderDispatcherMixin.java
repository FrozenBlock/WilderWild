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

package net.frozenblock.wilderwild.mixin.client.firefly;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.client.renderer.feature.FireflyFeatureRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(FeatureRenderDispatcher.class)
public class FeatureRenderDispatcherMixin {

	@Shadow
	@Final
	private SubmitNodeStorage submitNodeStorage;
	@Shadow
	@Final
	private MultiBufferSource.BufferSource bufferSource;
	@Unique
	private final FireflyFeatureRenderer wilderWild$fireflyFeatureRenderer = new FireflyFeatureRenderer();

	@Inject(
		method = "renderAllFeatures",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/feature/EntityModelFeatureRenderer;render(Lnet/minecraft/client/renderer/SubmitNodeStorage;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;Lnet/minecraft/client/renderer/OutlineBufferSource;)V",
			shift = At.Shift.AFTER
		)
	)
	public void wilderWild$renderFireflies(CallbackInfo info) {
		this.wilderWild$fireflyFeatureRenderer.render(this.submitNodeStorage, this.bufferSource);
	}

}
