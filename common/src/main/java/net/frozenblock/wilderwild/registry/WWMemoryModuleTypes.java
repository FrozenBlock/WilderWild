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
import java.util.UUID;
import net.frozenblock.lib.platform.api.registry.DeferredMemoryModuleType;
import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.entity.AbstractOstrich;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.entity.Penguin;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;

public final class WWMemoryModuleTypes {
	private static final DeferredRegister.MemoryModuleTypes REGISTER = DeferredRegister.createMemoryModuleTypes(WWConstants.MOD_ID);

	public static final DeferredMemoryModuleType<List<Firefly>> NEARBY_FIREFLIES = register("nearby_fireflies");
	public static final DeferredMemoryModuleType<Unit> NATURAL = register("natural", Unit.CODEC);
	public static final DeferredMemoryModuleType<Integer> HOME_VALIDATE_COOLDOWN = register("home_validate_cooldown", Codec.INT);
	public static final DeferredMemoryModuleType<Boolean> IS_SWARM_LEADER = register("is_swarm_leader", Codec.BOOL);
	public static final DeferredMemoryModuleType<PositionTracker> SWARM_LEADER_TRACKER = register("swarm_leader_tracker");
	public static final DeferredMemoryModuleType<Boolean> IS_UNDERGROUND = register("is_underground", Codec.BOOL);
	public static final DeferredMemoryModuleType<List<Crab>> NEARBY_CRABS = register("nearby_crabs");
	public static final DeferredMemoryModuleType<Integer> HEAL_COOLDOWN_TICKS = register("heal_cooldown_ticks", Codec.INT);
	public static final DeferredMemoryModuleType<Boolean> IS_PLAYER_NEARBY = register("is_player_nearby", Codec.BOOL);
	public static final DeferredMemoryModuleType<Boolean> CAN_DIG = register("can_dig", Codec.BOOL);
	public static final DeferredMemoryModuleType<Unit> FIRST_BRAIN_TICK = register("first_brain_tick");
	public static final DeferredMemoryModuleType<List<AbstractOstrich>> NEARBY_OSTRICHES = register("nearby_ostriches");
	public static final DeferredMemoryModuleType<List<Penguin>> NEARBY_PENGUINS = register("nearby_penguins");
	public static final DeferredMemoryModuleType<AbstractBoat> TRACKED_BOAT = register("tracked_boat");
	public static final DeferredMemoryModuleType<Integer> IDLE_TIME = register("idle_time", Codec.INT);
	public static final DeferredMemoryModuleType<Integer> DIVE_TICKS = register("dive_ticks", Codec.INT);
	public static final DeferredMemoryModuleType<Unit> LAYING_DOWN = register("laying_down", Unit.CODEC);
	public static final DeferredMemoryModuleType<Unit> STANDING_UP = register("standing_up", Unit.CODEC);
	public static final DeferredMemoryModuleType<Unit> STARTING_SEARCH = register("starting_search", Unit.CODEC);
	public static final DeferredMemoryModuleType<Unit> SEARCHING_FOR_WATER = register("searching_for_water");
	public static final DeferredMemoryModuleType<GlobalPos> LAND_POS = register("land_pos", GlobalPos.CODEC);
	public static final DeferredMemoryModuleType<GlobalPos> WATER_POS = register("water_pos", GlobalPos.CODEC);
	public static final DeferredMemoryModuleType<Unit> WANTS_TO_CALL = register("wants_to_call", Unit.CODEC);
	public static final DeferredMemoryModuleType<Integer> CALL_COOLDOWN_TICKS = register("call_cooldown_ticks", Codec.INT);
	public static final DeferredMemoryModuleType<Unit> CALLING = register("calling", Unit.CODEC);
	public static final DeferredMemoryModuleType<UUID> CALLER = register("caller", UUIDUtil.CODEC);
	public static final DeferredMemoryModuleType<Unit> ESCAPING = register("escaping", Unit.CODEC);

	static {
		REGISTER.register();
	}

	public static void init() {}

	private static <U> DeferredMemoryModuleType<U> register(String name, Codec<U> codec) {
		return REGISTER.register(name, codec);
	}

	private static <U> DeferredMemoryModuleType<U> register(String name) {
		return REGISTER.register(name);
	}

	private WWMemoryModuleTypes() {}
}
