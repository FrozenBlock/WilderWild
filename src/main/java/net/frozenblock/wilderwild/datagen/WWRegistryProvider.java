package net.frozenblock.wilderwild.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.frozenblock.wilderwild.world.generation.WilderFeatureBootstrap;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

final class WWRegistryProvider extends FabricDynamicRegistryProvider {

	WWRegistryProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(@NotNull HolderLookup.Provider registries, @NotNull Entries entries) {
		final var damageTypes = WWDataGenerator.asLookup(entries.getLookup(Registries.DAMAGE_TYPE));

		entries.addAll(damageTypes);

		WilderFeatureBootstrap.bootstrap(entries);
	}

	@Override
	@NotNull
	public String getName() {
		return "Wilder Wild Dynamic Registries";
	}
}
