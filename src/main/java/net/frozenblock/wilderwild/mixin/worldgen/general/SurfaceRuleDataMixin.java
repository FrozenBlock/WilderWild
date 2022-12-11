package net.frozenblock.wilderwild.mixin.worldgen.general;

import net.frozenblock.lib.FrozenBools;
import net.frozenblock.wilderwild.world.generation.WilderSharedWorldgen;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = SurfaceRuleData.class, priority = 1001)
public class SurfaceRuleDataMixin {

	@ModifyVariable(method = "overworldLike", at = @At("STORE"), ordinal = 8)
	private static SurfaceRules.RuleSource overworldLike(SurfaceRules.RuleSource rule, boolean abovePreliminarySurface, boolean bedrockRoof, boolean bedrockFloor) {
		if (FrozenBools.IS_QUILT || FrozenBools.HAS_TERRABLENDER) {
			return SurfaceRules.sequence(
					WilderSharedWorldgen.cypressSurfaceRules(),
					WilderSharedWorldgen.warmRiverRules(),
					WilderSharedWorldgen.gravelBetaBeaches(),
					WilderSharedWorldgen.sandBetaBeaches(),
					WilderSharedWorldgen.multilayerSandBetaBeaches(),
					rule,
					WilderSharedWorldgen.cypressSurfaceRules(),
					WilderSharedWorldgen.warmRiverRules(),
					WilderSharedWorldgen.gravelBetaBeaches(),
					WilderSharedWorldgen.sandBetaBeaches(),
					WilderSharedWorldgen.multilayerSandBetaBeaches()
			);
		}
		return rule;
	}
}
