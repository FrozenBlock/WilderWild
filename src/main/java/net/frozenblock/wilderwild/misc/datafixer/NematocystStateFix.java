package net.frozenblock.wilderwild.misc.datafixer;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.fixes.EntityBlockStateFix;
import net.minecraft.util.datafix.fixes.References;
import java.util.Objects;
import java.util.Optional;

public class NematocystStateFix extends DataFix {

	private final String name;
	private final String blockId;
	private final String oldState;
	private final String defaultValue;

	public NematocystStateFix(Schema outputSchema, String name, ResourceLocation blockId) {
		this(outputSchema, name, blockId.toString());
	}

	private NematocystStateFix(Schema outputSchema, String name, String blockId) {
		super(outputSchema, false);
		this.name = name;
		this.blockId = blockId;
		this.oldState = "facing";
		this.defaultValue = "up";
	}

	private Dynamic<?> fix(Dynamic<?> dynamic) {
		Optional<String> optional = dynamic.get("Name").asString().result();
		return optional.equals(Optional.of(this.blockId)) ? dynamic.update("Properties", dynamicx -> {
			String string = dynamicx.get(this.oldState).asString(this.defaultValue);
			return dynamicx.remove(this.oldState).set(string, dynamicx.createString("true"));
		}) : dynamic;
	}

	@Override
	protected TypeRewriteRule makeRule() {
		return this.fixTypeEverywhereTyped(
				this.name, this.getInputSchema().getType(References.BLOCK_STATE), typed -> typed.update(DSL.remainderFinder(), this::fix)
		);
	}
}
