package net.frozenblock.wilderwild.mixin.worldgen;

import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(DefaultBiomeFeatures.class)
public class RemoveForestTreesMixin {

    //DELETE THIS IF NO LONGER NEEDED

    /**
     * @author FrozenBlock
     * @reason new birch pog
     */
    @Overwrite
    public static void addForestTrees(GenerationSettings.Builder builder) {
    }
}