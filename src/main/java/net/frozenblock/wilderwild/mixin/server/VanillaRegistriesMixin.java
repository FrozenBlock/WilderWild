package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.world.feature.WilderFeatureUtils;
import net.frozenblock.wilderwild.world.feature.WilderPlacementUtils;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.registries.VanillaRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(VanillaRegistries.class)
public class VanillaRegistriesMixin {

	@ModifyVariable(method = "<clinit>", at = @At(value = "FIELD", target = "Lnet/minecraft/data/registries/VanillaRegistries;BUILDER:Lnet/minecraft/core/RegistrySetBuilder;"))
	private static RegistrySetBuilder addWilderRegistries(RegistrySetBuilder original) {
		return original
				.add(Registry.BIOME_REGISTRY, RegisterWorldgen::registerWorldgen)
				.add(Registry.CONFIGURED_FEATURE_REGISTRY, WilderFeatureUtils::bootstrap)
				.add(Registry.PLACED_FEATURE_REGISTRY, WilderPlacementUtils::bootstrap);
	}
}
