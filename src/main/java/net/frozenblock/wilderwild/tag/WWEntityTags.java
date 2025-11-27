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

package net.frozenblock.wilderwild.tag;

import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public final class WWEntityTags {
	public static final TagKey<EntityType<?>> CAN_SWIM_IN_ALGAE = bind("can_swim_in_algae");
	public static final TagKey<EntityType<?>> STAYS_IN_MESOGLEA = bind("stays_in_mesoglea");
	public static final TagKey<EntityType<?>> JELLYFISH_CANT_STING = bind("jellyfish_cant_sting");
	public static final TagKey<EntityType<?>> CRAB_HUNT_TARGETS = bind("crab_hunt_targets");
	public static final TagKey<EntityType<?>> PENGUIN_HUNT_TARGETS = bind("penguin_hunt_targets");
	public static final TagKey<EntityType<?>> LEAF_PARTICLES_FRANTIC_SPAWN = bind("leaf_particles_frantic_spawn");
	public static final TagKey<EntityType<?>> COCONUT_CANT_BONK = bind("coconut_cant_bonk");
	public static final TagKey<EntityType<?>> COCONUT_CANT_SPLIT = bind("coconut_cant_split");
	public static final TagKey<EntityType<?>> TUMBLEWEED_PASSES_THROUGH = bind("tumbleweed_passes_through");
	public static final TagKey<EntityType<?>> GEYSER_PUSHES_FURTHER = bind("geyser_pushes_further");
	public static final TagKey<EntityType<?>> GEYSER_CANNOT_PUSH = bind("geyser_cannot_push");
	public static final TagKey<EntityType<?>> FRAGILE_ICE_UNWALKABLE_MOBS = bind("fragile_ice_unwalkable_mobs");
	public static final TagKey<EntityType<?>> FRAGILE_ICE_DOESNT_CRACK_ON_FALL = bind("fragile_ice_doesnt_crack_on_fall");
	public static final TagKey<EntityType<?>> FRAGILE_ICE_DOESNT_CRACK_PROJECTILE = bind("fragile_ice_doesnt_crack_projectile");

	private WWEntityTags() {
		throw new UnsupportedOperationException("WWEntityTags contains only static declarations.");
	}

	private static TagKey<EntityType<?>> bind(String path) {
		return TagKey.create(Registries.ENTITY_TYPE, WWConstants.id(path));
	}
}
