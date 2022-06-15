package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.world.feature.WildTreeConfigured;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.block.sapling.SpruceSaplingGenerator;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(SpruceSaplingGenerator.class)
public class SpruceSaplingGeneratorMixin {

    /**
     * @author FrozenBlock
     * @reason cool spruce
     */
    @Overwrite
    public RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return WildTreeConfigured.NEW_SPRUCE;
    }
}
