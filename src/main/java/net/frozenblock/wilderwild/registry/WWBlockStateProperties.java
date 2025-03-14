/*
 * Copyright 2023-2025 FrozenBlock
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

import net.frozenblock.wilderwild.block.state.properties.BubbleDirection;
import net.frozenblock.wilderwild.block.state.properties.GeyserStage;
import net.frozenblock.wilderwild.block.state.properties.GeyserType;
import net.frozenblock.wilderwild.block.state.properties.TubeWormsPart;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.lighting.LightEngine;

public final class WWBlockStateProperties {

	// Hanging Tendril
	public static final BooleanProperty TWITCHING = BooleanProperty.create("twitching");
	public static final BooleanProperty WRINGING_OUT = BooleanProperty.create("wringing_out");

	// Echo Glass
	public static final IntegerProperty DAMAGE = IntegerProperty.create("damage", 0, 3);

	// Shelf Fungus
	public static final IntegerProperty FUNGUS_STAGE = IntegerProperty.create("shelf_fungus_stage", 1, 4);

	// Termite Mound
	public static final BooleanProperty NATURAL = BooleanProperty.create("natural"); // Also used for Geysers
	public static final BooleanProperty TERMITES_AWAKE = BooleanProperty.create("termites_awake");
	public static final BooleanProperty CAN_SPAWN_TERMITE = BooleanProperty.create("can_spawn_termites");

	// Firefly Lantern
	public static final IntegerProperty DISPLAY_LIGHT = IntegerProperty.create("display_light", 0, LightEngine.MAX_LEVEL);

	// Stone Chest
	public static final BooleanProperty HAS_SCULK = BooleanProperty.create("has_sculk");

	// Mesoglea
	public static final EnumProperty<BubbleDirection> BUBBLE_DIRECTION = EnumProperty.create("bubble_direction", BubbleDirection.class);

	// Scorched Sand
	public static final BooleanProperty CRACKED = BooleanProperty.create("cracked");

	// Geyser
	public static final EnumProperty<GeyserType> GEYSER_TYPE = EnumProperty.create("geyser_type", GeyserType.class);
	public static final EnumProperty<GeyserStage> GEYSER_STAGE = EnumProperty.create("geyser_stage", GeyserStage.class);

	// Sea Anenome
	public static final BooleanProperty GLOWING = BooleanProperty.create("glowing");

	// Tube Worms
	public static final EnumProperty<TubeWormsPart> TUBE_WORMS_PART = EnumProperty.create("part", TubeWormsPart.class);

	// Vanilla Blocks
	public static final BooleanProperty TERMITE_EDIBLE = BooleanProperty.create("termite_edible"); //Wood
	public static final IntegerProperty SNOW_LAYERS = IntegerProperty.create("snow_layers", 0, 8);

	private WWBlockStateProperties() {
		throw new UnsupportedOperationException("WWBlockStateProperties contains only static declarations.");
	}
}
