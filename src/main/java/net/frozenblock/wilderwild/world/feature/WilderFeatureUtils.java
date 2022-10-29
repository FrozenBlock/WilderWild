package net.frozenblock.wilderwild.world.feature;

import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.jetbrains.annotations.NotNull;

class WilderFeatureUtils {


	static Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> register(String id, Feature<NoneFeatureConfiguration> feature) {
		return register(id, feature, FeatureConfiguration.NONE);
	}

	static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(@NotNull String id, F feature, @NotNull FC config) {
		return registerExact(BuiltinRegistries.CONFIGURED_FEATURE, id, new ConfiguredFeature<>(feature, config));
	}

	static <V extends T, T> Holder<V> registerExact(Registry<T> registry, String id, V value) {
		return (Holder<V>) BuiltinRegistries.register(registry, WilderSharedConstants.id(id), value);
	}
}
