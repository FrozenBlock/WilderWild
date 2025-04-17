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

package net.frozenblock.wilderwild.mixin.client.sodium;


import net.caffeinemc.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Environment(EnvType.CLIENT)
@Mixin(BlockRenderer.class)
public abstract class BlockRendererMixin {
	//extends AbstractBlockRenderContext {

	@Unique
	private static final BlockModelShaper WILDERWILD$BLOCK_MODEL_SHAPER = Minecraft.getInstance().getBlockRenderer().getBlockModelShaper();

	@Shadow
	public abstract void renderModel(BakedModel model, BlockState state, BlockPos pos, BlockPos origin);

	@Inject(method = "renderModel", at = @At("HEAD"), remap = false, require = 0)
	public void wilderWild$renderModel(BakedModel model, BlockState state, BlockPos pos, BlockPos origin, CallbackInfo info) {
		if (SnowloggingUtils.isSnowlogged(state)) {
			BlockState snowState = SnowloggingUtils.getSnowEquivalent(state);
			BakedModel snowModel = WILDERWILD$BLOCK_MODEL_SHAPER.getBlockModel(snowState);
			this.renderModel(snowModel, snowState, pos, origin);
		}
	}

}
