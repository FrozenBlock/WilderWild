package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.world.feature.WildTreeConfigured;
import net.minecraft.block.sapling.OakSaplingGenerator;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OakSaplingGenerator.class)
public class OakSaplingGeneratorMixin {

    @Inject(method = "getTreeFeature", at = @At("HEAD"), cancellable = true)
    public void getTreeFeature(Random random, boolean bees, CallbackInfoReturnable<RegistryEntry<? extends ConfiguredFeature<?, ?>>> cir) {
        if (random.nextInt(10) == 0) {
            cir.setReturnValue(bees ? WildTreeConfigured.NEW_FANCY_OAK_BEES_0004 : WildTreeConfigured.NEW_FANCY_OAK);
            cir.cancel();
        } else {
            cir.setReturnValue(bees ? WildTreeConfigured.NEW_OAK_BEES_0004 : WildTreeConfigured.NEW_OAK);
            cir.cancel();
        }
    }
}