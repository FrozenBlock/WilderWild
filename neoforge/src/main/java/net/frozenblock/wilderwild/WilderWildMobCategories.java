package net.frozenblock.wilderwild;

import java.util.ArrayList;
import net.frozenblock.lib.entity.api.category.entrypoint.FrozenMobCategoryEntrypoint;
import net.frozenblock.lib.entity.impl.category.FrozenMobCategory;
import net.frozenblock.wilderwild.config.WWEntityConfig;

public final class WilderWildMobCategories implements FrozenMobCategoryEntrypoint {

	@Override
	public void newCategories(ArrayList<FrozenMobCategory> context) {
		context.add(FrozenMobCategoryEntrypoint.createCategory(WWConstants.id("firefly"), "FF", WWEntityConfig.FIREFLY_SPAWN_CAP.get(), true, false, 40));
		context.add(FrozenMobCategoryEntrypoint.createCategory(WWConstants.id("butterfly"), "BF", WWEntityConfig.BUTTERFLY_SPAWN_CAP.get(), true, false, 80));
		context.add(FrozenMobCategoryEntrypoint.createCategory(WWConstants.id("jellyfish"), "JF", WWEntityConfig.JELLYFISH_SPAWN_CAP.get(), true, false, 64));
		context.add(FrozenMobCategoryEntrypoint.createCategory(WWConstants.id("crab"), "CR", WWEntityConfig.CRAB_SPAWN_CAP.get(), true, false, 64));
		context.add(FrozenMobCategoryEntrypoint.createCategory(WWConstants.id("tumbleweed"), "TW", WWEntityConfig.TUMBLEWEED_SPAWN_CAP.get(), true, false, 64));
	}
}
