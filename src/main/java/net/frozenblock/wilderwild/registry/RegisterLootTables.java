package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.util.Rarity;

public class RegisterLootTables {

    // allows for other mods to add their items to the loot table without breaking anything
    public static void init() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (LootTables.ANCIENT_CITY_CHEST.equals(id) && source.isBuiltin()) {
                LootPool.Builder pool = LootPool.builder()
                        .with(ItemEntry.builder(RegisterItems.ANCIENT_HORN).weight(1).quality(Rarity.EPIC.ordinal() + 1)).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(-10.0F, 0.5F)));

                tableBuilder.pool(pool);
            }
        });

        LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (EntityType.GOAT.getLootTableId().equals(id) && source.isBuiltin()) {
                LootPool.Builder pool = LootPool.builder()
                        .with(ItemEntry.builder(RegisterItems.MUSIC_DISC_GOAT_HORN_SYMPHONY).conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.KILLER, EntityPredicate.Builder.create().type(EntityTypeTags.SKELETONS))));

                tableBuilder.pool(pool);
            }
        }));
    }
}
