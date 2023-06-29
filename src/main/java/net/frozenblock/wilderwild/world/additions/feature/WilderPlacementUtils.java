package net.frozenblock.wilderwild.world.additions.feature;

import java.util.List;
import net.frozenblock.lib.worldgen.feature.api.FrozenPlacedFeature;
import net.frozenblock.lib.worldgen.feature.api.placementmodifier.NoisePlacementFilter;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import org.jetbrains.annotations.NotNull;

public final class WilderPlacementUtils {
	private WilderPlacementUtils() {
		throw new UnsupportedOperationException("WilderPlacementUtils contains only static declarations.");
	}

	@NotNull
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static <FC extends FeatureConfiguration> FrozenPlacedFeature register(@NotNull String id, Holder<ConfiguredFeature<FC, ?>> configured, @NotNull List<PlacementModifier> modifiers) {
		return new FrozenPlacedFeature(WilderSharedConstants.id(id))
			.makeAndSetHolder((Holder) configured, modifiers);
	}

	@NotNull
	public static <FC extends FeatureConfiguration> FrozenPlacedFeature register(@NotNull String id, Holder<ConfiguredFeature<FC, ?>> registryEntry, @NotNull PlacementModifier... modifiers) {
		return register(id, registryEntry, List.of(modifiers));
	}

	@NotNull
	public static FrozenPlacedFeature register(@NotNull String id) {
		var key = WilderSharedConstants.id(id);
		return new FrozenPlacedFeature(key);
	}

	public static final NoisePlacementFilter TREE_CLEARING_FILTER = new NoisePlacementFilter(4, 0.0065, 0.625, 1.0, 0.2, false, false, false);
	public static final NoisePlacementFilter SHRUB_CLEARING_FILTER = new NoisePlacementFilter(4, 0.0065, 0.69, 1.0, 0.2, false, false, false);
	public static final NoisePlacementFilter TREE_CLEARING_FILTER_INVERTED = new NoisePlacementFilter(4, 0.0065, 0.675, 1.0, 0.175, false, false, true);

}
