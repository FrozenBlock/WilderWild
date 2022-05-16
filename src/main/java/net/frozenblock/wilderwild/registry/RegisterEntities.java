package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.AncientHornProjectileEntity;
import net.frozenblock.wilderwild.entity.FireflyEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RegisterEntities {
    //public static final EntityType<SculkSensorTendrilEntity> TENDRIL_ENTITY = Registry.register(Registry.ENTITY_TYPE, new Identifier(WilderWild.MOD_ID, "sculk_sensor_tendril"), FabricEntityTypeBuilder.create(SpawnGroup.MISC, SculkSensorTendrilEntity::new).dimensions(EntityDimensions.fixed(1.0f, 0.0f)).build());
    public static final EntityType<AncientHornProjectileEntity> ANCIENT_HORN_PROJECTILE_ENTITY = Registry.register(Registry.ENTITY_TYPE, new Identifier(WilderWild.MOD_ID, "ancient_horn_projectile"), FabricEntityTypeBuilder.<AncientHornProjectileEntity>create(SpawnGroup.MISC, AncientHornProjectileEntity::new).dimensions(EntityDimensions.fixed(0.6F, 0.6F)).trackRangeBlocks(8).trackedUpdateRate(2).build());
    public static final EntityType<FireflyEntity> FIREFLY = Registry.register(Registry.ENTITY_TYPE, new Identifier(WilderWild.MOD_ID, "firefly"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, FireflyEntity::new).dimensions(EntityDimensions.fixed(0.3F, 0.3F)).build());


    public static void init() {
        //FabricDefaultAttributeRegistry.register(TENDRIL_ENTITY, SculkSensorTendrilEntity.createLivingAttributes());
        FabricDefaultAttributeRegistry.register(FIREFLY, FireflyEntity.addAttributes());
    }
}
