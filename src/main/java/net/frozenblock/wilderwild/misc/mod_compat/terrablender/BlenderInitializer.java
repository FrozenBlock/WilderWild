package net.frozenblock.wilderwild.misc.mod_compat.terrablender;

import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import terrablender.api.Regions;
import terrablender.api.TerraBlenderApi;

public final class BlenderInitializer implements TerraBlenderApi {

    @Override
    public void onTerraBlenderInitialized() {
        // Set the weight to the number of biome modifications made by the mod.
        Regions.register(new WilderOverworldRegion(WilderSharedConstants.id("overworld"), 10));
    }
}
