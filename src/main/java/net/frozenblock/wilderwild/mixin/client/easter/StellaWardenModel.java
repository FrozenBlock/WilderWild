/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.mixin.client.easter;

import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.client.animation.definitions.impl.WilderWardenModel;
import net.minecraft.client.model.WardenModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.WardenRenderState;
import net.minecraft.world.entity.monster.warden.Warden;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(WardenModel.class)
public abstract class StellaWardenModel<T extends Warden> implements WilderWardenModel {

	@Final
	@Shadow
	protected ModelPart head;

	@Final
	@Shadow
	protected ModelPart rightTendril;

	@Final
	@Shadow
	protected ModelPart leftTendril;

	@Unique
	private List<ModelPart> wilderWild$headAndTendrils;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void setHeadAndTendrils(ModelPart root, CallbackInfo info) {
		this.wilderWild$headAndTendrils = List.of(this.head, this.leftTendril, this.rightTendril);
	}

	@Override
	public List<ModelPart> wilderWild$getHeadAndTendrils(WardenRenderState renderState) {
		return this.wilderWild$headAndTendrils;
	}
}
