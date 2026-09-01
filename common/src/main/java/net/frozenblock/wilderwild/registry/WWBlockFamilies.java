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
	public static final BlockFamily MOSSY_MUD_BRICK = BlockFamilies.familyBuilder(WWBlocks.MOSSY_MUD_BRICKS.get())
		.stairs(WWBlocks.MOSSY_MUD_BRICK_STAIRS.get())
		.slab(WWBlocks.MOSSY_MUD_BRICK_SLAB.get())
		.wall(WWBlocks.MOSSY_MUD_BRICK_WALL.get())
		.getFamily();

	public static final BlockFamily GABBRO = BlockFamilies.familyBuilder(WWBlocks.GABBRO.get())
		.stairs(WWBlocks.GABBRO_STAIRS.get())
		.slab(WWBlocks.GABBRO_SLAB.get())
		.wall(WWBlocks.GABBRO_WALL.get())
		.polished(WWBlocks.POLISHED_GABBRO.get())
		.dontGenerateModel()
		.getFamily();
	public static final BlockFamily POLISHED_GABBRO = BlockFamilies.familyBuilder(WWBlocks.POLISHED_GABBRO.get())
		.stairs(WWBlocks.POLISHED_GABBRO_STAIRS.get())
		.slab(WWBlocks.POLISHED_GABBRO_SLAB.get())
		.wall(WWBlocks.POLISHED_GABBRO_WALL.get())
		.getFamily();
	public static final BlockFamily GABBRO_BRICK = BlockFamilies.familyBuilder(WWBlocks.GABBRO_BRICKS.get())
		.stairs(WWBlocks.GABBRO_BRICK_STAIRS.get())
		.slab(WWBlocks.GABBRO_BRICK_SLAB.get())
		.wall(WWBlocks.GABBRO_BRICK_WALL.get())
		.cracked(WWBlocks.CRACKED_GABBRO_BRICKS.get())
		.chiseled(WWBlocks.CHISELED_GABBRO_BRICKS.get())
		.getFamily();
	public static final BlockFamily MOSSY_GABBRO_BRICK = BlockFamilies.familyBuilder(WWBlocks.MOSSY_GABBRO_BRICKS.get())
		.stairs(WWBlocks.MOSSY_GABBRO_BRICK_STAIRS.get())
		.slab(WWBlocks.MOSSY_GABBRO_BRICK_SLAB.get())
		.wall(WWBlocks.MOSSY_GABBRO_BRICK_WALL.get())
		.getFamily();

	public static final BlockFamily BAOBAB_PLANKS = BlockFamilies.familyBuilder(WWBlocks.BAOBAB_PLANKS.get())
		.log(WWBlocks.BAOBAB_LOG.get())
		.strippedLog(WWBlocks.STRIPPED_BAOBAB_LOG.get())
		.button(WWBlocks.BAOBAB_BUTTON.get())
		.fence(WWBlocks.BAOBAB_FENCE.get())
		.fenceGate(WWBlocks.BAOBAB_FENCE_GATE.get())
		.hangingSign(WWBlocks.BAOBAB_HANGING_SIGN.get(), WWBlocks.BAOBAB_WALL_HANGING_SIGN.get())
		.pressurePlate(WWBlocks.BAOBAB_PRESSURE_PLATE.get())
		.sign(WWBlocks.BAOBAB_SIGN.get(), WWBlocks.BAOBAB_WALL_SIGN.get())
		.slab(WWBlocks.BAOBAB_SLAB.get())
		.stairs(WWBlocks.BAOBAB_STAIRS.get())
		.door(WWBlocks.BAOBAB_DOOR.get())
		.trapdoor(WWBlocks.BAOBAB_TRAPDOOR.get())
		.recipeGroupPrefix("wooden")
		.recipeUnlockedBy("has_planks")
		.getFamily();
	public static final BlockFamily WILLOW_PLANKS = BlockFamilies.familyBuilder(WWBlocks.WILLOW_PLANKS.get())
		.log(WWBlocks.WILLOW_LOG.get())
		.strippedLog(WWBlocks.STRIPPED_WILLOW_LOG.get())
		.button(WWBlocks.WILLOW_BUTTON.get())
		.fence(WWBlocks.WILLOW_FENCE.get())
		.fenceGate(WWBlocks.WILLOW_FENCE_GATE.get())
		.hangingSign(WWBlocks.WILLOW_HANGING_SIGN.get(), WWBlocks.WILLOW_WALL_HANGING_SIGN.get())
		.pressurePlate(WWBlocks.WILLOW_PRESSURE_PLATE.get())
		.sign(WWBlocks.WILLOW_SIGN.get(), WWBlocks.WILLOW_WALL_SIGN.get())
		.slab(WWBlocks.WILLOW_SLAB.get())
		.stairs(WWBlocks.WILLOW_STAIRS.get())
		.door(WWBlocks.WILLOW_DOOR.get())
		.trapdoor(WWBlocks.WILLOW_TRAPDOOR.get())
		.recipeGroupPrefix("wooden")
		.recipeUnlockedBy("has_planks")
		.getFamily();
	public static final BlockFamily CYPRESS_PLANKS = BlockFamilies.familyBuilder(WWBlocks.CYPRESS_PLANKS.get())
		.log(WWBlocks.CYPRESS_LOG.get())
		.strippedLog(WWBlocks.STRIPPED_CYPRESS_LOG.get())
		.button(WWBlocks.CYPRESS_BUTTON.get())
		.fence(WWBlocks.CYPRESS_FENCE.get())
		.fenceGate(WWBlocks.CYPRESS_FENCE_GATE.get())
		.hangingSign(WWBlocks.CYPRESS_HANGING_SIGN.get(), WWBlocks.CYPRESS_WALL_HANGING_SIGN.get())
		.pressurePlate(WWBlocks.CYPRESS_PRESSURE_PLATE.get())
		.sign(WWBlocks.CYPRESS_SIGN.get(), WWBlocks.CYPRESS_WALL_SIGN.get())
		.slab(WWBlocks.CYPRESS_SLAB.get())
		.stairs(WWBlocks.CYPRESS_STAIRS.get())
		.door(WWBlocks.CYPRESS_DOOR.get())
		.trapdoor(WWBlocks.CYPRESS_TRAPDOOR.get())
		.recipeGroupPrefix("wooden")
		.recipeUnlockedBy("has_planks")
		.getFamily();
	public static final BlockFamily PALM_PLANKS = BlockFamilies.familyBuilder(WWBlocks.PALM_PLANKS.get())
		.log(WWBlocks.PALM_LOG.get())
		.strippedLog(WWBlocks.STRIPPED_PALM_LOG.get())
		.button(WWBlocks.PALM_BUTTON.get())
		.fence(WWBlocks.PALM_FENCE.get())
		.fenceGate(WWBlocks.PALM_FENCE_GATE.get())
		.hangingSign(WWBlocks.PALM_HANGING_SIGN.get(), WWBlocks.PALM_WALL_HANGING_SIGN.get())
		.pressurePlate(WWBlocks.PALM_PRESSURE_PLATE.get())
		.sign(WWBlocks.PALM_SIGN.get(), WWBlocks.PALM_WALL_SIGN.get())
		.slab(WWBlocks.PALM_SLAB.get())
		.stairs(WWBlocks.PALM_STAIRS.get())
		.door(WWBlocks.PALM_DOOR.get())
		.trapdoor(WWBlocks.PALM_TRAPDOOR.get())
		.recipeGroupPrefix("wooden")
		.recipeUnlockedBy("has_planks")
		.getFamily();
	public static final BlockFamily MAPLE_PLANKS = BlockFamilies.familyBuilder(WWBlocks.MAPLE_PLANKS.get())
		.log(WWBlocks.MAPLE_LOG.get())
		.strippedLog(WWBlocks.STRIPPED_MAPLE_LOG.get())
		.button(WWBlocks.MAPLE_BUTTON.get())
		.fence(WWBlocks.MAPLE_FENCE.get())
		.fenceGate(WWBlocks.MAPLE_FENCE_GATE.get())
		.hangingSign(WWBlocks.MAPLE_HANGING_SIGN.get(), WWBlocks.MAPLE_WALL_HANGING_SIGN.get())
		.pressurePlate(WWBlocks.MAPLE_PRESSURE_PLATE.get())
		.sign(WWBlocks.MAPLE_SIGN.get(), WWBlocks.MAPLE_WALL_SIGN.get())
		.slab(WWBlocks.MAPLE_SLAB.get())
		.stairs(WWBlocks.MAPLE_STAIRS.get())
		.door(WWBlocks.MAPLE_DOOR.get())
		.trapdoor(WWBlocks.MAPLE_TRAPDOOR.get())
		.recipeGroupPrefix("wooden")
		.recipeUnlockedBy("has_planks")
		.getFamily();

	private WWBlockFamilies() {}
}
