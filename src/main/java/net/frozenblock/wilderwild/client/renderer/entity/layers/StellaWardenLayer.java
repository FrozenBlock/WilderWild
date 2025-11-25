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

package net.frozenblock.wilderwild.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Function;
import net.frozenblock.wilderwild.client.animation.definitions.impl.WilderWarden;
import net.minecraft.client.model.monster.warden.WardenModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.LivingEntityEmissiveLayer;
import net.minecraft.client.renderer.entity.state.WardenRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;

public class StellaWardenLayer extends LivingEntityEmissiveLayer<WardenRenderState, WardenModel> {

	public StellaWardenLayer(
		RenderLayerParent<WardenRenderState, WardenModel> context,
		Function<WardenRenderState, Identifier> texture,
		AlphaFunction<WardenRenderState> alphaFunction,
		WardenModel model,
		Function<Identifier, RenderType> bufferProvider
	) {
		super(context, texture, alphaFunction, model, bufferProvider, false);
	}

	@Override
	public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, WardenRenderState renderState, float f, float g) {
		if (renderState instanceof WilderWarden wilderWarden && !wilderWarden.wilderWild$isStella()) return;
		super.submit(poseStack, submitNodeCollector, i, renderState, f, g);
	}
}
