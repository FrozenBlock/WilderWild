package net.frozenblock.wilderwild.mixin.worldgen;

import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(DefaultBiomeFeatures.class)
public class DefaultBiomeFeaturesMixin {

    //DELETE THIS IF NO LONGER NEEDED

    /**
     * @author FrozenBlock
     * @reason new birch pog
     */
    @Overwrite
    public static void addBirchTrees(GenerationSettings.Builder builder) {
    }
}
