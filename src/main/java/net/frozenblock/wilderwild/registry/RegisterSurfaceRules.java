package net.frozenblock.wilderwild.registry;

import net.frozenblock.lib.FrozenBools;
import net.frozenblock.lib.worldgen.surface.api.FrozenPresetBoundRuleSource;
import net.frozenblock.lib.worldgen.surface.api.FrozenSurfaceRuleEntrypoint;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.frozenblock.wilderwild.world.generation.WilderSharedWorldgen;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.qsl.frozenblock.worldgen.surface_rule.api.SurfaceRuleContext;
import org.quiltmc.qsl.frozenblock.worldgen.surface_rule.api.SurfaceRuleEvents;
import java.util.ArrayList;

public final class RegisterSurfaceRules implements FrozenSurfaceRuleEntrypoint {

	/*@Override
	public void modifyOverworldRules(SurfaceRuleContext.@NotNull Overworld context) {
		if (!FrozenBools.HAS_TERRABLENDER) {
			context.ruleSources().add(0, WilderSharedWorldgen.cypressSurfaceRules());
			context.ruleSources().add(WilderSharedWorldgen.cypressSurfaceRules());
			if (ClothConfigInteractionHandler.betaBeaches()) {
				context.ruleSources().add(0, WilderSharedWorldgen.betaBeaches());
				context.ruleSources().add(WilderSharedWorldgen.betaBeaches());
			}
			WilderSharedConstants.log("Wilder Wild's Overworld Surface Rules have been added!", WilderSharedConstants.UNSTABLE_LOGGING);
		}
	}*/

	// SPONGEBOB
    /*@Override
    public void modifyNetherRules(SurfaceRuleContext.@NotNull Nether context) {
        /*context.materialRules().clear();
        context.materialRules().add(0, FrozenSurfaceRules.makeStateRule(Blocks.SPONGE));
        WilderSharedConstants.log("SPONGEBOB", WilderSharedConstants.UNSTABLE_LOGGING);
    }*/

	@Override
	public void addOverworldSurfaceRules(ArrayList<SurfaceRules.RuleSource> context) {
		context.add(WilderSharedWorldgen.surfaceRules());
		WilderSharedConstants.log("Wilder Wild's Overworld Surface Rules have been added!", true);
	}

	@Override
	public void addNetherSurfaceRules(ArrayList<SurfaceRules.RuleSource> context) {

	}

	@Override
	public void addEndSurfaceRules(ArrayList<SurfaceRules.RuleSource> context) {

	}

	@Override
	public void addSurfaceRules(ArrayList<FrozenPresetBoundRuleSource> context) {

	}
}
