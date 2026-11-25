/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.client.harpoon;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.wilderwild.client.renderer.entity.state.HarpoonRenderState;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.particle.ItemPickupParticle;
import net.minecraft.client.particle.ItemPickupParticleGroup;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@ClientOnly
@Mixin(ItemPickupParticleGroup.ParticleInstance.class)
public class ItemPickupParticleGroupParticleInstanceMixin {

	@ModifyReturnValue(method = "fromParticle", at = @At("RETURN"))
	private static ItemPickupParticleGroup.ParticleInstance wilderWild$fixHarpoonLeashStates(
		ItemPickupParticleGroup.ParticleInstance original,
		@Local(argsOnly = true) ItemPickupParticle particle,
		@Local(name = "time") float time,
		@Local(name = "xt") double xt,
		@Local(name = "yt") double yt,
		@Local(name = "zt") double zt
	) {
		if (original.itemRenderState() instanceof HarpoonRenderState harpoonState && harpoonState.leashStates != null) {
			for (EntityRenderState.LeashState leashState : harpoonState.leashStates) {
				leashState.end = new Vec3(
					Mth.lerp(time, xt, particle.itemRenderState.x),
					Mth.lerp(time, yt, particle.itemRenderState.y),
					Mth.lerp(time, zt, particle.itemRenderState.z)
				);
			}
		}
		return original;
	}
}
