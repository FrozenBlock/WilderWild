package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.frozenblock.lib.mobcategory.api.FrozenMobCategories;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;
import net.frozenblock.wilderwild.entity.CoconutProjectile;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.frozenblock.wilderwild.entity.render.easter.EasterEggs;
import net.frozenblock.wilderwild.misc.ChestBubbleTicker;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public final class RegisterEntities {
	private RegisterEntities() {
		throw new UnsupportedOperationException("RegisterBlockEntities contains only static declarations.");
	}

	public static final EntityType<AncientHornProjectile> ANCIENT_HORN_PROJECTILE_ENTITY = register(
			"ancient_horn_projectile",
			FabricEntityTypeBuilder.<AncientHornProjectile>create(MobCategory.MISC, AncientHornProjectile::new)
					.fireImmune()
					.dimensions(EntityDimensions.scalable(0.6F, 0.6F))
					.trackRangeBlocks(64)
					.trackedUpdateRate(2)
					.build()
	);

	public static final EntityType<Firefly> FIREFLY = register(
			"firefly",
			FabricEntityTypeBuilder.createMob()
					.spawnGroup(FrozenMobCategories.getCategory(WilderSharedConstants.MOD_ID, "fireflies"))
					.entityFactory(Firefly::new)
					.defaultAttributes(Firefly::addAttributes)
					.spawnRestriction(SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING, Firefly::canSpawn)
					.dimensions(EntityDimensions.scalable(0.3F, 0.3F))
					.build()
	);

	public static final EntityType<Jellyfish> JELLYFISH = register(
			"jellyfish",
			FabricEntityTypeBuilder.createMob()
					.spawnGroup(FrozenMobCategories.getCategory(WilderSharedConstants.MOD_ID, "jellyfish"))
					.entityFactory(Jellyfish::new)
					.defaultAttributes(Jellyfish::addAttributes)
					.spawnRestriction(SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Jellyfish::canSpawn)
					.dimensions(EntityDimensions.scalable(0.4F, 0.4F))
					.build()
	);

	public static final EntityType<Tumbleweed> TUMBLEWEED = register(
			"tumbleweed",
			FabricEntityTypeBuilder.createMob()
					.spawnGroup(FrozenMobCategories.getCategory(WilderSharedConstants.MOD_ID, "tumbleweed"))
					.entityFactory(Tumbleweed::new)
					.defaultAttributes(Tumbleweed::addAttributes)
					.spawnRestriction(SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Tumbleweed::canSpawn)
					.dimensions(EntityDimensions.scalable(0.98F, 0.98F))
					.build()
	);

	public static final EntityType<CoconutProjectile> COCONUT = register(
			"coconut",
			FabricEntityTypeBuilder.<CoconutProjectile>create(MobCategory.MISC, CoconutProjectile::new)
					.dimensions(EntityDimensions.scalable(0.25F, 0.25F))
					.trackRangeBlocks(64)
					.trackedUpdateRate(10)
					.build()
	);

	public static final EntityType<ChestBubbleTicker> CHEST_BUBBLER = register(
			"chest_bubbler",
			FabricEntityTypeBuilder.<ChestBubbleTicker>create(MobCategory.MISC, ChestBubbleTicker::new)
					.dimensions(EntityDimensions.scalable(1.0F, 1.0F))
					.trackRangeBlocks(0)
					.trackedUpdateRate(10)
					.build()
	);

    public static void init() {
        WilderSharedConstants.logWild("Registering Entities for", WilderSharedConstants.UNSTABLE_LOGGING);
		EasterEggs.registerEaster();
    }

    private static <E extends Entity, T extends EntityType<E>> T register(String path, T entityType) {
        return Registry.register(Registry.ENTITY_TYPE, WilderSharedConstants.id(path), entityType);
    }
}
