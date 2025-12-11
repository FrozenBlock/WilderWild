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

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.datafix.fixes.AddNewChoices;
import net.minecraft.util.datafix.fixes.References;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(DataFixers.class)
public class DataFixersMixin {

	@ModifyExpressionValue(
		method = "addFixers",
		at = @At(
			value = "INVOKE",
			target = "Lcom/mojang/datafixers/DataFixerBuilder;addSchema(ILjava/util/function/BiFunction;)Lcom/mojang/datafixers/schemas/Schema;",
			ordinal = 0,
			remap = false
		),
		slice = @Slice(
			from = @At(
				value = "CONSTANT",
				args = "intValue=3438"
			)
		)
	)
	private static Schema wilderWild$addFixers3438(Schema schema, DataFixerBuilder builder) {
		builder.addFixer(new AddNewChoices(schema, WWConstants.string("display_lantern"), References.BLOCK_ENTITY));
		builder.addFixer(new AddNewChoices(schema, WWConstants.string("hanging_tendril"), References.BLOCK_ENTITY));
		builder.addFixer(new AddNewChoices(schema, WWConstants.string("scorched_block"), References.BLOCK_ENTITY));
		builder.addFixer(new AddNewChoices(schema, WWConstants.string("stone_chest"), References.BLOCK_ENTITY));
		builder.addFixer(new AddNewChoices(schema, WWConstants.string("termite_mound"), References.BLOCK_ENTITY));
		builder.addFixer(new AddNewChoices(schema, WWConstants.string("geyser"), References.BLOCK_ENTITY));
		builder.addFixer(new AddNewChoices(schema, WWConstants.string("icicle"), References.BLOCK_ENTITY));
		return schema;
	}

	@ModifyExpressionValue(
		method = "addFixers",
		at = @At(
			value = "INVOKE",
			target = "Lcom/mojang/datafixers/DataFixerBuilder;addSchema(ILjava/util/function/BiFunction;)Lcom/mojang/datafixers/schemas/Schema;",
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "CONSTANT",
				args = "intValue=3328"
			)
		),
		remap = false
	)
	private static Schema wilderWild$addFixers3328(Schema schema, DataFixerBuilder builder) {
		builder.addFixer(new AddNewChoices(schema, WWConstants.string("jellyfish"), References.ENTITY));
		builder.addFixer(new AddNewChoices(schema, WWConstants.string("ostrich"), References.ENTITY));
		builder.addFixer(new AddNewChoices(schema, WWConstants.string("crab"), References.ENTITY));
		builder.addFixer(new AddNewChoices(schema, WWConstants.string("firefly"), References.ENTITY));
		builder.addFixer(new AddNewChoices(schema, WWConstants.string("butterfly"), References.ENTITY));
		builder.addFixer(new AddNewChoices(schema, WWConstants.string("tumbleweed"), References.ENTITY));
		builder.addFixer(new AddNewChoices(schema, WWConstants.string("coconut"), References.ENTITY));
		builder.addFixer(new AddNewChoices(schema, WWConstants.string("scorched"), References.ENTITY));
		builder.addFixer(new AddNewChoices(schema, WWConstants.string("moobloom"), References.ENTITY));
		builder.addFixer(new AddNewChoices(schema, WWConstants.string("penguin"), References.ENTITY));
		builder.addFixer(new AddNewChoices(schema, WWConstants.string("falling_leaves"), References.ENTITY));
		return schema;
	}

}
