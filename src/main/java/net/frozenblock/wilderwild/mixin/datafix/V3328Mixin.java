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
import net.minecraft.util.datafix.schemas.V100;
import net.minecraft.util.datafix.schemas.V3328;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(V3328.class)
public class V3328Mixin {

	@Inject(method = "registerEntities", at = @At("RETURN"))
	public void wilderWild$registerEntities(
		Schema schema, CallbackInfoReturnable<Map<String, Supplier<TypeTemplate>>> info,
		@Local Map<String, Supplier<TypeTemplate>> map
	) {
		schema.registerSimple(
			map,
			WWConstants.string("jellyfish")
		);
		schema.register(
			map,
			WWConstants.string("ostrich"),
			() -> V100.equipment(schema)
		);
		schema.register(
			map,
			WWConstants.string("crab"),
			() -> V100.equipment(schema)
		);
		schema.register(
			map,
			WWConstants.string("firefly"),
			() -> V100.equipment(schema)
		);
		schema.register(
			map,
			WWConstants.string("butterfly"),
			() -> V100.equipment(schema)
		);
		schema.register(
			map,
			WWConstants.string("tumbleweed"),
			(string) -> DSL.optionalFields("Items", References.ITEM_STACK.in(schema), V100.equipment(schema))
		);
		schema.register(
			map,
			WWConstants.string("coconut"),
			DSL::remainder
		);
		schema.register(
			map,
			WWConstants.string("scorched"),
			() -> V100.equipment(schema)
		);
		schema.register(
			map,
			WWConstants.string("moobloom"),
			() -> V100.equipment(schema)
		);
		schema.register(
			map,
			WWConstants.string("penguin"),
			() -> V100.equipment(schema)
		);
		schema.register(
			map,
			WWConstants.string("falling_leaves"),
			(string) -> DSL.optionalFields("LeafLitterBlock", References.BLOCK_NAME.in(schema))
		);
	}
}
