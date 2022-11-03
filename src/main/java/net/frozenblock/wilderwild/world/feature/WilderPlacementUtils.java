package net.frozenblock.wilderwild.world.feature;

import java.util.List;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public class WilderPlacementUtils {

	public static void bootstrap(BootstapContext<PlacedFeature> bootstrapContext) {
		WilderTreePlaced.bootstrap(bootstrapContext);
	}

	public static ResourceKey<PlacedFeature> createKey(String string) {
		return ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, WilderSharedConstants.id(string));
	}

	public static void register(
			BootstapContext<PlacedFeature> bootstapContext, ResourceKey<PlacedFeature> registryKey, Holder<ConfiguredFeature<?, ?>> holder, List<PlacementModifier> list
	) {
		PlacementUtils.register(bootstapContext, registryKey, holder, list);
	}

	public static void register(
			BootstapContext<PlacedFeature> bootstapContext,
			ResourceKey<PlacedFeature> registryKey,
			Holder<ConfiguredFeature<?, ?>> holder,
			PlacementModifier... placementModifiers
	) {
		PlacementUtils.register(bootstapContext, registryKey, holder, placementModifiers);
	}
}
