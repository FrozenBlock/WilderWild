package net.frozenblock.wilderwild.misc.datafixer;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.fixes.CauldronRenameFix;
import net.minecraft.util.datafix.fixes.References;

import java.util.Map;
import java.util.Optional;

public class DisplayLanternRenameFix extends DataFix {

    public DisplayLanternRenameFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType);
    }

    private static Dynamic<?> fix(Dynamic<?> dynamic) {
        Optional<String> optional = dynamic.get("Name").asString().result();
        return optional.equals(Optional.of("wilderwild:display_lantern")) ? dynamic.update("Properties", dynamicx -> {
            String string = dynamicx.get("light").asString("0");
            return dynamicx.remove("light").set("display_light", dynamicx.createString(string));
        }) : dynamic;
    }

    @Override
    protected TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped(
                "display_lantern_rename_fix", this.getInputSchema().getType(References.BLOCK_STATE), typed -> typed.update(DSL.remainderFinder(), DisplayLanternRenameFix::fix)
        );
    }
}
