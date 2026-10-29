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

import java.util.Optional;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.renderer.entity.state.SunkenRenderState;
import net.frozenblock.wilderwild.entity.monster.skeleton.Sunken;
import net.frozenblock.wilderwild.entity.monster.skeleton.SunkenVariant;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.AbstractSkeletonRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;

@ClientOnly
public class SunkenRenderer extends AbstractSkeletonRenderer<Sunken, SunkenRenderState> {

	public SunkenRenderer(Context context) {
		super(context, WWModelLayers.SUNKEN, WWModelLayers.SUNKEN_ARMOR);
	}

	@Override
	public SunkenRenderState createRenderState() {
		return new SunkenRenderState();
	}

	@Override
	public void extractRenderState(Sunken sunken, SunkenRenderState state, float partialTicks) {
		super.extractRenderState(sunken, state, partialTicks);

		final Optional<Holder<SunkenVariant>> optionalVariant = Optional.ofNullable(sunken.wilderWild$getVariant());
		if (optionalVariant.isEmpty()) return;

		final SunkenVariant variant = optionalVariant.get().value();
		final boolean eyePatch = sunken.hasEyePatch();
		state.texture = (eyePatch
			? sunken.isLeftHanded() ? variant.eyePatchLeftHandedTexture() : variant.eyePatchRightHandedTexture()
			: variant.texture()
		).texturePath();
		state.modelType = variant.model();
	}

	@Override
	protected HumanoidModel.ArmPose getArmPose(Sunken sunken, HumanoidArm arm) {
		extractCrossbowPose: {
			if (sunken.getMainArm() != arm) break extractCrossbowPose;

			final ItemStack itemStack = sunken.getMainHandItem();
			if (!itemStack.is(Items.CROSSBOW)) break extractCrossbowPose;

			if (sunken.isChargingCrossbow()) return HumanoidModel.ArmPose.CROSSBOW_CHARGE;
			if (!itemStack.getOrDefault(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY).isEmpty()) return HumanoidModel.ArmPose.CROSSBOW_HOLD;
		}

		return super.getArmPose(sunken, arm);
	}

	@Override
	public Identifier getTextureLocation(SunkenRenderState state) {
		return state.texture;
	}
}

