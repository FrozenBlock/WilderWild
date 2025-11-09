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

package net.frozenblock.wilderwild.mixin.client.easter;

import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.renderer.entity.layers.StellaWardenLayer;
import net.minecraft.client.model.WardenModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.WardenRenderer;
import net.minecraft.client.renderer.entity.state.WardenRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.warden.Warden;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(WardenRenderer.class)
public abstract class StellaWardenRenderer extends MobRenderer<Warden, WardenRenderState, WardenModel> {
	@Unique
	private static final Identifier WILDERWILD$STELLA_BIOLUMINESCENT_LAYER_TEXTURE = WWConstants.id("textures/entity/warden/stella_warden_bioluminescent_overlay.png");
	@Unique
	private static final Identifier WILDERWILD$STELLA_HEART_TEXTURE = WWConstants.id("textures/entity/warden/stella_warden_heart.png");
	@Unique
	private static final Identifier WILDERWILD$STELLA_TENDRILS_TEXTURE = WWConstants.id("textures/entity/warden/stella_warden_tendrils.png");
	@Unique
	private static final Identifier WILDERWILD$STELLA_PULSATING_SPOTS_1_TEXTURE = WWConstants.id("textures/entity/warden/stella_warden_pulsating_spots_1.png");
	@Unique
	private static final Identifier WILDERWILD$STELLA_PULSATING_SPOTS_2_TEXTURE = WWConstants.id("textures/entity/warden/stella_warden_pulsating_spots_2.png");

	public StellaWardenRenderer(EntityRendererProvider.Context context, WardenModel entityModel, float f) {
		super(context, entityModel, f);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void wilderWild$addStellaLayers(
		EntityRendererProvider.Context context, CallbackInfo info,
		@Local(ordinal = 0) WardenModel bioluminescentModel,
		@Local(ordinal = 1) WardenModel pulsatingSpotsModel,
		@Local(ordinal = 2) WardenModel tendrilsModel,
		@Local(ordinal = 3) WardenModel heartModel
	) {
		this.addLayer(
			new StellaWardenLayer(
				this,
				(wardenRenderState) -> WILDERWILD$STELLA_BIOLUMINESCENT_LAYER_TEXTURE,
				(warden, animationProgress) -> 1F,
				bioluminescentModel,
				RenderTypes::entityTranslucentEmissive
			)
		);
		this.addLayer(
			new StellaWardenLayer(
				this,
				(wardenRenderState) -> WILDERWILD$STELLA_PULSATING_SPOTS_1_TEXTURE,
				(warden, animationProgress) -> Math.max(0F, Mth.cos(animationProgress * 0.045F) * 0.25F),
				pulsatingSpotsModel,
				RenderTypes::entityTranslucentEmissive
			)
		);
		this.addLayer(
			new StellaWardenLayer(
				this,
				(wardenRenderState) -> WILDERWILD$STELLA_PULSATING_SPOTS_2_TEXTURE,
				(warden, animationProgress) -> Math.max(0F, Mth.cos(animationProgress * 0.045F + Mth.PI) * 0.25F),
				pulsatingSpotsModel,
				RenderTypes::entityTranslucentEmissive
			)
		);
		this.addLayer(
			new StellaWardenLayer(
				this,
				(wardenRenderState) -> WILDERWILD$STELLA_TENDRILS_TEXTURE,
				(warden, animationProgress) -> warden.tendrilAnimation,
				tendrilsModel,
				RenderTypes::entityTranslucentEmissive
			)
		);
		this.addLayer(
			new StellaWardenLayer(
				this,
				(wardenRenderState) -> WILDERWILD$STELLA_HEART_TEXTURE,
				(warden, animationProgress) -> warden.heartAnimation,
				heartModel,
				RenderTypes::entityTranslucentEmissive
			)
		);
	}
}
