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

package net.frozenblock.wilderwild.mixin.client.warden;

import net.frozenblock.wilderwild.client.animation.definitions.impl.WilderWarden;
import net.frozenblock.wilderwild.entity.impl.SwimmingWardenState;
import net.minecraft.client.renderer.entity.state.WardenRenderState;
import net.minecraft.world.entity.AnimationState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(WardenRenderState.class)
public class WardenRenderStateMixin implements WilderWarden, SwimmingWardenState {

	@Unique
	private final AnimationState wilderWild$dyingAnimationState = new AnimationState();
	@Unique
	private final AnimationState wilderWild$swimmingDyingAnimationState = new AnimationState();
	@Unique
	private final AnimationState wilderWild$kirbyDeathAnimationState = new AnimationState();
	@Unique
	private boolean wilderWild$isStella;
	@Unique
	private int wilderWild$deathTicks;
	@Unique
	private float wilderWild$swimAmount;
	@Unique
	private float wilderWild$wadingProgress;

	@Unique
	@Override
	public AnimationState wilderWild$dyingAnimationState() {
		return this.wilderWild$dyingAnimationState;
	}

	@Unique
	@Override
	public AnimationState wilderWild$swimmingDyingAnimationState() {
		return this.wilderWild$swimmingDyingAnimationState;
	}

	@Unique
	@Override
	public AnimationState wilderWild$kirbyDeathAnimationState() {
		return this.wilderWild$kirbyDeathAnimationState;
	}

	@Unique
	@Override
	public boolean wilderWild$isStella() {
		return this.wilderWild$isStella;
	}

	@Unique
	@Override
	public void wilderWild$setStella(boolean isStella) {
		this.wilderWild$isStella = isStella;
	}

	@Unique
	@Override
	public void wilderWild$setDeathTicks(int i) {
		this.wilderWild$deathTicks = i;
	}

	@Unique
	@Override
	public int wilderWild$getDeathTicks() {
		return this.wilderWild$deathTicks;
	}

	@Unique
	@Override
	public float wilderWild$getSwimAmount() {
		return this.wilderWild$swimAmount;
	}

	@Unique
	@Override
	public float wilderWild$getWadingProgress() {
		return this.wilderWild$wadingProgress;
	}

	@Unique
	@Override
	public void wilderWild$setSwimAmount(float amount) {
		this.wilderWild$swimAmount = amount;
	}

	@Unique
	@Override
	public void wilderWild$setWadingProgress(float progress) {
		this.wilderWild$wadingProgress = progress;
	}
}
