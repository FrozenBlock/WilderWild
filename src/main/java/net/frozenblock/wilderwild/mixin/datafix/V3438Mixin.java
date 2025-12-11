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

package net.frozenblock.wilderwild.mixin.datafix;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.V3438;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(V3438.class)
public class V3438Mixin {

	@Inject(method = "registerBlockEntities", at = @At("RETURN"))
	public void wilderWild$registerBlockEntities(
		Schema schema, CallbackInfoReturnable<Map<String, Supplier<TypeTemplate>>> info,
		@Local Map<String, Supplier<TypeTemplate>> map
	) {
		schema.register(
			map,
			WWConstants.string("display_lantern"),
			() -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in(schema)))
		);
		schema.register(
			map,
			WWConstants.string("hanging_tendril"),
			DSL::remainder
		);
		schema.register(
			map,
			WWConstants.string("scorched_block"),
			DSL::remainder
		);
		schema.register(
			map,
			WWConstants.string("stone_chest"),
			() -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in(schema)))
		);
		schema.register(
			map,
			WWConstants.string("termite_mound"),
			DSL::remainder
		);
		schema.register(
			map,
			WWConstants.string("geyser"),
			DSL::remainder
		);
		schema.register(
			map,
			WWConstants.string("icicle"),
			DSL::remainder
		);
	}
}
