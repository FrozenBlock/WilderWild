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

package net.frozenblock.wilderwild.datafix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import net.fabricmc.frozenblock.datafixer.api.DataFixerEntrypoint;
import net.fabricmc.frozenblock.datafixer.api.SchemaRegistry;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.util.datafix.fixes.References;

public final class WWDataFixerEntrypoint implements DataFixerEntrypoint {

	@Override
	public void onRegisterBlockEntities(SchemaRegistry registry, Schema schema) {
		registry.register(WWConstants.id("display_lantern"), () -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in(schema))));
		registry.register(WWConstants.id("hanging_tendril"), DSL::remainder);
		registry.register(WWConstants.id("scorched_block"), DSL::remainder);
		registry.register(WWConstants.id("stone_chest"), () -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in(schema))));
		registry.register(WWConstants.id("termite_mound"), DSL::remainder);
		registry.register(WWConstants.id("geyser"), DSL::remainder);
		registry.register(WWConstants.id("geothermal_vent"), DSL::remainder);
		registry.register(WWConstants.id("icicle"), DSL::remainder);
	}

	@Override
	public void onRegisterEntities(SchemaRegistry registry, Schema schema) {
		registry.register(WWConstants.id("jellyfish"), DSL::remainder);
		registry.register(WWConstants.id("ostrich"), () -> DSL.optionalFields("SaddleItem", References.ITEM_STACK.in(schema)));
		registry.register(WWConstants.id("crab"), DSL::remainder);
		registry.register(WWConstants.id("firefly"), DSL::remainder);
		registry.register(WWConstants.id("butterfly"), DSL::remainder);
		registry.register(WWConstants.id("tumbleweed"), () -> DSL.optionalFields("Items", References.ITEM_STACK.in(schema)));
		registry.register(WWConstants.id("coconut"), DSL::remainder);
		registry.register(WWConstants.id("scorched"), DSL::remainder);
		registry.register(WWConstants.id("moobloom"), DSL::remainder);
		registry.register(WWConstants.id("penguin"), DSL::remainder);
		registry.register(WWConstants.id("falling_leaves"), () -> DSL.optionalFields("LeafLitterBlock", References.BLOCK_NAME.in(schema)));
	}
}
