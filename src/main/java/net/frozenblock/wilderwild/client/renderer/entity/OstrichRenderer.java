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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.WWEquipmentClientInfo;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.animal.ostrich.BabyOstrichModel;
import net.frozenblock.wilderwild.client.model.animal.ostrich.OstrichInbredModel;
import net.frozenblock.wilderwild.client.model.animal.ostrich.OstrichModel;
import net.frozenblock.wilderwild.client.renderer.entity.state.OstrichRenderState;
import net.frozenblock.wilderwild.entity.Ostrich;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.SimpleEquipmentLayer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class OstrichRenderer extends AbstractOstrichRenderer<Ostrich, OstrichRenderState, EntityModel<OstrichRenderState>> {
	private static final Identifier OSTRICH_LOCATION = WWConstants.id("textures/entity/ostrich/ostrich.png");
	private static final Identifier OSTRICH_BABY_LOCATION = WWConstants.id("textures/entity/ostrich/ostrich_baby.png");

	private final EntityModel<OstrichRenderState> normalModel;
	private final EntityModel<OstrichRenderState> normalBabyModel;
	private final EntityModel<OstrichRenderState> inbredModel;
	private final EntityModel<OstrichRenderState> inbredBabyModel;

	public OstrichRenderer(EntityRendererProvider.Context context) {
		super(context, new OstrichModel<>(context.bakeLayer(WWModelLayers.OSTRICH)), new BabyOstrichModel<>(context.bakeLayer(WWModelLayers.OSTRICH_BABY)));

		this.normalModel = this.adultModel;
		this.normalBabyModel = this.babyModel;

		this.inbredModel = new OstrichInbredModel(context.bakeLayer(WWModelLayers.OSTRICH_INBRED));
		this.inbredBabyModel = new OstrichInbredModel(context.bakeLayer(WWModelLayers.OSTRICH_BABY_INBRED));

		this.addLayer(
			new SimpleEquipmentLayer<>(
				this,
				context.getEquipmentRenderer(),
				WWEquipmentClientInfo.OSTRICH_SADDLE,
				ostrichRenderState -> ostrichRenderState.saddle,
				new OstrichModel<>(context.bakeLayer(WWModelLayers.OSTRICH_SADDLE)),
				new BabyOstrichModel<>(context.bakeLayer(WWModelLayers.OSTRICH_BABY_SADDLE))
			)
		);
	}

	@Override
	public void submit(
		OstrichRenderState renderState,
		PoseStack poseStack,
		SubmitNodeCollector collector,
		CameraRenderState camera
	) {
		if (renderState.isInbred) {
			this.adultModel = this.inbredModel;
			this.babyModel = this.inbredBabyModel;
		} else {
			this.adultModel = this.normalModel;
			this.babyModel = this.normalBabyModel;
		}
		super.submit(renderState, poseStack, collector, camera);
	}

	@Override
	public Identifier getTextureLocation(OstrichRenderState renderState) {
		if (!renderState.isInbred && renderState.isBaby) return OSTRICH_BABY_LOCATION;
		return OSTRICH_LOCATION;
	}

	@Override
	public OstrichRenderState createRenderState() {
		return new OstrichRenderState();
	}

	@Override
	public void extractRenderState(Ostrich ostrich, OstrichRenderState renderState, float partialTicks) {
		super.extractRenderState(ostrich, renderState, partialTicks);
		renderState.isInbred = ostrich.isInbred();
	}
}

