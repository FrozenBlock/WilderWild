package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.frozenblock.wilderwild.entity.render.easter.EasterEggs;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public final class RegisterEntities {
	private RegisterEntities() {
		throw new UnsupportedOperationException("RegisterBlockEntities contains only static declarations.");
	}

    public static final EntityType<AncientHornProjectile> ANCIENT_HORN_PROJECTILE_ENTITY = Registry.register(Registry.ENTITY_TYPE, WilderWild.id("ancient_horn_projectile"), FabricEntityTypeBuilder.<AncientHornProjectile>create(MobCategory.MISC, AncientHornProjectile::new).fireImmune().dimensions(EntityDimensions.scalable(0.6F, 0.6F)).trackRangeBlocks(64).trackedUpdateRate(2).build());
    public static final EntityType<Firefly> FIREFLY = Registry.register(Registry.ENTITY_TYPE, WilderWild.id("firefly"), FabricEntityTypeBuilder.createMob().spawnGroup(WilderWild.FIREFLIES).entityFactory(Firefly::new).defaultAttributes(Firefly::addAttributes).dimensions(EntityDimensions.scalable(0.3F, 0.3F)).build());
    public static final EntityType<Jellyfish> JELLYFISH = Registry.register(Registry.ENTITY_TYPE, WilderWild.id("jellyfish"), FabricEntityTypeBuilder.createMob().spawnGroup(WilderWild.JELLYFISH).entityFactory(Jellyfish::new).defaultAttributes(Jellyfish::addAttributes).dimensions(EntityDimensions.scalable(0.4F, 0.4F)).build());
	public static final EntityType<Tumbleweed> TUMBLEWEED = Registry.register(Registry.ENTITY_TYPE, WilderWild.id("tumbleweed"), FabricEntityTypeBuilder.<Tumbleweed>create(MobCategory.MISC, Tumbleweed::new).dimensions(EntityDimensions.scalable(0.98F, 0.98F)).build());

    public static void init() {
        WilderWild.logWild("Registering Entities for", WilderWild.UNSTABLE_LOGGING);
        SpawnPlacements.register(FIREFLY, SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING, Firefly::canSpawn);
        SpawnPlacements.register(JELLYFISH, SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Jellyfish::canSpawn);
		EasterEggs.registerEaster();
    }
}
