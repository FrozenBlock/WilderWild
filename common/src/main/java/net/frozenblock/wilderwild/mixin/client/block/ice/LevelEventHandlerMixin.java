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

package net.frozenblock.wilderwild.mixin.client.block.ice;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.wilderwild.registry.WWConfigPredicates;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelEventHandler;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@ClientOnly
@Mixin(LevelEventHandler.class)
public class LevelEventHandlerMixin {

	@Shadow
	@Final
	private ClientLevel level;

	@ModifyExpressionValue(
		method = "levelEvent",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/sounds/SoundEvents;ICICLE_LAND:Lnet/minecraft/core/Holder;",
			opcode = Opcodes.GETSTATIC
		)
	)
	private Holder<SoundEvent> wilderWild$changeIcicleLandSound(Holder<SoundEvent> original) {
		if (ConfigPredicate.lookupAndTest(this.level.registryAccess(), WWConfigPredicates.SOUND_OVERRIDE_ICICLE)) return WWSounds.BLOCK_ICICLE_LAND.asHolder();
		return original;
	}
}
