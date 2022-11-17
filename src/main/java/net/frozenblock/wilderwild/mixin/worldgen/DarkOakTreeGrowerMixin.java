package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.world.feature.WilderTreeConfigured;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.DarkOakTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = DarkOakTreeGrower.class, priority = 69420)
public class DarkOakTreeGrowerMixin {

    @Inject(method = "getConfiguredMegaFeature", at = @At("RETURN"), cancellable = true)
    public void getConfiguredMegaFeature(RandomSource randomSource, CallbackInfoReturnable<ResourceKey<ConfiguredFeature<?, ?>>> cir) {
        if (randomSource.nextFloat() < 0.2F) {
            cir.setReturnValue(WilderTreeConfigured.TALL_DARK_OAK);
        }
    }

}
