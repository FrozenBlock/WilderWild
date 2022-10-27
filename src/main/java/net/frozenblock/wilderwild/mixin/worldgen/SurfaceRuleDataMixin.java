package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.lib.FrozenBools;
import net.frozenblock.wilderwild.world.gen.SharedWorldgen;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = SurfaceRuleData.class, priority = 0)
public class SurfaceRuleDataMixin {

	@ModifyVariable(method = "overworldLike", at = @At("STORE"), ordinal = 8)
	private static SurfaceRules.RuleSource overworldLike(SurfaceRules.RuleSource rule, boolean abovePreliminarySurface, boolean bedrockRoof, boolean bedrockFloor) {
		if (FrozenBools.isQuilt) {
			return SurfaceRules.sequence(
					SharedWorldgen.cypressSurfaceRules(),
					SharedWorldgen.betaBeaches(),
					rule,
					SharedWorldgen.cypressSurfaceRules(),
					SharedWorldgen.betaBeaches());
		}
		return rule;
	}
}
