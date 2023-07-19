/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.client.general;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.config.EntityConfig;
import net.frozenblock.wilderwild.entity.render.animations.CustomAllayAnimations;
import net.frozenblock.wilderwild.entity.render.animations.WilderAllay;
import net.minecraft.client.model.AllayModel;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.allay.Allay;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(AllayModel.class)
public abstract class AllayModelMixin extends HierarchicalModel<Allay> implements ArmedModel {

	@Unique
	private static final float WILDERWILD$PI180 = Mth.PI / 180;
	@Unique
	private final AllayModel wilderWild$model = AllayModel.class.cast(this);
	@Shadow
	@Final
	private ModelPart head;
	@Shadow
	@Final
	private ModelPart root;

	@Inject(method = "setupAnim(Lnet/minecraft/world/entity/animal/allay/Allay;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;lerp(FFF)F"))
	private void wilderWild$setupAnim(Allay allay, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo info) {
		if (EntityConfig.get().allay.keyframeAllayDance) {
			this.root.yRot = 0.0F;
			this.root.zRot = 0.0F;
			this.head.xRot = headPitch * WILDERWILD$PI180;
			this.head.yRot = netHeadYaw * WILDERWILD$PI180;
			wilderWild$model.animate(((WilderAllay) allay).wilderWild$getDancingAnimationState(), CustomAllayAnimations.DANCING, ageInTicks);
		}
	}
}
