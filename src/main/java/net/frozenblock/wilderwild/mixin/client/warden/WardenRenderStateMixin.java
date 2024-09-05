/*
 * Copyright 2024 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.client.warden;

import net.frozenblock.wilderwild.entity.render.animation.WilderWarden;
import net.minecraft.client.renderer.entity.state.WardenRenderState;
import net.minecraft.world.entity.AnimationState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(WardenRenderState.class)
public class WardenRenderStateMixin implements WilderWarden {

	@Unique
	private final AnimationState dyingAnimationState = new AnimationState();
	@Unique
	private final AnimationState swimmingDyingAnimationState = new AnimationState();
	@Unique
	private final AnimationState kirbyDeathAnimationState = new AnimationState();
	@Unique
	private boolean isStella;
	@Unique
	private int deathTicks;

	@Override
	public AnimationState wilderWild$getDyingAnimationState() {
		return this.dyingAnimationState;
	}

	@Override
	public AnimationState wilderWild$getSwimmingDyingAnimationState() {
		return this.swimmingDyingAnimationState;
	}

	@Override
	public AnimationState wilderWild$getKirbyDeathAnimationState() {
		return this.kirbyDeathAnimationState;
	}

	@Override
	public boolean wilderWild$isStella() {
		return this.isStella;
	}

	@Override
	public void wilderWild$setIsStella(boolean value) {
		this.isStella = value;
	}

	@Override
	public void wilderWild$setDeathTicks(int i) {
		this.deathTicks = i;
	}

	@Override
	public int wilderWild$getDeathTicks() {
		return this.deathTicks;
	}
}
