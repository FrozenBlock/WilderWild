package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.world.feature.WildTreeConfigured;
import net.minecraft.block.sapling.OakSaplingGenerator;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(OakSaplingGenerator.class)
public class OakSaplingGeneratorMixin extends SaplingGenerator {

    @Override
    public RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(AbstractRandom random, boolean bees) {
        if (random.nextInt(10) == 0) {
            return bees ? WildTreeConfigured.NEW_FANCY_OAK_BEES_0002 : WildTreeConfigured.NEW_FANCY_OAK;
        } else {
            return bees ? WildTreeConfigured.NEW_OAK_BEES_0002 : WildTreeConfigured.NEW_OAK;
        }
    }
}