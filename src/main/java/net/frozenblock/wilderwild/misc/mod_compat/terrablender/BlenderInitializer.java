package net.frozenblock.wilderwild.misc.mod_compat.terrablender;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.world.gen.SharedWorldgen;
import net.minecraft.world.level.levelgen.SurfaceRules;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public final class BlenderInitializer implements TerraBlenderApi {

    @Override
    public void onTerraBlenderInitialized() {
        // Set the weight to the number of biome modifications made by the mod.
        Regions.register(new WilderOverworldRegion(WilderSharedConstants.id("overworld"), 10));

        // Register our surface rules
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, WilderSharedConstants.MOD_ID, SharedWorldgen.rawSurfaceRules());
    }
}
