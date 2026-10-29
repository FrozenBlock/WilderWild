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

import net.frozenblock.wilderwild.client.renderer.entity.state.AbstractOstrichRenderState;
import net.frozenblock.wilderwild.entity.animal.ostrich.AbstractOstrich;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.world.entity.EquipmentSlot;

@ClientOnly
public abstract class AbstractOstrichRenderer<T extends AbstractOstrich, S extends AbstractOstrichRenderState, M extends EntityModel<? super S>> extends AgeableMobRenderer<T, S, M> {

	public AbstractOstrichRenderer(Context context, M adultModel, M babyModel) {
		super(context, adultModel, babyModel, 0.75F);
	}

	@Override
	public void extractRenderState(T entity, S state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		state.walkAnimationPos *= 1.65F;
		state.walkAnimationSpeed = Math.min(state.walkAnimationSpeed * 1.5F, 1F);

		state.beakAnimProgress = entity.getBeakAnimProgress(partialTicks);
		state.targetStraightProgress = entity.getTargetStraightProgress(partialTicks);
		state.saddle = entity.getItemBySlot(EquipmentSlot.SADDLE);
	}
}

