package net.frozenblock.wilderwild.mixin.worldgen;

import net.frozenblock.wilderwild.world.feature.WilderPlacedFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.List;

@Pseudo
@Mixin(value = VegetationPlacements.class, priority = 69420)
public class VegetationPlacementsMixin {

    @Redirect(method = "<clinit>", slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=trees_birch")), at = @At(value = "INVOKE", target = "Lnet/minecraft/data/worldgen/placement/PlacementUtils;register(Ljava/lang/String;Lnet/minecraft/core/Holder;Ljava/util/List;)Lnet/minecraft/core/Holder;", ordinal = 0))
    private static Holder<PlacedFeature> newBirchTrees(String string, Holder<? extends ConfiguredFeature<?, ?>> holder, List<PlacementModifier> list) {
        return WilderPlacedFeatures.CONFIG_BIRCH_TREE();
    }

    @Redirect(method = "<clinit>", slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=birch_tall")), at = @At(value = "INVOKE", target = "Lnet/minecraft/data/worldgen/placement/PlacementUtils;register(Ljava/lang/String;Lnet/minecraft/core/Holder;Ljava/util/List;)Lnet/minecraft/core/Holder;", ordinal = 0))
    private static Holder<PlacedFeature> newTallBirchTrees(String string, Holder<? extends ConfiguredFeature<?, ?>> holder, List<PlacementModifier> list) {
        return WilderPlacedFeatures.CONFIG_TALL_BIRCH_TREE();
    }
}
