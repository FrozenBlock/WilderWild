package net.frozenblock.wilderwild.misc.mod_compat;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.misc.FireflyColor;
import net.frozenblock.wilderwild.misc.WilderEnumValues;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class TerralithModIntegration extends ModIntegration {
    public TerralithModIntegration() {
        super("terralith");
    }

    @Override
    public void init() {
        Firefly.FireflyBiomeColorRegistry.addBiomeColor(id("cave/frostfire_caves"), FireflyColor.BLUE);
        Firefly.FireflyBiomeColorRegistry.addBiomeColor(id("cave/frostfire_caves"), FireflyColor.LIGHT_BLUE);

        Firefly.FireflyBiomeColorRegistry.addBiomeColor(id("cave/thermal_caves"), FireflyColor.RED);
        Firefly.FireflyBiomeColorRegistry.addBiomeColor(id("cave/thermal_caves"), FireflyColor.ORANGE);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(ResourceKey.create(Registry.BIOME_REGISTRY, id("cave/underground_jungle"))),
                WilderEnumValues.FIREFLIES, RegisterEntities.FIREFLY, 12, 2, 4);
    }
}
