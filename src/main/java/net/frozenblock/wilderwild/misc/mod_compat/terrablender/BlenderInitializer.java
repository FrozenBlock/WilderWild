package net.frozenblock.wilderwild.misc.mod_compat.terrablender;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.world.generation.SharedWorldgen;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public final class BlenderInitializer implements TerraBlenderApi {

    @Override
    public void onTerraBlenderInitialized() {
        // Set the weight to the number of biome modifications made by the mod.
        Regions.register(new WilderOverworldRegion(WilderWild.id("overworld"), 10));

        // Register our surface rules
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, WilderWild.MOD_ID, SharedWorldgen.rawSurfaceRules());
    }
}
