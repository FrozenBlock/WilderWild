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

package net.frozenblock.wilderwild.registry;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Optional;
import net.frozenblock.wilderwild.WilderConstants;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.entity.Ostrich;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import org.jetbrains.annotations.NotNull;

public final class RegisterMemoryModuleTypes {
	private RegisterMemoryModuleTypes() {
		throw new UnsupportedOperationException("RegisterMemoryModuleTypes contains only static declarations.");
	}

	public static void register() {
		WilderConstants.logWithModId("Registering MemoryModuleTypes for", WilderConstants.UNSTABLE_LOGGING);
	}

	public static final MemoryModuleType<Boolean> IS_UNDERGROUND = register("is_underground", Codec.BOOL);
	public static final MemoryModuleType<List<Crab>> NEARBY_CRABS = register("nearby_crabs");
	public static final MemoryModuleType<Integer> HEAL_COOLDOWN_TICKS = register("heal_cooldown_ticks", Codec.INT);
	public static final MemoryModuleType<Boolean> IS_PLAYER_NEARBY = register("is_player_nearby", Codec.BOOL);
	public static final MemoryModuleType<Boolean> CAN_DIG = register("can_dig", Codec.BOOL);
	public static final MemoryModuleType<Unit> FIRST_BRAIN_TICK = register("first_brain_tick");
	public static final MemoryModuleType<List<Ostrich>> NEARBY_OSTRICHES = register("nearby_ostriches");

	@NotNull
	private static <U> MemoryModuleType<U> register(String identifier, Codec<U> codec) {
		return Registry.register(BuiltInRegistries.MEMORY_MODULE_TYPE, WilderConstants.id(identifier), new MemoryModuleType<>(Optional.of(codec)));
	}

	@NotNull
	private static <U> MemoryModuleType<U> register(String identifier) {
		return Registry.register(BuiltInRegistries.MEMORY_MODULE_TYPE, WilderConstants.id(identifier), new MemoryModuleType<>(Optional.empty()));
	}

}
