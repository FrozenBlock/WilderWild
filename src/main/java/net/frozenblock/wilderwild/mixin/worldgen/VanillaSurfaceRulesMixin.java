package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.world.gen.SharedWorldgen;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Pseudo
@Mixin(value = SurfaceRuleData.class, priority = 69420)
public class VanillaSurfaceRulesMixin {

    @Shadow
    @Final
    private static SurfaceRules.RuleSource WATER;
    @Shadow
    @Final
    private static SurfaceRules.RuleSource GRAVEL;

    @Shadow
    @Final
    private static SurfaceRules.RuleSource SAND;

    @Shadow
    @Final
    private static SurfaceRules.RuleSource SANDSTONE;


    @ModifyVariable(method = "overworldLike", at = @At("STORE"), ordinal = 8)
    private static SurfaceRules.RuleSource injected(SurfaceRules.RuleSource materialRule) {
        if (!WilderWild.hasTerraBlender) {
            return SurfaceRules.sequence(materialRule, SharedWorldgen.surfaceRules());
        } else {
            return materialRule;
        }
    }
}

//6217428885497005581