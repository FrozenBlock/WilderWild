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

package net.frozenblock.wilderwild.registry;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.frozenblock.lib.platform.api.registry.FrozenDeferredRegister;
import net.frozenblock.lib.platform.api.registry.FrozenHolder;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.entity.Firefly;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.vehicle.boat.Boat;

public final class WWMemoryModuleTypes {
	private static final FrozenDeferredRegister<MemoryModuleType<?>> REGISTER = FrozenDeferredRegister.create(
		Registries.MEMORY_MODULE_TYPE,
		WWConstants.MOD_ID
	);

	public static final FrozenHolder<MemoryModuleType<?>, MemoryModuleType<List<Firefly>>> NEARBY_FIREFLIES = register("nearby_fireflies");
	public static final FrozenHolder<MemoryModuleType<?>, MemoryModuleType<Unit>> NATURAL = register("natural", Unit.CODEC);
	public static final FrozenHolder<MemoryModuleType<?>, MemoryModuleType<Integer>> HOME_VALIDATE_COOLDOWN = register("home_validate_cooldown", Codec.INT);
	public static final FrozenHolder<MemoryModuleType<?>, MemoryModuleType<Boolean>> IS_SWARM_LEADER = register("is_swarm_leader", Codec.BOOL);
	public static final FrozenHolder<MemoryModuleType<?>, MemoryModuleType<PositionTracker>> SWARM_LEADER_TRACKER = register("swarm_leader_tracker");
	public static final FrozenHolder<MemoryModuleType<?>, MemoryModuleType<Boolean>> IS_UNDERGROUND = register("is_underground", Codec.BOOL);
	// TODO NEOFORGE CRAB NEARBY CRABS HERE
	public static final FrozenHolder<MemoryModuleType<?>, MemoryModuleType<Integer>> HEAL_COOLDOWN_TICKS = register("heal_cooldown_ticks", Codec.INT);
	public static final FrozenHolder<MemoryModuleType<?>, MemoryModuleType<Boolean>> IS_PLAYER_NEARBY = register("is_player_nearby", Codec.BOOL);
	public static final FrozenHolder<MemoryModuleType<?>, MemoryModuleType<Boolean>> CAN_DIG = register("can_dig", Codec.BOOL);
	public static final FrozenHolder<MemoryModuleType<?>, MemoryModuleType<Unit>> FIRST_BRAIN_TICK = register("first_brain_tick");
	//TODO NEOFORGE OSTRICH NEARBY OSTRICHES HERE
	//TODO NEOFORGE PENGUIN NEARBY PENGUINS HERE
	public static final FrozenHolder<MemoryModuleType<?>, MemoryModuleType<Boat>> TRACKED_BOAT = register("tracked_boat");
	public static final FrozenHolder<MemoryModuleType<?>, MemoryModuleType<Integer>> IDLE_TIME = register("idle_time", Codec.INT);
	public static final FrozenHolder<MemoryModuleType<?>, MemoryModuleType<Integer>> DIVE_TICKS = register("dive_ticks", Codec.INT);
	public static final FrozenHolder<MemoryModuleType<?>, MemoryModuleType<Unit>> LAYING_DOWN = register("laying_down", Unit.CODEC);
	public static final FrozenHolder<MemoryModuleType<?>, MemoryModuleType<Unit>> STANDING_UP = register("standing_up", Unit.CODEC);
	public static final FrozenHolder<MemoryModuleType<?>, MemoryModuleType<Unit>> STARTING_SEARCH = register("starting_search", Unit.CODEC);
	public static final FrozenHolder<MemoryModuleType<?>, MemoryModuleType<Unit>> SEARCHING_FOR_WATER = register("searching_for_water");
	public static final FrozenHolder<MemoryModuleType<?>, MemoryModuleType<GlobalPos>> LAND_POS = register("land_pos", GlobalPos.CODEC);
	public static final FrozenHolder<MemoryModuleType<?>, MemoryModuleType<GlobalPos>> WATER_POS = register("water_pos", GlobalPos.CODEC);
	public static final FrozenHolder<MemoryModuleType<?>, MemoryModuleType<Unit>> WANTS_TO_CALL = register("wants_to_call", Unit.CODEC);
	public static final FrozenHolder<MemoryModuleType<?>, MemoryModuleType<Integer>> CALL_COOLDOWN_TICKS = register("call_cooldown_ticks", Codec.INT);
	public static final FrozenHolder<MemoryModuleType<?>, MemoryModuleType<Unit>> CALLING = register("calling", Unit.CODEC);
	public static final FrozenHolder<MemoryModuleType<?>, MemoryModuleType<UUID>> CALLER = register("caller", UUIDUtil.CODEC);
	public static final FrozenHolder<MemoryModuleType<?>, MemoryModuleType<Unit>> ESCAPING = register("escaping", Unit.CODEC);

	static {
		REGISTER.register();
	}

	public static void init() {}

	private static <U> FrozenHolder<MemoryModuleType<?>, MemoryModuleType<U>> register(String name, Codec<U> codec) {
		return REGISTER.register(name, () -> new MemoryModuleType<>(Optional.of(codec)));
	}

	private static <U> FrozenHolder<MemoryModuleType<?>, MemoryModuleType<U>> register(String name) {
		return REGISTER.register(name, () -> new MemoryModuleType<>(Optional.empty()));
	}
}
