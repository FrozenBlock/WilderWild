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

import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;

public final class WWBlockFamilies {
	public static final BlockFamily BAOBAB_PLANKS = BlockFamilies.familyBuilder(WWBlocks.BAOBAB_PLANKS)
		.log(WWBlocks.BAOBAB_LOG)
		.strippedLog(WWBlocks.STRIPPED_BAOBAB_LOG)
		.button(WWBlocks.BAOBAB_BUTTON)
		.fence(WWBlocks.BAOBAB_FENCE)
		.fenceGate(WWBlocks.BAOBAB_FENCE_GATE)
		.hangingSign(WWBlocks.BAOBAB_HANGING_SIGN, WWBlocks.BAOBAB_WALL_HANGING_SIGN)
		.pressurePlate(WWBlocks.BAOBAB_PRESSURE_PLATE)
		.sign(WWBlocks.BAOBAB_SIGN, WWBlocks.BAOBAB_WALL_SIGN)
		.slab(WWBlocks.BAOBAB_SLAB)
		.stairs(WWBlocks.BAOBAB_STAIRS)
		.door(WWBlocks.BAOBAB_DOOR)
		.trapdoor(WWBlocks.BAOBAB_TRAPDOOR)
		.recipeGroupPrefix("wooden")
		.recipeUnlockedBy("has_planks")
		.getFamily();
	public static final BlockFamily WILLOW_PLANKS = BlockFamilies.familyBuilder(WWBlocks.WILLOW_PLANKS)
		.log(WWBlocks.WILLOW_LOG)
		.strippedLog(WWBlocks.STRIPPED_WILLOW_LOG)
		.button(WWBlocks.WILLOW_BUTTON)
		.fence(WWBlocks.WILLOW_FENCE)
		.fenceGate(WWBlocks.WILLOW_FENCE_GATE)
		.hangingSign(WWBlocks.WILLOW_HANGING_SIGN, WWBlocks.WILLOW_WALL_HANGING_SIGN)
		.pressurePlate(WWBlocks.WILLOW_PRESSURE_PLATE)
		.sign(WWBlocks.WILLOW_SIGN, WWBlocks.WILLOW_WALL_SIGN)
		.slab(WWBlocks.WILLOW_SLAB)
		.stairs(WWBlocks.WILLOW_STAIRS)
		.door(WWBlocks.WILLOW_DOOR)
		.trapdoor(WWBlocks.WILLOW_TRAPDOOR)
		.recipeGroupPrefix("wooden")
		.recipeUnlockedBy("has_planks")
		.getFamily();
	public static final BlockFamily CYPRESS_PLANKS = BlockFamilies.familyBuilder(WWBlocks.CYPRESS_PLANKS)
		.log(WWBlocks.CYPRESS_LOG)
		.strippedLog(WWBlocks.STRIPPED_CYPRESS_LOG)
		.button(WWBlocks.CYPRESS_BUTTON)
		.fence(WWBlocks.CYPRESS_FENCE)
		.fenceGate(WWBlocks.CYPRESS_FENCE_GATE)
		.hangingSign(WWBlocks.CYPRESS_HANGING_SIGN, WWBlocks.CYPRESS_WALL_HANGING_SIGN)
		.pressurePlate(WWBlocks.CYPRESS_PRESSURE_PLATE)
		.sign(WWBlocks.CYPRESS_SIGN, WWBlocks.CYPRESS_WALL_SIGN)
		.slab(WWBlocks.CYPRESS_SLAB)
		.stairs(WWBlocks.CYPRESS_STAIRS)
		.door(WWBlocks.CYPRESS_DOOR)
		.trapdoor(WWBlocks.CYPRESS_TRAPDOOR)
		.recipeGroupPrefix("wooden")
		.recipeUnlockedBy("has_planks")
		.getFamily();
	public static final BlockFamily PALM_PLANKS = BlockFamilies.familyBuilder(WWBlocks.PALM_PLANKS)
		.log(WWBlocks.PALM_LOG)
		.strippedLog(WWBlocks.STRIPPED_PALM_LOG)
		.button(WWBlocks.PALM_BUTTON)
		.fence(WWBlocks.PALM_FENCE)
		.fenceGate(WWBlocks.PALM_FENCE_GATE)
		.hangingSign(WWBlocks.PALM_HANGING_SIGN, WWBlocks.PALM_WALL_HANGING_SIGN)
		.pressurePlate(WWBlocks.PALM_PRESSURE_PLATE)
		.sign(WWBlocks.PALM_SIGN, WWBlocks.PALM_WALL_SIGN)
		.slab(WWBlocks.PALM_SLAB)
		.stairs(WWBlocks.PALM_STAIRS)
		.door(WWBlocks.PALM_DOOR)
		.trapdoor(WWBlocks.PALM_TRAPDOOR)
		.recipeGroupPrefix("wooden")
		.recipeUnlockedBy("has_planks")
		.getFamily();
	public static final BlockFamily MAPLE_PLANKS = BlockFamilies.familyBuilder(WWBlocks.MAPLE_PLANKS)
		.log(WWBlocks.MAPLE_LOG)
		.strippedLog(WWBlocks.STRIPPED_MAPLE_LOG)
		.button(WWBlocks.MAPLE_BUTTON)
		.fence(WWBlocks.MAPLE_FENCE)
		.fenceGate(WWBlocks.MAPLE_FENCE_GATE)
		.hangingSign(WWBlocks.MAPLE_HANGING_SIGN, WWBlocks.MAPLE_WALL_HANGING_SIGN)
		.pressurePlate(WWBlocks.MAPLE_PRESSURE_PLATE)
		.sign(WWBlocks.MAPLE_SIGN, WWBlocks.MAPLE_WALL_SIGN)
		.slab(WWBlocks.MAPLE_SLAB)
		.stairs(WWBlocks.MAPLE_STAIRS)
		.door(WWBlocks.MAPLE_DOOR)
		.trapdoor(WWBlocks.MAPLE_TRAPDOOR)
		.recipeGroupPrefix("wooden")
		.recipeUnlockedBy("has_planks")
		.getFamily();
}
