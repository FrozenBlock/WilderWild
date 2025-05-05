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

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.V3328;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(V3328.class)
public class V3328Mixin {

	@WrapOperation(
		method = "registerEntities",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/datafix/schemas/NamespacedSchema;registerEntities(Lcom/mojang/datafixers/schemas/Schema;)Ljava/util/Map;",
			ordinal = 0
		)
	)
	public Map<String, Supplier<TypeTemplate>> wilderWild$registerEntities(V3328 instance, Schema schema, Operation<Map<String, Supplier<TypeTemplate>>> original) {
		Map<String, Supplier<TypeTemplate>> map = original.call(instance, schema);
		schema.registerSimple(
			map,
			WWConstants.string("jellyfish")
		);
		schema.register(
			map,
			WWConstants.string("ostrich"),
			() -> DSL.optionalFields("SaddleItem", References.ITEM_STACK.in(schema))
		);
		schema.registerSimple(
			map,
			WWConstants.string("crab")
		);
		schema.registerSimple(
			map,
			WWConstants.string("firefly")
		);
		schema.registerSimple(
			map,
			WWConstants.string("butterfly")
		);
		schema.register(
			map,
			WWConstants.string("tumbleweed"),
			(string) -> DSL.optionalFields("Items", References.ITEM_STACK.in(schema))
		);
		schema.register(
			map,
			WWConstants.string("coconut"),
			DSL::remainder
		);
		schema.register(
			map,
			WWConstants.string("scorched"),
			DSL::remainder
		);
		schema.registerSimple(
			map,
			WWConstants.string("moobloom")
		);
		schema.registerSimple(
			map,
			WWConstants.string("penguin")
		);
		schema.register(
			map,
			WWConstants.string("falling_leaves"),
			(string) -> DSL.optionalFields("LeafLitterBlock", References.BLOCK_NAME.in(schema))
		);
		return map;
	}
}
