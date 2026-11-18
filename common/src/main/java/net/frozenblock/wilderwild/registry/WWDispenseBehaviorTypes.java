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

package net.frozenblock.wilderwild.registry;

import com.mojang.serialization.MapCodec;
import lombok.experimental.UtilityClass;
import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.dispenser.EmptyMobBottleDispenseBehavior;
import net.frozenblock.wilderwild.block.dispenser.SpawnTumbleweedDispenseBehavior;
import net.minecraft.core.dispenser.DispenseBehavior;
import net.minecraft.core.registries.Registries;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@UtilityClass
public final class WWDispenseBehaviorTypes {

	public static void init() {
		final DeferredRegister<MapCodec<? extends DispenseBehavior>> register = DeferredRegister.create(
			Registries.DISPENSE_BEHAVIOR_TYPE,
			WWConstants.MOD_ID
		);
		register.register("spawn_tumbleweed", () -> SpawnTumbleweedDispenseBehavior.MAP_CODEC);
		register.register("empty_mob_bottle", () -> EmptyMobBottleDispenseBehavior.MAP_CODEC);
		register.register();
	}
}
