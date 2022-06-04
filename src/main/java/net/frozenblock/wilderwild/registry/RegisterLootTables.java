package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.EnchantRandomlyLootFunction;

public class RegisterLootTables {
	// allows for other mods to add their items to the loot table without breaking anything

	// delete the ancient city chest override when this works lol
    public static void init() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
	      if (LootTables.ANCIENT_CITY_CHEST.equals(id) && source.isBuiltin()) {
	          LootPool.Builder pool = LootPool.builder()
	              .with(ItemEntry.builder(RegisterItems.ANCIENT_HORN)
						  .weight(1)
						  .apply(new EnchantRandomlyLootFunction.Builder()
								  .add(RegisterEnchantments.ANCIENT_HORN_COOLDOWN_ENCHANTMENT)
								  .add(RegisterEnchantments.ANCIENT_HORN_SPEED_ENCHANTMENT)
						  )
				  );

	          tableBuilder.pool(pool);
	      }
	  });
    }
}
