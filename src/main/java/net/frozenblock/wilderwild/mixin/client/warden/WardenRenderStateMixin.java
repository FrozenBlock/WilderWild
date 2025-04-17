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
	private final AnimationState dyingAnimationState = new AnimationState();
	@Unique
	private final AnimationState swimmingDyingAnimationState = new AnimationState();
	@Unique
	private final AnimationState kirbyDeathAnimationState = new AnimationState();
	@Unique
	private boolean isStella;
	@Unique
	private int deathTicks;
	@Unique
	private float swimAmount;
	@Unique
	private float wadingProgress;

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

	@Override
	public float wilderWild$getSwimAmount() {
		return this.swimAmount;
	}

	@Override
	public float wilderWild$getWadingProgress() {
		return this.wadingProgress;
	}

	@Override
	public void wilderWild$setSwimAmount(float amount) {
		this.swimAmount = amount;
	}

	@Override
	public void wilderWild$setWadingProgress(float progress) {
		this.wadingProgress = progress;
	}
}
