package net.frozenblock.wilderwild.mixin.worldgen;

import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(DefaultBiomeFeatures.class)
public class FeaturesMixin {

    /**
     * @author FrozenBlock
     * @reason pog
     */
    @Overwrite
    public static void addForestGrass(GenerationSettings.Builder builder) {
    }
}

