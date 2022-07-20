package net.frozenblock.api.simplemethods;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

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
        for (Holder<Block> block : Registry.BLOCK.getTagOrEmpty(tag)) {
            if (block.unwrapKey().equals(Registry.BLOCK.getResourceKey(block1))) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    public static Block getRandomBlock(RandomSource random, TagKey<Block> tag) {
        ArrayList<Block> blocks = new ArrayList<>();
        for (Holder<Block> block : Registry.BLOCK.getTagOrEmpty(tag)) {
            if (block.unwrapKey().isPresent()) {
                Registry.BLOCK.getOptional(block.unwrapKey().get()).ifPresent(blocks::add);
            }
        }
        if (!blocks.isEmpty()) {
            return blocks.get(WilderWild.random().nextInt(blocks.size()));
        }
        return null;
    }

    public static boolean fluidTagContains(Fluid fluid1, TagKey<Fluid> tag) {
        for (Holder<Fluid> fluid : Registry.FLUID.getTagOrEmpty(tag)) {
            if (fluid.unwrapKey().equals(Registry.FLUID.getResourceKey(fluid1))) {
                return true;
            }
        }
        return false;
    }

    /**
     * @deprecated Use entity.isIn(tag) instead
     */
    @Deprecated
    public static boolean entityTagContains(EntityType<?> type, TagKey<EntityType<?>> tag) {
        for (Holder<EntityType<?>> entity : Registry.ENTITY_TYPE.getTagOrEmpty(tag)) {
            if (entity.unwrapKey().equals(Registry.ENTITY_TYPE.getResourceKey(type))) {
                return true;
            }
        }
        return false;
    }

    public static boolean itemTagContains(Item item1, TagKey<Item> tag) {
        for (Holder<Item> item : Registry.ITEM.getTagOrEmpty(tag)) {
            if (item.unwrapKey().equals(Registry.ITEM.getResourceKey(item1))) {
                return true;
            }
        }
        return false;
    }
}
