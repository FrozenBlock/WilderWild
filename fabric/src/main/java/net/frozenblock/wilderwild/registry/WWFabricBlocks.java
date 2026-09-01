package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.frozenblock.lib.block.storage.api.NoInteractionStorage;
import static net.frozenblock.wilderwild.registry.WWBlocks.STONE_CHEST;

public final class WWFabricBlocks {

	public static void registerBlockProperties() {
		registerInventories();
	}

	private static void registerInventories() {
		ItemStorage.SIDED.registerForBlocks(
			(level, pos, state, blockEntity, direction) -> new NoInteractionStorage<>(),
			STONE_CHEST.get()
		);
	}

	private WWFabricBlocks() {}
}
