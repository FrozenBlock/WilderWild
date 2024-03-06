package net.frozenblock.wilderwild.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import java.util.Map;
import java.util.function.Supplier;

public class WWV18 extends NamespacedSchema {
	public WWV18(int versionKey, Schema parent) {
		super(versionKey, parent);
	}

	@Override
	public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema schema) {
		Map<String, Supplier<TypeTemplate>> map = super.registerBlockEntities(schema);
		schema.register(
			map,
			WilderSharedConstants.string("display_lantern"),
			() -> DSL.optionalFields(WilderSharedConstants.string("fireflies"), References.ENTITY_TREE.in(schema))
		);
		return map;
	}
}
