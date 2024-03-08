package net.frozenblock.wilderwild.mixin.datafix;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.datafix.fixes.AddNewChoices;
import net.minecraft.util.datafix.fixes.References;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;
import java.util.function.BiFunction;

@Mixin(DataFixers.class)
public class DataFixersMixin {

	@WrapOperation(
		method = "addFixers",
		at = @At(
			value = "INVOKE",
			target = "Lcom/mojang/datafixers/DataFixerBuilder;addSchema(ILjava/util/function/BiFunction;)Lcom/mojang/datafixers/schemas/Schema;",
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "CONSTANT",
				args = "intValue=3807"
			)
		),
		remap = false
	)
	private static Schema wilderWild$addFixers3807(DataFixerBuilder builder, int version, BiFunction<Integer, Schema, Schema> factory, Operation<Schema> original) {
		Schema schema = original.call(builder, version, factory);
		builder.addFixer(new AddNewChoices(schema, WilderSharedConstants.string("display_lantern"), References.BLOCK_ENTITY));
		builder.addFixer(new AddNewChoices(schema, WilderSharedConstants.string("hanging_tendril"), References.BLOCK_ENTITY));
		builder.addFixer(new AddNewChoices(schema, WilderSharedConstants.string("scorched_block"), References.BLOCK_ENTITY));
		builder.addFixer(new AddNewChoices(schema, WilderSharedConstants.string("stone_chest"), References.BLOCK_ENTITY));
		builder.addFixer(new AddNewChoices(schema, WilderSharedConstants.string("termite_mound"), References.BLOCK_ENTITY));
		return schema;
	}

	@WrapOperation(
		method = "addFixers",
		at = @At(
			value = "INVOKE",
			target = "Lcom/mojang/datafixers/DataFixerBuilder;addSchema(ILjava/util/function/BiFunction;)Lcom/mojang/datafixers/schemas/Schema;",
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "CONSTANT",
				args = "intValue=3816"
			)
		),
		remap = false
	)
	private static Schema wilderWild$addFixers3816(DataFixerBuilder builder, int version, BiFunction<Integer, Schema, Schema> factory, Operation<Schema> original) {
		Schema schema = original.call(builder, version, factory);
		builder.addFixer(new AddNewChoices(schema, WilderSharedConstants.string("jellyfish"), References.ENTITY));
		builder.addFixer(new AddNewChoices(schema, WilderSharedConstants.string("ostrich"), References.ENTITY));
		builder.addFixer(new AddNewChoices(schema, WilderSharedConstants.string("crab"), References.ENTITY));
		builder.addFixer(new AddNewChoices(schema, WilderSharedConstants.string("firefly"), References.ENTITY));
		builder.addFixer(new AddNewChoices(schema, WilderSharedConstants.string("tumbleweed"), References.ENTITY));
		builder.addFixer(new AddNewChoices(schema, WilderSharedConstants.string("ancient_horn_vibration"), References.ENTITY));
		builder.addFixer(new AddNewChoices(schema, WilderSharedConstants.string("coconut"), References.ENTITY));
		builder.addFixer(new AddNewChoices(schema, WilderSharedConstants.string("chest_bubbler"), References.ENTITY));
		builder.addFixer(new AddNewChoices(schema, WilderSharedConstants.string("sculk_spreader"), References.ENTITY));
		return schema;
	}

}
