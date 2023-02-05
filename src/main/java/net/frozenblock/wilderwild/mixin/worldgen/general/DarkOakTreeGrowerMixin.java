package net.frozenblock.wilderwild.mixin.worldgen.general;

import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.world.additions.feature.WilderTreeConfigured;
import net.minecraft.core.Holder;
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
    public void wilderWild$getConfiguredMegaFeature(RandomSource randomSource, CallbackInfoReturnable<Holder<? extends ConfiguredFeature<?, ?>>> info) {
		if (WilderSharedConstants.CONFIG().wildTrees()) {
			if (randomSource.nextFloat() < 0.2F) {
				info.setReturnValue(WilderTreeConfigured.NEW_TALL_DARK_OAK);
			}
		}
    }

}
