package net.frozenblock.wilderwild.tag;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

public class WilderBlockTags {
    public static final TagKey<Block> ANCIENT_CITY_BLOCKS = of("ancient_city_blocks");
    public static final TagKey<Block> SCULK_SLAB_REPLACEABLE_WORLDGEN = of("sculk_slab_replaceable_worldgen");
    public static final TagKey<Block> SCULK_STAIR_REPLACEABLE_WORLDGEN = of("sculk_stair_replaceable_worldgen");
    public static final TagKey<Block> SCULK_WALL_REPLACEABLE_WORLDGEN = of("sculk_wall_replaceable_worldgen");
    public static final TagKey<Block> SCULK_VEIN_REMOVE = of("sculk_vein_removed_on");
    public static final TagKey<Block> ANCIENT_HORN_NON_COLLIDE = of("ancient_horn_vibration_non_collide");
    public static final TagKey<Block> KILLS_TERMITE = of("kills_termite");
    public static final TagKey<Block> TERMITE_BREAKABLE = of("termite_breakable");
    public static final TagKey<Block> BLOCKS_TERMITE = of("blocks_termite");
    public static final TagKey<Block> FIREFLY_HIDEABLE_BLOCKS = of("firefly_hideable_blocks");
    public static final TagKey<Block> PACKED_MUD_REPLACEABLE = of("packed_mud_replaceable");

    private WilderBlockTags() {
    }

    private static TagKey<Block> of(String path) {
        return TagKey.of(Registry.BLOCK_KEY, WilderWild.id(path));
    }
}
