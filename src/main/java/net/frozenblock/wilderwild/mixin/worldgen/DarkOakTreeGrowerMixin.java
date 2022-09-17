package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.world.feature.WilderTreeConfigured;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.DarkOakTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = DarkOakTreeGrower.class, priority = 69420)
public class DarkOakTreeGrowerMixin {
    @Shadow @Final
    private float tallProbability;

    @Inject(method = "getConfiguredMegaFeature", at = @At("RETURN"), cancellable = true)
    public void getConfiguredMegaFeature(RandomSource randomSource, CallbackInfoReturnable<Holder<? extends ConfiguredFeature<?, ?>>> cir) {
        cir.setReturnValue(randomSource.nextFloat() > 0.3F ? TreeFeatures.DARK_OAK : WilderTreeConfigured.NEW_TALL_DARK_OAK);
        cir.cancel();
    }
}