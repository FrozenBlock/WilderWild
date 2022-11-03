package net.frozenblock.wilderwild.world.feature;

import com.mojang.serialization.DataResult;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.qsl.frozenblock.core.registry.api.event.DynamicRegistryManagerSetupContext;

public class WilderFeatureUtils {


	public static Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> register(String id, Feature<NoneFeatureConfiguration> feature) {
		return register(id, feature, FeatureConfiguration.NONE);
	}

	public static Holder<? extends ConfiguredFeature<NoneFeatureConfiguration, ?>> register(DynamicRegistryManagerSetupContext context, DynamicRegistryManagerSetupContext.RegistryMap registries, String id, Feature<NoneFeatureConfiguration> feature) {
		return register(context, registries, id, feature, FeatureConfiguration.NONE);
	}

	public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<? extends ConfiguredFeature<FC, ?>> register(DynamicRegistryManagerSetupContext context, DynamicRegistryManagerSetupContext.RegistryMap registries, @NotNull String id, F feature, @NotNull FC config) {
		var configuredRegistry = registries.get(Registry.CONFIGURED_FEATURE_REGISTRY);
		final ConfiguredFeature<FC, ?> configuredFeature = new ConfiguredFeature<>(feature, config);
		Registry.register(configuredRegistry, WilderSharedConstants.id(id), configuredFeature);
		var featureEntry = getExact(registries, configuredFeature);
		return featureEntry;
	}

	public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(@NotNull String id, F feature, @NotNull FC config) {
		VanillaRegistries.createLookup()
		return registerExact(BuiltinRegistries.CONFIGURED_FEATURE, id, new ConfiguredFeature<>(feature, config));
	}

	public static <V extends T, T extends ConfiguredFeature<?, ?>> Holder<V> registerExact(Registry<T> registry, String id, V value) {
		return (Holder<V>) BuiltinRegistries.register(registry, WilderSharedConstants.id(id), value);
	}

	public static <FC extends FeatureConfiguration, V extends T, T extends ConfiguredFeature<FC, ?>> Holder.Reference<V> getExact(DynamicRegistryManagerSetupContext.RegistryMap registries, V value) {
		var configuredRegistry = registries.get(Registry.CONFIGURED_FEATURE_REGISTRY);
		var holder = configuredRegistry.getHolderOrThrow(configuredRegistry.getResourceKey(value).orElseThrow());
		var exactHolder = getExactReference(holder);
		return exactHolder;
	}

	public static <FC extends FeatureConfiguration, F extends Feature<FC>, V extends ConfiguredFeature<FC, ?>> Holder.Reference<V> getExactReference(Holder.Reference<?> reference) {
		return (Holder.Reference<V>) reference;
	}
}
