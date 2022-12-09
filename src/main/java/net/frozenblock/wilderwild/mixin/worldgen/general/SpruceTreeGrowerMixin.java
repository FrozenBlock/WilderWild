package net.frozenblock.wilderwild.mixin.worldgen.general;

import net.frozenblock.wilderwild.world.additions.feature.WilderTreeConfigured;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.SpruceTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SpruceTreeGrower.class, priority = 69420)
public class SpruceTreeGrowerMixin {

    @Inject(method = "getConfiguredFeature", at = @At("RETURN"), cancellable = true)
    public void getConfiguredFeature(RandomSource random, boolean bees, CallbackInfoReturnable<Holder<? extends ConfiguredFeature<?, ?>>> cir) {
        cir.setReturnValue(WilderTreeConfigured.NEW_SPRUCE);
    }

}
