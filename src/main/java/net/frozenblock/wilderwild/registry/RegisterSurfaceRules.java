package net.frozenblock.wilderwild.registry;

import java.util.List;
import net.frozenblock.lib.worldgen.surface.api.SurfaceRuleEvents;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.world.generation.WilderSharedWorldgen;
import net.minecraft.world.level.levelgen.SurfaceRules;

public final class RegisterSurfaceRules implements SurfaceRuleEvents.OverworldSurfaceRuleCallback {

	@Override
	public void addOverworldSurfaceRules(List<SurfaceRules.RuleSource> context) {
		var surfaceRules = SurfaceRules.sequence(
			WilderSharedWorldgen.betaBeaches(),
			WilderSharedWorldgen.cypressSurfaceRules(),
			WilderSharedWorldgen.warmRiverRules(),
			WilderSharedWorldgen.oasisRules(),
			WilderSharedWorldgen.aridGrass(),
			WilderSharedWorldgen.aridRules(),
			WilderSharedWorldgen.oldGrowthSnowyTaigaRules(),
			WilderSharedWorldgen.oldGrowthDarkForestRules(),
			WilderSharedWorldgen.temperateRainforestRules(),
			WilderSharedWorldgen.rainforestRules()
		);

		context.add(surfaceRules);
		WilderSharedConstants.log("Wilder Wild's Overworld Surface Rules have been added!", true);
	}
}
