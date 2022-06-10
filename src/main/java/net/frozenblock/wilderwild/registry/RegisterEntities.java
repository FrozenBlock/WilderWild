package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.AncientHornProjectileEntity;
import net.frozenblock.wilderwild.entity.FireflyEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;

public class RegisterEntities {

    public static final EntityType<AncientHornProjectileEntity> ANCIENT_HORN_PROJECTILE_ENTITY = Registry.register(Registry.ENTITY_TYPE, new Identifier(WilderWild.MOD_ID, "ancient_horn_projectile"), FabricEntityTypeBuilder.<AncientHornProjectileEntity>create(SpawnGroup.MISC, AncientHornProjectileEntity::new).dimensions(EntityDimensions.fixed(0.6F, 0.6F)).trackRangeBlocks(8).trackedUpdateRate(2).build());
    public static final EntityType<FireflyEntity> FIREFLY = Registry.register(Registry.ENTITY_TYPE, new Identifier(WilderWild.MOD_ID, "firefly"), FabricEntityTypeBuilder.createLiving().spawnGroup(WilderWild.FIREFLIES).entityFactory(FireflyEntity::new).defaultAttributes(FireflyEntity::addAttributes).dimensions(EntityDimensions.fixed(0.3F, 0.3F)).build());

    public static void init() {
        WilderWild.logWild("Registering Entities for", WilderWild.UNSTABLE_LOGGING);
        SpawnRestrictionAccessor.callRegister(FIREFLY, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FireflyEntity::canSpawn);
    }
}
