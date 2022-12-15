package net.frozenblock.wilderwild.misc.datafixer;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.fixes.References;

public class NematocystStateFix extends DataFix {

	private final String name;
	private final String blockId;
	private static final String OLD_STATE = "facing";
	private static final String DEFAULT_VALUE = "up";

	public NematocystStateFix(Schema outputSchema, String name, ResourceLocation blockId) {
		this(outputSchema, name, blockId.toString());
	}

	private NematocystStateFix(Schema outputSchema, String name, String blockId) {
		super(outputSchema, false);
		this.name = name;
		this.blockId = blockId;
	}

	private Dynamic<?> fix(Dynamic<?> dynamic) {
		Optional<String> optional = dynamic.get("Name").asString().result();
		return optional.equals(Optional.of(this.blockId)) ? dynamic.update("Properties", dynamicx -> {
			String string = dynamicx.get(OLD_STATE).asString(DEFAULT_VALUE);
			String trueDirection;
			switch (string) {
				case "down" -> trueDirection = "up";
				case "north" -> trueDirection = "south";
				case "south" -> trueDirection = "north";
				case "east" -> trueDirection = "west";
				case "west" -> trueDirection = "east";
				default -> trueDirection = "down";
			}
			return dynamicx.remove(OLD_STATE).set(trueDirection, dynamicx.createString("true"));
		}) : dynamic;
	}

	@Override
	protected TypeRewriteRule makeRule() {
		return this.fixTypeEverywhereTyped(
				this.name, this.getInputSchema().getType(References.BLOCK_STATE), typed -> typed.update(DSL.remainderFinder(), this::fix)
		);
	}
}
