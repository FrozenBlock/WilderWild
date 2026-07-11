package net.frozenblock.wilderwild.registry;

import net.frozenblock.lib.entity.api.category.MobCategoryApiEntrypoint;
import net.frozenblock.lib.entity.api.category.MutableMobCategory;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.minecraft.world.entity.MobCategory;

public final class WWMobCategories implements MobCategoryApiEntrypoint {
	public static MobCategory FIREFLY;
	public static MobCategory BUTTERFLY;
	public static MobCategory JELLYFISH;
	public static MobCategory CRAB;
	public static MobCategory TUMBLEWEED;

	static {
		MobCategory.values();
	}

	@Override
	public void add(Context context) {
		context.add(
			MutableMobCategory.create(
				WWConstants.MOD_ID,
				"firefly",
				"FF",
				WWEntityConfig.FIREFLY_SPAWN_CAP.get(),
				true,
				false,
				40,
				category -> FIREFLY = category
			)
		);

		context.add(
			MutableMobCategory.create(
				WWConstants.MOD_ID,
				"butterfly",
				"BF",
				WWEntityConfig.BUTTERFLY_SPAWN_CAP.get(),
				true,
				false,
				80,
				category -> BUTTERFLY = category
			)
		);

		context.add(
			MutableMobCategory.create(
				WWConstants.MOD_ID,
				"jellyfish",
				"JF",
				WWEntityConfig.JELLYFISH_SPAWN_CAP.get(),
				true,
				false,
				64,
				category -> JELLYFISH = category
			)
		);

		context.add(
			MutableMobCategory.create(
				WWConstants.MOD_ID,
				"crab",
				"CR",
				WWEntityConfig.CRAB_SPAWN_CAP.get(),
				true,
				false,
				64,
				category -> CRAB = category
			)
		);

		context.add(
			MutableMobCategory.create(
				WWConstants.MOD_ID,
				"tumbleweed",
				"TW",
				WWEntityConfig.TUMBLEWEED_SPAWN_CAP.get(),
				true,
				false,
				64,
				category -> TUMBLEWEED = category
			)
		);
	}
}
