/*
 * Copyright 2024 FrozenBlock
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

package net.frozenblock.wilderwild.datafix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import net.fabricmc.frozenblock.datafixer.api.DataFixerEntrypoint;
import net.fabricmc.frozenblock.datafixer.api.SchemaRegistry;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.V100;

public class WWDataFixerEntrypoint implements DataFixerEntrypoint {
	@Override
	public void onRegisterBlockEntities(SchemaRegistry registry, Schema schema) {
		registry.register(
			WWConstants.id("display_lantern"),
			() -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in(schema)))
		);
		registry.register(
			WWConstants.id("hanging_tendril"),
			DSL::remainder
		);
		registry.register(
			WWConstants.id("scorched_block"),
			DSL::remainder
		);
		registry.register(
			WWConstants.id("stone_chest"),
			() -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in(schema)))
		);
		registry.register(
			WWConstants.id("termite_mound"),
			DSL::remainder
		);
		registry.register(
			WWConstants.id("geyser"),
			DSL::remainder
		);
	}

	@Override
	public void onRegisterEntities(SchemaRegistry registry, Schema schema) {
		registry.register(
			WWConstants.id("jellyfish"),
			() -> V100.equipment(schema)
		);
		registry.register(
			WWConstants.id("ostrich"),
			() -> V100.equipment(schema)
		);
		registry.register(
			WWConstants.id("crab"),
			() -> V100.equipment(schema)
		);
		registry.register(
			WWConstants.id("firefly"),
			() -> V100.equipment(schema)
		);
		registry.register(
			WWConstants.id("tumbleweed"),
			(string) -> DSL.optionalFields("Items", References.ITEM_STACK.in(schema), V100.equipment(schema))
		);
		registry.register(
			WWConstants.id("coconut"),
			DSL::remainder
		);
		registry.register(
			WWConstants.id("chest_bubbler"),
			DSL::remainder
		);
		registry.register(
			WWConstants.id("sculk_spreader"),
			DSL::remainder
		);
		registry.register(
			WWConstants.id("scorched"),
			() -> V100.equipment(schema)
		);
		registry.register(
			WWConstants.id("falling_leaves"),
			(string) -> DSL.optionalFields("LeafLitterBlock", References.BLOCK_NAME.in(schema))
		);
	}
}
