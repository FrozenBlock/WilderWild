package net.frozenblock.wilderwild.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.frozenblock.lib.tag.api.FrozenItemTags;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.tag.WilderItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

final class WWItemTagProvider extends FabricTagProvider.ItemTagProvider {

	WWItemTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> completableFuture) {
		super(output, completableFuture);
	}

	@Override
	protected void addTags(@NotNull HolderLookup.Provider arg) {
		this.getOrCreateTagBuilder(FrozenItemTags.ALWAYS_SAVE_COOLDOWNS)
			.add(RegisterItems.ANCIENT_HORN);

		this.getOrCreateTagBuilder(WilderItemTags.BROWN_MUSHROOM_STEW_INGREDIENTS)
			.add(Items.BROWN_MUSHROOM)
			.addOptional(WilderSharedConstants.id("brown_shelf_fungus"));

		this.getOrCreateTagBuilder(WilderItemTags.RED_MUSHROOM_STEW_INGREDIENTS)
			.add(Items.RED_MUSHROOM)
			.addOptional(WilderSharedConstants.id("red_shelf_fungus"));
	}
}
