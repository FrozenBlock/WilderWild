package net.frozenblock.api.simplemethods;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
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
    public static Block getRandomBlock(RandomSource random, TagKey<Block> tag) {
        ArrayList<Block> blocks = new ArrayList<>();
        for (Holder<Block> block : Registry.BLOCK.getTagOrEmpty(tag)) {
            if (block.unwrapKey().isPresent()) {
                Registry.BLOCK.getOptional(block.unwrapKey().get()).ifPresent(blocks::add);
            }
        }
        if (!blocks.isEmpty()) {
            return blocks.get(random.nextInt(blocks.size()));
        }
        return null;
    }
}
