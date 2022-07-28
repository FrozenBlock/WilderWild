package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.entity.HangingTendrilBlockEntity;
import net.frozenblock.wilderwild.block.entity.NewSculkSensorBlockEntity;
import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SculkSensorBlockEntity;
import net.minecraft.util.registry.Registry;

public class RegisterBlockEntities {
    public static final BlockEntityType<HangingTendrilBlockEntity> HANGING_TENDRIL = Registry.register(Registry.BLOCK_ENTITY_TYPE, WilderWild.id("hanging_tendril"), FabricBlockEntityTypeBuilder.create(HangingTendrilBlockEntity::new, RegisterBlocks.HANGING_TENDRIL).build(null));
    public static final BlockEntityType<NewSculkSensorBlockEntity> NEW_SCULK_SENSOR = Registry.register(Registry.BLOCK_ENTITY_TYPE, WilderWild.id("new_sculk_sensor"), FabricBlockEntityTypeBuilder.create(NewSculkSensorBlockEntity::new, Blocks.SCULK_SENSOR).build(null));
    public static final BlockEntityType<TermiteMoundBlockEntity> TERMITE_MOUND = Registry.register(Registry.BLOCK_ENTITY_TYPE, WilderWild.id("termite_mound"), FabricBlockEntityTypeBuilder.create(TermiteMoundBlockEntity::new, RegisterBlocks.TERMITE_MOUND).build(null));

    public static void register() {
        WilderWild.logWild("Registering BlockEntities for", WilderWild.UNSTABLE_LOGGING);
    }
}
