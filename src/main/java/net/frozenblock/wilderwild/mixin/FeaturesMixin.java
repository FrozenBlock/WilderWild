package net.frozenblock.wilderwild.mixin;

import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(DefaultBiomeFeatures.class)
public class FeaturesMixin {

    @Overwrite
    public static void addForestGrass(GenerationSettings.Builder builder) {
    }
}

