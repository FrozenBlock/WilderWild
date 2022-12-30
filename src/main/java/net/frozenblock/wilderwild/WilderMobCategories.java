package net.frozenblock.wilderwild;

import java.util.ArrayList;
import net.frozenblock.lib.mobcategory.api.entrypoint.FrozenMobCategoryEntrypoint;
import net.frozenblock.lib.mobcategory.impl.FrozenMobCategory;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;

public class WilderMobCategories extends FrozenMobCategoryEntrypoint {

	@Override
	public ArrayList<FrozenMobCategory> newCategories() {
		ArrayList<FrozenMobCategory> list = new ArrayList<>();
		list.add(FrozenMobCategoryEntrypoint.createCategory(WilderSharedConstants.MOD_ID, "fireflies", ClothConfigInteractionHandler.fireflySpawnCap(), true, false, 80));
		list.add(FrozenMobCategoryEntrypoint.createCategory(WilderSharedConstants.MOD_ID, "jellyfish", ClothConfigInteractionHandler.jellyfishSpawnCap(), true, false, 64));
		list.add(FrozenMobCategoryEntrypoint.createCategory(WilderSharedConstants.MOD_ID, "tumbleweed", ClothConfigInteractionHandler.tumbleweedSpawnCap(), true, false, 64));
		return list;
	}

}
