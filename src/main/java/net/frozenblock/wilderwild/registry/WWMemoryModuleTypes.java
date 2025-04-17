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

package net.frozenblock.wilderwild.registry;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.entity.Ostrich;
import net.frozenblock.wilderwild.entity.Penguin;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Registry;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.vehicle.Boat;
import org.jetbrains.annotations.NotNull;

public final class WWMemoryModuleTypes {
	private WWMemoryModuleTypes() {
		throw new UnsupportedOperationException("WWMemoryModuleTypes contains only static declarations.");
	}

	public static void register() {
		WWConstants.logWithModId("Registering MemoryModuleTypes for", WWConstants.UNSTABLE_LOGGING);
	}

	public static final MemoryModuleType<List<Firefly>> NEARBY_FIREFLIES = register("nearby_fireflies");
	public static final MemoryModuleType<Boolean> NATURAL = register("natural", Codec.BOOL);
	public static final MemoryModuleType<Integer> HOME_VALIDATE_COOLDOWN = register("home_validate_cooldown", Codec.INT);
	public static final MemoryModuleType<Boolean> IS_SWARM_LEADER = register("is_swarm_leader", Codec.BOOL);
	public static final MemoryModuleType<PositionTracker> SWARM_LEADER_TRACKER = register("swarm_leader_tracker");
	public static final MemoryModuleType<Boolean> IS_UNDERGROUND = register("is_underground", Codec.BOOL);
	public static final MemoryModuleType<List<Crab>> NEARBY_CRABS = register("nearby_crabs");
	public static final MemoryModuleType<Integer> HEAL_COOLDOWN_TICKS = register("heal_cooldown_ticks", Codec.INT);
	public static final MemoryModuleType<Boolean> IS_PLAYER_NEARBY = register("is_player_nearby", Codec.BOOL);
	public static final MemoryModuleType<Boolean> CAN_DIG = register("can_dig", Codec.BOOL);
	public static final MemoryModuleType<Unit> FIRST_BRAIN_TICK = register("first_brain_tick");
	public static final MemoryModuleType<List<Ostrich>> NEARBY_OSTRICHES = register("nearby_ostriches");
	public static final MemoryModuleType<List<Penguin>> NEARBY_PENGUINS = register("nearby_penguins");
	public static final MemoryModuleType<List<Penguin>> CLOSE_PENGUINS = register("close_penguins");
	public static final MemoryModuleType<Boat> TRACKED_BOAT = register("tracked_boat");
	public static final MemoryModuleType<Integer> IDLE_TIME = register("idle_time", Codec.INT);
	public static final MemoryModuleType<Integer> DIVE_TICKS = register("dive_ticks", Codec.INT);
	public static final MemoryModuleType<Unit> LAYING_DOWN = register("laying_down", Unit.CODEC);
	public static final MemoryModuleType<Unit> STANDING_UP = register("standing_up", Unit.CODEC);
	public static final MemoryModuleType<Unit> STARTING_SEARCH = register("starting_search", Unit.CODEC);
	public static final MemoryModuleType<Unit> SEARCHING_FOR_WATER = register("searching_for_water");
	public static final MemoryModuleType<GlobalPos> LAND_POS = register("land_pos", GlobalPos.CODEC);
	public static final MemoryModuleType<GlobalPos> WATER_POS = register("water_pos", GlobalPos.CODEC);
	public static final MemoryModuleType<Unit> WANTS_TO_CALL = register("wants_to_call", Unit.CODEC);
	public static final MemoryModuleType<Integer> CALL_COOLDOWN_TICKS = register("call_cooldown_ticks", Codec.INT);
	public static final MemoryModuleType<Unit> CALLING = register("calling", Unit.CODEC);
	public static final MemoryModuleType<UUID> CALLER = register("caller", UUIDUtil.CODEC);
	public static final MemoryModuleType<Unit> ESCAPING = register("escaping", Unit.CODEC);

	@NotNull
	private static <U> MemoryModuleType<U> register(String identifier, Codec<U> codec) {
		return Registry.register(BuiltInRegistries.MEMORY_MODULE_TYPE, WWConstants.id(identifier), new MemoryModuleType<>(Optional.of(codec)));
	}

	@NotNull
	private static <U> MemoryModuleType<U> register(String identifier) {
		return Registry.register(BuiltInRegistries.MEMORY_MODULE_TYPE, WWConstants.id(identifier), new MemoryModuleType<>(Optional.empty()));
	}

}
