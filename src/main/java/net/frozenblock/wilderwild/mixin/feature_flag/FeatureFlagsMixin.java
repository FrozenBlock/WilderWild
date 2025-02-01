package net.frozenblock.wilderwild.mixin.feature_flag;

import net.frozenblock.lib.FrozenBools;
import net.frozenblock.wilderwild.WWFeatureFlags;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = FeatureFlags.class, priority = 2001)
public class FeatureFlagsMixin {

	@Final
	@Shadow
	@Mutable
	public static FeatureFlagSet DEFAULT_FLAGS;

	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void wilderWild$modifyDefaultSet(CallbackInfo info) {
		if (FrozenBools.hasMod("trailiertales")) {
			DEFAULT_FLAGS = DEFAULT_FLAGS.join(WWFeatureFlags.TRAILIER_TALES_COMPAT_FLAG_SET);
		}
	}
}
