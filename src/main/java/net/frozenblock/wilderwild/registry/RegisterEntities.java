package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;
import net.frozenblock.wilderwild.entity.Firefly;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;

public class RegisterEntities {

    public static final EntityType<AncientHornProjectile> ANCIENT_HORN_PROJECTILE_ENTITY = Registry.register(Registry.ENTITY_TYPE, WilderWild.id("ancient_horn_projectile"), FabricEntityTypeBuilder.<AncientHornProjectile>create(SpawnGroup.MISC, AncientHornProjectile::new).dimensions(EntityDimensions.fixed(0.6F, 0.6F)).trackRangeBlocks(32).trackedUpdateRate(2).build());
    public static final EntityType<Firefly> FIREFLY = Registry.register(Registry.ENTITY_TYPE, WilderWild.id("firefly"), FabricEntityTypeBuilder.createMob().spawnGroup(WilderWild.FIREFLIES).entityFactory(Firefly::new).defaultAttributes(Firefly::addAttributes).dimensions(EntityDimensions.fixed(0.3F, 0.3F)).build());

    public static void init() {
        WilderWild.logWild("Registering Entities for", WilderWild.UNSTABLE_LOGGING);
        SpawnRestrictionAccessor.callRegister(FIREFLY, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING, Firefly::canSpawn);
    }
}
