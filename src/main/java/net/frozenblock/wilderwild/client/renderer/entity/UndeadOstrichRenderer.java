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

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.WWEquipmentClientInfo;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.OstrichModel;
import net.frozenblock.wilderwild.client.renderer.entity.state.AbstractOstrichRenderState;
import net.frozenblock.wilderwild.entity.ZombieOstrich;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.SimpleEquipmentLayer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class UndeadOstrichRenderer extends AbstractOstrichRenderer<ZombieOstrich, AbstractOstrichRenderState, EntityModel<AbstractOstrichRenderState>> {
	private static final Identifier ZOMBIE_OSTRICH_LOCATION = WWConstants.id("textures/entity/ostrich/ostrich_zombie.png");

	public UndeadOstrichRenderer(EntityRendererProvider.Context context) {
		super(context, new OstrichModel<>(context.bakeLayer(WWModelLayers.OSTRICH)), new OstrichModel<>(context.bakeLayer(WWModelLayers.OSTRICH_BABY)));

		this.addLayer(
			new SimpleEquipmentLayer<>(
				this,
				context.getEquipmentRenderer(),
				WWEquipmentClientInfo.OSTRICH_ZOMBIE_SADDLE,
				ostrichRenderState -> ostrichRenderState.saddle,
				new OstrichModel<>(context.bakeLayer(WWModelLayers.OSTRICH_SADDLE)),
				new OstrichModel<>(context.bakeLayer(WWModelLayers.OSTRICH_BABY_SADDLE))
			)
		);
	}

	@Override
	public void submit(
		AbstractOstrichRenderState renderState,
		PoseStack poseStack,
		SubmitNodeCollector collector,
		CameraRenderState cameraState
	) {
		super.submit(renderState, poseStack, collector, cameraState);
	}
	
	@Override
	public Identifier getTextureLocation(AbstractOstrichRenderState renderState) {
		return ZOMBIE_OSTRICH_LOCATION;
	}

	@Override
	public AbstractOstrichRenderState createRenderState() {
		return new AbstractOstrichRenderState();
	}

	@Override
	public void extractRenderState(ZombieOstrich ostrich, AbstractOstrichRenderState renderState, float partialTick) {
		super.extractRenderState(ostrich, renderState, partialTick);
	}
}

