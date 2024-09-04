/*
 * Copyright 2023-2024 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.mixin.client.easter;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderConstants;
import net.frozenblock.wilderwild.entity.render.animation.WilderWardenModel;
import net.frozenblock.wilderwild.entity.render.layer.StellaWardenLayer;
import net.minecraft.client.model.WardenModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.WardenRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.warden.Warden;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(WardenRenderer.class)
public abstract class StellaWardenRenderer extends MobRenderer<Warden, WardenModel<Warden>> {
	@Unique
	private static final ResourceLocation WILDERWILD$STELLA_BIOLUMINESCENT_LAYER_TEXTURE = WilderConstants.id("textures/entity/warden/stella_warden_bioluminescent_overlay.png");
	@Unique
	private static final ResourceLocation WILDERWILD$STELLA_HEART_TEXTURE = WilderConstants.id("textures/entity/warden/stella_warden_heart.png");
	@Unique
	private static final ResourceLocation WILDERWILD$STELLA_TENDRILS_TEXTURE = WilderConstants.id("textures/entity/warden/stella_warden_tendrils.png");
	@Unique
	private static final ResourceLocation WILDERWILD$STELLA_PULSATING_SPOTS_1_TEXTURE = WilderConstants.id("textures/entity/warden/stella_warden_pulsating_spots_1.png");
	@Unique
	private static final ResourceLocation WILDERWILD$STELLA_PULSATING_SPOTS_2_TEXTURE = WilderConstants.id("textures/entity/warden/stella_warden_pulsating_spots_2.png");

	public StellaWardenRenderer(EntityRendererProvider.Context context, WardenModel<Warden> entityModel, float f) {
		super(context, entityModel, f);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void addStellaLayers(EntityRendererProvider.Context context, CallbackInfo info) {
		this.addLayer(
			new StellaWardenLayer<>(
				this, WILDERWILD$STELLA_BIOLUMINESCENT_LAYER_TEXTURE,
				(warden, partialTick, animationProgress) -> 1F,
				WardenModel::getBioluminescentLayerModelParts
			)
		);
		this.addLayer(
			new StellaWardenLayer<>(
				this,
				WILDERWILD$STELLA_PULSATING_SPOTS_1_TEXTURE,
				(warden, partialTick, animationProgress) -> Math.max(0F, Mth.cos(animationProgress * 0.045F) * 0.25F),
				WardenModel::getPulsatingSpotsLayerModelParts
			)
		);
		this.addLayer(
			new StellaWardenLayer<>(
				this,
				WILDERWILD$STELLA_PULSATING_SPOTS_2_TEXTURE,
				(warden, partialTick, animationProgress) -> Math.max(0F, Mth.cos(animationProgress * 0.045F + Mth.PI) * 0.25F),
				WardenModel::getPulsatingSpotsLayerModelParts
			)
		);
		this.addLayer(
			new StellaWardenLayer<>(
				this, WILDERWILD$STELLA_TENDRILS_TEXTURE, (warden, partialTick, animationProgress) -> warden.getTendrilAnimation(partialTick),
				model -> model instanceof WilderWardenModel wilderWardenModel ? wilderWardenModel.wilderWild$getHeadAndTendrils() : model.getTendrilsLayerModelParts()
			)
		);
		this.addLayer(
			new StellaWardenLayer<>(
				this, WILDERWILD$STELLA_HEART_TEXTURE, (warden, partialTick, animationProgress) -> warden.getHeartAnimation(partialTick),
				WardenModel::getHeartLayerModelParts
			)
		);
	}
}
