package net.frozenblock.wilderwild.registry;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.frozenblock.lib.feature_flag.api.FrozenFeatureFlags;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagRegistry;
import net.minecraft.world.flag.FeatureFlagSet;

public final class RegisterResources {

	public static final FeatureFlag EXPERIMENTAL;
	public static final FeatureFlagRegistry REGISTRY;
	public static final Codec<FeatureFlagSet> CODEC;

	private RegisterResources() {
		throw new UnsupportedOperationException("RegisterResources contains only static declarations.");
	}

	public static void register() {
		ResourceManagerHelper.registerBuiltinResourcePack(WilderWild.id("new_main_menu"), WilderSharedConstants.MOD_CONTAINER, ResourcePackActivationType.DEFAULT_ENABLED);
	}

	static {
		var builder = FrozenFeatureFlags.createBuilder(WilderSharedConstants.MOD_CONTAINER);
		EXPERIMENTAL = builder.create(WilderWild.id("experimental"));
		REGISTRY = builder.build();
		CODEC = REGISTRY.codec();
	}
}
