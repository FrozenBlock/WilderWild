package net.frozenblock.wilderwild.registry;

import java.util.ArrayList;
import net.frozenblock.lib.worldgen.surface.api.FrozenSurfaceRuleEntrypoint;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.world.generation.WilderSharedWorldgen;
import net.minecraft.world.level.levelgen.SurfaceRules;

public final class RegisterSurfaceRules implements FrozenSurfaceRuleEntrypoint {

	@Override
	public void addOverworldSurfaceRules(ArrayList<SurfaceRules.RuleSource> context) {
		context.add(WilderSharedWorldgen.betaBeaches());
		context.add(WilderSharedWorldgen.cypressSurfaceRules());
		context.add(WilderSharedWorldgen.warmRiverRules());
		context.add(WilderSharedWorldgen.oasisRules());
		context.add(WilderSharedWorldgen.aridGrass());
		context.add(WilderSharedWorldgen.aridRules());
		context.add(WilderSharedWorldgen.oldGrowthSnowyTaigaRules());
		context.add(WilderSharedWorldgen.oldGrowthDarkForestRules());
		context.add(WilderSharedWorldgen.temperateRainforestRules());
		context.add(WilderSharedWorldgen.rainforestRules());
		WilderSharedConstants.log("Wilder Wild's Overworld Surface Rules have been added!", true);
	}

	@Override
	public void addOverworldSurfaceRulesNoPrelimSurface(ArrayList<SurfaceRules.RuleSource> context) {

	}

	@Override
	public void addNetherSurfaceRules(ArrayList<SurfaceRules.RuleSource> context) {

	}

	@Override
	public void addEndSurfaceRules(ArrayList<SurfaceRules.RuleSource> context) {

	}

	@Override
	public void addSurfaceRules(ArrayList<FrozenDimensionBoundRuleSource> context) {

	}
}
