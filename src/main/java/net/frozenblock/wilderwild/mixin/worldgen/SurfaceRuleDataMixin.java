package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.world.gen.SharedWorldgen;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Pseudo
@Mixin(value = SurfaceRuleData.class, priority = 69420)
public class SurfaceRuleDataMixin {

    @ModifyVariable(method = "overworldLike", at = @At("STORE"), ordinal = 8)
    private static SurfaceRules.RuleSource injected(SurfaceRules.RuleSource ruleSource) {
        if (!WilderWild.hasTerraBlender) {
            return SurfaceRules.sequence(ruleSource, SharedWorldgen.surfaceRules());
        } else {
            return ruleSource;
        }
    }
}

//6217428885497005581