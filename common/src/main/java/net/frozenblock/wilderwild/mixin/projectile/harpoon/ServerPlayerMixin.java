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

package net.frozenblock.wilderwild.mixin.projectile.harpoon;

import net.frozenblock.lib.platform.ModLoader;
import net.frozenblock.wilderwild.registry.WWAttachmentTypes;
import net.frozenblock.wilderwild.registry.WWCriteria;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

	@Shadow
	private @Nullable Vec3 startingToFallPosition;

	@Inject(
		method = "trackStartFallingPosition",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/server/level/ServerPlayer;currentExplosionImpactPos:Lnet/minecraft/world/phys/Vec3;",
			ordinal = 0,
			opcode = Opcodes.GETFIELD
		)
	)
	public void wilderWild$triggerFallAfterHarpoonGrapple(CallbackInfo info) {
		// This should never occur, since the Inject is placed after the field being set.
		if (this.startingToFallPosition == null) {
			if (ModLoader.isDevelopmentEnvironment()) throw new IllegalStateException("startingToFallPosition is null in wilderWild$triggerFallAfterHarpoonGrapple!");
			return;
		}

		final Vec3 harpoonGrappleFromGroundStartPos = WWAttachmentTypes.HARPOON_GRAPPLE_FROM_GROUND_START_POSITION.get(ServerPlayer.class.cast(this));
		if (harpoonGrappleFromGroundStartPos != null && harpoonGrappleFromGroundStartPos.y <= this.startingToFallPosition.y) {
			WWCriteria.FALL_AFTER_HARPOON_GRAPPLE_FROM_GROUND.get().trigger(ServerPlayer.class.cast(this), harpoonGrappleFromGroundStartPos);
		}

		WWAttachmentTypes.HARPOON_GRAPPLE_FROM_GROUND_START_POSITION.remove(ServerPlayer.class.cast(this));
	}
}
