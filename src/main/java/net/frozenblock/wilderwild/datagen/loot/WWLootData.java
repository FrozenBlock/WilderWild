package net.frozenblock.wilderwild.datagen.loot;

import com.google.common.collect.Maps;
import net.frozenblock.wilderwild.entity.variant.JellyfishVariant;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.Util;
import net.minecraft.world.level.ItemLike;
import java.util.LinkedHashMap;
import java.util.Map;

public interface WWLootData {

	Map<JellyfishVariant, ItemLike> NEMATOCYST_BY_DYE = Util.make(Maps.newEnumMap(JellyfishVariant.class), map -> {
		map.put(JellyfishVariant.BLUE, WWBlocks.BLUE_NEMATOCYST);
		map.put(JellyfishVariant.LIME, WWBlocks.LIME_MESOGLEA);
		map.put(JellyfishVariant.PINK, WWBlocks.PINK_NEMATOCYST);
		map.put(JellyfishVariant.RED, WWBlocks.RED_NEMATOCYST);
		map.put(JellyfishVariant.YELLOW, WWBlocks.YELLOW_NEMATOCYST);

		map.put(JellyfishVariant.PEARLESCENT_BLUE, WWBlocks.BLUE_PEARLESCENT_NEMATOCYST);
		map.put(JellyfishVariant.PEARLESCENT_PURPLE, WWBlocks.PURPLE_PEARLESCENT_NEMATOCYST);
	});
}
