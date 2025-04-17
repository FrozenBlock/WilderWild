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

package net.frozenblock.wilderwild.client.renderer.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;

@Environment(EnvType.CLIENT)
public class CrabRenderState extends LivingEntityRenderState {
	private static final ResourceLocation DEFAULT_TEXTURE = WWConstants.id("textures/entity/crab/crab.png");
	public float climbXRot;
	public float attackTime;
	public boolean isDitto;
	public final AnimationState hidingAnimationState = new AnimationState();
	public final AnimationState diggingAnimationState = new AnimationState();
	public final AnimationState emergingAnimationState = new AnimationState();
	public ResourceLocation texture = DEFAULT_TEXTURE;
}
