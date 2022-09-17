package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.world.feature.WilderTreeConfigured;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.MangroveTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MangroveTreeGrower.class, priority = 69420)
public class MangroveTreeGrowerMixin {
    @Shadow @Final
    private float tallProbability;

    @Inject(method = "getConfiguredFeature", at = @At("RETURN"), cancellable = true)
    public void getConfiguredFeature(RandomSource randomSource, boolean bl, CallbackInfoReturnable<Holder<? extends ConfiguredFeature<?, ?>>> cir) {
        if (randomSource.nextFloat() < this.tallProbability) {
            cir.setReturnValue(TreeFeatures.TALL_MANGROVE);
            cir.cancel();
        }
        cir.setReturnValue(randomSource.nextFloat() > 0.3F ? TreeFeatures.MANGROVE : WilderTreeConfigured.NEW_SWAMP_TREE);
        cir.cancel();
    }
}