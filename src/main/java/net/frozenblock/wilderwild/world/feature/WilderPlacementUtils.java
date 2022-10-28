package net.frozenblock.wilderwild.world.feature;

import java.util.List;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import org.jetbrains.annotations.NotNull;

class WilderPlacementUtils {

	static Holder<PlacedFeature> register(@NotNull String id, Holder<? extends ConfiguredFeature<?, ?>> registryEntry, @NotNull List<PlacementModifier> modifiers) {
		return BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, WilderWild.id(id), new PlacedFeature(Holder.hackyErase(registryEntry), List.copyOf(modifiers)));
	}

	static Holder<PlacedFeature> register(@NotNull String id, Holder<? extends ConfiguredFeature<?, ?>> registryEntry, @NotNull PlacementModifier... modifiers) {
		return register(id, registryEntry, List.of(modifiers));
	}
}
