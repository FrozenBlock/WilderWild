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
import net.minecraft.util.datafix.schemas.V100;
import net.minecraft.util.datafix.schemas.V3816;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(V3816.class)
public class V3816Mixin {

	@WrapOperation(
		method = "registerEntities",
		at = @At(
			value = "INVOKE",
			target = "Lcom/mojang/datafixers/schemas/Schema;register(Ljava/util/Map;Ljava/lang/String;Ljava/util/function/Supplier;)V"
		),
		remap = false
	)
	public void wilderWild$registerEntities(Schema schema, Map<String, Supplier<TypeTemplate>> map, String name, Supplier<TypeTemplate> template, Operation<Void> original) {
		original.call(schema, map, name, template);
		schema.register(map, WilderSharedConstants.string("jellyfish"), () -> V100.equipment(schema));
		schema.register(map, WilderSharedConstants.string("ostrich"), () -> V100.equipment(schema));
		schema.register(map, WilderSharedConstants.string("crab"), () -> V100.equipment(schema));
		schema.register(map, WilderSharedConstants.string("firefly"), () -> V100.equipment(schema));
		schema.register(map, WilderSharedConstants.string("tumbleweed"),
			(string) -> DSL.optionalFields("Items", References.ITEM_STACK.in(schema), V100.equipment(schema)));
		schema.register(map, WilderSharedConstants.string("ancient_horn_vibration"), DSL::remainder);
		schema.register(map, WilderSharedConstants.string("coconut"), DSL::remainder);
		schema.register(map, WilderSharedConstants.string("chest_bubbler"), DSL::remainder);
		schema.register(map, WilderSharedConstants.string("sculk_spreader"), DSL::remainder);
	}
}
