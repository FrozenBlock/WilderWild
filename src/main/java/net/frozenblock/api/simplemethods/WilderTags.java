package net.frozenblock.api.simplemethods;

import net.fabricmc.fabric.api.tag.convention.v1.TagUtil;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
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

    @Nullable
    public static Block getRandomBlock(Random random, TagKey<Block> tag) {
        ArrayList<Block> blocks = new ArrayList<>();
        for (RegistryEntry<Block> block : Registry.BLOCK.iterateEntries(tag)) {
            if (block.getKey().isPresent()) {
                Registry.BLOCK.getOrEmpty(block.getKey().get()).ifPresent(blocks::add);
            }
        }
        if (!blocks.isEmpty()) {
            return blocks.get(random.nextInt(blocks.size()));
        }
        return null;
    }
}
