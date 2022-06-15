package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.world.feature.WildTreeConfigured;
import net.minecraft.block.sapling.BirchSaplingGenerator;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BirchSaplingGenerator.class)
public class BirchSaplingGeneratorMixin {

    /**
     * @author FrozenBlock
     * @reason cool birch
     */
    @Overwrite
    public RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return WildTreeConfigured.NEW_BIRCH_BEES_0004;
    }
}