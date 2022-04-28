package net.frozenblock.wilderwild.mixin;

import net.frozenblock.wilderwild.world.feature.WildConfiguredFeatures;
import net.minecraft.block.sapling.BirchSaplingGenerator;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BirchSaplingGenerator.class)
public class BirchSaplingGeneratorMixin extends SaplingGenerator {

    @Override
    public RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(AbstractRandom random, boolean bees) {
        return WildConfiguredFeatures.NEW_BIRCH_TREE;
    }
}