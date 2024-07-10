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

package net.frozenblock.wilderwild.mixin.datafix;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.frozenblock.wilderwild.WilderConstants;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.V100;
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
		schema.register(
			map,
			WilderConstants.string("jellyfish"),
			() -> V100.equipment(schema)
		);
		schema.register(
			map,
			WilderConstants.string("ostrich"),
			() -> V100.equipment(schema)
		);
		schema.register(
			map,
			WilderConstants.string("crab"),
			() -> V100.equipment(schema)
		);
		schema.register(
			map,
			WilderConstants.string("firefly"),
			() -> V100.equipment(schema)
		);
		schema.register(
			map,
			WilderConstants.string("tumbleweed"),
			(string) -> DSL.optionalFields("Items", References.ITEM_STACK.in(schema), V100.equipment(schema))
		);
		schema.register(
			map,
			WilderConstants.string("ancient_horn_vibration"),
			DSL::remainder
		);
		schema.register(
			map,
			WilderConstants.string("coconut"),
			DSL::remainder
		);
		schema.register(
			map,
			WilderConstants.string("chest_bubbler"),
			DSL::remainder
		);
		schema.register(
			map,
			WilderConstants.string("sculk_spreader"),
			DSL::remainder
		);
		schema.register(
			map,
			WilderConstants.string("scorched"),
			() -> V100.equipment(schema)
		);
		return map;
	}
}
