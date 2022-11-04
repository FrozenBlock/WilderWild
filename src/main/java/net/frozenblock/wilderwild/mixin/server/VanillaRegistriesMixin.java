package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.world.feature.WilderConfiguredFeatureBootstrap;
import net.frozenblock.wilderwild.world.feature.WilderConfiguredFeatures;
import net.frozenblock.wilderwild.world.feature.WilderPlacedFeatureBootstrap;
import net.frozenblock.wilderwild.world.feature.WilderPlacedFeatures;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.registries.VanillaRegistries;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VanillaRegistries.class)
public class VanillaRegistriesMixin {

	@Shadow
	@Final
	@Mutable
	private static RegistrySetBuilder BUILDER;

	@Inject(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/RegistrySetBuilder;add(Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/core/RegistrySetBuilder$RegistryBootstrap;)Lnet/minecraft/core/RegistrySetBuilder;", ordinal = 14))
	private static void addWilderRegistries(CallbackInfo ci) {
		BUILDER = BUILDER.add(Registry.BIOME_REGISTRY, RegisterWorldgen::registerWorldgen)
				.add(Registry.CONFIGURED_FEATURE_REGISTRY, WilderConfiguredFeatureBootstrap::bootstrap)
				.add(Registry.PLACED_FEATURE_REGISTRY, WilderPlacedFeatureBootstrap::bootstrap);
	}
}
