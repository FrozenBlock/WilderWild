package net.frozenblock.wilderwild.datagen;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.frozenblock.wilderwild.registry.RegisterDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import org.jetbrains.annotations.NotNull;

final class WWDamageTypeTagProvider extends FabricTagProvider<DamageType> {

	public WWDamageTypeTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, Registries.DAMAGE_TYPE, lookupProvider);
	}

	@Override
	public void addTags(@NotNull HolderLookup.Provider arg) {
		this.getOrCreateTagBuilder(DamageTypeTags.NO_ANGER)
			.add(RegisterDamageTypes.TUMBLEWEED)
			.add(RegisterDamageTypes.PRICKLY_PEAR);

		this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ARMOR)
			.add(RegisterDamageTypes.ANCIENT_HORN);

		this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_EFFECTS)
			.add(RegisterDamageTypes.PRICKLY_PEAR);

		this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ENCHANTMENTS)
			.add(RegisterDamageTypes.ANCIENT_HORN);

		this.getOrCreateTagBuilder(DamageTypeTags.WITCH_RESISTANT_TO)
			.add(RegisterDamageTypes.ANCIENT_HORN);
	}
}
