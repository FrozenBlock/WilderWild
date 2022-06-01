package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.AncientHornProjectileEntity;
import net.frozenblock.wilderwild.entity.FireflyEntity;
import net.frozenblock.wilderwild.entity.WilderBoatEntity;
import net.frozenblock.wilderwild.entity.WilderChestBoatEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RegisterEntities {

    public static final EntityType<AncientHornProjectileEntity> ANCIENT_HORN_PROJECTILE_ENTITY = Registry.register(Registry.ENTITY_TYPE, new Identifier(WilderWild.MOD_ID, "ancient_horn_projectile"), FabricEntityTypeBuilder.<AncientHornProjectileEntity>create(SpawnGroup.MISC, AncientHornProjectileEntity::new).dimensions(EntityDimensions.fixed(0.6F, 0.6F)).trackRangeBlocks(8).trackedUpdateRate(2).build());
    public static final EntityType<FireflyEntity> FIREFLY = Registry.register(Registry.ENTITY_TYPE, new Identifier(WilderWild.MOD_ID, "firefly"), FabricEntityTypeBuilder.create(WilderWild.FIREFLIES, FireflyEntity::new).dimensions(EntityDimensions.fixed(0.3F, 0.3F)).build());
    public static EntityType<WilderBoatEntity> BAOBAB_BOAT = Registry.register(Registry.ENTITY_TYPE, new Identifier(WilderWild.MOD_ID, "baobab_boat"), FabricEntityTypeBuilder.<WilderBoatEntity>create(SpawnGroup.MISC, WilderBoatEntity::new).dimensions(EntityDimensions.fixed(1.375F, 0.5625F)).build());
    public static EntityType<WilderChestBoatEntity> BAOBAB_CHEST_BOAT = Registry.register(Registry.ENTITY_TYPE, new Identifier(WilderWild.MOD_ID, "baobab_chest_boat"), FabricEntityTypeBuilder.<WilderChestBoatEntity>create(SpawnGroup.MISC, WilderChestBoatEntity::new).dimensions(EntityDimensions.fixed(1.375F, 0.5625F)).build());

    public static void init() {
        WilderWild.logWild("Registering Entities for", true);
        FabricDefaultAttributeRegistry.register(FIREFLY, FireflyEntity.addAttributes());
    }
}
