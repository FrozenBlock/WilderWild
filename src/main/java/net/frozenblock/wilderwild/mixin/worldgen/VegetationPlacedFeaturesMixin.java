package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.world.feature.WilderPlacedFeatures;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
