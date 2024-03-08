package net.frozenblock.wilderwild.mixin.datafix;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.V3807;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(V3807.class)
public class V3807Mixin {

	@WrapOperation(
		method = "registerBlockEntities",
		at = @At(
			value = "INVOKE",
			target = "Lcom/mojang/datafixers/schemas/Schema;register(Ljava/util/Map;Ljava/lang/String;Ljava/util/function/Supplier;)V"
		),
		remap = false
	)
	public void wilderWild$registerBlockEntities(Schema schema, Map<String, Supplier<TypeTemplate>> map, String name, Supplier<TypeTemplate> template, Operation<Void> original) {
		original.call(schema, map, name, template);
		schema.register(
			map,
			WilderSharedConstants.string("display_lantern"),
			DSL::remainder
		);
		schema.register(
			map,
			WilderSharedConstants.string("hanging_tendril"),
			DSL::remainder
		);
		schema.register(
			map,
			WilderSharedConstants.string("scorched_block"),
			DSL::remainder
		);
		schema.register(
			map,
			WilderSharedConstants.string("stone_chest"),
			() -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in(schema)))
		);
		schema.register(
			map,
			WilderSharedConstants.string("termite_mound"),
			DSL::remainder
		);
	}
}
