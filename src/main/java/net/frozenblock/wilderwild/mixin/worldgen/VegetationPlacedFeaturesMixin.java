package net.frozenblock.wilderwild.mixin.worldgen;

import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(VegetationPlacedFeatures.class)
public class VegetationPlacedFeaturesMixin {

    // how do you override it without crashing ADSRFPO JDS JOGSDFGJ
    /*(@Shadow @Final @Mutable public static RegistryEntry<PlacedFeature> BIRCH_TALL;

    @Inject(method = "<clinit>", at = @At(value = "FIELD", opcode = Opcodes.PUTSTATIC, target = "Lnet/minecraft/world/gen/feature/VegetationPlacedFeatures;BIRCH_TALL:Lnet/minecraft/util/registry/RegistryEntry;", shift = At.Shift.NONE), cancellable = true)
    private static void newTallBirchTrees(CallbackInfo ci) {
        ci.cancel();
        var tallBirch = WilderPlacedFeatures.NEW_TALL_BIRCH_PLACED;
        BIRCH_TALL = tallBirch;
    }*/
}
