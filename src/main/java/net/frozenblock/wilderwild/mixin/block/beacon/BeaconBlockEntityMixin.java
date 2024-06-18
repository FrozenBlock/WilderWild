/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.block.beacon;

import net.frozenblock.wilderwild.registry.RegisterMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.ArrayList;
import java.util.List;

@Mixin(BeaconBlockEntity.class)
public final class BeaconBlockEntityMixin {

	@Shadow
	@Final
	public static MobEffect[][] BEACON_EFFECTS;

	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void wilderWild$addReachBoost(CallbackInfo info) {
		ArrayList<MobEffect> effects = new ArrayList<>(List.of(BEACON_EFFECTS[2]));
		effects.add(RegisterMobEffects.REACH_BOOST);
		BEACON_EFFECTS[2] = effects.toArray(new MobEffect[0]);
	}

}
