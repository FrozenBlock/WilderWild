package net.frozenblock.wilderwild.mixin.worldgen.general;

import net.frozenblock.wilderwild.world.feature.WilderTreeConfigured;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.OakTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = OakTreeGrower.class, priority = 69420)
public class OakTreeGrowerMixin {

    @Inject(method = "getConfiguredFeature", at = @At("RETURN"), cancellable = true)
    public void getConfiguredFeature(RandomSource random, boolean bees, CallbackInfoReturnable<ResourceKey<ConfiguredFeature<?, ?>>> cir) {
        if (random.nextInt(10) == 0) {
            cir.setReturnValue(bees ? WilderTreeConfigured.FANCY_OAK_BEES_0004 : WilderTreeConfigured.FANCY_OAK);
        } else {
            cir.setReturnValue(bees ? WilderTreeConfigured.OAK_BEES_0004 : WilderTreeConfigured.OAK);
        }
    }

}
