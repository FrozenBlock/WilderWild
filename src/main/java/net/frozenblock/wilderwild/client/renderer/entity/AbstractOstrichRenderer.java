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

package net.frozenblock.wilderwild.client.renderer.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.client.WWEquipmentClientInfo;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.OstrichModel;
import net.frozenblock.wilderwild.client.renderer.entity.state.AbstractOstrichRenderState;
import net.frozenblock.wilderwild.entity.AbstractOstrich;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.SimpleEquipmentLayer;
import net.minecraft.world.entity.EquipmentSlot;
import org.jetbrains.annotations.NotNull;
import org.joml.Math;

@Environment(EnvType.CLIENT)
public abstract class AbstractOstrichRenderer<T extends AbstractOstrich, S extends AbstractOstrichRenderState, M extends EntityModel<? super S>> extends AgeableMobRenderer<T, S, M> {

	public AbstractOstrichRenderer(EntityRendererProvider.Context context, M adultModel, M babyModel) {
		super(context, adultModel, babyModel, 0.75F);

		this.addLayer(
			new SimpleEquipmentLayer<>(
				this,
				context.getEquipmentRenderer(),
				WWEquipmentClientInfo.OSTRICH_SADDLE,
				ostrichRenderState -> ostrichRenderState.saddle,
				new OstrichModel(context.bakeLayer(WWModelLayers.OSTRICH_SADDLE)),
				new OstrichModel(context.bakeLayer(WWModelLayers.OSTRICH_BABY_SADDLE))
			)
		);
	}

	@Override
	public void extractRenderState(@NotNull T entity, @NotNull S renderState, float partialTick) {
		super.extractRenderState(entity, renderState, partialTick);
		renderState.walkAnimationPos *= 1.65F;
		renderState.walkAnimationSpeed = Math.min(renderState.walkAnimationSpeed * 1.5F, 1F);

		renderState.beakAnimProgress = entity.getBeakAnimProgress(partialTick);
		renderState.targetStraightProgress = entity.getTargetStraightProgress(partialTick);
		renderState.saddle = entity.getItemBySlot(EquipmentSlot.SADDLE);
	}
}

