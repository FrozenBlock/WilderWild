package net.frozenblock.wilderwild.tag;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class WilderItemTags {
	private WilderItemTags() {
		throw new UnsupportedOperationException("WilderItemTags contains only static declarations.");
	}

    public static final TagKey<Item> GOAT_DROP_MUSIC_DISCS = bind("goat_drop_music_discs");
	public static final TagKey<Item> TUMBLEWEED_COMMON = bind("tumbleweed_common");
	public static final TagKey<Item> TUMBLEWEED_MEDIUM = bind("tumbleweed_medium");
	public static final TagKey<Item> TUMBLEWEED_RARE = bind("tumbleweed_rare");

    private static TagKey<Item> bind(String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, WilderWild.id(path));
    }
}
