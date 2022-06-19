package net.frozenblock.api.simplemethods;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;

import java.util.ArrayList;
import java.util.Optional;

/**
 * <h2>Contains booleans to check if something is within a tag</h2>
 *
 * <h3>Example usage</h3>
 * <pre>
 * {@code
 * if (WilderTags.blockTagcontains(block, BlockTags.MINEABLE_PICKAXE)) {
 *
 * }
 *
 * return WilderWild.blockTagContains(block, BlockTags.MINEABLE_PICKAXE);
 * }
 * </pre>
 */
public class WilderTags {
    // lol just a port from twm

    public static boolean blockTagContains(Block block1, TagKey<Block> tag) {
        for (RegistryEntry<Block> block : Registry.BLOCK.iterateEntries(tag)) {
            if (block.getKey().equals(Registry.BLOCK.getKey(block1))) {
                return true;
            }
        }
        return false;
    }

    public static Block getRandomBlock(Random random, TagKey<Block> tag) {
        ArrayList<Block> blocks = new ArrayList<>();
        for (RegistryEntry<Block> block : Registry.BLOCK.iterateEntries(tag)) {
            if (block.getKey().isPresent()) {
                Optional<Block> block1 = Registry.BLOCK.getOrEmpty(block.getKey().get());
                block1.ifPresent(blocks::add);
            }
        }
        if (!blocks.isEmpty()) {
            return blocks.get(random.nextBetween(0, 1) * blocks.size());
        }
        return null;
    }

    public static boolean fluidTagContains(Fluid fluid1, TagKey<Fluid> tag) {
        for (RegistryEntry<Fluid> fluid : Registry.FLUID.iterateEntries(tag)) {
            if (fluid.getKey().equals(Registry.FLUID.getKey(fluid1))) {
                return true;
            }
        }
        return false;
    }

    /**
     * @deprecated  Use entity.isIn(tag) instead
     */
    @Deprecated
    public static boolean entityTagContains(EntityType<?> type, TagKey<EntityType<?>> tag) {
        for (RegistryEntry<EntityType<?>> entity : Registry.ENTITY_TYPE.iterateEntries(tag)) {
            if (entity.getKey().equals(Registry.ENTITY_TYPE.getKey(type))) {
                return true;
            }
        }
        return false;
    }

    public static boolean itemTagContains(Item item1, TagKey<Item> tag) {
        for (RegistryEntry<Item> item : Registry.ITEM.iterateEntries(tag)) {
            if (item.getKey().equals(Registry.ITEM.getKey(item1))) {
                return true;
            }
        }
        return false;
    }
}
